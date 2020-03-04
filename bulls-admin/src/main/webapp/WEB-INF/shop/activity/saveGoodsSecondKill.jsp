<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
	<title>商城活动详情</title>
	<link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
	<link href="${basePath}css/fileupload/jquery.fileupload.css" media="all" rel="stylesheet" type="text/css"/>
	<link href="${basePath}css/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css"/>
	<link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
	<link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
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
				秒杀商品详情
			</h1>
		</div>
		<!-- DataTables Example -->
		<div class="row">
			<div class="col-lg-12">
				<div class="widget-container fluid-height clearfix">
					<div class="heading">
						<i class="icon-table"></i>秒杀商品详情
					</div>
					<div class="widget-content padded clearfix">
						<form action="${basePath}shop/activity/saveGoodsSecondKill" method="post" class="form-horizontal" id="validate-form" ENCTYPE="multipart/form-data">
							<div class="form-group">
								<label class="control-label col-md-2">活动时间区间</label>

								<div class="col-md-7">
									<p class="form-control-static">
										【<fmt:formatDate value="${activity.startDate}" pattern="yyyy-MM-dd"/>】 -
										【<fmt:formatDate value="${activity.stopDate}" pattern="yyyy-MM-dd"/>】
									</p>
								</div>
							</div>
							<c:if test="${data == null}">
								<div class="form-group">
									<label class="control-label col-md-2"></label>
									<div class="col-md-7">
										<button class="btn btn-primary" data-toggle="modal" data-target="#goodsListModal">选择商品</button>
									</div>
								</div>
							</c:if>
                            <div id="goods"
									<c:if test="${data.goodId == null}">style="display: none;"</c:if>
							>
                                <div class="form-group">
                                    <label class="control-label col-md-2">商品信息</label>

									<input type="hidden" name="goodId" id="goodId" value="${data.goodsId}"/>
                                    <div class="col-md-7">
                                        <div class="row">
                                            <div class="col-md-3">
												<div class="fileupload fileupload-new" data-provides="fileupload">
													<div class="fileupload-new img-thumbnail" style="width: 150px; height: 150px;">
														<c:if test="${data.activityImage == null}">
															<img id="activityImage" alt="秒杀活动图片" src="${aPath}images/no-image.gif">
														</c:if>
														<c:if test="${data.activityImage != null}">
															<img id="activityImage" alt="秒杀活动图片" src="${data.activityImage}">
														</c:if>
													</div>
													<div class="fileupload-preview fileupload-exists img-thumbnail"
														 style="width: 640px; max-height: 260px"></div>
													<div>
                                                        <span class="btn btn-default btn-file">
                                                            <span class="fileupload-new">选择图片</span>
                                                            <span class="fileupload-exists">修改</span>
                                                            <input type="file" name="file">
                                                        </span>
														<a class="btn btn-default fileupload-exists" data-dismiss="fileupload" href="#">删除</a>
													</div>
												</div>
                                            </div>
                                            <div class="col-md-6">
												<div>
													商品名称:
													<span id="goods-name">${data.goodsName}</span>
												</div>
												<div>
													商品库存:
													<span id="goods-stock">${data.stock}</span>
												</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">活动价格</label>
                                <div class="col-md-7">
                                    <input class="form-control" name="price" value="${data.price}" id="price" placeholder="请输入活动价格" type="number">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">会员价格</label>
                                <div class="col-md-7">
                                    <input class="form-control" name="memberPrice" value="${data.memberPrice}" id="memberPrice" placeholder="请输入会员活动价格" type="number">
                                </div>
                            </div>
							<div class="form-group">
								<label class="control-label col-md-2">开始时间</label>
								<div class="col-md-7">
									<input class="form-control" name="startTimeStr" value="<fmt:formatDate value="${data.startTime}" pattern="HH:mm:ss"/>" 
											id="startTimeStr" placeholder="请输入活动名称" type="text">
                                    <em style="color:red;">&nbsp;* 格式为:HH:mm:ss，如:15:00:00。</em>
									<%--<input type="hidden" name="startTime" id="startTime" />--%>
								</div>
								<input type="hidden" name="id" id="id" value="${data.id}"/>
								<input type="hidden" name="activityId" id="activityId" value="${activity.id}"/>
							</div>

							<div class="form-group">
								<label class="control-label col-md-2">秒杀时长（分钟）</label>

								<div class="col-md-7">
									<input class="form-control" name="killTime" value="${data.killTime}" id="killTime" placeholder="请输入秒杀时长" type="number">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-2">星期</label>

								<div class="col-md-7">
									<select class="select2able" name="weekDay" id="weekDay">
										<option value="">请选择</option>
										<option value="1" <c:if test="${data.weekDay == 1}">selected </c:if> >星期一</option>
										<option value="2" <c:if test="${data.weekDay == 2}">selected </c:if> >星期二</option>
										<option value="3" <c:if test="${data.weekDay == 3}">selected </c:if> >星期三</option>
										<option value="4" <c:if test="${data.weekDay == 4}">selected </c:if> >星期四</option>
										<option value="5" <c:if test="${data.weekDay == 5}">selected </c:if> >星期五</option>
										<option value="6" <c:if test="${data.weekDay == 6}">selected </c:if> >星期六</option>
										<option value="7" <c:if test="${data.weekDay == 7}">selected </c:if> >星期日</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-2">库存</label>

								<div class="col-md-7">
									<input class="form-control" name="stockCount" value="${data.stockCount}" id="stockCount" placeholder="请输入库存" type="number">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-2">每人限购</label>

								<div class="col-md-7">
									<input class="form-control" name="limitCount" value="${data.limitCount}" id="limitCount" placeholder="请输入每人限购" type="number">
								</div>
							</div>
							<c:if test="${optType == 'edit'}">
								<%--<div class="form-group">
									<label class="control-label col-md-2">已出售</label>

									<div class="col-md-7">
										<p class="form-control-static">
											<c:if test="${data.saledCount != null}">
												${data.saledCount }
											</c:if>
											<c:if test="${data.saledCount == null}">
												0
											</c:if>
										</p>
									</div>
								</div>
                                <div class="form-group">
                                    <label class="control-label col-md-2">活动商品锁定库存</label>

                                    <div class="col-md-7">
                                        <p class="form-control-static">
                                            <c:if test="${data.lockStock != null}">
                                                ${data.lockStock }
                                            </c:if>
                                            <c:if test="${data.lockStock == null}">
                                                0
                                            </c:if>
                                        </p>
                                    </div>
                                </div>--%>
							</c:if>
							<div class="form-group">
								<label class="control-label col-md-2">活动描述</label>

								<div class="col-md-7">
									<textarea class="form-control" name="title" id="title" placeholder="请输入活动描述">${data.title}</textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-2">是否可卖</label>

								<div class="col-md-7">
									<label class="radio-inline">
										<input type="radio" name="isOnShelves" value="1" <c:if test="${(data.isOnShelves == 1) || (optType == 'add' && data.isOnShelves == null) }">checked</c:if>>
										<span>上架</span>
									</label>
									<label class="radio-inline">
										<input type="radio" name="isOnShelves" value="0" <c:if test="${data.isOnShelves == 0 }">checked</c:if>>
										<span>下架</span>
									</label>
								</div>
							</div>
							
							<div class="form-group">
								<label class="control-label col-md-2"></label>

								<div class="text-center col-md-7">
									<a class="btn btn-default-outline" onclick="javascript:window.history.go(-1);">返回</a>
									<button class="btn btn-primary" type="button" onclick="saveGoodsSecondKill()" >保存</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- end DataTables Example -->
	</div>

	<div class="modal fade" id="goodsListModal" style="width:70%; height: 90%;margin: auto;" tabindex="-2" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-content">
			<div class="widget-content padded clearfix">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">选择商品</h4>
				</div>
				<div class="modal-body">
					<div class="heading">
						<i class="icon-table"></i>
						商品列表
						<div id="myModal">
							<form class="form-inline  col-md-12 pull-right col-xs-12" id="formRelation"  action="${basePath}user/list/addrelation">
								<input type="hidden" name="page" value="1" />

								<div class="row">
									<div class="form-group col-md-6">
									</div>
									<div class="form-group col-md-6">
                                        <div class="input-group">
                                            <input name="keyword" id="keyword" type="text" placeholder="请输入商品名称搜索" class="form-control keyword" />
                                            <span class="input-group-btn">
						                        <button type="button" class="btn btn-primary pull-right" onclick="searchGoods()">搜索</button>
                                            </span>
                                        </div>
									</div>
								</div>
							</form>
						</div>
						<div class="table-responsive">
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th></th>
                                    <th>ID</th>
                                    <th>商品名称</th>
                                    <th>图片</th>
                                    <th>编号</th>
                                    <th>进货价格</th>
                                    <th>销售价格</th>
                                    <th>会员价格</th>
                                    <th>库存</th>
                                    <th>活动库存</th>
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
					<input type="button" onclick="submitClick()" data-dismiss="modal" aria-hidden="true" class=" btn btn-primary" value="确认" />
					<input type="button" onclick="cancelClick()" class="btn" value="取消"/>
				</div>
			</div>
			<!-- /.modal-content -->
			<!-- /.modal -->
		</div>
	</div>
</div>


<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/jquery-fileupload/jquery.ui.widget.js" type="text/javascript"></script>
<script src="${basePath}js/jquery-fileupload/jquery.fileupload.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.fancybox.pack.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-fileupload.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $(".datepicker").datepicker({
            showSecond: true,
            format: 'hh:mm:ss',
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });
        $('.select2able').select2();
        validateForm();

    });
    
    function validateForm() {
        $("#validate-form").validate({
            rules: {
                price: {
                    required: true
                },
                memberPrice: {
                    required: true
                },
                startTime: {
                    required: true
                },
                killTime: {
					required: true
				},
                weekDay: {
                    required: true
                },
                stockCount: {
                    required: true
                },
                limitCount: {
                    required: true
                }
            },
            messages: {
                price: {
                    required: "活动价格不能为空"
                },
                memberPrice: {
                    required: "活动会员价格不能为空"
                },
                startTime: {
                    required: "活动开始时间不能为空"
                },
                killTime: {
                    required: "秒杀时长不能为空"
                },
                weekDay: {
                    required: "星期不能为空"
                },
                stockCount: {
                    required: "活动库存不能为空"
                },
                limitCount: {
                    required: "每人限购不能为空"
                }
            }
        });
	}
	
	function searchGoods() {
        var keyword = $("#keyword").val();
        if(keyword == '' || keyword == null){
            alert("请输入商品名称");
            return;
        }
        $.ajax({
            url: '${basePath}shop/listGoodsByAjax?keyword='+keyword,
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
                $.each(JSON.parse(data).list,function(i,ele){
                    str+="<tr>"+
                        "<td><input class='checknum' name='choice' type='radio' value='"+ele.id+"'  style='display: block; float: left;'></td>"+
                        "<td>"+ele.id+"</td>"+
                        "<td>"+ele.goodsName+"</td>"+
                        "<td><img src=" + ele.goodsPictures[0].upload.cdnPath+ " height='80px' width='80px'></td>"+
                        "<td>"+ele.goodsNo+"</td>"+
                        "<td>"+ele.buyingPrice+"</td>"+
                        "<td>"+ele.salingPrice+"</td>"+
                        "<td>"+ele.memberSalingPrice+"</td>"+
                        "<td>"+ele.stock+"</td>"+
                        "<td>"+(ele.sellStock == undefined? "" : ele.sellStock)+"</td>"+
                        "</tr>";
                });
                $(".tableContent").html(str);
            }
        });
    }

    function cancelClick(){
        $('#goodsListModal').find('.close').click();
    }

    function submitClick(){
        var goodsId = $(".checknum:checked").val();
        $.ajax({
            url: '${basePath}shop/getOneGoodsByAjax?id='+goodsId,
            type: "GET",
            dataType: "html",
            async: false,
            success: function (data) {
                var jsonData = JSON.parse(data);
                // console.log(jsonData);
                if(jsonData.status == 'error'){
                    alert(jsonData.message);
                    return;
                }
                var goods = jsonData.data;
                // console.log(goods);
                showGoodsDiv(goods);
            }
        });
    }

    function showGoodsDiv(goods) {
        if (goods == null || goods.id == undefined) {
            return false;
        }
        $("#goodId").val(goods.id);
        $("#goods-name").html(goods.goodsName);
        $("#goods-stock").html(goods.stock);
        if (goods.activityPicPath != undefined) {
            $("#activityImage").attr("src", goods.activityPicPath);
        }
        document.getElementById("goods").style.display = "block";
    }
    
    function checkStartTime() {
        // 开始时间 字符串
        var startTimeStr = $("#startTimeStr").val();
        // 开始时间校验规则正则
        var reg = /^([01][0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?$/;
        console.log("验证" + reg.test(startTimeStr));
        if (reg.test(startTimeStr)) {
            console.log(1);
            return true;
		}
        console.log(0);
		return false;
	}
    
    function saveGoodsSecondKill() {
        var goodsId = $("#goodId").val();
        if (goodsId == "") {
            alert("请先选择商品！");
            return false;
		}
		// 商品名称
		var goodsName = $("#goods-name").html();
        // 星期n（下拉框文本）
        var weekDayName = $("#weekDay").find("option:selected").text();
        // 表单校验对象
        var validator = $("#validate-form").validate();
        if (validator.form()) {
            // 校验日期格式
            if (!checkStartTime()) {
                alert("活动开始时间格式错误");
                return false;
			} else {
                var isOnShelves = $("input:radio[name='isOnShelves']:checked").val();
                // console.log("1 isOnShelves: " +isOnShelves);
                if ('${data.isOnShelves}' == '1' && isOnShelves == 0) {
                    if (confirm("是否确认下架秒杀商品"))	{
                        // 提交表单
                        $("#validate-form").submit();
					}
                    return false;
                }
                if ('${data.isOnShelves}' == '0' && isOnShelves == 1) {
                    if (!confirm("是否确认上架秒杀商品")) {
                        return false;
                    }
                }
                // 计算活动锁定库存
                $.ajax({
                    url: "${basePath}shop/activity/calculateLockStock",
                    type:"POST",
                    data: {
                        mallActivityId: ${activity.id},
                        weekDay: $("#weekDay").val(),
                        stockCount: $("#stockCount").val()
                    },
                    dataType:"json",
                    success:function(res){
                        console.log(res);
                        if (res.code == 1) {
                            var data = res.data;
                            var msg = "活动时间【"+data.startDate+" - "+data.stopDate+"】内，有【"+data.weekCount+"】个【"+weekDayName+"】" +
                                "，将锁定商品【"+goodsName+"】的库存数量为【"+data.lockStock+"】，是否确认保存？！";
                            if (confirm(msg)) {
                                console.log($("#validate-form").serialize());
                                // 提交表单
                                $("#validate-form").submit();
                            }
                        } else {
                            alert(res.msg)
                        }
                    }
                });
            }
        }
    }
	
	
</script>
</body>
</html>
