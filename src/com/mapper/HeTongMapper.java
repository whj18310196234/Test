package com.mapper;

import com.entity.HeTong;
import com.entity.Person;
import com.entity.PersonExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HeTongMapper {
   
	List<HeTong> selectByExample(Integer page1);

    
}