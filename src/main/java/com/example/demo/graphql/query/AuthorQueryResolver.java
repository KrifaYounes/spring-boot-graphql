package com.example.demo.graphql.query;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.demo.model.hibernate.Author;
import com.example.demo.repository.AuthorRepository;

import graphql.annotations.annotationTypes.GraphQLName;

@Component
@GraphQLName("Query")
public class AuthorQueryResolver implements GraphQLQueryResolver {
    private AuthorRepository authorRepository;

    public AuthorQueryResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    public Iterable<Author> findAllAuthors() {
        return authorRepository.findAll();
    }
    
}