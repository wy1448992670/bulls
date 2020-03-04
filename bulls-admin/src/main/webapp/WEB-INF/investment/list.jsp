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
    <title>用户投资列表</title>
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
                用户投资管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>投资列表
                        <a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="" onclick="yyccheck()"
                           href="JavaScript:;"><i
                                class="icon-plus"></i>导出Excel</a>
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()"> 搜索</button>
                    </div>
                    <script>
                    function yyccheck(){
                    	var flag = false;
                    	var method = $("#method").val();
                    	var keyword = $("#keyword").val();
                    	var source = $("#source").val();
                    	var seq = $("#seq").val();
                    	var type = $("#type").val();
                    	var investType = $("#investType").val();
                    	var startTime = $("#startTime").val();
                    	var endTime = $("#endTime").val();
						if(startTime!="" && endTime!=""){
							if(startTime<=endTime){
                    			flag = true;
                    		}else{
                    			alert("投资结束时间必须大于等于投资开始时间");
                    			return;
                    		}
                    	}
						if(flag){
                    		location.href="${basePath}report/export/investment?startTime="+startTime+"&endTime="+endTime+"&seq="+seq+"&codes=${codes}&type="+type+"&investType="+investType+"&source="+source+"&method="+method+"&keyword="+keyword+"";
                    	}else{
                    		alert("投资时间区间为必选项");
                    	}
                    }
                    </script>
                    <div class="widget-content padded clearfix">

                        <form class="form-inline hidden-xs col-lg-5 pull-right" id="form" action="list">
                            <div class="row">
                                <div class="form-group col-md-3">
                                    <select class="select2able" name="seq" id="seq">
                                        <option value="" <c:if test="${seq == null }">selected</c:if>>无金额限制</option>
                                        <option value="0" <c:if test="${seq == 0 }">selected</c:if>>0-1000元</option>
                                        <option value="1" <c:if test="${seq == 1 }">selected</c:if>>1000-2000元</option>
                                        <option value="2" <c:if test="${seq == 2 }">selected</c:if>>2000-5000元</option>
                                        <option value="3" <c:if test="${seq == 3 }">selected</c:if>>5000-10000元</option>
                                        <option value="4" <c:if test="${seq == 4 }">selected</c:if>>10000元以上</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-3">
                                    <select class="select2able" name="type" id="type" onchange="isUserable()">
                                        <option value=""  <c:if test="${type == null }">selected</c:if>>项目类型</option>
                                        <option value="0" <c:if test="${type == 0 }">selected</c:if>>散标</option>
                                        <option value="1" <c:if test="${type == 1 }">selected</c:if>>活期</option>
                                        <option value="2" <c:if test="${type == 2 }">selected</c:if>>新手</option>
                                        <option value="4" <c:if test="${type == 4 }">selected</c:if>>VIP</option>
                                        <option value="8" <c:if test="${type == 8 }">selected</c:if>>智投</option>
                                    </select>
                                </div>

                                <div class="form-group col-md-3">
                                    <div>
                                        <select class="select2able" name="investType" id="investType">
                                            <option value="" <c:if test="${investType == null }">selected</c:if>>投资类型</option>
                                            <option value="1" <c:if test="${investType == 1 }">selected</c:if>>债权转让</option>
                                            <option value="15" <c:if test="${investType == 15 }">selected</c:if>>15天</option>
                                            <option value="30" <c:if test="${investType == 30 }">selected</c:if>>30天</option>
                                            <option value="90" <c:if test="${investType == 90 }">selected</c:if>>90天</option>
                                            <option value="180" <c:if test="${investType == 180 }">selected</c:if>>180天</option>
                                            <option value="360" <c:if test="${investType == 360 }">selected</c:if>>360天</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group col-md-3">
                                    <div>
                                        <select class="select2able" name="source" id="source">
                                            <option value="" <c:if test="${source == null }">selected</c:if>>客户来源</option>
                                            <option value="0" <c:if test="${source == 0 }">selected</c:if>>IOSAPP</option>
                                            <option value="1" <c:if test="${source == 1 }">selected</c:if>>IOS微信</option>
                                            <option value="2" <c:if test="${source == 2 }">selected</c:if>>安卓APP</option>
                                            <option value="3" <c:if test="${source == 3 }">selected</c:if>>安卓微信</option>
                                            <option value="4" <c:if test="${source == 4 }">selected</c:if>>PC网站</option>

                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-md-4">
                                    <input class="form-control datepicker" value="${startTime }" name="startTime" id="startTime"
                                           type="text" placeholder="请选择投资起始时间"/>
                                </div>
                                <div class="form-group col-md-4">
                                    <input class="form-control datepicker" value="${endTime }" name="endTime" id="endTime"
                                           type="text" placeholder="请选择投资结束时间"/>
                                </div>
                                <div class="form-group col-md-4">
                                    <input class="form-control keyword" name="keyword" id="keyword" type="text"
                                           placeholder="请输入用户昵称、真实姓名、手机号搜索" value="${keyword }"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-4">
                                    <input class="form-control keyword" name="codes" id="codes" type="text"
                                           placeholder="请输入渠道号搜索" value="${codes }"/>
                                </div>
                            </div>

                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>
                                    项目
                                </th>
                                <th>
                                    类型
                                </th>
                                <th>
                                    昵称
                                </th>
                                <th>
                                    真名
                                </th>
                                <th>
                                    渠道
                                </th>
                                <th>
                                    项目期限
                                </th>
                                <th>
                                    金额
                                </th>
              					<th>
                                    年化
                                </th>
                                <th>
                                    投资时间
                                </th>
                                <th>
                                    使用红包金额
                                </th>
                                <th>
                                    来源
                                </th>
                                <th>
                                    投资合同
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list }" varStatus="s">
                                <tr>
                                    <td>${i.id}</td>
                                    <td>
                                        <c:if test="${i.type != 1 }">
                                            <a href="${basePath}project/detail?id=${i.project_id}">
                                                <c:choose>
                                                    <c:when test="${empty i.title }">${i.title2}</c:when>
                                                    <c:otherwise>${i.title}</c:otherwise>
                                                </c:choose>
                                            </a>
                                        </c:if>
                                        <c:if test="${i.type == 1 }">
                                         <a href="#">
                                             <c:choose>
                                                 <c:when test="${empty i.title }">${i.title2}</c:when>
                                                 <c:otherwise>${i.title}</c:otherwise>
                                             </c:choose>
                                         </a>
                                        </c:if>
                                        <c:if test="${i.type == 8 || i.type == 9}">${i.month_title}</c:if>
                                    </td>
                                    <td>
                                        <c:if test="${i.type == 0 }">
						                    <c:if test="${i.parent_id1 != null }">                 
						                                        <span class="label label-success">债转</span>
						                    </c:if>
						                    <c:if test="${i.parent_id1 == null }">                 
						                                       <span class="label label-success">散标</span>
						                    </c:if>
                                        </c:if>
                                        <c:if test="${i.type == 1 }"><span class="label label-success">活期</span></c:if>
                                        <c:if test="${i.type == 2 }"><span class="label label-success">新手标</span></c:if>
                                        <c:if test="${i.type == 4 }"><span class="label label-success">VIP</span></c:if>
                                        <c:if test="${i.type == 8 || i.type == 9}"><span class="label label-success">智投</span></c:if>
                                    </td>
                                    <td>
                                        <a href="${basePath}user/detail/app?id=${i.user_id}">${i.username }</a>
                                    </td>
                                    <td>
                                        <a href="${basePath}user/detail/app?id=${i.user_id}">${i.trueName }</a>
                                    </td>
                                    <td>
                                            ${i.code}
                                    </td>
                                     <td><c:choose>
                                          <c:when test="${i.type == 8||i.type == 9}">
                                             ${i.month_limit_day }
                                          </c:when>
                                          <c:otherwise>
                                          <c:if test="${i.difday > 0 }">${i.difday}</c:if>
                                        <c:if test="${i.difday <= 0 || i.difday == null }">0</c:if>
                                          </c:otherwise>
                                     </c:choose>
                                            天
                                    </td>
                                    <td>
                                            ${i.amount}元
                                    </td>
                                    <td><c:if test="${i.type == 0||i.type == 2}"><fmt:formatNumber value="${i.amount/365*i.difday}" type="currency" pattern="0.00"/></c:if></td>
                                    <td>
                                        <fmt:formatDate value="${i.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <c:if test="${i.hb_amount > 0 }">${i.hb_amount}元</c:if>

                                    </td>

                                    <td>
                                        <c:if test="${i.terminal == 0 }">PC端</c:if>
                                        <c:if test="${i.terminal == 1 }">安卓</c:if>
                                        <c:if test="${i.terminal == 2 }">IOS</c:if>
                                        <c:if test="${i.terminal == 3 }">WAP</c:if>
                                    </td>
                                     <c:if test="${i.type == 8 || i.type == 9}">
                                            <td>
	                                            <c:if test="${i.type != 1 }">
	                                                <a target="_blank" href="${basePath}investment/monthInvestmentDetail?investmentId=${i.id}">投资详情</a>
	                                            </c:if>
	                                            </a>
                                            </td>
                                        </c:if>
                                    <c:if test="${i.type==0 or i.type==2 or i.type==4}">
                                        <td>
                                            <a target="_blank" href="${basePath}investment/contract?investmentId=${i.id}&userId=${i.user_id}">查看合同</a>
                                            <c:if test="${i.type != 1 }">|
                                                <a target="_blank" href="${basePath}investment/detail?investmentId=${i.id}">投资详情</a>
                                            </c:if>
                                        </td>
                                    </c:if>
                                    <c:if test="${i.type==1}">
                                        <td>
                                            <c:if test="${i.id>=104452}">
                                                <a target="_blank" href="${basePath}investment/currentAgreement?investmentId=${i.id}&userId=${i.user_id}">查看合同</a>
                                            </c:if>
                                            <c:if test="${i.id<104452}">
                                                无
                                            </c:if>
                                            <c:if test="${i.type != 1 }">|
                                                <a target="_blank" href="${basePath}investment/detail?investmentId=${i.id}">投资详情</a>
                                            </c:if>
                                        </td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <B>投资总额:</B>${totalAmount }
                        <B>投资年化:</B>${yearAmount}
                        <B>投资人数:</B>${sum }
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
                    return "list?keyword=${keyword}&seq=${seq}&type=${type}&codes=${codes}&endTime=${endTime}&investType=${investType}&source=${source}&startTime=${startTime}&page=" + page;
                }
            });
        });

        function isUserable() {
            var flag = $('select[name="type"]').val();
            if (flag == 0 || flag == 4 || flag == 8) {
                $('select[name="investType"]').attr("disabled", false);
            } else {
                $('select[name="investType"]').attr("disabled", true);
            }
        }
        isUserable();

    </script>
</body>
</html>