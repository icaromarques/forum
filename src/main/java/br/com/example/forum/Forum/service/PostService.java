package br.com.example.forum.Forum.service;

import br.com.example.forum.Forum.exception.MensagensException;
import br.com.example.forum.Forum.exception.ValidationException;
import br.com.example.forum.Forum.model.Persistent.Post;
import br.com.example.forum.Forum.model.Persistent.User;
import br.com.example.forum.Forum.model.dto.PostReturnList;
import br.com.example.forum.Forum.model.enumeration.PostTypeEnum;
import br.com.example.forum.Forum.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

    @Autowired
    PostRepository repository;

    @Autowired
    UserService userService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Post save(Post post) throws Exception {


        if (post.getCreationDate() == null) {
            post.setCreationDate(LocalDateTime.now());
        }

        if (post.getQuestion() != null && post.getQuestion().getId() != null) {
            post.setQuestion(this.findById(post.getQuestion().getId()));
        }
        User user = null;
        if (post.getUser().getId() != null) {
            user = userService.findById(post.getUser().getId());
            post.setUser(user);
        } else if (post.getUser().getName() != null) {
            user = userService.findByNameIgnoreCase(post.getUser().getName());

            if (user == null) {
                user = new User(post.getUser().getName());
                user = userService.save(user);
            }

            post.setUser(user);
        } else {
            throw new ValidationException(MensagensException.USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }


        if (post.getLikes() != null && post.getLikes().size() > 0) {
            User userAux = userService.findByNameIgnoreCase(post.getLikes().get(0).getName());

            if (userAux == null) {
                userAux = new User(post.getLikes().get(0).getName());
                userAux = userService.save(userAux);
            }

            Post postAux = this.findById(post.getId());
            post.setLikes(postAux.getLikes());

            User finalUserAux = userAux;
            User userlike = post.getLikes()
                    .stream()
                    .filter(u -> u.getId() == finalUserAux.getId()).findAny().orElse(null);

            if (userlike != null) {
                post.getLikes().remove(userlike);
            } else {
                post.getLikes().add(userAux);
            }
        }

        try {
            post = repository.saveAndFlush(post);
            LOGGER.info(LocalDateTime.now().toString() + "Post - Saved: ", post);

        } catch (Exception e) {
            LOGGER.error(LocalDateTime.now().toString() + "Error to save: ", post);
            throw new ValidationException(MensagensException.SAVE_ERROR.getText(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return post;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteById(Integer id) throws ValidationException {
        try {
            repository.deleteById(id);
            LOGGER.info(LocalDateTime.now().toString() + "Post - Deleted: ", id);
        } catch (Exception ex) {
            throw new ValidationException(MensagensException.DELETE_ERROR.getText(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public Post findById(Integer id) throws ValidationException {
        Optional<Post> post = repository.findById(id);
        return post.orElseThrow(() -> new ValidationException(MensagensException.POST_NOT_FOUND, HttpStatus.NOT_FOUND));
    }


    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public PostReturnList findByType(Integer firstResult, Integer maxResults, Integer questionId, PostTypeEnum typeEnum,
                                     String text, Boolean notAnsewered, Sort.Direction direction, String sortField) throws Exception {

        PostReturnList postReturnList = null;
        if (questionId == null) {
            if (text == null && !notAnsewered) {
                Page<Post> page = repository.findByPostType(typeEnum,
                        PageRequest.of(
                                firstResult,
                                maxResults,
                                Sort.by(direction, sortField)));

                postReturnList = new PostReturnList(page.getTotalPages() - 1,
                        page.getNumber(),
                        page.toList());
                return postReturnList;
            } else if (text == null) {
                Page<Post> page = repository.findByPostTypeAndAnswersIsEmpty(typeEnum,
                        PageRequest.of(
                                firstResult,
                                maxResults,
                                Sort.by(direction, sortField)));

                postReturnList = new PostReturnList(page.getTotalPages() - 1,
                        page.getNumber(),
                        page.toList());
                return postReturnList;
            } else if (!notAnsewered) {
                Page<Post> page = repository
                        .findByPostTypeAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(typeEnum,
                                text,
                                text,
                                PageRequest.of(
                                        firstResult,
                                        maxResults,
                                        Sort.by(direction, sortField)));

                postReturnList = new PostReturnList(page.getTotalPages() - 1,
                        page.getNumber(),
                        page.toList());
                return postReturnList;
            } else {
                Page<Post> page = repository
                        .findWithFilters(typeEnum,
                                text,
                                text,
                                PageRequest.of(
                                        firstResult,
                                        maxResults,
                                        Sort.by(direction, sortField)));

                postReturnList = new PostReturnList(page.getTotalPages() - 1,
                        page.getNumber(),
                        page.toList());
                return postReturnList;
            }
        } else {

            Post question = this.findById(questionId);
            Page<Post> page = repository
                    .findByPostTypeAndQuestion(typeEnum, question, PageRequest.of(
                            firstResult,
                            maxResults,
                            Sort.by(direction, sortField)));

            postReturnList = new PostReturnList(page.getTotalPages() - 1,
                    page.getNumber(),
                    page.toList());
            return postReturnList;
        }

    }

}
