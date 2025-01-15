package co.istad.codeadvisor.notification.feature.notification.dto.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ContentCreatedEvent {
    String userId;
    String type;
    String contentId;
    String body;
    String ownerId;
    String slug;
}
