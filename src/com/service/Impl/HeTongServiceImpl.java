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
 *@date    2017��9��25��   
 */
@Service
public class HeTongServiceImpl implements HeTongService{

	@Autowired
	private HeTongMapper heTongMapper;

	/*
	 * �����Ѻ�ж�
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
	 * �����Ѻ�ж�
	 */
	public static Map<String,Object>  Test(String html) throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        html = StringEscapeUtils.unescapeJson(html);
		Document doc1 = Jsoup.parseBodyFragment(html);
		Element tbody = doc1.body().getElementsByTag("table").get(0).getElementsByTag("tbody").get(0);		
		//������
		//TODO �ϰ��ж����Ѻ��
		Elements select = tbody.getElementsMatchingOwnText("�����Ʒ���йصĵ�Ѻ���Ϊ").select("input[class=textbox_bottom]");
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
	 * ����������ж�
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
	 * ����������ж�
	 */
	public static Map<String,Object>  TestBuyer(String html) throws Exception {
        Map<String,Object> map = new HashMap<String, Object>();
        html = StringEscapeUtils.unescapeJson(html);
		Document doc1 = Jsoup.parseBodyFragment(html);
		Element tbody = doc1.body().getElementsByTag("table").get(0).getElementsByTag("tbody").get(0);	
		
		if(tbody.child(9).select("input[name=contractUserName]").size()==3){
			map.put("������������:��ͬ���:", tbody.child(6).select("td input").val());
			map.put("������������:", tbody.child(9).select("input[name=contractUserName]").get(2).val());
		}
		if(tbody.child(9).select("input[name=contractUserName]").size()==4){
			map.put("���ĸ�������:��ͬ��ţ�", tbody.child(6).select("td input").val());
			map.put("���ĸ�������:", tbody.child(9).select("input[name=contractUserName]").get(3).val());
		}
		if(tbody.child(9).select("input[name=contractUserName]").size()==5){
			map.put("�����������:��ͬ��ţ�", tbody.child(6).select("td input").val());
			map.put("�����������:", tbody.child(9).select("input[name=contractUserName]").get(4).val());
		}
		//��������2���º�ͬû�У��ж��Ƿ���ֵ
		Elements fonts = tbody.getElementsMatchingOwnText("ǩ������ͬǰ����").select("font");
		if(!fonts.get(0).text().trim().equals("X")
				&&!fonts.get(0).text().trim().equals("x")
				&&!fonts.get(0).text().trim().equals("")){
			map.put("��������2��last����ͬ��ţ�", tbody.child(6).select("td input").val());
			map.put("��������2��last", fonts.get(0).text());
		}
		
		return map;
		
	}
	public static void main(String[] args) throws Exception{
		//��ȡ�ļ�
			InputStreamReader reader =
					new InputStreamReader(new FileInputStream("C:/Users/dell/Desktop/�½��ı��ĵ� (2).txt"), "gbk");

			BufferedReader bReader = new BufferedReader(reader);//newһ��BufferedReader���󣬽��ļ����ݶ�ȡ������
			StringBuilder sb = new StringBuilder();//����һ���ַ������棬���ַ�����Ż�����
			String s = "";
			while ((s = bReader.readLine()) != null) {//���ж�ȡ�ļ����ݣ�����ȡ���з���ĩβ�Ŀո�
				sb.append(s + "\n");//����ȡ���ַ�����ӻ��з����ۼӴ���ڻ�����
			}
			bReader.close();
			String html = sb.toString();
			Map<String, Object> map = new HashMap<String, Object>();

			//ת�ַ���
			html = StringEscapeUtils.unescapeJson(html);
			//ת Document
			Document doc1 = Jsoup.parseBodyFragment(html);
			Element tbody = doc1.body().getElementsByTag("table").get(0).getElementsByTag("tbody").get(0);				
			
			if(tbody.child(9).select("input[name=contractUserName]").size()>2){
				map.put("������������", tbody.child(9).select("input[name=contractUserName]").get(2).val());
			}
			System.out.println(tbody.child(9).select("input[name=contractUserName]").size());
//			System.out.println(map);
//			System.out.println(map.size());
	}
}
