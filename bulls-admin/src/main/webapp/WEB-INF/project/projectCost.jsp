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
    <title>项目运营成本详情</title>
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
                项目运营成本详情
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>项目运营成本列表
                        <a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id=""
                           href="${basePath}report/export/projectCost?status=${status}&endTime=<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>&startTime=<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>&seq=${seq}&limitDays=${limitDays}&type=${type}&investType=${investType}&source=${source}&keyword=${keyword}&method=${method}"><i
                                investTyp class="icon-plus">导出Excel</i></a>
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()"> 搜索</button>
                    </div>
                    <div class="widget-content padded clearfix">

                        <form class="form-inline hidden-xs col-lg-5 pull-right" id="form" action="${basePath}project/list/projectCost">
                            <div class="row">

                                <div class="form-group col-md-3">
                                    <select class="select2able" name="type" onchange="isUserable()">
                                        <option value="" <c:if test="${type == null }">selected</c:if>>项目类型</option>
                                        <option value="0" <c:if test="${type == 0 }">selected</c:if>>散标</option>
                                        <option value="1" <c:if test="${type == 1 }">selected</c:if>>活期</option>
                                        <option value="2" <c:if test="${type == 2 }">selected</c:if>>新手</option>
                                        <option value="4" <c:if test="${type == 4 }">selected</c:if>>VIP</option>
                                        <option value="3" <c:if test="${type == 3 }">selected</c:if>>债权转让</option>
                                    </select>
                                </div>

                                <div class="form-group col-md-3">
                                    <div>
                                        <select class="select2able" name="limitDays">
                                            <option value="" <c:if test="${limitDays == null }">selected</c:if>>项目天数</option>
                                            <option value="14" <c:if test="${limitDays == 14 }">selected</c:if>>14天</option>
                                            <option value="30" <c:if test="${limitDays == 30 }">selected</c:if>>30天</option>
                                            <option value="90" <c:if test="${limitDays == 90 }">selected</c:if>>90天</option>
                                            <option value="180" <c:if test="${limitDays == 180 }">selected</c:if>>180天</option>
                                            <option value="365" <c:if test="${limitDays == 365 }">selected</c:if>>365天</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group col-md-6">
                                    <input class="form-control keyword" name="keyword" type="text"
                                           placeholder="请输入项目名称搜索" value="${keyword }"/>
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control datepicker"
                                               value="<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>"
                                               id="startTime" name="startTime" type="text" placeholder="请选择起始时间"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control datepicker"
                                               value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd"/>"
                                               id="endTime" name="endTime" type="text" placeholder="请选择结束时间"/>
                                    </div>
                                </div>

                            </div>
                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>
                                    项目名称

                                </th>
                                <th>
                                    项目类型

                                </th>
                                <th>
                                    项目期限
                                </th>
                                <th>
                                    项目总额（元）
                                </th>
                                <th>
                                    真实融资额（元）
                                </th>

                                <th>
                                    项目利率
                                </th>
                                <th>
                                    发布时间
                                </th>
                                <th>
                                    满标时间
                                </th>
                                <th>
                                    状态
                                </th>
                                <th>
                                    项目加息收益（元）
                                </th>
                                <th>
                                    投资红包总额（元）
                                </th>
                                <th>
                                    现金红包总额（元）
                                </th>
                                <th>
                                    加息收益（元）
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list }" varStatus="s">
                                <tr>
                                    <td>${i.id}</td>
                                    <td>
                                        <c:if test="${i.project_type != 2 }">
                                            <a href="${basePath}project/detail?id=${i.id}">
                                                    ${i.title}
                                            </a>
                                        </c:if>
                                        <c:if test="${i.project_type == 2 }">
                                            <a href="#">
                                                    ${i.title}
                                            </a>
                                        </c:if>


                                    </td>
                                    <td>
                                            <c:if test="${i.project_type == 2 }">活期</c:if>
                                            <c:if test="${i.parent_id ==  null &&  i.noob==0  && i.project_type == 0 }">散标</c:if>
                                            <c:if test="${i.parent_id ==  null &&  i.noob==1  && i.project_type == 0}">新手标</c:if>
                                            <c:if test="${i.parent_id ==  null &&  i.noob==2  && i.project_type == 0}">vip</c:if>
                                            <c:if test="${i.parent_id != null && i.project_type == 1 }">债权</c:if>
                                            <c:if test="${i.parent_id ==  null &&  i.noob==3 }">个人借贷</c:if>
                                    </td>
                                    <td>${i.limit_days}</td>
                                    <td>${i.total_amount}</td>
                                    <td>${i.sumAmount}</td>

                                    <td>${i.annualized}%
                                        <c:if test="${i.increase_annualized >0 }">+ ${i.increase_annualized}%</c:if>
                                    </td>

                                    <td><fmt:formatDate value="${i.start_time }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td>
                                        <fmt:formatDate value="${i.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>

                                    <td>

                                        <c:if test="${i.status==0  && (i.project_type==0 || i.project_type==2)}">创建</c:if>
                                        <c:if test="${i.status==1  && (i.project_type==0 || i.project_type==2)}">预购</c:if>
                                        <c:if test="${i.status==2  && (i.project_type==0 || i.project_type==2)}">投资中</c:if>
                                        <c:if test="${i.status==3  && (i.project_type==0 || i.project_type==2)}">投资完成</c:if>
                                        <c:if test="${i.status==4  && (i.project_type==0 || i.project_type==2)}">还款中</c:if>
                                        <c:if test="${i.status==5  && (i.project_type==0 || i.project_type==2)}">还款成功</c:if>
                                        <c:if test="${i.status==6  && (i.project_type==0 || i.project_type==2)}">还款失败</c:if>

                                        <c:if test="${i.status==0  && i.project_type==1  }">转让中</c:if>
                                        <c:if test="${i.status==1  && i.project_type==1  }">已转让</c:if>
                                        <c:if test="${i.status==2  && i.project_type==1  }">已取消</c:if>
                                    </td>

                                    <td>

                                    </td>
                                    <td>
                                            ${i.tzhb}
                                    </td>
                                    <td>
                                            ${i.xjhb}
                                    </td>


                                        <td>

                                        </td>

                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                        <ul id="pagination" style=" float: right">
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
                    return "${basePath}project/list/projectCost?keyword=${keyword}&type=${type}&limitDays=${limitDays}&endTime=<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>&startTime=<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>&page=" + page;
                }
            });
        });

        function isUserable() {
            var flag = $('select[name="type"]').val();
            if (flag == 1 || flag == "") {
                $('select[name="limitDays"]').attr("disabled", true);
            } else {
                $('select[name="limitDays"]').attr("disabled", false);
            }
        }
        isUserable();

    </script>
</body>
</html>