package com.telemetry.rollerskates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
public class KafkaHandler extends MessageProducerSupport implements MessageHandler {

    private static final String ENTITY_PACKAGE = "com.telemetry.rollerskates.entity.";

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        String data = message.getPayload().toString();
        ArrayList<String> types = new ArrayList<>();
        types.add("Temperature");
        types.add("Humidity");
        types.add("Pressure");
        types.add("Speed");

        String classType = null;

        for (String type : types) {
            if (data.contains(type.toLowerCase())) {
                classType = type;
            }
        }

        try {
            Object object = new ObjectMapper().readValue(data, Class.forName(ENTITY_PACKAGE + classType));
            sendMessage(MessageBuilder.withPayload(object).build());

        } catch (JsonProcessingException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}