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
    <title>导入资产</title>
    <style type="text/css">
    .form-group [class*="col-"] {
      padding-top: 6px;
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
              	 智投项目管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>智投详细
                    </div>
                    <div class="widget-content padded clearfix form-horizontal">
                    		<input type="hidden" id="prackgeId" value="${prackgeId}">
                            <div class="form-group">
                                <label class="control-label col-md-2">产品期限</label>
                                <div class="col-md-7">
                                  ${produtcList.limitDays}天
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="control-label col-md-2">募集总额</label>
                                <div class="col-md-7">
                                  ${produtcList.totalAmount}
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">年化利率</label>
                                <div class="col-md-7">
                                   ${produtcList.annualized}
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">加息利率</label>

                                <div class="col-md-7">
                                    ${produtcList.addAnnualized}
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">实际发放金额</label>
                                <div class="col-md-7">
                                                                   ￥ ${produtcList.investedAmount}(元)
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">标题</label>

                                <div class="col-md-7">
                                    ${produtcList.title}
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">发布时间</label>
                                <div class="col-md-8">
                                   <fmt:formatDate value="${produtcList.startTime}" pattern="yyyy-MM-dd  hh:mm:ss"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">募集完成时间</label>
                                <div class="col-md-7">
                                    <fmt:formatDate value="${produtcList.endTime}" pattern="yyyy-MM-dd  hh:mm:ss"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">投资人数</label>
                                <div class="col-md-7">
                                   ${countInv}人 <a class="btn btn-sm btn-primary-outline" href="getMonthGainInvestment?prackgeId=${produtcList.id}" >查看详细</a>
                                </div>
                            </div>
                    </div>
                </div>
            </div>
            <div class="widget-container fluid-height ">
                    <div class="heading">
                        <i class="icon-table"></i>可导入资产
                    </div>
                    <div class="widget-content padded clearfix">
                    
                    	<!-- 检索框 -->
                    	<form action="importAssetMarks" id="form" class="form-inline hidden-xs col-lg-5 pull-right" method="get">
                    		<input type="hidden" name="prackgeId" value="${prackgeId}">
                    		
                    		<div class="row">
                    			<div class="form-group col-md-6">资产类型：</div>
								<div class="form-group col-md-3">
									<select class="select2able" name="projectType" id="projectType">
                                        <option value="1" <c:if test="${projectType eq 1}">selected</c:if>>退出资产</option>
                                        <option value="2" <c:if test="${projectType eq 2}">selected</c:if>>原始资产</option>
                                    </select>
								</div>
                    		</div>
                    		
                    		<div class="row">
								<div class="form-group col-md-6">归属锁定期：</div>
								<div class="form-group col-md-3">
									<select class="select2able" name="limitDays" id="limitDays">
                                        <option value="">-全部-</option>
                                        <c:forEach  items="${limitDaysList}" var="li" >
                                        <option value="${li.limitDays}" <c:if test="${limitDays eq li.limitDays}">selected</c:if>>${li.limitDays}</option>
                                        </c:forEach>
                                    </select>
								</div>
								<div class="form-group col-md-3">
									<button class="btn btn-primary pull-right" type="button" onclick="aa()"> 搜索</button>
								</div>
							</div>
                            <input type="hidden" name="page" value="${page}" id="page"/>
                    	</form>
                    
                    	<table>
	                     <tr>
	                    	<td>已勾选</td>
	                    	<td>&nbsp;&nbsp;资产数:</td>
	                    	<td id="sum">0</td>
	                    	<td>&nbsp;&nbsp;总金额:</td>
	                    	<td id="sumAmount">0</td>
	                     </tr>
                    	</table>
                    
                        <table class="table table-bordered trade" id="trade">
                            <thead>
                            <tr align="center">
                            	<th align="center"><input type="checkbox" id="selectAll" style="display:block;margin: auto;"/></th>
                                <th>序号</th>
                                <th>ID</th>
                                <th>标题</th>
                                <th><c:if test="${projectType eq 1}">原</c:if>标期限（天）</th>
                                <c:if test="${projectType eq 1}">
                                <th>剩余期限（天）</th>
                                <th>所属智投锁定期</th>
                                </c:if>
                                <th>总金额(元)</th>
                                <c:if test="${projectType eq 1}">
                                <th>债转时间</th>
                                </c:if>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${projectList }" var="pro" varStatus="index">
	                             <tr>
	                              <td align="center">
	                              <input type="checkbox" name="ids" value="${pro.id}" style="display:block;"/>
	                              <input type="hidden" id="money_${pro.id}" value="${pro.total_amount}">
	                              </td>
	                              <td>${index.index+1}</td>
	                              <td>${pro.id}</td>
	                              <td>
	                              	<c:if test="${projectType eq 2}">${pro.title}</c:if>
	                              	<c:if test="${projectType eq 1}">${pro.true_title}</c:if>
	                              </td>
	                              <td>
		                              <c:if test="${projectType eq 2}">${pro.limit_days}</c:if>
		                              <c:if test="${projectType eq 1}">${pro.limitDays}</c:if>
	                              </td>
	                              <c:if test="${projectType eq 1}">
	                              <td>${pro.diffDays}</td>
	                              <td>${pro.mgpp_limitDays}</td>
	                              </c:if>
	                              <td>${pro.total_amount}</td>
	                               <c:if test="${projectType eq 1}">
	                              <td><fmt:formatDate value="${pro.create_time}" pattern="yyyy-MM-dd  hh:mm:ss"/></td>
	                               </c:if>
	                             </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <ul id="pagination" style="float: right"></ul>
                        <button class="btn btn-primary pull-left" style="float:left" type="button" id="importAssetMarks" onclick="importAssetMarks();" >批量导入</button>
                    	 <span style="color: red;">下次自动打包时间（导入操作时间请不要与此时间冲突）：${nextFireDate}</span>
                    </div>
                </div>
        </div>
    </div>
    <!-- end DataTables Example -->
</div>
</div>

<script src="${basePath }js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });
        
        // 全选动作
        $("#selectAll").change(function(){
        	var checkbox = $("input[type=checkbox][name='ids']");
        	if($(this).prop("checked")){
        		checkbox.prop("checked", true);
        		sumDatas();
        	}else{
        		checkbox.prop("checked", false);
        		sumDatas();
        	}
        });
        
        
        // 获取选择的资产类型，如果为原始资产，期限需要进行初始化
        if($("#projectType").val()==2){
 		   $("#limitDays").val("");
 		   $("#limitDays").attr("disabled",true);
 	   }else{
 		   $("#limitDays").removeAttr("disabled");
 	   }
        
       $("#projectType").change(function(){
    	   if($(this).val()==2){
    		   $("#limitDays").val("");
    		   $("#limitDays").attr("disabled",true);
    	   }else{
    		   $("#limitDays").removeAttr("disabled");
    	   }
       });
        
        
        // 分页
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "importAssetMarks?prackgeId=${prackgeId}&limitDays=${limitDays}&projectType=${projectType}&page=" + page;
            }
        });
        
        // 勾选取消动作，触发的JS
        $("input[type=checkbox][name='ids']").change(function(){
        	// 已经勾选的数量
        	$("#sum").html($("input[type=checkbox][name='ids']:checked").length);
        	// 已经勾选的债转金额（遍历累计）
        	var sumAmount = 0;
        	$.each($("input[type=checkbox][name='ids']:checked"),function(i){
        		sumAmount = parseFloat(sumAmount) +  parseFloat($("#money_"+$(this).val()).val());
            });
        	$("#sumAmount").html(sumAmount);
        });
        
       
        
    });
    
    function aa() {
        $("#form").submit();
    }
    
    // 进行选中数据计算
    function sumDatas(){
    	// 已经勾选的数量
    	$("#sum").html($("input[type=checkbox][name='ids']:checked").length);
    	// 已经勾选的债转金额（遍历累计）
    	var sumAmount = 0;
    	$.each($("input[type=checkbox][name='ids']:checked"),function(i){
    		sumAmount = parseFloat(sumAmount) +  parseFloat($("#money_"+$(this).val()).val());
        });
    	$("#sumAmount").html(sumAmount);
    }
    
    
    // 批量导入资产
    // 设置防止重复提交标识位
    var isClick=0;
    function importAssetMarks(){
    	
    	if(isClick!=0){
    		return;
    	}
    	
    	// 进行提交前的提示
    	if(!confirm("确定进行批量导入操作？")){
    		return;
    	}
    	
    	
    	// 选中的值存储变量
    	var ids='';
    	var splitStr=',';
    	
    	if($("input[type=checkbox][name='ids']:checked").length<1){
    		alert("请选择需要操作的数据！");
    		return;
    	}
    	
   	  	$.each($("input[type=checkbox][name='ids']:checked"),function(i){
            if(i==0){
            	ids = ids + $(this).val();
            }else{
            	ids = ids +splitStr+$(this).val();
            }
        });
   	  	var prackgeId = $("#prackgeId").val();
    	$.ajax({
            url: "importAssetMarks?r="+Math.random(),
            type: "post",
            dataType: "json",
            async: false,
            data: {prackgeId:prackgeId,ids:ids,splitStr:splitStr},
            beforeSend:function(){
				//触发ajax请求开始时执行
            	isClick =1;
            	$("#importAssetMarks").attr("disabled",true); 
			},
            success: function(oData) {
            	console.log(oData);
                // 查询失败
                if (!oData.success) {
                	// 是未登录还是其他原因
                	alert("操作失败！");
                	return;
                }
                // 弹出确定框
                alert("操作成功!");
                // 确定成功，刷新页面
                window.location.reload();
                
            },
            complete:function(){
				//ajax请求完成时执行
            	isClick =0;
            	$("#importAssetMarks").attr("disabled",false); 
			}
        });
    }
  
</script>
</body>
</html>