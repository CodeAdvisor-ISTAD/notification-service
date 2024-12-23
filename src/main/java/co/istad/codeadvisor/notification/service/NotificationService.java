package co.istad.codeadvisor.notification.service;

import co.istad.codeadvisor.notification.domain.Notification;
import org.apache.kafka.streams.kstream.KStream;

import java.util.List;
import java.util.function.Function;

public interface NotificationService {

    List<Notification> getAllNotificationsByUserId(String userId);

    void markNotificationAsRead(String notificationId, Boolean read);

    void removeNotification(String notificationId);

}
