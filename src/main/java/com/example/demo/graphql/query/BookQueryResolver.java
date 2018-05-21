package com.example.demo.graphql.query;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.demo.model.hibernate.Book;
import com.example.demo.repository.BookRepository;

import graphql.annotations.annotationTypes.GraphQLName;

@Component
@GraphQLName("Query")
public class BookQueryResolver implements GraphQLQueryResolver {
    private BookRepository bookRepository;

    public BookQueryResolver(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public long countBooks() {
        return bookRepository.count();
    }
    
}
