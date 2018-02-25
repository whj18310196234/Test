<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试</title>
</head>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/static/common/js/jquery-1.11.3.min.js"></script>
 --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery.js"></script>
<body>
   <div style="margin: 10px;height:50px;"></div>
   <div style="margin:0 auto; width:200px; height:50px;">
       <h2 style="text-align:center">用户登录系统</h2>
   </div>
   <div style="margin: 10px;"></div>
   <div style="margin:0 auto; width:400px; height:120px;text-align:center">       
	   <input type="text" name="name" id="name" style="margin: 10px;border:1px solid #317ef3;border-radius: 2px;height:23px"> <span>用户</span></br>
	   <input type="password" name="password" id="password" style="margin: 10px;border:1px solid #317ef3;border-radius: 2px;height:23px"> <span>密码</span></br>
 	   <input type="button" value="登录" onclick="checkUser();" style="font-size: 20px;margin: 10px;background-color: white;border:1px solid blue;border-radius: 2px;">       
   </div>
</body>
<script type="text/javascript">
	function checkUser() {
		var name = document.getElementById("name").value;
		var password = document.getElementById("password").value;
			$.ajax({
	            url:"${pageContext.request.contextPath}/login",
	            type:"post",
	            data:{"name":name,"password":password},
	            dataType:"json",
	            success:function(count){		            	 
	            	if(count==1){
	            		alert("用户不存在");
	            	}else if(count==2){
	            		alert("密码错误");
	            	}else if(count==0){
	            		window.location.href='${pageContext.request.contextPath}/index';
	            		/* window.location.href='http://www.baidu.com'; */
	            	}
	           },
	           error:function(){
	          	 alert("获取信息失败!");
	            }
			});
	}
</script>
</html>