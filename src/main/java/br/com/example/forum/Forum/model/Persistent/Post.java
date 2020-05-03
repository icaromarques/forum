package br.com.example.forum.Forum.model.Persistent;

import br.com.example.forum.Forum.model.enumeration.PostTypeEnum;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@Component
public class Post extends Persistent {

    private String title;
    private String description;
    private User user;
    private LocalDateTime creationDate;
    private Post question;
    private List<Post> answers;
    private List<User> likes;
    private PostTypeEnum postType;

    public Post(){
        this.likes = new ArrayList<>();
    }

    public Post(Integer id, String title, String description, User user, LocalDateTime creationDate, Post question,
                List<Post> answers, PostTypeEnum postType,List<User> likes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.user = user;
        this.creationDate = creationDate;
        this.question = question;
        this.answers = answers;
        this.postType = postType;
        this.likes = likes;
    }

    public Post(Integer question) {
        this.id = question;
        this.user = new User();
    }

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "post_seq")
    @SequenceGenerator(name = "post_seq", sequenceName = "post_seq", allocationSize = 1)
    @Column(name="id")
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "title", nullable = false, length = 500)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "description", nullable = false, length = 4000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "creation_date", nullable = false)
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="id_post", nullable = true)
    public Post getQuestion() {
        return question;
    }

    public void setQuestion(Post question) {
        this.question = question;
    }

    @OneToMany(mappedBy="question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Post> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Post> answers) {
        this.answers = answers;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "post_type", nullable = false)
    public PostTypeEnum getPostType() {
        return postType;
    }

    public void setPostType(PostTypeEnum postType) {
        this.postType = postType;
    }

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable( name = "post_likes",
                joinColumns = { @JoinColumn(name = "id_post") },
                inverseJoinColumns = { @JoinColumn(name = "id_user") })
    public List<User> getLikes() {
        return likes;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }
}
