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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>红包兑换码列表</title>
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
                活动管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>红包兑换码列表
                    </div>
                    <div class="widget-content padded clearfix">

                        <!-- 自适应移动端浏览器 ------------------开始--------------   -->
                        <form class="form-inline hidden-sm hidden-md hidden-lg hidden-print col-lg-3" id="form2"
                              action="hongbaoRedeemList">
                            <div class="row">
                                <div class="form-group hidden-sm hidden-md hidden-lg hidden-print col-md-6">
                                    <div>
                                        <input class="form-control keyword" name="keyword" type="text"
                                               placeholder="请输入关键字" value="${keyword }"/>
                                    </div>
                                </div>

                                <div class="form-group hidden-sm hidden-md hidden-lg hidden-print col-md-3">
                                    <button class="btn btn-primary" type="button" onclick="aa('form2')">
                                        搜索
                                    </button>

                                </div>

                                <div class="form-group hidden-sm hidden-md hidden-lg hidden-print col-md-4">
                                    <a class="btn btn-sm btn-primary-outline" href="hongbaoRedeemDetail"
                                       id="add-row"><i class="icon-plus"></i>新增兑换码</a>

                                </div>
                            </div>
                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                        <!-- 自适应移动端浏览器 ------------------结束开始--------------   -->


                        <!-- 自适应PC浏览器 ------------------开始--------------   -->
                        <form class="form-inline hidden-xs col-lg-3 pull-right" id="form" action="hongbaoRedeemList">
                            <div class="row">
                                <div class="form-group col-md-5">
                                    <div>
                                        <input class="form-control keyword" name="keyword" type="text"
                                               placeholder="请输入关键字" value="${keyword }"/>
                                    </div>
                                </div>

                                <div class="form-group col-md-3">
                                    <button class="btn btn-primary pull-right hidden-xs" type="button"
                                            onclick="aa('form')">
                                        搜索
                                    </button>

                                </div>

                                <div class="form-group col-md-4">
                                    <a class="btn btn-sm btn-primary-outline pull-right" href="hongbaoRedeemDetail"
                                       id="add-row"><i class="icon-plus"></i>新增兑换码</a>

                                </div>
                            </div>
                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                        <!-- 自适应PC浏览器 ------------------结束--------------   -->


                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>ID</th>
                                <th>兑换码主题</th>
                                <th>红包兑换码</th>
                                <th>兑换开始时间</th>
                                <th>兑换结束时间</th>
                                <th>实际兑换时间</th>
                                <th>剩余可兑次数</th>
                                <th>派发人</th>
                                <th>兑换码类型</th>
                                <th>兑换码状态</th>
                                <th>所属活动</th>
                                <th width="80">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list }" varStatus="s">
                                <tr>
                                    <td>${s.count}</td>
                                    <td>${i.id}</td>
                                    <td>${i.title}</td>
                                    <td>${i.redeemCode}</td>
                                    <td>
                                        <fmt:formatDate value="${i.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td><fmt:formatDate value="${i.expireTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td><fmt:formatDate value="${i.useTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td>${i.useCount}</td>
                                    <th>${i.adminName}</th>
                                    <td>
                                        <c:if test="${i.type==0}"> <label class="label label-primary">复用型</label></c:if>
                                        <c:if test="${i.type==1}"> <label class="label label-default">唯一型</label></c:if>
                                    </td>
                                    <td>
                                        <c:if test="${i.status==0}"> <label
                                                class="label label-warning">不可用</label></c:if>
                                        <c:if test="${i.status==1}"> <label
                                                class="label label-success">可用</label></c:if>
                                    </td>
                                    <td>
                                        <c:if test="${i.name==null}"> <label class="label label-info">无限制</label></c:if>
                                        <c:if test="${i.name!=null}"> <label
                                                class="label label-info">${i.name}</label></c:if>
                                    </td>
                                    <td>
                                        <c:if test="${i.useTime == null}">
                                            <a class="btn btn-primary" href="hongbaoRedeemDetail?id=${i.id}">编辑</a>
                                        </c:if>
                                        <c:if test="${i.useTime!=null}">
                                            已兑换
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
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
    function aa(formId) {
        $("#" + formId).submit();
    }
    if (${code!=null and code!=''}) {
        alert("${code}");
    }
    if (${ret==0}) {
        alert("更新失败");
    }
    if (${ret==1}) {
        alert("更新成功");
    }
    $(function () {

        $('.select2able').select2();
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
                return "hongbaoRedeemList?keyword=${keyword}&page=" + page;
            }
        });
    });
</script>
</body>
</html>