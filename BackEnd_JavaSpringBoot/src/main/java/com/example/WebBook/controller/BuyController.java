package com.example.WebBook.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.WebBook.model.Buy;
import com.example.WebBook.model.User;

@RestController
@CrossOrigin
public class BuyController {
	private List<Buy> buys = new ArrayList<>();
	private int idu;
	
	@PostMapping("/webBook/buy/{id}")
	public void setBuy(@RequestBody Buy buy, @PathVariable int id) {
		this.idu = id;
		buy.setDay(Date.valueOf(LocalDate.now()));
		buys.add(buy);
	}
	
	@GetMapping("/webBook/buys/{id}")
	public List<Buy> getBuys(@PathVariable int id){
		this.idu = id;
		List<Buy> buy = new ArrayList<>();
		for(Buy b: buys) {
			if(b.getUser().getId() == id) {
				buy.add(b);
			}
		}
		return buy;
	}
	
	@DeleteMapping("/webBook/buy/delete/{id}")
	public void deleteBuy(@PathVariable int id) {
		for(int i=0; i<buys.size(); i++) {
			if(buys.get(i).getBook().getId() == id) {
				buys.remove(i);
				break;
			}
		}
	}
}
