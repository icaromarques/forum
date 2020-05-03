package br.com.example.forum.Forum.model.dto;

import java.time.LocalDateTime;

public class UserDto {

    private Integer id;
    private String name;
    private LocalDateTime creationDate;
    private String avatarColor;

    public UserDto() {
    }

    public UserDto(Integer id, String name, LocalDateTime creationDate, String avatarColor) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.avatarColor = avatarColor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getAvatarColor() {
        return avatarColor;
    }

    public void setAvatarColor(String avatarColor) {
        this.avatarColor = avatarColor;
    }
}
