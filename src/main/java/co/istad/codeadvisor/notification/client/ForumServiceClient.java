package co.istad.codeadvisor.notification.client;

import co.istad.codeadvisor.notification.client.dto.ContentDTO;
import co.istad.codeadvisor.notification.client.dto.ForumDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// client/ForumServiceClient.java
@FeignClient(name = "forum-service")
public interface ForumServiceClient {

    @GetMapping("/api/v1/forums/{forumId}/author")
    String getForumAuthorId(@PathVariable String forumId);

    @GetMapping("/api/v1/forums/{forumId}")
    ForumDTO getForumById(@PathVariable String forumId);

    @GetMapping("/api/v1/comments/{commentId}/author")
    String getCommentAuthorId(@PathVariable String commentId);

}
