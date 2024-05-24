package com.example.WebBook.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.WebBook.model.Book;
import com.example.WebBook.model.Category;

public class BookDAO extends DAO{

	public BookDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public List<Book> selectBooks(){
		List<Book> books = new ArrayList<>();
		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT book.id as id, book.title as title, book.author as author, book.day as day, "
						+ "book.page as page, book.sold as sold, book.des as des, book.image as image, category.id as idc, category.name as namec, category.des as dc "
						+ "FROM book, category "
						+ "where book.category_id = category.id "
						+ "group by id")){
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Book b = new Book();
				b.setId(rs.getInt("id"));
				b.setTitle(rs.getString("title"));
				b.setAuthor(rs.getString("author"));
				b.setCategory(new Category());
					b.getCategory().setName(rs.getString("namec"));
					b.getCategory().setId(rs.getInt("idc"));
					b.getCategory().setDes(rs.getString("dc"));
				b.setDay(rs.getDate("day"));
				b.setPage(rs.getInt("page"));
				b.setSold(rs.getInt("sold"));
				b.setDes(rs.getString("des"));
				b.setImage(rs.getString("image"));
				books.add(b);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
	
	public List<Book> selectBooksByCategory(int idc){
		List<Book> books = new ArrayList<>();
		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT book.id as id, book.title as title, book.author as author, book.day as day, "
						+ "book.page as page, book.sold as sold, book.des as des, book.image as image, category.id as idc, category.name as namec, category.des as dc "
						+ "FROM book, category "
						+ "where book.category_id = category.id and category_id = ? "
						+ "group by id")){
			ps.setInt(1, idc);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Book b = new Book();
				b.setId(rs.getInt("id"));
				b.setTitle(rs.getString("title"));
				b.setAuthor(rs.getString("author"));
				b.setCategory(new Category());
					b.getCategory().setName(rs.getString("namec"));
					b.getCategory().setId(rs.getInt("idc"));
					b.getCategory().setDes(rs.getString("dc"));
				b.setDay(rs.getDate("day"));
				b.setPage(rs.getInt("page"));
				b.setSold(rs.getInt("sold"));
				b.setDes(rs.getString("des"));
				b.setImage(rs.getString("image"));
				books.add(b);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}

	public Book selectBookByID(int id) {
		Book b = new Book();
		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT book.id as id, book.title as title, book.author as author, book.day as day, "
						+ "book.page as page, book.sold as sold, book.des as des, book.image as image, category.id as idc, category.name as namec, category.des as dc "
						+ "FROM book, category "
						+ "where book.category_id = category.id and book.id = ? "
						+ "group by book.id");){
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				b.setId(id);
				b.setTitle(rs.getString("title"));
				b.setAuthor(rs.getString("author"));
				b.setDay(rs.getDate("day"));
				b.setPage(rs.getInt("page"));
				b.setSold(rs.getInt("sold"));
				b.setDes(rs.getString("des"));
				b.setImage(rs.getString("image"));
				b.setCategory(new Category());
					b.getCategory().setId(rs.getInt("idc"));
					b.getCategory().setName(rs.getString("namec"));
					b.getCategory().setDes(rs.getString("dc"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	public void insertBook(Book book) {
		try(Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement("INSERT INTO `book` (`title`, `author`, `category_id`, `day`, `page`, `sold`, `des`, `image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
				+ "");){
			int result = 0;
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setInt(3, book.getCategory().getId());
			ps.setDate(4, book.getDay());
			ps.setInt(5, book.getPage());
			ps.setInt(6, book.getSold());
			ps.setString(7, book.getDes() == null ? book.getDes(): null);
			ps.setString(8, book.getImage());
			result = ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateBook(Book book) {
		try(Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement("UPDATE `book` SET `title` = ?, `author` = ?, `category_id` = ?, `day` = ?, `page` = ?, `sold` = ?, `des` = ?, `image` = ? WHERE (`id` = ?)");){
			int result = 0;
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setInt(3, book.getCategory().getId());
			ps.setDate(4, book.getDay());
			ps.setInt(5, book.getPage());
			ps.setString(6, book.getDes() == null ? book.getDes(): null);
			ps.setString(7, book.getImage());
			ps.setInt(8, book.getId());
			result = ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateNotImage(Book book) {
		try(Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement("UPDATE `book` SET `title` = ?, `author` = ?, `category_id` = ?, `day` = ?, `page` = ?, `des` = ? WHERE (`id` = ?)");){
			int result = 0;
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setInt(3, book.getCategory().getId());
			ps.setDate(4, book.getDay());
			ps.setInt(5, book.getPage());
			ps.setString(6, book.getDes() == null ? book.getDes(): null);
			ps.setInt(7, book.getId());
			result = ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteBook(Book book) {
		try(Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement("DELETE FROM `book` WHERE (`id` = ?);");){
			int result = 0;
			ps.setInt(1, book.getId());
			result = ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkDuplicate(Book book) {
		try(Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement("select * from book where title = ? and author = ?");){
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ResultSet rs = ps.executeQuery();
			if(rs.next()&&rs.getInt(1)>0) return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
