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
    <title>奔富牧业用户列表</title>
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
                奔富牧业用户管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>鑫聚财用户列表
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="bb()"> 搜索</button>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-md-6 pull-right" id="form"
                              action="${basePath}user/list/app/customer">
                            <div class="row">
                                <div class="form-group col-md-12">
                                    <div>
                                        <input name="id" type="text" placeholder="请输入用户ID"
                                               class="form-control keyword" value="${id }"/>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>
                                        ID
                                    </th>
                                    <th>
                                        用户名
                                    </th>
                                    <th>
                                        真实姓名
                                    </th>
                                    <th>
                                        账户余额
                                    </th>

                                    <th>
                                        活期投资
                                    </th>
                                    <th>
                                        散标投资
                                    </th>
                                    <th>
                                        手机号
                                    </th>
                                    <th>
                                        性别
                                    </th>
                                    <th>
                                        注册时间
                                    </th>
                                    <th>
                                        注册IP
                                    </th>

                                    <th>
                                        状态
                                    </th>
                                    <th width="75"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${!empty userCustomer}">
                                    <tr>
                                        <td>
                                                ${userCustomer.id }
                                        </td>
                                        <td>
                                            <c:if test="${userCustomer.level == 0}"><span class="label label-default">V0</span></c:if>
                                            <c:if test="${userCustomer.level == 1}"><span class="label label-danger">V1</span></c:if>
                                                ${userCustomer.username }
                                        </td>
                                        <td>
                                                ${userCustomer.trueName }
                                        </td>
                                        <td>
                                                ${userCustomer.assets.availableBalance}
                                        </td>
                                        <td>
                                                ${userCustomer.assets.huoInvestmentAmount}
                                        </td>
                                        <td>
                                                ${userCustomer.assets.uncollectCapital}
                                        </td>
                                        <td>
                                                ${userCustomer.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                        </td>
                                        <td>
                                            <c:if test="${userCustomer.sex == 0 }">
                                                女
                                            </c:if>
                                            <c:if test="${userCustomer.sex == 1 }">
                                                男
                                            </c:if>
                                            <c:if test="${userCustomer.sex == 2 }">
                                                保密
                                            </c:if>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${userCustomer.registerTime }"
                                                            pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td>
                                                ${userCustomer.registerIp }
                                        </td>
                                        <td>
                                            <c:if test="${userCustomer.status == 0 }">
                                                <span class="label label-success">可用</span>
                                            </c:if>
                                            <c:if test="${userCustomer.status == 1 }">
                                                <span class="label label-warning">已冻结</span>
                                            </c:if>
                                            <c:if test="${userCustomer.status == 2 }">
                                                <span class="label label-danger">已删除</span>
                                            </c:if>
                                            <c:if test="${userCustomer.status == 3 }">
                                                <span class="label label-danger">测试帐号</span>
                                            </c:if>
                                        </td>
                                        <td>
                                            <a class="delete-row" href="${basePath}user/detail/app?id=${userCustomer.id }">详情</a>
                                        </td>
                                    </tr>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- end DataTables Example -->
    </div>
</div>
<script type="text/javascript">
    function bb() {
        $("#form").submit();
    }
    $(function () {
        $(".keyword").keyup(function (e) {
            e = e || window.e;
            if (e.keyCode == 13) {
                $("#form").submit();
            }
        });
        if("${error}"!=""){
        	alert("${error}");
        }
    })
</script>
</body>
</html>
