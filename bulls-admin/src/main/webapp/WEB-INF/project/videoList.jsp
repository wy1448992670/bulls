<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <title>视频列表</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css" />
	<link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
	<link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css" />
    <style type="text/css">
        .table .over {
            overflow: hidden;
            width: 40%;
            text-overflow: ellipsis;
            white-space: nowrap;
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
              	视频列表
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
               <div class="widget-container fluid-height clearfix">
					<div class="heading">
                        <i class="icon-table"></i>视频列表
                        <img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;"/>
                        <shiro:hasPermission name="project:addVideoPage">
                            <a class="btn btn-sm btn-primary-outline pull-right" href="addVideoPage" id="add-row"><i class="icon-plus"></i>添加视频</a>
                        </shiro:hasPermission>
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()">搜索</button>
                    </div>
                    <div class="widget-content padded clearfix">
                   		<div id="myModal">
							<form class="search1 AppFixed form-inline  col-md-6 pull-right col-xs-11" id="form" action="videoAlbumList">
								<input type="hidden" name="page" value="1" />
								<div class="row">
									<div class="form-group col-md-6">
										<div>
											<input name="keyword" type="text" placeholder="请输入标题关键字" class="form-control keyword" value="${keyword }" />
										</div>
									</div>
									<div class="form-group col-md-3 col-xs-6">
										<select class="select2able" name="isRecommend">
											<option value="" <c:if test="${isRecommend == null }">selected</c:if>>是否推荐</option>
											<option value="0" <c:if test="${isRecommend == 0 }">selected</c:if>>不推荐</option>
											<option value="1" <c:if test="${isRecommend == 1 }">selected</c:if>>推荐</option>
										</select>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-md-6">
										<div>
											<input class="form-control datepicker" value="<fmt:formatDate value='${beginCreateTime }' pattern="yyyy-MM-dd"/>" id="beginCreateTime" name="beginCreateTime" type="text" placeholder="请选择创建起始时间" />
										</div>
									</div>
									<div class="form-group col-md-6">
										<div>
											<input class="form-control datepicker" value="<fmt:formatDate value="${endCreateTime}" pattern="yyyy-MM-dd"/>" id="endCreateTime" name="endCreateTime" type="text" placeholder="请选择创建结束时间" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-md-6">
										<div>
											<input class="form-control datepicker" value="<fmt:formatDate value="${beginShowTime }" pattern="yyyy-MM-dd"/>" id="beginShowTime" name="beginShowTime" type="text" placeholder="请选择显示起始时间" />
										</div>
									</div>
									<div class="form-group col-md-6">
										<div>
											<input class="form-control datepicker" value="<fmt:formatDate value="${endShowTime}" pattern="yyyy-MM-dd"/>" id="endShowTime" name="endShowTime" type="text" placeholder="请选择显示结束时间" />
										</div>
									</div>
								</div>
								<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="search()">搜索</button>
								<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
							</form>
						</div>
					</div>
                    <div class="widget-content padded clearfix">
	                    <div class="table-responsive">
	                        <table class="table table-bordered table-hover">
	                            <thead>
	                            <tr>
	                                <th width="50px;">序号</th>
	                                <th>标题 </th>
	                                <th width="15%">视频封面地址 </th>
	                                <th width="15%">视频地址 </th>
	                                <th style="text-align: center;">是否推荐</th>
	                                <th style="text-align: center;">显示时间</th>
	                                <th style="text-align: center;">创建时间</th>
	                                <th style="text-align: center;">更新时间</th>
	                                <th style="text-align: center;">操作</th>
	                            </tr>
	                            </thead>
	                            <tbody>
	                            <c:forEach var="g" items="${list}" varStatus="s">
	                                <tr>
	                                    <td>
	                                            ${s.count}
	                                    </td>
	                                    <td>
	                                            ${g.title}
	                                    </td>
	                                    <td>
	                                         <img alt="" style="width: 230px; height: 130px;" src="${g.videoPageUrl }">
	                                    </td>
	                                    <td>
	                                            ${g.videoUrl }
	                                    </td>
	                                    <td  style="text-align: center;">
	                                    	<c:if test="${g.isRecommend == 1}"><span style="color: blue">是</c:if>
                                    		<c:if test="${g.isRecommend == 0}"><span style="color: red">否</c:if>
	                                    </td>
	                                    <td style="text-align: center;">
	                                        <fmt:formatDate value="${g.showTime }" pattern="yyyy-MM-dd HH:mm:ss" />
	                                    </td>
	                                    <td style="text-align: center;">
	                                        <fmt:formatDate value="${g.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
	                                    </td>
	                                    <td style="text-align: center;"> 
	                                        <fmt:formatDate value="${g.updateTime }" pattern="yyyy-MM-dd HH:mm:ss" />
	                                    </td>
	                                    <td style="text-align: center;">
		                                    <shiro:hasPermission name="project:videoDelete">
		                                    	<a href="#" onClick="deleteVideo('${g.id }');" >删除</a>
		                                    </shiro:hasPermission>
		                                    &nbsp; &nbsp;
		                                    <shiro:hasPermission name="project:editVideo">
		                                    	<a href="editVideo?id=${g.id}">编辑</a>
		                                    </shiro:hasPermission>
		                                    &nbsp; &nbsp;
		                                   <shiro:hasPermission name="project:videoRecommend">
			                                    <a href="#" onClick="updateRecommed('${g.id }','0')">
			                                    	<c:if test="${g.isRecommend == 1}"><span style="color: red">不推荐</c:if>
			                                    </a>
			                                    <a href="#" onClick="updateRecommed('${g.id }','1')">
		                                    		<c:if test="${g.isRecommend == 0}"><span style="color: blue">推荐</c:if>
		                                   		</a>
		                                    </shiro:hasPermission> 
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
        <!-- end DataTables Example -->
    </div>
</div>
	<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
	<script src="${basePath}js/bootstrap-paginator.min.js" type="text/javascript"></script>
	<script src="${basePath}js/select2.js" type="text/javascript"></script>
	<script src="${basePath}js/comm.js" type="text/javascript"></script>
<script type="text/javascript">
function search() {
    $("#form").submit();
}
function deleteVideo(id){
	if(confirm('确定要删除此视频吗？')){
		jQuery.ajax({
            url: 'videoDelete',
            type: "POST",
            data: "id=" + id,
            dataType: "json",
            success: function (data) {
                if (data.status == "success") {
                    alert("删除成功");
                    window.location.reload();
                }
                if (data.status == "error") {
                    alert("删除失败");
                }
            }
        });
	}
}

function updateRecommed(id, recommed){
	jQuery.ajax({
        url: 'videoRecommend',
        type: "GET",
        data: "isRecommend="+recommed+"&id=" + id,
        dataType: "json",
        success: function (data) {
            if (data.status == "success") {
                window.location.reload();
            }
            if (data.status == "error") {
                alert("修改失败");
            }
        }
    });
}
 
$(function() {
	$('#pagination').bootstrapPaginator(
			{
				currentPage : parseInt('${page}'),
				totalPages : parseInt('${pages}'),
				bootstrapMajorVersion : 3,
				alignment : "right",
				pageUrl : function(type, page, current) {
					return "videoAlbumList?keyword=${keyword}&status=${status}&isRecommend=${isRecommend}&endShowTime=<fmt:formatDate value="${endShowTime }" pattern="yyyy-MM-dd"/>&beginShowTime=<fmt:formatDate value="${beginShowTime }" pattern="yyyy-MM-dd"/>&beginCreateTime=<fmt:formatDate value="${beginCreateTime }" pattern="yyyy-MM-dd"/>&endCreateTime=<fmt:formatDate value="${endCreateTime }" pattern="yyyy-MM-dd"/>&page=" + page;
				}
			});
	
	
	 $(".datepicker").datepicker({
         format: 'yyyy-mm-dd',
         showSecond: true,
         timeFormat: "hh:mm:ss",
         dateFormat: "yy-mm-dd"
     });
     $('.select2able').select2({width: "150"});
});
</script>
</body>
</html>
