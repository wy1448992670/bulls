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
    <title>用户反馈查询</title>
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
                用户管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>用户反馈列表
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()"> 搜索</button>
                    </div>
                    <div class="widget-content padded clearfix">
                    	<div id="myModal">
	                        <form class="search1 AppFixed form-inline  col-md-6 pull-right col-xs-11" id="form" action="list">
	                            <input type="hidden" name="page" value="1"/>
	
	                            <div class="row">
	                                <div class="form-group col-md-6 col-md-offset-6">
	                                    <div>
	                                        <input class="form-control keyword" name="keyword" type="text"
	                                               placeholder="请输入用户名称或者手机号搜索" value="${keyword }"/>
	                                    </div>
	                                </div>
	
	                            </div>
	                            <div class="row">
	                                <div class="form-group col-md-6">
	                                    <input class="form-control datepicker" value="${startTime }" name="startTime"
	                                           type="text" placeholder="请选择起始时间"/>
	                                </div>
	                                <div class="form-group col-md-6">
	                                    <input class="form-control datepicker" value="${endTime }" name="endTime"
	                                           type="text" placeholder="请选择结束时间"/>
	                                </div>
	                            </div>
	                            <button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="aa()">搜索</button>
								<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
	                            <input type="hidden" name="page" value="${page }"/>
	                        </form>
                        </div>
                        <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                    昵称
                                </th>
                                <th>
                                    真名
                                </th>
                                <th>
                                    手机号
                                </th>
                                <th class="hidden-xs over">
                                    反馈内容
                                </th>
                                <th>
                                    反馈时间
                                </th>
                                <th>
                                    回复时间
                                </th>
                                <th>
                                    回复人
                                </th>
                                <!-- <th class="hidden-xs over">
                                    回复内容
                                </th> -->
                                <th width="120">
                                    操作
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list }">
                                <tr>
                                    <td>
                                        <a href="${basePath}user/detail/app?id=${i.user_id}">${i.username }</a>
                                    </td>
                                    <td>
                                        <a href="${basePath}user/detail/app?id=${i.user_id}">${i.trueName }</a>
                                    </td>
                                    <td>
                                        <shiro:lacksPermission name="user:adminPhone">
                                            ${i.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                        </shiro:lacksPermission>
                                        <shiro:hasPermission name="user:adminPhone">
                                            ${i.phone }
                                        </shiro:hasPermission>
                                    </td>
                                    <td>
                                            ${i.content }
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${i.create_time }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${i.reply_time }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                            ${i.reply_username }
                                    </td>
                                    <td>
                                        <c:if test="${i.reply_time == null}">
                                            <shiro:hasPermission name="feedback:reply">
                                                <a class="btn btn-warning"
                                                   href="${basePath }feedback/reply?id=${i.id}">回复</a>
                                            </shiro:hasPermission>
                                        </c:if>
                                        <c:if test="${i.reply_time != null}">
                                            <shiro:hasPermission name="feedback:detail">
                                                <a href="${basePath}feedback/detail?id=${i.id}">详情</a>
                                            </shiro:hasPermission>
                                            &nbsp;
                                            <shiro:hasPermission name="feedback:revoke">
                                                <a href="javascript:void(0)" onclick="isRevoke(${i.id})">撤销</a>
                                            </shiro:hasPermission>
                                        </c:if>
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
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/comm.js" type="text/javascript"></script>

<script type="text/javascript">
    function isRevoke(id) {
        if (confirm("是否撤销该条反馈信息的回复？")) {
            jQuery.ajax({
                url: "${basePath }feedback/revoke",
                type: "POST",
                data: "id=" + id,
                dataType: "json",
                success: function (data) {
                    if (data.status == 0) {
                        window.location.href = "${basePath}feedback/list";
                    } else {
                        alert("撤回失败！")
                    }
                }
            });
        }
    }
    ;

    function aa() {
        $("#form").submit();
    }
    ;
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });
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
                return "list?keyword=${keyword}&endTime=${endTime}&startTime=${startTime}&page=" + page;
            }
        });

        $(".datepicker").datepicker({
            showSecond: true,
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });
    });

    function aa(){
        $("#form").submit();
    }

</script>
</body>
</html>
