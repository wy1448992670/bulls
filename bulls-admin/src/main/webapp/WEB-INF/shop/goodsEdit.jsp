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
<title>编辑商品</title>
<link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/jquery-fileupload/jquery.fileupload.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}js/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
<SCRIPT type=text/javascript src="${basePath}ueditor/ueditor.config.js"></SCRIPT>
<SCRIPT type=text/javascript src="${basePath}ueditor/ueditor.all.js"></SCRIPT>
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
				<h1>商品管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							编辑商品
						</div>
						<div class="widget-content padded clearfix">
							<form action="goodsEditData" method="post" class="form-horizontal" id="validate-form">
								<input type="hidden" value="${good.id }" name="id" />

								<div class="form-group">
									<label class="control-label col-md-2">
										商品封面图
										<br>
										<font color="red">(图片尺寸：350px*350px)</font>
									</label>
									<div class="col-md-7 upload-picture">
										<c:forEach var="pic" items="${good.goodsPictures }">
											<c:if test="${pic.type==14}">
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
									<label class="control-label col-md-2">
										商品详细图
										<br>
										<font color="red">(图片尺寸：750px*750px)</font>
									</label>
									<div class="col-md-7 upload-picture">
										<c:forEach var="pic" items="${good.goodsPictures }">
											<c:if test="${pic.type==12}">
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
									<label class="control-label col-md-2">商品名称</label>
									<div class="col-md-7">
										<input class="form-control" name="goodsName" value="${good.goodsName }" type="text">
									</div>
								</div>
								<%-- 		<div class="form-group">
									<label class="control-label col-md-2">企业名称</label>

									<div class="col-md-7">
										<input id="selectEnterprise" value="${project.enterpriseId }" name="enterpriseId" type="hidden" />
									</div>
								</div> --%>
								<%-- 		<div class="form-group">
									<label class="control-label col-md-2">年化收益</label>

									<div class="col-md-7">
										<input class="form-control" id="annualized" name="annualized" value="${project.annualized }" placeholder="请输入年化收益" type="text">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">年化收益加息</label>

									<div class="col-md-7">
										<input class="form-control" id="increaseAnnualized" name="increaseAnnualized" placeholder="请输入年化收益加息" type="text" value="${project.increaseAnnualized }">
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">商品期限</label>

									<div class="col-md-7 input-group">
										<input class="form-control" id="limitDays" name="limitDays" value="${project.limitDays }" placeholder="请输入还款期限,单位为天" type="text" onblur="clearCheck()">
										<div class="input-group-addon">天</div>
									</div>
								</div> --%>
								<div class="form-group">
									<label class="control-label col-md-2">商品编号</label>

									<div class="col-md-7">
										<input class="form-control" name="goodsNo" id="goodsNo" type="text" value="${good.goodsNo }">
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">所属品牌</label>
									<div class="col-md-7">
										<select class="select2able" id="brandId" name="brandId">
											<option value="">请选择</option>
											<c:if test="${!empty goodsBrands}">
												<c:forEach items="${goodsBrands}" var="goodsBrand">
													<option value="${goodsBrand.id}" <c:if test="${goodsBrand.id==good.brandId }">  selected="selected" </c:if>>${goodsBrand.brandName}</option>
												</c:forEach>
											</c:if>
										</select>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">进货价格</label>

									<div class="col-md-7">
										<input class="form-control" id="buyingPrice" name="buyingPrice" type="number" value="${good.buyingPrice}">
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">销售价</label>

									<div class="col-md-7">
										<input class="form-control" id="salingPrice" name="salingPrice" type="number" value="${good.salingPrice }">
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">会员价</label>

									<div class="col-md-7">
										<input class="form-control" name="memberSalingPrice" id="memberSalingPrice" type="number" value="${good.memberSalingPrice }">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">库存</label>

									<div class="col-md-7">
										<input class="form-control" name="stock" id="stock" type="number" value="${good.stock }">
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">库存单位</label>
									<div class="col-md-7">
										<select class="select2able" id="stockUnit" name="stockUnit">
											<option value="-1">---请选择---</option>
											<c:forEach items="${stockUnit }" var="stockUnit">
												<option value="${stockUnit.tValue }" <c:if test="${good.stockUnit==stockUnit.tValue }"> selected="selected"</c:if>>${stockUnit.tValue }</option>
											</c:forEach>
										</select>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">商品类别</label>
									<div class="col-md-7">
										<select class="select2able" onchange="listGoodsProperty('');" id="categoryId" name="categoryId">
											<option value="">请选择</option>
											<c:if test="${!empty goodsCategoryList}">
												<c:forEach items="${goodsCategoryList}" var="goodsCategory">
													<option value="${goodsCategory.id}" <c:if test="${goodsCategory.id == good.categoryId }">selected</c:if>>${goodsCategory.categoryName}</option>
												</c:forEach>
											</c:if>
											<c:forEach var="property" items="${properesMaps}">
												<input id="property_${property.propertyId}" value="${property.propertyValue}" type="hidden" />
											</c:forEach>
										</select>
									</div>
								</div>



								<!-- 类目显示 -->
								<div class="selectHTML"></div>
								<div class="form-group">
									<label class="control-label col-md-2">排序</label>
									<div class="col-md-7">
										<input class="form-control" name="sort" id="sort" type="number" value="${good.sort }" onblur="sortNumber();">
										<br>
										<font color="red">最大值99，数值越小，排名越靠前</font>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">SKU码</label>

									<div class="col-md-7">
										<input class="form-control" name="skuCode" id="skuCode" placeholder="请输入SKU码" type="text" value="${good.skuCode }">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">商品重量(KG)</label>

									<div class="col-md-7">
										<input class="form-control" name="weight" id="weight" placeholder="请输入商品重量" type="number" value="${good.weight }">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">商品介绍</label>

									<div class="col-md-7">
										<!-- 加载编辑器的容器 -->
										<script id="container" name="container" type="text/plain" height="500"></script>
										<textarea rows="" cols="" hidden="true" name="introductionHtml" id="htmlContent">${good.introductionHtml }</textarea>
										<textarea rows="" cols="" hidden="true" name="introduction" id="textContent">${good.introduction }</textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">已出售</label>
									<div class="col-md-7">
										<input class="form-control" name="sellStock" id="sellStock" type="number" value="${good.sellStock }">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">是否上架</label>
									<div class="col-md-7">
										<select class="select2able" name="upDown" id="upDown">
											<option value="">---请选择---</option>
											<option value="1" <c:if test="${good.upDown == 1 }">selected</c:if>>已上架</option>
											<option value="0"  <c:if test="${good.upDown == 0 }">selected</c:if>>未上架</option>
										</select>
									</div>
								</div>

								<%-- 								<input type="hidden" value="${project.projectType }" name="projectType" /> --%>
								<div class="form-group">
									<label class="control-label col-md-2"></label>

									<div class="text-center col-md-7">
										<a class="btn btn-default-outline" onclick="javascript:window.history.go(-1);">取消</a>
										<button class="btn btn-primary" type="button" onclick="checkPost();">编辑</button>
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
					<h4 class="modal-title">商品图片上传</h4>
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
					<h4 class="modal-title">商品图片上传</h4>
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
	var ue = UE.getEditor('container', {
        initialFrameHeight: 500
    });
    
    ue.ready(function () {
        ue.execCommand('insertHtml', $('#htmlContent').text());
    });
    $(function () {
    	listGoodsProperty(${good.categoryId});
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
            url: "upload?type=14",
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
                if (file.size > 512000*6) {//10M
                    $(".alert-danger .alert-content").text("图片大于3M");
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
            url: "upload?type=12",
            autoUpload: false, //不自动上传
            formData: new FormData().append("picName", $.trim($("#picName2").val())),
            add: function (e, data) {
                var file = data.files[0];
                if (!new RegExp(/(\.|\/)(gif|jpe?g|png|bmp)$/i).test(file.type)) {
                    $(".alert-danger .alert-content").text("错误的图片类型");
                    $(".alert-danger").fadeIn().delay(2000).fadeOut();
                    return false;
                }
                if (file.size > 512000*6) {//10M
                    $(".alert-danger .alert-content").text("图片大于3M");
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
                    remote: {
                        url: "checkName?id=${project.id}",     //后台处理程序
                        type: "get",
                        dataType: "json",
                        data: {                     //要传递的数据
                            username: function () {
                                return $("#title").val();
                            }
                        }
                    }
                },
                annualized: {
                    range: [0, 1]
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
                    required: "请输入商品名",
                    maxlength: "商品名不超过128个字符",
                    remote: "商品名称已存在"
                },
                annualized: {
                    range: "请输入0-1之间的小数"
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
    	var totalAmount=$.trim($("#totalAmount").val());
/*     	if (/^[1-9]\d*00$/.test(totalAmount)==false) {
            alert("商品募集金额必须是100的整数倍");
            return false;
        } */
        var annualized = $('#annualized').val();
        var increaseAnnualized = $('#increaseAnnualized').val();
        if(parseFloat(annualized)+parseFloat(increaseAnnualized)>= 1){
            alert("年化收益+年化收益加息要小于1！");
            return false;
        }
        var limitDays = $('#limitDays').val();
        var couponDays = $('#rateCouponDays').val();
        if (parseInt(couponDays) <= 0) {
            alert("加息券生息天数要大于0！");
            return false;
        } else if (parseInt(couponDays) > parseInt(limitDays)) {
            alert("加息券生息天数要在1到商品期限天数内！");
            return false;
        }
        var checked=$("#transferablecheck").is(':checked')
       	var transferable = $('#transferable').val();
       	if(checked==true){
       		if(parseInt(transferable)>parseInt(limitDays)){
       			$("#result").text("输入的天数必须大于1且小于商品期限！");
       			alert("输入的天数必须大于1且小于商品期限！");
       			return false;
       		}
       		if(parseInt(transferable)<=0){
       			$("#result").text("可转让期限天数要大于0！");
       			alert("可转让期限天数要大于1！");
       			return false;
       		}
       	}
       	
		 var stockUnit=$('#stockUnit').val();
		 if(stockUnit=='-1'){
			 alert('请选择库存单位');
			 return false;
		 }
		 
		 var sellStock = $("#sellStock").val();
		 if(sellStock < 0){
			 alert('售出数量不能小于0');
			 return false;
		 }
		 var upDown = $("#upDown").val();
		 if(upDown == '' || upDown == null){
			alert('请选择是否上架');
			return false;
		 }
       	$('#htmlContent').text(ue.getContent());
        $('#textContent').text(ue.getContentTxt());
        $('#validate-form').submit();
    }
  //切换产品属性
	function listGoodsProperty(id) {
		var categoryId = $('#categoryId').val(); //选中的值
		if(id != ""){
			categoryId = id;
		}
		$.ajax({
			url : "${basePath}shop/listGoodsPropertyAjax",
			data : {
				categoryId : categoryId
			},
			dataType : "json",
			success : function(result) {
				packagePropertyHtml(result);
			}
		});
	}
	//组装属性html
	function packagePropertyHtml(obj) {
		console.log(obj);
		var html = '';
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
								html += '<div class="form-group">'
										+ '<label class="control-label col-md-2 flag">'
										+ ele.propertyName
										+ '</label>'
										+ '<input   name="ids" value="'+ele.id+'"  type="hidden">'
										+ '<div class="col-md-7">'
										+ '<input class="form-control" name="category" type="text" value="'+vl+'">'
										+ '</div>' + '</div>';
							});

			$('.selectHTML').html(html);
		}
	}
	
	 function sortNumber(){
			var sort=$.trim($('#sort').val());
			if(sort==''){
				$('#sort').val(99);
			}
			if(sort>99){
				$('#sort').val(99);
			}
	}
</script>
</body>
</html>