<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/jsp/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/jquery.js"></script>
</head>
<script type="text/javascript" src="${baseurl}/common/pub/js/javascript/WdatePicker.js"></script>
<script type="text/javascript" src="${baseurl}/common/pub/js/javascript/jquery.js"></script>

<link rel="stylesheet" href="${baseurl}/style/WdatePicker.css" />
<body>
<h3>多个抵押类型判断...</h3>
<input type="button" onclick="testData()" value="下一页">
</body>
<script type="text/javascript">
    function testData(){
    	window.location.href='${pageContext.request.contextPath}/getData?page1=${page1}';
    }
   /*  setInterval("testData()",40000);    */
   
</script>



</html>