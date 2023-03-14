package com.gtt.springboot.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.gtt.springboot.entities.Category;

@RepositoryRestResource
public interface CategoryRepository extends MongoRepository<Category, String> {
	
}
