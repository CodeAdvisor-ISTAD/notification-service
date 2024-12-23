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





//// create this class for test consume event from content service
//
//package co.istad.codeadvisor.notification.domain;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//@NoArgsConstructor
//public class Notification {
//    private String id;
//    private String title;
//    private String message;
//
//    // Represents of the Content or Forum
//    private String notificationData;
//
//    // Enum for notification types LIKE, COMMENT, REPLIES
//    private String notificationType;
//    private boolean isRead;
//    private String senderId;
//    private String receiverId;
//    private LocalDateTime createdAt;
//
//}


