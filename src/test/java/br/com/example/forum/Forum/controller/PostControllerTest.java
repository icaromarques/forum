package br.com.example.forum.Forum.controller;

import br.com.example.forum.Forum.exception.ValidationException;
import br.com.example.forum.Forum.model.Persistent.Post;
import br.com.example.forum.Forum.model.Persistent.User;
import br.com.example.forum.Forum.model.dto.PostDto;
import br.com.example.forum.Forum.model.dto.PostReturnList;
import br.com.example.forum.Forum.model.dto.UserDto;
import br.com.example.forum.Forum.model.enumeration.PostTypeEnum;
import br.com.example.forum.Forum.service.PostService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PostControllerTest {

    private PostController postControllerUnderTest;

    @Mock
    private Post mockQuestion;
    private Post postUnderTest;
    private User userTest;

    @Before
    public void setUp() {

        userTest = new User("name");
        userTest.setId(0);

        postUnderTest = new Post(0, "title",
                "description",
                userTest,
                LocalDateTime.of(2017, 1, 1, 0, 0, 0),
                mockQuestion,
                null,
                PostTypeEnum.QUESTION,
                null);

        postControllerUnderTest = new PostController();
        postControllerUnderTest.service = mock(PostService.class);
        postControllerUnderTest.modelMapper = mock(ModelMapper.class);
    }

    @Test
    public void testFindQuestions() throws Exception {
        // Setup

        // Configure PostService.findByType(...).
        final PostReturnList postReturnList = new PostReturnList(0, 0, Arrays.asList(postUnderTest));
        when(postControllerUnderTest.service.findByType(any(),any(),any(),any(),any(),any(), any(), any())).thenReturn(postReturnList);

        // Configure ModelMapper.map(...).
        final PostDto postDto = new PostDto(0, "title", "description", new UserDto(0, "name",
                LocalDateTime.of(2017, 1, 1, 0, 0, 0),
                "avatarColor"), LocalDateTime.of(2017, 1, 1, 0, 0, 0),
                0, 0, PostTypeEnum.QUESTION, 0);

        when(postControllerUnderTest.modelMapper.map(any(Object.class), eq(PostDto.class))).thenReturn(postDto);

        // Run the test
        final ResponseEntity<PostReturnList> result = postControllerUnderTest.findQuestions(0, 0,
                "text", false, Sort.Direction.ASC, "sortField");

        // Verify the results
        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test(expected = Exception.class)
    public void testFindQuestions_PostServiceThrowsException() throws Exception {
        // Setup
        when(postControllerUnderTest.service.findByType(0, 0, 0, PostTypeEnum.QUESTION, "text", false, Sort.Direction.ASC, "sortField")).thenThrow(Exception.class);

        // Configure ModelMapper.map(...).
        final PostDto postDto = new PostDto(0, "title", "description", new UserDto(0, "name", LocalDateTime.of(2017, 1, 1, 0, 0, 0), "avatarColor"), LocalDateTime.of(2017, 1, 1, 0, 0, 0), 0, 0, PostTypeEnum.QUESTION, 0);
        when(postControllerUnderTest.modelMapper.map(any(Object.class), eq(PostDto.class))).thenReturn(postDto);

        // Run the test
        postControllerUnderTest.findQuestions(0, 0, "text", false, Sort.Direction.ASC, "sortField");
    }

    @Test
    public void testFindAllAnswers() throws Exception {
        // Setup

        // Configure PostService.findByType(...).
        final PostReturnList postReturnList = new PostReturnList(1, 0, Arrays.asList(postUnderTest));

        when(postControllerUnderTest.service.findByType(any(),
                any(), any(), any(),any(), any(),any(), any())).thenReturn(postReturnList);

        // Configure ModelMapper.map(...).
        final PostDto postDto = new PostDto(0, "title", "description", new UserDto(0, "name", LocalDateTime.of(2017, 1, 1, 0, 0, 0), "avatarColor"), LocalDateTime.of(2017, 1, 1, 0, 0, 0), 0, 0, PostTypeEnum.QUESTION, 0);
        when(postControllerUnderTest.modelMapper.map(any(Object.class), eq(PostDto.class))).thenReturn(postDto);

        // Run the test
        final ResponseEntity<PostReturnList> result = postControllerUnderTest.findAllAnswers(0, 0, 0);

        // Verify the results
        Assert.assertEquals(result.getStatusCode(),HttpStatus.OK);
    }

    @Test(expected = Exception.class)
    public void testFindAllAnswers_PostServiceThrowsException() throws Exception {
        // Setup
        when(postControllerUnderTest.service.findByType(0, 0, 0, PostTypeEnum.QUESTION, "text", false, Sort.Direction.ASC, "sortField")).thenThrow(Exception.class);

        // Configure ModelMapper.map(...).
        final PostDto postDto = new PostDto(0, "title", "description", new UserDto(0, "name", LocalDateTime.of(2017, 1, 1, 0, 0, 0), "avatarColor"), LocalDateTime.of(2017, 1, 1, 0, 0, 0), 0, 0, PostTypeEnum.QUESTION, 0);
        when(postControllerUnderTest.modelMapper.map(any(Object.class), eq(PostDto.class))).thenReturn(postDto);

        // Run the test
        postControllerUnderTest.findAllAnswers(0, 0, 0);
    }

    @Test
    public void testFindById() throws Exception {
        // Setup
        when(postControllerUnderTest.service.findById(0)).thenReturn(new Post(0));

        // Configure ModelMapper.map(...).
        final PostDto postDto = new PostDto(0, "title", "description", new UserDto(0, "name", LocalDateTime.of(2017, 1, 1, 0, 0, 0), "avatarColor"), LocalDateTime.of(2017, 1, 1, 0, 0, 0), 0, 0, PostTypeEnum.QUESTION, 0);
        when(postControllerUnderTest.modelMapper.map(any(Object.class), eq(PostDto.class))).thenReturn(postDto);

        // Run the test
        final ResponseEntity<PostDto> result = postControllerUnderTest.findById(0);

        // Verify the results
    }

    @Test(expected = Exception.class)
    public void testFindById_PostServiceThrowsValidationException() throws Exception {
        // Setup
        when(postControllerUnderTest.service.findById(0)).thenThrow(ValidationException.class);

        // Configure ModelMapper.map(...).
        final PostDto postDto = new PostDto(0, "title", "description", new UserDto(0, "name", LocalDateTime.of(2017, 1, 1, 0, 0, 0), "avatarColor"), LocalDateTime.of(2017, 1, 1, 0, 0, 0), 0, 0, PostTypeEnum.QUESTION, 0);
        when(postControllerUnderTest.modelMapper.map(any(Object.class), eq(PostDto.class))).thenReturn(postDto);

        // Run the test
        final ResponseEntity<PostDto> result = postControllerUnderTest.findById(0);

        // Verify the results
    }

    @Test
    public void testDeleteById() throws Exception {
        // Setup
        final ResponseEntity<Boolean> expectedResult = new ResponseEntity<>(true, HttpStatus.OK);

        // Run the test
        final ResponseEntity<Boolean> result = postControllerUnderTest.deleteById(0);

        // Verify the results
        assertEquals(expectedResult, result);
        verify(postControllerUnderTest.service).deleteById(0);
    }

    @Test(expected = Exception.class)
    public void testDeleteById_PostServiceThrowsValidationException() throws Exception {
        // Setup
        final ResponseEntity<Boolean> expectedResult = new ResponseEntity<>(false, HttpStatus.CONTINUE);
        doThrow(ValidationException.class).when(postControllerUnderTest.service).deleteById(0);

        // Run the test
        final ResponseEntity<Boolean> result = postControllerUnderTest.deleteById(0);

    }

    @Test
    public void testCreate() throws Exception {
        // Setup
        final PostDto postDto = new PostDto(0, "title", "description",
                new UserDto(0,
                        "name",
                        LocalDateTime.of(2017, 1, 1, 0, 0, 0),
                        "#fff"),
                LocalDateTime.of(2017, 1, 1, 0, 0, 0),
                null,
                0,
                PostTypeEnum.QUESTION,
                0);
        final ResponseEntity<PostDto> expectedResult = new ResponseEntity<>(postDto, HttpStatus.CREATED);
        when(postControllerUnderTest.modelMapper.map(any(PostDto.class), eq(Post.class))).thenReturn(postUnderTest);
        when(postControllerUnderTest.modelMapper.map(any(Post.class), eq(PostDto .class))).thenReturn(postDto);
        when(postControllerUnderTest.service.findById(any())).thenReturn(postUnderTest);
        when(postControllerUnderTest.service.save(any())).thenReturn(postUnderTest);

        // Run the test
        final ResponseEntity<PostDto> result = postControllerUnderTest.create(postDto);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test(expected = Exception.class)
    public void testCreate_PostServiceThrowsValidationException() throws Exception {
        // Setup
        final PostDto postDto = new PostDto(0, "title",
                "description",
                new UserDto(0,
                        "name",
                        LocalDateTime.of(2017, 1, 1, 0, 0, 0),
                        "#fff"),
                LocalDateTime.of(2017, 1, 1, 0, 0, 0),
                null,
                0,
                PostTypeEnum.QUESTION,
                0);
        when(postControllerUnderTest.modelMapper.map(any(Object.class), eq(Post.class))).thenReturn(new Post(0));
        when(postControllerUnderTest.service.findById(0)).thenThrow(ValidationException.class);

        // Run the test
        final ResponseEntity<PostDto> result = postControllerUnderTest.create(postDto);

        // Verify the results
    }

    @Test(expected = Exception.class)
    public void testCreate_PostServiceThrowsException() throws Exception {
        // Setup
        final PostDto postDto = new PostDto(0, "title", "description", new UserDto(0, "name", LocalDateTime.of(2017, 1, 1, 0, 0, 0), "avatarColor"), LocalDateTime.of(2017, 1, 1, 0, 0, 0), 0, 0, PostTypeEnum.QUESTION, 0);
        when(postControllerUnderTest.modelMapper.map(any(Object.class), eq(Post.class))).thenReturn(new Post(0));
        when(postControllerUnderTest.service.findById(0)).thenReturn(new Post(0));
        when(postControllerUnderTest.service.save(any(Post.class))).thenThrow(Exception.class);

        // Run the test
        postControllerUnderTest.create(postDto);
    }

    @Test
    public void testUpdate() throws Exception {
        // Setup

        final PostDto postDto = new PostDto(0, "title", "description",
                new UserDto(0,
                        "name",
                        LocalDateTime.of(2017, 1, 1, 0, 0, 0),
                        "#fff"),
                LocalDateTime.of(2017, 1, 1, 0, 0, 0),
                null,
                0,
                PostTypeEnum.QUESTION,
                0);
        final ResponseEntity<PostDto> expectedResult = new ResponseEntity<>(postDto, HttpStatus.OK);
        when(postControllerUnderTest.modelMapper.map(any(PostDto.class), eq(Post.class))).thenReturn(postUnderTest);
        when(postControllerUnderTest.modelMapper.map(any(Post.class), eq(PostDto .class))).thenReturn(postDto);
        when(postControllerUnderTest.service.findById(any())).thenReturn(postUnderTest);
        when(postControllerUnderTest.service.save(any())).thenReturn(postUnderTest);

        // Run the test
        final ResponseEntity<PostDto> result = postControllerUnderTest.update(postDto);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test(expected = Exception.class)
    public void testUpdate_PostServiceThrowsValidationException() throws Exception {
        // Setup
        final PostDto postDto = new PostDto(0, "title", "description", new UserDto(0, "name", LocalDateTime.of(2017, 1, 1, 0, 0, 0), "avatarColor"), LocalDateTime.of(2017, 1, 1, 0, 0, 0), 0, 0, PostTypeEnum.QUESTION, 0);
        when(postControllerUnderTest.modelMapper.map(any(Object.class), eq(Post.class))).thenReturn(new Post(0));
        when(postControllerUnderTest.service.findById(0)).thenThrow(ValidationException.class);

        // Run the test
        final ResponseEntity<PostDto> result = postControllerUnderTest.update(postDto);

        // Verify the results
    }

    @Test(expected = Exception.class)
    public void testUpdate_PostServiceThrowsException() throws Exception {
        // Setup
        final PostDto postDto = new PostDto(0, "title", "description", new UserDto(0, "name", LocalDateTime.of(2017, 1, 1, 0, 0, 0), "avatarColor"), LocalDateTime.of(2017, 1, 1, 0, 0, 0), 0, 0, PostTypeEnum.QUESTION, 0);
        when(postControllerUnderTest.modelMapper.map(any(Object.class), eq(Post.class))).thenReturn(new Post(0));
        when(postControllerUnderTest.service.findById(0)).thenReturn(new Post(0));
        when(postControllerUnderTest.service.save(any(Post.class))).thenThrow(Exception.class);

        // Run the test
        postControllerUnderTest.update(postDto);
    }
}
