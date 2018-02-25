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
<div id="container">
	<div id="hd">
    	<div class="hd-wrap ue-clear">
        	<div class="top-light"></div>
            <h1 class="logo" onclick="goWelcom()"></h1>
            <div class="login-info ue-clear">
                <div class="welcome ue-clear" style="width:400px;"><span>欢迎您,</span></div>
                <input type="hidden" id="ServerContext" value="${pageContext.request.contextPath }"/>
            </div>            
        </div>
    </div>
    <div><input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" > &nbsp;&nbsp;时间</div>
    <div id="bd">
    	<div class="wrap ue-clear">
        	<div class="sidebar">
            	<h2 class="sidebar-header"><p>功能导航</p></h2>
                <ul class="nav">
                    <li class="konwledge">
                    	<div class="nav-header"><a href="javascript:;" class="ue-clear"><span>用户成绩</span><i class="icon"></i></a></div>
                        <ul class="subnav">
                        	<li><a href="javascript:;" onclick="kaoshi();">考试成绩</a></li>
                        	<%-- <li><a href="javascript:;" date-src="${baseurl}/payQuery.action">理赔服务</a></li> --%>
                        </ul>
                    </li>
                    <li class="system">
                    	<div class="nav-header"><a href="javascript:;" class="ue-clear"><span>用户资料</span><i class="icon"></i></a></div>
                        <ul class="subnav">
                        	<li><a href="javascript:;" onclick="xinxi();">个人信息</a></li>
                        	<%-- <li><a href="javascript:;" date-src="${baseurl}/updatePwd.action">修改密码</a></li> --%>
                        </ul>
                    </li>                    
                </ul>
            </div>
            
        </div>
    </div>
    
</div>
</body>
<script type="text/javascript">
    function kaoshi(){
    	alert(1);
    }
    function xinxi(){
    	alert(2);
    }
</script>



</html>