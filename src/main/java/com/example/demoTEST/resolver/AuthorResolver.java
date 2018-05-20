package com.example.demoTEST.resolver;


import com.coxautodev.graphql.tools.GraphQLResolver;
import com.example.demoTEST.model.Author;
import com.example.demoTEST.model.Book;
import com.example.demoTEST.repository.BookRepository;

public class AuthorResolver implements GraphQLResolver<Author> {
    private BookRepository bookRepository;

    public AuthorResolver(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getBook(Author author) {
        return bookRepository.findOne(author.getId());
    }
}
