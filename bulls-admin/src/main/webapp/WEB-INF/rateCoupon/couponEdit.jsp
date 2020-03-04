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
    <title>创建券</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <style type="text/css">
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
                创建券
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>创建券
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}coupon/couponEdit" method="post" class="form-horizontal" 
                        id="validate-form" ENCTYPE="multipart/form-data" onsubmit="return check()">
                        	<input type="hidden" name="templateId" value="${couponTemplate.templateId}"/>
                        	
                        	<div class="form-group">
                                <label class="control-label col-md-2">券类型</label>
                                <div class="col-md-7">
                                    <select class="select2able" name="type" id="type" disabled>
                                        <option <c:if test="${couponTemplate.type==0}">selected</c:if> value="0">投资红包</option>
                                        <option <c:if test="${couponTemplate.type==1}">selected</c:if> value="1">加息券</option>
                                    </select>
                                </div>
                            </div>
                        	
                            <div class="form-group">
                                <label class="control-label col-md-2">标题</label>
                                <div class="col-md-7">
                                    <input class="form-control" readonly name="title" id="title" placeholder="请输入标题" type="text" value="${couponTemplate.title}" />
                                </div>
                            </div>
                            
                            <div class="form-group divAmount" style="display:none">
                                <label class="control-label col-md-2">红包金额</label>
                                <div class="col-md-7">
                                    <input class="form-control" readonly name="amount" id="amount" placeholder="请输入红包金额" 
                                    type="text" value="${couponTemplate.amountStr}" pattern="###.##"/>
                                </div>
                            </div>
                            
                            <div class="form-group divRate" style="display:none">
                                <label class="control-label col-md-2">加息利率</label>
                                <div class="col-md-7">
                                    <input class="form-control" readonly name="rate" id="rate" placeholder="请输入加息利率" type="text" value="${couponTemplate.rate}" />
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="control-label col-md-2">起投金额</label>
                                <div class="col-md-7">
                                    <input class="form-control" readonly name="minAmount" id="minAmount" placeholder="请输入起投金额" type="text" value="${couponTemplate.minAmount}" />
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="control-label col-md-2">有效期</label>
                                <div class="col-md-7">
                                    <input class="form-control" readonly name="effectiveDays" id="effectiveDays" placeholder="请输入有效期" type="text" value="${couponTemplate.effectiveDays}" />
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="control-label col-md-2">最低投资周期</label>
                                <div class="col-md-7">
                                    <input class="form-control" readonly name="minDays" id="minDays" placeholder="请输入30/90/180/360中的整数值" type="text" value="${couponTemplate.minDays}" />
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="control-label col-md-2">库存上限</label>
                                <div class="col-md-7">
                                    <input class="form-control" name="stockLimit" id="stockLimit" placeholder="库存上限" type="text" readonly value="${couponTemplate.stockLimit}" />
                                </div>
                            </div>
                            
                            
                            <div class="form-group">
                                <label class="control-label col-md-2">剩余库存</label>
                                <div class="col-md-7">
                                    <input class="form-control" name="stockBalance" id="stockBalance" placeholder="剩余库存" type="text" readonly value="${couponTemplate.stockBalance}" />
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="control-label col-md-2">增加库存</label>
                                <div class="col-md-7">
                                    <input class="form-control" name="addNum" id="addNum" placeholder="输入增加的数量" type="text" value="0" />
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="control-label col-md-2"></label>
                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" type="submit">保存</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $('.select2able').select2();
        var stockLimit = '${couponTemplate.stockLimit}';
        var stockBalance = '${couponTemplate.stockBalance}';
        
        var type = '${couponTemplate.type}'
        if(type == 0 || type == 2){
        	$(".divAmount").show();
    		$(".divRate").hide();
        }else if(type == 1){
        	$(".divAmount").hide();
    		$(".divRate").show();
        }
        
        $("#type").change(function(){
        	var type = $("#type").select().val();
        	if(type == 0 || type == 2){
        		$(".divAmount").show();
        		$(".divRate").hide();
        	}else if(type == 1){
        		$(".divAmount").hide();
        		$(".divRate").show();
        	}
        })
        
        $("#validate-form").validate({
            rules: {
                title: {
                    required: true,
                    maxlength:128
                },
                amount: {
                    required: true,
                    number:true
                },
                rate: {
                    required: true,
                    range:[0,1]
                },
                minAmount: {
                    required: true,
                    digits:true
                },
                effectiveDays: {
                    required: true,
                    digits:true
                },
                minDays: {
                    required: true,
                    digits:true
                },
                stockLimit: {
                    required: true,
                    digits:true
                },
                stockBalance: {
                    required: true,
                    digits:true
                },
                addNum: {
                	digits:true,
                	range:[0,stockLimit - stockBalance]
                }
            },
            messages: {
            	title: {
                    required: "请输入标题"
                },
                amount: {
                    required: "请输入金额",
                    number: "请输入数字"
                },
                rate: {
                    required: "请输入利率",
                    digits: "请输入数字"
                },
                minAmount: {
                    required: "请输入起投金额",
                    digits: "请输入数字"
                },
                effectiveDays: {
                    required: "请输入有效期",
                    digits: "请输入数字"
                },
                minDays: {
                    required: "请输入最低投资周期",
                    digits: "请输入数字"
                },
                stockLimit: {
                    required: "请输入库存上限",
                    digits: "请输入数字"
                },
                stockBalance: {
                    required: "请输入剩余库存",
                    digits: "请输入数字"
                },
                addNum: {
                	digits: "请输入数字",
                	range: "超过库存上限"
                }
            }
        });
    });
    
	function check(){
    	
    	var minDays = parseInt($("#minDays").val());
    	if(minDays != 30 && minDays != 90 && minDays != 180 && minDays != 360){
    		alert("最低投资周期输入有误");
    		return false;
    	}
    	
    	var stockLimit = parseInt($("#stockLimit").val());
    	var stockBalance = parseInt($("#stockBalance").val());
    	if(stockBalance > stockLimit){
    		alert("超过库存上限");
    		return false;
    	}
    	return true;
    }
</script>
</body>
</html>