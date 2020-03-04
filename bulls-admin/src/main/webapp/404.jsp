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
	<title>404</title>
</head>
<body class="fourofour">
    <!-- Login Screen -->
    <div class="fourofour-container">
        <h1>
            404
        </h1>
        <h2>
            您访问的链接地址不存在
        </h2>
        <a class="btn btn-lg btn-default-outline" href="${basePath}home"><i class="icon-home"></i>返回首页</a>

    </div>
    <!-- End Login Screen -->
</body>

</html>