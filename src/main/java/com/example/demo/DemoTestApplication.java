package com.example.demo;
import com.example.demo.exception.GraphQLErrorAdapter;
import com.example.demo.model.hibernate.Author;
import com.example.demo.model.hibernate.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class DemoTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoTestApplication.class, args);
	}

	@Bean
	public GraphQLErrorHandler errorHandler() {
		return new GraphQLErrorHandler() {
			@Override
			public List<GraphQLError> processErrors(List<GraphQLError> errors) {
				List<GraphQLError> clientErrors = errors.stream()
						.filter(this::isClientError)
						.collect(Collectors.toList());

				List<GraphQLError> serverErrors = errors.stream()
						.filter(e -> !isClientError(e))
						.map(GraphQLErrorAdapter::new)
						.collect(Collectors.toList());

				List<GraphQLError> e = new ArrayList<>();
				e.addAll(clientErrors);
				e.addAll(serverErrors);
				return e;
			}

			protected boolean isClientError(GraphQLError error) {
				return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
			}
		};
	}
	
	@Bean
	public CommandLineRunner demo(AuthorRepository authorRepository, BookRepository bookRepository) {
		return (args) -> {
			Author author = new Author("Jean", "Robert");
			Author author2 = new Author("Michel", "Bernard");
			Author author3 = new Author("Toto", "tata");

			authorRepository.save(author);
			authorRepository.save(author2);
			authorRepository.save(author3);

			bookRepository.save(new Book("GO LANGUAGE", "XXXX", 728, author));
			bookRepository.save(new Book("GRAPHQL LANGUAGE", "YYY", 728, author));
			bookRepository.save(new Book("JAVA LANGUAGE", "ZZZ", 728, author2));
			bookRepository.save(new Book("JAVASCRIPT", "ZZZ", 728, author3));

		};
	}
}

