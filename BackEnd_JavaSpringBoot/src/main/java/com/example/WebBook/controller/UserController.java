package com.example.WebBook.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.WebBook.DAO.UserDAO;
import com.example.WebBook.model.User;

@RestController
@CrossOrigin
public class UserController {
	private UserDAO userDAO = new UserDAO();

	@PostMapping("/webBook/CheckLogin")
	public ResponseEntity<User> checkLogin(@RequestBody User user) {
		User u = userDAO.selectUser(user);
		if(u != null) return ResponseEntity.ok(u);
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/webBook/register")
	public ResponseEntity<User> Logup(@RequestBody User user){
		if(!userDAO.checkEmail(user)) {
			userDAO.insertUser(user);
			return ResponseEntity.ok(userDAO.selectUser(user));
		}
		return ResponseEntity.notFound().build();
	}
}
