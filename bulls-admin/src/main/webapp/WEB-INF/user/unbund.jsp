<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>解绑银行卡</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                网站用户管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>解绑银行卡
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="unbund" class="form-horizontal" id="form">
                            <div class="form-group">
                                <label class="control-label col-md-2">用户名</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${user.username }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">真实姓名</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <c:choose>
                                            <c:when test="${empty user.trueName  }"><span class="label label-danger">此用户还没有进行实名化</span></c:when>
                                            <c:otherwise>${user.trueName }</c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">身份证号</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <c:choose>
                                            <c:when test="${empty user.identityCard }"><span class="label label-danger">此用户还没有进行实名化</span></c:when>
                                            <c:otherwise>${user.identityCard}</c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">手机号</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <shiro:lacksPermission name="user:adminPhone">
                                            ${user.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                        </shiro:lacksPermission>
                                        <shiro:hasPermission name="user:adminPhone">
                                            ${user.phone }
                                        </shiro:hasPermission>
                                    </p>
                                </div>
                            </div>
                            <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>卡ID</th>
								<th>预留手机号</th>
								<th>银行</th>
								<th>卡号</th>
								<th>协议号码</th>
								<th>绑卡时间</th>
                                <th width="80">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${bankCards }" varStatus="s">
                                <tr>
                                    <td>${s.index+1}</td> 
                                    <td>
                                    	<shiro:lacksPermission name="user:adminPhone">
                                            ${i.phone.replaceAll("(\\d{6})\\d{5}(\\w{4})","$1*****$2")}
                                        </shiro:lacksPermission>
                                        <shiro:hasPermission name="user:adminPhone">
                                            ${i.phone }
                                        </shiro:hasPermission>
                                   	</td>
                                    <td>${i.bank.name }</td>
                                    <td>${i.cardNumber} </td>
                                    <td>${i.protoColNo} </td>
                                    <td><fmt:formatDate value="${i.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                     <td>
                                        <a class="btn btn-primary" data-loading-text="正在解绑..."  onclick="unbund(${i.id});"
                                                id="unbund">确定解绑</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- end DataTables Example -->
    </div>
</div>

<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
   
    function unbund(bankCardId){
    	 if (confirm("您确定给该用户银行卡解绑吗?!")) {
             $.ajax({
                 url: '${basePath}user/unbund',
                 data: {
                     "userId": '${user.id}',
                     "bankCardId": bankCardId 
                 },
                 type: "POST",
                 dataType: "json",
                 beforeSend: function () {
                     $("#send").button('loading');
                 },
                 success: function (data) {
                     alert(data); 
                     window.location.reload();
                 }
             });
         }
    }
</script>
</body>
</html>