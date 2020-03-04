<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>编辑企业</title>
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
                企业管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>编辑企业
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="edit" method="post" class="form-horizontal" id="validate-form">
                            <div class="form-group">
                                <label class="control-label col-md-2">企业相关图片</label>

                                <div class="col-md-7 upload-picture">
                                    <c:forEach var="pic" items="${enterprise.enterprisePicture }">
                                        <c:if test="${pic.type==0}">
                                            <a class="gallery-item fancybox" rel="g1" href="/upload/${pic.picturePath }"
                                               picId="${pic.id }" title="${pic.name }">
                                               
                                                <c:choose> 
                                                	<c:when test="${fn:endsWith(pic.name, '.png') || fn:endsWith(pic.name, '.jpg') || fn:endsWith(pic.name, '.gif')}">  
                                                		<img src="${aPath}upload/${pic.picturePath}"/>


												   </c:when>

                                                    <c:when test="${fn:endsWith(pic.name, '.docx') || fn:endsWith(pic.name, '.doc')}">
                                                        <img src="${aPath}upload/icons/W.jpg"/>
                                                    </c:when>

                                                    <c:when test="${fn:endsWith(pic.name, '.xlsx') || fn:endsWith(pic.name, '.xls')}">
                                                        <img src="${aPath}upload/icons/X.jpg"/>
                                                    </c:when>

                                                    <c:when test="${fn:endsWith(pic.name, '.pdf')}">
                                                        <img src="${aPath}upload/icons/PDF.jpg"/>
                                                    </c:when>

                                                    <c:when test="${fn:endsWith(pic.name, '.zip')}">
                                                        <img src="${aPath}upload/icons/ZIP.jpg"/>
                                                    </c:when>

                                                    <c:when test="${fn:endsWith(pic.name, '.ppt')}">
                                                        <img src="${aPath}upload/icons/P.jpg"/>
                                                    </c:when>

                                                </c:choose>

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
                                <label class="control-label col-md-2">企业公章</label>

                                <div class="col-md-7 upload-picture">
                                    <c:forEach var="pic" items="${enterprise.enterprisePicture }">
                                        <c:if test="${pic.type==1}">
                                            <a class="gallery-item fancybox" rel="g1"
                                               href="/upload/${pic.picturePath }"
                                               picId="${pic.id }" title="${pic.name }">
                                                <img src="${aPath}upload/${pic.picturePath }"/>

                                                <div class="actions">
                                                    <i class="icon-trash"></i><i class="icon-zoom-in"></i>
                                                </div>
                                            </a>
                                        </c:if>
                                    </c:forEach>
                                    <a data-toggle="modal" href="#myModal2" id="borrow-picture">
                                        <i class="iconfont" style="font-size: 150px;cursor: pointer;">&#xe602;</i>
                                    </a>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">用户类型</label>
                                <div class="col-md-7">
                                    <select class="select2able" name="type" disabled="disabled" >
                                        <option value="0" <c:if test="${enterprise.type ==0 }">selected</c:if>>企业用户</option>
                                        <option value="1" <c:if test="${enterprise.type ==1 }">selected</c:if>>个人用户</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">企业编号</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="no" id="no" value="${enterprise.no }"
                                           placeholder="请输入企业编号" type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">企业名称</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="name" id="name" value="${enterprise.name }"
                                           placeholder="请输入企业名称" type="text">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="control-label col-md-2">手机号码</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="phone" id="phone" value="${enterprise.phone }" type="text">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="control-label col-md-2">银行卡号</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="cardNumber" id="cardNumber"  value="${enterprise.cardNumber }" type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">企业简介</label>

                                <div class="col-md-7">
                                    <!-- 加载编辑器的容器 -->
                                    <script id="container" name="content" type="text/plain"></script>
                                    <textarea rows="" cols="" hidden="true" name="intro"
                                              id="htmlContent">${enterprise.intro }</textarea>
                                    <textarea rows="" cols="" hidden="true" name="textContent"
                                              id="textContent"></textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">企业背景</label>

                                <div class="col-md-7">
                                    <textarea class="form-control" name="background" placeholder="请输入企业背景 (个人住址或公司地址)"
                                              rows="5">${enterprise.background }</textarea>
                                </div>
                            </div>

                            <c:if test="${enterprise.type ==0 }">
                                <div class="form-group"  id="range">
                                    <label class="control-label col-md-2">营业范围</label>

                                    <div class="col-md-7">
                                    <textarea class="form-control" name="scope" placeholder="请输入营业范围"
                                              rows="5">${enterprise.scope }</textarea>
                                    </div>
                                </div>

                                <div class="form-group" id="condition">
                                    <label class="control-label col-md-2">经营状况</label>

                                    <div class="col-md-7">
                                    <textarea class="form-control" name="conditionState" placeholder="请输入经营状况"
                                              rows="5">${enterprise.conditionState }</textarea>
                                    </div>
                                </div>



                            </c:if>


                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" type="submit" id="edit">编辑</button>
                                </div>
                            </div>
                            <input type="hidden" name="id" value="${enterprise.id }"/>
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
                    项目图片上传
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


<div class="modal fade" id="myModal2">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" class="close" data-dismiss="modal" type="button">&times;</button>
                <h4 class="modal-title">
                    企业公章图片
                </h4>
            </div>
            <div class="modal-body">
                <form action="" class="form-horizontal" id="picture-form2">
                    <div class="form-group">
                        <label class="control-label col-md-2">图片名称</label>

                        <div class="col-md-7">
                            <input class="form-control" name="picName" id="picName2" placeholder="请输入图片名称" type="text"/>
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
                            <img src="" id="target2" width="200px;"/>

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
                <button class="btn btn-primary" id="add-picture2" disabled>添加</button>
            </div>
        </div>
    </div>
</div>

<script type=text/javascript src="${basePath}ueditor/ueditor.config.js"></script>  
<script type=text/javascript src="${basePath}ueditor/ueditor.all.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-fileupload.js" type="text/javascript"></script>
<script src="${basePath}js/jquery-fileupload/jquery.ui.widget.js" type="text/javascript"></script>
<script src="${basePath}js/jquery-fileupload/jquery.fileupload.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.fancybox.pack.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
    	
    	$('#edit').click(function () {
            $('#htmlContent').text(ue.getContent());
            $('#textContent').text(ue.getContentTxt());
        });
        var ue = UE.getEditor('container', {
            initialFrameHeight: 500
        });
        ue.ready(function () {
            ue.execCommand('insertHtml', $('#htmlContent').text());
        });

        ue.addListener("keyup", function () {
            $('#htmlContent').text(ue.getContent());
            $('#textContent').text(ue.getContentTxt());
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
        $("#enterprise-picture").click(function () {
            $("#picName").val("");
            $("#target").attr("src", "");
            $("#add-picture").attr("disabled", true);
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
            url: "upload?type=0&id="+$('[name=id]').val(),
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
                if (file.size > 10000000) {//10M
                    $(".alert-danger .alert-content").text("图片大于10M");
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
            url: "upload?type=1&id="+$('[name=id]').val(),
            autoUpload: false, //不自动上传
            formData: new FormData().append("picName", $.trim($("#picName2").val())),
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
                no: {
                    required: true,
                    maxlength: 255
                },
                name: {
                    required: true,
                    maxlength: 255,
                    remote: {
                        url: "checkName?id=${enterprise.id}",     //后台处理程序
                        type: "get",
                        dataType: "json",
                        data: {                     //要传递的数据
                            username: function () {
                                return $("#name").val();
                            }
                        }
                    }
                },
                intro: {
                    maxlength: 1024
                },
                background: {
                    maxlength: 1024
                },
                scope: {
                    maxlength: 1024
                },
                conditionState: {
                    maxlength: 1024
                }
            },
            messages: {
                no: {
                    required: "请输入企业编号",
                    maxlength: "企业编号不能超过255个字"
                },
                name: {
                    required: "请输入企业名",
                    maxlength: "企业名不能超过255个字",
                    remote: "企业名已存在"
                },
                intro: {
                    maxlength: "企业简介不能超过1024个字"
                },
                background: {
                    maxlength: "企业背景不能超过1024个字"
                },
                scope: {
                    maxlength: "经营范围不能超过1024个字"
                },
                condition_state: {
                    maxlength: "经营状况不能超过1024个字"
                }
            }
        });
        
       
    });
</script>
</body>
</html>