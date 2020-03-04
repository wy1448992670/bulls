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
    <title>字典列表</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        .table .over {
            overflow: hidden;
            width: 40%;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
    </style>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                运营管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>字典列表
                        <img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;"/">

                        <shiro:hasPermission name="activity:add">
                            <a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id=""
                               href="${basePath}dict/add"><i class="icon-plus"></i>新增字典</a>
                        </shiro:hasPermission>
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()"> 刷新缓存</button>
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="bb()"> 搜索</button>
                    </div>
					<div class="widget-content padded clearfix">
						<div id="myModal">
		                    <form class="search1 AppFixed form-inline col-lg-4 pull-right col-xs-11" id="form" action="list">
		                    	<input type="hidden" name="page" value="1"/>
		                    	
		                    	<div class="row">
		                       		<div class="form-group col-md-4"></div>
									<div class="form-group col-md-4">
										<input name="key" type="text" placeholder="请输入tKey搜索" class="form-control keyword" value="${key}" id="key"/>
		                            </div>
									<div class="form-group col-md-4">
										<input name="name" type="text" placeholder="请输入名称搜索" class="form-control keyword" value="${name }" id="name"/>
									</div>
								</div>
								<input type="hidden" name="page" value="1"/>
								<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="bb()">搜索</button>
								<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
		                    </form>
		                </div>
	
	                    <div class="widget-content padded clearfix">
		                    <div class="table-responsive">
		                        <table class="table table-bordered table-hover">
		                            <thead>
		                            <tr>
		                                <th width="50">
		                                    id
		                                </th>
		                                <th>
		                                    名称
		                                </th>
		                                <th>
		                                    key
		                                </th>
		                                <th>
		                                    value
		                                </th>
		                                <th width="50">
		                                    排序
		                                </th>
		                                <th>
		                                    memo
		                                </th>
		                                <th width="80">
		                                    详情
		                                </th>
		                            </tr>
		                            </thead>
		
		                            <tbody>
		                            <c:forEach var="i" items="${list }" varStatus="s">
		                                <tr>
		                                    <td>
		                                            ${i.id }
		                                    </td>
		                                    <td>
		                                            ${i.tName}
		                                    </td>
		                                    <td>
		                                            ${i.tKey }
		                                    </td>
		                                    <td>
		                                            ${i.tValue}
		                                    </td>
		                                    <td>
		                                            ${i.tSort }
		                                    </td>
		                                    <td>
		                                            ${i.memo}
		                                    </td>
		                                    <td>
		                                        <shiro:hasPermission name="dict:add">
		                                            <a class="delete-row" href="${basePath}dict/add?id=${i.id }">编辑</a>&nbsp;
		                                        </shiro:hasPermission>
		                                    </td>
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
        </div>
        <!-- end DataTables Example -->
    </div>
</div>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/comm.js" type="text/javascript"></script>
<script type="text/javascript">
    function bb() {
        $("#form").submit();
    }
    function aa() {
        $.ajax({
            url: '${basePath}dict/reflush',
            dataType: "json",
            success: function (obj) {
                if (obj == true) {
                    alert("刷新成功");
                } else {
                    alert("刷新失败");
                }
            },
            error: function (obj) {
                alert("对不起，系统繁忙");
            }
        });
    }
    $(function () {
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "list?name=${name}&key=${key}&page=" + page;
            }
        });

    });
</script>
</body>
</html>