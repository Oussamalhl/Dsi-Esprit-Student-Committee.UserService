package dsi.esprit.tn.services;

import dsi.esprit.tn.Models.User;

import java.util.List;

public interface IuserServiceImpl {

    User addUser(User user);
    void deleteUser(long idUser);
    User updateUser(User user);
    List<User> showAllUsers();
    User showUser(Long idUser);
}
