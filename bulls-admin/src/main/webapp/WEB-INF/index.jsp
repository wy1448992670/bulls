<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>欢迎登入</title>
</head>
<body>
	<div class="modal-shiftfix">
        <!-- Navigation -->
      	<jsp:include page="common/header.jsp"></jsp:include>
        <!-- End Navigation -->
        <div class="container-fluid main-content">
         	<div class="row">
                <div class="col-lg-12">
                    <div class="widget-container stats-container">
                    	<h2 style="padding: 30px 0;">
                    		欢迎你，${sessionScope.user.roleName } <label class="label label-success"> ${sessionScope.user.trueName }</label>
                    	</h2>
                    </div>
                </div>
            </div>
        </div>	
    </div>
	<script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
</body>
</html>