package co.istad.codeadvisor.notification.consumer;

import co.istad.codeadvisor.notification.domain.Notification;
import co.istad.codeadvisor.notification.domain.NotificationData;
import co.istad.codeadvisor.notification.domain.NotificationType;
import co.istad.codeadvisor.notification.dto.*;
import co.istad.codeadvisor.notification.repository.NotificationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final ObjectMapper objectMapper;
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;


    @KafkaListener(topics = {
            "${kafka.topic.comment-created}",
            "${kafka.topic.comment-replied}",
            "${kafka.topic.comment-reported}",
            "${kafka.topic.content-reacted}",
            "${kafka.topic.content-reported}",
            "${kafka.topic.content-created}",
            "${kafka.topic.question-created}",
            "${kafka.topic.question-voted}",
            "${kafka.topic.answer-created}",
            "${kafka.topic.answer-replied}",
            "${kafka.topic.answer-voted}",
            "${kafka.topic.answer-accepted}"
    }, groupId = "notification-group")
    public void handleMessage(@Payload String message,
                              @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            switch (topic) {
                case "comment-created-events-topic" -> {
                    CommentCreatedEvent event = objectMapper.readValue(message, CommentCreatedEvent.class);
                    handleCommentCreated(event);
                }
                case "comment-replied-events-topic" -> {
                    CommentRepliedEvent event = objectMapper.readValue(message, CommentRepliedEvent.class);
                    handleCommentReplied(event);
                }
                case "content-reacted-events-topic" -> {
                    ContentReactedEvent event = objectMapper.readValue(message, ContentReactedEvent.class);
                    handleContentReacted(event);
                }
                default -> log.warn("Unknown topic: {}", topic);
            }
        } catch (JsonProcessingException e) {
            log.error("Error processing message from topic {}: {}", topic, message, e);
            throw new RuntimeException("Message processing failed", e);
        }
    }

    private void handleCommentCreated(CommentCreatedEvent event) {
        Notification notification = buildNotification(
                event.getUserId(),
                event.getBody(),
                NotificationType.COMMENT,
                event.getContentId()
        );
        saveAndSendNotification(notification);
    }

    private void handleCommentReplied(CommentRepliedEvent event) {
        Notification notification = buildNotification(
                event.getUserId(),
                "Replied to your comment",
                NotificationType.REPLY,
                event.getContentId()
        );
        saveAndSendNotification(notification);
    }

    private void handleContentReacted(ContentReactedEvent event) {
        Notification notification = buildNotification(
                event.getUserId(),
                "Reacted with " + event.getReactionType(),
                NotificationType.LIKE,
                event.getContentId()
        );
        saveAndSendNotification(notification);
    }

    private Notification buildNotification(String senderId, String message,
                                           NotificationType type, String contentId) {
        NotificationData notificationData = new NotificationData();
        notificationData.setUuid(contentId);

        return Notification.builder()
                .senderId(senderId)
                .message(message)
                .notificationType(type)
                .notificationData(notificationData)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private void saveAndSendNotification(Notification notification) {
        notification = notificationRepository.save(notification);
        simpMessagingTemplate.convertAndSend(
                "/topic/notifications/" + notification.getReceiverId(),
                notification
        );
        log.info("Notification saved and sent: {}", notification);
    }
}