package com.example.whiteboardfall2018prernapurohitserverjava.models;

import java.util.ArrayList;
import java.util.List;

public class Lesson {
	private int id = (int)(Math.random() * Integer.MAX_VALUE);
	private String title;
	private List<Topic> topics = new ArrayList<Topic>();
	public List<Topic> getTopics() {
		return topics;
	}
	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}
	public Lesson() {}
	public Lesson(String title) {
		this.title = title;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
