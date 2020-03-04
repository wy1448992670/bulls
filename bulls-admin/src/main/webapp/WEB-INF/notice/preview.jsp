<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<meta name="keywords" content="全民理财,鑫聚财,P2P,P2B,安全透明,网络投资,网络投资平台,网上投资,抵押借贷,免费投资,放心投资,安全投资,安全理财,P2P网络投资,P2P网上投资,本金保障,高收益,高回报,投资,借贷,理财,个人投资,民间投资,P2P投资,企业融资,企业借贷"/>
	<meta name="description" content="鑫聚财-您的理财专家"/>
	<meta name="renderer" content="webkit">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>全民理财-公告-${notice.title }</title>
</head>
<body>
	<div class="container">
		<div style="border: 1px solid #ddd;padding: 10px;margin: 15px 0;" class="clearfix">
			<h2>${notice.title }</h2>
			<p style="text-indent: 2em;">
				<c:out value="${notice.htmlContent }" escapeXml="false"></c:out>
			</p>
			<img src="${aPath }images/notice.png" style="float: right;" width="90"/>
		</div>
	</div>
</body>
</html>
