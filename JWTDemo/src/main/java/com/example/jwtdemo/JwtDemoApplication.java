package com.example.jwtdemo;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.jwtdemo.service.FilesStorageService;

@SpringBootApplication
public class JwtDemoApplication implements CommandLineRunner {

	@Resource
	  FilesStorageService storageService;
	public static void main(String[] args) {
		SpringApplication.run(JwtDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		storageService.deleteAll();
	    storageService.init();
	}

}
