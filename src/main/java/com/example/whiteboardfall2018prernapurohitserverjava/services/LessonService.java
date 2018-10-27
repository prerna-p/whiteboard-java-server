package com.example.whiteboardfall2018prernapurohitserverjava.services;

import java.util.List;

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
import com.example.whiteboardfall2018prernapurohitserverjava.models.Lesson;
import com.example.whiteboardfall2018prernapurohitserverjava.models.Module;
import com.example.whiteboardfall2018prernapurohitserverjava.models.User;

@RestController
@CrossOrigin(origins="*")
public class LessonService {
	@Autowired
	UserService userService;
	int userId,courseId,moduleId;

	
	@GetMapping("/api/user/{userId}/course/{cid}/module/{mid}/lesson")
	public List<Lesson> findLessonsForCourseId(
			@PathVariable("userId") int userId,
			@PathVariable("cid") int courseId,
			@PathVariable("mid") int moduleId) {
		this.userId = userId;
		this.courseId = courseId;
		User user = userService.findUserById(userId);
		for(Course course: user.getCourses()) {
			if(course.getId() == courseId) {
				for(Module module: course.getModules()) {
					if(module.getId() == moduleId) {
						this.moduleId = moduleId;
						return module.getLessons();
					}
				}
			}
		}
		return null;
	}
	
	@GetMapping("/api/module/{mid}/lesson")
	public List<Lesson> findAllLessons(
			@PathVariable("moduleId") int moduleId) {
		List<Lesson> lessons = findLessonsForCourseId(this.userId, this.courseId, moduleId);
		return lessons;
	}
	
	@PostMapping("/api/module/{mid}/lesson")
	public List<Lesson> createLesson(
			@PathVariable("mid") int midId,
			@RequestBody Lesson lesson) {
		List<Lesson> myLessons = findAllLessons(midId);
		myLessons.add(lesson);
		return myLessons;
	}
	
	@GetMapping("/api/lesson/{lid}")
	public Lesson findLessonById(
			@PathVariable("lid") int lessonId) {
		List<Lesson> myLessons = findAllLessons(this.moduleId);
		for(Lesson les : myLessons) {
			if(les.getId() == lessonId)
				return les;
		}
		return null;
	}
	
	@PutMapping("/api/lesson/{lid}")
	public List<Lesson> updateLesson(
			@PathVariable("lid") int lessonId,
			@RequestBody Lesson newLesson){
		
		Lesson old = findLessonById(lessonId);
		List<Lesson> myLessons = findAllLessons(this.moduleId);
		myLessons.remove(old);
		myLessons.add(newLesson);
		return myLessons;
		
	}
	
	@DeleteMapping("/api/lesson/{lid}")
	public List<Lesson> deleteLesson(
			@PathVariable("lid") int lessonId){
		
		List<Lesson> myLessons = findAllLessons(this.moduleId);
		Lesson old = findLessonById(lessonId);
		myLessons.remove(old);
		return myLessons;
		
	}
}
