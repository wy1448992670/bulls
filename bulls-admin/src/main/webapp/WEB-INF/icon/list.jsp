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
    <title>Icon查询</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="${basePath}css/style.css">
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                运营管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>Icon查询
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-4 pull-right" id="form" action="deleteIcons"
                              method="post">
                            <div class="row">
                                <div class="form-group col-md-12">
                                    <a class="btn btn-sm btn-primary-outline pull-right hidden-xs"
                                       href="${basePath}icon/add?page=${page}" id="add-row"><i class="icon-plus"></i>添加ICON</a>
                                    <button class="btn btn-primary pull-right hidden-xs" type="button" id="deleteBtn"
                                            onclick="deleteIcons()"> 删除
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="widget-content padded clearfix">
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                    <label>
                                        <input class="icon-class" id="checkAll" name="checkAll" type="checkbox">
                                        <span></span>
                                    </label>
                                </th>
                                <th>序号</th>
                                <!-- <th>关联ID</th> -->
                                <th>icon类型</th>
                                <th>icon名称</th>
                                <th>icon图标</th>
                                <th>所属组别</th>
                                <th>所属版本</th>
                                <th>所属组状态</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${list }" var="l" varStatus="st">
                                <tr class="dataTr">
                                    <input type="hidden" name="linkId" id="linkId" value="${l.linkId }"/>
                                    <input type="hidden" name="uploadId" id="uploadId" value="${l.uploadId }"/>
                                    <input type="hidden" name="iconId" id="iconId" value="${l.iconId }"/>
                                    <td>
                                        <label>
                                            <input class="checkOne" name="check${st.index+1}" type="checkbox">
                                            <span></span>
                                        </label>
                                    </td>
                                    <td>${st.index+1 }</td>
                                    <td>
                                        <select class="select2able" name="type" id="type" disabled="disabled">
                                            <option value="" <c:if test="${l.type == null}">selected</c:if>>----
                                            </option>
                                            <option value="0" <c:if test="${l.type == 0 }">selected</c:if>>首页</option>
											<option value="1" <c:if test="${l.type == 1 }">selected</c:if>>个人主页</option>
											<option value="2" <c:if test="${l.type == 2 }">selected</c:if>>tabbarIcon</option>
											<option value="3" <c:if test="${l.type == 3 }">selected</c:if>>tabbarIconGray</option>
											<option value="4" <c:if test="${l.type == 4 }">selected</c:if>>账户中心</option>
											<option value="5" <c:if test="${l.type == 5 }">selected</c:if>>关于我们</option>
											<option value="6" <c:if test="${l.type == 6 }">selected</c:if>>商城导航</option>
											<option value="7" <c:if test="${l.type == 7 }">selected</c:if>>商城首页-ICON</option>
											<option value="8" <c:if test="${l.type == 8 }">selected</c:if>>商城首页-活动</option>
                                        </select>
                                    </td>
                                    <td><a href="${basePath}icon/add?id=${l.linkId}&page=${page}"><p
                                            style="margin:0;text-align:center;">${l.title }</p></a></td>
                                    <td>
                                        <img src="${aPath }upload/${l.path }"/>
                                    </td>
                                    <td>${l.groupTitle}</td>
                                    <td>${l.version}</td>
                                    <td>
                                        <select class="select2able" name="status" id="status" disabled="disabled">
                                            <option value="" <c:if test="${l.status == null}">selected</c:if>>----
                                            </option>
                                            <option value="0" <c:if test="${l.status == 0}">selected</c:if>>闲置</option>
                                            <option value="1" <c:if test="${l.status == 1}">selected</c:if>>启用</option>
                                        </select>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                        <ul id="pagination" style="float: right"></ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script type="text/javascript">

    $(function () {
        $('.select2able').select2();
        $("#checkAll").on("click", function () {
            var check = $(this).is(':checked');
            if (check) {
                $(".checkOne").prop("checked", true);
            } else {
                $(".checkOne").prop("checked", false);
            }
        });

        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "list?page=" + page;
            }
        });

        $("#deleteBtn").on('click', function () {
            var array = getData();
            var checkArray = [];
            var temp = "";
            for (var i in array) {
                temp += '<input type="hidden" name="links"  value="' + array[i].linkId + '" />'
                        + '<input type="hidden" name="uploads"  value="' + array[i].uploadId + '" />'
                        + '<input type="hidden" name="icons"  value="' + array[i].iconId + '" />';
                checkArray.push(array[i].linkId);
            }

            $.ajax({
                url: '${basePath}icon/checkDelete',
                type: 'get',
                data: 'ids=' + checkArray,
                dataType: "json",
                success: function (result) {
                    if (result.code == "success") {
                        if (result.count > 0) {
                            alert("艾武林报错啦！所选删除的icon含有分组配置！");
                        } else {
                            $("#form").append(temp);
                            $("#form").submit();
                        }
                    } else {
                        alert("艾武林报错啦！赶快去查东西！");
                    }
                }
            });

        });
    });

    function getData() {
        var array = [];
        var length = $(".dataTr").length;
        var check = {};
        for (var i = 0; i < length; i++) {
            var checked = $(".dataTr").eq(i).find(".checkOne").prop("checked");
            if (checked) {
                var data = {};
                var linkId = $(".dataTr").eq(i).find("#linkId").val();
                var uploadId = $(".dataTr").eq(i).find("#uploadId").val();
                var iconId = $(".dataTr").eq(i).find("#iconId").val();

                data.linkId = linkId;
                data.uploadId = uploadId;
                data.iconId = iconId;
                array.push(data);
            }
        }
        return array;
    }
    ;
</script>
</body>
</html>