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
    <title>分享列表</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <style type="text/css">

        .table .over {
            overflow: hidden;
            width: 40%;
            text-overflow: ellipsis;
            white-space: nowrap;
        }

        .spinner {
            width: 100px;
        }

        .spinner input {
            text-align: right;
        }

        .input-group-btn-vertical {
            position: relative;
            white-space: nowrap;
            width: 1%;
            vertical-align: middle;
            display: table-cell;
        }

        .input-group-btn-vertical > .btn {
            display: block;
            float: none;
            width: 100%;
            max-width: 100%;
            padding: 8px;
            margin-left: -1px;
            position: relative;
            border-radius: 0;
        }

        .input-group-btn-vertical > .btn:first-child {
            border-top-right-radius: 4px;
        }

        .input-group-btn-vertical > .btn:last-child {
            margin-top: -2px;
            border-bottom-right-radius: 4px;
        }

        .input-group-btn-vertical i {
            position: absolute;
            top: 0;
            left: 4px;
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
                        <i class="icon-table"></i>分享列表
                        <shiro:hasPermission name="share:add">
                        	<a class="btn btn-sm btn-primary-outline pull-right" href="${basePath}share/add" id="add-row"><i class="icon-plus"></i>添加分享</a>
                        </shiro:hasPermission>
                    </div>
                    <div class="widget-content padded clearfix">
	                    <div class="table-responsive">
	                        <table class="table table-bordered table-hover">
	                            <thead>
	                            <tr>
	                                <th style="width:30px;">
	                                    ID
	                                </th>
	                                <th style="width:200px;">
	                                    标题
	                                </th>
	                                <th style="width:70px;">
	                                    图片
	                                </th>
	                                <th style="width:400px;">
	                                    内容
	                                </th>
	                                <td style="width:250px;">
	                                    地址
	                                </td>
	                                <td style="width:150px;">
	                                    更新时间
	                                </td>
	                                <th style="width:100px;">
	                                    状态
	                                </th>
	                                <th width="100">编辑</th>
	                                <%--<th width="100">操作内容</th>--%>
	                            </tr>
	                            </thead>
	                            <tbody>
	                            <c:forEach var="b" items="${list }">
	                                <tr>
	                                    <td>
	                                            ${b.id }
	                                    </td>
	                                    <td style="word-wrap: break-word;">
	                                            ${b.title }
	                                    </td>
	                                    <td>
	                                        <c:if test="${b.path == null }">
	                                            暂无图片
	                                        </c:if>
	                                        <c:if test="${b.path != null }">
	                                            <img alt="" src="${aPath}upload/${b.path}" onload="javascript:DrawImage(this,60,60);" width="60" height="60" />
	                                        </c:if>
	                                    </td>
	                                    <td style="word-wrap: break-word;">
	                                            ${b.context }
	                                    </td>
	                                    <td style="word-wrap: break-word;">
	                                            ${b.link }
	                                    </td>
	                                    <td style="word-wrap: break-word;">
	                                        <%--<fmt:formatDate value="${b.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/>--%>
	                                            ${b.updateDate }
	                                    </td>
	
	
	                                        <%--<td>--%>
	                                        <%--<c:if test="${b.type== 1 }"><span--%>
	                                        <%--class="label label-info">APP</span></c:if>--%>
	                                        <%--<c:if test="${b.type == 3 }"><span--%>
	                                        <%--class="label label-danger">PC</span></c:if>--%>
	                                        <%--<c:if test="${b.type == 4 }"><span--%>
	                                        <%--class="label label-danger">PC</span></c:if>--%>
	                                        <%--</td>--%>
	                                    <td>
	                                        <c:if test="${b.status == 0 }"><span
	                                                class="label label-success">启用</span></c:if>
	                                        <c:if test="${b.status == 1 }"><span
	                                                class="label label-primary">未启用</span></c:if>
	                                    </td>
	                                    <td>
	                                    	<shiro:hasPermission name="share:detail">
		                                        <a class="delete-row" href="${basePath}share/detail?id=${b.id }"
		                                           id="detail">编辑</a>
	                                        </shiro:hasPermission>
	
	                                        <%--<a class="delete-row" href="${basePath}share/delete?id=${b.id }&status=1"--%>
	                                            <%--onClick="return confirm('确定要删除分享吗？');" id="delete">删除</a>--%>
	                                    </td>
	
	                                        <%--<td>--%>
	                                        <%--<c:if test="${b.status == 0 }">--%>
	                                        <%--<a class="delete-row" href="${basePath}share/update?id=${b.id }&status=1"--%>
	                                        <%--onClick="return confirm('确定要弃用此分享吗？');" id="delete">弃用</a>--%>
	                                        <%--</c:if>--%>
	                                        <%--<c:if test="${b.status == 1 }">--%>
	                                        <%--<a class="delete-row" href="${basePath}share/update?id=${b.id }&status=0"--%>
	                                        <%--onClick="return confirm('确定要启用此分享吗？');" id="update">启用</a>--%>
	                                        <%--</c:if>--%>
	                                        <%--</td>--%>
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
<script type="text/javascript">
    $(function () {
        $('.select2able').select2({width: "190"});
        $(".select2able").change(function () {
            $("#form").submit();
        });

        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "list?status=${status}&page=" + page
            }
        });
    });
    (function ($) {
        $('.spinner .btn:first-of-type').on('click', function () {
//            $('.spinner input').val( parseInt($('.spinner input').val(), 10) + 1);\
            var parent = $(this).parents(".spinner");
            parent.find("input").val(parseInt(parent.find("input").val(), 10) + 1);
        });
        $('.spinner .btn:last-of-type').on('click', function () {
//            $('.spinner input').val( parseInt($('.spinner input').val(), 10) - 1);
            var parent = $(this).parents(".spinner");
            if (parent.find("input").val() <= 0) {
                alert("不能小于零");
                return;
            }
            parent.find("input").val(parseInt(parent.find("input").val(), 10) - 1);
        });
    })(jQuery);


    function DrawImage(ImgD, FitWidth, FitHeight) {
        var image = new Image();
        image.src = ImgD.src;
        if (image.width > 0 && image.height > 0) {
            if (image.width / image.height >= FitWidth / FitHeight) {
                if (image.width > FitWidth) {
                    ImgD.width = FitWidth;
                    ImgD.height = (image.height * FitWidth) / image.width;
                } else {
                    ImgD.width = image.width;
                    ImgD.height = image.height;
                }
            } else {
                if (image.height > FitHeight) {
                    ImgD.height = FitHeight;
                    ImgD.width = (image.width * FitHeight) / image.height;
                } else {
                    ImgD.width = image.width;
                    ImgD.height = image.height;
                }
            }
        }
    }
</script>
</body>
</html>
