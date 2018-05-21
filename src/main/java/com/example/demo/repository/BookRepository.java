package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.hibernate.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}
