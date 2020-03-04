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
    <title>项目一级列表</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
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
                项目一级列表
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>项目一级列表
                        <%--
                                                <shiro:hasPermission name="project:addProjectClass">
                        --%>
                        <a class="btn btn-sm btn-primary-outline pull-right" href="addProjectClass" id="add-row"><i
                                class="icon-plus"></i>创建项目</a> <button class="btn btn-primary pull-right" type="button" onclick="aa2('PROJECT_CLASS_LIST')"> 刷新缓存</button>
                        <%--
                                                </shiro:hasPermission>
                        --%>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-5 pull-right" id="form" action="projectClassList">
                            <div class="row">
                            	<div class="form-group col-md-6"></div>
                                <div class="form-group col-md-3">
                                     <select class="select2able" name="noob">
                                         <option value="" <c:if test="${noob == null }">selected</c:if>>所有类型</option>
                                         <option value="0" <c:if test="${noob == 0 }">selected</c:if>>普通散标</option>
                                         <option value="1" <c:if test="${noob == 1 }">selected</c:if>>新手标</option>
                                     </select>
                                </div>
                                <div class="form-group col-md-3">
                                      <select class="select2able" name="limitDays">
                                          <option value="" <c:if test="${limitDays == null }">selected</c:if>>所有项目期限</option>
                                          <option value="15" <c:if test="${limitDays == 15 }">selected</c:if>>15</option>
                                          <option value="30" <c:if test="${limitDays == 30 }">selected</c:if>>30</option>
                                          <option value="60" <c:if test="${limitDays == 60 }">selected</c:if>>60</option>
                                          <option value="90" <c:if test="${limitDays == 90 }">selected</c:if>>90</option>
                                          <option value="120" <c:if test="${limitDays == 120 }">selected</c:if>>120</option>
                                          <option value="180" <c:if test="${limitDays == 180 }">selected</c:if>>180</option>
                                          <option value="240" <c:if test="${limitDays == 240 }">selected</c:if>>240</option>
                                          <option value="270" <c:if test="${limitDays == 270 }">selected</c:if>>270</option>
                                          <option value="360" <c:if test="${limitDays == 360 }">selected</c:if>>360</option>
                                      </select>
                                </div>
                           </div>
	                       <div class="row">
								<div class="form-group col-md-6"></div>
								<div class="form-group col-md-6">
									<input class="form-control keyword" name="keyword" type="text" placeholder="请输入项目名称搜索" value="${keyword }" id="keyword"/>
								</div>
							</div>
                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                        
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                    名称
                                </th>
                                <th>
                                    设备类型
                                </th>
                                <th>
                                    利息
                                </th>
                                <th>
                                    加息
                                </th>
                                <th>
                                    期限/天
                                </th>
                                <th>
                                    万元预期收益
                                </th>
                                <th>
                                    创建时间
                                </th>
                                <th>
                                    更新时间
                                </th>
                                <th>
                                    排序
                                </th>
                                <th width="60"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="g" items="${list }">
                                <tr>
                                    <td width="100">
                                        <c:if test="${g.noob==0}"><span class="label label-primary">普通标</span></c:if>
                                        <c:if test="${g.noob==1}"><span class="label label-danger">新手标</span></c:if>
                                        <c:out value="${g.projectTitle }" escapeXml="false"/>
                                    </td>
                                    <td>
                                            ${g.deviceType }
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${g.annualized }" type="percent" maxFractionDigits="3"
                                                          groupingUsed="false"/>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${g.increaseAnnualized }" type="percent" maxFractionDigits="5"
                                                          groupingUsed="false"/>
                                    </td>
                                    <td>
                                            ${g.limitDays }
                                    </td>
                                    <td>
                                        <c:if test="${g.preAmount==null }">
                                            0元 </c:if>
                                        <c:if test="${g.preAmount!=null }">
                                            ${g.preAmount }元</c:if>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${g.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${g.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                            ${g.sort}
                                    </td>
                                    <td>
                                        <a class="edit-row" href="addProjectClass?id=${g.id }">编辑</a>
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
<script src="${basePath}js/jquery-1.10.2.min.js"></script>
<script src="${basePath}js/jquery-ui.js"></script>
<script src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {

        $('.select2able').select2({width: "150"});
        $(".select2able").change(function () {
            $("#form").submit();
        });

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
                return "list?limitDays=${limitDays}&keyword=${keyword}&noob=${noob}&status=${status}&page=" + page;
            }
        });
    });
    function aa2(key) {
        $.ajax({
            url: '${basePath}dict/reflushKey',
            type: "get",
            dataType: "json",
            data: "key=" + key,
            success: function (obj) {
                if (obj == true) {
                    alert("刷新成功");
                } else {
                    alert("刷新失败");
                }
            },
            error: function (obj) {
                alert("对不起，系统繁忙");
            }
        });
    }
</script>
</body>
</html>