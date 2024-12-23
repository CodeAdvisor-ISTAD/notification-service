package co.istad.codeadvisor.notification.service.impl;


import co.istad.codeadvisor.notification.domain.Notification;
import co.istad.codeadvisor.notification.repository.NotificationRepository;
import co.istad.codeadvisor.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public List<Notification> getAllNotificationsByUserId(String userId) {

        // Get all notifications by userId
        return notificationRepository.findAllByReceiverId(userId);
    }

    @Override
    public void markNotificationAsRead(String notificationId, Boolean read) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(read);
        notificationRepository.save(notification);
    }


    @Override
    public void removeNotification(String notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}
