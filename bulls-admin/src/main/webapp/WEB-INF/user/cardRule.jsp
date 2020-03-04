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
    <title>银行卡规则管理</title>
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
                用户管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>银行卡规则管理<a class="btn btn-sm btn-primary-outline pull-right" href="cardRuleDetail" id="add-row"><i class="icon-plus"></i>添加规则</a>
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()"> 搜索</button>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-3 pull-right" id="form" action="cardRule">
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <div>
                                        <select class="select2able" name="payType">
                                            <option value="" <c:if test="${payType == null }">selected</c:if>>选择支付类型</option>
                                            <option value="0" <c:if test="${payType == 0 }">selected</c:if>>绑卡支付</option>
                                            <option value="1" <c:if test="${payType == 1 }">selected</c:if>>网银支付</option>
                                            <option value="2" <c:if test="${payType == 2 }">selected</c:if>>快捷支付</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <div>
                                        <select class="select2able" name="ruleType">
                                            <option value="" <c:if test="${ruleType == null }">selected</c:if>>选择规则类型</option>
                                            <option value="0" <c:if test="${ruleType == 0 }">selected</c:if>>排除</option>
                                            <option value="1" <c:if test="${ruleType == 1 }">selected</c:if>>包含</option>
                                            <option value="2" <c:if test="${ruleType == 2 }">selected</c:if>>触发</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <div>
                                        <select class="select2able" name="status">
                                            <option value="" <c:if test="${status == null }">selected</c:if>>选择规则状态</option>
                                            <option value="0" <c:if test="${status == 0 }">selected</c:if>>待定</option>
                                            <option value="1" <c:if test="${status == 1 }">selected</c:if>>启用</option>
                                            <option value="2" <c:if test="${status == 2 }">selected</c:if>>放弃</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control keyword" name="keyword" type="text" placeholder="请输入关键字" value="${keyword }"/>
                                    </div>
                                </div>
                            </div>
                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>银行名称</th>
                                <th>规则标题</th>
                                <th>支付类型</th>
                                <th>规则类型</th>
                                <th>规则状态</th>
                                <th>更新时间</th>
                                <%--<th>来源附件</th>--%>
                                <th width="80">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list }" varStatus="s">
                                <tr>
                                    <td>${s.index+1}</td>
                                    <td>${i.bankName}</td>
                                    <td>${i.title}</td>
                                    <td>
                                        <c:if test="${i.payType == 0 }">
                                            绑卡支付
                                        </c:if>
                                        <c:if test="${i.payType == 1 }">
                                            网银支付
                                        </c:if>
                                        <c:if test="${i.payType == 2 }">
                                            快捷支付
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:if test="${i.ruleType == 0 }">
                                            排除
                                        </c:if>
                                        <c:if test="${i.ruleType == 1 }">
                                            包含
                                        </c:if>
                                        <c:if test="${i.ruleType == 2 }">
                                            触发
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:if test="${i.status == 0 }">
                                            待定
                                        </c:if>
                                        <c:if test="${i.status == 1 }">
                                            启用
                                        </c:if>
                                        <c:if test="${i.status == 2 }">
                                            放弃
                                        </c:if>
                                    </td>
                                    <td class="hidden-xs">
                                        <fmt:formatDate value="${i.time }" pattern="yyyy-MM-dd"/>
                                    </td>
                                        <%--<td class="hidden-xs">
                                            <a class="edit-row" href="${basePath}/upload/${i.path}">${i.attachment }</a>
                                        </td>--%>
                                    <td>
                                        <a class="btn btn-primary" href="cardRuleDetail?id=${i.id}&keyword=${keyword}&payType=${payType}&ruleType=${ruleType}&status=${status}&page=${page}">编辑</a>
                                        <a class="btn btn-primary" href="javascript:void(0)" onclick="deleteRule(${i.id},${page})" id="delete">删除</a>
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

    function aa() {
        $("#form").submit();
    }

    function deleteRule(id, page) {
        if (confirm("是否删除该规则？")) {
            $.ajax({
                url: '${basePath}user/deleteRuleAjax',
                type: 'post',
                data: 'id=' + id,
                dataType: "json",
                success: function (result) {
//					alert(result.msg);
                    window.location.href = "${basePath}user/cardRule?page=" + page;
                }
            });
        }
    }
    $(function () {
        $('.select2able').select2();
        /*$(".select2able").change(function(){
         $("#form").submit();
         });*/
        $(".keyword").keyup(function (e) {
            e = e || window.e;
            if (e.keyCode == 13) {
                $("#form").submit();
            }
        });
        $("#user-list a:eq(12)").addClass("current");
        $("#user-list a:eq(0)").addClass("current");
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "cardRule?keyword=${keyword}&payType=${payType}&ruleType=${ruleType}&time=<fmt:formatDate value="${i.time }" pattern="yyyy-MM-dd"/>&status=${status}&page=" + page;
            }
        });
    });
</script>
</body>
</html>