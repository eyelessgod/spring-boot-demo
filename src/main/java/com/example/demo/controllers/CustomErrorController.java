package com.example.demo.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class CustomErrorController implements ErrorController{

	@RequestMapping(value="error")
	public ModelAndView  error(  HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("error");
		String errorStatusCode = RequestDispatcher.ERROR_STATUS_CODE;
		Integer errorCode = (Integer) request.getAttribute(errorStatusCode);
		
		String message;
		switch (errorCode){
		case 404:
			message = "Страница не найдена";
			break;
		default:
			message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
			break;
		}
		message=errorCode+": "+message;
		
		modelAndView.addObject("message",message);
		return modelAndView;
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "error";
	}
}
