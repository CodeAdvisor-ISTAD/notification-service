package co.istad.codeadvisor.notification.feature.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ContentReportedEvent {
    String contentId;
    String type;
    String userId;
}