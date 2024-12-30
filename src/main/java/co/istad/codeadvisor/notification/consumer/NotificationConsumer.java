package co.istad.codeadvisor.notification.consumer;

import co.istad.codeadvisor.notification.domain.Notification;
import co.istad.codeadvisor.notification.domain.NotificationData;
import co.istad.codeadvisor.notification.domain.NotificationType;
import co.istad.codeadvisor.notification.dto.CommentCreatedEvent;
import co.istad.codeadvisor.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@KafkaListener(topics={
        "${comment-created-events-topic}",
        "${comment-replied-events-topic}",
        "${comment-reported-events-topic}",
        "${content-reacted-events-topic}",
        "${content-reported-events-topic}",
        "content-events",
        "comment-created-events-topic",
})
public class NotificationConsumer {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;


    @KafkaHandler
    public void handleEvent(@Payload CommentCreatedEvent event) {
        System.out.println("NOTIFICATION DATA: " + event);
    }

    @KafkaHandler
    public void handleEvent(@Payload Notification event) {
        System.out.println("NOTIFICATION DATA: " + event);
    }




//    @KafkaHandler
//    public void handleEvent(@Payload CommentCreatedEvent event) {
//
//        Notification notification = new Notification();
//        NotificationData notificationData = new NotificationData();
//        notificationData.setSlug(event.getContentId());
//
//        notification.setTitle("New Comment");
//        notification.setMessage(event.getBody());
//        notification.setSenderId(event.getUserId());
//        notification.setNotificationData(notificationData);
//        notification.setNotificationType(NotificationType.COMMENT);
//        notification.setRead(false);
//
//        // Save notification
//        notificationRepository.save(notification);
//
//        // Send the notification to WebSocket clients
//        messagingTemplate.convertAndSend("/topic/notifications/" + notification.getReceiverId(), notification);
//    }









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