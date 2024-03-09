package io.learn.kafka.libraryeventproducer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class LibraryEvent{
    UUID libraryEventId;
    LibraryEventType libraryEventType;
    @JsonProperty("book")
    Book book;
}
