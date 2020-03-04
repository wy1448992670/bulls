<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${basePath}css/bootstrap.min.css" media="all" rel="stylesheet" type="text/css" />
    <link href="${basePath}css/style.css" media="all" rel="stylesheet" type="text/css" />
	<title>错误提示</title>
</head>
<body>
    <!-- Login Screen -->
   	<div style="font-size:20px; color:red; text-align:center;">${error }<br /><a href="javascript:history.back(-1);">返回</a></div>
    <!-- End Login Screen -->
</body>

</html>