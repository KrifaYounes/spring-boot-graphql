package com.example.demoTEST.resolver;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.demoTEST.exception.BookNotFoundException;
import com.example.demoTEST.model.Author;
import com.example.demoTEST.model.AuthorInput;
import com.example.demoTEST.model.Book;
import com.example.demoTEST.repository.AuthorRepository;
import com.example.demoTEST.repository.BookRepository;
import com.google.common.graph.Graph;

import graphql.execution.ExecutionId;
import graphql.execution.ExecutionTypeInfo;
import graphql.language.Field;
import graphql.language.FragmentDefinition;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingEnvironmentImpl;
import graphql.schema.DataFetchingFieldSelectionSet;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLType;
import graphql.servlet.GraphQLContext;
import graphql.servlet.GraphQLQueryProvider;

public class Mutation implements GraphQLMutationResolver {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Mutation(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Author newAuthor(AuthorInput authorInput) {
        Author author = new Author();
        author.setFirstName(authorInput.getFirstName());
        author.setLastName(authorInput.getLastName());

        authorRepository.save(author);

        return author;
    }
 
    public Author updateAuthor(Long authorId, AuthorInput authorInput, DataFetchingEnvironment env ) {
        Author authorToUpdate = authorRepository.findOne(authorId);

        Map<String, Object> arguments = env.getArguments();
        Map<String, Object> authorArgs = (Map<String, Object>) 
        		arguments.get("authorInput");

        if (authorArgs.containsKey("firstName")) {
            authorToUpdate.setFirstName(authorInput.getFirstName());
        }
        
        if (authorArgs.containsKey("lastName")) {
            authorToUpdate.setLastName(authorInput.getLastName());
        }
        
        authorRepository.save(authorToUpdate);
        return authorToUpdate;
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
    
    public Book updateBook(Book book) {
        Book bookToUpdate = bookRepository.findOne(book.getId());
        if(bookToUpdate == null) {
            throw new BookNotFoundException("The book to be updated was found", book.getId());
        }

        bookRepository.save(book);

        return book;
    }

	


}
