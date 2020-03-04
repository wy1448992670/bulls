<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>修改密码</title>
</head>
<body>
<div class="modal-shiftfix">
        <!-- Navigation -->
      	<jsp:include page="../common/header.jsp"></jsp:include>
        <!-- End Navigation -->
        <div class="container-fluid main-content">
            <div class="page-title">
                <h1>
                    后台用户管理
                </h1>
            </div>
            <!-- DataTables Example -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="widget-container fluid-height clearfix">
                        <div class="heading">
                            <i class="icon-table"></i>修改密码
                        </div>
                        <div class="widget-content padded clearfix">
                            <form action="${basePath}user/edit/password" method="post" class="form-horizontal" id="validate-form">
                            	<input type="hidden" name="id" value="${user.id }" />
                            	
                            	<div class="form-group">
                                    <label class="control-label col-md-2">旧密码</label>
                                    <div class="col-md-7">
                                        <input class="form-control" name="oldPassword" id="oldPassword" placeholder="请输入旧的密码" type="password">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="control-label col-md-2">新密码</label>
                                    <div class="col-md-7">
                                        <input class="form-control" name="password" id="password" placeholder="请输入新密码" type="password">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="control-label col-md-2">确认密码</label>
                                    <div class="col-md-7">
                                        <input class="form-control" name="password2" placeholder="请再次输入新密码" type="password">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                	<label class="control-label col-md-2"></label>
                                    <div class="col-md-7 text-center">
                                    	<a class="btn btn-default-outline" onclick="javascript:window.history.go(-1);">取消</a>
                                        <button class="btn btn-primary" type="submit">提交</button>
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
    
    <script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
    <script type="text/javascript">
    	$(function(){
    		 $("#validate-form").validate({
    		      rules: {
	   		    	oldPassword: {
	       		          required: true,
	       		          minlength: 5,
	       		          maxlength:20,
	       		          remote: {
	       		        	    url: "${basePath}user/checkOldPassword",     //后台处理程序
	       		        	    type: "get",
	       		        	    dataType: "json",
	       		        	    data: {                     //要传递的数据
	       		        	        oldPassword: function() {
	       		        	            return $("#oldPassword").val();
	       		        	        }
	       		        	    }
	       		        	}
	       		    },
	       		 	password: {
	                    required: true,
	                    checkPwd: true
	                },
	                password2: {
	                    required: true,
	                    checkPwdConfirm: true,
	                    equalTo: "#password"
	                }
    		      },
    		      messages: {
    		    	oldPassword: {
        		          required: "请输入旧的密码",
        		          minlength: "密码至少5个字",
        		          maxlength: "密码不能超过20个字",
        		          remote: "旧密码输入错误"
        		    },
    		        password: {
    		          required: "请输入密码",
    		          minlength: "密码至少5个字",
    		          maxlength: "密码不能超过20个字"
    		        },
    		        password2: {
    		          required: "请再次输入密码",
    		          minlength: "密码至少5个字",
    		          maxlength: "密码不能超过20个字",
    		          equalTo: "两次输入密码不一致"
    		        }
    		      }
    		    });
    		 
    		 $.validator.addMethod("checkPwd",function(value,element,params){  
    	            var checkPwd = /^(?=.*[0-9].*)(?=.*[a-zA-Z].*).{8,20}$/;  
    	            return this.optional(element)||(checkPwd.test(value));  
    	        },"请输入正确的密码(密码必须为8~20位并且包含字母数字)！");  
    	        
    	        $.validator.addMethod("checkPwdConfirm",function(value,element,params){  
    	            var checkPwdConfirm = /^(?=.*[0-9].*)(?=.*[a-zA-Z].*).{8,20}$/;  
    	            return this.optional(element)||(checkPwdConfirm.test(value));  
    	        },"请输入正确的确认密码(密码必须为8~20位并且包含字母数字)！");  
    	});
    </script>
</body>
</html>