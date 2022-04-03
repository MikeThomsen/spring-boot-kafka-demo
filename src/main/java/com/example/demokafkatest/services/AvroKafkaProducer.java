package com.example.demokafkatest.services;

import com.example.demokafkatest.Person;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

@Component
public class AvroKafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(AvroKafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Value("${demo.topic.avro_test}")
    private String topic;

    public void produce(Person person) {
        try {
            ReflectDatumWriter writer = new ReflectDatumWriter(Person.class);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
            writer.write(person, encoder);
            encoder.flush();
            out.close();
            String key = String.format("person.%s", UUID.randomUUID());
            kafkaTemplate.send(topic, key, out.toByteArray());
            LOGGER.debug(String.format("Sent %s with key %s to avro-messages", person, key));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
