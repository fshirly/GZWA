package com.fable.insightview.mp4.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/platform/vedio")
public class mp4Controller {
	
	private final Logger LOGGER = LoggerFactory.getLogger(mp4Controller.class);
	
	@RequestMapping("/toVedio")
	public ModelAndView toVedio() {
		System.out.println("/platform/vedio/toVedio");
		return new ModelAndView("/vedio/vedio");
	}
}
