<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
</head>
<body>
<div>
    <div class="table-responsive">
        <table class="table table-bordered" id="trade">
            <thead>
            <tr>
                <th>序号</th>
                <th>交易类型</th>
                <th>账户类型</th>
                <th>资金操作类型</th>
                <th>交易金额</th>
                <th>账户可用余额</th>
                <th>冻结余额</th>
                <th>授信资金</th>
                <th>冻结授信资金</th>
                <th>交易时间</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
    <div class="text-right">
        <ul id="pagination"></ul>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        showTradeDetail(1);
    });


    function convertNullToEmptyString(val) {
        if (val && val != 'null' && val != null && val != undefined) {
            return val;
        }
        return '';
    }

    function showTradeDetail(page) {
        var userId = convertNullToEmptyString('<%=request.getParameter("userId")%>');
        var businessId = convertNullToEmptyString('<%=request.getParameter("businessId")%>');
        var businessTableName = convertNullToEmptyString('<%=request.getParameter("tableName")%>');
        var limit = convertNullToEmptyString('<%=request.getParameter("limit")%>');

        // console.log('userId', userId)
        // console.log('businessId', businessId)
        // console.log('businessTableName', businessTableName)
        // console.log('limit', limit)

        var url = "page=" + page + "&userId=" + userId;
        if (limit != '') {
            url += "&limit=" + limit;
        }

        $.ajax({
            url: "${basePath}withdraw/trade",
            type: "get",
            dataType: "json",
            data: url,
            success: function (obj) {
                $("#trade tbody").empty();
                for (var i = 0; i < obj.list.length; i++) {
                    var trade = obj.list[i];
                    var tableName = trade.tableName, otherId = trade.otherId;

                    // console.log('-----otherId------', otherId)

                    var style = "";
                    if (businessId != '' && businessTableName != '' && tableName == businessTableName && otherId == businessId) {
                        style = "background\: \#6220204d;"
                    }
                    // console.log('-----tableName == businessTableName------', (tableName == businessTableName))
                    // console.log('-----otherId == businessId------', (otherId == businessId))
                    var tr = "<tr style='" + style + "' >" +
                        "<td>" + (i + 1) + "</td>" +
                        "<td>" + trade.aoeTypeMsg + "</td>" +
                        "<td>" + trade.accountTypeMsg + "</td>" +
                        "<td>" + trade.accountOperateTypeMsg + "</td>" +
                        "<td>" + trade.amount + "</td>" +
                        "<td>" + trade.balanceAmount + "</td>" +
                        "<td>" + trade.frozenAmount + "</td>" +
                        "<td>" + trade.creditAmount + "</td>" +
                        "<td>" + trade.frozenCreditAmount + "</td>" +
                        // "<td>" + new Date(trade.createDate).format('yyyy-MM-dd hh:mm:ss') + "</td>" +
                        "<td>" + trade.createDate + "</td>" +
                        "</tr>";
                    $("#trade tbody").append(tr);
                }
                if (obj.pages > 0) {
                    $('#pagination').bootstrapPaginator({
                        currentPage: parseInt(obj.page),
                        totalPages: parseInt(obj.pages),
                        bootstrapMajorVersion: 3,
                        alignment: "right",
                        pageUrl: function (type, page, current, limit) {
                            return "javascript:showTradeDetail(" + page + ")";
                        }
                    });
                }
            }
        });
    }
</script>
</body>
</html>
