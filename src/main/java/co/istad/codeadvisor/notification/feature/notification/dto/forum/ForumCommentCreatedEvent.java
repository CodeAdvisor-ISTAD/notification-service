package co.istad.codeadvisor.notification.feature.notification.dto.forum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForumCommentCreatedEvent {
    private String questionOwnerUuid;
    private String answerOwnerUuid;
    private String description;
    private String forumSlug;
}
