package br.com.example.forum.Forum.service;

import br.com.example.forum.Forum.exception.ValidationException;
import br.com.example.forum.Forum.model.Persistent.Post;
import br.com.example.forum.Forum.model.Persistent.User;
import br.com.example.forum.Forum.model.dto.PostReturnList;
import br.com.example.forum.Forum.model.enumeration.PostTypeEnum;
import br.com.example.forum.Forum.repository.PostRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PostServiceTest {

    private PostService postServiceUnderTest;

    @Mock
    private Post mockQuestion;
    private Post postUnderTest;
    private User userTest;

    private static final String CREATIONDATE = "creationDate";


    @Before
    public void setUp() {
        userTest = new User("name");
        userTest.setId(0);

        postUnderTest = new Post(0, "title",
                "description",
                userTest,
                LocalDateTime.of(2017, 1, 1, 0, 0, 0),
                mockQuestion,
                Arrays.asList(new Post(0)),
                PostTypeEnum.QUESTION,
                null);
        postServiceUnderTest = new PostService();
        postServiceUnderTest.repository = mock(PostRepository.class);
        postServiceUnderTest.userService = mock(UserService.class);
    }

    @Test
    public void testSave() throws Exception {
        // Setup
        final Post post = new Post(0, "title",
                "description",
                userTest,
                LocalDateTime.of(2017, 1, 1, 0, 0, 0),
                mockQuestion,
                Arrays.asList(new Post(0)),
                PostTypeEnum.QUESTION,
                null);

        when(postServiceUnderTest.repository.findById(0)).thenReturn(Optional.of(post));
        when(postServiceUnderTest.userService.findById(0)).thenReturn(userTest);
        when(postServiceUnderTest.userService.findByNameIgnoreCase("name")).thenReturn(userTest);
        when(postServiceUnderTest.userService.save(any(User.class))).thenReturn(userTest);
        when(postServiceUnderTest.repository.saveAndFlush(any(Post.class))).thenReturn(post);

        // Run the test
        final Post result = postServiceUnderTest.save(post);

        // Verify the results
        Assert.assertEquals(postUnderTest.getId(), result.getId());
        Assert.assertEquals(postUnderTest.getLikes(), result.getLikes());
        Assert.assertEquals(postUnderTest.getUser(), result.getUser());
        Assert.assertEquals(postUnderTest.getQuestion(), result.getQuestion());
        Assert.assertEquals(postUnderTest.getAnswers().get(0).getId(), result.getAnswers().get(0).getId());
        Assert.assertEquals(postUnderTest.getCreationDate(), result.getCreationDate());
        Assert.assertEquals(postUnderTest.getDescription(), result.getDescription());
        Assert.assertEquals(postUnderTest.getTitle(), result.getTitle());
        Assert.assertEquals(postUnderTest.getPostType(), result.getPostType());

    }

    @Test(expected = Exception.class)
    public void testSave_ThrowsException() throws Exception {
        // Setup
        final Post post = new Post(0);
        when(postServiceUnderTest.repository.findById(0)).thenReturn(Optional.of(new Post(0)));
        when(postServiceUnderTest.userService.findById(0)).thenReturn(new User("name"));
        when(postServiceUnderTest.userService.findByNameIgnoreCase("name")).thenReturn(new User("name"));
        when(postServiceUnderTest.userService.save(any(User.class))).thenReturn(new User("name"));
        when(postServiceUnderTest.repository.saveAndFlush(any(Post.class))).thenReturn(new Post(0));

        // Run the test
        postServiceUnderTest.save(post);
    }

    @Test
    public void testDeleteById() throws Exception {
        // Setup

        // Run the test
        postServiceUnderTest.deleteById(0);

        // Verify the results
        verify(postServiceUnderTest.repository).deleteById(0);
    }

    @Test
    public void testFindById() throws Exception {
        // Setup
        when(postServiceUnderTest.repository.findById(0)).thenReturn(Optional.of(postUnderTest));

        // Run the test
        final Post result = postServiceUnderTest.findById(0);

        // Verify the results
        Assert.assertEquals(postUnderTest.getId(), result.getId());
        Assert.assertEquals(postUnderTest.getLikes(), result.getLikes());
        Assert.assertEquals(postUnderTest.getUser(), result.getUser());
        Assert.assertEquals(postUnderTest.getQuestion(), result.getQuestion());
        Assert.assertEquals(postUnderTest.getAnswers().get(0).getId(), result.getAnswers().get(0).getId());
        Assert.assertEquals(postUnderTest.getCreationDate(), result.getCreationDate());
        Assert.assertEquals(postUnderTest.getDescription(), result.getDescription());
        Assert.assertEquals(postUnderTest.getTitle(), result.getTitle());
        Assert.assertEquals(postUnderTest.getPostType(), result.getPostType());
    }

    @Test(expected = ValidationException.class)
    public void testFindById_ThrowsValidationException() throws Exception {
        // Setup
        when(postServiceUnderTest.repository.findById(0)).thenReturn(Optional.of(new Post(0)));

        // Run the test
        postServiceUnderTest.findById(1);
    }

    @Test
    public void testFindByType() throws Exception {
        // Setup

        // Configure PostRepository.findByPostTypeOrderByCreationDateDesc(...).
        final Page<Post> posts = new PageImpl<>(Arrays.asList(postUnderTest));
        when(postServiceUnderTest.repository.findByPostType(PostTypeEnum.QUESTION,
                PageRequest.of(0,
                        1,
                        Sort.by(Sort.Direction.DESC, CREATIONDATE)))).thenReturn(posts);


        // Run the test
        final PostReturnList result = postServiceUnderTest.findByType(0, 1, null, PostTypeEnum.QUESTION, null, false, Sort.Direction.DESC, CREATIONDATE);

        // Verify the results
        Assert.assertNotNull(result);

        Assert.assertEquals(postUnderTest.getId(), result.getPostsOriginal().get(0).getId());
        Assert.assertEquals(postUnderTest.getLikes(), result.getPostsOriginal().get(0).getLikes());
        Assert.assertEquals(postUnderTest.getUser(), result.getPostsOriginal().get(0).getUser());
        Assert.assertEquals(postUnderTest.getQuestion(), result.getPostsOriginal().get(0).getQuestion());
        Assert.assertEquals(postUnderTest.getAnswers().get(0).getId(), result.getPostsOriginal().get(0).getAnswers().get(0).getId());
        Assert.assertEquals(postUnderTest.getCreationDate(), result.getPostsOriginal().get(0).getCreationDate());
        Assert.assertEquals(postUnderTest.getDescription(), result.getPostsOriginal().get(0).getDescription());
        Assert.assertEquals(postUnderTest.getTitle(), result.getPostsOriginal().get(0).getTitle());
        Assert.assertEquals(postUnderTest.getPostType(), result.getPostsOriginal().get(0).getPostType());
    }

    @Test
    public void testFindByType1() throws Exception {
        // Setup

        // Configure PostRepository.findByPostTypeAndAnswersIsEmptyOrderByCreationDateDesc(...).
        final Page<Post> posts1 = new PageImpl<>(Arrays.asList(postUnderTest));
        when(postServiceUnderTest.repository.findByPostTypeAndAnswersIsEmpty(PostTypeEnum.QUESTION,
                PageRequest.of(0,
                        1,
                        Sort.by(Sort.Direction.DESC, CREATIONDATE)))).thenReturn(posts1);

        // Run the test
        final PostReturnList result = postServiceUnderTest.findByType(0, 1, null, PostTypeEnum.QUESTION, null, true,Sort.Direction.DESC, CREATIONDATE);

        // Verify the results
        Assert.assertNotNull(result);

        Assert.assertEquals(postUnderTest.getId(), result.getPostsOriginal().get(0).getId());
        Assert.assertEquals(postUnderTest.getLikes(), result.getPostsOriginal().get(0).getLikes());
        Assert.assertEquals(postUnderTest.getUser(), result.getPostsOriginal().get(0).getUser());
        Assert.assertEquals(postUnderTest.getQuestion(), result.getPostsOriginal().get(0).getQuestion());
        Assert.assertEquals(postUnderTest.getAnswers().get(0).getId(), result.getPostsOriginal().get(0).getAnswers().get(0).getId());
        Assert.assertEquals(postUnderTest.getCreationDate(), result.getPostsOriginal().get(0).getCreationDate());
        Assert.assertEquals(postUnderTest.getDescription(), result.getPostsOriginal().get(0).getDescription());
        Assert.assertEquals(postUnderTest.getTitle(), result.getPostsOriginal().get(0).getTitle());
        Assert.assertEquals(postUnderTest.getPostType(), result.getPostsOriginal().get(0).getPostType());
    }

    @Test
    public void testFindByType2() throws Exception {
        // Setup
        // Configure PostRepository.findByPostTypeAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByCreationDateDesc(...).
        final Page<Post> posts2 = new PageImpl<>(Arrays.asList(postUnderTest));
        when(postServiceUnderTest.repository.findByPostTypeAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(PostTypeEnum.QUESTION,
                "text",
                "text",
                PageRequest.of(0,
                        1,
                        Sort.by(Sort.Direction.DESC, CREATIONDATE)))).thenReturn(posts2);


        // Run the test
        final PostReturnList result = postServiceUnderTest.findByType(0, 1, null, PostTypeEnum.QUESTION, "text", false,Sort.Direction.DESC, CREATIONDATE);

        // Verify the results
        Assert.assertNotNull(result);

        Assert.assertEquals(postUnderTest.getId(), result.getPostsOriginal().get(0).getId());
        Assert.assertEquals(postUnderTest.getLikes(), result.getPostsOriginal().get(0).getLikes());
        Assert.assertEquals(postUnderTest.getUser(), result.getPostsOriginal().get(0).getUser());
        Assert.assertEquals(postUnderTest.getQuestion(), result.getPostsOriginal().get(0).getQuestion());
        Assert.assertEquals(postUnderTest.getAnswers().get(0).getId(), result.getPostsOriginal().get(0).getAnswers().get(0).getId());
        Assert.assertEquals(postUnderTest.getCreationDate(), result.getPostsOriginal().get(0).getCreationDate());
        Assert.assertEquals(postUnderTest.getDescription(), result.getPostsOriginal().get(0).getDescription());
        Assert.assertEquals(postUnderTest.getTitle(), result.getPostsOriginal().get(0).getTitle());
        Assert.assertEquals(postUnderTest.getPostType(), result.getPostsOriginal().get(0).getPostType());
    }

    @Test
    public void testFindByType3() throws Exception {
        // Setup

        // Configure PostRepository.findWithFilters(...).
        final Page<Post> posts3 = new PageImpl<>(Arrays.asList(postUnderTest));
        when(postServiceUnderTest.repository.findWithFilters(PostTypeEnum.QUESTION, "text",
                "text",
                PageRequest.of(0,
                        1,
                        Sort.by(Sort.Direction.DESC, CREATIONDATE)))).thenReturn(posts3);

        // Run the test
        final PostReturnList result = postServiceUnderTest.findByType(0, 1, null, PostTypeEnum.QUESTION, "text", true,Sort.Direction.DESC, CREATIONDATE);

        // Verify the results
        Assert.assertNotNull(result);

        Assert.assertEquals(postUnderTest.getId(), result.getPostsOriginal().get(0).getId());
        Assert.assertEquals(postUnderTest.getLikes(), result.getPostsOriginal().get(0).getLikes());
        Assert.assertEquals(postUnderTest.getUser(), result.getPostsOriginal().get(0).getUser());
        Assert.assertEquals(postUnderTest.getQuestion(), result.getPostsOriginal().get(0).getQuestion());
        Assert.assertEquals(postUnderTest.getAnswers().get(0).getId(), result.getPostsOriginal().get(0).getAnswers().get(0).getId());
        Assert.assertEquals(postUnderTest.getCreationDate(), result.getPostsOriginal().get(0).getCreationDate());
        Assert.assertEquals(postUnderTest.getDescription(), result.getPostsOriginal().get(0).getDescription());
        Assert.assertEquals(postUnderTest.getTitle(), result.getPostsOriginal().get(0).getTitle());
        Assert.assertEquals(postUnderTest.getPostType(), result.getPostsOriginal().get(0).getPostType());
    }

    @Test
    public void testFindByType4() throws Exception {
        // Setup
        User user = new User("name");
        user.setId(1);

        Post post = new Post(0, "title",
                "description",
                user,
                LocalDateTime.of(2017, 1, 1, 0, 0, 0),
                postUnderTest,
                Arrays.asList(new Post(0)),
                PostTypeEnum.ANSWER,
                null);

        // Configure PostRepository.findByPostTypeAndQuestionOrderByCreationDateDesc(...).
        final Page<Post> posts4 = new PageImpl<>(Arrays.asList(post));

        when(postServiceUnderTest.repository.findByPostTypeAndQuestion(
                PostTypeEnum.ANSWER,
                postUnderTest,
                PageRequest.of(
                0,
                1,
                Sort.by(Sort.Direction.DESC, CREATIONDATE)))).thenReturn(posts4);

        when(postServiceUnderTest.repository.findById(0)).thenReturn(Optional.of(postUnderTest));

        // Run the test
        final PostReturnList result = postServiceUnderTest.findByType(0, 1, postUnderTest.getId(), PostTypeEnum.ANSWER, null, false,Sort.Direction.DESC, CREATIONDATE);

        // Verify the results
        Assert.assertNotNull(result);

        Assert.assertEquals(post.getId(), result.getPostsOriginal().get(0).getId());
        Assert.assertEquals(post.getLikes(), result.getPostsOriginal().get(0).getLikes());
        Assert.assertEquals(post.getUser(), result.getPostsOriginal().get(0).getUser());
        Assert.assertEquals(post.getQuestion(), result.getPostsOriginal().get(0).getQuestion());
        Assert.assertEquals(post.getAnswers().get(0).getId(), result.getPostsOriginal().get(0).getAnswers().get(0).getId());
        Assert.assertEquals(post.getCreationDate(), result.getPostsOriginal().get(0).getCreationDate());
        Assert.assertEquals(post.getDescription(), result.getPostsOriginal().get(0).getDescription());
        Assert.assertEquals(post.getTitle(), result.getPostsOriginal().get(0).getTitle());
        Assert.assertEquals(post.getPostType(), result.getPostsOriginal().get(0).getPostType());
    }

    @Test(expected = Exception.class)
    public void testFindByType_ThrowsException() throws Exception {
        // Setup

         // Run the test
        postServiceUnderTest.findByType(0, 1, null, PostTypeEnum.QUESTION,
                null,
                false,
                Sort.Direction.DESC, CREATIONDATE);
    }


}
