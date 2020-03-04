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
    <title>管理用户列表</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
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
                后台用户管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>后台用户列表
                        <img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;"/">
                        <a class="btn btn-sm btn-primary-outline pull-right  hidden-xs" href="add" id="add-row"><i class="icon-plus"></i>添加用户</a>
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()"> 搜索</button>
                    </div>
                    <div class="widget-content padded clearfix">
                    	<div id="myModal">
	                        <form class="search1 AppFixed form-inline  col-md-6 pull-right col-xs-11" id="form" action="list">
	                            <div class="form-group col-md-6">
	                                <div>
	                                    <input class="form-control keyword" name="keyword" type="text"
	                                           placeholder="请输入用户名搜索" value="${keyword }"/>
	                                </div>
	                            </div>
	                            <div class="form-group col-md-3">
	                                <div>
	                                    <select class="select2able" name="status">
	                                        <option value="" <c:if test="${status == null }">selected</c:if>>所有</option>
	                                        <option value="0" <c:if test="${status == 0 }">selected</c:if>>可用</option>
	                                        <option value="1" <c:if test="${status == 1 }">selected</c:if>>不可用</option>
	                                        <option value="2" <c:if test="${status == 2 }">selected</c:if>>已删除</option>
	                                    </select>
	                                </div>
	                            </div>
                                <div class="form-group col-md-3">
                                    <div>
                                        <select class="select2able" name="roleId">
                                            <option value="" <c:if test="${roleId == null }">selected</c:if>>用户角色</option>
                                            <c:forEach var="item" items="${roles}">
                                                <option value="${item.id }" <c:if test="${roleId == item.id }">selected</c:if>>${item.description }</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
								<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="search()">搜索</button>
								<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
	                            <input type="hidden" name="page" value="1"/>
	                        </form>
                        </div>
                       
                        <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                    用户名
                                </th>
                                <th>
                                    真名
                                </th>
                                <th>
                                    性别
                                </th>
                                <th>
                                    创建时间
                                </th>
                                <th>
                                    创建IP
                                </th>
                                <th>
                                    状态
                                </th>
                                <th>
                                    用户角色
                                </th>
                                <th>
                                    对应部门
                                </th>
                                <shiro:hasPermission name="user:edit">
                                    <th width="60"></th>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="user:detail">
                                    <th width="75"></th>
                                </shiro:hasPermission>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="user" items="${users }">
                                <tr>
                                    <td>
                                            ${user.username }
                                    </td>
                                    <td>
                                            ${user.trueName }
                                    </td>
                                    <td>
                                        <c:if test="${user.sex == 0 }">
                                            女
                                        </c:if>
                                        <c:if test="${user.sex == 1 }">
                                            男
                                        </c:if>
                                        <c:if test="${user.sex == 2 }">
                                            保密
                                        </c:if>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${user.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                            ${user.createIp }
                                    </td>
                                    <td>
                                        <c:if test="${user.status == 0 }">
                                            <span class="label label-success">可用</span>
                                        </c:if>
                                        <c:if test="${user.status == 1 }">
                                            <span class="label label-warning">不可用</span>
                                        </c:if>
                                        <c:if test="${user.status == 2 }">
                                            <span class="label label-danger">已删除</span>
                                        </c:if>
                                    </td>
                                    <td>
                                            ${user.roleList.get(0).roleName }
                                    </td>
                                    <td>
                                    	<c:if test="${user.departmentId == 0 }">公司</c:if>
                                    	<c:if test="${user.departmentId == 1 }">新客</c:if>
                                    	<c:if test="${user.departmentId == 2 }">亿亿</c:if>
                                    	<c:if test="${user.departmentId == 3 }">分公司</c:if>
                                    	<c:if test="${user.departmentId == 4 }">宁德</c:if>
                                    	<c:if test="${user.departmentId == 5 }">温州</c:if>
                                    	<c:if test="${user.departmentId == 6 }">枣庄</c:if>
                                    	<c:if test="${user.departmentId == 7 }">亿亿-分公司</c:if>
                                    </td>
                                    <shiro:hasPermission name="user:edit">
                                        <td>
                                            <a class="edit-row" href="edit?id=${user.id }">编辑</a>
                                        </td>
                                    </shiro:hasPermission>
                                    <shiro:hasPermission name="user:detail">
                                        <td>
                                            <a class="delete-row" href="detail?id=${user.id }" id="delete">详细</a>
                                        </td>
                                    </shiro:hasPermission>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        </div>
                        <ul id="pagination" style="float: right"></ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- end DataTables Example -->
    </div>
</div>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/comm.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $('.select2able').select2({width: "150"});
        $(".keyword").keyup(function (e) {
            e = e || window.e;
            if (e.keyCode == 13) {
                $("#form").submit();
            }
        });
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "list?keyword=${keyword}&status=${status}&page=" + page;
            }
        });
    });

    function search() {

    	console.log($("#form").serialize());
    	 $("#form").submit();
    };
</script>
</body>
</html>
