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
	<link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css" />
	<title>微信抢红包-创建红包</title>
	<style type="text/css">
		.table td,.table th{text-align: center;}
		.heading label{font-size: 18px;}
	</style>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <link rel="stylesheet" href="${basePath}css/style.css">
 
</head>
<body>
<div class="modal-shiftfix">
        <!-- Navigation -->
      	<jsp:include page="../common/header.jsp"></jsp:include>
        <!-- End Navigation -->
        <div class="container-fluid main-content">
            <!-- end DataTables Example -->
            <div class="row">
            	<div class="col-lg-12">
	            	<div class="widget-container fluid-height clearfix">
	                     <div class="heading">
                         	微信抢红包-创建红包
	                     </div>
	                     <div class="widget-content padded clearfix">
	                        <form action="${basePath}report/wx/hb/add" method="post" class="form-horizontal" id="validate-form">
                                <div class="form-group">
                                    <label class="control-label col-md-2">红包金额</label>
                                    <div class="col-md-7">
                                        <input class="form-control" name="amount" id="amount" placeholder="请输入红包金额" type="text">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="control-label col-md-2">红包份数</label>
                                    <div class="col-md-7">
                                        <input class="form-control" name="num" id="num" placeholder="请输入红包份数" type="text">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                	<label class="control-label col-md-2"></label>
                                    <div class="text-center col-md-7">
                                    	<a class="btn btn-default-outline" onclick="javascript:window.history.go(-1);">取消</a>
                                        <button class="btn btn-primary" type="submit">创建红包</button>
                                    </div>
                                </div>
                                
                                 <div class="form-group">
                                    <label class="control-label col-md-2">提示：</label>
                                    <div class="col-md-2">
                                    	<p class="form-control-static text-danger">
                                    		创建红包后红包默认过期时间为3天
                                    	</p>
                                    </div>
                                </div>
                            </form>
	                     </div>
	                 </div>
	           </div>
            </div>
        </div>
    </div>
    
    <script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
    <script type="text/javascript">
   		$(function(){
   		 $("#validate-form").validate({
   	      rules: {
   	        amount: {
   	          required:true,
   	          digits:true
   	        },
   	        num: {
   	        	required:true,
   	        	digits:true,
   	        	min:1
   	        }
   	      },
   	      messages: {
   	    	  amount: {
   		          required:"请输入红包金额",
   		          digits:"请输入合法的整数"
   		        },
   		        num: {
   		        	required:"请输入红包份数",
   		        	digits:"请输入合法的整数",
   		        	min:"份数最低不能小于1"
   		        }
   	      }
   	    });
   		});
	</script>
</body>
</html>