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
	<title>标的批量新增</title>
	<link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css" />
	<link href="${basePath}css/jquery-fileupload/jquery.fileupload.css" media="all" rel="stylesheet" type="text/css" />
	<link href="${basePath}css/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css" />
	<link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
	<link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css" />
	<style type="text/css">
		.upload-picture a{display:inline-block; overflow: hidden;border: 0;vertical-align: top;margin: 0 5px 10px 0;background: #fff;}
		.gallery-item:hover{background: #000;}
		#validate-form i.icon-zoom-in{width: 36px;height: 36px;font-size: 18px;line-height: 35px;margin-top: 0;}
	</style>
</head>
<body>
<div class="modal-shiftfix">
        <!-- Navigation -->
      	<jsp:include page="../common/header.jsp"></jsp:include>
        <!-- End Navigation -->
        <div class="container-fluid main-content">
            <div class="page-title">
                <h1>项目管理</h1>
            </div>
            <!-- DataTables Example -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="widget-container fluid-height clearfix">
                        <div class="heading">
                            <i class="icon-table"></i>批量创建散标项目
                        </div>
                        <div class="widget-content padded clearfix">
                            <form action="batchAdd" method="post" class="form-horizontal" id="validate-form" onsubmit="return checkPost();">
           	                 <ul class="reminderAdd_ul" style="list-style:none;">
           	                 
                                <li class="form-group" id="items">
                                    <label class="control-label col-md-2">项目名称</label>
                                    <div class="col-md-7">
                                        <input class="form-control" id="title" name="title" type="text" />
                                    </div>                           
                                </li>
                                
                                <li class="form-group" id="items">
                                    <label class="control-label col-md-2">项目地址</label>
                                    <div class="col-md-7">
                                        <input class="form-control" id="address" name="address" value="${contractFile.address}" type="text" />
                                    </div>                           
                                </li>
                                
                                <li class="form-group" id="items">
                                    <label class="control-label col-md-2">应收租金总和</label>
                                    <div class="col-md-7">
                                        <input class="form-control" id="rentSum" name="rentSum" value="" type="text" />
                                    </div>                           
                                </li>
                                
                                <li class="form-group" id="items">
                                    <label class="control-label col-md-2">借款人</label>
                                    <div class="col-md-7">
                                        <input class="form-control" id="landlordName" value="${contractFile.landlordName}" name="landlordName" type="text" readonly />
                                        <input type="hidden" value="${contractFile.landlordId}" name="userId" type="hidden"/>
                                        <input type="hidden" value="${contractId}" name="contractId"/>
                                    </div>                           
                                </li>
                                
                                <li class="form-group" id="items">
                                    <label class="control-label col-md-2">借款期限</label>
                                    <div class="col-md-7">
                                        <input class="form-control" id="limitDays" value="${contractFile.day}" name="limitDays" type="text" readonly/>
                                    </div>                           
                                </li>
                                
                                <li class="form-group" id="items">
                                    <label class="control-label col-md-2">首次借款金额</label>
                                    <div class="col-md-7">
                                        <input class="form-control" id="amount" value="${contractFile.amount}" name="amount" type="text" readonly/>
                                    </div>                           
                                </li>
                                
                                <li class="form-group" id="items">
                                    <label class="control-label col-md-2">年化收益率</label>
                                    <div class="col-md-7">
                                        <input class="form-control" value="${lendRate*100}%" type="text" readonly />
                                        <input class="form-control" id="annualized" name="annualized" value="${lendRate}" type="hidden" />
                                    </div>                           
                                </li>
                                
                                <li class="form-group" id="items">
                                    <label class="control-label col-md-2">年化收益加息</label>
                                    <div class="col-md-7">
                                        <input class="form-control" id="increaseAnnualized" name="increaseAnnualized" type="text" value="0"/>
                                    </div>                           
                                </li>
                                
                                <li class="form-group" id="items">
                                    <label class="control-label col-md-2">加息限制天数</label>
                                    <div class="col-md-7">
                                        <input class="form-control" id="rateCouponDays" name="rateCouponDays" type="text"/>
                                    </div>                           
                                </li>
                                
                                <li class="form-group" id="items">
                                    <label class="control-label col-md-2"><font color="red">平台服务费</font></label>
                                    <div class="col-md-7">
                                    	<select class="" name="platServiceCharge" id="platServiceCharge">
	                                        <option value="0.00375" selected>0.00375</option>
	                                        <option value="0.0025">0.0025</option>
	                                    </select>
                                        
                                    </div>                           
                                </li>
                                
                                
                                
									<div class="form-group" class="transfor">
		                                    <label class="control-label col-md-2">转让限制</label>
		                                    <div class="col-md-7" style="margin-top:5px;">
		                                        		可转让<input class="icon-class" style="display:inline" id="transferablecheck"  onclick="transabledclick()" type="checkbox" <c:if test="${contractFile.day gt 90 }"> checked="checked"  </c:if> />
		                                    </div>
		                            </div>
		                            <div class="form-group" id="transferablecheckfor" class="transfor" <c:if test="${contractFile.day le 90 }">style="display:none;"  </c:if> >
		                                <label class="control-label col-md-2">转让条件</label>
		                                <div class="col-md-2" style="display:inline-block;">
		                                		   <span style="display: inline-block;margin-right:5px;margin-top: 5px;float:left">持有</span> 
		                                           <input class="form-control" onblur="clearResult()"  id="transferable" value="0" name="transferable" placeholder="输入可转让期限"
		                                           type="text" onkeyup="value=value.replace(/[^\d]/g,'')" style="width:85%;">
		                                    </div>
		                                <div style="margin-top:5px;">天可转让&nbsp;&nbsp;&nbsp;&nbsp;<span id="result" style="color: red;"></span></div>
		                            </div>
                                <li class="form-group" id="items">
                                    <label class="control-label col-md-2">标的类型</label>
                                    <div class="col-md-7">
                                    <select class="" name="noob">
                                        <option value="3">散标标</option>
                                        <option value="1">新手标</option>
                                    </select>
                                	</div>    
                                </li>
                                
                                <li class="form-group" id="items">
                                    <label class="control-label col-md-2">项目风险等级</label>
                                    <c:set var="riskEnum" value="<%= com.goochou.p2b.constant.project.ProjectRiskEnum.values() %>"/>
                                    <select class="select2able" name="projectRiskGrade">
                                        <c:forEach var="e" items="${riskEnum}">
                                            <c:if test="${e.star != 0}">
                                                <option value="${e.star}">${e.description}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>                          
                                </li>
                                
                                <li class="form-group" id="items">
                                    <label class="control-label col-md-2">支持发标时间</label>
                                    <div class="col-md-7">
                                        <input class="form-control datepicker" name="lendBeginTime" 
                                        value="<fmt:formatDate value="${contractFile.lendBeginTimeSevenDaysAgo}" pattern="yyyy-MM-dd"/>" type="text"
											placeholder="首次借款起始时间" />
                                    </div>                           
                                </li>
                                
                                <li class="form-group" id="items">
                                    <label class="control-label col-md-2">还款方式</label>
                                    <div class="col-md-7">
                                        <input class="form-control" id="repaymentMethod"  type="text" value="每月还息，到期还本" readonly />
                                        <input type="hidden" name="repaymentMethod" value="0"/>
                                    </div>                           
                                </li>
                                
                                <li class="form-group" id="items">
                                    <label class="control-label col-md-2">借款用途</label>
                                    <div class="col-md-7">
                                        <input class="form-control" id="useOfFunds" name="useOfFunds" value="${useOfFunds}" type="text" readonly />
                                    </div>                           
                                </li>
                                
                                <li class="form-group" id="items">
                                    <label class="control-label col-md-2">租赁合同到期日</label>
                                    <div class="col-md-7">
                                        <input class="form-control datepicker" name="expiryDate" type="text" placeholder="租赁合同到期日" />
                                    </div>                           
                                </li>
                                
                            </ul>
                            	<br/><br/><br/>
                                <div class="form-group">
                                	<label class="control-label col-md-2"></label>
                                    <div class="text-center col-md-7">
                                        <button class="btn btn-primary" type="submit">马上创建</button>
                                    </div>
                                </div>
                                <br/><br/><br/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!-- end DataTables Example -->
        </div>
    </div>
    
<div class="modal fade" id="myModal3">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" class="close" data-dismiss="modal" type="button">&times;</button>
                <h4 class="modal-title">
                   首页&列表页图片
                </h4>
            </div>
            <div class="modal-body">
                <form action="" class="form-horizontal" id="picture-form3">
                    <div class="form-group">
                        <label class="control-label col-md-2">图片名称</label>

                        <div class="col-md-7">
                            <input class="form-control" name="picName" id="picName3" placeholder="请输入图片名称" type="text"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-2">图片</label>

                        <div class="col-md-3">
                                    <span class="btn btn-success fileinput-button">
					                    <i class="glyphicon glyphicon-plus"></i>
					                    <span>上传</span>
					                    <input type="file" name="file" id="fileupload3">
					                </span>
                            <img src="" id="target3" width="200px;"/>

                            <div class="alert alert-danger" style="display: none;width: 300px;">
                                <button class="close" data-dismiss="alert" type="button">&times;</button>
                                <span class="alert-content"></span>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default-outline" data-dismiss="modal" type="button">关闭</button>
                <button class="btn btn-primary" id="add-picture3" disabled>添加</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="myModal4">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" class="close" data-dismiss="modal" type="button">&times;</button>
                <h4 class="modal-title">
                  安全保障图片
                </h4>
            </div>
            <div class="modal-body">
                <form action="" class="form-horizontal" id="picture-form4">
                    <div class="form-group">
                        <label class="control-label col-md-2">图片名称</label>

                        <div class="col-md-7">
                            <input class="form-control" name="picName" id="picName4" placeholder="请输入图片名称" type="text"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-2">图片</label>

                        <div class="col-md-3">
                                    <span class="btn btn-success fileinput-button">
					                    <i class="glyphicon glyphicon-plus"></i>
					                    <span>上传</span>
					                    <input type="file" name="file" id="fileupload4">
					                </span>
                            <img src="" id="target4" width="200px;"/>

                            <div class="alert alert-danger" style="display: none;width: 300px;">
                                <button class="close" data-dismiss="alert" type="button">&times;</button>
                                <span class="alert-content"></span>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default-outline" data-dismiss="modal" type="button">关闭</button>
                <button class="btn btn-primary" id="add-picture4" disabled>添加</button>
            </div>
        </div>
    </div>
</div>
    
    <script src="${basePath}js/bootstrap-fileupload.js" type="text/javascript"></script>
	<script src="${basePath}js/jquery-fileupload/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="${basePath}js/jquery-fileupload/jquery.fileupload.js" type="text/javascript"></script>
    <script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
    <script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
    <script type="text/javascript">
	  	//验证输入框的格式
	    var reg1 = /^\d+(\.\d{0,2})?/gi;  //只能输入数字，最多两位小数
	    var reg2 = /[^\d]/g; //只能输入数字
	    function regInput(obj, reg) {
	        obj.on("input", function () {
	            var _this = $(this);
	            var val = $.trim(_this.val());
	            if (isNaN(+val)) {
	                _this.val("");
	                return;
	            } else if (val.match(reg)) {
	                _this.val(val.match(reg)[0]);
	            }
	        });
	    }
	    
	   	$(function(){
	   		
	   		$(".datepicker").datepicker({
	            format: 'yyyy-mm-dd'
	        });
	   		
	   		regInput($("#increaseAnnualized"), reg1);
	   		regInput($("#rateCouponDays"), reg2);
	   		regInput($("#rentSum"),reg1);
	   		
	   		
	   		$("#fileupload3").fileupload({
	            url: "upload?type=3",
	            autoUpload: false, //不自动上传
	            formData: new FormData().append("picName", $.trim($("#picName3").val())),
	            add: function (e, data) {
	                var file = data.files[0];
	                if (!new RegExp(/(\.|\/)(gif|jpe?g|png|bmp)$/i).test(file.type)) {
	                    $(".alert-danger .alert-content").text("错误的图片类型");
	                    $(".alert-danger").fadeIn().delay(2000).fadeOut();
	                    return false;
	                }
	                if (file.size > 512000) {//10M
	                    $(".alert-danger .alert-content").text("图片大于500k");
	                    $(".alert-danger").fadeIn().delay(2000).fadeOut();
	                    return false;
	                }
	                var reader = new FileReader();
	                reader.onload = function (e) {
	                    $('#target3').attr('src', e.target.result);
	                };
	                reader.readAsDataURL(file);
	                data.context = $("#add-picture3").unbind("click").bind("click", function () {
	                    if ($.trim($("#picName3").val()) == '') {
	                        alert("请输入图片名称");
	                        return;
	                    }
	                    data.submit();
	                });
	                $("#add-picture3").attr("disabled", false);
	            },
	            added: function (e, data) {
	                console.log(data.files);
	            },
	            done: function (e, result) {
	                var data = JSON.parse(result.result);
	                console.log(data);
	                if (data.status == "error") {
	                    $(".alert-danger .alert-content").text(data.message);
	                    $(".alert-danger").fadeIn().delay(2000).fadeOut();
	                } else {
	                    var $html = $('<a class="gallery-item fancybox" rel="g1" title="" picId="">' +
	                            '<img src="" />' +
	                            '<div class="actions">' +
	                            '<i class="icon-trash"></i><i class="icon-zoom-in"></i>' +
	                            '</div>' +
	                            '</a>');
	                    var path = '${aPath}upload/' + data.object.picturePath;
	                    $html.attr("href", path).attr("title", data.object.name).attr("picId", data.object.id);
	                    $html.find("img").attr("src", path);
	                    $html.insertBefore($("#indexList-picture"));
	                    $("#myModal3").modal("hide");
	                    //添加隐藏输入框 保存当前的图片ID
	                    $("#validate-form").append('<input id="picture-' + data.object.id + '" type="hidden" name="picture3" value="' + data.object.id + '"/>');
	                }
	            }
	        });
	        
	        $("#fileupload4").fileupload({
	            url: "upload?type=4",
	            autoUpload: false, //不自动上传
	            formData: new FormData().append("picName", $.trim($("#picName4").val())),
	            add: function (e, data) {
	                var file = data.files[0];
	                if (!new RegExp(/(\.|\/)(gif|jpe?g|png|bmp)$/i).test(file.type)) {
	                    $(".alert-danger .alert-content").text("错误的图片类型");
	                    $(".alert-danger").fadeIn().delay(2000).fadeOut();
	                    return false;
	                }
	                if (file.size > 512000) {//10M
	                    $(".alert-danger .alert-content").text("图片大于500k");
	                    $(".alert-danger").fadeIn().delay(2000).fadeOut();
	                    return false;
	                }
	                var reader = new FileReader();
	                reader.onload = function (e) {
	                    $('#target4').attr('src', e.target.result);
	                };
	                reader.readAsDataURL(file);
	                data.context = $("#add-picture4").unbind("click").bind("click", function () {
	                    if ($.trim($("#picName4").val()) == '') {
	                        alert("请输入图片名称");
	                        return;
	                    }
	                    data.submit();
	                });
	                $("#add-picture4").attr("disabled", false);
	            },
	            added: function (e, data) {
	                console.log(data.files);
	            },
	            done: function (e, result) {
	                var data = JSON.parse(result.result);
	                console.log(data);
	                if (data.status == "error") {
	                    $(".alert-danger .alert-content").text(data.message);
	                    $(".alert-danger").fadeIn().delay(2000).fadeOut();
	                } else {
	                    var $html = $('<a class="gallery-item fancybox" rel="g1" title="" picId="">' +
	                            '<img src="" />' +
	                            '<div class="actions">' +
	                            '<i class="icon-trash"></i><i class="icon-zoom-in"></i>' +
	                            '</div>' +
	                            '</a>');
	                    var path = '${aPath}upload/' + data.object.picturePath;
	                    $html.attr("href", path).attr("title", data.object.name).attr("picId", data.object.id);
	                    $html.find("img").attr("src", path);
	                    $html.insertBefore($("#safety-picture"));
	                    $("#myModal4").modal("hide");
	                    //添加隐藏输入框 保存当前的图片ID
	                    $("#validate-form").append('<input id="picture-' + data.object.id + '" type="hidden" name="picture4" value="' + data.object.id + '"/>');
	                }
	            }
	        });
	        
	        $(".upload-picture").on("click", ".icon-trash", function () {
	            var $this = $(this);
	            if (confirm("您确定要删除该图片吗?,图片一旦删除，将不可恢复!")) {
	                var picId = $this.parent().parent().attr("picId");
	                $.ajax({
	                    url: "delete/picture?id=" + picId,
	                    type: "post",
	                    dataType: "json",
	                    success: function (data) {
	                        if (data.status == "success") {
	                            $("#picture-" + picId).remove();
	                            $this.parent().parent().remove();
	                        } else {
	                            alert("服务器忙,请稍后重试");
	                        }
	                    }
	                });
	            }
	            return false;
	        });
	        
	        
	      //进来清空
	        $(".upload-picture").click(function () {
	            $("#picName3").val("");
	            $("#target3").attr("src", "");
	            $("#add-picture3").attr("disabled", true);
	            
	            $("#picName4").val("");
	            $("#target4").attr("src", "");
	            $("#add-picture4").attr("disabled", true);
	        });
	      
	        $("#validate-form").validate({
	            rules: {
	                title: {
	                    required: true,
                        maxlength: 128,
	                    remote: {
	                        url: "checkName",     //后台处理程序
	                        type: "get",
	                        dataType: "json",
	                        data: {                     //要传递的数据
	                            username: function () {
	                                return $("#title").val();
	                            }
	                        }
	                    }
	                },
//	                increaseAnnualized: {
//	                    required: true,
//	                    range: [0, 1]
//	                },
	                rateCouponDays: {
	                    required: true,
	                    range: [0, 999]
	                },
	                address: {
	                    required: true
	                },
	                rentSum: {
	                    required: true
	                },
	                expiryDate: {
	                    required: true
	                },
	                transferable:{
	                	digits: true,
	                	range: [0, 365]
	                }
	            },
	            messages: {
	                title: {
	                    required: "请输入项目名",
	                    maxlength: "项目名不超过128个字符",
	                    remote: "项目名称已存在"
	                },
//	                increaseAnnualized: {
//	                    required: "请输入年化收益加息",
//	                    range: "请输入0-1之间的小数"
//	                },
	                rateCouponDays: {
	                	required: "请输入加息限制天数",
	                    range: "请输入0-999之间的小数"
	                },
	                expiryDate: {
	                	required: "请输入合同到期日"
	                },
	                rentSum: {
	                	required: "请输入应收租金总和"
	                },
	                address: {
	                	required: "请输入项目地址"
	                },
	                transferable:{
	                	digits: "请输入正确的整数",
	                	rang: "请输入0-365之间的整数"
	                }
	            }
	        });
	   		
	   	})
	   	 function transabledclick(){
	    	if($("#transferablecheck").is(':checked')==true){
	    		$("#transferablecheckfor").show();
	    	}else{
	    		$('#transferable').val(0);
	    		$("#transferablecheckfor").hide();
	    	}
    	
    	 }
	   	function clearCheck(){
	    	 var limitDays = $('#limitDays').val();
	    	 if(parseInt(limitDays)<=90){
	    		$("#transferablecheck").prop("checked",false);
	     		$('#transferable').val(0);
	     		$("#transferablecheckfor").hide();
	     	}
	     	if(parseInt(limitDays)>90&&parseInt(limitDays)<=360){
	     		$("#transferablecheck").prop("checked",true);
	     		$("#transferablecheckfor").show();
	     	}
	     }
	   	function clearResult(){
	    	var limitDays = $('#limitDays').val();
	        var checked=$("#transferablecheck").is(':checked')
	       	var transferable = $('#transferable').val();
	       	if(checked==true){
	       		if(parseInt(transferable)<=1){
	       			$("#result").text("输入的天数必须大于1");
	       			return false;
	       		}else	if(parseInt(transferable)<=parseInt(limitDays)){
	       			$("#result").text("");
	       			return true;
	       		}
	       	}
	    }
	   	
	   	//防止重复提交
	   	var flag = true;
	   	
	   	function checkPost(){
	   		if(!flag){
	   			return false;
	   		}
            var annualized = $('#annualized').val();
            var increaseAnnualized = $('#increaseAnnualized').val();
            if(parseFloat(annualized)+parseFloat(increaseAnnualized)>= 1){
                alert("年化收益+年化收益加息要小于1！");
                return false;
            }
	   		return clearResult();

	        var limitDays = $('#limitDays').val();
	        var couponDays = $('#rateCouponDays').val();
            if(parseInt(couponDays)<=0){
                alert("加息券生息天数要大于0！");
                return false;
            }else if(parseInt(couponDays)>parseInt(limitDays)){
                alert("加息券生息天数要在1到项目期限天数内！");
                return false;
            }else{
                flag = true;
                return true;
            }
    	}
    </script>
</body>
</html>