package io.learn.kafka.libraryeventproducer.controller;


import io.learn.kafka.libraryeventproducer.model.LibraryEvent;
import io.learn.kafka.libraryeventproducer.service.LibraryEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v2/kafka")
public class LibraryEventController {

  @Autowired
  LibraryEventService kafkaEventService;


  @PostMapping("/library-events")
  public ResponseEntity<LibraryEvent> getLibraryEvent(@RequestBody LibraryEvent libraryEvent) {

    log.info("libraryEvent : {}", libraryEvent);

    if (kafkaEventService.publishEvent(libraryEvent)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(libraryEvent);
    }
    return
        ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
  }

}
