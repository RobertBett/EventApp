package com.example.loginreg.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.loginreg.models.User;

@Component
public class UserValidator implements Validator {
	private static final String EMAIL_RE = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private Pattern pattern;
	private Matcher matcher;
	
	public UserValidator() {
		pattern = Pattern.compile(EMAIL_RE);
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}
	
	public boolean validEmail(String email) {
		matcher =pattern.matcher(email);
		return matcher.matches();
	}
	
	@Override
	public void validate(Object object, Errors errors) {
		User user = (User) object;
		
		if(!user.getConfirm().equals(user.getPassword())) {
			errors.reject("confrim", "Passwords Do Not Match");
		}else if(!validEmail(user.getEmail())) {
			errors.rejectValue("email","Match");
		}
	}
}
