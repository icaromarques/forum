package br.com.example.forum.Forum.model.Persistent;

import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Random;


@Entity
@Table(name = "user")
@Component
public class User extends Persistent {


    private String name;
    private LocalDateTime creationDate;
    private String avatarColor;

    public User() {
    }

    public User(String name) {
        this.name = name;
        this.creationDate = LocalDateTime.now();
        this.avatarColor = generateRandomAvatarColor();
    }

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "creation_date", nullable = false)
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "avatar_color", nullable = true, length = 7)
    public String getAvatarColor() {
        return avatarColor;
    }

    public void setAvatarColor(String avatarColor) {
        this.avatarColor = avatarColor;
    }

    private String generateRandomAvatarColor() {
        Random obj = new Random();
        int rand_num = obj.nextInt(0xffffff + 1);
        return String.format("#%06x", rand_num);
    }
}
