package co.istad.codeadvisor.notification.feature.notification;

import co.istad.codeadvisor.notification.domain.Notification;

import java.util.List;

public interface NotificationService {

    /**
     * Retrieves all notifications for a specific user by their user ID.
     *
     * @param userId the ID of the user whose notifications are to be retrieved
     * @return a list of notifications for the specified user
     */
    List<Notification> getAllNotificationsByUserId(String userId);

    /**
     * Marks a notification as read or unread.
     *
     * @param notificationId the ID of the notification to be marked
     * @param read          the read status to be set
     */
    void markNotificationAsRead(String notificationId, Boolean read);

    /**
     * Marks all notifications for a specific user as read.
     *
     * @param userId the ID of the user whose notifications are to be marked as read
     */
    void markAllNotificationsAsRead(String userId);

    /**
     * Removes a notification from the system.
     *
     * @param notificationId the ID of the notification to be removed
     */
    void removeNotification(String notificationId);

}
