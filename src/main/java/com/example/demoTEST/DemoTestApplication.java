package com.example.demoTEST;
import com.example.demoTEST.exception.GraphQLErrorAdapter;
import com.example.demoTEST.model.Author;
import com.example.demoTEST.model.Book;
import com.example.demoTEST.repository.AuthorRepository;
import com.example.demoTEST.repository.BookRepository;
import com.example.demoTEST.resolver.AuthorResolver;
import com.example.demoTEST.resolver.BookResolver;
import com.example.demoTEST.resolver.Mutation;
import com.example.demoTEST.resolver.Query;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingEnvironmentBuilder;
import graphql.schema.DataFetchingEnvironmentImpl;
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
	public BookResolver authorResolver(AuthorRepository authorRepository) {
		return new BookResolver(authorRepository);
	}
	
	@Bean
	public AuthorResolver bookResolver(BookRepository bookRepository) {
		return new AuthorResolver(bookRepository);
	}

	@Bean
	public Query query(AuthorRepository authorRepository, BookRepository bookRepository) {
		return new Query(authorRepository, bookRepository);
	}

	
	@Bean
	public Mutation mutation(AuthorRepository authorRepository, BookRepository bookRepository) {
		return new Mutation(authorRepository, bookRepository);
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

