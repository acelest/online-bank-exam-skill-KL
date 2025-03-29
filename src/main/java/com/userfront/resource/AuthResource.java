package com.userfront.resource;

import com.userfront.domain.User;
import com.userfront.domain.security.Role;
import com.userfront.domain.security.UserRole;
import com.userfront.service.UserService;
import com.userfront.security.JwtTokenProvider;
import com.userfront.dao.RoleDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@Api(value = "Authentication", description = "Authentication endpoints for the Online Banking System")
public class AuthResource {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RoleDao roleDao;

    @PostMapping("/register")
    @ApiOperation(value = "Register a new user")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            if (userService.checkUserExists(user.getUsername(), user.getEmail())) {
                return ResponseEntity.badRequest().body("Username or email already exists");
            }

            Set<UserRole> userRoles = new HashSet<>();
            Role role = roleDao.findByName("ROLE_USER");
            if (role == null) {
                role = new Role();
                role.setName("ROLE_USER");
                roleDao.save(role);
            }
            userRoles.add(new UserRole(user, role));

            User createdUser = userService.createUser(user, userRoles);
            
            if (createdUser != null) {
                String token = jwtTokenProvider.generateToken(createdUser.getUsername());
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("user", createdUser);
                return ResponseEntity.ok(response);
            }
            
            return ResponseEntity.badRequest().body("Error creating user");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    @ApiOperation(value = "Login user and return JWT token")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            String token = jwtTokenProvider.generateToken(loginRequest.getUsername());
            User user = userService.findByUsername(loginRequest.getUsername());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", user);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
} 