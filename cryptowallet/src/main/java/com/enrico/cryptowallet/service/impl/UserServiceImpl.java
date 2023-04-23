package com.enrico.cryptowallet.service.impl;

import com.enrico.cryptowallet.exception.ResourceNotFoundException;
import com.enrico.cryptowallet.exception.UserAlreadyExistsException;
import com.enrico.cryptowallet.model.User;
import com.enrico.cryptowallet.model.Wallet;
import com.enrico.cryptowallet.repository.UserRepository;
import com.enrico.cryptowallet.service.UserService;
import nl.flotsam.xeger.Xeger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Value("${crypto.bitcoin.tag}")
    private String currencyBTC;


    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        if (this.isExistingUser(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        this.createUserWallet(user);
        this.encodePassword(user);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public User updateUser(User user, long id) {
        User userToUpdate = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        return userRepository.save(userToUpdate);
    }

    @Override
    public void deleteUser(long id) {
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(userToDelete);
    }

    public boolean isExistingUser(String email) {
        return (userRepository.findByEmail(email) != null);
    }
    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    private void createUserWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setCurrency(currencyBTC);
        wallet.setAddress(this.generateBTCAddress());
        wallet.setUser(user);
        user.setWallet(wallet);
    }

    private String generateBTCAddress() {
        String regex = "(bc1|[13])[A-HJ-NP-Za-km-z1-9]{25,34}";
        Xeger generator = new Xeger(regex);
        return generator.generate();
    }

    public boolean isUserAuthorized(String email, String password) {
        User userDb = userRepository.findByEmail(email);
        return (userDb != null) && (passwordEncoder.matches(password, userDb.getPassword()));
    }

}
