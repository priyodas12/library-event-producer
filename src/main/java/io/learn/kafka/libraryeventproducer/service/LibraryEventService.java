package io.learn.kafka.libraryeventproducer.service;

import io.learn.kafka.libraryeventproducer.model.LibraryEvent;
import io.learn.kafka.libraryeventproducer.producer.KafkaEventProducer;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LibraryEventService {


  @Autowired
  KafkaEventProducer kafkaEventProducer;

  public boolean publishEvent(LibraryEvent libraryEvent) {
    if (libraryEvent.getLibraryEventId() == null) {
      libraryEvent.setLibraryEventId(UUID.randomUUID());
      log.info("Publishing EventId: {}",libraryEvent.getLibraryEventId());
    }
    boolean result = kafkaEventProducer.publishEvent(libraryEvent);
    return result;
  }

}
