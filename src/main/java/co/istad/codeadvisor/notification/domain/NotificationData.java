package co.istad.codeadvisor.notification.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationData {
    private String uuid;
    private String slug;
    private String title;
    private String thumbnail;
    private Boolean isContent;
}
