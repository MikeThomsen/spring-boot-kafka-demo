package com.example.demokafkatest;

import com.example.demokafkatest.services.StringKafkaConsumer;
import com.example.demokafkatest.services.StringKafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class StringKafkaTests {
	@Autowired
	private StringKafkaConsumer stringKafkaConsumer;

	@Autowired
	private StringKafkaProducer stringKafkaProducer;

	@Value("${demo.topic.string_test}")
	private String topic;

	@Test
	void testProduceAndConsume() throws Exception {
		stringKafkaProducer.send(topic, "Sending with own simple KafkaProducer");
		stringKafkaConsumer.getLatch().await(10000, TimeUnit.MILLISECONDS);

		assertEquals(stringKafkaConsumer.getLatch().getCount(), 0L);
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\nPayload: " + stringKafkaConsumer.getPayload());
		assertTrue(stringKafkaConsumer.getPayload().contains("KafkaProducer"));
	}

}
