package co.istad.codeadvisor.notification.feature.notification.dto.forum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForumCreatedEvent {
    private String uuid;
    private String slug;
    private String authorUuid;
    private String title;
    private String description;
    private String introduction;
    private String expectedAnswers;
}
