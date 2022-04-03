package com.example.demokafkatest.services;

import com.example.demokafkatest.Organization;
import com.example.demokafkatest.Person;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class AvroKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AvroKafkaConsumer.class);

    private List<Object> received;

    public AvroKafkaConsumer() {
        received = new ArrayList<>();
    }

    @KafkaListener(topics = "${demo.topic.avro_test}", groupId = "avroConsumerGroup")
    public void receive(ConsumerRecord<String, byte[]> consumerRecord) {
        try {
            String key = consumerRecord.key();
            byte[] payload = consumerRecord.value();
            LOGGER.info(String.format("Got back key %s with payload of %d bytes", key, (payload != null ? payload.length : -1)));
            Class clz = key.startsWith("person") ? Person.class : Organization.class;
            ReflectDatumReader reader = new ReflectDatumReader(clz);
            Decoder decoder = DecoderFactory.get().binaryDecoder(new ByteArrayInputStream(payload), null);

            received.add(reader.read(null, decoder));

            LOGGER.info("Got back: " + received.get(received.size() - 1));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Object> getPayload() {
        return received;
    }
}
