package ru.geek.lesson4springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geek.lesson4springboot.persist.User;
import ru.geek.lesson4springboot.persist.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserRepr> findAll() {
        return userRepository.findAll().stream().map(UserRepr::new).collect(Collectors.toList());
    }

    @Override
    public UserRepr findById(long id) {
        User user = userRepository.findById(id);
        if(user != null) {
            return new UserRepr(user);
        }
        return null;
    }

    @Transactional
    @Override
    public void insert(UserRepr user) {
        userRepository.insert(new User(user));
    }
    @Transactional
    @Override
    public void update(UserRepr user) {
        userRepository.update(new User(user));
    }
    @Transactional
    @Override
    public void delete(long id) {
        userRepository.delete(id);
    }
}
