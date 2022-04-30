package com.example.jwtdemo.utils;

import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.stereotype.Component;

import com.example.jwtdemo.model.Players;

@Component
public class BeanValidator {
	
	public Validator getValidator() {
		return Validation.buildDefaultValidatorFactory().getValidator();
	}

	public ArrayList<String> signUpValidation(Players players){
		ArrayList<String> arrayList = new ArrayList<>();
		Set<ConstraintViolation<Players>> constraintViolations=getValidator().validate(players);
		for(ConstraintViolation<Players> constraintViolation:constraintViolations) {
			if(constraintViolation.getPropertyPath().toString().equals("name"))
				arrayList.add(constraintViolation.getMessage());
			if(constraintViolation.getPropertyPath().toString().equals("mobNo"))
				arrayList.add(constraintViolation.getMessage());
			if(constraintViolation.getPropertyPath().toString().equals("email"))
				arrayList.add(constraintViolation.getMessage());
		}
		return arrayList;
	}
}
