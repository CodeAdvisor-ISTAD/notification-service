package co.istad.codeadvisor.notification.exception;

public class NotificationProcessingException extends RuntimeException {
    public NotificationProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
