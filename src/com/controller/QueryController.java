package com.controller;

import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.Person;
import com.service.QueryService;

/**
 * @author  wanghongjun
 *
 *@date    2017��9��25��   
 */
@Controller
public class QueryController {

	@Resource
	private QueryService queryService;
	@RequestMapping("/gologin")
	public String goLogin(){		
		return "login";
	}
	@RequestMapping("/login")
	@ResponseBody
	public int login(String name,String password){		
		int count = queryService.selectByName(name,password);		
		return count;
	}
	@RequestMapping("/index")
	public String queryPerson(){			
		return "index";
	}
}
