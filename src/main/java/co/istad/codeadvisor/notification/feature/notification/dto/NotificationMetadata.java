package co.istad.codeadvisor.notification.feature.notification.dto;

// Helper record to store metadata
public record NotificationMetadata(
        String id,
        String slug,
        String title,
        String thumbnail,
        String ownerId,
        boolean isContent
) {}