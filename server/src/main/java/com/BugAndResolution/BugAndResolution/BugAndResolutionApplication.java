package com.BugAndResolution.BugAndResolution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class BugAndResolutionApplication {

	public static void main(String[] args) {
		SpringApplication.run(BugAndResolutionApplication.class, args);
	}

}
