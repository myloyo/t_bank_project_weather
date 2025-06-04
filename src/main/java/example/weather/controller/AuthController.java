package example.weather.controller;

import example.weather.model.dto.JwtResponse;
import example.weather.model.dto.UserLoginRequest;
import example.weather.model.dto.UserRegisterRequest;
import example.weather.model.entity.User;
import example.weather.service.UserService;
import example.weather.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest request) {
        if (userService.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Пользователь с таким username уже существует");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        userService.registerUser(user);
        return ResponseEntity.ok("Пользователь успешно зарегистрирован");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        return userService.findByUsername(request.getUsername())
                .filter(user -> userService.checkPassword(request.getPassword(), user.getPassword()))
                .<ResponseEntity<?>>map(
                        user -> ResponseEntity.ok(new JwtResponse(jwtUtil.generateToken(user.getUsername()))))
                .orElseGet(() -> ResponseEntity.status(401).body("Неверный логин или пароль"));
    }
}