<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>权限管理</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}js/easyui/easyui.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}js/easyui/icon.css" media="all" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        .widget-content .panel-body {
            padding: 0;
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
                权限管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>权限管理列表
                        <shiro:hasPermission name="role:all">
                            <a class="btn btn-sm btn-primary-outline pull-right" href="add" id="add-row"><i
                                    class="icon-plus"></i>添加权限</a>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="role:all">
                            <a class="btn btn-sm btn-primary-outline pull-right" href="javascript:void(0)"
                               id="edit-row"><i class="icon-edit"></i>编辑权限</a>
                        </shiro:hasPermission>
                    </div>
                    <div class="widget-content padded clearfix">
                        <table class="tree table table-bordered table-striped easyui-treegrid" id="table"
                               url="${basePath}resources/list/ajax" rownumbers="true" idField="id" treeField="name">
                            <thead>
                            <tr>
                                <th field="name" width="10%">
                                    权限名称
                                </th>
                                <th field="url" width="10%">
                                    链接地址
                                </th>
                                <th field="permission" width="10%">
                                    权限字符串
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <!-- end DataTables Example -->
    </div>
</div>
<script type="text/javascript" src="${basePath}js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${basePath}js/easyui/jquery.treegrid.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $('#edit-row').click(function () {
            var row = $('#table').treegrid('getSelected');
            if (row) {
                window.open("${basePath}resources/edit?id=" + row.id);
            } else {
                alert("请选择一行进行编辑");
            }
        });
    });

    function edit() {
        if (editingId != undefined) {
            $('#tg').treegrid('select', editingId);
            return;
        }

    }
</script>
</body>
</html>