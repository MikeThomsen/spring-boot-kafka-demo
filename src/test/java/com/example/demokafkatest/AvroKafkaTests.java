package com.example.demokafkatest;

import com.example.demokafkatest.services.AvroKafkaConsumer;
import com.example.demokafkatest.services.AvroKafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

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
        Person person = new Person("John", "Smith", 30);
        avroKafkaProducer.produce(person);
        Thread.sleep(2500);
        Person received = avroKafkaConsumer.getPayload();
        assertEquals(person.getFirstName(), received.getFirstName());
        assertEquals(person.getLastName(), received.getLastName());
        assertEquals(person.getAge(), person.getAge());
    }
}
