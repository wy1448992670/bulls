<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>推送管理</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="">
   <%-- ${ios}
   ${android} --%>
   
   <table>
   	<tr>
   		<td width="100">渠道</td>
   		<!-- <td width="100">发送数量</td> -->
   		<!-- <td>task_id</td> -->
   		<td width="120">消息实际发送数</td>
   		<td width="100">打开数</td>
   		<td width="100">状态</td>
   	</tr>
   	<tr>
   		<td>IOS</td>
   		<%-- <td>${ios.data.total_count}</td> --%>
   		<%-- <td>${ios.data.task_id}</td> --%>
   		<td>${ios.data.sent_count}</td>
   		<td>${ios.data.open_count}</td>
   		<td>
   		<c:if test="${ios.data.status==0}">排队中</c:if>
   		<c:if test="${ios.data.status==1}">发送中</c:if>
   		<c:if test="${ios.data.status==2}">发送完成</c:if>
   		<c:if test="${ios.data.status==3}">发送失败</c:if>
   		<c:if test="${ios.data.status==4}">消息被撤销</c:if>
   		<c:if test="${ios.data.status==5}">消息过期</c:if>
   		<c:if test="${ios.data.status==6}">筛选结果为空</c:if>
   		<c:if test="${ios.data.status==7}">定时任务尚未开始处理</c:if>
   		</td>
   	</tr>
   	<tr>
   		<td>Android</td>
   		<%-- <td>${android.data.total_count}</td> --%>
   		<%-- <td>${android.data.task_id}</td> --%>
   		<td>${android.data.sent_count}</td>
   		<td>${android.data.open_count}</td>
   		<td>
   		<c:if test="${android.data.status==0}">排队中</c:if>
   		<c:if test="${android.data.status==1}">发送中</c:if>
   		<c:if test="${android.data.status==2}">发送完成</c:if>
   		<c:if test="${android.data.status==3}">发送失败</c:if>
   		<c:if test="${android.data.status==4}">消息被撤销</c:if>
   		<c:if test="${android.data.status==5}">消息过期</c:if>
   		<c:if test="${android.data.status==6}">筛选结果为空</c:if>
   		<c:if test="${android.data.status==7}">定时任务尚未开始处理</c:if>
   		</td>
   	</tr>
   </table>
   <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">返回</a>
</div>

</body>
</html>