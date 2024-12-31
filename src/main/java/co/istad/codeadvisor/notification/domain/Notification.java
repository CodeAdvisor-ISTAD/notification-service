package co.istad.codeadvisor.notification.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;

    private String title;
    private String message;

    // Object for notification types as Content or Forum
    @Field("notification_Data")
    private NotificationData notificationData;

    // Enum for notification types LIKE, COMMENT, REPLY
    @Field("notification_type")
    private NotificationType notificationType;

    @Field("is_read")
    private boolean isRead;

    @Field("sender_id")
    private String senderId;

    @Field("receiver_id")
    private String receiverId;

    @Field("created_at")
    private LocalDateTime createdAt;

}

