package co.istad.codeadvisor.notification.consumer;

import co.istad.codeadvisor.notification.domain.Notification;
import co.istad.codeadvisor.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "content-events", groupId = "notification-group")
    public void consumeNotification(@Payload Notification notification) {

        log.info("Processing notification: {}", notification);
        notification.setCreatedAt(LocalDateTime.now());

        // Save notification
        notificationRepository.save(notification);

        // Send the notification to WebSocket clients
        messagingTemplate.convertAndSend("/topic/notifications/" + notification.getReceiverId(), notification);
    }
}