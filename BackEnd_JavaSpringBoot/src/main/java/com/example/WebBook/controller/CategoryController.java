package com.example.WebBook.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.WebBook.DAO.CategoryDAO;
import com.example.WebBook.model.Category;

@RestController
@CrossOrigin
public class CategoryController {
	private CategoryDAO categoryDAO = new CategoryDAO();
	
	@GetMapping("/webBook/categorys")
	public List<Category> getCategorys(){
		List<Category> category = categoryDAO.selectCategorys();
		return category;
	}
}
