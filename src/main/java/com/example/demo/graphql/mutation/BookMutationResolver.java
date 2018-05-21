package com.example.demo.graphql.mutation;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.model.hibernate.Author;
import com.example.demo.model.hibernate.Book;
import com.example.demo.repository.BookRepository;

import graphql.annotations.annotationTypes.GraphQLName;


@Component
@GraphQLName("Mutation")
public class BookMutationResolver implements GraphQLMutationResolver {
    private BookRepository bookRepository;

    public BookMutationResolver(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book newBook(String title, String isbn, Integer pageCount, Long authorId) {
        Book book = new Book();
        book.setAuthor(new Author(authorId));
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPageCount(pageCount != null ? pageCount : 0);
        bookRepository.save(book);
        return book;
    }

    public boolean deleteBook(Long id) {
        bookRepository.delete(id);
        return true;
    }

    public Book updateBookPageCount(Integer pageCount, Long id) {
        Book book = bookRepository.findOne(id);
        if(book == null) {
            throw new BookNotFoundException("The book to be updated was found", id);
        }
        book.setPageCount(pageCount);

        bookRepository.save(book);

        return book;
    }
    

}
