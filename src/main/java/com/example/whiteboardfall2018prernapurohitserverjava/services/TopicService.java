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
import com.example.whiteboardfall2018prernapurohitserverjava.models.Topic;
import com.example.whiteboardfall2018prernapurohitserverjava.models.User;

@RestController
@CrossOrigin(origins="*")
public class TopicService {
	@Autowired
	UserService userService;
	int userId, courseId, moduleId, lessonId;
	
	
	@GetMapping("/api/user/{userId}/course/{courseId}/module/{moduleId}/lesson/{lessonId}/topic")
	public List<Topic> findTopicsForLessonId(
			@PathVariable("userId") int userId,
			@PathVariable("courseId") int courseId,
			@PathVariable("moduleId") int moduleId,
			@PathVariable("lessonId") int lessonId) {
		User user = userService.findUserById(userId);
		this.userId = userId;
		for(Course course: user.getCourses()) {
			if(course.getId() == courseId) {
				this.courseId = courseId;
				for(Module module: course.getModules()) {
					if(module.getId() == moduleId) {
						this.moduleId = moduleId;
						for(Lesson lesson: module.getLessons()) {
							if(lesson.getId() == lessonId) {
								this.lessonId = lessonId;
								return lesson.getTopics();
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	@GetMapping("/api/lesson/{lid}/topic")
	public List<Topic> findAllTopics(
			@PathVariable("lid") int lessonId){
			return findTopicsForLessonId(this.userId, this.courseId, this.moduleId, this.lessonId);
	}
	
	@PostMapping("/api/lesson/{lid}/topic")
	public List<Topic> createTopic(
			@PathVariable("lid") int lessonId,
			@RequestBody Topic topic){
		
		List<Topic> topicList = findAllTopics(lessonId);
		topicList.add(topic);
		return topicList;
		
	}
	
	@GetMapping("/api/topic/{tid}")
	public Topic findTopicById(
			@PathVariable("tid") int topicId) {
		List<Topic> myTopics = findAllTopics(this.lessonId);
		for(Topic top : myTopics) {
			if(top.getId() == topicId) {
				return top;
			}
		}
		return null;
	}
	
	@PutMapping("/api/topic/{tid}") 
	public List<Topic> updateTopic(
			@PathVariable("tid") int topicId,
			@RequestBody Topic topic){
		
		List<Topic> myTopics = findAllTopics(this.lessonId);
		for(Topic top : myTopics) {
			if(top.getId() == topicId) {
				top = topic;
			}
		}
		return myTopics;
	}

	@DeleteMapping("/api/topic/{tid}") 
	public List<Topic> deleteTopic(
			@PathVariable("tid") int topicId){
		
		List<Topic> myTopics = findAllTopics(this.lessonId);
		Topic old = findTopicById(topicId);
		myTopics.remove(old);
		return myTopics;
	}

}

