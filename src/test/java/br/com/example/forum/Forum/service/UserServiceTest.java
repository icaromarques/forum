package br.com.example.forum.Forum.service;

import br.com.example.forum.Forum.model.Persistent.User;
import br.com.example.forum.Forum.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class UserServiceTest {

    private UserService userServiceUnderTest;

    @Before
    public void setUp() {
        userServiceUnderTest = new UserService();
        userServiceUnderTest.repository = mock(UserRepository.class);
    }

    @Test
    public void testFindById() {
        final User result = userServiceUnderTest.findById(0);
    }

    @Test
    public void testFindByNameIgnoreCase() {
        final User result = userServiceUnderTest.findByNameIgnoreCase("name");
    }

    @Test
    public void testSave() {
        final User user = new User("name");
        final User result = userServiceUnderTest.save(user);
    }
}
