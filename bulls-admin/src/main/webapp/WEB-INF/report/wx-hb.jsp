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
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <title>微信抢红包</title>
    <style type="text/css">
        .table td, .table th {
            text-align: center;
        }

        .heading label {
            font-size: 18px;
        }
    </style>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="${basePath}css/style.css">

</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <!-- end DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        微信抢红包
                        <a class="btn btn-sm btn-primary-outline pull-right" href="${basePath}report/wx/hb/add"><i
                                class="icon-plus"></i>发红包</a>
                    </div>
                    <div class="widget-content padded clearfix">
                        <table class="table table-bordered" id="allCapitalDetail">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>金额</th>
                                <th>剩余金额</th>
                                <th>红包数量</th>
                                <th>剩余数量</th>
                                <th>创建时间</th>
                                <th>过期时间</th>
                                <th>分享红包</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${list }" var="l" varStatus="st">
                                <tr>
                                    <td>${l.id}</td>
                                    <td>${l.amount }</td>
                                    <td>${l.leftAmount }</td>
                                    <td>${l.num }</td>
                                    <td>${l.leftNum }</td>
                                    <td><fmt:formatDate value="${l.createTime}"
                                                        pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td><fmt:formatDate value="${l.overTime}"
                                                        pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td>
                                        <!-- JiaThis Button BEGIN -->
                                        <div class="jiathis_style_32x32">
                                            <a class="jiathis_button_weixin"></a>
                                            <a href="http://www.jiathis.com/share?uid=2017516"
                                               class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
                                        </div>
                                        <script type="text/javascript">
                                            var jiathis_config = {
                                                url: '${l.url}',
                                                title: "抢抢抢抢红包啦!史上最凶残,无上限红包大放送!",
                                                ralateuid: "5573695352",
                                                summary: "全民理财-活期理财专家，年化收益高达10%，亿元红包大放送，告别穷DS，迎娶白富美，出任CEO，走向人生巅峰！想想还有点小鸡冻呢！",
                                                data_track_clickback: 'true'
                                            };
                                        </script>
                                        <script type="text/javascript"
                                                src="http://v3.jiathis.com/code/jia.js?uid=1427171021540691"
                                                charset="utf-8"></script>
                                        <!-- JiaThis Button END -->
                                    </td>
                                    <td>
                                        <a href="${basePath}report/wx/hb/detail?hbId=${l.id}" class="btn btn-primary">查看详情</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>