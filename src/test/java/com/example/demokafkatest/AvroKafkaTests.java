package com.example.demokafkatest;

import com.example.demokafkatest.services.AvroKafkaConsumer;
import com.example.demokafkatest.services.AvroKafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
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
        List<Object> objectList = Arrays.asList(new Person("John", "Smith", 30),
                new Person("Jane", "Doe", 27),
                new Organization("Microsoft", "BUSINESS"));
        objectList.forEach(obj -> avroKafkaProducer.produce(obj));
        Thread.sleep(250);
        List<Object> received = avroKafkaConsumer.getPayload();
        assertEquals(objectList.size(), received.size());
        for (int index = 0; index < objectList.size(); index++) {
            assertEquals(objectList.get(index), received.get(index));
        }
    }
}
