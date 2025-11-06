package org.example.pfa.service;

import org.example.pfa.IService.IUserService;
import org.example.pfa.entity.Role;
import org.example.pfa.entity.User;
import org.example.pfa.repository.RoleRepo;
import org.example.pfa.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private RoleRepo roleRepository; // ✅ injection manquante corrigée

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        // ✅ Vérifier si un utilisateur existe déjà avec cet email
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("Email déjà utilisé !");
        }

        // ✅ Encoder le mot de passe
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // ✅ Récupérer ou créer le rôle USER
        Role roleUser = roleRepository.findByName("USER");
        if (roleUser == null) {
            roleUser = new Role();
            roleUser.setName("USER");
            roleRepository.save(roleUser);
        }

        // ✅ Assigner le rôle par défaut à l’utilisateur
        user.setRoles(Collections.singletonList(roleUser));

        // ✅ Sauvegarder l’utilisateur
        return userRepository.save(user);
    }

    @Override
    public User login(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null &&
                passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            return existingUser;
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User updateUser(Long id, User userDetails) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setUserName(userDetails.getUserName());
            existingUser.setEmail(userDetails.getEmail());
            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            }
            return userRepository.save(existingUser);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
