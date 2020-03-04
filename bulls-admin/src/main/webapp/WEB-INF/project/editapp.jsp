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
    <title>编辑活期项目</title>
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
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                项目管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>编辑活期项目
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}project/add" method="post" class="form-horizontal" id="validate-form">
                            <input type="hidden" value="${project.id }" name="id"/>
                            <input type="hidden" name="projectType" value="2"/>

                            <div class="form-group">
                                <label class="control-label col-md-2">借款协议</label>

                                <div class="col-md-7 upload-picture">
                                    <c:forEach var="pic" items="${project.pictures }">
                                        <c:if test="${pic.type==2}">
                                            <a class="gallery-item fancybox" rel="g1"
                                               href="/upload/${pic.upload.path }"
                                               picId="${pic.id }" title="${pic.name }">
                                                <img src="${aPath}upload/${pic.upload.path }"/>

                                                <div class="actions">
                                                    <i class="icon-trash"></i><i class="icon-zoom-in"></i>
                                                </div>
                                            </a>
                                        </c:if>
                                    </c:forEach>
                                    <a data-toggle="modal" href="#myModal" id="enterprise-picture">
                                        <i class="iconfont" style="font-size: 150px;cursor: pointer;">&#xe602;</i>
                                    </a>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">项目名称</label>

                                <div class="col-md-7">
                                    <input disabled class="form-control" value="${project.title }"
                                           placeholder="请输入项目名称" type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">年化利率</label>

                                <div class="col-md-7">
                                    <input class="form-control" type="text"
                                           value="${project.annualized}" disabled>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">总融资金额</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="totalAmount" id="totalAmount"
                                           placeholder="请输入总融资金额(单位万元)" type="text" value="${project.totalAmount}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">月租金收入</label>

                                <div class="col-md-7">
                                    <input rows="5" class="form-control" placeholder="请输入月租金收入" name="rentalIncome"
                                           value="${project.rentalIncome}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">项目编号</label>

                                <div class="col-md-7">
                                    <input rows="5" class="form-control" placeholder="请输入项目编号例如（借2016092001）"
                                           name="itemNumber" value="${project.itemNumber}"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">项目详情</label>

                                <div class="col-md-7">
                                    <textarea rows="5" class="form-control" placeholder="请输入项目详情"
                                              name="projectDescription">${project.projectDescription}</textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">项目地址</label>

                                <div class="col-md-7">
                                    <textarea rows="5" class="form-control" placeholder="请输入项目地址"
                                              name="address">${project.address}</textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">资金用途</label>

                                <div class="col-md-7">
                                    <textarea rows="5" class="form-control" placeholder="请输入资金用途"
                                              name="useOfFunds">${project.useOfFunds}</textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">还款来源</label>

                                <div class="col-md-7">
                                    <textarea rows="5" class="form-control" placeholder="请输入还款来源"
                                              name="repaymentSource">${project.repaymentSource}</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">抵(质)押物</label>

                                <div class="col-md-7">
                                    <textarea rows="5" class="form-control" placeholder="请输入抵(质)押物"
                                              name="collateralInfo">${project.collateralInfo}</textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">风险保障金</label>

                                <div class="col-md-7">
                                    <textarea rows="5" class="form-control" placeholder="请输入风险保障金信息"
                                              name="riskMoney">${project.riskMoney}</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">状态</label>

                                <div class="col-md-7">
                                    <label class="radio-inline">
                                        <input name="status" type="radio" value="0"
                                               <c:if test="${project.status == 0 }">checked</c:if>>
                                        <span>创建</span>
                                    </label>
                                    <label class="radio-inline">
                                        <input name="status" type="radio" value="1"
                                               <c:if test="${project.status == 1 }">checked</c:if>>
                                        <span>预购</span>
                                    </label>
                                    <label class="radio-inline">
                                        <input name="status" type="radio" value="2"
                                               <c:if test="${project.status == 2 }">checked</c:if>>
                                        <span>投资中</span>
                                    </label>
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
<div class="modal fade" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" class="close" data-dismiss="modal" type="button">&times;</button>
                <h4 class="modal-title">
                    借款协议
                </h4>
            </div>
            <div class="modal-body">
                <form action="" class="form-horizontal" id="picture-form">
                    <div class="form-group">
                        <label class="control-label col-md-2">图片名称</label>

                        <div class="col-md-7">
                            <input class="form-control" name="picName" id="picName" placeholder="请输入图片名称" type="text"/>
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
                            <img src="" id="target" width="200px;"/>

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
                <button class="btn btn-primary" id="add-picture" disabled>添加</button>
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
<script src="${basePath}js/ueditor/ueditor.config.js" type="text/javascript"></script>
<script src="${basePath}js/ueditor/ueditor.all.min.js" type="text/javascript"></script>
<script src="${basePath}js/ueditor/ueditor.parse.min.js" type="text/java script"></script>
<script type="text/javascript">
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
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
        });

        $("#fileupload").fileupload({
            url: "${basePath}project/upload?type=2",
            autoUpload: false, //不自动上传
            formData: new FormData().append("picName", $.trim($("#picName").val())),
            add: function (e, data) {
                var file = data.files[0];
                if (!new RegExp(/(\.|\/)(gif|jpe?g|png|bmp)$/i).test(file.type)) {
                    $(".alert-danger .alert-content").text("错误的图片类型");
                    $(".alert-danger").fadeIn().delay(2000).fadeOut();
                    return false;
                }
                if (file.size > 2000000) {//10M
                    $(".alert-danger .alert-content").text("图片大于2M");
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
                    $html.insertBefore($("#enterprise-picture"));
                    $("#myModal").modal("hide");
                    //添加隐藏输入框 保存当前的图片ID
                    $("#validate-form").append('<input id="picture-' + data.object.id + '" type="hidden" name="picture" value="' + data.object.id + '"/>');
                }
            }
        });

        $("#validate-form").validate({
            rules: {
                title: {
                    required: true
                },
                annualized: {
                    required: true
                },
                totalAmount: {
                    required: true
                }
            },
            messages: {
                title: {
                    required: "请输入项目名称"
                },
                annualized: {
                    required: "请输入年化利率"
                },
                totalAmount: {
                    required: "请输入总融资金额"
                }
            }
        });
    });
</script>
</body>
</html>