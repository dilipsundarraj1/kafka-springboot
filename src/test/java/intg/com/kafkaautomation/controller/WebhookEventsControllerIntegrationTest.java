package com.kafkaautomation.controller;

import com.kafkaautomation.domain.Webhook;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

//import com.kafkaautomation.domain.WebhookEvent;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = {"logpoint-alert1"}, partitions = 3)
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.admin.properties.bootstrap-servers=${spring.embedded.kafka.brokers}"})
public class WebhookEventsControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    private Consumer<Integer, String> consumer;

    @BeforeEach
    void setUp() {
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.consumerProps("group1", "true", embeddedKafkaBroker));
        consumer = new DefaultKafkaConsumerFactory<>(configs, new IntegerDeserializer(), new StringDeserializer()).createConsumer();
        embeddedKafkaBroker.consumeFromAllEmbeddedTopics(consumer);
    }

    @AfterEach
    void tearDown() {
        consumer.close();
    }

//    @Configuration
//    @EnableWebSecurity
//    public class AppWebSecurityConfigurer extends WebSecurityConfigurerAdapter {
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//                    .csrf().disable()
//                    .authorizeRequests()
//                    .anyRequest().permitAll();
//        }
//    }

    @Test
    @Timeout(5)
    void postWebhookEvent() throws InterruptedException {
        //given
        Webhook webhook = Webhook.builder()
                .name("Keti TEST ALERT")
                .riskLevel("low")
                .timestamp(123456)
                .build();

//        WebhookEvent webhookEvent = WebhookEvent.builder()
//                  .name("Keti TEST ALERT")
////                .riskLevel("low")
////                .timestamp("123456")
////                .build();
        HttpHeaders headers = new HttpHeaders();
      headers.set("API-Key", "y2PFRhWbUq7ywaTm");
        headers.set("content-type", MediaType.APPLICATION_JSON.toString());
        HttpEntity request = new HttpEntity(webhook, headers);

        //when
        ResponseEntity<Webhook> responseEntity = restTemplate.exchange("/logpoint-webhook/alert/notification", HttpMethod.POST, request, Webhook.class);

        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        /*ConsumerRecord<Integer, String> consumerRecord = KafkaTestUtils.getSingleRecord(consumer, "logpoint.alert");
//        Thread.sleep(3000);
//        String expectedRecord = "{\"webhookEventId\":null,\"webhookEventType\":\"NEW\",\"webhook\":{\"name\":\"TEST ALERT\",\"riskLevel\":\"low\",\"timestamp\":123456}}";
        String expectedRecord = "{\"name\":\"Keti TEST ALERT\",\"riskLevel\":\"low\",\"timestamp\":123456}";
        String value = consumerRecord.value();
        assertEquals(expectedRecord, value);*/
    }

}
