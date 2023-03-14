package com.gtt.springboot.entities;




import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document
@Data
@NoArgsConstructor
public class Category {
	
	@Id
	private String id;
	private String name;
	@DBRef
	private Collection<Product> products = new ArrayList<>();
	
	public Category(String id, String name, Collection<Product> products) {
		super();
		this.id = id;
		this.name = name;
		this.products = products;
	}
	
	

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Collection<Product> getProducts() {
		return products;
	}



	public void setProducts(Collection<Product> products) {
		this.products = products;
	}



	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	} 	
	
	
	
	
	
	

}
