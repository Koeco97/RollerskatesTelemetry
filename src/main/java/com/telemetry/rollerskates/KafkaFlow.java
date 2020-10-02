package com.telemetry.rollerskates;

import com.telemetry.rollerskates.entity.BaseDetector;
import com.telemetry.rollerskates.entity.Humidity;
import com.telemetry.rollerskates.entity.Pressure;
import com.telemetry.rollerskates.entity.Speed;
import com.telemetry.rollerskates.entity.Temperature;
import com.telemetry.rollerskates.repository.Impl.HumidityRepository;
import com.telemetry.rollerskates.repository.Impl.MeasureRepository;
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
import org.springframework.stereotype.Repository;

@Configuration
@EnableIntegration
public class KafkaFlow {
    private static final String TOPIC_FROM = "topic1";
    @Autowired
    private ConsumerFactory consumerFactory;
    @Autowired
    private KafkaHandler kafkaHandler;
    @Autowired
    private MeasureRepository measureRepository;

    @Bean
    IntegrationFlow fromKafka() {
        return IntegrationFlows.from(Kafka.messageDrivenChannelAdapter(consumerFactory, TOPIC_FROM))
                .handle(kafkaHandler)
                .handle(m -> measureRepository.save((BaseDetector)m.getPayload()))
                .get();
    }
}
