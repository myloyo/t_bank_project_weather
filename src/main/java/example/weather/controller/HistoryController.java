package example.weather.controller;

import example.weather.model.dto.HistoryResponse;
import example.weather.service.HistoryService;
import example.weather.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {
    private final HistoryService historyService;
    private final UserService userService;

    public HistoryController(HistoryService historyService, UserService userService) {
        this.historyService = historyService;
        this.userService = userService;
    }

    @GetMapping("/me")
    public List<HistoryResponse> getMyHistory(Authentication authentication) {
        String username = authentication.getName();
        Long userId = userService.findByUsername(username).map(u -> u.getId()).orElse(null);
        return userId != null ? historyService.getUserHistory(userId) : List.of();
    }

    @DeleteMapping("/me")
    public void clearMyHistory(Authentication authentication) {
        String username = authentication.getName();
        Long userId = userService.findByUsername(username).map(u -> u.getId()).orElse(null);
        if (userId != null) {
            historyService.clearUserHistory(userId);
        }
    }
}