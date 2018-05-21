package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.hibernate.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
