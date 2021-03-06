package com.example.demo.graphql.mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.demo.graphql.input.AuthorInput;
import com.example.demo.graphql.input.BookInput;
import com.example.demo.model.hibernate.Author;
import com.example.demo.model.hibernate.Book;
import com.example.demo.repository.AuthorRepository;

import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;


@Component
@GraphQLName("Mutation")
public class AuthorMutationResolver implements GraphQLMutationResolver {
    private AuthorRepository authorRepository;

    public AuthorMutationResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author newAuthor(AuthorInput authorInput) {
        Author author = new Author();
        author.setFirstName(authorInput.getFirstName());
        author.setLastName(authorInput.getLastName());
        
        if (authorInput.getBooks() != null) {
	        List<BookInput> books = authorInput.getBooks();
	        List<Book> booksToSave = new ArrayList<>();
	        books.forEach(book -> 
	        	booksToSave.add(new Book(
	        			book.getTitle(), 
	        			book.getIsbn(), 
	        			book.getPageCount(), author )));
	     
	        author.setBooks(booksToSave);
        }
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
}
