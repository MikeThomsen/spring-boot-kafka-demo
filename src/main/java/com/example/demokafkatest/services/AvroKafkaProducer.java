package com.example.demokafkatest.services;

import com.example.demokafkatest.Organization;
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

    public void produce(Object object) {
        try {
            Class clz = object instanceof Person ? Person.class : Organization.class;
            ReflectDatumWriter writer = new ReflectDatumWriter(clz);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
            writer.write(object, encoder);
            encoder.flush();
            out.close();
            String key = String.format("%s.%s", clz.getSimpleName().toLowerCase(), UUID.randomUUID());
            kafkaTemplate.send(topic, key, out.toByteArray());
            LOGGER.debug(String.format("Sent %s with key %s to avro-messages", object, key));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
