package co.istad.codeadvisor.notification.client.dto;

import lombok.Data;
import lombok.Value;

@Data
public class ForumDTO {
    private String id;
    private String slug;
    private String title;
    private String thumbnail;
    private String ownerId;
}
