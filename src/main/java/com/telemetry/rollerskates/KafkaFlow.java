package com.telemetry.rollerskates;

import com.telemetry.rollerskates.entity.Humidity;
import com.telemetry.rollerskates.entity.Pressure;
import com.telemetry.rollerskates.entity.Speed;
import com.telemetry.rollerskates.entity.Temperature;
import com.telemetry.rollerskates.repository.Impl.HumidityRepository;
import com.telemetry.rollerskates.repository.Impl.PressureRepository;
import com.telemetry.rollerskates.repository.Impl.SpeedRepository;
import com.telemetry.rollerskates.repository.Impl.TemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.core.ConsumerFactory;

@Configuration
@EnableIntegration
public class KafkaFlow {
    private static final String TOPIC_FROM = "topic1";
    @Autowired
    private ConsumerFactory consumerFactory;
    @Autowired
    private KafkaHandler kafkaHandler;
    @Autowired
    private TemperatureRepository temperatureRepository;
    @Autowired
    private HumidityRepository humidityRepository;
    @Autowired
    private PressureRepository pressureRepository;
    @Autowired
    private SpeedRepository speedRepository;

    @Bean
    IntegrationFlow fromKafka() {
        return IntegrationFlows.from(Kafka.messageDrivenChannelAdapter(consumerFactory, TOPIC_FROM))
                .handle(kafkaHandler)
                .<Object, Class<?>>route(Object::getClass, m -> m
                        .channelMapping(Temperature.class, "Temperature")
                        .channelMapping(Speed.class, "Speed")
                        .channelMapping(Pressure.class, "Pressure")
                        .channelMapping(Humidity.class, "Humidity"))
                .get();
    }

    @Bean
    IntegrationFlow fromTemperature() {
        return IntegrationFlows.from("Temperature")
                .handle(m -> temperatureRepository.save((Temperature) m.getPayload()))
                .get();
    }

    @Bean
    IntegrationFlow fromHumidity() {
        return IntegrationFlows.from("Humidity")
                .handle(m -> humidityRepository.save((Humidity) m.getPayload()))
                .get();
    }

    @Bean
    IntegrationFlow fromPressure() {
        return IntegrationFlows.from("Pressure")
                .handle(m -> pressureRepository.save((Pressure) m.getPayload()))
                .get();
    }

    @Bean
    IntegrationFlow fromSpeed() {
        return IntegrationFlows.from("Speed")
                .handle(m -> speedRepository.save((Speed) m.getPayload()))
                .get();
    }
}
