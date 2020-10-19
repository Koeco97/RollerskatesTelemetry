package com.telemetry.rollerskates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class KafkaHandler extends MessageProducerSupport implements MessageHandler {
    Logger logger = LoggerFactory.getLogger(KafkaHandler.class);

    private static final String ENTITY_PACKAGE = "com.telemetry.rollerskates.entity.";
    private static final List<String> TYPES = List.of("Temperature", "Humidity", "Pressure", "Speed");

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        String data = message.getPayload().toString();
        String classType = null;

        for (String type : TYPES) {
            if (data.contains(type.toLowerCase())) {
                classType = type;
                logger.info(type + " is detected");
            }
        }

        if (classType == null) {
            logger.error("Message contains undefined type");
            throw new IllegalArgumentException();
        }

        try {
            Object object = new ObjectMapper().readValue(data, Class.forName(ENTITY_PACKAGE + classType));
            logger.info(object.getClass().getSimpleName() + " is deserialized");
            sendMessage(MessageBuilder.withPayload(object).build());

        } catch (JsonProcessingException | ClassNotFoundException e) {
            logger.error("failed to deserialize");
            e.printStackTrace();
        }
    }
}