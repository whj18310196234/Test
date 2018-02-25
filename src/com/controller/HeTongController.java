package com.controller;

import java.util.List;

import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.HeTong;
import com.entity.Person;
import com.service.HeTongService;
import com.service.QueryService;

/**
 * @author  wanghongjun
 *
 *@date    2017年9月25日   
 */
@Controller
public class HeTongController {

	@Resource
	private HeTongService heTongService;
	
	//多个抵押人判断	
	@RequestMapping("/getData")
	public String  login(HttpServletRequest request,Model model) throws Exception{	
		String page1Str = request.getParameter("page1");
		Integer page1 =0;
		if(page1Str!=null){
			 page1 = Integer.valueOf(page1Str)+5000;
		}
		model.addAttribute("page1", page1);		
		heTongService.selectByExample(page1);
		System.out.println("第"+page1+5000+"条数据。。。。。。。。。。。。。。。。。。。。。");
		return "getDataTest";
	}
	
	//多个买受人判断
	@RequestMapping("/getDataBuyer")
	public String  selectBuyer(HttpServletRequest request,Model model) throws Exception{	
		String page1Str = request.getParameter("page1");
		Integer page1 =0;
		if(page1Str!=null){
			 page1 = Integer.valueOf(page1Str)+5000;
		}
		model.addAttribute("page1", page1);		
		heTongService.selectBuyer(page1);
		System.out.println("第"+page1+5000+"条数据。。。。。。。。。。。。。。。。。。。。。");
		return "getDataBuyer";
	}
	
}
