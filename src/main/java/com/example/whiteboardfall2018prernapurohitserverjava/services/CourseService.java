package com.example.whiteboardfall2018prernapurohitserverjava.services;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.whiteboardfall2018prernapurohitserverjava.models.Course;
import com.example.whiteboardfall2018prernapurohitserverjava.models.User;

@RestController
//@CrossOrigin(origins = "*")
@CrossOrigin(origins = "http://localhost:3000" , allowCredentials = "true" , allowedHeaders = "*")
public class CourseService {

	int userId;
	int courseId;
	
	@Autowired
	UserService userService;

	List<Course> courses = null;
	@GetMapping("/api/user/{userId}/course")
	public List<Course> findAllCourses(@PathVariable("userId") int userId,
			HttpSession session) {
		 User currentUser = (User)session.getAttribute("currentUser");
	    return currentUser.getCourses();
	   
	}

	@PostMapping("/api/user/{userId}/course")
	public List<Course> createCourse(
			@PathVariable("userId") int userId,
			@RequestBody Course course){
		User user = userService.findUserById(userId);
		user.getCourses().add(course);
		return user.getCourses();
	}
	
	@GetMapping("/api/user/{userId}/course/{cid}")
	public Course findCourseById(
			@PathVariable("userId") int userId,
			@PathVariable("cid") int courseId) {
		
		User user = userService.findUserById(userId);
		List<Course> courses = user.getCourses();
		for(Course course: courses) {
			if(course.getId() == courseId)
				return course;
		}
		return null;
	}
	
	@PutMapping("/api/user/{userId}/course/{cid}")
	public List<Course> updateCourse(
			@PathVariable("userId") int userId,
			@PathVariable("cid") int courseId,
			@RequestBody Course newCourse) {
		User user = userService.findUserById(userId);
		Course course = findCourseById(courseId, userId);
		List<Course> courses = user.getCourses();
		courses.remove(course);
		courses.add(newCourse);
		return courses;
		
	}
	
	@DeleteMapping("/api/user/{userId}/course/{cid}")
	public List<Course> deleteCourse(
			@PathVariable("userId") int userId,
			@PathVariable("cid") int courseId) {
		
		User user = userService.findUserById(userId);
		
		List<Course> courses = user.getCourses();
		Course courseToDel = null;
		for(Course course: courses) {
			if(course.getId() == courseId)
				courseToDel = course;
		}
		if(!courseToDel.equals(null)) {
			courses.remove(courseToDel);
		}
		else {
			System.out.println("Course not found");
		}
		return courses;
		
	}
}
