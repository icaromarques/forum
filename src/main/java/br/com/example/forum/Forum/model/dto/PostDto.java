package br.com.example.forum.Forum.model.dto;

import br.com.example.forum.Forum.model.enumeration.PostTypeEnum;

import java.time.LocalDateTime;

public class PostDto {

    private Integer id;
    private String title;
    private String description;
    private UserDto user;
    private LocalDateTime creationDate;
    private Integer questionId;
    private Integer answersCount;
    private PostTypeEnum postType;
    private Integer likesCount;
    private UserDto upVote;

    public PostDto() {
    }

    public PostDto(Integer id, String title, String description, UserDto user, LocalDateTime creationDate,
                   Integer questionId, Integer answersCount, PostTypeEnum postType, Integer likesCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.user = user;
        this.creationDate = creationDate;
        this.questionId = questionId;
        this.answersCount = answersCount;
        this.postType = postType;
        this.likesCount = likesCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getAnswersCount() {
        return answersCount;
    }

    public void setAnswersCount(Integer answersCount) {
        this.answersCount = answersCount;
    }

    public PostTypeEnum getPostType() {
        return postType;
    }

    public void setPostType(PostTypeEnum postType) {
        this.postType = postType;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public UserDto getUpVote() {
        return upVote;
    }

    public void setUpVote(UserDto upVote) {
        this.upVote = upVote;
    }
}
