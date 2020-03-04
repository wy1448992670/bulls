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
    <title>添加智投资产包</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/jquery-fileupload/jquery.fileupload.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}js/datetimepicker/bootstrap-datetimepicker.min.css" media="all" rel="stylesheet" type="text/css"/>
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
                新增资产包
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>资产池说明
                    </div>
                    <div class="row">
                    <div class="col-xs-12 col-md-6" style="text-align: center;">
					    	<h2 class="post-title">
					    			资产池的债权资金量
					    	</h2>
					    	<h3 class="post-title">
					    		￥${totalAmount}(元)
					    	</h2>
			       </div>
			       <div class="col-xs-12 col-md-6" style="text-align: center;">
				    	<h2 class="post-title">
				    		当日到期金额
				    	</h2>
				        <h3 class="post-title" >
				              <c:if test="${empty  amount}">
                         	        ￥0
                         	  </c:if>
                         	  <c:if test="${not empty  amount}">
                         	               ￥${amount}
                         	  </c:if>(元)
				        </h3>
				   </div>
				   </div>
				   <hr />
					   <div class="heading">
	                        <i class="icon-plus"></i>新增
	                    </div>
                    <div>
                    <div class="widget-content padded clearfix">
                        <form action="saveMonthGain" method="post" class="form-horizontal" id="validate-form">
                          
                            <div class="form-group">
                                <label class="control-label col-md-2">选择产品:</label>

                                <div class="col-md-7">
                                    <select name="productId" id="productId" class="form-control">
                                    	<option value="" id="product_" class="">--请选择产品--</option>
	                                    <c:forEach items="${productList}" var="pro"> 
	                                    <option value="${pro.id}" id="product_${pro.id}" class="${pro.limitDays}" jjs="${pro.name}">${pro.name}</option>
	                                    </c:forEach>
                                    </select>
                                </div>
                            </div>
                            
                            <input type="hidden" value="0" name="newsUser" id="newsUser"/>

                            <div class="form-group">
                                <label class="control-label col-md-2">产品期限(天):</label>
                                <div class="col-md-7">
                                	<input class="form-control" name="limitDay" id="limitDay"  placeholder="请输入产品期限(天)" type="text" readonly="readonly"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">基础年化:</label>
                                <div class="col-md-7">
                                    <input class="form-control" name="annualized" id="annualized"  placeholder="请输入基础年化" type="text"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">产品加息:</label>
                                <div class="col-md-7">
                                    <input class="form-control" name="annualizedadd" id="annualizedadd"  placeholder="请输入产品加息" type="text"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">募集金额:</label>
                                <div class="col-md-7">
                                    <input class="form-control" name="totalAmount" id="totalAmount"  placeholder="输入的金额应为100的整数倍" type="text" onkeyup="this.value=this.value.replace(/\D/g, '')"/>
                                </div>
                            </div>
                            
                            <div class="form-group">
                            <label class="control-label col-md-2">是否匹配原始资产: </label>
                                <div class="col-md-7">
                                    <input type="checkbox" class="checkbox" name="matchingOriginalProject" id="matchingOriginalProject" value="1"  checked  type="checkbox" style="display:block;margin-top:6px;width:25px;" >
                                </div>
							  </div>
							  
                            <div class="form-group">
                                <label class="control-label col-md-2">标签</label>
                                <div class="col-md-7">
                                		<input class="form-control" name="tag" id="tag" placeholder="请输入标签" type="text"/>
                                </div>
                            </div>
                           
                            <div class="form-group">
                                <label class="control-label col-md-2">开始时间:</label>
                                <div class="col-md-7">
                                    <input class="form-control datepicker"  value="<fmt:formatDate value="${startTime }" pattern="yyyy/MM/dd"/>"
                                               id="startTime" name="startTime" type="text" placeholder="请选择起始时间"/>
                                </div>
                            </div>
                            
                             <div class="form-group">
                                <label class="control-label col-md-2">开始时间说明:</label>
                                <div class="col-md-7">
                                    <span style="color: red;">
                                    		开始时间为该智投项目打包的计划最早时间：</br>
                                    		当开始时间设置为小于当前时间，则添加的打包任务将在随后5秒内被执行；</br>
                                    		当开始时间设置为大于当前时间，则在将来某个整点执行</br>
                                    		例如：设置开始时间为：2018-09-04 10:30:00（当前时间为2018-09-03），打包计划任务执行目前设置为整点执行，则该设置的计划将在2018-09-04 11:00:00被执行</span>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="control-label col-md-2">项目风险等级</label>

                                <div class="col-md-7">
                                    <c:set var="riskEnum" value="<%= com.goochou.p2b.constant.project.ProjectRiskEnum.values() %>"/>
                                    <select class="form-control select2able" name="projectRiskGrade">
                                        <c:forEach var="e" items="${riskEnum}">
                                            <c:if test="${e.star != 0}">
                                                <option value="${e.star}">${e.description}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            
                            
                            <!-- 目前先隐藏 -->
<!--                             <div class="form-group"> -->
<!--                                 <label class="control-label col-md-2">开始时间:</label> -->
<!--                                 <div class="col-md-7"> -->
<%--                                     <input class="form-control datepicker"  value="<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>" --%>
<!--                                                id="startTime" name="startTime" type="text" placeholder="请选择起始时间"/> -->
<!--                                 </div> -->
<!--                             </div> -->

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
<!--                                     <a class="btn btn-default-outline" -->
<!--                                        onclick="javascript:window.history.go(-1);">取消</a> -->
                                    <button class="btn btn-primary" type="submit">马上创建</button>
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
<script src="${basePath}js/jquery-fileupload/jquery.ui.widget.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.fancybox.pack.js" type="text/javascript"></script>
<script src="${basePath}js/datetimepicker/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$("#newsUser").val(0);
	Date.prototype.Format = function (fmt) { //author: meizz
	  var o = {
	    "M+": this.getMonth() + 1, //月份
	    "d+": this.getDate(), //日
	    "h+": this.getHours(), //小时
	    "m+": this.getMinutes(), //分
	    "s+": this.getSeconds(), //秒
	    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
	    "S": this.getMilliseconds() //毫秒
	  };
	  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	  for (var k in o)
	  if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	  return fmt;
	}
    $(function () {
    	 $(".datepicker").datetimepicker({
             format: 'yyyy/mm/dd hh:ii:ss',
             showSecond: true,
             timeFormat: "hh:mm:ss",
             dateFormat: "yy/mm/dd"
         });
    	 
    	 // 选择产品，自动选择对应的锁定期，锁定期不可手动修改
    	 $("#productId").change(function(){
    		 var option = "option[id='product_"+$(this).val()+"']";
    		 var limitDays = $(this).find(option).attr("class");
    		 $("#newsUser").val(0);
    		 if($(this).val() == 4){
    			 $("#newsUser").val(1);
    		 }
    		 $("#limitDay").val(limitDays);
    		 //自动带出数据
    		 switch(limitDays){
    		 case "7":
    			 $("#annualized").val(0.05);
    			 $("#annualizedadd").val(0.01);
    			 break;
    		 case "30":
    			 $("#annualized").val(0.07);
    			 var jjs = $(this).find(option).attr("jjs");
    			 if(jjs == "新手专享"){
    				 $("#annualizedadd").val(0.05);
    			 }else{
    				 $("#annualizedadd").val(0.006);
    			 }
    			 break;
    		 case "90":
    			 $("#annualized").val(0.08);
    			 $("#annualizedadd").val(0.012);
    			 break;
    		 case "180":
    			 $("#annualized").val(0.095);
    			 $("#annualizedadd").val(0.018);
    			 break;
    		 }
    		 $("#tag").val("喜迎国庆");
    		 $("#startTime").val(new Date().Format("yyyy/MM/dd hh:mm:ss"));
    		 $("#totalAmount").val(200000);
    	 });
    	 
    	 
    	 //自定义校验规则
		 $.validator.addMethod("isTotalAmount", function(value, element) {
			 if(value<=0){
				 return false;
			 };
			 var isTen=value%100;
			 if(isTen==0){
				 return true;
			   }else{
				 return false;
		     }
		 }, "请正确金额数据(100的整数倍)");


        $("#validate-form").validate({
            rules: {
                productId: {
                    required: true
                },
                annualized: {
                    required: true,
                    range: [0, 1]
                },
                annualizedAdd: {
                	required: true,
                    range: [0, 1]
                },
                totalAmount:{
                    required: true,
                    digits: true,
                    isTotalAmount: true
                },
//                 tag: {
//                     required: true
//                 },
                startTime: {
               	 required: true,
               	 date : true
                },
                limitDay: {
                	required: true,
                	digits:true
                }
            },
            messages: {
            	productId: {
                    required: "请选择一个产品"
                },
                annualized: {
                    required: "请输入年化利率",
                    range: "请输入0-1之间的数字"
                },
                annualizedAdd: {
                    required: "请输入加息",
                    range: "请输入0-1之间的数字"
                },
                totalAmount: {
                	required: "请输入募集金额",
                	digits: "请输入正确的整数",
                	range: "请输入正确的金额"
                },
//                 tag: {
//                     required: "请输入标签"
//                 },
                startTime: {
                    required: "请输入开始时间",
                    date : "日期格式不正确"
                },
                limitDay: {
                    required: "请输入授权服务期限",
                    digits: "请输入正确的整数"
                }
             }
        });
    });
</script>
</body>
</html>