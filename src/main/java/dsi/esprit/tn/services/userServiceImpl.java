package dsi.esprit.tn.services;

import dsi.esprit.tn.Models.User;
import dsi.esprit.tn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userServiceImpl implements IuserServiceImpl{
    @Autowired
    UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }
    @Override
    public List<User> showAllUsers() {
        return ( List<User>) userRepository.findAll();
    }

    @Override
    public User showUser(Long id) {
        return userRepository.findById(id).orElse(null);

    }
}
