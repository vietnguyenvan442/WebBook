package com.example.WebBook.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.WebBook.model.User;

public class UserDAO extends DAO{

	public UserDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User selectUser(User u) {
		User user = new User();
		boolean check = true;
		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement("select * from user where email = ? and password = ?")){
			ps.setString(1, u.getEmail());
			ps.setString(2, u.getPassword());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				check = false;
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setPosition(rs.getString("position"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(check) return null;
		return user;
	}
	
	public boolean checkEmail(User user) {
		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement("select * from user where email = ?");){
			ps.setString(1, user.getEmail());
			ResultSet rs = ps.executeQuery();
			if(rs.next() && rs.getInt(1) > 0) return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void insertUser(User user) {
		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement("insert into user(name, email, password, position) values(?, ?, ?, ?)")){
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, "user");
			int rs = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
