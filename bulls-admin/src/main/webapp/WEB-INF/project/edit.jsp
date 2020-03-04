<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑项目</title>
<link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/jquery-fileupload/jquery.fileupload.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}js/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
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
							<i class="icon-table"></i>
							编辑项目
						</div>
						<div class="widget-content padded clearfix">
							<form action="edit" method="post" class="form-horizontal" id="validate-form">
								<input type="hidden" value="${project.id }" name="id" id="id" />
								<input type="hidden" value="${listParams }" name="listParams" id="listParams" />

								<div class="form-group">
									<label class="control-label col-md-2">项目封面</label>

									<div class="col-md-7 upload-picture">
										<c:forEach var="pic" items="${project.pictures }">
											<c:if test="${pic.type==1}">
												<a class="gallery-item fancybox" rel="g1" href="${aPath}upload/${pic.upload.path }" picId="${pic.id }" title="${pic.name }">
													<img src="${aPath}upload/${pic.upload.path }" />

													<div class="actions">
														<i class="icon-trash"></i>
														<i class="icon-zoom-in"></i>
													</div>
												</a>
											</c:if>
										</c:forEach>
										<a data-toggle="modal" href="#myModal" id="enterprise-picture">
											<i class="iconfont" style="font-size: 150px; cursor: pointer;">&#xe602;</i>
										</a>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">项目明细图</label>
									<div class="col-md-7 upload-picture">
										<c:forEach var="pic" items="${project.pictures }">
											<c:if test="${pic.type==13}">
												<a class="gallery-item fancybox" rel="g1" href="${aPath}upload/${pic.upload.path }" picId="${pic.id }" title="${pic.name }">
													<img src="${aPath}upload/${pic.upload.path }" />
													<div class="actions">
														<i class="icon-trash"></i>
														<i class="icon-zoom-in"></i>
													</div>
												</a>
											</c:if>
										</c:forEach>
										<a data-toggle="modal" href="#myModal2" id="borrow-picture">
											<i class="iconfont" style="font-size: 150px; cursor: pointer;">&#xe602;</i>
										</a>
									</div>
								</div>
								
								<div class="form-group">
	                                <label class="control-label col-md-2">物权类型</label>
	
	                                <div class="col-md-7">
	                                    <select class="select2able" id="projectType" name="projectType">
	                                        <c:if test="${!empty types}">
	                                            <c:forEach items="${types}" var="d">
	                                                <option value="${d.featureType}"
	                                                        <c:if test="${project.projectType==d.featureType}">selected="selected"</c:if>>${d.description}</option>
	                                            </c:forEach>
	                                        </c:if>
	                                    </select>
	                                </div>
	                            </div>

								<div class="form-group">
									<label class="control-label col-md-2">项目名称</label>
									<div class="col-md-7">
										<input class="form-control" name="title" value="${project.title }" type="text">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">企业名称</label>

									<div class="col-md-7">
										<input id="selectEnterprise" value="${project.enterpriseId }" name="enterpriseId" type="hidden" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">体重</label>

									<div class="col-md-7">
										<input class="form-control" name="weight" id="weight" placeholder="请输入体重" value="${project.weight}" type="text" onkeyup="sian()">
									</div>
								</div>
								<div class="form-group">
									<script>
									var product = new Array();
									</script>
									<label class="control-label col-md-2">牧场产品表</label>
									<div class="col-md-7">
										<select class="select2able" onchange="listProductProperty('');" id="productId" name="productId">
											<option value="">请选择</option>
											<c:if test="${!empty productList}">
												<c:forEach items="${productList}" var="product">
													<option value="${product.id}" <c:if test="${product.id == project.productId }">selected</c:if>>${product.name}</option>
													<script>
													product[${product.id}] = {malePrice:${product.malePrice},femalePrice:${product.femalePrice},feedPrice:${product.feedPrice},addWeight:${product.addWeight}};
													</script>
												</c:forEach>
											</c:if>
											<c:forEach var="property" items="${project.propertys }">
												<input id="property_${property.productPropertyId}" value="${property.propertyValue}" type="hidden" />
											</c:forEach>
										</select>
									</div>
								</div>
								<!-- 类目显示 -->
								<div class="selectHTML"></div>

								<div class="form-group" >
									<label class="control-label col-md-2">是否新手</label>

									<div class="col-md-7">
										<select class="select2able" id="noob" name="noob" onchange="goToPage(this)">
											<option value="0" <c:if test="${project.noob == 0 }">selected</c:if>>否</option>
											<option value="1" <c:if test="${project.noob == 1 }">selected</c:if>>是</option>
										</select>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">项目期限</label>
									<div class="col-md-7">
										<select class="select2able" id="limitDays" name="limitDays" onchange="changeLimit()">
											<c:if test="${!empty days}">
												<c:forEach items="${days}" var="d">
													<option value="${d.featureType}" <c:if test="${project.limitDays==d.featureType}">selected="selected"</c:if>>${d.featureName}</option>
												</c:forEach>
											</c:if>
										</select>
										<script>
										function changeLimit(){
                                            var vl = $("#limitDays").val();
                                            var vl2 = $('#noob').val();
                                            if (vl == "30" && vl2 == 0) {
                                                $("#annualized").val(7.8);
                                            } else if (vl == "30" && vl2 == 1) {
                                                $("#annualized").val(12);
                                            } else if (vl == "60") {
                                                $("#annualized").val(8.2);
                                            } else if (vl == "90") {
                                                $("#annualized").val(8.6);
                                            } else if (vl == "180") {
                                                $("#annualized").val(9.6);
                                            }/* else if(vl == "270"){
												$("#annualized").val(9);
											} */ else if (vl == "360") {
                                                $("#annualized").val(10.8);
                                            }

											sian();
										}
										</script>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">年化收益</label>

									<div class="col-md-7">
										<input class="form-control" id="annualized" name="annualized" value="${project.annualized }" onkeyup="sian()" placeholder="请输入年化收益(1-20之间数字)" type="number">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">性别</label>
									<div class="col-md-7">
										<select class="select2able" id="sex" name="sex" onchange="sian()">
											<option value="-1">请选择</option>
											<option value="0" <c:if test="${project.sex=='0'}">selected="selected"</c:if>>公</option>
											<option value="1" <c:if test="${ project.sex=='1'}">selected="selected"</c:if>>母</option>
										</select>
									</div>
								</div>
								<input class="form-control" name="totalAmount" id="totalAmount" value="<fmt:formatNumber type="number" value="${project.totalAmount}" groupingUsed="false" maxFractionDigits="0"/>" placeholder="请输入项目募集额，单位是元" type="hidden">
								<script type="text/javascript">
								 function sian() {
								     console.log("**************************");
	                                    try {
	                                        var weight = Number($("#weight").val());
	                                        var productId = $("#productId").val();
	                                        var limitDays = Number($("#limitDays").val());
	                                        var annualized = Number($("#annualized").val());
	                                        var sex = $("#sex").val();
	                                        var raiseFee = 0;
	                                        var manageFee = 0;
	                                        var amount = 0;
	                                        var profit = 0;
	                                        var nice = 0;
	                                        if (sex == "1") {
	                                        	$("#unitZoomPrice").val(product[productId].femalePrice);
	                                        } else {
	                                        	$("#unitZoomPrice").val(product[productId].malePrice);
	                                        }
	                                        console.log("unitZoomPrice: " + $("#unitZoomPrice").val());
	                                        /*if (sex == "1") {
	                                            price = Number(product[productId].femalePrice);
	                                            $("#unitZoomPrice").val(product[productId].femalePrice);
	                                            if (productId == 4) {
	                                                nice = Number(product[productId].addWeight) * price * limitDays;
	                                            } else {
	                                                nice = 8000 / 360 * limitDays;
	                                            }
	                                        } else {
	                                            price = Number(product[productId].malePrice);
	                                            $("#unitZoomPrice").val(product[productId].malePrice);
	                                            nice = Number(product[productId].addWeight) * price * limitDays;
	                                        }
	                                        amount = Number(price).mul(Number(weight));
	                                        raiseFee = Number(product[productId].feedPrice).mul(Number(limitDays));
	                                        annualized = (annualized / 100) / 360 * limitDays;
	                                        //计算管理费单价
	                                        var managePrice = toFixed(Math.abs(weight * price * annualized + raiseFee * annualized - nice + raiseFee) / Math.abs(limitDays + limitDays * annualized), 2);
	                                        manageFee = Number(managePrice).mul(Number(limitDays));
	                                        $("#raiseFee").val(raiseFee);
	                                        $("#manageFee").val(manageFee);
	                                        $("#unitFeedPrice").val(product[productId].feedPrice);
	                                        $("#addWeight").val(product[productId].addWeight);
	                                        $("#unitManagePrice").val(managePrice);
	                                        $("#totalAmount").val(amount + manageFee + raiseFee); */
	                                        $.ajax({
	                                            url: "calculateBuyMoney",
	                                            data: {
	                                            	weight:weight,
	                                                productId: productId, 
	                                                limitDays:limitDays, 
	                                                sex:sex,
                                                    annualized: annualized
	                                            },
	                                            type: "post",
	                                            dataType: "json",
	                                            success: function (data) {
	                                                if (data.status == "success") {
	                                                    var buyBullsDetail = data.vo;
	                                                    console.log(buyBullsDetail);
	                                                    amount = buyBullsDetail.bullMoney;
	                                                    manageFee = buyBullsDetail.manageMoney;
	                                                    raiseFee = buyBullsDetail.feedMoney;
	                                                    var totalAmount = buyBullsDetail.totalMoney;
	                                                    var unitFeedPrice = buyBullsDetail.unitFeedMoney;
	                                                    var unitManagePrice = buyBullsDetail.unitManageMoney;
	                                                    $("#raiseFee").val(raiseFee);
	                                                    $("#manageFee").val(manageFee);
	                                                    $("#unitFeedPrice").val(unitFeedPrice);
	                                                    $("#addWeight").val(product[productId].addWeight);
	                                                    $("#unitManagePrice").val(unitManagePrice);
	                                                    $("#totalAmount").val(totalAmount);
	                                                    var s = "";
	                                                    s += "牲畜单价：" + amount + "<br />";
	                                                    s += "管理费：" + manageFee + "(" + unitManagePrice + "元/天)<br />";
	                                                    s += "饲养费：" + raiseFee + "(" + product[productId].feedPrice + "元/天)<br />";
	                                                    s += "标的总价：" + totalAmount;
	                                                    $("#s").html(s);
	                                                } 
	                                            }
	                                        });
	                                        
	                                        
	                                    } catch (err) {
	                                    }
	                                }
								function toFixed(num,val){
									if(num.toString().indexOf(".")==-1){
										num = num+".0000";
									}
									var arr = num.toString().split(".");
									var right = arr[1].substring(0,val);
									return arr[0]+"."+right;
								}
								</script>

								<div class="form-group">
									<label class="control-label col-md-2">计算结果</label>
									<div class="col-md-7" id="s" style="line-height: 25px; color: red"></div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">还款方式</label>

									<div class="col-md-7">
										<select class="select2able" name="repaymentMethod">
											<option value="0">按月还息,到期还</option>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">项目标签</label>

									<div class="col-md-7">
										<input rows="5" class="form-control" placeholder="请输入新手标记例如(新手专享) 多个以-号隔开，最多不超过两个" name="tag" value="${project.tag}" id="tag" />
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">耳标号</label>

                                <div class="col-md-7">
                                    <input class="form-control" id="earNumber" name="earNumber" placeholder=""
                                           type="text" value="${project.earNumber}"
                                           onblur="ifExistProjectByEarnumberAjax();">
                                    <input class="form-control" id="earNumber_" name="earNumber_" type="hidden">
                                    <input class="form-control" id="earNumber_1" name="earNumber_1" type="hidden"
                                           value="${project.earNumber}">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">真实耳标号</label>

                                <div class="col-md-7">
                                    <input class="form-control" id="realEarNumber" name="realEarNumber" placeholder=""
                                           type="text" value="${project.realEarNumber}">
                                    <input class="form-control" id="realEarNumber_" name="realEarNumber_"
                                           type="hidden">
                                    <input class="form-control" id="realEarNumber_1" name="realEarNumber_1"
                                           type="hidden" value="${project.realEarNumber}">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="control-label col-md-2"></div>
                                <div class="col-md-7">
                                    <font color="red">发布的项目，耳标号不能重复</font>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">保险编号</label>
                                <div class="col-md-7">
                                    <input class="form-control" id="safeNumber" name="safeNumber" placeholder=""
                                           type="text" value="${project.safeNumber}">
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="control-label col-md-2">选择牧场</label>
                                <div class="col-md-7">
                                    <select class="select2able" name="prairieValue" id="prairieValue">
                                        <option value="">---请选择牧场---</option>
                                        <c:forEach items="${prairie}" var="prairie">
                                            <option value="${prairie.tValue }"
                                                    <c:if test="${project.tmDict.tValue == prairie.tValue }">selected</c:if>>${prairie.tName }</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>


                            <%-- 								<div class="form-group">
                                                                <label class="control-label col-md-2">GPS设备编号</label>
                                                                <div class="col-md-7">
                                                                    <input class="form-control" id="gpsNumber" name="gpsNumber" placeholder="" type="text" value="${project.gpsNumber}">
                                                                </div>
                                                            </div> --%>

								<div class="form-group">
									<label class="control-label col-md-2">排序</label>
									<div class="col-md-7">
										<input class="form-control" id="sort" name="sort" placeholder="最大99，数值越小排名越靠前" type="number" value="${project.sort}" onblur="sortNumber();">
									</div>
								</div>
								<!-- 
								<div class="form-group">
									<label class="control-label col-md-2">饲养费</label>
									<div class="col-md-7">
										<input class="form-control" id="raiseFee" name="raiseFee" placeholder="" type="text" onblur="clearCheck()" value="${project.raiseFee}">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">管理费</label>
									<div class="col-md-7">
										<input class="form-control" id="manageFee" name="manageFee" placeholder="" type="text" onblur="clearCheck()" value="${project.manageFee}">
									</div>
								</div> -->
								<input class="form-control" id="raiseFee" name="raiseFee" placeholder="" type="hidden" onblur="clearCheck()" value="${project.raiseFee}">
								<input class="form-control" id="manageFee" name="manageFee" placeholder="" type="hidden" onblur="clearCheck()" value="${project.manageFee}">
								<input class="form-control" id="unitZoomPrice" name="unitZoomPrice" type="hidden" value="${project.unitZoomPrice}">
								<input class="form-control" id="unitFeedPrice" name="unitFeedPrice" type="hidden" value="${project.unitFeedPrice}">
								<input class="form-control" id="unitManagePrice" name="unitManagePrice" type="hidden" value="${project.unitManagePrice}">
								<input class="form-control" id="addWeight" name="addWeight" type="hidden" value="${project.addWeight}">
								<div class="form-group">
									<label class="control-label col-md-2">项目详情</label>
									<div class="col-md-7">
										<textarea rows="5" class="form-control" placeholder="请输入项目描述" name="projectDescription">${project.projectDescription }</textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">状态</label>

									<div class="col-md-7">

										<label class="radio-inline">
											<input name="status" type="radio" value="0" <c:if test="${project.status == 0 }">checked</c:if>>
											<span>待上架</span>
										</label>
										<label class="radio-inline">
											<input name="status" type="radio" value="1" <c:if test="${project.status == 1 }">checked</c:if>>
											<span>上架</span>
										</label>
										<label class="radio-inline">
											<input name="status" type="radio" value="5" <c:if test="${project.status == 5 }">checked</c:if>>
											<span>已死亡</span>
										</label>
										<%-- 	<label class="radio-inline">
											<input name="status" type="radio" value="2" <c:if test="${project.status == 2 }">checked</c:if>>
											<span>投资中</span>
										</label> --%>
									</div>
								</div>
								<input type="hidden" value="${project.projectType }" name="projectType" />
								<div class="form-group">
									<label class="control-label col-md-2"></label>

									<div class="text-center col-md-7">
										<a class="btn btn-default-outline" onclick="javascript:window.history.go(-1);">取消</a>
										<button class="btn btn-primary" type="button" onclick="checkPost();">完成</button>
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

	<div class="modal fade" id="myModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" class="close" data-dismiss="modal" type="button">&times;</button>
					<h4 class="modal-title">项目图片上传</h4>
				</div>
				<div class="modal-body">
					<form action="" class="form-horizontal" id="picture-form">
						<div class="form-group">
							<label class="control-label col-md-2">图片名称</label>

							<div class="col-md-7">
								<input class="form-control" name="picName" id="picName" placeholder="请输入图片名称" type="text" />
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-2">图片</label>

							<div class="col-md-3">
								<span class="btn btn-success fileinput-button">
									<i class="glyphicon glyphicon-plus"></i>
									<span>上传</span>
									<input type="file" name="file" id="fileupload">
								</span>
								<img src="" id="target" width="200px;" />

								<div class="alert alert-danger" style="display: none; width: 300px;">
									<button class="close" data-dismiss="alert" type="button">&times;</button>
									<span class="alert-content"></span>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-default-outline" data-dismiss="modal" type="button">关闭</button>
					<button class="btn btn-primary" id="add-picture" disabled>添加</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="myModal2">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" class="close" data-dismiss="modal" type="button">&times;</button>
					<h4 class="modal-title">项目图片上传</h4>
				</div>
				<div class="modal-body">
					<form action="" class="form-horizontal" id="picture-form2">
						<div class="form-group">
							<label class="control-label col-md-2">图片名称</label>

							<div class="col-md-7">
								<input class="form-control" name="picName" id="picName2" placeholder="请输入图片名称" type="text" />
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-2">图片</label>

							<div class="col-md-3">
								<span class="btn btn-success fileinput-button">
									<i class="glyphicon glyphicon-plus"></i>
									<span>上传</span>
									<input type="file" name="file" id="fileupload2">
								</span>
								<img src="" id="target2" width="200px;" />

								<div class="alert alert-danger" style="display: none; width: 300px;">
									<button class="close" data-dismiss="alert" type="button">&times;</button>
									<span class="alert-content"></span>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-default-outline" data-dismiss="modal" type="button">关闭</button>
					<button class="btn btn-primary" id="add-picture2" disabled>添加</button>
				</div>
			</div>
		</div>
	</div>

	<script src="${basePath}js/select2.js" type="text/javascript"></script>
	<script src="${basePath}js/bootstrap-fileupload.js" type="text/javascript"></script>
	<script src="${basePath}js/jquery-fileupload/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="${basePath}js/jquery-fileupload/jquery.fileupload.js" type="text/javascript"></script>
	<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
	<script src="${basePath}js/jquery.fancybox.pack.js" type="text/javascript"></script>
	<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
	<%-- <script src="${basePath}js/ueditor/ueditor.config.js" type="text/javascript"></script>
<script src="${basePath}js/ueditor/ueditor.all.min.js" type="text/javascript"></script>
<script src="${basePath}js/ueditor/ueditor.parse.min.js" type="text/java script"></script> --%>
	<script type="text/javascript">
    $(function () {
    	listProductProperty(${project.productId});
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });

        $('#selectEnterprise').select2({
            placeholder: "请输入企业名称搜索",
            minimumInputLength: 0,
            ajax: {
                url: "${basePath}enterprise/list/usable?type=0",
                dataType: 'json',
                quietMillis: 100,
                data: function (term) {
                    return {
                        keyword: term, //search term
                    };
                },
                results: function (data) {
                    return {results: data};
                }
            },
            initSelection: function (element, callback) {
                callback({id: '${project.enterpriseId}', name: '${project.enterprise.name}'});//调用formatSelection
            },
            formatResult: function (object, container, query) {
                return object.name;
            },
            formatSelection: function (object, container) {
                //选中时触发
                return object.name;
            },
            escapeMarkup: function (m) {
                return m;
            } // we do not want to escape markup since we are displaying html in results
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
            $("#picName").val("");
            $("#target").attr("src", "");
            $("#add-picture").attr("disabled", true);

            $("#picName2").val("");
            $("#target2").attr("src", "");
            $("#add-picture2").attr("disabled", true);
        });
        $(".fancybox").fancybox({
            maxWidth: 700,
            height: 'auto',
            fitToView: false,
            autoSize: true,
            padding: 15,
            nextEffect: 'fade',
            prevEffect: 'fade',
            helpers: {
                title: {
                    type: "outside"
                }
            }
        });
        $("#fileupload").fileupload({
            url: "upload?type=1",
            maxFileSize: 10000000, //10M
            autoUpload: false, //不自动上传
            acceptFileTypes: /(\.|\/)(gif|jpe?g|png|bmp)$/i,
            formData: new FormData().append("picName", $.trim($("#picName").val())),
            add: function (e, data) {
                var file = data.files[0];
                if (!new RegExp(/(\.|\/)(gif|jpe?g|png|bmp)$/i).test(file.type)) {
                    $(".alert-danger .alert-content").text("错误的图片类型");
                    $(".alert-danger").fadeIn().delay(2000).fadeOut();
                    return false;
                }
                if (file.size >10000000) {//10M
                    $(".alert-danger .alert-content").text("图片不能大于10M");
                    $(".alert-danger").fadeIn().delay(2000).fadeOut();
                    return false;
                }
                var reader = new FileReader();
                reader.onload = function (e) {
                    $('#target').attr('src', e.target.result);
                };
                reader.readAsDataURL(file);
                data.context = $("#add-picture").unbind("click").bind("click", function () {
                    if ($.trim($("#picName").val()) == '') {
                        alert("请输入图片名称");
                        return;
                    }
                    data.submit();
                });
                $("#add-picture").attr("disabled", false);
            },
            done: function (e, result) {
                var data = JSON.parse(result.result);
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
                    $html.insertBefore($("#enterprise-picture"));
                    $("#myModal").modal("hide");
                    //添加隐藏输入框 保存当前的图片ID
                    $("#validate-form").append('<input id="picture-' + data.object.id + '" type="hidden" name="picture" value="' + data.object.id + '"/>');
                }
            }
        });

        $("#fileupload2").fileupload({
            url: "upload?type=13",
            autoUpload: false, //不自动上传
            formData: new FormData().append("picName", $.trim($("#picName2").val())),
            add: function (e, data) {
                var file = data.files[0];
                if (!new RegExp(/(\.|\/)(gif|jpe?g|png|bmp)$/i).test(file.type)) {
                    $(".alert-danger .alert-content").text("错误的图片类型");
                    $(".alert-danger").fadeIn().delay(2000).fadeOut();
                    return false;
                }
                if (file.size > 10000000) {//10M
                    $(".alert-danger .alert-content").text("图片不能大于10M");
                    $(".alert-danger").fadeIn().delay(2000).fadeOut();
                    return false;
                }
                var reader = new FileReader();
                reader.onload = function (e) {
                    $('#target2').attr('src', e.target.result);
                };
                reader.readAsDataURL(file);
                data.context = $("#add-picture2").unbind("click").bind("click", function () {
                    if ($.trim($("#picName2").val()) == '') {
                        alert("请输入图片名称");
                        return;
                    }
                    data.submit();
                });
                $("#add-picture2").attr("disabled", false);
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
                    $html.insertBefore($("#borrow-picture"));
                    $("#myModal2").modal("hide");
                    //添加隐藏输入框 保存当前的图片ID
                    $("#validate-form").append('<input id="picture-' + data.object.id + '" type="hidden" name="picture2" value="' + data.object.id + '"/>');
                }
            }
        });

        $('.select2able').select2();
        $("#validate-form").validate({
            rules: {
                title: {
                    required: true,
                    maxlength: 128,
                    <%--remote: {--%>
                        <%--url: "checkName?id=${project.id}",     //后台处理程序--%>
                        <%--type: "get",--%>
                        <%--dataType: "json",--%>
                        <%--data: {                     //要传递的数据--%>
                            <%--username: function () {--%>
                                <%--return $("#title").val();--%>
                            <%--}--%>
                        <%--}--%>
                    <%--}--%>
                },
                annualized: {
                    range: [1, 20]
                },
                deadline: {
                    date: true
                },
                limitDays: {
                    digits: true
                },
                totalAmount: {
                    digits: true
                },
                projectDescription: {
                    maxlength: 1024
                },
                useOfFunds: {
                    maxlength: 1024
                },
                repaymentSource: {
                    maxlength: 1024
                },
                investorsNum: {
                    digits: true
                },
                collateralInfo: {
                    maxlength: 1024
                },
                riskControlMethod: {
                    maxlength: 1024
                },
                involvingLawsuitInfo: {
                    maxlength: 1024
                },
                suggestion: {
                    maxlength: 1024
                },
                transferable:{
                	digits: true,
                	range: [1, 365]
                }
            },
            messages: {
                title: {
                    required: "请输入项目名",
                    maxlength: "项目名不超过128个字符",
                    // remote: "项目名称已存在"
                },
                annualized: {
                    range: "请输入1-20之间的小数"
                },
                deadline: {
                    date: "请输入正确的时间格式"
                },
                limitDays: {
                    digits: "请输入正确的整数"
                },
                totalAmount: {
                    digits: "请输入正确的整数"
                },
                projectDescription: {
                    maxlength: "不能超过1024个字符"
                },
                useOfFunds: {
                    maxlength: "不能超过1024个字符"
                },
                repaymentSource: {
                    maxlength: "不能超过1024个字符"
                },
                investorsNum: {
                    digits: "请输入正确的整数"
                },
                collateralInfo: {
                    maxlength: "不能超过1024个字符"
                },
                riskControlMethod: {
                    maxlength: "不能超过1024个字符"
                },
                involvingLawsuitInfo: {
                    maxlength: "不能超过1024个字符"
                },
                suggestion: {
                    maxlength: "不能超过1024个字符"
                },
                transferable:{
                	digits: "请输入正确的整数",
                	rang: "请输入0-365之间的整数"
                }
            }
        });
        var detailOption = {
            placeholder: "请选债权模版",
            minimumInputLength: 0,
            ajax: {
                url: "${basePath}project/getContractTitleList",
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
                callback({id: '${map.detailId}', title: '${map.detailTitle}'});//调用formatSelection
            },
            formatResult: function (object, container, query) {
                return object.title;
            },
            formatSelection: function (object, container) {
                //选中时触发
                var id = object.id;
                $('#detailId').val(id);
                return object.title;
            },
            escapeMarkup: function (m) {
                return m;
            } // we do not want to escape markup since we are displaying html in results
        };
        $('#detailId').select2(detailOption);

        <c:if test="${!empty map.userId}">
        var option = {
            placeholder: "请输入用户昵称、手机号或者真实姓名搜索",
            minimumInputLength: 0,
            multiple: true,
            ajax: {
                url: "${basePath}user/useSuperInvestor",
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
                callback({id: '${map.userId}', username: '${map.username}', phone: '${map.phone}'});//调用formatSelection
            },
            formatResult: function (object, container, query) {
                return object.username + "(" + object.phone + ")";
            },
            formatSelection: function (object, container) {
                //选中时触发

                $.ajax({
                    type: "GET",
                    url: "${basePath}project/userAssetsBalance",
                    data: {userId: object.id, totalAmount: $("#totalAmount").val()},
                    dataType: "json",
                    success: function (adata) {
                        if (adata) {
                            alert("超级投资人余额不足");
                        }
                    }
                });
                return object.username + "(" + object.phone + ")";
            },
            escapeMarkup: function (m) {
                return m;
            }, // we do not want to escape markup since we are displaying html in results
        };
        $('#selectUser').select2(option);
        </c:if>

    });

    function goToPage(obj) {
        var vl  = $("#limitDays").val();
        var vl2 = $('#noob').val();
        console.log(vl+"   " + vl2 )
        if (vl == 30 && vl2 == 0) {
            $("#annualized").val(7.8);
        }
        if (vl == 30 && vl2 == 1) {
            $("#annualized").val(12);
        }

        console.log(obj.value)
        if (obj.value == 1) {
            $('#tag').val('新手专享');
        } else if (obj.value == 2) {
            $('#tag').val('VIP专享');
        } else {
            $('#tag').val(null);
        }

    }
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
   	 if('${project.transferable gt 0}'){
		 return true;
	 }
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
       	return true;
    }

    function checkPost() {
    	if(clearResult()==false){
    		return;
    	}
    	
		var sex = $('#sex').val();
		if (sex == -1) {
			alert('请选择性别');
			$('#sex').focus();
			return false;
		}
    	
		/*var totalAmount=$.trim($("#totalAmount").val());
    	if (/^[1-9]\d*00$/.test(totalAmount)==false) {
            alert("项目募集金额必须是100的整数倍");
            return false;
        }
         var annualized = $('#annualized').val();
        var increaseAnnualized = $('#increaseAnnualized').val();
        if(parseFloat(annualized)+parseFloat(increaseAnnualized)>= 1){
            alert("年化收益+年化收益加息要小于1！");
            return false;
        } */
        var limitDays = $('#limitDays').val();
        var couponDays = $('#rateCouponDays').val();
        if (parseInt(couponDays) <= 0) {
            alert("加息券生息天数要大于0！");
            return false;
        } else if (parseInt(couponDays) > parseInt(limitDays)) {
            alert("加息券生息天数要在1到项目期限天数内！");
            return false;
        }
        var checked=$("#transferablecheck").is(':checked')
       	var transferable = $('#transferable').val();
       	if(checked==true){
       		if(parseInt(transferable)>parseInt(limitDays)){
       			$("#result").text("输入的天数必须大于1且小于项目期限！");
       			alert("输入的天数必须大于1且小于项目期限！");
       			return false;
       		}
       		if(parseInt(transferable)<=0){
       			$("#result").text("可转让期限天数要大于0！");
       			alert("可转让期限天数要大于1！");
       			return false;
       		}
       	}
       	
    	var earNumber=$.trim($('#earNumber').val());
		if(earNumber==''){
			alert('请填写耳标号');
			return false;
		}
		
		var prairieValue=$.trim($('#prairieValue').val());
		if(prairieValue==''){
			alert('请选择牧场');
			$('#prairieValue').focus();
			return false;
		}
		
		
		var earNumber_=$('#earNumber_').val();
		if(earNumber_=='yes'){
			alert('已发布的项目中，该耳标号已存在！');
			return false;
		}
       	
		var age=$('#age').val();
		if(testInt(age)==false){
		    alert('月龄必须为整数');
			return false;
		}

		
        var res = confirm("确定发布吗？没有后悔药，请仔细核对数据 ！");
        if(!res){
    		return res;
        }

		
        $('#validate-form').submit();
    }
  //切换产品属性
	function listProductProperty(id) {
		var productId = $('#productId').val(); //选中的值
		if(id != ""){
			productId = id;
		}
		$.ajax({
			url : "${basePath}project/listProductPropertyAjax",
			data : {
				productId : productId
			},
			dataType : "json",
			success : function(result) {
				packagePropertyHtml(result);
				sian();
			}
		});
	}
	//组装属性html
	function packagePropertyHtml(obj) {
		console.log(obj);
		var html = '';
		var inputType='';
		if (obj.length) {
			$
					.each(
							obj,
							function(index, ele) {
								console.log(ele.propertyName);
								var vl = $("#property_"+ele.id).val();
								if(typeof vl == "undefined"){
									vl = "";
								}
								
								
								if(ele.propertyName =='月龄'){
									inputType='type="number"';
									html += '<div class="form-group">'
										+ '<label class="control-label col-md-2 flag">'
										+ ele.propertyName
										+ '</label>'
										+ '<input   name="ids" value="'+ele.id+'"  type="hidden">'
										+ '<div class="col-md-7">'
										+ '<input class="form-control" name="category"  id="age" '+inputType+' value="'+vl+'">'
										+ '</div>' + '</div>';
								}else{
									inputType='type="text"';
									html += '<div class="form-group">'
										+ '<label class="control-label col-md-2 flag">'
										+ ele.propertyName
										+ '</label>'
										+ '<input   name="ids" value="'+ele.id+'"  type="hidden">'
										+ '<div class="col-md-7">'
										+ '<input class="form-control" name="category" '+inputType+' value="'+vl+'">'
										+ '</div>' + '</div>';
								}
								
								

							});

			$('.selectHTML').html(html);
		}
	}
	
	function ifExistProjectByEarnumberAjax() {
		var earNumber = $('#earNumber').val(); //选中的值
		var projectId=$('#id').val();
		$.ajax({
			url : "${basePath}project/ifExistProjectByEarnumberAjax",
			data : {
				earNumber : earNumber,
				projectId:projectId
			},
			dataType : "json",
			success : function(result) {
				if(result.code==1||result.code==-1){
					$('#earNumber_').val('yes'); 
					$('#earNumber').val('');
					$('#earNumber').attr('placeholder','上架请输入耳标号，且唯一');		
					alert(result.msg);
				}else{
					$('#earNumber_').val('no'); 
				}
			}
		});
	}
	
	$('[name=status]').click(function(){
		var status=$('[name=status]:checked').val();
		var old_earNumber=$('#earNumber_1').val();
		if(status==1){
			ifExistProjectByEarnumberAjax();
		}else{
			$('#earNumber_').val('');
			$('#earNumber').val(old_earNumber);
		}
		
	})
	
			 function sortNumber(){
				var sort=$('#sort').val();
				if(sort>99){
					$('#sort').val(99);
				}
		}
	function testInt(str){
		 if(!/^\d+$/.test(str))
		return false;
	 }
</script>
</body>
</html>
