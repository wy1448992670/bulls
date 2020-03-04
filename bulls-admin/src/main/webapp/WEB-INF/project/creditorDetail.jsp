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
    <link href="${basePath}js/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <title>债权配置详情页面</title>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                公告管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>债权配置详情页面
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}project/editCreditorDetail" method="post" class="form-horizontal"
                              id="validate-form" onSubmit="return checkInput()">
                            <div class="form-group">
                                <label class="control-label col-md-2">配置项目</label>

                                <div class="col-md-7">
                                    <input id=projectId name="projectId" type="hidden" placeholder="请选择你要配置债权的项目"
                                           value="${map.projectId }"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">债权标题</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="title" id="title" placeholder="请输入债权标题"
                                           value="${map.title }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">债权详细内容</label>

                                <div class="col-md-7">
                                    <textarea rows="10" class="form-control" name="content" id="content"
                                              placeholder="请输入债权详细内容">${map.content }</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">债权详情模板</label>

                                <div class="col-md-7">
                                    <input id=detailId name="detailId" type="hidden" placeholder="请选择你要的债权详情模板"
                                           value="${map.detailId }"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">债权状态</label>

                                <div class="col-md-7">
                                    <select class="select2" name="status">
                                        <option value="1" <c:if test="${map.status == 1 }">selected</c:if>>启用</option>
                                        <option value="2" <c:if test="${map.status == 2 }">selected</c:if>>停止</option>
                                    </select>
                                </div>
                            </div>
                            <%-- <div class="form-group">
                                <label class="control-label col-md-2">债权详情模板</label>
                                <div class="col-md-7">
                                  <select class="select2" name="status">
                                      <option value="" <c:if test="${status == null }">selected</c:if>>所有</option>
                                      <option value="0" <c:if test="${status == 0 }">selected</c:if>>创建</option>
                                      <option value="1" <c:if test="${status == 1 }">selected</c:if>>预购中</option>
                                      <option value="2" <c:if test="${status == 2 }">selected</c:if>>投资中</option>
                                      <option value="3" <c:if test="${status == 3 }">selected</c:if>>投资完成</option>
                                      <option value="4" <c:if test="${status == 4 }">selected</c:if>>还款中</option>
                                      <option value="5" <c:if test="${status == 5 }">selected</c:if>>投资完成</option>
                                      <option value="6" <c:if test="${status == 6 }">selected</c:if>>投资失败</option>
                                  </select>
                              </div> --%>
                    </div>
                    <input type="hidden" name="id" id="id" value="${map.id }"/>

                    <div class="form-group">
                        <label class="control-label col-md-2"></label>

                        <div class="text-center col-md-7">
                            <a class="btn btn-default-outline" onclick="javascript:window.history.go(-1);">取消</a>
                            <button class="btn btn-success" type="submit" id="pub">提交</button>
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

<%--     <script src="${basePath}js/ueditor/ueditor.config.js" type="text/javascript"></script>
    <script src="${basePath}js/ueditor/ueditor.all.min.js" type="text/javascript"></script>
    <script src="${basePath}js/ueditor/ueditor.parse.min.js" type="text/java script"></script> --%>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath }js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $('#cao').click(function () {
            $('#status').val(0);
        });
        $('#pub').click(function () {
            $('#status').val(1);
        });
        $('.select2').select2();
        var option = {
            placeholder: "请选择新增客服姓名",
            minimumInputLength: 0,
            ajax: {
                url: "${basePath}project/getProjectTitleOption?id=${map.projectId}",
                dataType: 'json',
                quietMillis: 100,
                data: function (term) {
                    return {
                        username: term,
                    };
                },
                results: function (data) {
                    return {results: data};
                }
            },
            initSelection: function (element, callback) {
                //初始化赋值
                callback({id: '${map.projectId}', title: '${map.projectTitle}'});//调用formatSelection
            },
            formatResult: function (object, container, query) {
                return object.title;
            },
            formatSelection: function (object, container) {
                //选中时触发
                var id = object.id;
                $('#projectId').val(id);
                return object.title;
            },
            escapeMarkup: function (m) {
                return m;
            } // we do not want to escape markup since we are displaying html in results
        };

        var detailOption = {
            placeholder: "请选择新增客服姓名",
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
        $('#projectId').select2(option);
        $('#detailId').select2(detailOption);


        /* 	var ue = UE.getEditor('container',{
         initialFrameHeight : 500
         });
         ue.ready(function(){
         ue.execCommand('insertHtml', $('#htmlContent').text());
         }); */

        $("#validate-form").validate({
            ignore: "",
            rules: {
                title: {
                    required: true,
                    maxlength: 64
                },
                textContent: {
                    required: true
                }
            },
            messages: {
                title: {
                    required: "请输入内容标题",
                    maxlength: "标题不能超过64个字符"
                },
                textContent: {
                    required: "请输入详细内容"
                },
                detailId: {
                    required: "请选择债权详情模板"
                }
            }
        });
    });

    function checkInput() {
        var detail = $('#detailId').val();
        if (detail == null || detail == "") {
            alert("请选择债权详情模板");
            return false;
        }
        return true;
    }
    ;
</script>
</body>
</html>