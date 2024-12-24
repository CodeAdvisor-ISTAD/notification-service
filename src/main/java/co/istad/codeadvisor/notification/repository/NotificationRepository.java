package co.istad.codeadvisor.notification.repository;

import co.istad.codeadvisor.notification.domain.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> { ;

    List<Notification> findAllByReceiverId(String userId);
}
