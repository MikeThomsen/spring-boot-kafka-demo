package com.example.demokafkatest;

import com.example.demokafkatest.services.AvroKafkaConsumer;
import com.example.demokafkatest.services.AvroKafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class AvroKafkaTests {
    @Autowired
    private AvroKafkaConsumer avroKafkaConsumer;

    @Autowired
    private AvroKafkaProducer avroKafkaProducer;

    @Test
    public void test() throws Exception {
        avroKafkaProducer.produce(new Person("John", "Smith", 30));
        avroKafkaProducer.produce(new Person("Jane", "Doe", 27));
        avroKafkaProducer.produce(new Organization("Microsoft", "BUSINESS"));
        Thread.sleep(2500);
        List<Object> received = avroKafkaConsumer.getPayload();
    }
}
