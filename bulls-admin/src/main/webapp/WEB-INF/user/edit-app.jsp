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
    <title>编辑奔富牧业用户</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/jquery-fileupload/jquery.fileupload.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                奔富牧业用户管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>编辑网站用户
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}user/edit/app" method="post" class="form-horizontal" id="validate-form"
                              enctype="multipart/form-data">
                            <input type="hidden" name="id" value="${user.id }"/>
                            
                            <div class="form-group">
                                <label class="control-label col-md-2">用户姓名</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="trueName" id="trueName" placeholder="" type="text" readonly="readonly"
                                           value="${user.trueName }">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="control-label col-md-2">手机号</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="phone" id="phone" placeholder="" type="text" readonly="readonly"
                                           value="${user.phone }">
                                </div>
                            </div>

                            <!-- <div class="form-group">
                                <label class="control-label col-md-2">可用金额</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="amount" id="amount" placeholder="请输入可用金额"
                                           value="" type="text">
                                </div>
                            </div> -->
                            
							<div class="form-group">
                                <label class="control-label col-md-2">部门</label>
                                
                                <div class="col-md-7">
                                	<select class="select2able" name="departmentId" id="departmentId">
										<option value="">请选择部门</option>
										<c:forEach var="department" items="${departments}">
											<option value="${department.id }" <c:if test="${user.departmentId == department.id }">selected</c:if>>${department.name }</option>
										</c:forEach>
									</select>
								</div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7 text-center">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" type="submit">编辑</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- end DataTables Example -->
    </div>
</div>

<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-fileupload.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.fancybox.pack.js" type="text/javascript"></script>
<script type="text/javascript">

    $(function () {
        $('.select2able').select2();
    });
    
</script>
</body>
</html>