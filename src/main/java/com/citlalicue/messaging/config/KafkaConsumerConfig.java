package com.citlalicue.messaging.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerconsumerProperties.AckMode;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.citlalicue.messaging.msg.EmployeeUpdateMsg;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    // pass bootstrap server details so that Consumers can connect to Kafka cluster
    public ConsumerFactory<String, String> consumerFactory(String groupId) {
        Map<String, Object> consumerProperties = new HashMap<>();

        // if (bootstrapAddress == null || bootstrapAddress.isEmpty()) {
        //     throw new IllegalArgumentException("Bootstrap servers cannot be null.");
        // }

        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress); // broker host:port (e.g: localhost:9092)
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId); // group of consumers where messages are delivered

        // use String Deserializer for reading keys and messages from  topic
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(consumerProperties);
    }

    public ConsumerFactory<String, EmployeeUpdateMsg> employeeConsumerFactory() {
        Map<String, Object> consumerProperties = new HashMap<>();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);

        // send a group name "employee" for consumer. If the Consumer group has more than one consumer, 
        // then they can read messages in parallel from the topic
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "employee");

        // set autocommit to false so same set of messages can be executed
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        
        return new DefaultKafkaConsumerFactory<>(consumerProperties, new StringDeserializer(),
            new JsonDeserializer<>(EmployeeUpdateMsg.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EmployeeUpdateMsg> employeeKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EmployeeUpdateMsg> factory =
            new ConcurrentKafkaListenerContainerFactory<>();

        // configure producer to wait for acknowledgments
        factory.setConsumerFactory(employeeConsumerFactory());
        factory.getContainerconsumerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE); // consumer immediately processes the commit
        factory.getContainerconsumerProperties().setAckOnError(true);
        factory.getContainerconsumerProperties().setSyncCommits(true);

        return factory;
    }

}