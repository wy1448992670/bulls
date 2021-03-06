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
    <title>使用加息券</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                使用加息券
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>使用加息券
                    </div>
                    <div class="widget-content padded clearfix">
                        <form method="post" class="form-horizontal" id="validate-form" enctype="multipart/form-data">
                            <div class="form-group">
                                <label class="control-label col-md-2">批量派发</label>

                                <div class="col-md-7">
                                    <input type="file" name="file" title="选择txt文件"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">消息标题</label>

                                <div class="col-md-7">
                                    <input class="form-control" type="text" name="title" placeholder="发完加息券消息的标题！"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">消息内容</label>

                                <div class="col-md-7">
                                    <input class="form-control " type="text" value="" name="descript"
                                           placeholder="发完加息券消息的内容！"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">加息券类型</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="type">
                                        <option value="2">活期加息券</option>
                                        <option value="3">散标加息券</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">指定到用户</label>

                                <div class="col-md-7">
                                    <input id="selectUser" name="userId" type="hidden"/>
                                </div>
                                <div class="col-md-3"><span class="label label-danger">如不选择用户，则发送给所有用户！</span></div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7">
                                    <a class="btn btn-sm btn-primary-outline" id="add-row">点击添加</a>
                                </div>
                            </div>


                            <div id="hongbaoList" name="" class="form-group">

                            </div>


                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7 text-center">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" data-loading-text="努力派发中..." type="button"
                                            id="send">派发
                                    </button>
                                </div>
                            </div>

                            <input type="hidden" name="template" id="template" value=""/>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- end DataTables Example -->
    </div>
</div>

<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script type="text/javascript">


    var index = 0;
    var temp = "";
    $('#add-row').on('click', function () {
        temp = '<div class="form-group checkGroup" id="row' + (index) + '">' +
                '<input class="hongbaoClass" type="hidden" name="templateId" value=""/>' +
                '<label class="control-label col-md-2"><a class="btn btn-sm btn-primary-outline delete-row">删除</a></label>' +
                '<div class="col-md-1">' +
                '<input class="form-control pull-left rateClass" name="rate" id="1" value="0.005"  placeholder="请输入派发的利率" type="text">' +
                '</div>' +
                '<div class="col-md-1">' +
                '<label class="label label-info" style="font-size: 16px;">加息券利率</label>' +
                '</div>' +
                '<div class="col-md-1">' +
                '<input class="form-control datepicker  enClass" name="limitAmount" id="2"   value="<fmt:formatDate value="${date}" pattern="yyyy-MM-dd"/>"  placeholder="请输入有效期" type="text">' +
                '</div>' +
                '<div class="col-md-1">' +
                '<label class="label label-info" style="font-size: 16px;">有效期</label>' +
                '</div>' +


                '<div class="col-md-1">' +
                '<input class="form-control pull-left daysClass" name="limitAmount" id="3"  value=""  placeholder="请输入加息天数" type="text">' +
                '</div>' +
                '<div class="col-md-1">' +
                '<label class="label label-info" style="font-size: 16px;">加息天数</label>' +
                '</div>' +


                '</div>';
        index = index + 1;

        $(temp).appendTo($("#hongbaoList"));

        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });
    });

    $('#hongbaoList').on("click", ".delete-row", function () {
        if (confirm('是否删除加息券信息？')) {
//                console.log( $(this).parent().parent())
            $(this).parent().parent().remove();
        }
    });

    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });


        $('.select2able').select2();
        var option = {
            placeholder: "请输入用户昵称、手机号或者真实姓名搜索",
            minimumInputLength: 0,
            multiple: true,
            ajax: {
                url: "${basePath}user/list/app/usable",
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
                return object.username + "(" + object.phone + ")";
            },
            escapeMarkup: function (m) {
                return m;
            } // we do not want to escape markup since we are displaying html in results
        };
        $('#selectUser').select2(option);
        $("#send").click(function () {
            var text = $.trim($("#rate").val());
//            if (text == '') {
//                alert('请输入需要派发的利率');
//                return false;
//            }
            var group = $('.checkGroup');
            var templates = [];
            var sumRate = 0;
            if (group.length < 1) {
                alert('至少添加一个加息券');
                return false;
            }
            for (var i = 0; i < group.length; i++) {
                var template = {};
                var o = $('.checkGroup').eq(i);
                template.rate = o.find('.rateClass')[0].value;
                sumRate = sumRate + o.find('.rateClass')[0].value;
                if (o.find('.rateClass')[0].value == "" || o.find('.rateClass')[0].value > 0.25 || sumRate > 0.1) {
                    alert('利率不能为空,并且单个利率小于0.025，利率之和小于0.1');
                    return false;
                }
                template.en = o.find('.enClass')[0].value;

                if (o.find('.enClass')[0].value == "") {

                    alert('有效期不能为空');
                    return false;
                }
                var date = new Date();
                var applydate = o.find('.enClass')[0].value;

                var applyyear = applydate.split("-")[0];
                var applymonth = applydate.split("-")[1] - 1;
                var applyday = applydate.split("-")[2];
                var applydate1 = new Date(applyyear, applymonth, applyday);
                if (applydate1 < date) {
                    alert('有效期应当大于当前日期');
                    return false;
                }

                template.days = o.find('.daysClass')[0].value;
                if (o.find('.daysClass')[0].value == "" || o.find('.daysClass')[0].value > 30) {
                    alert('加息天数不能为空，并且不能大于30天');
                    return false;
                }
                /*var checkList = $('.checkGroup').eq(i).find('.checkOne');
                 var monthType = {};
                 for (var j in checkList) {
                 monthType[checkList[j].name] = checkList[j].checked;
                 }
                 template.monthType = monthType;*/
                templates.push(template);
            }
            $('#template').val(JSON.stringify(templates));
//            alert(JSON.stringify(templates));


            if (confirm("您确定给用户派发加息券吗?!")) {
                $("#validate-form").submit();

            }
//     			window.setTimeout("window.location='list.action'",9000);
        });
    });


    function datepicker() {
        $(this).datepicker({
            format: 'yyyy-mm-dd'
        });
    }


</script>
</body>
</html>