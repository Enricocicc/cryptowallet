package com.enrico.cryptowallet.controller;

import com.enrico.cryptowallet.model.User;
import com.enrico.cryptowallet.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    // build Create User REST API
    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.CREATED);

    }

    // build GetAll Users REST API
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // build Get User by Id
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        return new ResponseEntity<User>(userService.getUserById(id), HttpStatus.OK);
    }

    // build Update User REST API
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") long id){
        return new ResponseEntity<User>(userService.updateUser(user, id), HttpStatus.OK);
    }

    // build Delete User REST API
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return new ResponseEntity<String>("User deleted successfully!", HttpStatus.OK);
    }

    @GetMapping("/bitcoin")
    public ResponseEntity<Object> getBitcoinValue() {
        final String uri = "https://api.coincap.io/v2/rates/bitcoin";
        RestTemplate restTemplate = new RestTemplate();
        return new ResponseEntity<Object>(restTemplate.getForObject(uri, Object.class), HttpStatus.OK);
    }

    @GetMapping("/custom-login")
    public String customLogin(@RequestParam String email,
                              @RequestParam String password) {
        String loginResult;
        if (userService.isUserAuthorized(email, password)) {
            loginResult = "Login successful";
        } else {
            loginResult = "Login failed";
        }
        return loginResult;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
