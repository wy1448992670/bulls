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
    <title>双重壕礼</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        .table {
            table-layout: fixed;
        }

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
                活动列表
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>双重壕礼
                    </div>
                    <div class="widget-content padded clearfix">
                        <strong style="font-size: 20px;">
                            总计：散标投资额<label class="label label-success"><fmt:formatNumber value="${rAmount}"
                                                                                         pattern="###,###.##"
                                                                                         type="number"/></label>元，
                            散标人数<label class="label label-warning"><fmt:formatNumber value="${rCount}"
                                                                                     pattern="###,###.##"
                                                                                     type="number"/></label>人，
                            活期投资额<label class="label label-primary"><fmt:formatNumber value="${huoAmount}"
                                                                                      pattern="###,###.##"
                                                                                      type="number"/></label>元，
                            活期人数<label class="label label-info"><fmt:formatNumber value="${huoCount}"
                                                                                  pattern="###,###.##"
                                                                                  type="number"/></label>人，
                            总注册人数<label class="label label-danger"><fmt:formatNumber value="${uCount}"
                                                                                     pattern="###,###.##"
                                                                                     type="number"/></label>人
                        </strong>

                        <form class="form-inline hidden-xs col-lg-4 pull-right" id="form" method="get"
                              action="getDoubleGiftList">
                            <input type="hidden" name="activityId" value="${activityId}">


                            <div class="row">

                                <div class="form-group col-md-6">
                                    <input class="form-control keyword" id="keyword" name="keyword" type="text"
                                           placeholder="请输入用户电话，或者真实姓名,奖品名称" value="${keyword }"/>
                                </div>

                                <%--<div class="form-group col-md-3">--%>
                                <%--<shiro:hasPermission name="user:export:app">--%>
                                <%--<a style="margin-left: 10px;"--%>
                                <%--class="btn btn-sm btn-primary-outline pull-right hidden-xs"--%>
                                <%--href="${basePath}report/getAwardGoldReport?activityId=${activityId}&keyword=${keyword}&startTime=<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>&endTime=<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>"--%>
                                <%--id="add-row"><i class="icon-plus"></i>导出excel</a>--%>
                                <%--</shiro:hasPermission>--%>
                                <%--</div>--%>
                                <div class="form-group col-md-3">
                                    <button class="btn btn-primary pull-right hidden-xs" type="submit"
                                            style="margin-left: 15px;">
                                        查询
                                    </button>
                                </div>
                            </div>


                            <div class="row">
                                <div class="form-group col-md-6">
                                    <input class="form-control datepicker" id="startTime" name="startTime" type="text"
                                           placeholder="请选择活动开始时间"
                                           value="<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>"/>
                                </div>
                                <div class="form-group col-md-6">
                                    <input class="form-control datepicker" id="endTime" name="endTime" type="text"
                                           placeholder="请选择活动结束时间"
                                           value="<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>"/>
                                </div>
                            </div>


                        </form>
                    </div>
                    <div class="widget-content padded clearfix">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th style="width: 50px;">序号</th>
                                <th>用户姓名</th>
                                <th>手机号码</th>
                                <th>项目标题</th>
                                <th>项目类型</th>
                                <th>投资金额</th>
                                <th>项目期限</th>
                                <th>交易时间</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list }" varStatus="s">
                                <tr>
                                    <td>${s.count}</td>
                                    <td>
                                        <a class="delete-row"
                                           href="${basePath}user/detail/app?id=${i.user_id }">${i.true_name}</a>
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
                                            ${i.title }
                                    </td>
                                    <td>
                                        <c:if test="${i.type == 0 }">
                                            散标
                                        </c:if>
                                        <c:if test="${i.type == 1 }">
                                            活期
                                        </c:if>
                                        <c:if test="${i.type == 2 }">
                                            新手标
                                        </c:if>
                                    </td>
                                    <td>
                                            ${i.amount }
                                    </td>
                                    <td>
                                        <c:if test="${i.type != 1 }">
                                            ${i.limit_days }
                                        </c:if>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${i.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
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
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
    function aa() {
        $("#form").submit();
    }
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });
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
                return "getDoubleGiftList?activityId=${activityId}&keyword=${keyword}&startTime=<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>&endTime=<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>&page=" + page;
            }
        });
        var activityOption = {
            placeholder: "请选择活动标题",
            minimumInputLength: 0,
            ajax: {
                url: "${basePath}activity/getActivityTitle",
                dataType: 'json',
                quietMillis: 100,
                data: function (term) {
                    return {
                        username: term, //search term
                    };
                },
                results: function (data) {
                    return {results: data};
                }
            },
            initSelection: function (element, callback) {
                //初始化赋值
                callback({id: '', name: '全部活动'});//调用formatSelection
            },
            formatResult: function (object, container, query) {
                return object.name;
            },
            formatSelection: function (object, container) {
                //选中时触发
                var id = object.id;
                $('#activityId').val(id);
                return object.name;
            },
            escapeMarkup: function (m) {
                return m;
            } // we do not want to escape markup since we are displaying html in results
        };
        $('#activityId').select2(activityOption)

        var giftOption = {
            placeholder: "请选择活动标题",
            minimumInputLength: 0,
            ajax: {
                url: "${basePath}activity/getActivityGiftItems",
                dataType: 'json',
                quietMillis: 100,
                data: function () {
                    return {
                        activityId: $("#activityId").val(), //search term
                    };
                },
                results: function (data) {
                    return {results: data};
                }
            },
            initSelection: function (element, callback) {
                //初始化赋值
                callback({id: '', name: '全部奖品'});//调用formatSelection
            },
            formatResult: function (object, container, query) {
                return object.name;
            },
            formatSelection: function (object, container) {

                //选中时触发
                var id = object.id;
                $('#giftId').val(id);
                return object.name;
            },
            escapeMarkup: function (m) {
                return m;
            } // we do not want to escape markup since we are displaying html in results
        };
        $('#giftId').select2(giftOption);
    });
</script>
</body>
</html>