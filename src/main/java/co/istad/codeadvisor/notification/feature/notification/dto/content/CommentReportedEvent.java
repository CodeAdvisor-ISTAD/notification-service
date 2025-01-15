package co.istad.codeadvisor.notification.feature.notification.dto.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentReportedEvent {
    String contentId;
    String type;
    String userId;
    String ownerId;
    String slug;

}
