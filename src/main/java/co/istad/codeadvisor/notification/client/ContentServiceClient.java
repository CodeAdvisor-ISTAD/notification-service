package co.istad.codeadvisor.notification.client;

import co.istad.codeadvisor.notification.client.dto.ContentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "content-service")
public interface ContentServiceClient {

    @GetMapping("/api/v1/contents/{contentId}/author")
    String getContentAuthorId(@PathVariable String contentId);

    @GetMapping("/api/v1/contents/{contentId}")
    ContentDTO getContentById(@PathVariable String contentId);

    @GetMapping("/api/v1/comments/{commentId}/author")
    String getCommentAuthorId(@PathVariable String commentId);
}

