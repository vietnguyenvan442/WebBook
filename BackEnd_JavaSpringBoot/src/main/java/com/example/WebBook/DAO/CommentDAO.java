package com.example.WebBook.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.WebBook.model.Book;
import com.example.WebBook.model.Comment;
import com.example.WebBook.model.User;

public class CommentDAO extends DAO{

	public CommentDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Comment> getCommentsByIDBook(int idb) {
		List<Comment> cmt = new ArrayList<>();
		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT user.id as idu, user.name as name, comment.id as idcmt, comment.content as cmt, comment.star as star "
						+ "FROM comment, user, book "
						+ "where user.id = comment.user_id and comment.book_id = ? "
						+ "group by comment.id order by comment.id desc")){
			ps.setInt(1, idb);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Comment c = new Comment();
				c.setId(rs.getInt("idcmt"));
				c.setUser(new User());
					c.getUser().setName(rs.getString("name"));
					c.getUser().setId(rs.getInt("idu"));
				c.setBook(new Book());
					c.getBook().setId(idb);
				c.setContent(rs.getString("cmt"));
				c.setStar(rs.getInt("star"));
				cmt.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cmt;
	}
	
	public void insertComment(Comment cmt) {
		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement("insert into comment(content, user_id, book_id, star) values(?, ?, ?, ?)")){
			ps.setString(1, cmt.getContent());
			ps.setInt(2, cmt.getUser().getId());
			ps.setInt(3, cmt.getBook().getId());
			ps.setInt(4, cmt.getStar());			
			int rs = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteComment(int idb) {
		try(Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement("DELETE FROM comment WHERE (book_id = ?);");){
			int result = 0;
			ps.setInt(1, idb);
			result = ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
