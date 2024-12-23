package co.istad.codeadvisor.notification.controller;

import co.istad.codeadvisor.notification.domain.Notification;
import co.istad.codeadvisor.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    public List<Notification> getAllNotificationsByUserId(@PathVariable String userId) {

        // Get all notifications by userId
        return notificationService.getAllNotificationsByUserId(userId);
    }

    @PutMapping("/{notificationId}/status")
    public void updateNotificationStatus(@PathVariable String notificationId, @RequestParam Boolean read) {
        notificationService.markNotificationAsRead(notificationId, read);
    }

    @DeleteMapping("/{notificationId}")
    public void removeNotification(@PathVariable String notificationId) {
        notificationService.removeNotification(notificationId);
    }
}
