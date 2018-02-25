package com.service.Impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.HeTong;
import com.entity.Person;
import com.entity.PersonExample;
import com.mapper.HeTongMapper;
import com.mapper.PersonMapper;
import com.service.HeTongService;
import com.service.QueryService;

/**
 * @author  wanghongjun
 *
 *@date    2017年9月25日   
 */
@Service
public class HeTongServiceImpl implements HeTongService{

	@Autowired
	private HeTongMapper heTongMapper;

	/*
	 * 多个抵押判断
	 */
	@Override
	public void selectByExample(Integer page1) throws Exception {
		List<HeTong> list = heTongMapper.selectByExample(page1);
		Integer sum = 0;
		 Map<Integer,Object> map = new HashMap<Integer, Object>();
		for(HeTong h :list){
			if(Test(h.getData().toString()).size()>0)
			map.put(sum++, Test(h.getData().toString()));			
		}
		for (Integer key : map.keySet()) {
			System.out.println("key= "+ key + " and value= " + map.get(key));
		}
		System.out.println(map.size());
	}
	/*
	 * 多个抵押判断
	 */
	public static Map<String,Object>  Test(String html) throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        html = StringEscapeUtils.unescapeJson(html);
		Document doc1 = Jsoup.parseBodyFragment(html);
		Element tbody = doc1.body().getElementsByTag("table").get(0).getElementsByTag("tbody").get(0);		
		//第四条
		//TODO 老版有多个抵押人
		Elements select = tbody.getElementsMatchingOwnText("与该商品房有关的抵押情况为").select("input[class=textbox_bottom]");
		if(!select.get(6).val().trim().equals("")&&!select.get(6).val().trim().equals("X")
				&&!select.get(6).val().trim().equals("x")
				&&!select.get(6).val().trim().equals("XX")){
			map.put("1",select.get(6).val());
		}
		if(!select.get(7).val().trim().equals("")&&!select.get(7).val().trim().equals("X")
				&&!select.get(7).val().trim().equals("x")
				&&!select.get(7).val().trim().equals("XX")){
			map.put("2",select.get(7).val());
		}
		if(!select.get(8).val().trim().equals("")&&!select.get(8).val().trim().equals("X")
				&&!select.get(8).val().trim().equals("x")
				&&!select.get(8).val().trim().equals("XX")){
			map.put("3",select.get(8).val());
		}
		if(!select.get(9).val().trim().equals("")&&!select.get(9).val().trim().equals("X")
				&&!select.get(9).val().trim().equals("x")
				&&!select.get(9).val().trim().equals("XX")){
			map.put("4",select.get(9).val());
		}
		if(!select.get(10).val().trim().equals("")&&!select.get(10).val().trim().equals("X")
				&&!select.get(10).val().trim().equals("x")
				&&!select.get(10).val().trim().equals("XX")){
			map.put("5",select.get(10).val());
		}
		if(!select.get(11).val().trim().equals("")&&!select.get(11).val().trim().equals("X")
				&&!select.get(11).val().trim().equals("x")
				&&!select.get(11).val().trim().equals("XX")){
			map.put("6",select.get(11).val());
		}
//		System.out.println(map);
//		System.out.println(map.size());
		return map;
		
	}

	
	/*
	 * 多个买受人判断
	 */
	@Override
	public void selectBuyer(Integer page1) throws Exception {
		List<HeTong> list = heTongMapper.selectByExample(page1);
		Integer sum = 0;
		Map<Integer,Object> map = new HashMap<Integer, Object>();
		for(HeTong h :list){
			Map<String, Object> test = TestBuyer(h.getData().toString());
			if(test.size()>0)
			map.put(sum++, test);			
		}
		for (Integer key : map.keySet()) {
			System.out.println("key= "+ key + " and value= " + map.get(key));
		}
		System.out.println(map.size());
	}
	/*
	 * 多个买受人判断
	 */
	public static Map<String,Object>  TestBuyer(String html) throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        html = StringEscapeUtils.unescapeJson(html);
		Document doc1 = Jsoup.parseBodyFragment(html);
		Element tbody = doc1.body().getElementsByTag("table").get(0).getElementsByTag("tbody").get(0);	
		
		if(tbody.child(9).select("input[name=contractUserName]").size()==3){
			map.put("第三个买受人:合同编号:", tbody.child(6).select("td input").val());
			map.put("第三个买受人:", tbody.child(9).select("input[name=contractUserName]").get(2).val());
		}
		if(tbody.child(9).select("input[name=contractUserName]").size()==4){
			map.put("第四个买受人:合同编号：", tbody.child(6).select("td input").val());
			map.put("第四个买受人:", tbody.child(9).select("input[name=contractUserName]").get(3).val());
		}
		if(tbody.child(9).select("input[name=contractUserName]").size()==5){
			map.put("第五个买受人:合同编号：", tbody.child(6).select("td input").val());
			map.put("第五个买受人:", tbody.child(9).select("input[name=contractUserName]").get(4).val());
		}
		//第七条：2：新合同没有，判断是否有值
		Elements fonts = tbody.getElementsMatchingOwnText("签订本合同前，买").select("font");
		if(!fonts.get(0).text().trim().equals("X")
				&&!fonts.get(0).text().trim().equals("x")
				&&!fonts.get(0).text().trim().equals("")){
			map.put("第七条：2：last：合同编号：", tbody.child(6).select("td input").val());
			map.put("第七条：2：last", fonts.get(0).text());
		}
		
		return map;
		
	}
	public static void main(String[] args) throws Exception{
		//读取文件
			InputStreamReader reader =
					new InputStreamReader(new FileInputStream("C:/Users/dell/Desktop/新建文本文档 (2).txt"), "gbk");

			BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
			StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
			String s = "";
			while ((s = bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
				sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
			}
			bReader.close();
			String html = sb.toString();
			Map<String, Object> map = new HashMap<String, Object>();

			//转字符串
			html = StringEscapeUtils.unescapeJson(html);
			//转 Document
			Document doc1 = Jsoup.parseBodyFragment(html);
			Element tbody = doc1.body().getElementsByTag("table").get(0).getElementsByTag("tbody").get(0);				
			
			if(tbody.child(9).select("input[name=contractUserName]").size()>2){
				map.put("第三个买受人", tbody.child(9).select("input[name=contractUserName]").get(2).val());
			}
			System.out.println(tbody.child(9).select("input[name=contractUserName]").size());
//			System.out.println(map);
//			System.out.println(map.size());
	}
}
