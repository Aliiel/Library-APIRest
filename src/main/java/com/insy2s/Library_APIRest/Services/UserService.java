package com.insy2s.Library_APIRest.Services;

import com.insy2s.Library_APIRest.Exceptions.UserAlreadyExistsException;
import com.insy2s.Library_APIRest.Exceptions.UserNotFoundException;
import com.insy2s.Library_APIRest.Models.DTO.UserDTO;
import com.insy2s.Library_APIRest.Models.Entities.User;
import com.insy2s.Library_APIRest.Models.Mapper.UserMapper;
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


    // méthode pour récuperer tous les utilisateurs enregistrés en bdd
    public List<UserDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserDTO)
                .toList();
    }


    // méthode pour récupérer un utilisateur enregistré en bdd à partir de son id
    public UserDTO getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user
                .map(UserMapper::toUserDTO)
                .orElseThrow(() -> new UserNotFoundException("L'utilisateur avec l'id " + id + " n'existe pas"));
    }


    // méthode pour créer un nouvel utilisateur
    public void addUser(UserDTO userDTO) {
        try {
            User user = UserMapper.toUser(userDTO);

            // vérification de l'existence de l'utilisateur avec son nom et son email
            boolean userExists = userRepository.existsByNameAndEmail(user.getName(), user.getEmail());
            if (userExists) {
                throw new UserAlreadyExistsException("Un utilisateur avec ce nom et cette adresse e-mail existe déjà.");
            }
            userRepository.save(user);

            // gestion de l'exception levée suite à l'annotation unique pour le champ de l'adresse email
        } catch (DataIntegrityViolationException e) {

            throw new UserAlreadyExistsException("Un utilisateur avec cette adresse e-mail existe déjà.");
        }
    }


    // méthode pour modifier un utilisateur
    public UserDTO updateUser(Long id, UserDTO userDTO) {

        User userUpdated = UserMapper.toUser(userDTO);

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("L'utilisateur avec l'id " + id + " n'existe pas"));


        if (userUpdated.getName() != null) {
            existingUser.setName(userUpdated.getName());
        }

        if (userUpdated.getEmail() != null) {
            existingUser.setEmail(userUpdated.getEmail());
        }

        try {
            userRepository.save(existingUser);
            return UserMapper.toUserDTO(existingUser);

        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException("Un utilisateur avec cette adresse e-mail existe déjà.");
        }
    }


    // méthode pour supprimer un utilisateur
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
