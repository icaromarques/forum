package br.com.example.forum.Forum.controller;

import br.com.example.forum.Forum.model.Persistent.Post;
import br.com.example.forum.Forum.model.Persistent.User;
import br.com.example.forum.Forum.model.dto.PostDto;
import br.com.example.forum.Forum.model.dto.PostReturnList;
import br.com.example.forum.Forum.model.dto.UserDto;
import br.com.example.forum.Forum.model.enumeration.PostTypeEnum;
import br.com.example.forum.Forum.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService service;

    @Autowired
    ModelMapper modelMapper;


    @GetMapping("/questions")
    public ResponseEntity<PostReturnList> findQuestions(@RequestParam(required=true) Integer page,
                                                        @RequestParam(required=true) Integer maxValue,
                                                        @RequestParam(required=false) String text,
                                                        @RequestParam(required=false) Boolean notAnsewered,
                                                        @RequestParam(required=false) Sort.Direction direction,
                                                        @RequestParam(required=false)String sortField) throws Exception {

        PostReturnList posts = service.findByType(page,
                maxValue,
                null,
                PostTypeEnum.QUESTION,
                text,
                notAnsewered == null?false:notAnsewered,
                direction==null? Sort.Direction.ASC:direction ,
                sortField==null?"creationDate":sortField);

        posts.setPosts(posts.getPostsOriginal().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
        return new ResponseEntity<PostReturnList>(posts, HttpStatus.OK);
    }

    @GetMapping("/answers")
    public ResponseEntity<PostReturnList> findAllAnswers(@RequestParam(required=true) Integer questionId,
                                                         @RequestParam(required=true) Integer page,
                                                         @RequestParam(required=true) Integer maxValue) throws Exception {

        PostReturnList posts = service.findByType(page,maxValue,questionId,PostTypeEnum.ANSWER, null, null, Sort.Direction.ASC, "creationDate");
        posts.setPosts(posts.getPostsOriginal().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
        return new ResponseEntity<PostReturnList>(posts, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<PostDto> findById(@PathVariable Integer id) throws Exception {
       Post post = service.findById(id);
        PostDto postDto = convertToDto(post);
       return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Integer id) throws Exception {
        service.deleteById(id);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<PostDto> create(@RequestBody PostDto postDto) throws Exception {
        Post post = convertToEntity(postDto);
        PostDto response = convertToDto(service.save(post));
        return new ResponseEntity<PostDto>(response, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<PostDto> update(@RequestBody PostDto postDto) throws Exception {
        Post post = convertToEntity(postDto);
        PostDto response = convertToDto(service.save(post));
        return new ResponseEntity<PostDto>(response, HttpStatus.OK);
    }


    private PostDto convertToDto(Post post) {
        PostDto postDto = modelMapper.map(post, PostDto.class);
        postDto.setQuestionId(post.getQuestion()!= null?post.getQuestion().getId():null);
        postDto.setUser(modelMapper.map(post.getUser(), UserDto.class));
        postDto.setAnswersCount(post.getAnswers()!= null?post.getAnswers().size():0);
        postDto.setLikesCount(post.getLikes()!= null?post.getLikes().size():0);
        return postDto;
    }

    private Post convertToEntity(PostDto postDto) throws Exception {
        Post post = modelMapper.map(postDto, Post.class);

        if (post.getCreationDate() == null){
            post.setCreationDate(LocalDateTime.now());
        }

        if (postDto.getQuestionId() != null){
            post.setQuestion(new Post(postDto.getQuestionId()));
        }

        if (postDto.getId() != null) {
            Post oldPost = service.findById(postDto.getId());
            post.setUser(oldPost.getUser());
        }

        if (postDto.getUpVote() != null) {
            User userLike = new User();
            userLike.setName(postDto.getUpVote().getName());
            post.getLikes().add(userLike);
        }
        return post;
    }
}
