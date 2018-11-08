package com.example.whiteboardfall2018prernapurohitserverjava.services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.whiteboardfall2018prernapurohitserverjava.models.Course;
import com.example.whiteboardfall2018prernapurohitserverjava.models.Lesson;
import com.example.whiteboardfall2018prernapurohitserverjava.models.Module;
import com.example.whiteboardfall2018prernapurohitserverjava.models.Topic;
import com.example.whiteboardfall2018prernapurohitserverjava.models.User;

@RestController
//@CrossOrigin(origins="*")
@CrossOrigin(origins = "http://localhost:3000" , allowCredentials = "true" , allowedHeaders = "*")
public class UserService {
	
	
	HttpSession newSession;
	
	static List<User> users = new ArrayList<User>();
	static String[] usernames    = {"alice", "bob", "charlie"};
	static String[] courseTitles = {"cs5200", "cs5610", "cs5500"};
	static String[] moduleTitles = {"Module 1", "Module 2"};
	static String[] lessonTitles = {"lesson 1", "lesson 2"};
	static String[] topicTitles = {"topic 1", "topic 2","topic 3"};
	{
		List<Topic> topics = new ArrayList<Topic>();
		for(String topicTitle : topicTitles) {
			topics.add(new Topic(topicTitle));
		}
		List<Lesson> lessons = new ArrayList<Lesson>();
		for(String lessonTitle : lessonTitles) {
			Lesson lesson = new Lesson(lessonTitle);
			if(lessonTitle.equals("lesson 1")) {
				lesson.setTopics(topics);
			}
			lessons.add(lesson);
		}
		List<Module> modules = new ArrayList<Module>();
		for(String moduleTitle: moduleTitles) {
			Module module = new Module(moduleTitle);
			if(moduleTitle.equals("Module 1")) {
				module.setLessons(lessons);
			}
			modules.add(module);
		}
		List<Course> courses = new ArrayList<Course>();
		for(String courseTitle: courseTitles) {
			Course course = new Course(courseTitle);
			if(courseTitle.equals("cs5200")) {
				course.setModules(modules);
			}
			courses.add(course);
		}
		for(String username: usernames) {
			User user = new User(username);
			if(username.equals("alice")) {
				user.setCourses(courses);
			}
			users.add(user);
		}
	}
	
	@GetMapping("/api/user")
	public List<User> findAllUsers() {
		return users;
	}
	
	@GetMapping("/api/user/{id}")
	public User findUserById(@PathVariable("id") int userId) {
		System.out.println("in findUserById");
		System.out.println("id" + userId);
		for(User user: users) {
			if(user.getId() == userId) {
				System.out.println("SUCCESS");
				System.out.println("user   "+user);
				return user;
			}
				
		}
		return null;
	}
	
	@PostMapping("/api/user")
	public List<User> createUser(@RequestBody User user) {
		users.add(user);
		return users;
	}
	
	@PostMapping("/api/register")
	public User register(
			@RequestBody User user,
			HttpSession session, HttpServletRequest request) {
		session.setAttribute("currentUser", user);
		newSession=session;
		//System.out.println(user.getUsername());
		
		users.add(user);
		return user;
	}
	
	
	@GetMapping("/api/profile")
	public User profile(HttpSession session, HttpServletRequest request) {
		User currentUser = (User)newSession.getAttribute("currentUser");
		//System.out.println(currentUser);
		return currentUser;
	}
	
	@PostMapping("/api/logout")
	public void logout(HttpSession session) {
		session.invalidate();
	}
	
	@PostMapping("/api/login")
	public User login(
			@RequestBody User credentials,
			HttpSession session) {
	 for (User user : users) {
	  if( user.getUsername().equals(credentials.getUsername())
	   /*|| user.getPassword().equals(credentials.getPassword())*/) {
	    session.setAttribute("currentUser", user);
	    newSession = session;
	    return user;
	  }
	  
	 }
	 return null;
	}




}
