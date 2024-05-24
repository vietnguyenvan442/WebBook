package com.example.WebBook.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.WebBook.DAO.BookDAO;
import com.example.WebBook.DAO.CategoryDAO;
import com.example.WebBook.DAO.CommentDAO;
import com.example.WebBook.model.Book;
import com.example.WebBook.model.Category;

@RestController
@CrossOrigin
public class BookController {
	private BookDAO bDAO = new BookDAO();
	private CategoryDAO cDAO = new CategoryDAO();
	private CommentDAO cmtDAO = new CommentDAO();
	
	@GetMapping("/webBook/books")
	public List<Book> getBooks(){
		List<Book> bs = bDAO.selectBooks();
		return bs;
	}
	
	@GetMapping("/webBook/books/{category}")
	public List<Book> getBooksByCategory(@PathVariable String category){
		Category c = cDAO.selectCategoryByName(category);
		List<Book> bs = bDAO.selectBooksByCategory(c.getId());
		return bs;
	}	
	
	@GetMapping("/webBook/book/{id}")
	public Book getBook(@PathVariable int id) {
		Book b = bDAO.selectBookByID(id);
		return b;
	}
	
	@PostMapping("/webBook/admin/book/save/{id}")
	public ResponseEntity<String> addOrUpdateBook(@PathVariable int id,
			@RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("des") String des,
            @RequestParam("day") Date day,
            @RequestParam("page") int page,
            @RequestParam("category") int category,
            @RequestParam(value= "image", required = false) MultipartFile image) {
		Book book = new Book();
		book.setId(id);
		book.setTitle(title);
		book.setAuthor(author);
		book.setDes(des);
		book.setDay(day);
		book.setPage(page);
		book.setCategory(new Category()); book.getCategory().setId(category);
		
		if(id <= 0) {
			if(!bDAO.checkDuplicate(book)) {
			    //Lưu ảnh vào thư mục của server
				boolean isUpdateImage = image != null && !image.isEmpty();
				String newFileName="";
				String uploadDir = "C:/Users/vietn/OneDrive/Máy tính/Web/images";
				if(isUpdateImage) {
					String fileName = StringUtils.cleanPath(image.getOriginalFilename());
					newFileName = UUID.randomUUID().toString() + "_" + fileName;
				    Path targetLocation = Paths.get(uploadDir, newFileName);
				    try {
				        Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
				    } catch (IOException e) {
				        e.printStackTrace();
				        return ResponseEntity.badRequest().body("Failed image");
				    }
				}
				book.setImage(newFileName);
				
				//Thêm sách
			    bDAO.insertBook(book);
				return ResponseEntity.ok("Add Complete");
			}
			return ResponseEntity.ok("Fail");
		} 
		
		Book book_check = bDAO.selectBookByID(id);//Kiểm tra sự tồn tại của sách
		
		if(id > 0 && book_check!=null) {
			boolean isUpdateImage = image != null && !image.isEmpty();//Kiểm tra đầu vào có ảnh không
			String newFileName="";
			String uploadDir = "C:/Users/vietn/OneDrive/Máy tính/Web/images";
			if(isUpdateImage) {
			    //Tạo tên ảnh để lưu
				String fileName = StringUtils.cleanPath(image.getOriginalFilename());
				newFileName = UUID.randomUUID().toString() + "_" + fileName;
				book.setImage(newFileName);
			    Path targetLocation = Paths.get(uploadDir, newFileName);
			    try {
			    	//Lưu ảnh
			        Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			        
			        //Cập nhật Book khi có ảnh
			        bDAO.updateBook(book);
			        
					//Xóa ảnh khỏi thư mục của server
					String fileNamed = book_check.getImage();
					if(fileNamed != null) {
				        String filePath = uploadDir + File.separator + fileNamed;
				        File file = new File(filePath);
				        if(file.exists()) file.delete();	
				    }
			    } catch (IOException e) {
			        e.printStackTrace();
			        return ResponseEntity.badRequest().body("Failed image");
			    }
			}
			
			//Cập nhật Book khi không có ảnh
			else {
				bDAO.updateNotImage(book);
			}
			return ResponseEntity.ok("Edit Complete");
		}
		return ResponseEntity.notFound().build();
	}
	@GetMapping("/book/image/{imageName}")
	public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
	    String uploadDir = "C:/Users/vietn/OneDrive/Máy tính/Web/images";
	    Path imagePath = Paths.get(uploadDir).resolve(imageName);
	    Resource imageResource;
	    try {
	        imageResource = new UrlResource(imagePath.toUri());
	        if (imageResource.exists() && imageResource.isReadable()) {
	            return ResponseEntity.ok()
	                    .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
	                    .body(imageResource);
	        }
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    }
	    return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/webBook/admin/book/delete/{id}")
	public ResponseEntity<String> deletePatient(@PathVariable int id) {
	    Book book = bDAO.selectBookByID(id);
	    if (book != null) {
	    	cmtDAO.deleteComment(id);
	        bDAO.deleteBook(book);
	        return ResponseEntity.ok("Book deleted with ID: "+book.getId());
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
}
