package org.example.pfa.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        System.out.println("🔹 JwtAuthenticationFilter exécuté pour : " + path);

        // ✅ Exclure les routes publiques
        if (path.equals("/api/users") || path.startsWith("/api/users/login")) {
            System.out.println("⏩ Route publique, filtrage ignoré");
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("⚠️ Aucun token JWT trouvé dans l’en-tête Authorization");
            filterChain.doFilter(request, response);
            return;
        }

        // Ici tu pourras plus tard valider ton JWT
        SecurityContextHolder.getContext().setAuthentication(null);

        filterChain.doFilter(request, response);
    }
}
