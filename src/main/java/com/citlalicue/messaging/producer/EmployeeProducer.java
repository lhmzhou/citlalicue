package com.citlalicue.messaging.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.citlalicue.messaging.msg.EmployeeUpdateMsg;

@Component
public class EmployeeProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeProducer.class);

    @Autowired
    private KafkaTemplate<String, EmployeeUpdateMsg> employeeKafkaTemplate;

    @Value(value = "${employee.topic.name}")
    private String employeeTopicName;

    public void sendEmployeeMessage(EmployeeUpdateMsg employee) {
        LOGGER.debug("Sending Message...");
        employeeKafkaTemplate.send(employeeTopicName, employee);
    }

}