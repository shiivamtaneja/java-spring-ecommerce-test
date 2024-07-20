package com.shivam.ecommerce.user_auth_service.jwt;

import com.shivam.ecommerce.user_auth_service.exceptions.ExpiredTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter for validating JWT tokens on incoming HTTP requests.
 * Extracts the token from the request, validates it, and sets the authentication in the security context.
 */
@Component
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    /**
     * Processes each HTTP request to validate the JWT token.
     *
     * @param request     The HTTP request.
     * @param response    The HTTP response.
     * @param filterChain The filter chain to pass the request and response to the next filter.
     * @throws ServletException If an error occurs during request processing.
     * @throws IOException      If an I/O error occurs during request processing.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        logger.info("AuthTokenFilter called for URI : {}", request.getRequestURI());

        String jwt = parseJwt(request);

        try {
            if (jwt != null && jwtUtils.isJwtValid(jwt)) {

                String username = jwtUtils.getUsernameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token: {}", e.getMessage());
            throw new ExpiredTokenException("JWT token expired");
        }

        filterChain.doFilter(request, response); // Continue with the next filter in the chain
    }

    /**
     * Extracts the JWT token from the HTTP request header.
     *
     * @param request The HTTP request.
     * @return The extracted JWT token, or null if no token is found.
     */
    private String parseJwt(HttpServletRequest request) {
        return jwtUtils.getJwtFromHeader(request);
    }
}
