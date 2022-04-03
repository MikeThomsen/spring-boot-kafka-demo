package com.example.demokafkatest.services;

import com.example.demokafkatest.Person;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.ByteArrayInputStream;

public class AvroKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AvroKafkaConsumer.class);

    @KafkaListener(topics = "${demo.topic.string_test}", groupId = "stringConsumerGroup")
    public void receive(ConsumerRecord<String, byte[]> consumerRecord) {
        try {
            String key = consumerRecord.key();
            byte[] payload = consumerRecord.value();
            ReflectDatumReader reader = new ReflectDatumReader(Person.class);
            Decoder decoder = DecoderFactory.get().binaryDecoder(new ByteArrayInputStream(payload), null);
            Person readBack = (Person) reader.read(null, decoder);

            LOGGER.info("Got back: " + readBack);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
