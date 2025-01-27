package com.insy2s.Library_APIRest.Services;

import com.insy2s.Library_APIRest.Exceptions.UserAlreadyExistsException;
import com.insy2s.Library_APIRest.Exceptions.UserNotFoundException;
import com.insy2s.Library_APIRest.Models.Entities.User;
import com.insy2s.Library_APIRest.Models.Repositories.IUserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException("L'utilisateur avec l'id " + id + " n'existe pas"));
    }


    public void addUser(User user) {
        try {
            boolean userExists = userRepository.existsByNameAndEmail(user.getName(), user.getEmail());
            if (userExists) {
                throw new UserAlreadyExistsException("Un utilisateur avec ce nom et cette adresse e-mail existe déjà.");
            }
            userRepository.save(user);

        } catch (DataIntegrityViolationException e) {

            throw new UserAlreadyExistsException("Un utilisateur avec cette adresse e-mail existe déjà.");
        }
    }


    public User updateUser(Long id, User userUpdated) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("L'utilisateur avec l'id " + id + " n'existe pas"));


        if (userUpdated.getName() != null) {
            existingUser.setName(userUpdated.getName());
        }

        if (userUpdated.getEmail() != null) {
            existingUser.setEmail(userUpdated.getEmail());
        }

        try {
            return userRepository.save(existingUser);

        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException("Un utilisateur avec cette adresse e-mail existe déjà.");
        }
    }


    public void deleteUser(Long id) {

        Optional<User> existingUser = userRepository.findById(id);
        existingUser.ifPresentOrElse(
                userRepository::delete,
                () -> {
                    throw new UserNotFoundException("L'utilisateur avec l'id " + id + " n'existe pas");
                }
        );
    }
}
