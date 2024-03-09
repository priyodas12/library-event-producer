package io.learn.kafka.libraryeventproducer.model;

import lombok.Data;

@Data
public class Book {

  private Integer bookId;
  private String bookName;
  private String bookAuthor;

}
