//package org.example.pfa.config;
//
//import lombok.RequiredArgsConstructor;
//import org.example.pfa.entity.Role;
//import org.example.pfa.entity.User;
//import org.example.pfa.repository.RoleRepo;
//import org.example.pfa.repository.UserRepo;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.Collections;
//
//@Component
//@RequiredArgsConstructor
//public class DataInitializer implements CommandLineRunner {
//
//    private final UserRepo userRepository;
//    private final RoleRepo roleRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) {
//        // Vérifie si l'admin existe déjà
//        if (userRepository.findByEmail("admin@flower.com") == null) {
//            System.out.println("🚀 Création du compte administrateur...");
//
//            // 🔹 Créer ou récupérer le rôle ADMIN
//            Role adminRole = roleRepository.findByName("ADMIN");
//            if (adminRole == null) {
//                adminRole = new Role();
//                adminRole.setName("ADMIN");
//                roleRepository.save(adminRole);
//            }
//
//            // 🔹 Créer l'utilisateur admin
//            User admin = new User();
//            admin.setUserName("Admin");
//            admin.setEmail("admin@gmail.com");
//            admin.setPassword(passwordEncoder.encode("123456"));
//            admin.setRoles(Collections.singletonList(adminRole));
//
//            // 🔹 Sauvegarder l’utilisateur
//            userRepository.save(admin);
//
//            System.out.println("✅ Admin créé avec succès !");
//            System.out.println("Email : admin@flower.com");
//            System.out.println("Mot de passe : admin123");
//        } else {
//            System.out.println("ℹ️ Admin déjà existant, aucune création nécessaire.");
//        }
//    }
//}
