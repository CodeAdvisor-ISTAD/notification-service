package co.istad.codeadvisor.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentReportedEvent {
    String contentId;
    String commentId;
    String type;
    String userId;

}
