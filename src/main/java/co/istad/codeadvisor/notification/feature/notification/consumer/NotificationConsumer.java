package co.istad.codeadvisor.notification.feature.notification.consumer;

import co.istad.codeadvisor.notification.domain.Notification;
import co.istad.codeadvisor.notification.domain.NotificationData;
import co.istad.codeadvisor.notification.domain.NotificationType;
import co.istad.codeadvisor.notification.feature.notification.NotificationRepository;
import co.istad.codeadvisor.notification.feature.notification.dto.content.*;
import co.istad.codeadvisor.notification.feature.notification.dto.forum.ForumAnswerCreatedEvent;
import co.istad.codeadvisor.notification.feature.notification.dto.forum.ForumCommentCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    /**
     * Listens to the Kafka topics and handles the incoming messages.
     *
     * @param message the incoming message
     * @param topic   the topic from which the message was received
     */
    @KafkaListener(topics = {

            // content
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
            "${kafka.topic.answer-accepted}",

            // forum
            "${kafka.topic.forum-answer-created}",
            "${kafka.topic.forum-reply-created}"

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
                case "content-reported-events-topic" -> {
                    ContentReportedEvent event = objectMapper.readValue(message, ContentReportedEvent.class);
                    handleContentReported(event);
                }
                case "question-created-events-topic" -> {
                    QuestionCreatedEvent event = objectMapper.readValue(message, QuestionCreatedEvent.class);
                    handleQuestionCreated(event);
                }
                case "question-voted-events-topic" -> {
                    QuestionVotedEvent event = objectMapper.readValue(message, QuestionVotedEvent.class);
                    handleQuestionVoted(event);
                }
                case "answer-created-events-topic" -> {
                    AnswerCreatedEvent event = objectMapper.readValue(message, AnswerCreatedEvent.class);
                    handleAnswerCreated(event);
                }
                case "answer-replied-events-topic" -> {
                    AnswerRepliedEvent event = objectMapper.readValue(message, AnswerRepliedEvent.class);
                    handleAnswerReplied(event);
                }
                case "answer-voted-events-topic" -> {
                    AnswerVotedEvent event = objectMapper.readValue(message, AnswerVotedEvent.class);
                    handleAnswerVoted(event);
                }
                case "answer-accepted-events-topic" -> {
                    AnswerAcceptedEvent event = objectMapper.readValue(message, AnswerAcceptedEvent.class);
                    handleAnswerAccepted(event);
                }
                case "forum-answer-created-events-topic" -> {
                    ForumCommentCreatedEvent event = objectMapper.readValue(message, ForumCommentCreatedEvent.class);
                    handleForumCommentCreated(event);
                }
                case "forum-reply-created-events-topic" -> {
                    ForumAnswerCreatedEvent event = objectMapper.readValue(message, ForumAnswerCreatedEvent.class);
                    handleForumAnswerCreated(event);
                }

                default -> log.warn("Unknown topic: {}", topic);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Message processing failed", e);
        }
    }


    /**
     * Handles the event when a comment is created.
     *
     * @param event the event containing the details of the created comment
     */
    private void handleCommentCreated(CommentCreatedEvent event) {
        Notification notification = buildNotification(
                event.getUserId(),
                "Commented on your content",
                NotificationType.COMMENT,
                event.getContentId(),
                event.getOwnerId(),
                event.getSlug()
        );
        NotificationData notificationData = new NotificationData();
        notificationData.setSlug(event.getSlug());
        notificationData.setIsContent(true);
        notificationData.setTitle(event.getBody());
        notification.setNotificationData(notificationData);
        saveAndSendNotification(notification);


        System.out.println("Notification sent: " + notification);
    }

    /**
     * Handles the event when a comment is replied to.
     *
     * @param event the event containing the details of the reply
     */
    private void handleCommentReplied(CommentRepliedEvent event) {
        Notification notification = buildNotification(
                event.getUserId(),
                "Replied to your comment",
                NotificationType.REPLY,
                event.getContentId(),
                event.getOwnerId(),
                event.getSlug()
        );
        NotificationData notificationData = new NotificationData();
        notificationData.setIsContent(true);
        notificationData.setSlug(event.getSlug());
        notificationData.setTitle(event.getBody());
        notification.setNotificationData(notificationData);
        saveAndSendNotification(notification);
    }


    /**
     * Handles the event when a content is reacted to.
     *
     * @param event the event containing the details of the reaction
     */
    private void handleContentReacted(ContentReactedEvent event) {
        Notification notification = buildNotification(
                event.getUserId(),
                "Reacted with " + event.getReactionType(),
                NotificationType.LIKE,
                event.getContentId(),
                event.getOwnerId(),
                event.getSlug()
        );
        NotificationData notificationData = new NotificationData();
        notificationData.setIsContent(true);
        notificationData.setSlug(event.getSlug());
        notification.setNotificationData(notificationData);
        saveAndSendNotification(notification);
    }


    /**
     * Handles the event when a content is reported.
     *
     * @param event the event containing the details of the report
     */
    private void handleContentReported(ContentReportedEvent event) {
        Notification notification = buildNotification(
                event.getUserId(),
                "Reported your content",
                NotificationType.REPORT,
                event.getContentId(),
                event.getOwnerId(),
                event.getSlug()
        );
        NotificationData notificationData = new NotificationData();
        notificationData.setIsContent(true);
        notificationData.setSlug(event.getSlug());
        notificationData.setTitle(event.getBody());
        notification.setNotificationData(notificationData);
        saveAndSendNotification(notification);
    }

    /**
     * Handles the event when a question is created.
     *
     * @param event the event containing the details of the created question
     */
    private void handleQuestionCreated(QuestionCreatedEvent event) {
        Notification notification = buildNotification(
                event.getUserId(),
                "Created a new question",
                NotificationType.QUESTION,
                event.getContentId(),
                event.getOwnerId(),
                event.getSlug()
        );
        NotificationData notificationData = new NotificationData();
        notificationData.setIsContent(true);
        notificationData.setSlug(event.getSlug());
        notificationData.setTitle(event.getBody());
        notification.setNotificationData(notificationData);
        saveAndSendNotification(notification);
    }

    /**
     * Handles the event when a question is voted on.
     *
     * @param event the event containing the details of the vote
     */
    private void handleQuestionVoted(QuestionVotedEvent event) {
        Notification notification = buildNotification(
                event.getUserId(),
                "Voted on your question",
                NotificationType.VOTE,
                event.getContentId(),
                event.getOwnerId(),
                event.getSlug()
        );
        NotificationData notificationData = new NotificationData();
        notificationData.setIsContent(true);
        notificationData.setSlug(event.getSlug());
        notificationData.setTitle(event.getBody());
        notification.setNotificationData(notificationData);
        saveAndSendNotification(notification);
    }

    /**
     * Handles the event when an answer is created.
     *
     * @param event the event containing the details of the created answer
     */
    private void handleAnswerCreated(AnswerCreatedEvent event) {
        Notification notification = buildNotification(
                event.getUserId(),
                "Created a new answer",
                NotificationType.ANSWER,
                event.getContentId(),
                event.getOwnerId(),
                event.getSlug()
        );
        NotificationData notificationData = new NotificationData();
        notificationData.setIsContent(true);
        notificationData.setSlug(event.getSlug());
        notificationData.setTitle(event.getBody());
        notification.setNotificationData(notificationData);
        saveAndSendNotification(notification);
    }

    /**
     * Handles the event when an answer is replied to.
     *
     * @param event the event containing the details of the reply
     */
    private void handleAnswerReplied(AnswerRepliedEvent event) {
        Notification notification = buildNotification(
                event.getUserId(),
                "Replied to your answer",
                NotificationType.REPLY,
                event.getContentId(),
                event.getOwnerId(),
                event.getSlug()
        );
        NotificationData notificationData = new NotificationData();
        notificationData.setIsContent(true);
        notificationData.setSlug(event.getSlug());
        notificationData.setTitle(event.getBody());
        notification.setNotificationData(notificationData);
        saveAndSendNotification(notification);
    }

    /**
     * Handles the event when an answer is voted on.
     *
     * @param event the event containing the details of the vote
     */
    private void handleAnswerVoted(AnswerVotedEvent event) {
        Notification notification = buildNotification(
                event.getUserId(),
                "Voted on your answer",
                NotificationType.VOTE,
                event.getContentId(),
                event.getOwnerId(),
                event.getSlug()
        );
        NotificationData notificationData = new NotificationData();
        notificationData.setIsContent(true);
        notificationData.setSlug(event.getSlug());
        notificationData.setTitle(event.getBody());
        notification.setNotificationData(notificationData);
        saveAndSendNotification(notification);
    }

    /**
     * Handles the event when an answer is accepted.
     *
     * @param event the event containing the details of the accepted answer
     */ 
    private void handleAnswerAccepted(AnswerAcceptedEvent event) {
        Notification notification = buildNotification(
                event.getUserId(),
                "Accepted your answer",
                NotificationType.ACCEPT,
                event.getContentId(),
                event.getOwnerId(),
                event.getSlug()
        );
        NotificationData notificationData = new NotificationData();
        notificationData.setIsContent(true);
        notificationData.setSlug(event.getSlug());
        notificationData.setTitle(event.getBody());
        notification.setNotificationData(notificationData);
        saveAndSendNotification(notification);
    }


//    Forum events
    public void handleForumCommentCreated(ForumCommentCreatedEvent event) {
        Notification notification = buildNotification(

                event.getAnswerOwnerUuid(),
                "Commented on your forum",
                NotificationType.COMMENT,
                event.getQuestionOwnerUuid(),
                event.getQuestionOwnerUuid(),
                event.getForumSlug()
        );
        NotificationData notificationData = new NotificationData();
        notificationData.setIsContent(false);
        notificationData.setSlug(event.getForumSlug());
        notification.setNotificationData(notificationData);
        saveAndSendNotification(notification);
    }

    public void handleForumAnswerCreated(ForumAnswerCreatedEvent event) {
        Notification notification = buildNotification(

                event.getQuestionOwnerUuid(),
                "Answered your forum",
                NotificationType.ANSWER,
                event.getAnswerOwnerUuid(),
                event.getAnswerOwnerUuid(),
                event.getForumSlug()
        );
        NotificationData notificationData = new NotificationData();
        notificationData.setIsContent(false);
        notificationData.setSlug(event.getForumSlug());
        notification.setNotificationData(notificationData);
        saveAndSendNotification(notification);
    }


    /**
     * Builds a notification based on the event details.
     *
     * @param senderId  the ID of the sender
     * @param message   the message to be displayed
     * @param type      the type of the notification
     * @param contentId the ID of the content
     * @return the built notification
     */
    private Notification buildNotification(String senderId, String message, NotificationType type, String contentId, String ownerId, String slug) {
        NotificationData notificationData = new NotificationData();
        notificationData.setUuid(contentId);
        notificationData.setSlug(slug);

        return Notification.builder()
                .receiverId(ownerId)
                .senderId(senderId)
                .message(message)
                .notificationType(type)
                .notificationData(notificationData)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();
    }

    /**
     * Saves the notification to the repository and sends it to the receiver.
     *
     * @param notification the notification to be saved and sent
     */
    private void saveAndSendNotification(Notification notification) {

        notification = notificationRepository.save(notification);
        simpMessagingTemplate.convertAndSend(
                "/topic/notifications/" + notification.getReceiverId(),
                notification
        );
    }

}
