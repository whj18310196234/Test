package com.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author  wanghongjun
 *
 *@date    2017年12月20日   
 */
public class GetData {
	public static void main(String[] args) throws Exception {
		
	
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


		//TODO  商品房买卖合同（预售）
		map.put("SUB_SY_CMR", tbody.getElementById("cuUser").val());//出卖人
		//记录买受人个数
		int SUB_MSR_COUNT = 1;
		map.put("SUB_SY_MSR", tbody.child(9).select("input[name=contractUserName]").get(0).val());//买受人
		if (!tbody.child(9).select("input[name=contractUserName]").get(1).val().equals("")
				&& !tbody.child(9).select("input[name=contractUserName]").get(1).val().equals("X")){
			map.put("SUB_SY_MSR", tbody.child(9).select("input[name=contractUserName]").get(0).val() +
					"、" + tbody.child(9).select("input[name=contractUserName]").get(1).val());//多个买受人
			++SUB_MSR_COUNT;
		}


		//TODO  第一章
		//出卖人
		map.put("SUB_1_1", tbody.getElementById("cuUser").val());
		map.put("SUB_1_2", tbody.getElementById("kAdress").val());
		map.put("SUB_1_3", tbody.getElementById("postcode").val());
		map.put("SUB_1_4", tbody.getElementById("licenseCode").val());
		map.put("SUB_1_5", tbody.getElementById("qualiNo").val());
		map.put("SUB_1_6", tbody.getElementById("enterLegal").val());
		map.put("SUB_1_7", tbody.getElementById("telephone").val());
		map.put("SUB_1_8", tbody.child(9).select("input[name=textfield]").get(0).val());//child(9)第一章(大tbody下tr下标为9的)
		map.put("SUB_1_9", tbody.child(9).select("input[name=textfield14]").get(0).val());
		map.put("SUB_1_10", tbody.child(9).select("input[name=textfield92]").get(0).val());
		map.put("SUB_1_11", tbody.child(9).select("input[name=textfield92]").get(1).val());
		map.put("SUB_1_12", tbody.child(9).select("input[name=textfield92]").get(2).val());
		map.put("SUB_1_13", tbody.child(9).select("input[name=textfield102]").get(0).val());
		map.put("SUB_1_14", tbody.child(9).select("input[name=textfield102]").get(1).val());
		map.put("SUB_1_15", tbody.child(9).select("input[name=textfield122]").get(0).val());
		map.put("SUB_1_16", tbody.child(9).select("input[name=textfield14]").get(1).val());
		//买受人:1
		map.put("SUB_1_1_1", tbody.child(9).select("input[name=contractUserName]").get(0).val());

		map.put("SUB_1_1_2_1", "");
		if (tbody.child(9).select("input[name=checkbox2]").get(0).select("[checked=checked]").size() > 0)
			map.put("SUB_1_1_2_1", "√");

		map.put("SUB_1_1_2_2", "");
		if (tbody.child(9).select("input[name=checkbox]").get(0).select("[checked=checked]").size() > 0)
			map.put("SUB_1_1_2_2", "√");

		map.put("SUB_1_1_2", tbody.child(9).select("input[name=textfield]").get(1).val());
		map.put("SUB_1_1_3_1", "√");
		map.put("SUB_1_1_3_2", "");
		map.put("SUB_1_1_3", tbody.child(9).select("input[name=textfield1232]").get(0).val());

		map.put("SUB_1_1_4_1", "");
		if (tbody.child(9).select("input[name=checkbox3]").get(0).select("[checked=checked]").size() > 0)
			map.put("SUB_1_1_4_1", "√");

		map.put("SUB_1_1_4_2", "");
		if (tbody.child(9).select("input[name=checkbox4]").get(0).select("[checked=checked]").size() > 0)
			map.put("SUB_1_1_4_2", "√");

		map.put("SUB_1_1_4_3", "");
		if (tbody.child(9).select("input[name=checkbox5]").get(0).select("[checked=checked]").size() > 0)
			map.put("SUB_1_1_4_3", "√");

		map.put("SUB_1_1_4", "");
		if (tbody.child(9).select("input[name=checkbox33]").get(0).select("[checked=checked]").size() > 0)
			map.put("SUB_1_1_4", tbody.child(9).select("input[name=textfield20]").get(0).val() + "√");

		map.put("SUB_1_1_5", tbody.child(9).select("input[name=contractUserCard]").get(0).val());
		map.put("SUB_1_1_6", tbody.child(9).select("input[class=textbox_bottom]").get(20).val());//第一章class下标20
		map.put("SUB_1_1_7", tbody.child(9).select("input[class=textbox_bottom]").get(21).val());
		map.put("SUB_1_1_8", tbody.child(9).select("input[class=textbox_bottom]").get(22).val());
		map.put("SUB_1_1_9", tbody.child(9).select("input[class=textbox_bottom]").get(23).val() + "性");

		map.put("SUB_1_1_10", tbody.child(9).select("input[name=textfield8222]").get(0).val());
		map.put("SUB_1_1_11", tbody.child(9).getElementById("HPartitionCode").val());
		map.put("SUB_1_1_12", tbody.child(9).getElementById("JPartitionCode").val());
		map.put("SUB_1_1_13", tbody.child(9).select("input[name=textfield1222]").get(0).val());
		map.put("SUB_1_1_14", tbody.child(9).select("input[name=textfield142]").get(0).val());

		map.put("SUB_1_1_15_1", "");
		if (tbody.child(9).select("input[name=checkbox32]").get(0).select("[checked=checked]").size() > 0)
			map.put("SUB_1_1_15_1", "√");

		//TODO 做个标记 -----新版有法定代理人，老版没有法定代理人，老版是自定义代理人；目前默认：新版法定代理人对应老版的自定义代理人

		map.put("SUB_1_1_15_2", "");
		if (tbody.child(9).select("input[name=checkbox34]").get(0).select("[checked=checked]").size() > 0)
			map.put("SUB_1_1_15_2", "√");
		map.put("SUB_1_1_15", tbody.child(9).select("input[name=textfield12322]").get(0).val());

		map.put("SUB_1_1_16_1", "√");
		map.put("SUB_1_1_16_2", "");
		map.put("SUB_1_1_16", tbody.child(9).select("input[name=textfield12323]").get(0).val());

		map.put("SUB_1_1_17_1", "");
		map.put("SUB_1_1_17_2", "");
		map.put("SUB_1_1_17_3", "");
		map.put("SUB_1_1_17", "X");
		map.put("SUB_1_1_18", "X");
		map.put("SUB_1_1_19", "X");
		map.put("SUB_1_1_20", "X");
		map.put("SUB_1_1_21", "X");
		map.put("SUB_1_1_22", "不详");

		map.put("SUB_1_2_23", tbody.child(9).select("input[name=textfield822]").get(0).val());
		map.put("SUB_1_2_24", tbody.child(9).select("input[name=textfield1233]").get(0).val());
		map.put("SUB_1_2_25", tbody.child(9).select("input[name=textfield1323]").get(0).val());


		//买受人2
		map.put("SUB_1_2_1", tbody.child(9).select("input[name=contractUserName]").get(1).val());

		map.put("SUB_1_2_2_1", "");
		if (tbody.child(9).select("input[name=checkbox2]").get(1).select("[checked=checked]").size() > 0)
			map.put("SUB_1_2_2_1", "√");

		map.put("SUB_1_2_2_2", "");
		if (tbody.child(9).select("input[name=checkbox]").get(1).select("[checked=checked]").size() > 0)
			map.put("SUB_1_2_2_2", "√");

		map.put("SUB_1_2_2", tbody.child(9).select("input[name=textfield]").get(3).val());
		map.put("SUB_1_2_3_1", "√");
		map.put("SUB_1_2_3_2", "");
		map.put("SUB_1_2_3", tbody.child(9).select("input[name=textfield1232]").get(1).val());

		map.put("SUB_1_2_4_1", "");
		if (tbody.child(9).select("input[name=checkbox3]").get(1).select("[checked=checked]").size() > 0)
			map.put("SUB_1_2_4_1", "√");

		map.put("SUB_1_2_4_2", "");
		if (tbody.child(9).select("input[name=checkbox4]").get(1).select("[checked=checked]").size() > 0)
			map.put("SUB_1_2_4_2", "√");

		map.put("SUB_1_2_4_3", "");
		if (tbody.child(9).select("input[name=checkbox5]").get(1).select("[checked=checked]").size() > 0)
			map.put("SUB_1_2_4_3", "√");

		map.put("SUB_1_2_4", "");
		if (tbody.child(9).select("input[name=checkbox33]").get(1).select("[checked=checked]").size() > 0)
			map.put("SUB_1_2_4", tbody.child(9).select("input[name=textfield20]").get(1).val() + "√");

		map.put("SUB_1_2_5", tbody.child(9).select("input[name=contractUserCard]").get(1).val());
		map.put("SUB_1_2_6", tbody.child(9).select("input[class=textbox_bottom]").get(38).val());//第一章class下标20
		map.put("SUB_1_2_7", tbody.child(9).select("input[class=textbox_bottom]").get(39).val());
		map.put("SUB_1_2_8", tbody.child(9).select("input[class=textbox_bottom]").get(40).val());

		if (tbody.child(9).select("input[class=textbox_bottom]").get(41).val().trim().equals("X")) {
			map.put("SUB_1_2_9", tbody.child(9).select("input[class=textbox_bottom]").get(41).val());
		}
		if (!tbody.child(9).select("input[class=textbox_bottom]").get(41).val().trim().equals("X")) {
			map.put("SUB_1_2_9", tbody.child(9).select("input[class=textbox_bottom]").get(41).val() + "性");
		}


		map.put("SUB_1_2_10", tbody.child(9).select("input[name=textfield8222]").get(1).val());
		map.put("SUB_1_2_11", tbody.child(9).getElementById("ShareHPartitionCode").val());
		map.put("SUB_1_2_12", tbody.child(9).getElementById("ShareJPartitionCode").val());
		map.put("SUB_1_2_13", tbody.child(9).select("input[name=textfield1222]").get(1).val());
		map.put("SUB_1_2_14", tbody.child(9).select("input[name=textfield142]").get(1).val());

		map.put("SUB_1_2_15_1", "");
		if (tbody.child(9).select("input[name=checkbox32]").get(1).select("[checked=checked]").size() > 0)
			map.put("SUB_1_2_15_1", "√");

		//TODO 做个标记  ----新版有法定代理人，老版没有法定代理人，老版是自定义代理人；目前默认：新版法定代理人对应老版的自定义代理人
		map.put("SUB_1_2_15_2", "");
		if (tbody.child(9).select("input[name=checkbox34]").get(1).select("[checked=checked]").size() > 0)
			map.put("SUB_1_2_15_2", "√");

		map.put("SUB_1_2_15", tbody.child(9).select("input[name=textfield12322]").get(1).val());

		map.put("SUB_1_2_16_1", "√");
		map.put("SUB_1_2_16_2", "");
		map.put("SUB_1_2_16", tbody.child(9).select("input[name=textfield12323]").get(1).val());

		map.put("SUB_1_2_17_1", "");
		map.put("SUB_1_2_17_2", "");
		map.put("SUB_1_2_17_3", "");
		map.put("SUB_1_2_17", "X");
		map.put("SUB_1_2_18", "X");
		map.put("SUB_1_2_19", "X");
		map.put("SUB_1_2_20", "X");
		map.put("SUB_1_2_21", "X");
		map.put("SUB_1_2_22", "不详");

		map.put("SUB_1_1_23", tbody.child(9).select("input[name=textfield822]").get(1).val());
		map.put("SUB_1_1_24", tbody.child(9).select("input[name=textfield1233]").get(1).val());
		map.put("SUB_1_1_25", tbody.child(9).select("input[name=textfield1323]").get(1).val());
        //记录买受人个数
		map.put("SUB_MSR_COUNT",Integer.toString(SUB_MSR_COUNT));

		//TODO  第二章
		//第一条
		map.put("SUB_2_1_1_1", "");
		String tdRight = tbody.getElementById("tdRight").val().trim();
		if (tdRight.equals("出让")) {
			map.put("SUB_2_1_1_1", "√");
		}
		map.put("SUB_2_1_1", "");
		if (tdRight.equals("划拨")) {
			map.put("SUB_2_1_1", "√");
		}
		map.put("SUB_2_1_2", "X");
		if (tdRight != null && !tdRight.equals("") && !tdRight.equals("X")
				&& !tdRight.equals("出让") && !tdRight.equals("划拨")) {
			map.put("SUB_2_1_2", tbody.getElementById("tdRight").val() + "√");
		}
		map.put("SUB_2_1_13", tbody.getElementById("tdWeizhi").val());
		map.put("SUB_2_1_3_1", "");
		if (tbody.select("input[name=checkbox372]").get(0).select("[checked=checked]").size() > 0)
			map.put("SUB_2_1_3_1", "√");

		map.put("SUB_2_1_3", "X");
		map.put("SUB_2_1_4", tbody.getElementById("tdCode").val());
		map.put("SUB_2_1_5", tbody.getElementById("tdArea").val());
		map.put("SUB_2_1_6", tbody.select("input[name=tdGHyongtu]").get(0).val());
		map.put("SUB_2_1_7", tbody.getElementById("td_end_year").val());
		map.put("SUB_2_1_8", tbody.getElementById("td_end_month").val());
		map.put("SUB_2_1_9", tbody.getElementById("td_end_day").val());

		map.put("SUB_2_1_10", tbody.getElementById("proName").val());
		map.put("SUB_2_1_11", tbody.getElementById("jsgcghCode").val());
		map.put("SUB_2_1_12", tbody.getElementById("sgCode").val());

		//第二条
		map.put("SUB_2_2_1", tbody.select("input[name=textfield]").get(8).val());
		map.put("SUB_2_2_2", tbody.getElementById("permitCode").val());
		//第三条

		map.put("SUB_2_3_1_1", "");
		if (tbody.select("input[name=checkbox32]").get(2).select("[checked=checked]").size() > 0)
			map.put("SUB_2_3_1_1", "√");

		map.put("SUB_2_3_1_2", "");
		if (tbody.select("input[name=checkbox32]").get(3).select("[checked=checked]").size() > 0)
			map.put("SUB_2_3_1_2", "√");

		map.put("SUB_2_3_1_3", "");
		if (tbody.select("input[name=checkbox32]").get(4).select("[checked=checked]").size() > 0)
			map.put("SUB_2_3_1_3", "√");

		map.put("SUB_2_3_1", "X");
		if (tbody.select("input[name=checkbox32]").get(5).select("[checked=checked]").size() > 0)
			map.put("SUB_2_3_1", tbody.select("input[name=tdGHyongtu]").get(1).val() + "√");

		map.put("SUB_2_3_2", tbody.getElementById("buildJiegou").val());
		map.put("SUB_2_3_3", tbody.getElementById("zc").val());
		map.put("SUB_2_3_4", tbody.getElementById("dsc").val());
		map.put("SUB_2_3_5", tbody.getElementById("dxc").val());

		map.put("SUB_2_3_6", tbody.getElementById("buildCode").val());
		map.put("SUB_2_3_7_1", "√");
		map.put("SUB_2_3_7_2", "");
		map.put("SUB_2_3_7", "X");
		map.put("SUB_2_3_8", tbody.getElementById("doorCode").val());
		map.put("SUB_2_3_9", tbody.getElementsMatchingOwnText("3.该商品房为第一条规定项目中的").select("input[name=textfield]").get(0).val());
		map.put("SUB_2_3_10", tbody.getElementById("houseCode").val());

		map.put("SUB_2_3_11", tbody.getElementsMatchingOwnText("3.该商品房为第一条规定项目中的").select("input[name=textfield]").get(1).val());
		map.put("SUB_2_3_12", tbody.select("input[name=contractArea]").get(0).val());
		map.put("SUB_2_3_13", tbody.getElementById("houseSymj").val());
		map.put("SUB_2_3_14", tbody.getElementById("ftmj").val());

		map.put("SUB_2_3_15", tbody.select("input[name=contractFsCg]").get(0).val());
		map.put("SUB_2_3_16", tbody.getElementsMatchingOwnText("3.该商品房为第一条规定项目中的").select("input[class=textbox_bottom]").get(14).val());
		map.put("SUB_2_3_17", tbody.getElementsMatchingOwnText("3.该商品房为第一条规定项目中的").select("input[name=textfield]").get(2).val());
		map.put("SUB_2_3_18", tbody.getElementsMatchingOwnText("3.该商品房为第一条规定项目中的").select("input[name=textfield]").get(3).val());


		//第四条
		map.put("SUB_2_4_1_1", "");
		if (tbody.getElementsMatchingOwnText("与该商品房有关的抵押情况为").select("input[name=checkbox37563]").get(0).select("input[checked=checked]").size() > 0)
			map.put("SUB_2_4_1_1", "√");

		map.put("SUB_2_4_1_2", "");
		if (tbody.getElementsMatchingOwnText("与该商品房有关的抵押情况为").select("input[name=checkbox37563]").get(1).select("input[checked=checked]").size() > 0)
			map.put("SUB_2_4_1_2", "√");
		//TODO 老版有多个抵押人
		map.put("SUB_2_4_1", tbody.getElementsMatchingOwnText("与该商品房有关的抵押情况为").select("input[class=textbox_bottom]").get(0).val());
		map.put("SUB_2_4_2", tbody.getElementsMatchingOwnText("与该商品房有关的抵押情况为").select("input[class=textbox_bottom]").get(1).val());
		map.put("SUB_2_4_3", tbody.getElementsMatchingOwnText("与该商品房有关的抵押情况为").select("input[class=textbox_bottom]").get(2).val());
		map.put("SUB_2_4_4", tbody.getElementsMatchingOwnText("与该商品房有关的抵押情况为").select("input[class=textbox_bottom]").get(3).val());
		map.put("SUB_2_4_5", tbody.getElementsMatchingOwnText("与该商品房有关的抵押情况为").select("input[class=textbox_bottom]").get(4).val());
		map.put("SUB_2_4_6", tbody.getElementsMatchingOwnText("与该商品房有关的抵押情况为").select("input[class=textbox_bottom]").get(5).val());

		//第五条
		map.put("SUB_2_5_1", tbody.select("tr").get(26).select("input").get(0).val());
		map.put("SUB_2_5_2", tbody.select("tr").get(27).select("input").get(0).val());
		map.put("SUB_2_5_3", tbody.getElementsMatchingOwnText("如该商品房权利状况与上述情况不符").select("font").get(0).text());

		map.put("SUB_2_5_3_1", "");
		if (tbody.getElementsMatchingOwnText("如该商品房权利状况与上述情况不符").select("input[name=checkbox37563]").get(0).select("[checked=checked]").size() > 0)
			map.put("SUB_2_5_3_1", "√");

		map.put("SUB_2_5_3_2", "");
		if (tbody.getElementsMatchingOwnText("如该商品房权利状况与上述情况不符").select("input[name=checkbox37563]").get(1).select("[checked=checked]").size() > 0)
			map.put("SUB_2_5_3_2", "√");


		//TODO  第三章
		//第六条
		map.put("SUB_3_6_1", tbody.getElementById("jisuanType").val());

		map.put("SUB_3_6_1_1", "");
		if (tbody.getElementById("ck_build_area").select("[checked=checked]").size() > 0)
			map.put("SUB_3_6_1_1", "√");

		map.put("SUB_3_6_1_2", "");
		if (tbody.getElementById("ck_in_area").select("[checked=checked]").size() > 0)
			map.put("SUB_3_6_1_2", "√");//
		map.put("SUB_3_6_2", tbody.getElementsMatchingOwnText("出卖人与买受人按照下列第").select("input[name=textfield]").get(0).val() + "币");
		map.put("SUB_3_6_3", tbody.getElementById("contractAprice").val());
		map.put("SUB_3_6_11", tbody.getElementsMatchingOwnText("出卖人与买受人按照下列第").select("input[name=textfield]").get(1).val() + "币");
		map.put("SUB_3_6_4", tbody.getElementById("contractPrice").val());
		map.put("SUB_3_6_5", tbody.getElementById("house_price_d").val());

		map.put("SUB_3_6_13", tbody.getElementsMatchingOwnText("出卖人与买受人按照下列第").select("input[name=textfield]").get(2).val() + "币");
		map.put("SUB_3_6_6", tbody.getElementById("d_house_price").val());
		map.put("SUB_3_6_7", tbody.select("input[name=textfield2122]").val());

		map.put("SUB_3_6_8", tbody.getElementsMatchingOwnText("出卖人与买受人按照下列第").select("input[class=textbox_bottom]").get(9).val());
		map.put("SUB_3_6_12", tbody.getElementsMatchingOwnText("出卖人与买受人按照下列第").select("input[name=textfield]").get(3).val() + "币");
		map.put("SUB_3_6_9", tbody.getElementById("zong1").val());
		map.put("SUB_3_6_10", tbody.select("input[name=textfield2123]").val());

		//第七条:(一) （第七条到第二十九条【...合同附件与本合同具有同等法律效力。】是一个大div。input标签，则用input取value;font标签，则用font取文本）
		Elements inputs = tbody.getElementsMatchingOwnText("签订本合同前，买").select("input");
		Elements fonts = tbody.getElementsMatchingOwnText("签订本合同前，买").select("font");

		map.put("SUB_3_7_1", inputs.get(0).val());
		map.put("SUB_3_7_2", inputs.get(1).val());
		map.put("SUB_3_7_3", inputs.get(2).val());

		map.put("SUB_3_7_4_1", "");
		if (inputs.get(3).select("[checked]").size() > 0)
			map.put("SUB_3_7_4_1", "√");

		map.put("SUB_3_7_4_2", "");
		if (inputs.get(4).select("[checked]").size() > 0)
			map.put("SUB_3_7_4_2", "√");

		map.put("SUB_3_7_4", "X");
		if (inputs.get(5).select("[checked]").size() > 0)
			map.put("SUB_3_7_4", inputs.get(6).val() + "√");

		map.put("SUB_3_7_5_1", "");
		if (inputs.get(7).select("[checked]").size() > 0)
			map.put("SUB_3_7_5_1", "√");

		map.put("SUB_3_7_5", "X");
		if (inputs.get(8).select("[checked]").size() > 0)
			map.put("SUB_3_7_5", inputs.get(9).val() + "√");

		//第七条:(二)

		map.put("SUB_3_7_6", inputs.get(10).val());

		map.put("SUB_3_7_7", inputs.get(11).val());
		map.put("SUB_3_7_8", inputs.get(12).val());
		map.put("SUB_3_7_9", inputs.get(13).val());

		map.put("SUB_3_7_10", inputs.get(14).val());
		map.put("SUB_3_7_11", inputs.get(15).val());
		map.put("SUB_3_7_12", inputs.get(16).val());
		map.put("SUB_3_7_13", inputs.get(17).val());

		map.put("SUB_3_7_14", inputs.get(18).val());
		map.put("SUB_3_7_15", inputs.get(19).val());
		map.put("SUB_3_7_16", inputs.get(20).val());
		map.put("SUB_3_7_17", inputs.get(21).val());
		map.put("SUB_3_7_18", inputs.get(22).val());
		map.put("SUB_3_7_19", inputs.get(23).val());

		map.put("SUB_3_7_20_1", "");
		if (inputs.get(24).select("[checked]").size() > 0)
			map.put("SUB_3_7_20_1", "√");

		map.put("SUB_3_7_20_2", "");
		if (inputs.get(25).select("[checked]").size() > 0)
			map.put("SUB_3_7_20_2", "√");

		map.put("SUB_3_7_20", "X");
		if (inputs.get(26).select("[checked]").size() > 0)
			map.put("SUB_3_7_20", inputs.get(27).val() + "√");

		map.put("SUB_3_7_21", inputs.get(28).val());
		map.put("SUB_3_7_22", inputs.get(29).val());
		map.put("SUB_3_7_23", inputs.get(30).val());
		map.put("SUB_3_7_24", inputs.get(31).val());
		map.put("SUB_3_7_25", inputs.get(32).val());
		map.put("SUB_3_7_26", inputs.get(33).val());
		map.put("SUB_3_7_27", inputs.get(34).val());
		map.put("SUB_3_7_28", inputs.get(35).val());
		map.put("SUB_3_7_29", inputs.get(36).val());
		map.put("SUB_3_7_30", inputs.get(37).val());
		map.put("SUB_3_7_31", inputs.get(38).val());

		map.put("SUB_3_7_32", fonts.get(1).text());

		//第七条:(三)
		map.put("SUB_3_7_33", inputs.get(39).val());
		map.put("SUB_3_7_34", inputs.get(40).val());
		map.put("SUB_3_7_35", inputs.get(41).val());

		//第八条
		map.put("SUB_3_8_1", inputs.get(42).val());
		map.put("SUB_3_8_2", inputs.get(43).val());
		map.put("SUB_3_8_3", inputs.get(44).val());
		map.put("SUB_3_8_4", inputs.get(45).val());
		map.put("SUB_3_8_5", inputs.get(46).val());
		map.put("SUB_3_8_6", inputs.get(47).val());
		map.put("SUB_3_8_7", inputs.get(48).val());
		map.put("SUB_3_8_8", fonts.get(2).text());

		//TODO 第四章  第九条
		map.put("SUB_4_9_1", inputs.get(49).val());
		map.put("SUB_4_9_2", inputs.get(50).val());
		map.put("SUB_4_9_3", fonts.get(3).text());
		map.put("SUB_4_9_4", fonts.get(4).text());

		//第十条 (一)
		map.put("SUB_4_10_5", fonts.get(5).text());
		map.put("SUB_4_10_6", fonts.get(6).text());
		map.put("SUB_4_10_7", fonts.get(7).text());
		map.put("SUB_4_10_8", fonts.get(8).text());

		map.put("SUB_4_10_9", inputs.get(51).val());
		map.put("SUB_4_10_10", inputs.get(52).val());
		map.put("SUB_4_10_11", inputs.get(53).val());
		map.put("SUB_4_10_4", inputs.get(54).val());
		map.put("SUB_4_10_12", inputs.get(55).val());

		map.put("SUB_4_10_13", fonts.get(9).text());

		//第十条(二)
		map.put("SUB_4_10_14", inputs.get(56).val());
		map.put("SUB_4_10_15", inputs.get(57).val());
		map.put("SUB_4_10_16", inputs.get(58).val());
		map.put("SUB_4_10_17", fonts.get(10).text());

		map.put("SUB_4_10_18", inputs.get(59).val());
		map.put("SUB_4_10_19", inputs.get(60).val());
		map.put("SUB_4_10_20", inputs.get(61).val());
		map.put("SUB_4_10_21", fonts.get(11).text());

		map.put("SUB_4_10_22", inputs.get(62).val());
		map.put("SUB_4_10_23", inputs.get(63).val());
		map.put("SUB_4_10_24", inputs.get(64).val());
		map.put("SUB_4_10_25", fonts.get(12).text());

		map.put("SUB_4_10_26", inputs.get(65).val());
		map.put("SUB_4_10_27", inputs.get(66).val());
		map.put("SUB_4_10_28", inputs.get(67).val());
		map.put("SUB_4_10_29", fonts.get(13).text());

		map.put("SUB_4_10_30", inputs.get(68).val());
		map.put("SUB_4_10_31", inputs.get(69).val());
		map.put("SUB_4_10_32", inputs.get(70).val());
		map.put("SUB_4_10_33", fonts.get(14).text());

		map.put("SUB_4_10_34", inputs.get(71).val());
		map.put("SUB_4_10_35", inputs.get(72).val());
		map.put("SUB_4_10_36", inputs.get(73).val());
		map.put("SUB_4_10_37", fonts.get(15).text());

		map.put("SUB_4_10_38", inputs.get(74).val());
		map.put("SUB_4_10_39", inputs.get(75).val());
		map.put("SUB_4_10_40", inputs.get(76).val());
		map.put("SUB_4_10_41", fonts.get(16).text());

		map.put("SUB_4_10_42", fonts.get(17).text());
		map.put("SUB_4_10_48", fonts.get(18).text());
		map.put("SUB_4_10_43", fonts.get(19).text());
		map.put("SUB_4_10_44", fonts.get(20).text());
		map.put("SUB_4_10_45", fonts.get(21).text());
		map.put("SUB_4_10_46", fonts.get(22).text());
		map.put("SUB_4_10_47", fonts.get(23).text());

		//第十一条：（一）
		map.put("SUB_4_11_1", inputs.get(77).val());
		map.put("SUB_4_11_2", inputs.get(78).val());
		map.put("SUB_4_11_3", inputs.get(79).val());

		//第十一条：（二）
		map.put("SUB_4_11_4", inputs.get(80).val());
		map.put("SUB_4_11_5", fonts.get(24).text());

		//第十一条：（三）
		map.put("SUB_4_11_6", inputs.get(81).val());
		map.put("SUB_4_11_7", fonts.get(25).text());
		map.put("SUB_4_11_8", fonts.get(26).text());
		map.put("SUB_4_11_9", fonts.get(27).text());
		map.put("SUB_4_11_10", fonts.get(28).text());

		//第十二条
		map.put("SUB_4_12_1", inputs.get(82).val());
		map.put("SUB_4_12_2", inputs.get(83).val());
		map.put("SUB_4_12_3", inputs.get(84).val());
		map.put("SUB_4_12_4", inputs.get(85).val());
		map.put("SUB_4_12_5", fonts.get(29).text());
		map.put("SUB_4_12_6", inputs.get(86).val());
		map.put("SUB_4_12_7", inputs.get(87).val());
		map.put("SUB_4_12_8", fonts.get(30).text());
		//
		//TODO 第五章
		//第十三条
		map.put("SUB_5_13_1", inputs.get(88).val());
		map.put("SUB_5_13_2", fonts.get(31).text());
		map.put("SUB_5_13_3", fonts.get(32).text());
		map.put("SUB_5_13_4", fonts.get(33).text());
		map.put("SUB_5_13_5", fonts.get(34).text());

		//TODO  第六章
		//第十四条
		map.put("SUB_6_14_1", fonts.get(35).text());
		map.put("SUB_6_14_2", inputs.get(89).val());
		map.put("SUB_6_14_3", fonts.get(36).text());

		//第十五条
		map.put("SUB_6_15_1", fonts.get(37).text());
		map.put("SUB_6_15_2", fonts.get(38).text());
		map.put("SUB_6_15_3", fonts.get(39).text());
		map.put("SUB_6_15_4", fonts.get(40).text());
		map.put("SUB_6_15_5", inputs.get(90).val());
		map.put("SUB_6_15_6", fonts.get(41).text());

		//TODO 第七章
		//第十六条 (一)
		map.put("SUB_7_16_1", fonts.get(42).text());

		map.put("SUB_7_16_1_1", "");
		if (inputs.get(91).select("[checked]").size() > 0)
			map.put("SUB_7_16_1_1", "√");

		map.put("SUB_7_16_1_2", "");
		if (inputs.get(92).select("[checked]").size() > 0)
			map.put("SUB_7_16_1_2", "√");

		map.put("SUB_7_16_2", fonts.get(43).text());
		//第十六条 (二)
		map.put("SUB_7_16_3", fonts.get(44).text());
		map.put("SUB_7_16_4", fonts.get(45).text());
		map.put("SUB_7_16_5", fonts.get(46).text());
		//第十六条 (三)
		map.put("SUB_7_16_6", "X");
		if (!inputs.get(93).val().trim().equals(""))
			map.put("SUB_7_16_6", inputs.get(93).val());

		map.put("SUB_7_16_7", "X");
		if (!inputs.get(94).val().trim().equals(""))
			map.put("SUB_7_16_7", inputs.get(94).val());

		map.put("SUB_7_16_8", fonts.get(47).text());
		map.put("SUB_7_16_9", fonts.get(48).text());
		//第十六条 (四)
		map.put("SUB_7_16_10_1", "");
		if (inputs.get(95).select("[checked]").size() > 0)
			map.put("SUB_7_16_10_1", "√");

		map.put("SUB_7_16_10_2", "");
		if (inputs.get(96).select("[checked]").size() > 0)
			map.put("SUB_7_16_10_2", "√");

		map.put("SUB_7_16_10", inputs.get(97).val());
		map.put("SUB_7_16_11", inputs.get(98).val());


		map.put("SUB_7_16_12_1", "");
		if (inputs.get(99).select("[checked]").size() > 0)
			map.put("SUB_7_16_12_1", "√");

		map.put("SUB_7_16_12_2", "");
		if (inputs.get(100).select("[checked]").size() > 0)
			map.put("SUB_7_16_12_2", "√");

		map.put("SUB_7_16_12", inputs.get(101).val());
		map.put("SUB_7_16_13", inputs.get(102).val());

		map.put("SUB_7_16_14", fonts.get(49).text());
		map.put("SUB_7_16_15", fonts.get(50).text());

		//第十七条
		map.put("SUB_7_17_1", fonts.get(51).text());
		map.put("SUB_7_17_2", inputs.get(103).val());
		//第十八条
		map.put("SUB_7_18_1",fonts.get(52).text());
//
		//TODO 第八章
		//第十九条
		map.put("SUB_8_19_1_1", "");
		if (inputs.get(104).select("[checked]").size() > 0)
			map.put("SUB_8_19_1_1", "√");

		map.put("SUB_8_19_1", "X");
		if (inputs.get(105).select("[checked]").size() > 0)
			map.put("SUB_8_19_1", inputs.get(106).val() + "√");

		map.put("SUB_8_19_2", fonts.get(53).text());
		//第二十条
		map.put("SUB_8_20_1", inputs.get(107).val());
		map.put("SUB_8_20_2", inputs.get(108).val());
		map.put("SUB_8_20_3", fonts.get(54).text());
		map.put("SUB_8_20_4", inputs.get(109).val());
		map.put("SUB_8_20_5", fonts.get(55).text());

		//TODO 第九章
		//第二十一条(一)
		map.put("SUB_9_21_1", fonts.get(56).text());
		//第二十一条(二)
		map.put("SUB_9_21_2", inputs.get(110).val());
		map.put("SUB_9_21_3", inputs.get(111).val());
		map.put("SUB_9_21_4", inputs.get(112).val());
		map.put("SUB_9_21_5", inputs.get(113).val());
		map.put("SUB_9_21_6", inputs.get(114).val());
		map.put("SUB_9_21_7", inputs.get(115).val());

		//第二十一条(三)
		map.put("SUB_9_21_8_1", "");
		if (inputs.get(116).select("[checked]").size() > 0)
			map.put("SUB_9_21_8_1", "√");

		map.put("SUB_9_21_8_2", "");
		if (inputs.get(117).select("[checked]").size() > 0)
			map.put("SUB_9_21_8_2", "√");

		map.put("SUB_9_21_8", "X");
		if (inputs.get(118).select("[checked]").size() > 0)
			map.put("SUB_9_21_8", inputs.get(119).val() + "√");

		map.put("SUB_9_21_9", inputs.get(120).val());

		//TODO 第十章
		//第二十二条
		map.put("SUB_10_22_1", fonts.get(57).text());
		map.put("SUB_10_22_2", fonts.get(58).text());
		map.put("SUB_10_22_3", fonts.get(59).text());
		map.put("SUB_10_22_4", fonts.get(60).text());
		//第二十三条
		map.put("SUB_10_23_1", inputs.get(121).val());
		//第二十四条
		map.put("SUB_10_24_1", fonts.get(61).text());
		map.put("SUB_10_24_2", fonts.get(62).text());
		//第二十五条
		map.put("SUB_10_25_1_1","");
		if (inputs.get(122).select("[checked]").size() > 0)
			map.put("SUB_10_25_1_1", "√");

		map.put("SUB_10_25_1_2", "");
		if (inputs.get(123).select("[checked]").size() > 0)
			map.put("SUB_10_25_1_2", "√");

		map.put("SUB_10_25_1", "X");
		if (inputs.get(124).select("[checked]").size() > 0)
			map.put("SUB_10_25_1", inputs.get(125).val() + "√");

		map.put("SUB_10_25_2", inputs.get(126).val());
		//第二十七条
		map.put("SUB_10_27_1", inputs.get(127).val());
		map.put("SUB_10_27_2", inputs.get(128).val());
		//第二十九条
		map.put("SUB_10_29_1", inputs.get(129).val());
		map.put("SUB_10_29_2", inputs.get(130).val());
		map.put("SUB_10_29_3", inputs.get(131).val());
		map.put("SUB_10_29_4", inputs.get(132).val());

		map.put("SUB_10_29_5", "X");
		if (!inputs.get(133).val().trim().equals(""))
			map.put("SUB_10_29_5", inputs.get(133).val());

		map.put("SUB_10_29_6", "X");
		if (!inputs.get(134).val().trim().equals(""))
			map.put("SUB_10_29_6", inputs.get(134).val());

		map.put("SUB_10_29_7", "X");
		if (!inputs.get(135).val().trim().equals(""))
			map.put("SUB_10_29_7", inputs.get(135).val());

		map.put("SUB_10_29_8", "X");
		if (!inputs.get(136).val().trim().equals(""))
			map.put("SUB_10_29_8", inputs.get(136).val());

		//  END 调用 --inputs、fonts
		map.put("SUB_10_29_9", tbody.getElementsMatchingOwnText("签订时间：").get(0).select("input").get(0).val());
		map.put("SUB_10_29_10", tbody.getElementsMatchingOwnText("签订时间：").get(0).select("input").get(1).val());
		map.put("SUB_10_29_11", tbody.getElementsMatchingOwnText("签订时间：").get(0).select("input").get(2).val());
		map.put("SUB_10_29_15", tbody.getElementsMatchingOwnText("签订地点:").get(0).select("input").val());

		map.put("SUB_10_29_12", tbody.getElementsMatchingOwnText("签订时间：").get(1).select("input").get(0).val());
		map.put("SUB_10_29_13", tbody.getElementsMatchingOwnText("签订时间：").get(1).select("input").get(1).val());
		map.put("SUB_10_29_14", tbody.getElementsMatchingOwnText("签订时间：").get(1).select("input").get(2).val());
		map.put("SUB_10_29_16", tbody.getElementsMatchingOwnText("签订地点:").get(1).select("input").val());


		//TODO  附件元素 ...附件二以后是一个大div,根据div中元素获取信息
		Elements fjInputs = tbody.getElementsMatchingOwnText("1.纳入该商品房分摊的共用部").select("input");
		Elements fjFonts = tbody.getElementsMatchingOwnText("1.纳入该商品房分摊的共用部").select("font");

		//TODO 附件一
		map.put("SUB_FJ1_1", "X");
		map.put("SUB_FJ1_2", "X");

		//TODO 附件二
		map.put("SUB_FJ2_2", fjFonts.get(0).text());

		//TODO 附件三
		map.put("SUB_FJ3_3", fjFonts.get(1).text());

		//TODO 附件四
		map.put("SUB_FJ4_1", fjFonts.get(2).text());

		//TODO 附件五
		map.put("SUB_FJ5_2", fjFonts.get(3).text());

		//TODO 附件六
		//1.
		map.put("SUB_F6_1_1_1", "");
		if (fjInputs.get(0).select("[checked]").size() > 0)
			map.put("SUB_F6_1_1_1", "√");

		map.put("SUB_F6_1_1_2", "");
		if (fjInputs.get(1).select("[checked]").size() > 0)
			map.put("SUB_F6_1_1_2", "√");

		map.put("SUB_F6_1_1_3", "");
		if (fjInputs.get(2).select("[checked]").size() > 0)
			map.put("SUB_F6_1_1_3", "√");

		map.put("SUB_F6_1_1", "X");
		if (fjInputs.get(3).select("[checked]").size() > 0)
			map.put("SUB_F6_1_1", fjFonts.get(4).text() + "√");

		map.put("SUB_F6_1_2", fjFonts.get(5).text());

		//2.(1)
		map.put("SUB_F6_2_1_1", "");
		if (fjInputs.get(4).select("[checked]").size() > 0)
			map.put("SUB_F6_2_1_1", "√");

		map.put("SUB_F6_2_1_2", "");
		if (fjInputs.get(5).select("[checked]").size() > 0)
			map.put("SUB_F6_2_1_2", "√");

		map.put("SUB_F6_2_1", "X");
		if (fjInputs.get(6).select("[checked]").size() > 0)
			map.put("SUB_F6_2_1", fjFonts.get(6).text() + "√");

		map.put("SUB_F6_2_2", fjFonts.get(7).text());

		//2.(2)
		map.put("SUB_F6_2_3_1", "");
		if (fjInputs.get(7).select("[checked]").size() > 0)
			map.put("SUB_F6_2_3_1", "√");

		map.put("SUB_F6_2_3_2", "");
		if (fjInputs.get(8).select("[checked]").size() > 0)
			map.put("SUB_F6_2_3_2", "√");

		map.put("SUB_F6_2_3", "X");
		if (fjInputs.get(9).select("[checked]").size() > 0)
			map.put("SUB_F6_2_3", fjFonts.get(8).text() + "√");

		map.put("SUB_F6_2_4", fjFonts.get(9).text());

		//2.(3)
		map.put("SUB_F6_2_5_1", "");
		if (fjInputs.get(10).select("[checked]").size() > 0)
			map.put("SUB_F6_2_5_1", "√");

		map.put("SUB_F6_2_5_2", "");
		if (fjInputs.get(11).select("[checked]").size() > 0)
			map.put("SUB_F6_2_5_2", "√");

		map.put("SUB_F6_2_5_3", "");
		if (fjInputs.get(12).select("[checked]").size() > 0)
			map.put("SUB_F6_2_5_3", "√");

		map.put("SUB_F6_2_5_4", "");
		if (fjInputs.get(13).select("[checked]").size() > 0)
			map.put("SUB_F6_2_5_4", "√");

		map.put("SUB_F6_2_5", "X");
		if (fjInputs.get(14).select("[checked]").size() > 0)
			map.put("SUB_F6_2_5", fjFonts.get(10).text() + "√");

		map.put("SUB_F6_2_6", fjFonts.get(11).text());


		//3.(1)
		map.put("SUB_F6_3_1_1", "");
		if (fjInputs.get(15).select("[checked]").size() > 0)
			map.put("SUB_F6_3_1_1", "√");

		map.put("SUB_F6_3_1_2", "");
		if (fjInputs.get(16).select("[checked]").size() > 0)
			map.put("SUB_F6_3_1_2", "√");

		map.put("SUB_F6_3_1", "X");
		if (fjInputs.get(17).select("[checked]").size() > 0)
			map.put("SUB_F6_3_1", fjFonts.get(12).text() + "√");

		map.put("SUB_F6_3_2", fjFonts.get(13).text());

		//3.(2)
		map.put("SUB_F6_3_3_1", "");
		if (fjInputs.get(18).select("[checked]").size() > 0)
			map.put("SUB_F6_3_3_1", "√");

		map.put("SUB_F6_3_3_2", "");
		if (fjInputs.get(19).select("[checked]").size() > 0)
			map.put("SUB_F6_3_3_2", "√");

		map.put("SUB_F6_3_3", "X");
		if (fjInputs.get(20).select("[checked]").size() > 0)
			map.put("SUB_F6_3_3", fjFonts.get(14).text() + "√");

		map.put("SUB_F6_3_4", fjFonts.get(15).text());

		//3.(3)
		map.put("SUB_F6_3_5_1", "");
		if (fjInputs.get(21).select("[checked]").size() > 0)
			map.put("SUB_F6_3_5_1", "√");

		map.put("SUB_F6_3_5_2", "");
		if (fjInputs.get(22).select("[checked]").size() > 0)
			map.put("SUB_F6_3_5_2", "√");

		map.put("SUB_F6_3_5", "X");
		if (fjInputs.get(23).select("[checked]").size() > 0)
			map.put("SUB_F6_3_5", fjFonts.get(16).text() + "√");

		map.put("SUB_F6_3_6", fjFonts.get(17).text());
		map.put("SUB_F6_3_7", fjFonts.get(18).text());


		//4.(1)
		map.put("SUB_F6_4_1_1", "");
		if (fjInputs.get(24).select("[checked]").size() > 0)
			map.put("SUB_F6_4_1_1", "√");

		map.put("SUB_F6_4_1_2", "");
		if (fjInputs.get(25).select("[checked]").size() > 0)
			map.put("SUB_F6_4_1_2", "√");

		map.put("SUB_F6_4_1", "X");
		if (fjInputs.get(26).select("[checked]").size() > 0)
			map.put("SUB_F6_4_1", fjFonts.get(19).text() + "√");

		map.put("SUB_F6_4_2", fjFonts.get(20).text());

		//4.(2)
		map.put("SUB_F6_4_3_1", "");
		if (fjInputs.get(27).select("[checked]").size() > 0)
			map.put("SUB_F6_4_3_1", "√");

		map.put("SUB_F6_4_3_2", "");
		if (fjInputs.get(28).select("[checked]").size() > 0)
			map.put("SUB_F6_4_3_2", "√");

		map.put("SUB_F6_4_3", "X");
		if (fjInputs.get(29).select("[checked]").size() > 0)
			map.put("SUB_F6_4_3", fjFonts.get(21).text() + "√");

		map.put("SUB_F6_4_4", fjFonts.get(22).text());

		//4.(3)
		map.put("SUB_F6_4_5_1", "");
		if (fjInputs.get(30).select("[checked]").size() > 0)
			map.put("SUB_F6_4_5_1", "√");

		map.put("SUB_F6_4_5_2", "");
		if (fjInputs.get(31).select("[checked]").size() > 0)
			map.put("SUB_F6_4_5_2", "√");

		map.put("SUB_F6_4_5", "X");
		if (fjInputs.get(32).select("[checked]").size() > 0)
			map.put("SUB_F6_4_5", fjFonts.get(23).text() + "√");

		map.put("SUB_F6_4_6", fjFonts.get(24).text());
		map.put("SUB_F6_4_7", fjFonts.get(25).text());


		//5
		map.put("SUB_F6_5_1_1", "");
		if (fjInputs.get(33).select("[checked]").size() > 0)
			map.put("SUB_F6_5_1_1", "√");

		map.put("SUB_F6_5_1_2", "");
		if (fjInputs.get(34).select("[checked]").size() > 0)
			map.put("SUB_F6_5_1_2", "√");

		map.put("SUB_F6_5_1_3", "");
		if (fjInputs.get(35).select("[checked]").size() > 0)
			map.put("SUB_F6_5_1_3", "√");

		map.put("SUB_F6_5_1_4", "");
		if (fjInputs.get(36).select("[checked]").size() > 0)
			map.put("SUB_F6_5_1_4", "√");

		map.put("SUB_F6_5_1", "X");
		if (fjInputs.get(37).select("[checked]").size() > 0)
			map.put("SUB_F6_5_1", fjFonts.get(26).text() + "√");

		map.put("SUB_F6_5_2", fjFonts.get(27).text());

		//6
		map.put("SUB_F6_6_1", fjFonts.get(28).text());
		map.put("SUB_F6_6_2", fjFonts.get(29).text());
		//7
		map.put("SUB_F6_7_1", fjFonts.get(30).text());
		//8
		map.put("SUB_F6_8_1", fjFonts.get(31).text());
		//9
		map.put("SUB_F6_9_1", fjFonts.get(32).text());
		//10
		map.put("SUB_F6_10_1", fjFonts.get(33).text());


		//TODO 附件七
		//(一)		TODO
		map.put("SUB_F7_1_1", fjInputs.get(39).val().trim());
		map.put("SUB_F7_1_2", fjFonts.get(34).text());

		map.put("SUB_F7_2_1", fjInputs.get(41).val());
		map.put("SUB_F7_2_2", fjFonts.get(35).text());

		map.put("SUB_F7_3_1", fjInputs.get(43).val());
		map.put("SUB_F7_3_2", fjFonts.get(36).text());

		map.put("SUB_F7_4_1", fjInputs.get(45).val());
		map.put("SUB_F7_4_2", fjFonts.get(37).text());

		map.put("SUB_F7_5_1", fjInputs.get(47).val());
		map.put("SUB_F7_5_2", fjFonts.get(38).text());

		map.put("SUB_F7_6_1", fjFonts.get(39).text());
		map.put("SUB_F7_7_1", fjFonts.get(40).text());
		map.put("SUB_F7_8_1", fjFonts.get(41).text());

		//(二)
		map.put("SUB_F7_9_1", fjFonts.get(42).text());

		//TODO 附件八
		map.put("SUB_FJ8_1", fjFonts.get(43).text());

		//TODO 附件九
		map.put("SUB_FJ9_1", fjFonts.get(44).text());

		//TODO 附件十
		map.put("SUB_FJ10_1", fjFonts.get(45).text());

		//TODO 附件十一
//				map.put("SUB_FJ11_1",new String(fjFonts.get(46).text().getBytes(),"gbk").replace("?", ""));
//				map.put("SUB_FJ11_1", fjFonts.get(46).text().replace("&nbsp;", ""));
//				map.put("SUB_FJ11_1", fjFonts.get(46).text());
		map.put("SUB_FJ11_1", fjFonts.get(46).toString().replaceAll("<br>","\n").replaceAll("&nbsp;"," ").replace("<font class=\"font_bottom\" id=\"htbcxy\" onclick=\"show_info(this)\">","").replace("</font>",""));



//		Elements d = tbody.getElementsMatchingOwnText("3.该商品房为第一条规定项目中的");
//		String d =tbody.select("tr").get(26).select("input").get(0).val();
//		System.out.println(d);
//		System.out.println(map.toString().replace("=", ":"));

//		System.out.println(map.size());

	}
}
 