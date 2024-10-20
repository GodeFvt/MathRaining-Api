package sit.project.mathrainingapi.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import sit.project.mathrainingapi.entities.CustomUserDetails;
import sit.project.mathrainingapi.exceptions.ErrorResponse;
import sit.project.mathrainingapi.exceptions.NotFoundException;
import sit.project.mathrainingapi.exceptions.UnauthorizedException;
import sit.project.mathrainingapi.services.JwtTokenUtil;
import sit.project.mathrainingapi.services.JwtUserDetailsService;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {

            final String requestTokenHeader = request.getHeader("Authorization");
            String username = null;
            String jwtToken = null;
            if (request.getRequestURI().equals("/login") || request.getRequestURI().equals("/token") || request.getRequestURI().equals("/signup")) {
                chain.doFilter(request, response);
                return;
            }

            if (requestTokenHeader != null) {
                if (requestTokenHeader.startsWith("Bearer ")) {
                    jwtToken = requestTokenHeader.substring(7);
                    try {
                        username = jwtTokenUtil.getSubjectFromToken(jwtToken, false);
                    } catch (IllegalArgumentException e) {
                        throw new UnauthorizedException("Unable to get JWT Token");
                    } catch (ExpiredJwtException e) {
                        throw new UnauthorizedException("JWT Token has expired");
                    } catch (JwtException e) {
                        throw new UnauthorizedException("JWT Token is invalid");
                    }
                } else {
                    throw new UnauthorizedException("JWT Token does not begin with Bearer String");
                }
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(jwtToken, (CustomUserDetails) userDetails, false)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            handleException(response, e, request.getRequestURI());
        }
    }

    protected void handleException(HttpServletResponse response, Exception e, String uri) throws IOException {
        HttpStatus status;
        String message;

        if (e instanceof UnauthorizedException) {
            status = HttpStatus.UNAUTHORIZED;
            message = e.getMessage();
        } else if (e instanceof NotFoundException) {
            status = HttpStatus.NOT_FOUND;
            message = e.getMessage();
        } else if (e instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
            message = e.getMessage();
        } else {
            status = HttpStatus.UNAUTHORIZED;
            message = e.getMessage();
        }

        response.setStatus(status.value());
        response.setContentType("application/json");
        ErrorResponse errorResponse = new ErrorResponse(status.value(), message, uri);
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }
}
