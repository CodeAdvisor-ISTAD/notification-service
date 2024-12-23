package co.istad.codeadvisor.notification.config.kafka;//package co.istad.codeadvisor.notification.config.kafka;
//
//import co.istad.codeadvisor.notification.domain.Notification;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.listener.DefaultErrorHandler;
//import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.util.backoff.FixedBackOff;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Slf4j
//@EnableKafka
//@Configuration
//public class KafkaConsumerConfig {
//
//    @Bean
//    public ConsumerFactory<String, Notification> consumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "206.189.159.20:9092");
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//
//        // Create JsonDeserializer for Notification
//        JsonDeserializer<Notification> notificationJsonDeserializer = new JsonDeserializer<>(Notification.class);
//        notificationJsonDeserializer.setRemoveTypeHeaders(false);
//        notificationJsonDeserializer.addTrustedPackages("*");
//
//        // Wrap with ErrorHandlingDeserializer
//        ErrorHandlingDeserializer<Notification> errorHandlingDeserializer =
//                new ErrorHandlingDeserializer<>(notificationJsonDeserializer);
//
//        return new DefaultKafkaConsumerFactory<>(
//                props,
//                new ErrorHandlingDeserializer<>(new StringDeserializer()),
//                errorHandlingDeserializer
//        );
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Notification> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Notification> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//
//        // Configure error handling
//        factory.setCommonErrorHandler(new DefaultErrorHandler(
//                new FixedBackOff(1000L, 2) // Wait 1 second between retries, max 2 attempts
//        ));
//
//        return factory;
//    }
//
//
//
//

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

////    @Bean
////    public ConcurrentKafkaListenerContainerFactory<String, Notification> kafkaListenerContainerFactory() {
////        ConcurrentKafkaListenerContainerFactory<String, Notification> factory =
////                new ConcurrentKafkaListenerContainerFactory<>();
////        factory.setConsumerFactory(consumerFactory());
////        return factory;
////    }
////
////    @Bean
////    public DefaultKafkaConsumerFactory<String, Notification> consumerFactory() {
////        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
////    }
////
////    @Bean
////    public Map<String, Object> consumerConfigs() {
////        Map<String, Object> props = new HashMap<>();
////        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "206.189.159.20:9092");
////        props.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
////        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
////        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, org.springframework.kafka.support.serializer.JsonDeserializer.class);
////        props.put(org.springframework.kafka.support.serializer.JsonDeserializer.TRUSTED_PACKAGES, "*");
////        return props;
////    }
//
//}


@Configuration
public class KafkaConsumerConfig {
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "206.189.159.20:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(JsonDeserializer.TYPE_MAPPINGS, "notification:co.istad.codeadvisor.notification.domain.Notification");
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "co.istad.codeadvisor.notification.domain.Notification");

        return new DefaultKafkaConsumerFactory<>(config, new ErrorHandlingDeserializer<>(new StringDeserializer()), new ErrorHandlingDeserializer<>(new JsonDeserializer<>(new ObjectMapper())));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
