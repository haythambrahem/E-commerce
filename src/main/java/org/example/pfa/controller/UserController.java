package org.example.pfa.controller;

import org.example.pfa.IService.IUserService;
import org.example.pfa.dto.response.AuthResponse;
import org.example.pfa.entity.User;
import org.example.pfa.security.jwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
  @Autowired
  private IUserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User newUser = userService.createUser(user);
            return ResponseEntity.ok(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT) // 409
                    .body(e.getMessage()); // "Email déjà utilisé !"
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        User loggedInUser = userService.login(user);

        if (loggedInUser != null) {
            String token = jwtUtil.generateToken(loggedInUser.getEmail());

            // ✅ Récupérer le rôle principal (ex: ADMIN ou USER)
            String roleName = loggedInUser.getRoles().isEmpty()
                    ? null
                    : loggedInUser.getRoles().get(0).getName();

            // ✅ Retourner la réponse complète
            AuthResponse response = new AuthResponse(
                    token,
                    loggedInUser.getEmail(),
                    loggedInUser.getUserName(),
                    roleName
            );

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou mot de passe incorrect");
        }
    }


    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }





}
