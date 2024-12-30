package co.istad.codeadvisor.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ContentReactedEvent {
    String contentId;
    String type;
    String userId;
    String reactionType;
}
