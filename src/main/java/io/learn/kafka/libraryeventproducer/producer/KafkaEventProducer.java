package io.learn.kafka.libraryeventproducer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.learn.kafka.libraryeventproducer.model.LibraryEvent;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaEventProducer {

  @Value("${spring.kafka.topic}")
  private String topicName;

  @Autowired
  KafkaTemplate<UUID, String> kafkaTemplate;

  @Autowired
  ObjectMapper objectMapper;

  public KafkaEventProducer(KafkaTemplate<UUID, String> kafkaTemplate,
      ObjectMapper objectMapper) {
    this.kafkaTemplate = kafkaTemplate;
    this.objectMapper = objectMapper;
  }

  public boolean publishEvent(LibraryEvent libraryEvent) {

    var key = libraryEvent.getLibraryEventId();
    String value = null;
    try {
      value = objectMapper.writeValueAsString(libraryEvent);
    } catch (JsonProcessingException e) {
      onException(key);
      return false;
    }

    CompletableFuture<SendResult<UUID, String>> result = kafkaTemplate.send(topicName, key, value);

    if (result.isCompletedExceptionally() || result.isCancelled()) {
      onException(key);
      return false;
    }
    onSuccess(key);
    return true;
  }

  private void onSuccess(UUID key) {
    log.info("Message Id: {} Published to Topic: {}",key,topicName);
  }

  private void onException(UUID key) {
    log.info("Error while publishing Message Id: {} to this Topic: {}",key,topicName);
  }

}
