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
    <title>短信发送</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                短信管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>短信发送
                    </div>
                    <div class="widget-content padded clearfix">
                        <form method="post" class="form-horizontal" id="validate-form" enctype="multipart/form-data">
                            <div class="form-group">
                                <label class="control-label col-md-2">短信模板</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="tempid" id="selectTemplet">
                                        <option value="" <c:if test="${type == null }">selected</c:if>>请选择模板</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">短信内容</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="content" id="content" placeholder="请输入需要发送的短信内容"/>
                                </div>
                                <div class="col-md-3"><span class="label label-danger">注意！字数需低于75个字！</span></div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">指定到用户</label>

                                <div class="col-md-7">
                                    <input id="selectUser" name="userId" type="hidden"/>
                                </div>
                                <div class="col-md-3"><span class="label label-danger">如不选择用户，则发送给所有用户！</span></div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">批量发送</label>

                                <div class="col-md-7">
                                    <input type="file" name="file" title="选择txt文件" class="btn-file"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7 text-center">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" data-loading-text="努力发送中..." type="button"
                                            id="send">发送
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
        $('.select2able').select2();
        var options = "";
        $.ajax({
            url: '${basePath}sms/listJson',
            type: 'get',
            dataType: "json",
            success: function (data) {
                $.each(data, function (idx, item) {
                    //输出
                    options += '<option value="' + data[idx].id + '">' + data[idx].content + '</option>';
                })
                $(options).appendTo($("#selectTemplet"));
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
        $('#selectTemplet').change(function () {
            var option_chosen = $.trim($(".select2-chosen").html());
            $("#content").val(option_chosen);
        });
        $("#send").click(function () {
            var content = $.trim($("#content").val());
            var selectTemplet = $("#selectTemplet").val();
            if (selectTemplet == '') {
                alert('请选择模板');
                return false;
            }
            if (content == '') {
                alert('请输入内容');
                return false;
            }
            if (confirm("您确定给用户发送短信吗?!")) {
                $("#validate-form").submit();
//     	    		   $.ajax({
// 	    	   	    		url:'${basePath}sms/sendMessage',
// 	    	   		    	data:$('#validate-form').serialize(),
// 	    	   		    	type:"POST",
// 	    	   		    	dataType:"json",
// 	    	   		    	beforeSend:function(){
// 	    	   		    		$("#send").button('loading');
// 	    	   		    	},
// 	    	   		    	success:function(data){
// 	    	   		    		alert(data);
// 	    	   		    	},
// 	    	   		    	complete:function(){
// 	    	   		    		$("#send").button('reset');
// 	    	   		    	}
//     	   	    		});
            }
        });
        if (${!empty flag}) {
            if (${flag==1}) {
                alert("发送成功");
                window.location.href = "${basePath}sms/detailsSMS";
            } else {
                alert("发送失败");
            }
        }
    });
</script>
</body>
</html>