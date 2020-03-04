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
    <title>角色详细信息</title>
    <link href="${basePath}js/JQuery zTree v3.3/css/demo.css" rel="stylesheet" type="text/css"/>
    <link href="${basePath}js/JQuery zTree v3.3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                角色管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>角色详细信息
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="editResources" method="post" class="form-horizontal" id="validate-form">
                            <input type="hidden" name="roleId" id="id" value="${role.id }"/>
                            <input type="hidden" name="resourcesId" id="resourcesId"/>

                            <div class="form-group">
                                <label class="control-label col-md-2">角色名称</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${role.role }
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">角色描述</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${role.description }
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">角色权限</label>

                                <div class="widget-content padded text-center">
                                    <div class="graph-container">
                                        <div class="caption"></div>
                                        <div id="panel">
                                            <ul id="tree" class="ztree"></ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" type="submit">提交</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="${basePath}js/JQuery zTree v3.3/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath}js/JQuery zTree v3.3/js/jquery.ztree.core-3.3.js"></script>
<script type="text/javascript" src="${basePath}js/JQuery zTree v3.3/js/jquery.ztree.excheck-3.3.js"></script>
<script type="text/javascript">
    $(function () {
        var zTreeObj;
        var setting = {
            check: {
                autoCheckTrigger: false,//false 不触发事件回调事件
                chkboxType: {"Y": "ps", "N": "s"},//勾选，取消都关联子
                chkStyle: "checkbox",
                nocheckInherit: true,
                enable: true //显示checkbox
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentId",
                    rootPid: 1	//待定，不晓得意义
                },
                key: {
                    checked: "checked",
                    url: "#"
                }
            },
            callback: {
                onCheck: zTreeOnCheck
            }
        };
        var roleId = $.trim($("#id").val());
        $.ajax({
            url: "${basePath}role/showResources?roleId=" + roleId,
            dataType: "json",
            success: function (data) {
            	console.log(data);
                if (data) {
                    zTreeObj = $.fn.zTree.init($("#tree"), setting, data.roleResource);
                }
                zTreeOnCheck();
            }
        });
        function zTreeOnCheck(event, treeId, treeNode) {
            var nodes = new Array();
            //取得选中的结点
            nodes = zTreeObj.getCheckedNodes(true);
            var ids = [];
            for (var i = 0; i < nodes.length; i++) {
                ids[i] = nodes[i].id;
            }
            $("#resourcesId").val(ids);
        }
    });
    //chkboxType属性：被勾选时：（Y属性，p代表：关联父，s代表：关联子）；取消勾选时：（N属性，p代表：关联父，s代表：关联子）

</script>
</body>
</html>