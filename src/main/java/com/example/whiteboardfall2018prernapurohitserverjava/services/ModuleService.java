package com.example.whiteboardfall2018prernapurohitserverjava.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.whiteboardfall2018prernapurohitserverjava.models.Course;
import com.example.whiteboardfall2018prernapurohitserverjava.models.Module;

@RestController
@CrossOrigin(origins = "*")
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
	
	
	@PostMapping("/api/course/{cid}/module")
	public List<Module> createModule(
			@PathVariable("cid") int courseId,
			@RequestBody Module module) {
			
		List<Module> moduleList = findAllModules(this.userId,courseId);
		moduleList.add(module);
		return moduleList;
	}
	
	@GetMapping("/api/course/{cid}/module/{mid}")
	public Module findModuleById(
			@PathVariable("cid") int courseId,
			@PathVariable("mid") int modId) {
		List<Module> moduleList = findAllModules(this.userId, courseId);
		for(Module m:moduleList) {
			if(m.getId() == modId) {
				return m;
			}
		}
		return null;
	}
	
}
