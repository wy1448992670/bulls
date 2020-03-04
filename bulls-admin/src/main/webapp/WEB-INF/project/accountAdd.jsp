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
    <title>质押应收账款清单添加</title>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                公告管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>质押应收账款清单添加
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}project/account/add" method="post" class="form-horizontal"
                              id="validate-form">
                            <div class="form-group">
                                <label class="control-label col-md-2">配置项目</label>

                                <div class="col-md-7">
                                    <input id=projectId name="projectId" type="hidden" placeholder="请选择你要配置的项目"
                                           required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">第三方债务人名称及通讯信息</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="userInfo" id="userInfo"
                                           placeholder="请输入第三方债务人名称及通讯信息"
                                    >
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">交易合同名称</label>

                                <div class="col-md-7">
                                    <input type="text" class="form-control" id="name" name="name"
                                           placeholder="请输入交易合同名称" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">交易合同编号</label>

                                <div class="col-md-7">
                                    <input class="form-control" id="orderNo" name="orderNo" type="text" placeholder="请输入交易合同编号"
                                           required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">签署日期</label>

                                <div class="col-md-7">
                                    <input class="form-control datepicker" id="signDate" name="signDate" type="text"
                                           placeholder="请选择签署日期"
                                           required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">应收账款基础交易</label>

                                <div class="col-md-7">
                                    <input class="form-control" id="basicAmount" name="basicAmount" type="number" step="0.01"
                                           placeholder="请输入应收账款基础交易"
                                           required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">应收账款金额</label>

                                <div class="col-md-7">
                                    <input class="form-control" id="amount" name="amount" type="number" step="0.01"
                                           placeholder="请输入应收账款金额"
                                           required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">应收账款到期日</label>

                                <div class="col-md-7">
                                    <input class="form-control datepicker" id="endTime" name="endTime" type="text"
                                           placeholder="请选择应收账款到期日"
                                           required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">状态</label>

                                <div class="col-md-7">
                                    <select class="select2" name="status">
                                        <option value="0">启用
                                        </option>
                                        <option value="1">停止
                                        </option>
                                    </select>
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-success" type="submit" id="pub">提交</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- end DataTables Example -->
</div>
</div>

<script src="${basePath }js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });
        $('.select2').select2();
        var option = {
            placeholder: "请选择项目名称",
            minimumInputLength: 0,
            ajax: {
                url: "${basePath}project/getProjectAccount",
                dataType: 'json',
                quietMillis: 100,
                data: function (term) {
                    return {
                        username: term,
                    };
                },
                results: function (data) {
                    return {results: data};
                }
            },
            initSelection: function (element, callback) {
            },
            formatResult: function (object, container, query) {
                return object.title;
            },
            formatSelection: function (object, container) {
                //选中时触发
                var id = object.id;
                $('#projectId').val(id);
                $.ajax({ 
                	url: "${basePath}project/getProjectAccountDetails",
                	data:{id:id},
                	success: function(res){
                		$("#userInfo").val(res.userInfo);
                		$("#name").val(res.name);
                		$("#orderNo").val(res.orderNo);
                		$("#signDate").val(dateFormat(res.signDate,"yyyy-MM-dd"));
                		$("#basicAmount").val(res.basicAmount);
                		$("#amount").val(res.amount);
                		$("#endTime").val(dateFormat(res.endTime,"yyyy-MM-dd"));
                	}
                });
                return object.title;
            },
            escapeMarkup: function (m) {
                return m;
            } // we do not want to escape markup since we are displaying html in results
        };

        $('#projectId').select2(option);


    });
    
    /*
    格式化日期
    */

   function dateFormat(date, format) {
       date = new Date(date);
       var map = {
           "M": date.getMonth() + 1, //月份
           "d": date.getDate(), //日
           "h": date.getHours(), //小时
           "m": date.getMinutes(), //分
           "s": date.getSeconds(), //秒
           "q": Math.floor((date.getMonth() + 3) / 3), //季度
           "S": date.getMilliseconds() //毫秒
       };
       format = format.replace(/([yMdhmsqS])+/g, function (all, t) {
           var v = map[t];
           if (v !== undefined) {
               if (all.length > 1) {
                   v = '0' + v;
                   v = v.substr(v.length - 2);
               }
               return v;
           } else if (t === 'y') {
               return (date.getFullYear() + '').substr(4 - all.length);
           }
           return all;
       });
       return format;
   }
    
    
    
   $("#validate-form").validate({
       rules: {
    	   name: {
               required: true,
               maxlength: 200,
               remote: {
                   url: "/project/checkAccountName",     //后台处理程序
                   type: "get",
                   dataType: "json",
                   data: {                     //要传递的数据
                       username: function () {
                           return $("#name").val();
                       }
                   }
               }
           },
           
           orderNo: {
               required: true,
               maxlength: 200,
               remote: {
                   url: "/project/checkAccountOrderNo",     //后台处理程序
                   type: "get",
                   dataType: "json",
                   data: {                     //要传递的数据
                       username: function () {
                           return $("#orderNo").val();
                       }
                   }
               }
           }
   
   			
       },
       messages: {
    	   name: {
               required: "请输入交易合同名称",
               maxlength: "项目名不超过200个字符",
               remote: "交易合同名称已存在"
           },
           
           orderNo: {
               required: "请输入交易合同编号",
               maxlength: "项目名不超过200个字符",
               remote: "交易合同编号已存在"
           }
       }
   });


</script>
</body>
</html>