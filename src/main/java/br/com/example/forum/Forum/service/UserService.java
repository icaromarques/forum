package br.com.example.forum.Forum.service;

import br.com.example.forum.Forum.model.Persistent.User;
import br.com.example.forum.Forum.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository repository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public User save(User user)  {
        return repository.saveAndFlush(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public User findById(Integer id)  {
        Optional<User> user = repository.findById(id);
        return user.orElse(null);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public User findByNameIgnoreCase(String name)  {
        Optional<User> user = repository.findByNameIgnoreCase(name);
        return user.orElse(null);
    }

}
