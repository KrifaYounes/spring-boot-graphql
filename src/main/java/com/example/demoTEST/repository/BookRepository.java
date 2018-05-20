package com.example.demoTEST.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demoTEST.model.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}
