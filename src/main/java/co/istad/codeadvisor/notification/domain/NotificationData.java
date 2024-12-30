package co.istad.codeadvisor.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationData {
    private String uuid;
    private String slug;
    private String title;
    private String thumbnail;
    private Boolean isContent;
}
