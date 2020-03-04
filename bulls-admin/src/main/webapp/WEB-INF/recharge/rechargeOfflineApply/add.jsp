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
    <title>添加线下充值申请</title>
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
    <jsp:include page="../../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                线下充值
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>线下充值申请
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}recharge/rechargeOfflineApply/save" method="post" class="form-horizontal"
                              id="validate-form" ENCTYPE="multipart/form-data">
                            <div class="form-group">
                                <label class="control-label col-md-2">
                                	线下转账凭证<br/>
                                    <em style="color:red;">(图片尺寸：750px*412px)</em>
                                </label>

                                <div class="col-md-4">
                                    <div class="fileupload fileupload-new" data-provides="fileupload">
                                        <div class="fileupload-new img-thumbnail" style="width: 200px; height: 150px;">
                                            <img alt="" src="${aPath}images/no-image.gif">
                                        </div>
                                        <div class="fileupload-preview fileupload-exists img-thumbnail"
                                             style="width: 1080px; max-height: 620px"></div>
                                        <div>
	                                                <span  onclick="setName()" class="btn btn-default btn-file">
	                                                    <span class="fileupload-new">选择图片</span>
	                                                    <span class="fileupload-exists">修改</span>
	                                                    <input type="file" name="file"  />
	                                                </span><a class="btn btn-default fileupload-exists"
                                                              data-dismiss="fileupload" href="#">删除</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">发起人</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="sourcer" id="sourcer" placeholder="请输入发起人" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">充值金额(元)</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="money" id="money" placeholder="请输入充值金额" type="number">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">充值用户</label>
                                <div class="col-md-7" >
                                	<input type="hidden" id="userId" name="userId" >
										
									<div class="input-group">
								      <input type="text" class="form-control" id="userName" name="userName" placeholder="请选择充值用户" readonly="readonly">
								      <span class="input-group-btn">
								        <button class="btn btn-warning" type="button" data-toggle="modal" data-target="#addRelationDiv">
								        	<i class="icon-plus"></i>添加用户
								        </button>
								      </span>
								    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">银行卡号</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="bankcardNum" id="bankcardNum" placeholder="请输入银行卡号" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">银行流水号</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="serialNumber" id="serialNumber" placeholder="请输入银行流水号" type="text">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" type="submit">添加</button>
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
<div class="modal fade" id="addRelationDiv" style="width:70%; height: 80%;margin: auto;" tabindex="-2" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-content">
		<div class="widget-content padded clearfix">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4 class="modal-title" id="myModalLabel">添加充值用户</h4>
		</div>
			<div class="modal-body">
					 <div class="heading">
						<i class="icon-table"></i>
						用户
						<!--  -->
						
						<input type="button" class="btn btn-primary pull-right  searchUser" value="搜索" />
						<div id="myModal">
							<form class="form-inline  col-md-12 pull-right col-xs-12" id="formRelation"  action="${basePath}user/list/addrelation">
								<input type="hidden" name="page" value="1" />
								 
								<div class="row">
									<div class="form-group col-md-5">
									</div>
									<div class="form-group col-md-6">
										<div>
											<input name="keyword" id="keyword" type="text" placeholder="请输入真实姓名、手机号搜索" class="form-control keyword" value="${keyword }" />
										</div>
									</div>
								</div>
							</form>
						</div>
						<div class="table-responsive">
						<table class="table table-bordered table-hover">
							<thead>
								<tr>
									<th>
										勾选
									</th>
									<th>ID</th>
									<th>真实姓名</th>
									<th>手机号</th>
									<th>性别</th>
									<th>注册时间</th>
									<th>最后登录时间</th>
									<th>会员等级</th>
								</tr>
							</thead>
							
							<tbody class="tableContent">
							</tbody>
						</table>
					</div>
			</div>
		</div>
		<div class="modal-footer">
			<!-- data-dismiss="modal" -->
			<input type="button" onclick="submitAdd()" data-dismiss="modal" aria-hidden="true" class=" btn btn-primary" value="确认" />
			<input type="button" onclick="cancelAdd()" class="btn" value="取消"/>
		</div>
	</div>
	<!-- /.modal-content -->
<!-- /.modal -->
</div>

<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-fileupload.js" type="text/javascript"></script>
<script src="${basePath}js/jquery-fileupload/jquery.ui.widget.js" type="text/javascript"></script>
<script src="${basePath}js/jquery-fileupload/jquery.fileupload.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.fancybox.pack.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-fileupload.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-paginator.min.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });
        $('.select2able').select2();
        $("#validate-form").validate({
            rules: {
            	userName: {
                    required: true,
                },
                money: {
                    required: true,
                }
            },
            messages: {
            	userName: {
                    required: "请选择充值用户",
                },
                money: {
                    required: "请选择充值金额",
                }
            }

        });
    });
    
    function setName(){
    	$('[type=file]').attr('name','file');
    }
    
    function cancelAdd(){
		 $('#addRelationDiv').find('.close').click();
	}
    
    $(function() {

		$(window).keydown( function(e) {
		    var key = window.event?e.keyCode:e.which;
		    if(key.toString() == "13"){
		    	return false;
		    }
		});

		 $('.searchUser').click(function(){
			 var keyword = $("#keyword").val();
			 if(keyword == '' || keyword == null){
				 alert("请输入手机号/姓名搜索");
				 return;
			 }
		     $.ajax({
                url: '${basePath}user/list/addrelation?keyword='+keyword,
                type: "GET",
                dataType: "html",
                async: false,
                success: function (data) {
               	var jsonData = JSON.parse(data);
               	if(jsonData.status == 'error'){
               		alert(jsonData.message);
               		return;
               	}
                	var str = "";
                	$.each(JSON.parse(data).users,function(i,ele){
                		var sex = '';
                		if(ele.sex === 1){
                			sex = '男';
                		}else if(ele.sex === 0){
                			sex = '女';
                		}else{
                			sex = '未知';
                		}
                		str+="<tr>"+
                		"<td><input class='checknum' name='choice' trueName='"+ele.trueName+"' type='radio' value='"+ele.id+"'  style='display: block; float: left;'></td>"+
                		"<td>"+ele.id+"</td>"+
                		"<td>"+(ele.trueName==undefined?"":ele.trueName)+"</td>"+
                		"<td>"+ele.phone+"</td>"+
                		"<td>"+sex+"</td>"+
                		"<td>"+getLocalTime(ele.create_date)+"</td>"+
                		"<td>"+(ele.last_login_time==undefined?"":getLocalTime(ele.last_login_time))+"</td>"+
                		"<td>"+ele.LEVEL+"</td>"+
                		
                		"</tr>";
                	})
                	$(".tableContent").html(str);
                }
            }); 
		 })
		
		
		$(".datepicker").datepicker({
			format : 'yyyy-mm-dd'
		});  
		$('.select2able').select2({
			width : "150"
		});
		$(".keyword").keyup(function(e) {
			e = e || window.e;
			if (e.keyCode == 13) {
				$("#form").submit();
			}
		});
		$('#pagination')
				.bootstrapPaginator(
						{
							currentPage : parseInt('${page}'),
							totalPages : parseInt('${pages}'),
							bootstrapMajorVersion : 3,
							alignment : "right",
							pageUrl : function(type, page, current) {
								return "filialeSellDetail?empId=${empId}&keyword=${keyword}&startDate=<fmt:formatDate value="${startDate }" pattern="yyyy-MM-dd"/>&endDate=<fmt:formatDate value="${endDate }" pattern="yyyy-MM-dd"/>&page="
										+ page;
							}
						});

	});
    
    function getLocalTime(nS) {  
		 return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/,' ');  
	}
    
	function submitAdd(){
		var trueName = $(".checknum:checked").attr('trueName');
        if (trueName) {
            trueName = "【未实名用户】";
        }
		$('#userName').val(trueName);
		var id = $(".checknum:checked").val();
		$('#userId').val(id);
	}
	
</script>
</body>
</html>
