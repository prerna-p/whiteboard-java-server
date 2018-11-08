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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.whiteboardfall2018prernapurohitserverjava.models.Course;
import com.example.whiteboardfall2018prernapurohitserverjava.models.Module;
import com.example.whiteboardfall2018prernapurohitserverjava.models.User;

@RestController
@CrossOrigin(origins = "http://course-manager-react-client.herokuapp.com" , allowCredentials = "true" , allowedHeaders = "*")
public class ModuleService {
	@Autowired
	CourseService courseService;
	UserService userService;
	int userId;
	
	@GetMapping("/api/user/{userId}/course/{cid}/module")
	public List<Module> findAllModules(
			@PathVariable("userId") int userId,
			@PathVariable("cid") int courseId) {
		Course course = courseService.findCourseById(userId, courseId);
		this.userId = userId;
		return course.getModules();
	}
	
	@GetMapping("/api/course/{cid}/module")
	public List<Module> findAllModules2(
			@PathVariable("cid") int courseId,
			HttpSession session) {
		User currentUser = (User)session.getAttribute("currentUser");
		Course course = courseService.findCourseById(currentUser.getId(), courseId);
		this.userId = currentUser.getId();
		return course.getModules();
	}
	
	@PostMapping("/api/course/{cid}/module")
	public List<Module> createModule(
			@PathVariable("cid") int courseId,
			@RequestBody Module module,
			HttpSession session) {
		User currentUser = (User)session.getAttribute("currentUser");
		List<Module> moduleList = findAllModules(currentUser.getId(),courseId);
		moduleList.add(module);
		return moduleList;
	}
	
	@GetMapping("/api/course/{cid}/module/{mid}")
	public Module findModuleById(
			@PathVariable("cid") int courseId,
			@PathVariable("mid") int moduleId) {
		List<Module> moduleList = findAllModules(this.userId, courseId);
		for(Module m:moduleList) {
			if(m.getId() == moduleId) {
				return m;
			}
		}
		return null;
	}
	
	@PutMapping("/api/course/{cid}/module/{mid}")
	public List<Module> updateModule(
			@PathVariable("cid") int courseId,
			@PathVariable("mid") int moduleId,
			@RequestBody Module module,
			HttpSession session) {
		User currentUser = (User)session.getAttribute("currentUser");
		List<Module> moduleList = findAllModules(currentUser.getId(), courseId);
		for(Module module1 : moduleList) {
			if(module1.getId() == module.getId()) {
				module1.setTitle(module.getTitle());
				return moduleList;
			}
		}
		
		return moduleList; 
	}
	
	@DeleteMapping("/api/course/{cid}/module/{mid}")
	public List<Module> deleteModule(
			@PathVariable("cid") int courseId,
			@PathVariable("mid") int moduleId,
			HttpSession session) {
	
		User currentUser = (User)session.getAttribute("currentUser");
		Course course = courseService.findCourseById(currentUser.getId(), courseId);
		List<Module> moduleList = course.getModules();
		Module mToDel = null;
		for(Module m:moduleList) {
			if(m.getId() == moduleId) {
				mToDel = m;
			}
		}
		if(!mToDel.equals(null))
			moduleList.remove(mToDel);
		
		return moduleList;
	}
	
}
