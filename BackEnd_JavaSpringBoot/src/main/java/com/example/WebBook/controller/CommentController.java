package com.example.WebBook.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.WebBook.DAO.CommentDAO;
import com.example.WebBook.model.Comment;

@RestController
@CrossOrigin
public class CommentController {
	private CommentDAO cmtDAO = new CommentDAO();
	
	@GetMapping("/webBook/cmt/{idb}")
	public List<Comment> getCmts(@PathVariable int idb){
		List<Comment> cmt = cmtDAO.getCommentsByIDBook(idb);
		return cmt;
	}
	
	@PostMapping("/webBook/cmt/save")
	public void setComment(@RequestBody Comment cmt) {
		cmtDAO.insertComment(cmt);
	}
}
