package com.example.whiteboardfall2018serverjava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.whiteboardfall2018serverjava.models.Message;

@RestController
public class HelloWorldService {
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello World!";
	}
	
	@GetMapping("/hello/{name}")
	public String sayHelloToName(
			@RequestParam(name="message", required=false) String msg,
			@PathVariable("name") String nameVariable) {
	   return "Hello " + nameVariable + ", message = " + msg;
	}

	@GetMapping("/message")
	public Message getMessage() {
	   return new Message();
	}

}
