package com.telemetry.rollerskates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telemetry.rollerskates.entity.Humidity;
import com.telemetry.rollerskates.entity.Pressure;
import com.telemetry.rollerskates.entity.Speed;
import com.telemetry.rollerskates.entity.Temperature;
import lombok.SneakyThrows;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;


@Component
public class KafkaHandler extends MessageProducerSupport implements MessageHandler {


    @SneakyThrows
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        String data = message.getPayload().toString();
        if (data.contains("temperature")) {
            Temperature temperature = new ObjectMapper().readValue(data, Temperature.class);
            sendMessage(MessageBuilder.withPayload(temperature).build());
        } else if (data.contains("humidity")) {
            Humidity humidity = new ObjectMapper().readValue(data, Humidity.class);
            sendMessage(MessageBuilder.withPayload(humidity).build());
        } else if (data.contains("pressure")) {
            Pressure pressure = new ObjectMapper().readValue(data, Pressure.class);
            sendMessage(MessageBuilder.withPayload(pressure).build());
        } else if (data.contains("speed")) {
            Speed speed = new ObjectMapper().readValue(data, Speed.class);
            sendMessage(MessageBuilder.withPayload(speed).build());
        } else {
            throw new IllegalArgumentException();
        }
    }
}