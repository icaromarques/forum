package br.com.example.forum.Forum.repository;


import br.com.example.forum.Forum.model.Persistent.Post;
import br.com.example.forum.Forum.model.enumeration.PostTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Integer> {

    public Page<Post> findByPostType(PostTypeEnum postType, Pageable var1);
    public Page<Post> findByPostTypeAndAnswersIsEmpty(PostTypeEnum postType, Pageable var1);
    public Page<Post> findByPostTypeAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(PostTypeEnum postType,
                                                                                                  String title,
                                                                                                  String Description,
                                                                                                  Pageable var1);
    public Page<Post> findByPostTypeAndQuestion(PostTypeEnum postType, Post question, Pageable var1);

    @Query("SELECT p " +
            " FROM Post p " +
            "WHERE p.answers is empty " +
            "  and p.postType = ?1 " +
            "  and  ( lower(p.title)  like lower(CONCAT('%',?2,'%')) " +
            "   or  lower(p.description) like lower(CONCAT('%',?3,'%'))) " +
            " order by p.creationDate desc ")
    public Page<Post> findWithFilters(PostTypeEnum postType, String title, String Description, Pageable var1);

}
