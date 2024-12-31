package co.istad.codeadvisor.notification.domain;

import lombok.*;

@Getter
@Setter
@Builder
public class NotificationData {
    private String uuid;
    private String slug;
    private String title;
    private String thumbnail;
    private Boolean isContent;
}
