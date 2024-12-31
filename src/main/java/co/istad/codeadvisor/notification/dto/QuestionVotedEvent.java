package co.istad.codeadvisor.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class QuestionVotedEvent {
    String userId;
    String type;
    String contentId;
    String body;
}
