package com.example.demoTEST.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.example.demoTEST.model.Author;
import com.example.demoTEST.model.Book;
import com.example.demoTEST.repository.AuthorRepository;

public class BookResolver implements GraphQLResolver<Book> {
    private AuthorRepository authorRepository;

    public BookResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author getAuthor(Book book) {
        return authorRepository.findOne(book.getAuthor().getId());
    }
}
