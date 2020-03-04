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
    <title>项目投资</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        .upload-picture a {
            display: inline-block;
            overflow: hidden;
            border: 0;
            vertical-align: top;
            margin: 0 5px 10px 0;
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
                项目管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-6">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>项目投资
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="invest" method="post" class="form-horizontal" id="validate-form"
                              onsubmit="return checkAmount();">
                            <input type="hidden" name="projectId" value="${project.id }"/>
                            <input type="hidden" name="projectType" id="projectType" value="${project.projectType }"/>

                            <div class="form-group">
                                <label class="control-label col-md-2">项目名称</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${project.title }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">投资人数</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${project.investorsNum } 人
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">年化收益</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <fmt:formatNumber value="${project.annualized }" type="percent"
                                                          maxFractionDigits="3" groupingUsed="false"/>
                                    </p>
                                </div>
                            </div>
                            <c:if test="${project.projectType ==0 }">
                                <div class="form-group">
                                    <label class="control-label col-md-2">真实投资金额</label>

                                    <div class="col-md-7">
                                        <p class="form-control-static">
                                            <c:if test="${project.trueAmount==null }">
                                                0元 </c:if>
                                            <c:if test="${project.trueAmount!=null }">
                                                ${project.trueAmount }元</c:if>

                                        </p>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${project.projectType ==1 }">
                                <div class="form-group">
                                    <label class="control-label col-md-2">真实投资金额</label>

                                    <div class="col-md-7">
                                        <p class="form-control-static">
                                            <c:if test="${project.trueAmount==null }">
                                                0元 </c:if>
                                            <c:if test="${project.trueAmount!=null }">
                                                ${project.trueAmount }元</c:if>

                                        </p>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${project.projectType ==2 }">
                                <div class="form-group">
                                    <label class="control-label col-md-2">真实投资金额</label>

                                    <div class="col-md-7">
                                        <p class="form-control-static">
                                            <c:if test="${project.trueAmount==null }">
                                                0元</c:if>
                                            <c:if test="${project.trueAmount!=null }">
                                                ${project.trueAmount }元</c:if>

                                        </p>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${project.projectType!=2 }">
                                <div class="form-group">
                                    <label class="control-label col-md-2">投资截至时间</label>

                                    <div class="col-md-7">
                                        <p class="form-control-static">
                                            <fmt:formatDate value="${project.deadline }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </p>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-md-2">还款期限</label>

                                    <div class="col-md-7">
                                        <p class="form-control-static">
                                                ${project.limitDays } 天
                                        </p>
                                    </div>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label class="control-label col-md-2">总融资金额</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${project.totalAmount / 10000 }万
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">融资进度</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <fmt:formatNumber value="${project.investedAmount/project.totalAmount }"
                                                          maxFractionDigits="2" type="percent"/>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">已融资金额</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${project.investedAmount>=10000?project.investedAmount/10000:project.investedAmount }
                                        <c:if test="${project.investedAmount>=10000 }">万</c:if>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">剩余可投资金额</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <c:if test="${(project.totalAmount - project.investedAmount)<10000 }">
                                            <fmt:formatNumber value="${project.totalAmount - project.investedAmount}"
                                                              maxFractionDigits="2"/>
                                        </c:if>
                                        <c:if test="${(project.totalAmount - project.investedAmount)>=10000 }">
                                            ${(project.totalAmount - project.investedAmount)/10000 }万
                                        </c:if>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">用户列表</label>

                                <div class="col-md-7">
                                    <c:forEach items="${list }" var="user" varStatus="s">
                                        <label class="radio-inline"
                                                <c:forEach items="${ids }" var="id">
                                                    <c:if test="${id == user.id }">style="color:red;"</c:if>
                                                </c:forEach>>
                                            <input name="userId" type="radio"
                                                   value="${user.id }" ${s.first ? 'checked' : ''}/>
                                            <span></span>${user.username }（可用余额：${user.availableBalance>=10000?user.availableBalance/10000:user.availableBalance }
                                            <c:if test="${user.availableBalance>=10000 }">万</c:if>）

                                        </label>
                                    </c:forEach>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">投资金额</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="amount" id="amount" type="text" value="">
                                    <label class="errors" style="color:red;"></label>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-9 label label-danger" style="width: 280px;">提示：用户名称红色表示已经投资过该项目</label>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7 text-center">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" type="submit">立刻投资</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div class="col-lg-6">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>项目投资信息
                    </div>
                    <div class="widget-content padded clearfix">
                        <table class="table" id="investors">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>投资用户</th>
                                <th>投资金额</th>
                                <th>投资时间</th>
                                <th>投资方式</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                        <div class="text-right">
                            <ul id="pagination"></ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- end DataTables Example -->
    </div>
</div>
<script src="${basePath}js/bootstrap-paginator.min.js"></script>
<script type="text/javascript">
    $(function () {

        $('#amount').keyup(function () {
            checkAmount();
        });

        showInvestors(1);
    });


    function checkAmount() {
        var amount = $.trim($('#amount').val());
        if ('${project.projectType}' == 0) {
            var ava = ${project.totalAmount-project.investedAmount };
            if ('${project.noob}' == 0) {
                //正常定期
                if (!/^[1-9]\d*00$/.test(amount)) {
                    $('.errors').text('投资金额必须是100的整数倍').show();
                    return false;
                }
                if (amount < 100 || amount > ava) {
                    $('.errors').text('投资金额必须在100-' + ava + '之间').show();
                    return false;
                }
                $('.errors').hide();
                return true;
            } else {
                //新手标
                var r = /^[0-9]*[1-9][0-9]*$/;
                if (!r.test(amount)) {
                    alert('必须是正整数');
                    return false;
                }
                if (ava - amount < 50) {
                    return true;
                } else {
                    if (amount < 50 || amount > ava) {
                        $('.errors').text('投资金额必须在50-' + ava + '之间').show();
                        return false;
                    }
                }
                $('.errors').hide();
                return true;
            }
        } else {
            var ava = ${project.totalAmount-project.investedAmount };
            //var ava = parseInt('<fmt:formatNumber value="${project.totalAmount-project.investedAmount }" groupingUsed="false" />');
            if (amount > ava) {
                $('.errors').text('投资金额必须小于' + ava).show();
                return false;
            }
            $('.errors').hide();
            return true;
        }
    }

    function showInvestors(page) {
        var projectId = ${project.id};

        $.ajax({
            url: "${basePath}project/investors",
            type: "get",
            dataType: "json",
            data: "page=" + page + "&projectId=" + projectId,
            success: function (obj) {
                $("#investors tbody").empty();
                for (var i = 0; i < obj.investors.length; i++) {
                    var invest = obj.investors[i];
                    var terminal = "安卓版";
                    if (invest.terminal == 0) {
                        terminal = '网页版';
                    } else if (invest.terminal == 1) {
                        terminal = '安卓版';
                    } else if (invest.terminal == 2) {
                        terminal = 'IOS版';
                    } else if (invest.terminal == 3) {
                        terminal = 'WAP版';
                    }
                    var tr = "<tr><td>" + (i + 1) + "</td><td>" + invest.username + "</td><td>" + invest.amount + "</td><td>" + invest.time + "</td><td>" + terminal + "</td></tr>";
                    $("#investors tbody").append(tr);
                }
                if (obj.pages > 0) {
                    $('#pagination').bootstrapPaginator({
                        currentPage: parseInt(obj.page),
                        totalPages: parseInt(obj.pages),
                        bootstrapMajorVersion: 3,
                        alignment: "right",
                        pageUrl: function (type, page, current, limit) {
                            return "javascript:showInvestors(" + page + ")";
                        }
                    });
                }
            }
        });
    }
</script>
</body>
</html>