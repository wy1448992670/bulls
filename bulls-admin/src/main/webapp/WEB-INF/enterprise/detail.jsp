<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>企业详细信息</title>
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
                企业管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>企业详细信息
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="add" method="post" class="form-horizontal" id="validate-form">
                            <div class="form-group">
                                <label class="control-label col-md-2">企业图片</label>

                                <div class="col-md-7">
                                    <div class="form-control-static upload-picture">
                                        <c:forEach var="pic" items="${enterprise.pictures }">
                                            <a class="gallery-item fancybox" rel="g1" href="/upload/${pic.picturePath }"
                                               title="${pic.name }">

                                                <c:choose>
                                                    <c:when test="${fn:endsWith(pic.name, '.png') || fn:endsWith(pic.name, '.jpg') || fn:endsWith(pic.name, '.gif')}">
                                                        <img src="${aPath}upload/${pic.picturePath}"/>
                                                    </c:when>

                                                    <c:when test="${fn:endsWith(pic.name, '.docx') || fn:endsWith(pic.name, '.doc')}">
                                                        <img src="${aPath}upload/icons/W.jpg"/>
                                                    </c:when>

                                                    <c:when test="${fn:endsWith(pic.name, '.xlsx') || fn:endsWith(pic.name, '.xls')}">
                                                        <img src="${aPath}upload/icons/X.jpg"/>
                                                    </c:when>

                                                    <c:when test="${fn:endsWith(pic.name, '.pdf')}">
                                                        <img src="${aPath}upload/icons/PDF.jpg"/>
                                                    </c:when>

                                                    <c:when test="${fn:endsWith(pic.name, '.zip')}">
                                                        <img src="${aPath}upload/icons/ZIP.jpg"/>
                                                    </c:when>

                                                    <c:when test="${fn:endsWith(pic.name, '.ppt')}">
                                                        <img src="${aPath}upload/icons/P.jpg"/>
                                                    </c:when>

                                                </c:choose>

                                                <div class="actions">
                                                    <i class="icon-zoom-in"></i>
                                                </div>
                                            </a>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">企业编号</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${enterprise.no }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">企业名称</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${enterprise.name }
                                    </p>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="control-label col-md-2">手机号码</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${enterprise.phone }
                                    </p>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="control-label col-md-2">银行卡号</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${enterprise.cardNumber }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">用户类型</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <c:if test="${enterprise.type ==0 }">企业用户</c:if>
                                        <c:if test="${enterprise.type ==1 }">个人用户</c:if>
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">企业简介</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${enterprise.intro }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">企业背景</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${enterprise.background }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">营业范围</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${enterprise.scope }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">经营状况</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${enterprise.conditionState }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7 text-center">
                                    <a class="btn btn-default-outline" href="javascript:history.go(-1);">返回</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- end DataTables Example -->
    </div>
</div>

<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.fancybox.pack.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $('.select2able').select2();

        $(".fancybox").fancybox({
            maxWidth: 700,
            height: 'auto',
            fitToView: false,
            autoSize: true,
            padding: 15,
            nextEffect: 'fade',
            prevEffect: 'fade',
            helpers: {
                title: {
                    type: "outside"
                }
            }
        });
    });
</script>
</body>
</html>