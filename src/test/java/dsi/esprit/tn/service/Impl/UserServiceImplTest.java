package dsi.esprit.tn.service.Impl;

import dsi.esprit.tn.Models.User;
import dsi.esprit.tn.repository.UserRepository;
import dsi.esprit.tn.services.IuserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserServiceImplTest {
    public static List<User> userList;

    @Autowired
    IuserServiceImpl userservice;

    @MockBean
    UserRepository userRepository;


    @BeforeAll
    public static void setUp() {
        userList = Stream.of(
                new User(1L,"Oussama1","Oussama1","Lahouel1","oussama1@gmail.com",true,"Oussama1"),
                new User(2L,"Oussama2","Oussama2","Lahouel2","oussama2@gmail.com",true,"Oussama2"),
                new User(3L,"Oussama3","Oussama3","Lahouel3","oussama3@gmail.com",true,"Oussama3"),
                new User(4L,"Oussama4","Oussama4","Lahouel4","oussama4@gmail.com",true,"Oussama4")
        ).collect(Collectors.toList());
    }

    @Test
    void saveData() {
        when(userRepository.save(userList.get(2))).thenReturn(userList.get(2));

        User user = userservice.addUser(userList.get(2));

        verify(userRepository, times(1)).save(userList.get(2));
        assertEquals(user, userList.get(2));
        assertEquals(user.getUsername(), userList.get(2).getUsername());
        assertEquals(user.getId(), userList.get(2).getId());
    }

    @Test
    void getDataById() {
        when(userRepository.findById(userList.get(0).getId())).thenReturn(Optional.ofNullable(userList.get(0)));

        User user = userservice.showUser(userList.get(0).getId());

        verify(userRepository, times(1)).findById(userList.get(0).getId());
        assertEquals(user, userList.get(0));
        assertEquals(user.getEmail(), userList.get(0).getEmail());
        assertEquals(user.getSexe(), userList.get(0).getSexe());
    }

    @Test
    void getAllData() {
        when(userRepository.findAll()).thenReturn(userList);

        List<User> users = userservice.showAllUsers();

        verify(userRepository, times(1)).findAll();
        assertEquals(users.size(), userList.size());
        assertEquals(users.get(3), userList.get(3));
        assertEquals(users.get(0).getUsername(), userList.get(0).getUsername());
        assertEquals(users.get(3).getId(), userList.get(3).getId());
        assertEquals(users.get(2).getEmail(), userList.get(2).getEmail());
    }

    @Test
    void deleteEmployeeById() {
        userservice.deleteUser(userList.get(2).getId());
        verify(userRepository, times(1)).deleteById(userList.get(2).getId());
    }

//    @Test
//    void getDataByIdException() {
//        assertThrows(EmployeeNotFound.class, () -> employeeDao.getDataById(34534), "Employee Not Found !!!");
//        verify(employeeRepository, times(1)).findById(34534);
//    }
}

