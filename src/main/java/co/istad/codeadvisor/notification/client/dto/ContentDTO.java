package co.istad.codeadvisor.notification.client.dto;

import lombok.Data;

@Data
public class ContentDTO {
    private String id;
    private String slug;
    private String title;
    private String thumbnail;
    private String ownerId;
}
