package com.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Person;
import com.entity.PersonExample;
import com.mapper.PersonMapper;
import com.service.QueryService;

/**
 * @author  wanghongjun
 *
 *@date    2017Äê9ÔÂ25ÈÕ   
 */
@Service
public class QueryServiceImpl implements QueryService{

	@Autowired
	private PersonMapper personMapper;
	@Override
	public int selectByName(String name,String password) {
		PersonExample example = new PersonExample();
		PersonExample.Criteria criteria =example.createCriteria();
		criteria.andNameEqualTo(name);		
		List<Person> list = personMapper.selectByExample(example);
		if(list==null||list.size()<=0){
			return 1;
		}
		criteria.andPasswordEqualTo(password);
		List<Person> list1 = personMapper.selectByExample(example);
		if(list1==null||list1.size()<=0){
			return 2;
		}
		
		return 0;
	}

}
