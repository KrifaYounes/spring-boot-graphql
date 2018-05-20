package com.example.demoTEST.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.demoTEST.model.Author;
import com.example.demoTEST.model.Book;
import com.example.demoTEST.repository.AuthorRepository;
import com.example.demoTEST.repository.BookRepository;

public class Query implements GraphQLQueryResolver {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Query(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Iterable<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public long countBooks() {
        return bookRepository.count();
    }
    public long countAuthors() {
        return authorRepository.count();
    }
}
