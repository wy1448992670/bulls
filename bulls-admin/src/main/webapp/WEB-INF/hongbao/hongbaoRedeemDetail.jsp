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
    <link href="${basePath}js/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
    
    <title>编辑红包兑换码</title>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                编辑红包兑换码
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>编辑红包兑换码
                    </div>
                    <input type="hidden" id="isZx" value="${isZx}">
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}hongbao/editHongbaoRedeem" method="post" class="form-horizontal"
                              id="validate-form">
                            <div class="form-group">
                                <label class="control-label col-md-2">兑换码标题</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="title" id="title" placeholder="请输入兑换码标题(生财礼包)"
                                           value="${map.title }">
                                </div>
                            </div>
                            <c:if test="${map.id !=null }">
                                <div class="form-group">
                                    <label class="control-label col-md-2">兑换码</label>

                                    <div class="col-md-7">
                                        <input class="form-control"
                                        <c:if test="${map.id !=null }"> disabled="true"</c:if>
                                               value="${map.redeemCode }">
                                    </div>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label class="control-label col-md-2">兑换起始日</label>

                                <div class="col-md-7">
                                    <input class="form-control datepicker" name="createTime" id="createTime" type="text"
                                           placeholder="请选择兑换起始日"
                                           value="<fmt:formatDate value="${map.createTime }" pattern="yyyy-MM-dd"/>">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">兑换截止日</label>

                                <div class="col-md-7">
                                    <input class="form-control datepicker" name="expireTime" id="expireTime" type="text"
                                           placeholder="请选择兑换截止日"
                                           value="<fmt:formatDate value="${map.expireTime }" pattern="yyyy-MM-dd"/>">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">兑换码使用次数</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="useCount" id="useCount" placeholder="兑换码的使用次数"
                                           value="${map.useCount }" onblur="checkIsNum()">
                                </div>
                            </div>
                            <%--<div class="form-group">--%>
                            <%--<label class="control-label col-md-2">指定到用户</label>--%>

                            <%--<div class="col-md-7">--%>
                            <%--<input id="selectUser" name="userId" type="hidden" value="${map.userId }" <c:if--%>
                            <%--test="${map.id !=null }"> disabled="true"</c:if>/>--%>
                            <%--</div>--%>
                            <%--<div class="col-md-3"><span class="label label-danger">如不选择用户，则所有用户都可用！</span></div>--%>
                            <%--</div>--%>
                            <div class="form-group">
                                <label class="control-label col-md-2">兑换码类型</label>

                                <div class="col-md-7">
                                    <select class="select2" name="type" id="type"
                                            <c:if test="${map.id !=null }">disabled="disabled"</c:if>>
                                        <option value="0" <c:if test="${map.type == 0 }">selected</c:if>>复用型</option>
                                        <option value="1" <c:if test="${map.type == 1 }">selected</c:if>>唯一型</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">兑换码状态</label>

                                <div class="col-md-7">
                                    <select class="select2" name="status" id="status">
                                        <option value="1" <c:if test="${map.status == 1 }">selected</c:if>>可用</option>
                                        <option value="0" <c:if test="${map.status == 0 }">selected</c:if>>不可用</option>
                                    </select>
                                </div>
                                <div class="col-md-3"><span class="label label-danger">新增兑换码完成后，不可编辑红包选定模版</span></div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">兑换码红包</label>

                                <div class="col-md-7">
                                    <a class="btn btn-sm btn-primary-outline" id="add-row">点击添加</a>
                                </div>
                            </div>
                            <div id="hongbaoList" name="template" class="form-group">
                            
                            	<c:choose> 
                            		<c:when test="${isZx == 'isZx'}">
                            			<c:forEach var="i" items="${boxList }" varStatus="s">
		                                    <div class="form-group checkGroup" id="row${s.index}">
		                                        <input class="hongbaoClass" type="hidden" id=""
		                                               name="templateId" value="${i.id }"/>
		                                        <label class="control-label col-md-2"><a
		                                                class="btn btn-sm btn-primary-outline delete-row">删除</a></label>
		
		                                        <div class="col-md-1">
		                                            <input class="form-control pull-left amountClass" name="amount" id="1"
		                                            <c:if test="${i.amount != null}"> value="${i.amount}" </c:if>
		                                                   placeholder="请输入红包金额" type="text" readonly>
		                                        </div>
		                                        <div class="col-md-1">
		                                            <label class="label label-info" style="font-size: 16px;">红包金额</label>
		                                        </div>
		                                        <div class="col-md-1">
		                                            <input class="form-control limitAmountClass" name="limitAmount"
		                                                   id="2"  onchange="changeHongBaoMoney('${s.index}')" onkeyup="changeHongBaoMoney('${s.index}')"
		                                            <c:if test="${i.limitAmount != null}"> value="${i.limitAmount}" </c:if>
		                                                   placeholder="请输入起投金额" type="text">
		                                        </div>
		                                        <div class="col-md-1">
		                                            <label class="label label-info" style="font-size: 16px;">起投金额</label>
		                                        </div>
		                                        <div class="col-md-1">
		                                            <label class="label label-success" style="font-size: 16px;">使用期限</label>
		                                        </div>
		                                        
		                                        <div class="col-md-1"> 
									                <select class="form-control select2able monthTypeClass" name="monthType" onchange="changeHongBaoMoney('${s.index}')">
										                <option value="0.0015" <c:if test="${i.monthType == 30}">selected</c:if>>30</option>
										                <option value="0.003" <c:if test="${i.monthType == 60}">selected</c:if>>60</option>
										                <option value="0.005" <c:if test="${i.monthType == 90}">selected</c:if>>90</option>
										                <option value="0.0055" <c:if test="${i.monthType == 120}">selected</c:if>>120</option>
										                <option value="0.006" <c:if test="${i.monthType == 180}">selected</c:if>>180</option>
										                <option value="0.005" <c:if test="${i.monthType == 240}">selected</c:if>>240</option>
										                <option value="0.006" <c:if test="${i.monthType == 270}">selected</c:if>>270</option>
										                <option value="0.005" <c:if test="${i.monthType == 360}">selected</c:if>>360</option>
									            	</select>
								                </div>
		                                        
		                                        <div class="col-md-1">
		                                            <label class="label label-success" style="font-size: 16px;">≥天</label>
		                                        </div>
		                                    </div>
		                                </c:forEach>
   									</c:when>
   									
   									<c:otherwise> 
   										<c:forEach var="i" items="${boxList }" varStatus="s">
		                                    <div class="form-group checkGroup" id="row${s.index}">
		                                        <input class="hongbaoClass" type="hidden" id=""
		                                               name="templateId" value="${i.id }"/>
		                                        <label class="control-label col-md-2"><a
		                                                class="btn btn-sm btn-primary-outline delete-row">删除</a></label>
		
		                                        <div class="col-md-1">
		                                            <input class="form-control amountClass" name="amount" id="1"
		                                            <c:if test="${i.amount != null}"> value="${i.amount}" </c:if>
		                                                   placeholder="请输入红包金额" type="text">
		                                        </div>
		                                        <div class="col-md-1">
		                                            <label class="label label-info" style="font-size: 16px;">红包金额</label>
		                                        </div>
		                                        <div class="col-md-1">
		                                            <input class="form-control limitAmountClass" name="limitAmount"
		                                                   id="2"
		                                            <c:if test="${i.limitAmount != null}"> value="${i.limitAmount}" </c:if>
		                                                   placeholder="请输入起投金额" type="text">
		                                        </div>
		                                        <div class="col-md-1">
		                                            <label class="label label-info" style="font-size: 16px;">起投金额</label>
		                                        </div>
		                                        <div class="col-md-1">
		                                            <label class="label label-success" style="font-size: 16px;">使用期限</label>
		                                        </div>
		                                        <div class="col-md-1">
		                                            <input class="form-control monthTypeClass" name="monthType"
		
		                                            <c:if test="${i.monthType != null}"> value="${i.monthType}" </c:if>
		                                                   placeholder="请输入起投天数" type="text">
		                                        </div>
		                                        <div class="col-md-1">
		                                            <label class="label label-success" style="font-size: 16px;">≥天</label>
		                                        </div>
		                                    </div>
		                                </c:forEach>
   									</c:otherwise>  
                            	</c:choose>
                                
                            </div>

                            <input type="hidden" name="id" id="id" value="${map.id }"/>
                            <input type="hidden" name="redeemCode" id="redeemCode" value="${map.redeemCode }"/>
                            <input type="hidden" name="version" id="version" value="${map.version }"/>


                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-success" type="submit" id="pub">提交</button>
                                </div>
                            </div>
                            <input type="hidden" name="template" id="template" value=""/>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- end DataTables Example -->
    </div>
</div>

<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath }js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $('#pub').click(function () {
            var title = $('#title').val().length;
            if (title == 0) {
                alert("请输入兑换码标题");
                return false;
            }
            if (title > 10) {
                alert("兑换码标题不能大于长度10");
                return false;
            }
            if($("#createTime").val() == ""){
            	alert("请选择兑换起始日");
                return false;
            }
            if($("#expireTime").val() == ""){
            	alert("请选择兑换截至日");
                return false;
            }
            
            if($("#useCount").val() == ""){
            	alert("请输入兑换码使用次数");
                return false;
            }
            
            if($("#useCount").val() <= 0){
            	alert("请输入正确的兑换码使用次数");
                return false;
            }
            
            
            var group = $('.checkGroup');
            
            if(group.length <= 0){
            	alert("请添加至少一条兑换码红包");
                return false;
            }
            
            var templates = [];
            for (var i = 0; i < group.length; i++) {
                var template = {};
                var o = $('.checkGroup').eq(i);
                template.limitAmount = o.find('.limitAmountClass')[0].value;
                template.amount = o.find('.amountClass')[0].value;
                template.templateId = o.find('.hongbaoClass')[0].value;
                
                if($("#isZx").val() == "isZx"){
                	template.monthType = o.find('.monthTypeClass').find("option:selected").text();
                }else{
                	template.monthType = o.find('.monthTypeClass')[0].value;
                }
                
                if(!o.find('.limitAmountClass')[0].value){
                	alert("请输入起投金额");
                	o.find('.limitAmountClass')[0].focus();
                	return false;
                }
                
                if(o.find('.limitAmountClass')[0].value <=0){
                	alert("请输入正确的起投金额");
                	o.find('.limitAmountClass')[0].focus();
                	return false;
                }
                
                if(o.find('.amountClass')[0].value <=0){
                	alert("红包金额不能为0");
                	o.find('.amountClass')[0].focus();
                	return false;
                }
                
                if(!template.monthType || template.monthType <= 0){
                	alert("请设置使用期限");
                	return false;
                }
                
                
                templates.push(template);
            }
            $('#template').val(JSON.stringify(templates));
        });
        var index = 0;
        var temp = "";
        $('#add-row').on('click', function () {
        	
        	index = $('.checkGroup').length + 1;
        	var isZx = $("#isZx").val();
        	if(!isZx){
        		temp = '<div class="form-group checkGroup" id="row' + (index) + '">' +
                '<input class="hongbaoClass" type="hidden" name="templateId" value=""/>' +
                '<label class="control-label col-md-2"><a class="btn btn-sm btn-primary-outline delete-row">删除</a></label>' +
                '<div class="col-md-1">' +
                '<input class="form-control amountClass" name="amount" id="1" <c:if test=""> value="" </c:if> placeholder="请输入红包金额" type="text">' +
                '</div>' +
                '<div class="col-md-1">' +
                '<label class="label label-info" style="font-size: 16px;">红包金额</label>' +
                '</div>' +
                '<div class="col-md-1">' +
                '<input class="form-control limitAmountClass" name="limitAmount" id="2" <c:if test=""> value="" </c:if> placeholder="请输入起投金额" type="text">' +
                '</div>' +
                '<div class="col-md-1">' +
                '<label class="label label-info" style="font-size: 16px;">起投金额</label>' +
                '</div>' +
                '<div class="col-md-1">' +
                '<label class="label label-success" style="font-size: 16px;">使用期限</label>' +
                '</div>' +
                '<div class="col-md-1">' +
                '<input class="form-control monthTypeClass" name="monthType" id="2" <c:if test=""> value="" </c:if> placeholder="最低投资天数" type="text">' +
                '</div>' +
                '<div class="col-md-1">' +
                '<label class="label label-success" style="font-size: 16px;">≥天</label>' +
                '</div></div>';
        	}else{
        		temp = '<div class="form-group checkGroup" id="row' + (index) + '">' +
                '<input class="hongbaoClass" type="hidden" name="templateId" value=""/>' +
                '<label class="control-label col-md-2"><a class="btn btn-sm btn-primary-outline delete-row">删除</a></label>' +
                '<div class="col-md-1">' +
                '<input class="form-control pull-left amountClass" name="amount" id="1" readonly value="0" type="text">' +
                '</div>' +
                '<div class="col-md-1">' +
                '<label class="label label-info" style="font-size: 16px;">红包金额</label>' +
                '</div>' +
                '<div class="col-md-1">' +
                '<input class="form-control limitAmountClass" name="limitAmount" id="2" onchange="changeHongBaoMoney('+index+')" onkeyup="changeHongBaoMoney('+index+')" <c:if test=""> value="" </c:if> placeholder="请输入起投金额" type="text">' +
                '</div>' +
                '<div class="col-md-1">' +
                '<label class="label label-info" style="font-size: 16px;">起投金额</label>' +
                '</div>' +
                '<div class="col-md-1">' +
                '<label class="label label-success" style="font-size: 16px;">使用期限</label>' +
                '</div>' +
                
                
                '<div class="col-md-1">' + 
                '<select class="form-control select2able monthTypeClass" name="monthType" onchange="changeHongBaoMoney('+index+')">' +
                '<option value="0.0015" selected>30</option>' +
                '<option value="0.003">60</option>' +
                '<option value="0.005">90</option>' +
                '<option value="0.0055">120</option>' +
                '<option value="0.006">180</option>' +
                '<option value="0.005">240</option>' +
                '<option value="0.006">270</option>' +
                '<option value="0.005">360</option>' +
            	'</select>' +
                '</div>' +
                
                '<div class="col-md-1">' +
                '<label class="label label-success" style="font-size: 16px;">≥天</label>' +
                '</div></div>';
        	}
            
            index = index + 1;
            $(temp).appendTo($("#hongbaoList"));
        });

		
        $("#createTime").datepicker({
            format: 'yyyy-mm-dd',
            autoclose : true
        }).on('changeDate',function(e){  
            var startTime = e.date;  
            $('#expireTime').datepicker('setStartDate',startTime);  
        });
        
        $("#expireTime").datepicker({
            format: 'yyyy-mm-dd',
            autoclose : true
        }).on('changeDate',function(e){  
            var endTime = e.date;  
            $('#createTime').datepicker('setEndDate',endTime);  
        });
        
        
        
        /* $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        }); */
        $('.select2').select2();
        var option = {
            placeholder: "请输入用户昵称、手机号或者真实姓名搜索",
            minimumInputLength: 0,
            multiple: true,
            ajax: {
                url: "${basePath}user/list/app/usable",
                dataType: 'json',
                quietMillis: 100,
                data: function (term) {
                    return {
                        username: term, //search term
                    };
                },
                results: function (data) {
                    return {results: data};
                }
            },
            formatResult: function (object, container, query) {
                return object.username + "(" + object.phone + ")";
            },
            formatSelection: function (object, container) {
                //选中时触发
                return object.username + "(" + object.phone + ")";
            },
            escapeMarkup: function (m) {
                return m;
            } // we do not want to escape markup since we are displaying html in results
        };
        $('#selectUser').select2(option);
        $("#validate-form").validate({
            ignore: "",
            rules: {
                name: {
                    required: true
                }
            },
            messages: {
                name: {
                    required: "请输入银行名称",
                    maxlength: "标题不能超过64个字符"
                }

            }
        });
        var activityOption = {
            placeholder: "请选择活动标题",
            minimumInputLength: 0,
            ajax: {
                url: "${basePath}activity/getActivityTitle",
                dataType: 'json',
                quietMillis: 100,
                data: function (term) {
                    return {
                        username: term, //search term
                    };
                },
                results: function (data) {
                    return {results: data};
                }
            },
            initSelection: function (element, callback) {
                //初始化赋值
                callback({id: '', name: '全部活动'});//调用formatSelection
            },
            formatResult: function (object, container, query) {
                return object.name;
            },
            formatSelection: function (object, container) {
                //选中时触发
                var id = object.id;
                $('#activityId').val(id);
                return object.name;
            },
            escapeMarkup: function (m) {
                return m;
            } // we do not want to escape markup since we are displaying html in results
        };
        $('#activityId').select2(activityOption);

        $('#hongbaoList').on("click", ".delete-row", function () {
            if (confirm('是否删除红包信息？')) {
//                console.log( $(this).parent().parent())
                $(this).parent().parent().remove();
            }
        });
    });

    function checkInput() {
        return true;
    }
    
    
	function changeHongBaoMoney(index){
    	
    	var limitAmount = $("#row"+index).find(".limitAmountClass").val();
    	var rate = $("#row"+index).find(".monthTypeClass").val();
    	if(!limitAmount){
    		limitAmount = 0;
    	}
    	
    	if(isNaN(limitAmount)){
    		limitAmount = 0;
    	}
    	
    	$("#row"+index).find(".amountClass").val(parseInt(limitAmount * rate));
    	
    	//$("#row"+index).find(".amountClass").val((limitAmount * rate).toFixed(2));
    	
    }
	
	function checkIsNum(){
		if(isNaN($("#useCount").val())){
			$("#useCount").val("1");
		}
	}

</script>
</body>
</html>