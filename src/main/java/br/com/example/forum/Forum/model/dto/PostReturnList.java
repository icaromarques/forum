package br.com.example.forum.Forum.model.dto;

import br.com.example.forum.Forum.model.Persistent.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class PostReturnList {
    private Integer totalPages;
    private Integer page;
    private List<PostDto> posts;
    @JsonIgnore
    private List<Post> postsOriginal;


    public PostReturnList() {
        posts = new ArrayList<>();
    }

    public PostReturnList(Integer totalPages, Integer page, List<Post> postsOriginal) {
        this.totalPages = totalPages;
        this.page = page;
        this.postsOriginal = postsOriginal;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<PostDto> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDto> posts) {
        this.posts = posts;
    }

    public List<Post> getPostsOriginal() {
        return postsOriginal;
    }

    public void setPostsOriginal(List<Post> postsOriginal) {
        this.postsOriginal = postsOriginal;
    }
}
