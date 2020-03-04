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
    <title>商品发货</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/jquery-fileupload/jquery.fileupload.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        .upload-picture a {
            display: inline-block;
            overflow: hidden;
            border: 0;
            vertical-align: top;
            margin: 0 5px 10px 0;
            background: #fff;
        }

        .gallery-item:hover {
            background: #000;
        }

        #validate-form i.icon-zoom-in {
            width: 36px;
            height: 36px;
            font-size: 18px;
            line-height: 35px;
            margin-top: 0;
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
                商品发货
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>商品发货
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}shop/sendGoods"  method="post" class="form-horizontal" id="validate-form" ENCTYPE="multipart/form-data">
                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-4">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">快递公司:</label>
                                <input type="hidden" name="id" value="${goodsOrder.id}"/>
                                
                                <div class="col-md-7">
                                	<select class="select2able" name="express" id="express">
										<option value="">请选择快递公司</option>
										<option value="EMS快递" >EMS快递</option>
                                        <option value="丹鸟" >丹鸟</option>
                                        <option value="丹马宽容" >丹马宽容</option>
                                        <option value="安鲜达" >安鲜达</option>
										<option value="顺丰快递" >顺丰快递</option>
										<option value="申通快递" >申通快递</option>
										<option value="圆通快递" >圆通快递</option>
										<option value="中通快递" >中通快递</option>
										<option value="韵达快递" >韵达快递</option>
                                        <option value="天天快递" >天天快递</option>
                                        <option value="宅急送快递" >宅急送快递</option>
										<option value="百世汇通快递" >百世汇通快递</option>
									</select>
                                    <%-- <input class="form-control" name="express" id="express" placeholder="请输入快递公司"
                                           type="text" value="${goodsOrder.express }"> --%>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">物流编号:</label>
								<div class="col-md-7">
                                    <input class="form-control" name="expressNum" id="expressNum" placeholder="请输入物流编号"
                                           type="text" value="${goodsOrder.expressNum }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" type="submit">确认发货</button>
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
<script src="${basePath}js/bootstrap-fileupload.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.fancybox.pack.js" type="text/javascript"></script>
<script type="text/javascript">

    $(function () {
        $('.select2able').select2();
    });
    
    $("#validate-form").validate({
        rules: {
        	express: {
                required: true
            },
            expressNum: {
                required: true
            },
        },
        messages: {
        	express: {
                required: "请输入快递公司"
            },
            expressNum: {
                required: "请输入物流编号"
            },
        }
    });
</script>
</body>
</html>