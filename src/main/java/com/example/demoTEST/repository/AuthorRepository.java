package com.example.demoTEST.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demoTEST.model.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
