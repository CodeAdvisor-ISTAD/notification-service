package co.istad.codeadvisor.notification.feature.notification.dto.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class CommentCreatedEvent {
    String userId;
    String type;
    String body;
    String contentId;
    String ownerId;
    String slug;
}
