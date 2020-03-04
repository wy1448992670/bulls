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
    <title>管理员操作日志管理</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
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
                        <i class="icon-table"></i>管理员操作日志列表
                        <img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;"/">
						<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id=""
                           href="${basePath}log/export/admin/log?keyWord=${keyWord}&startTime=${startTime}&endTime=${endTime}&lvl=${lvl}"><i
                                investTyp class="icon-plus"></i>导出Excel</a>
                        <button class="btn btn-primary hidden-xs pull-right" type="button" onclick="aa()"> 搜索</button>
                    </div>
                    <div class="widget-content padded clearfix">
                    <div id="myModal">
                        <form class="search1 AppFixed form-inline col-lg-5 pull-right col-xs-11" id="form" action="admin">
                        	<input type="hidden" name="page" value="1"/>
                        
                            <div class="row">
                            	<div class="form-group col-md-5"></div>
                                <div class="form-group col-md-4">
                                    <div>
                                        <input class="form-control keyword" name="keyWord" type="text"
                                               placeholder="请输入管理员用户名搜索" value="${keyWord }"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-3">
                                    <div>
                                        <select class="select2able" name="lvl">
                                            <option value="" <c:if test="${lvl == null }">selected</c:if>>操作级别</option>
                                            <option value="0" <c:if test="${lvl == 0 }">selected</c:if>>重要</option>
                                            <option value="1" <c:if test="${lvl == 1 }">selected</c:if>>一般</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                            	<div class="form-group col-md-4"></div>
                                <div class="form-group col-md-4">
                                    <input class="form-control datepicker" value="${startTime }" name="startTime"
                                           type="text" placeholder="请选择起始时间"/>
                                </div>
                                <div class="form-group col-md-4">
                                    <input class="form-control datepicker" value="${endTime }" name="endTime"
                                           type="text" placeholder="请选择结束时间"/>
                                </div>
                            </div>
                            <input type="hidden" name="page" value="1"/>
                            <button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="aa()">搜索</button>
							<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
                        </form>
                    </div>
                        <div class="table-responsive">
	                        <table class="table table-bordered table-hover">
	                            <thead>
	                            <tr>
	                                <th>
	                                    日志ID
	                                </th>
	                                <th>
	                                    管理员ID
	                                </th>
	                                <th>
	                                    管理员名
	                                </th>
	                                <th>
	                                    操作时Ip
	                                </th>
	                                <th>
	                                    操作时间
	                                </th>
	                                <th>
	                                    操作内容
	                                </th>
	                                <th>
	                                    操作级别
	                                </th>
	                            </tr>
	                            </thead>
	                            <tbody>
	                            <c:forEach var="i" items="${list }">
	                                <tr>
	                                    <td>
	                                            ${i.id }
	                                    </td>
	                                    <td>
	                                            ${i.adminId }
	                                    </td>
	                                    <td>
	                                            ${i.adminUserName }
	                                    </td>
	                                    <td>
	                                            ${i.adminIp }
	                                    <td>
	                                        <fmt:formatDate value="${i.operateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
	                                    </td>
	                                    <td>
	                                            ${i.remark}
	                                    </td>
	                                    <td>
	                                        <c:if test="${i.lvl == 0 }"><span class="label label-warning">重要</span></c:if>
	                                        <c:if test="${i.lvl == 1 }"><span class="label label-success">一般</span></c:if>
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
        <!-- end DataTables Example -->
    </div>
</div>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/comm.js" type="text/javascript"></script>
<script type="text/javascript">
    function aa() {
        $("#form").submit();
    }

    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });

        $('.select2able').select2({width: ""});
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "admin?keyWord=${keyWord}&lvl=${lvl}&endTime=${endTime}&startTime=${startTime}&page=" + page;
            }
        });
    });
</script>
</body>
</html>