package com.example.springrest;

import com.example.springrest.entity.User;
import com.example.springrest.exceptions.UserNotFoundException;
import com.example.springrest.service.RepositoryStubService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class UserRestController {

    @Autowired
    private RepositoryStubService repositoryService;

    @GetMapping("users")
    public List<User> listAllUsers() {
        return repositoryService.findAll();
    }

    @GetMapping("users/{id}")
    public User getUserById(@PathVariable(name = "id", required = true) Long id) {
        User user = repositoryService.findUserById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    @PostMapping("users")
    public User createUser(@RequestBody User newUser) {
        return repositoryService.saveUser(newUser);
    }

    @PutMapping("users/{id}")
    public User updateUser(@PathVariable(name = "id", required = true) Long id,
                           @RequestBody User updatedUser) {
        User userToUpdate = repositoryService.findUserById(id);
        if (userToUpdate != null) {
            userToUpdate.setFirstName(updatedUser.getFirstName());
            userToUpdate.setSecondName(updatedUser.getSecondName());
            userToUpdate.setPosition(updatedUser.getPosition());
            userToUpdate.setDepartment(updatedUser.getDepartment());
            return repositoryService.saveUser(id, userToUpdate);
        } else {
            updatedUser.setId(id);
            return repositoryService.saveUser(id, updatedUser);
        }
    }

    @DeleteMapping("users/{id}")
    public void deleteUser(@PathVariable(name = "id", required = true) Long id) {
        repositoryService.deleteById(id);
    }


}
