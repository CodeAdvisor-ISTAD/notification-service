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
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
//@KafkaListener(topics={
////        "${comment-created-events-topic}",
////        "${comment-replied-events-topic}",
////        "${comment-reported-events-topic}",
////        "${content-reacted-events-topic}",
////        "${content-reported-events-topic}",
////        "content-events",
//        "comment-created-events-topic",
//})
public class NotificationConsumer {

        private final ObjectMapper objectMapper;

        @KafkaListener(topics = "comment-created-events-topic", groupId = "notification-group")
        public void consumeCommentCreated(@Payload String message) {
            try {
                CommentCreatedEvent event = objectMapper.readValue(message, CommentCreatedEvent.class);
                handleCommentCreated(event);
            } catch (JsonProcessingException e) {
                log.error("Error processing CommentCreatedEvent: {}", message, e);
                throw new RuntimeException("Message processing failed", e);
            }
        }

        @KafkaListener(topics = "comment-replied-events-topic", groupId = "notification-group")
        public void consumeCommentReplied(String message) {
            try {
                CommentRepliedEvent event = objectMapper.readValue(message, CommentRepliedEvent.class);
                handleCommentReplied(event);
            } catch (JsonProcessingException e) {
                log.error("Error deserializing CommentRepliedEvent", e);
            }
        }

        @KafkaListener(topics = "comment-reported")
        public void consumeCommentReported(String message) {
            try {
                CommentReportedEvent event = objectMapper.readValue(message, CommentReportedEvent.class);
                handleCommentReported(event);
            } catch (JsonProcessingException e) {
                log.error("Error deserializing CommentReportedEvent", e);
            }
        }

        @KafkaListener(topics = "content-reacted")
        public void consumeContentReacted(String message) {
            try {
                ContentReactedEvent event = objectMapper.readValue(message, ContentReactedEvent.class);
                handleContentReacted(event);
            } catch (JsonProcessingException e) {
                log.error("Error deserializing ContentReactedEvent", e);
            }
        }

        @KafkaListener(topics = "content-reported")
        public void consumeContentReported(String message) {
            try {
                ContentReportedEvent event = objectMapper.readValue(message, ContentReportedEvent.class);
                handleContentReported(event);
            } catch (JsonProcessingException e) {
                log.error("Error deserializing ContentReportedEvent", e);
            }
        }

        private void handleCommentCreated(CommentCreatedEvent event) {
            log.info("Received CommentCreatedEvent: {}", event);
            // Add your business logic here
        }

        private void handleCommentReplied(CommentRepliedEvent event) {
            log.info("Received CommentRepliedEvent: {}", event);
            // Add your business logic here
        }

        private void handleCommentReported(CommentReportedEvent event) {
            log.info("Received CommentReportedEvent: {}", event);
            // Add your business logic here
        }

        private void handleContentReacted(ContentReactedEvent event) {
            log.info("Received ContentReactedEvent: {}", event);
            // Add your business logic here
        }

        private void handleContentReported(ContentReportedEvent event) {
            log.info("Received ContentReportedEvent: {}", event);
            // Add your business logic here
        }
    }