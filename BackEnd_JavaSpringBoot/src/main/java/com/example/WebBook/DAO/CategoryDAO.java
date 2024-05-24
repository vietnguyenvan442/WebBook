package com.example.WebBook.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.WebBook.model.Category;

public class CategoryDAO extends DAO{

	public CategoryDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public List<Category> selectCategorys(){
		List<Category> category = new ArrayList<>();
		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement("select * from category");){
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Category c = new Category();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				c.setDes(rs.getString("des"));
				category.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return category;
	}
	
	public Category selectCategoryByName(String name) {
		Category c = new Category();
		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement("select * from category where name = ?")){
			ps.setString(1, name);
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				c.setId(rs.getInt("id"));
				c.setName(name);
				c.setDes(rs.getString("des"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}
}
