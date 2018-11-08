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
import com.example.whiteboardfall2018prernapurohitserverjava.models.Lesson;
import com.example.whiteboardfall2018prernapurohitserverjava.models.Module;
import com.example.whiteboardfall2018prernapurohitserverjava.models.Topic;
import com.example.whiteboardfall2018prernapurohitserverjava.models.User;

@RestController
@CrossOrigin(origins = "http://localhost:3000" , allowCredentials = "true" , allowedHeaders = "*")
public class TopicService {
	@Autowired
	UserService userService;
	int userId, courseId, moduleId, lessonId;
	
	
	@GetMapping("/api/course/{courseId}/module/{moduleId}/lesson/{lessonId}/topic")
	public List<Topic> findTopicsForLessonId(
			@PathVariable("courseId") int courseId,
			@PathVariable("moduleId") int moduleId,
			@PathVariable("lessonId") int lessonId,
			HttpSession session) {
		User user = (User)session.getAttribute("currentUser");
		this.userId = user.getId();
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
			@PathVariable("lid") int lessonId,
			HttpSession session){
			User user = (User)session.getAttribute("currentUser");
			for(Course course: user.getCourses()) {
				for(Module module: course.getModules()) {
					for(Lesson lesson: module.getLessons()) {
						if(lesson.getId() == lessonId) {
							this.lessonId = lessonId;
							return lesson.getTopics();
						}
					}
				}
			}
			return null;
	}
	
	@PostMapping("/api/lesson/{lid}/topic")
	public List<Topic> createTopic(
			@PathVariable("lid") int lessonId,
			@RequestBody Topic topic,
			HttpSession session){
		User user = (User)session.getAttribute("currentUser");
		List<Topic> topicList = null;
		for(Course course: user.getCourses()) {
			for(Module module: course.getModules()) {
				for(Lesson lesson: module.getLessons()) {
					if(lesson.getId() == lessonId) {
						topicList = lesson.getTopics();
					}
				}
			}
		}
		topicList.add(topic);
		return topicList;
		
	}
	
	@GetMapping("/api/topic/{tid}")
	public Topic findTopicById(
			@PathVariable("tid") int topicId,
			HttpSession session) {
		User user = (User)session.getAttribute("currentUser");

		for(Course course: user.getCourses()) {
			for(Module module: course.getModules()) {
				for(Lesson lesson: module.getLessons()) {
					for(Topic top : lesson.getTopics()) {
						if(top.getId() == topicId) {
							return top;
						}
					}
				}
			}
		}
		return null;
	}
	
	@PutMapping("/api/topic/{tid}") 
	public List<Topic> updateTopic(
			@PathVariable("tid") int topicId,
			@RequestBody Topic topic,
			HttpSession session){
		User user = (User)session.getAttribute("currentUser");		
		List<Topic> mytopics = null;
		Topic old = null;
		for(Course course: user.getCourses()) {
			for(Module module: course.getModules()) {
				for(Lesson lesson: module.getLessons()) {
					for(Topic top : lesson.getTopics()) {
						
						if(top.getId() == topicId) {
							top.setTitle(topic.getTitle());
							mytopics = lesson.getTopics();
							return lesson.getTopics();
						}
					}
				}
			}
		}
		
		/*mytopics.remove(old);
		mytopics.add(topic);*/
		return mytopics;
	}

	@DeleteMapping("/api/topic/{tid}") 
	public List<Topic> deleteTopic(
			@PathVariable("tid") int topicId,
			HttpSession session){
		User user = (User)session.getAttribute("currentUser");		
		List<Topic> mytopics = null;
		Topic old = null;
		for(Course course: user.getCourses()) {
			for(Module module: course.getModules()) {
				for(Lesson lesson: module.getLessons()) {
					for(Topic top : lesson.getTopics()) {
						
						if(top.getId() == topicId) {
							mytopics = lesson.getTopics();
							old = top;
						}
					}
				}
			}
		}
		
		mytopics.remove(old);
		return mytopics;
	}

}

