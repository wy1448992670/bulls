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
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
    <title>微信推广统计详情</title>
    <style type="text/css">
        .table td, .table th {
            text-align: center;
        }

        .heading label {
            font-size: 18px;
        }
    </style>

</head>
<body>
<div class="modal-shiftfix">

    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <!-- end DataTables Example -->

        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        微信推广统计详情

                    </div>


                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-5 pull-right" id="form"
                              action="${basePath}report/wx/detail">

                            <div class="row">
                                <div class="form-group col-md-7"></div>
                                <div class="form-group col-md-2">
                                    <div>
                                        <button class="btn btn-primary pull-right hidden-xs" type="button"
                                                onclick="search()"> 搜索
                                        </button>
                                    </div>
                                </div>

                                <div class="form-group col-md-3">
                                    <shiro:hasPermission name="report:export:wx">
                                        <a class="btn btn-sm btn-primary-outline pull-right" id=""
                                           href="${basePath}report/export/wx?code=${code}&countInvest=${countInvest}&startTime=<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>&endTime=<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>"><i
                                                class="icon-plus"></i>导出Excel</a>
                                    </shiro:hasPermission>

                                </div>
                            </div>


                            <div class="row">


                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control datepicker"
                                               value="<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>"
                                               id="startTime" name="startTime" type="text" placeholder="请选择注册起始时间"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control datepicker"
                                               value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd"/>"
                                               id="endTime" name="endTime" type="text" placeholder="请选择注册结束时间"/>
                                    </div>
                                </div>
                            </div>


                            <div class="row">

                                <div class="form-group col-md-3">
                                    <div>
                                        <select class="select2able" name="countInvest" onchange="">
                                            <option value="">筛选条件</option>
                                            <option value="0" <c:if test="${countInvest == 0 }">selected</c:if>>累计投资</option>
                                            <option value="1" <c:if test="${countInvest == 1 }">selected</c:if>>在投</option>
                                            <option value="2" <c:if test="${countInvest == 2 }">selected</c:if>>复投</option>
                                            <option value="3" <c:if test="${countInvest == 3 }">selected</c:if>>首投</option>
                                        </select>
                                    </div>
                                </div>
                                <c:if test="${countInvest ==0}">
                                    <div class="form-group col-md-3">
                                        <div>
                                            <select class="select2able" name="type" onchange="isUserable()">
                                                <option value="">筛选条件</option>
                                                <option value="0" <c:if test="${type == 0 }">selected</c:if>>未投资</option>
                                                <option value="3" <c:if test="${type == 3 }">selected</c:if>>已投资</option>
                                            </select>
                                        </div>
                                    </div>
                                </c:if>


                                <div class="form-group col-md-3">
                                    <div>
                                        <select class="select2able" name="source">
                                            <option value="">客户来源</option>
                                            <option value="0" <c:if test="${source == 0 }">selected</c:if>>IOSAPP</option>
                                            <option value="1" <c:if test="${source == 1 }">selected</c:if>>IOS微信</option>
                                            <option value="2" <c:if test="${source == 2 }">selected</c:if>>安卓APP </option>
                                            <option value="3" <c:if test="${source == 3 }">selected</c:if>>安卓微信</option>
                                            <option value="4" <c:if test="${source == 4 }">selected</c:if>>PC网站</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <input type="hidden" name="code" id="code" value="${code}"/>
                        </form>
                        <table class="table table-bordered table-hover" id="allCapitalDetail">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>手机号</th>
                                <th>渠道识别</th>
                                <th>注册时间</th>
                                <th>真实姓名</th>
                                <th>昵称</th>
                                <th>充值金额</th>
                                <th>提现金额</th>
                                <th>账户余额</th>
                                <th>活期金额</th>
                                <c:if test="${countInvest ==3 }">
                                    <th>投资时间</th>
                                </c:if>
                                <th>散标14天</th>
                                <th>散标30天</th>
                                <th>散标90天</th>
                                <th>散标180天</th>
                                <th>散标365天</th>
                                <th>债权转让</th>
                                <c:if test="${countInvest == 0 or  countInvest == 3 or countInvest == 1 }">
                                    <th>新手标7天</th>
                                    <th>新手标15天</th>
                                    <th>新手标28天</th>
                                </c:if>
                                <c:if test="${countInvest == 0 or  countInvest == 3 or countInvest == 1 }">
                                    <th>安心赚</th>
                                    <c:if test="${countInvest == 0 or  countInvest == 1 }">
                                        <th>安心赚自动</th>
                                    </c:if>
                                </c:if>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${list}" var="l" varStatus="st">
                                <tr>
                                    <td>${st.index+1 }</td>
                                    <td><a href="${basePath }user/detail/app?id=${l.id2}">
                                    <shiro:lacksPermission name="user:adminPhone">
                                        ${l.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                    </shiro:lacksPermission>
                                    <shiro:hasPermission name="user:adminPhone">
                                        ${l.phone }
                                    </shiro:hasPermission>
                                    </a></td>
                                    <td>${l.params }</td>
                                    <td><fmt:formatDate value="${l.register_time}" pattern="yyyy-MM-dd"/></td>
                                    <td><a href="${basePath }user/detail/app?id=${l.id2}">${l.true_name }</a></td>
                                    <td><a href="${basePath }user/detail/app?id=${l.id2}">${l.username }</a></td>
                                    <td>${l.chongzhi }</td>
                                    <td>${l.tixian }</td>
                                    <td>${l.available_balance }</td>
                                    <td> <fmt:formatNumber value="${l.huoqi }" pattern="#.##" type="number"/> </td>
                                    <c:if test="${countInvest ==3 }">
                                        <td>${l.time }</td>
                                    </c:if>

                                    <td><fmt:formatNumber value="${l.amount_14 }" pattern="#.##" type="number"/>  </td>
                                    <td><fmt:formatNumber value="${l.amount_30 }" pattern="#.##" type="number"/> </td>
                                    <td><fmt:formatNumber value=" ${l.amount_90 }" pattern="#.##" type="number"/></td>
                                    <td><fmt:formatNumber value="${l.amount_180 }" pattern="#.##" type="number"/> </td>
                                    <td><fmt:formatNumber value="${l.amount_365 }" pattern="#.##" type="number"/> </td>
                                    <c:if test="${countInvest == 0 or countInvest == 1 }">
                                        <td>${l.zhaiquan1 + l.zhaiquan2 }</td>
                                    </c:if>
                                    <c:if test="${countInvest == 2 or countInvest == 3 }">
                                        <td><fmt:formatNumber value="${l.zhaiquan}" pattern="#.##" type="number"/> </td>
                                    </c:if>

                                    <c:if test="${countInvest == 0 or  countInvest == 3 or countInvest == 1 }">
                                        <td>${l.amount_7 }</td>
                                        <td>${l.amount_15 }</td>
                                        <td>${l.amount_28 }</td>
                                    </c:if>

                                    <c:if test="${countInvest == 0 or  countInvest == 3 or countInvest == 1 }">
                                        <th><fmt:formatNumber value="${l.amount_huanle }" pattern="#.##" type="number"/></th>
                                        <c:if test="${countInvest == 0 or  countInvest == 1 }">
                                            <th><fmt:formatNumber value="${l.amount_auto }" pattern="#.##" type="number"/></th>
                                        </c:if>
                                    </c:if>

                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div style="font-size: 17px; color: #00a0e9">
                            总人数：${count}人；
                        </div>
                        <ul id="pagination" style="float: right"></ul>

                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
    function search() {
        $("#form").submit();
    }
    $(function () {
        $(".select2able").change(function () {
            $("#form").submit();
        });
        var code = $('#code').val();
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "detail?page=" + page + "&code=" + code + '&source=${source}&countInvest=${countInvest}&type=${type}&investType=${investType}&endTime=<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>&startTime=<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>';
            }
        });

        $('.select2able').select2({width: "150"});
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd',
            showSecond: true,
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });
    });


    function isUserable() {
//        var flag = $('select[name="type"]').val();
//        if (flag == 3) {
//            $('select[name="investType"]').attr("disabled", false);
//
//        } else {
//            $('select[name="investType"]').attr("disabled", true);
//
//        }
    }
    isUserable();


</script>
</body>
</html>
