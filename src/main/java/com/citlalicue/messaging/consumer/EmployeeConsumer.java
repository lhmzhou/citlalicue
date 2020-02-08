package com.citlalicue.messaging.consumer;

import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.citlalicue.messaging.msg.EmployeeUpdateMsg;
import com.citlalicue.messaging.service.RepoService;

@Component
public class EmployeeConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeConsumer.class);

    @Autowired
    RepoService repoService;

    private CountDownLatch employeeLatch = new CountDownLatch(1);


    public CountDownLatch getLatch() {
        return employeeLatch; // can be increased to send more messages per batch
    }

    @KafkaListener(topics = "${employee.topic.name}",
        containerFactory = "employeeKafkaListenerContainerFactory")
    public void employeeListener(EmployeeUpdateMsg employeeUpdate, Acknowledgment acknowledgment) {
        LOGGER.info("Message Consumed {}", employeeUpdate);
        try {
          repoService.applyMessageUpdate(employeeUpdate);
          this.employeeLatch.countDown();
        } catch (Exception e) {
          // publish this message to "unhandled messages"
          LOGGER.error("An error has occurred while applying Employee updates{}...", employeeUpdate,
              e.getMessage());
        }

        //  send ack so the producer knows that the message has actually made it to the partition on the broker
        acknowledgment.acknowledge();
    }

}