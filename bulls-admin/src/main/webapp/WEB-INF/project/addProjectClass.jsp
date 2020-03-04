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
    <title>创建一级项目</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/jquery-fileupload/jquery.fileupload.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
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
                一级项目管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>创建一级项目
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="addOrEditProjectClass" method="post" class="form-horizontal" id="validate-form" onsubmit="return CheckPost();">
                            <input type="hidden" name="id" value="${project.id }"/>
                            <div class="form-group">
                                <label class="control-label col-md-2">一级项目名称</label>

                                <div class="col-md-7">
                                    <!-- 加载编辑器的容器 -->
                                    <input class="form-control" name="projectTitle" id="projectTitle" value="${project.projectTitle }" placeholder="请输入一级项目名称"
                                           type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">设备类型</label>
                                <div class="col-md-7">
                                    <select class="select2able" name="deviceType">
                                        <option value="APP" <c:if test="${project.deviceType == 'APP' }">selected</c:if>>APP</option>
                                        <option value="PC" <c:if test="${project.deviceType == 'PC' }">selected</c:if>>PC</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">年化收益</label>
                                <div class="col-md-7">
                                    <input class="form-control" name="annualized" placeholder="请输入年化收益" type="text"
                                           value="${project.annualized }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">年化收益加息</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="increaseAnnualized" placeholder="请输入年化收益加息"
                                           type="text" value="${project.increaseAnnualized }">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">限制天数</label>

                                <div class="col-md-7">
                                    <input class="form-control" id="limitDays" value="${project.limitDays}" name="limitDays" placeholder="项目期限"
                                           type="text" onkeyup="value=value.replace(/[^\d]/g,'')">
                                </div>
                            </div>


                            <div class="form-group" id="noob">
                                <label class="control-label col-md-2">是否是新手标</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="noob" onchange="goToPage(this)">
                                        <option value="0" <c:if test="${project.noob == 0 }">selected</c:if>>普通散标项目</option>
                                        <option value="1" <c:if test="${project.noob == 1 }">selected</c:if>>
                                            新手散标项目
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">项目标记</label>

                                <div class="col-md-7">
                                    <input rows="5" class="form-control" placeholder="请输入标记例如(新手专享) 多个以-号隔开，最多不超过两个"
                                           name="tag" id="tag" value="${project.tag}"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">万元预期收益</label>

                                <div class="col-md-7">
                                    <input class="form-control" id="preAmount" value="${project.preAmount}" name="preAmount" placeholder="万元预期收益" type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">排序</label>

                                <div class="col-md-7 upload-picture">
                                    <input class="form-control" name="sort" maxlength="2"
                                           value="<fmt:formatNumber type="number" value="${project.sort}" groupingUsed="false" maxFractionDigits="0"/>"
                                           placeholder="请输入排序顺序, 数字越小, 优先级越大" type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">项目状态</label>

                                <div class="col-md-7">
                                    <input class="form-control" id="status" value="${project.status}" name="status" placeholder="项目状态（0不可用1可用）" type="text">

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

<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-fileupload.js" type="text/javascript"></script>
<script src="${basePath}js/jquery-fileupload/jquery.ui.widget.js" type="text/javascript"></script>
<script src="${basePath}js/jquery-fileupload/jquery.fileupload.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath}js/ueditor/ueditor.config.js" type="text/javascript"></script>
<script src="${basePath}js/ueditor/ueditor.all.min.js" type="text/javascript"></script>
<script src="${basePath}js/ueditor/ueditor.parse.min.js" type="text/java script"></script>
<script type="text/javascript">

    $(function () {
        $("#selectUsers").hide();
        var ue = UE.getEditor('container', {
            initialFrameHeight: 100
        });
        $('.select2able').select2();

        $("#validate-form").validate({
            rules: {
                projectTitle: {
                    required: true,
                    maxlength: 128,
                },
                annualized: {
                    required: true,
                    range: [0, 1]
                },
                limitDays: {
                    required: true,
                    digits: true
                },
                sort: {
                    required: true,
                    digits: true
                },
                status: {
                    required: true,
                    digits: true
                },
                preAmount: {
                    required: true,
                    number: true
                }
            },
            messages: {
                projectTitle: {
                    required: "请输入项目名",
                    maxlength: "项目名不超过128个字符",
                    remote: "项目名称已存在"
                },
                annualized: {
                    required: "请输入利率",
                    range: "请输入0-1之间的小数"
                },
                limitDays: {
                    required: "请输入项目期限",
                    digits: "请输入正确的整数"
                },
                sort: {
                    required: "请输入排序",
                    digits: "请输入正确的整数"
                },
                status: {
                    required: "项目状态",
                    digits: "请输入0或1"
                },
                preAmount: {
                    required: "请输入预计万元收益",
                    number: "请输入正确的数字"
                }
            }
        });
    });


        function goToPage(obj) {
            if (obj.value == 1) {
                $('#tag').val('新手专享');
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

        function CheckPost() {
            var limitDays = $('#limitDays').val();
            if (parseInt(limitDays) <= 0) {
                alert("项目期限要大于0！");
                return false;
            } else {
                return true;
            }
        }

</script>
</body>
</html>