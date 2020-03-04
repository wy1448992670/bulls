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
    <title>抽奖活动列表</title>
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
                活动管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>抽奖活动列表

                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-4 pull-right" id="formCount"
                              action="selectChrismasLotteryCount">
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <input class="form-control keyword" id="phone" name="phone" type="text"
                                           placeholder="请输入用户电话" value="${phone }"/>
                                </div>
                                <div class="form-group col-md-6">
                                    <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="url()">
                                        查询抽奖次数
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-4 pull-right" id="form" action="chrismasList">
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <input class="form-control keyword" id="account" name="account" type="text"
                                           placeholder="请输入用户ID、电话、姓名搜索" value="${account }"/>
                                </div>
                                <div class="form-group col-md-6">
                                    <select class="select2able" name="status" id="status">
                                        <option value="">请选择奖品状态</option>
                                        <option value="0" <c:if test="${status == 0 }">selected</c:if>>未中奖</option>
                                        <option value="1" <c:if test="${status == 1 }">selected</c:if>>未发放</option>
                                        <option value="2" <c:if test="${status == 2 }">selected</c:if>>已发放</option>
                                        <option value="3" <c:if test="${status == 3 }">selected</c:if>>用户收货</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <input id="activityId" name="activityId" type="hidden" placeholder="活动标题"
                                           value="${activityId }"/>
                                </div>
                                <div class="form-group col-md-6">
                                    <input id="giftId" name="giftId" type="hidden" placeholder="选择奖品"
                                           value="${giftId }"/>
                                    <%--<select class="select2able" name="giftId" id="giftId">
                                        <option value="" >请选择奖品</option>
                                        <option value="4" <c:if test="${giftId == 4 }">selected</c:if>>0.5日加息券</option>
                                        <option value="10" <c:if test="${giftId == 10 }">selected</c:if>>1.0日加息券</option>
                                        <option value="3" <c:if test="${giftId == 3 }">selected</c:if>>体验金</option>
                                        <option value="5" <c:if test="${giftId == 5 }">selected</c:if>>小米手环</option>
                                        <option value="6" <c:if test="${giftId == 6 }">selected</c:if>>小米充电宝</option>
                                        <option value="7" <c:if test="${giftId == 7 }">selected</c:if>>华为荣耀手环</option>
                                        <option value="8" <c:if test="${giftId == 8 }">selected</c:if>>马克杯</option>
                                    </select>--%>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <input class="form-control datepicker"
                                           value="<fmt:formatDate value="${date }" pattern="yyyy-MM-dd"/>" id="date"
                                           name="date" type="text" placeholder="请选择中奖日期搜索"/>
                                </div>
                                <div class="form-group col-md-6">
                                    <input class="form-control keyword" id="orderId" name="orderId" type="text"
                                           placeholder="请输入奖品单号搜索" value="${orderId }"/>
                                </div>

                            </div>
                            <div class="row">
                                <div class="form-group col-md-6">
                                </div>
                                <div class="form-group col-md-6">
                                    <input type="hidden" name="page" value="${page }"/>
                                    <shiro:hasPermission name="activity:all">
                                        <a class="btn btn-sm btn-primary-outline pull-right"
                                           href="${basePath}activity/chrismasListExport?status=${status}&date=<fmt:formatDate value="${date }" pattern="yyyy-MM-dd"/>&orderId=${orderId}&account=${account}&activityId=${activityId}&giftId=${giftId}"><i
                                                class="icon-plus"></i>导出Excel</a>
                                    </shiro:hasPermission>
                                    <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()">
                                        搜索
                                    </button>
                                </div>
                            </div>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>奖品单号</th>
                                <th>奖品名称</th>
                                <th>中奖账号</th>
                                <th>中奖人电话</th>
                                <th>中奖时间</th>
                                <th>奖品状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list }" varStatus="s">
                                <tr>
                                    <td>${s.index+1}</td>
                                    <td>${i.id}</td>
                                    <td>${i.name }</td>
                                    <td>
                                        <c:if test="${i.loginId != null }">
                                            <c:if test="${i.true_name != null }">${i.true_name }</c:if>
                                            <c:if test="${i.true_name == null }">${i.username }</c:if>
                                        </c:if>
                                        <c:if test="${i.loginId == null }">${i.loginPhone }</c:if>
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
                                        <fmt:formatDate value="${i.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <c:if test="${i.status == 0 }">未中奖</c:if>
                                        <c:if test="${i.status == 1 }">未发放</c:if>
                                        <c:if test="${i.status == 2 }">已发放</c:if>
                                        <c:if test="${i.status == 3 }">用户收货</c:if>
                                    </td>
                                    <td>
                                        <a class="delete-row"
                                           href="${basePath}activity/editChrismasLottery?id=${i.id }">编辑</a>
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
    function url() {
//			var phone = $('#phone').val();
        /*window.location.href=
        ${basePath}+"activity/selectChrismasLotteryCount?phone="+phone;*/
        $("#formCount").submit();
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
                return "chrismasList?status=${status}&date=<fmt:formatDate value="${date }" pattern="yyyy-MM-dd"/>&orderId=${orderId}&account=${account}&activityId=${activityId}&giftId=${giftId}&page=" + page;
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