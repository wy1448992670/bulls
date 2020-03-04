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
    <title>派发红包</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <style>
    	#temp{
    		display:none;
    	}
    	#shopTemp{
    		display:none;
    	}
    	#pinTemp{
    		display:none;
    	}
    	#userID{
    		display:none;
    	}
    	#hongbaoTitle{
    		display:none;
    	}
    	#amountTitle{
    		display:none;
    	}
    	#days{
    		display:none;
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
                运营管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i> 派发红包
                    </div>
                    <div class="widget-content padded clearfix">
                        <form method="post" class="form-horizontal" id="validate-form" enctype="multipart/form-data">
                            <div class="form-group">
                                <label class="control-label col-md-2">红包类型</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="type" id="hbType">
                                        <option value="2">牧场红包</option>
                                        <option value="1">现金红包</option>
                                        <option value="3">商城券</option>
                                        <option value="4">拼牛红包</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="amountTitle">
                                <label class="control-label col-md-2">红包金额</label>

                                <div class="col-md-7">
                                    <input class="form-control" value="" name="amount" id="amount"
                                           placeholder="请输入需要派发的金额" type="text">
                                </div>
                            </div>
                            <div class="form-group" id="hongbaoTitle">
                                <label class="control-label col-md-2">红包标题</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="cashTitle" id="cashTitle"
                                           placeholder="现金红包请输入标题" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">红包站内信内容</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="content"
                                           placeholder="发送个人消息的内容" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">手机短信内容</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="message"
                                           placeholder="发送短信的内容" type="text">
                                </div>
                            </div>
                            <!-- <div class="form-group" id="userID">
                                <label class="control-label col-md-2">指定到用户</label>

                                <div class="col-md-7">
                                    <input id="selectUser" name="userId" type="hidden"/>
                                </div>
                                <div class="col-md-3"><span class="label label-danger">如不选择用户，则发送给所有用户！</span></div>
                            </div> -->
                            <div class="form-group" id="days">
                                <label class="control-label col-md-2">有效期</label>

                                <div class="col-md-7">
                                    <input class="form-control" value="0" name="days"
                                           placeholder="请输入有效天数" type="text">
                                </div>
                            </div>
                            <div class="form-group" id="department">
                                <label class="control-label col-md-2">选择部门发送<br><em style="color:red;">(发送该部门下所有用户<br>选择部门和批量发送不可同时进行)</em></label>

                                <div class="col-md-7">
                                    <select class="select2able" name=departmentId id="departmentId">
                                        <option value="">请选择部门</option>
										<c:forEach var="department" items="${departments}">
											<option value="${department.id }" <c:if test="${departmentId == department.id }">selected</c:if>>${department.name }</option>
										</c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" id="file">
                                <label class="control-label col-md-2">批量发送</label>

                                <div class="col-md-4">
                                    <input type="file" name="file" title="选择txt文件"/>
                                </div>
                                
                                <div class="col-md-3" id="investTemp">
                                    <a class="btn btn-warning pull-right hidden-xs" href="${basePath}template/muchang.txt" download="牧场红包模板"><i class="fa fa-arrow-circle-down"></i>下载牧场红包模版(.txt格式)</a>
                                </div>
                                <div class="col-md-3" id="temp">
                                    <a class="btn btn-danger pull-right hidden-xs" href="${basePath}template/xianjin.txt" download="现金红包模版">下载现金红包模版(.txt格式)</a>
                                </div>
                                <div class="col-md-3" id="shopTemp">
                                    <a class="btn btn-primary pull-right hidden-xs" href="${basePath}template/shangcheng.txt" download="商城券模版">下载商城券模版(.txt格式)</a>
                                </div>
                                <div class="col-md-3" id="pinTemp">
                                    <a class="btn btn-success pull-right hidden-xs" href="${basePath}template/pinniu.txt" download="拼牛红包模版">下载拼牛红包模版(.txt格式)</a>
                                </div>
                            </div>
                            <div class="form-group" id="hongbaoTemp">
                                <label class="control-label col-md-2">添加红包模版</label>
								<input type="hidden" name="template" id="template"/>
								
                                <div class="col-md-7">
                                    <a class="btn btn-sm btn-primary-outline" id="add-row">点击添加</a>
                                </div>
                            </div>
                            <div id="hongbaoList" name="template" class="form-group">
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7 text-center">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" data-loading-text="努力派发中..." type="button"
                                            id="send">派发
                                    </button>
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
<script type="text/javascript">

    $(function () {
//         $('#amountTitle').hide();
//        // $('#hongbaoTemp').show();
//        // $('#hongbaoList').show();
//         $('#days').hide();
//         $('#hongbaoTitle').hide();
//       //  $('#file').show();
//         $('#userID').hide();
//        // $('#investTemp').show();
//         //$('#temp').hide();
//         //$('#shopTemp').hide();
        $('.select2able').select2();
        
        $('#hbType').change(function () {
            var type = $(this).val();
            $("#hongbaoList").html("");
            if (type == 1) {
                //$('#amountTitle').show();
                $('#hongbaoTemp').hide();
                $('#hongbaoList').hide();
                $('#temp').show();
                $('#days').show();
                $('#hongbaoTitle').show();
                // $('#file').hide();
                //$('#userID').show();
                $('#investTemp').hide();
                $('#shopTemp').hide();
                $('#pinTemp').hide();
                $('#department').hide();
            } else if(type == 2) {
                $('#amountTitle').hide();
                $('#hongbaoTemp').show();
                $('#hongbaoList').show();
                $('#temp').hide();
                $('#hongbaoTitle').hide();
                $('#days').hide();
                //$('#file').show();
                //$('#userID').hide();
                $('#investTemp').show();
                $('#shopTemp').hide();
                $('#pinTemp').hide();
                $('#department').show();
            } else if(type == 4){
            	$('#amountTitle').hide();
                $('#hongbaoTemp').show();
                $('#hongbaoList').show();
                $('#temp').hide();
                $('#hongbaoTitle').hide();
                $('#days').hide();
                //$('#file').show();
                //$('#userID').hide();
                $('#investTemp').hide();
                $('#shopTemp').hide();
                $('#pinTemp').show();
                $('#department').show();
            } else {
            	$('#amountTitle').hide();
                $('#hongbaoTemp').show();
                $('#hongbaoList').show();
                $('#temp').hide();
                $('#hongbaoTitle').hide();
                $('#days').hide();
                //$('#file').show();
                //$('#userID').hide();
                $('#investTemp').hide();
                $('#shopTemp').show();
                $('#pinTemp').hide();
                $('#department').show();
            }
        });
        
        $('#departmentId').change(function () {
        	var departmentId = $(this).val();
            $("#hongbaoList").html("");
            if (departmentId != "") {
                $('#file').hide();
            } else if (departmentId == "") {
            	$('#file').show();
            }
        });

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
        $("#send").click(function () {
        	if($('.isNull').length){
        		var flag = 0;
        		$.each($('.isNull'),function(index,ele){
        			console.log($(ele).val());
        			if(!$.trim($(ele).val())){
        				flag++;
        			}
        		});
        		if(flag){
        			alert('除红包标题外，其余必填');
        			return false;
        		}
        	}
//            var text = $.trim($("#amount").val());
//
//            if ($('#hbType').val() == 1) {
//                if (text == '') {
//                    alert('请输入需要派发的金额');
//                    return false;
//                }
//            }
			$("#send").attr("disabled", true);

            if ($('#hbType').val() == 2) {
                if ($([name = days]).val() < 0) {
                    alert('有效期必须大于0');
                    return false;
                }
            }
            var group = $('.checkGroup');
            if (group.length == 0) {
//                if ($('#amount').val() == '' || $('#amount').val() > 1) {
//                    alert('现金红包不能大于1，不能为空');
//                    return false;
//                }
            }


            var templates = [];
            for (var i = 0; i < group.length; i++) {
                var template = {};
                var o = $('.checkGroup').eq(i);
                var type =  $('#hbType').val();
                template.limitAmount = o.find('.limitAmountClass')[0].value;
                template.amount = o.find('.amountClass')[0].value;
                template.templateId = o.find('.hongbaoClass')[0].value;
                template.descript = o.find('.titleClass')[0].value;
                template.effectiveDay = o.find('.effectiveDayClass')[0].value;
                if(type == 2 || type == 4){
                	template.minInvestDay = o.find('.minInvestDayClass')[0].value;
                }
                templates.push(template);
            }
            $('#template').val(JSON.stringify(templates));
            if (confirm("您确定给用户派发红包吗?!")) {
                $("#validate-form").submit();
                <%--$.ajax({--%>
                <%--url: '${basePath}hongbao/send',--%>
                <%--data: $('#validate-form').serialize(),--%>
                <%--type: "POST",--%>
                <%--dataType: "json",--%>
                <%--beforeSend: function () {--%>
                <%--$("#send").button('loading');--%>
                <%--},--%>
                <%--success: function (data) {--%>
                <%--alert(data);--%>
                <%--},--%>
                <%--complete: function () {--%>
                <%--$("#send").button('reset');--%>
                <%--}--%>
                <%--});--%>
            }
        });
        var index = 0;
        var temp = "";
        $('#add-row').on('click', function () {
        	var type =  $('#hbType').val();
        	var str = '';
        	if(type == 2 || type == 4){
        		str = '<div class="col-md-1">' +
                '<input class="form-control pull-left minInvestDayClass isNull" name="minInvestDay" id="2" <c:if test=""> value="" </c:if> placeholder="最低投资天数" type="text">' +
                '</div>' +
                '<div class="col-md-1">' +
                '<label class="label label-success" style="font-size: 16px;">≥天</label>' +
                '</div>';
        	}else{
        		str='';
        	}
            temp = '<div class="form-group checkGroup" id="row' + (index) + '">' +
                    '<input class="hongbaoClass" type="hidden" name="templateId" value=""/>' +
                    '<label class="control-label col-md-2"><a class="btn btn-sm btn-primary-outline delete-row">删除</a></label>' +
                    '<div class="col-md-1">' +
                    '<input class="form-control pull-left titleClass" name="descript" id="1" <c:if test=""> value="" </c:if> placeholder="请输入红包标题" type="text">' +
                    '</div>' +
                    '<div class="col-md-1">' +
                    '<label class="label label-danger" style="font-size: 16px;">红包标题</label>' +
                    '</div>' +
                    '<div class="col-md-1">' +
                    '<input class="form-control pull-left amountClass isNull" name="amount" id="2" <c:if test=""> value="" </c:if> placeholder="请输入红包金额" type="text">' +
                    '</div>' +
                    '<div class="col-md-1">' +
                    '<label class="label label-info" style="font-size: 16px;">红包金额</label>' +
                    '</div>' +
                    '<div class="col-md-1">' +
                    '<input class="form-control pull-left limitAmountClass isNull" name="limitAmount" id="2" <c:if test=""> value="" </c:if> placeholder="请输入起投金额" type="text">' +
                    '</div>' +
                    '<div class="col-md-1">' +
                    '<label class="label label-info" style="font-size: 16px;">起投金额</label>' +
                    '</div>' + 
                    '<div class="col-md-1">' +
                    '<input class="form-control pull-left effectiveDayClass isNull" name="effectiveDay" id="2" <c:if test=""> value="" </c:if> placeholder="请输入红包期限" type="text">' +
                    '</div>' +
                    '<div class="col-md-1">' +
                    '<label class="label label-success" style="font-size: 16px;">天</label>' +
                    '</div>' + str +
                    '</div>';
            index = index + 1;
            $(temp).appendTo($("#hongbaoList"));
        });

        if (${!empty flag}) {
            if (${flag==1}) {
                alert("发送成功");
                window.location.href = "${basePath}hongbao/list";
            } else if(${flag==0}) {
                alert("发送失败");
            } else if(${flag==-1}){
            	alert("发送失败,请添加红包模版");
            } else if(${flag==-2}){
            	alert("发送失败,请选择用户");
            } else if(${flag==-3}){
            	alert("发送失败,只能单独上传文件或选择部门");
            }
        }


        $('#hongbaoList').on("click", ".delete-row", function () {
            if (confirm('是否删除红包信息？')) {
//                console.log( $(this).parent().parent())
                $(this).parent().parent().remove();
            }
        });

    });


</script>
</body>
</html>