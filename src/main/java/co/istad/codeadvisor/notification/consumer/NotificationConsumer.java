package co.istad.codeadvisor.notification.consumer;

import co.istad.codeadvisor.notification.domain.CommentCreatedEvent;
import co.istad.codeadvisor.notification.domain.Notification;
import co.istad.codeadvisor.notification.domain.NotificationData;
import co.istad.codeadvisor.notification.domain.NotificationType;
import co.istad.codeadvisor.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics={
        "comment-created-events-topic",
})
public class NotificationConsumer {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;

    @KafkaHandler
    public void consumeNotification(CommentCreatedEvent commentCreatedEvent) {


        log.info("CommentCreatedEvent: {}", commentCreatedEvent);

//        Notification notification = new Notification();
//
//        notification.setMessage(commentCreatedEvent.getBody());
//        notification.setSenderId(commentCreatedEvent.getUserId());
//        notification.setNotificationType(NotificationType.CREATED);
//        notification.setNotificationData(NotificationData.builder()
//                .slug(commentCreatedEvent.getContentId())
//                .build());
//        notification.setRead(false);
//        notification.setCreatedAt(LocalDateTime.now());
//
//
//        // Save notification
//        notificationRepository.save(notification);
//
//        System.out.println("Notification data: " + notification);

//        // Send the notification to WebSocket clients
//        messagingTemplate.convertAndSend("/topic/notifications/" + notification.getReceiverId(), notification);
    }




//    @KafkaListener(topics = {"content-events", "forum-events"}, groupId = "notification-group")
//    public void consumeNotification(@Payload Notification notification) {
//
//        notification.setRead(false);
//        notification.setCreatedAt(LocalDateTime.now());
//
//        // Save notification
//        notificationRepository.save(notification);
//
//        // Send the notification to WebSocket clients
//        messagingTemplate.convertAndSend("/topic/notifications/" + notification.getReceiverId(), notification);
//    }
}