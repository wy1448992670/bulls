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
    <title>创建个人周期标</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/jquery-fileupload/jquery.fileupload.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
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
            <h1>
                项目管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>发布个人周期标
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="add" method="post" class="form-horizontal" id="validate-form" onsubmit="return check();">
                            <div class="form-group">
                                <label class="control-label col-md-2">项目照片</label>

                                <div class="col-md-7 upload-picture">
                                    <a data-toggle="modal" href="#myModal" id="enterprise-picture">
                                        <i class="iconfont" style="font-size: 150px;cursor: pointer;">&#xe602;</i>
                                    </a>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">借款协议</label>

                                <div class="col-md-7 upload-picture">
                                    <a data-toggle="modal" href="#myModal2" id="borrow-picture">
                                        <i class="iconfont" style="font-size: 150px;cursor: pointer;">&#xe602;</i>
                                    </a>
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="control-label col-md-2">项目名称</label>

                                <div class="col-md-7">
                                    <!-- 加载编辑器的容器 -->
                                    <%--<script id="container" name="title" type="text/plain" height="500"></script>--%>
                                    <%--<textarea rows="" cols="" hidden="true" name="title"--%>
                                    <%--id="title"></textarea>--%>
                                    <input class="form-control" name="title" id="title" placeholder="请输入项目名称"
                                           type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">个人名称</label>

                                <div class="col-md-7">
                                    <input id="selectEnterprise" name="enterpriseId" type="hidden"/>
                                </div>
                            </div>

                            <%--<div class="form-group">--%>
                            <%--<label class="control-label col-md-2">担保机构</label>--%>

                            <%--<div class="col-md-7">--%>
                            <%--<input id="selectGuarantee" name="guaranteeInstitutionId" type="hidden"/>--%>
                            <%--</div>--%>
                            <%--</div>--%>

                            <div class="form-group">
                                <label class="control-label col-md-2">年化收益</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="annualized" placeholder="请输入年化收益" type="text"
                                           value="0.07">
                                </div>
                            </div>
                            <input type="hidden" name="increaseAnnualized" value="0"/>
                            <%--<div class="form-group">--%>
                                <%--<label class="control-label col-md-2">年化收益加息</label>--%>

                                <%--<div class="col-md-7">--%>
                                    <%--<input class="form-control" name="increaseAnnualized" placeholder="请输入年化收益加息"--%>
                                           <%--type="text" value="0">--%>
                                <%--</div>--%>
                            <%--</div>--%>

                            <%--<div class="form-group">--%>
                                <%--<label class="control-label col-md-2">投资截至时间</label>--%>

                                <%--<div class="col-md-7">--%>
                                    <%--<input class="form-control datepicker" name="deadline" type="text"--%>
                                           <%--placeholder="请选择投资截至时间">--%>
                                <%--</div>--%>
                            <%--</div>--%>

                            <div class="form-group">
                                <label class="control-label col-md-2">还款期限</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="limitDays" placeholder="请输入还款期限,单位为天" type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">项目募集额</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="totalAmount" id="totalAmount"
                                           placeholder="请输入项目募集额,单位是元"
                                           type="text">
                                </div>
                            </div>
                            <input value="5" name="projectType" type="hidden"/>
                            <input value="0" name="noob" type="hidden"/>
                            <div class="form-group">
                                <label class="control-label col-md-2">债权详情模板</label>

                                <div class="col-md-7">
                                    <input id=detailId name="detailId" type="hidden" placeholder="请选择你要的债权详情模板"
                                    />
                                </div>
                            </div>
                            <%--<div class="form-group">--%>
                                <%--<label class="control-label col-md-2">月租金收入</label>--%>

                                <%--<div class="col-md-7">--%>
                                    <%--<input rows="5" class="form-control" placeholder="请输入月租金收入" name="rentalIncome"/>--%>
                                <%--</div>--%>
                            <%--</div>--%>

                            <div class="form-group">
                                <label class="control-label col-md-2">项目详情</label>

                                <div class="col-md-7">
                                    <textarea rows="5" class="form-control" placeholder="请输入项目详情"
                                              name="projectDescription"></textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">项目地址</label>

                                <div class="col-md-7">
                                    <textarea rows="5" class="form-control" placeholder="请输入项目地址"
                                              name="address"></textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">资金用途</label>

                                <div class="col-md-7">
                                    <textarea rows="5" class="form-control" placeholder="请输入资金用途"
                                              name="useOfFunds"></textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">还款来源</label>

                                <div class="col-md-7">
                                    <textarea rows="5" class="form-control" placeholder="请输入还款来源"
                                              name="repaymentSource"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">抵(质)押物</label>

                                <div class="col-md-7">
                                    <textarea rows="5" class="form-control" placeholder="请输入抵(质)押物"
                                              name="collateralInfo"></textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">风险保障金</label>

                                <div class="col-md-7">
                                    <textarea rows="5" class="form-control" placeholder="请输入风险保障金信息"
                                              name="riskMoney"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">所属产品库</label>
                                <div id="divProductList" class="col-md-7">
                                    <%--<label class="radio-inline">
                                        <input name="productId" type="radio" value="0"  checked>
                                        <span>
                                            月月盈
                                        </span>
                                    </label>
                                    <label class="radio-inline">
                                        <input name="productId" type="radio" value="1">
                                        <span>
                                            季季升
                                        </span>
                                    </label>--%>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
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
                    借款协议图片
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
    function check(){
        var selectEnterprise = $("#selectEnterprise").val();
        if(selectEnterprise>0){
            return true;
        }
        alert("请选择企业名称");
        return false;
    }
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });
        $("#selectUsers").hide();
        var ue = UE.getEditor('container', {
            initialFrameHeight: 100
        });
        $('#selectEnterprise').select2({
            placeholder: "请输入个人名称搜索",
            minimumInputLength: 0,
            ajax: {
                url: "${basePath}enterprise/list/usable?type=1",
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
            autoUpload: false, //不自动上传
            formData: new FormData().append("picName", $.trim($("#picName").val())),
            add: function (e, data) {
                var file = data.files[0];
                if (!new RegExp(/(\.|\/)(gif|jpe?g|png|bmp)$/i).test(file.type)) {
                    $(".alert-danger .alert-content").text("错误的图片类型");
                    $(".alert-danger").fadeIn().delay(2000).fadeOut();
                    return false;
                }
                if (file.size > 512000) {//10M
                    $(".alert-danger .alert-content").text("图片大于500K");
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

        $("#fileupload2").fileupload({
            url: "upload?type=2",
            autoUpload: false, //不自动上传
            formData: new FormData().append("picName", $.trim($("#picName2").val())),
            add: function (e, data) {
                var file = data.files[0];
                if (!new RegExp(/(\.|\/)(gif|jpe?g|png|bmp)$/i).test(file.type)) {
                    $(".alert-danger .alert-content").text("错误的图片类型");
                    $(".alert-danger").fadeIn().delay(2000).fadeOut();
                    return false;
                }
                if (file.size > 512000) {//10M
                    $(".alert-danger .alert-content").text("图片大于500K");
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
                annualized: {
                    required: true,
                    range: [0, 1]
                },
                deadline: {
                    date: true
                },
                limitDays: {
                    required: true,
                    digits: true
                },
                totalAmount: {
                    required: true,
                    digits: true
                },
                <%--userId: {--%>
                <%--required: true,--%>
                <%--remote: {--%>
                <%--url: "${basePath}project/userAssetsBalance",     //后台处理程序--%>
                <%--type: "get",--%>
                <%--dataType: "json",--%>
                <%--data: {                     //要传递的数据--%>
                <%--userId: function () {--%>
                <%--return $("#userId").val();--%>
                <%--},--%>
                <%--totalAmount: function () {--%>
                <%--return $("#totalAmount").val();--%>
                <%--}--%>
                <%--}--%>
                <%--}--%>
                <%--},--%>
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
                }
            },
            messages: {
                title: {
                    required: "请输入项目名",
                    maxlength: "项目名不超过128个字符",
                    remote: "项目名称已存在"
                },
                annualized: {
                    required: "请输入利率",
                    range: "请输入0-1之间的小数"
                },
                deadline: {
                    date: "请输入正确的时间格式"
                },
                limitDays: {
                    required: "请输入项目期限",
                    digits: "请输入正确的整数"
                },
                totalAmount: {
                    required: "请输入总募集金额",
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
                }
//                userId: {
//                    required: "请选择超级投资用户",
//                    remote: "超级投资人账户余额不够"
//                }
            }
        });


        var detailOption = {
            placeholder: "请选债权模版",
            minimumInputLength: 0,
            ajax: {
                url: "${basePath}project/getContractTitleList?id=6",
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


        $.ajax({
            type: "GET",
            url: "${basePath}product/ajaxProductList",
            dataType: "json",
            success: function (adata) {
                var html = "";
                for(var i=0;i<adata.length;i++) {
                    if(adata[i] != null){
                        var defaultCheck="";
                        var st="";
                        if(i==0){
                            defaultCheck = " checked"
                        }
                        if(adata[i]["status"]==0){
                            st="(未启用)";
                        }
                        html = html + "<label class='radio-inline'><input name='productId' type='radio' value="+adata[i]["id"]+defaultCheck+">"
                                +"<span>"+adata[i]["name"]+st+"</span></label>";
                    }
                }       
                $("#divProductList").html(html);
            }
        });
    });

    function goToPage(obj) {
        if (obj.value == 1) {
            $('#tag').val('新手专享');
        } else if (obj.value == 2) {
            $('#tag').val('VIP专享');
        } else {
            $('#tag').val(null);
        }
    }
    function goToPage2(obj) {
        if (obj.value == 3) {
            var totalAmount = $('#totalAmount').val();
            if (totalAmount == null || totalAmount == '') {
                alert("总投资金额为空");
            }
            $('#selectUsers').show();
            $('#noob').hide();
        } else {
            $('#selectUsers').hide();
        }
    }

</script>
</body>
</html>