package com.uniovi.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;

@Component
public class CreatePostValidator implements Validator {
	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Post post = (Post) target;
		if (post.getTitle().length() < 5 || post.getTitle().length() > 50) {
			errors.rejectValue("title", "Error.post.title.length");
		}
		if (post.getText().length() < 5 || post.getText().length() > 100) {
			errors.rejectValue("text", "Error.post.text.length");
		}

	}

}
