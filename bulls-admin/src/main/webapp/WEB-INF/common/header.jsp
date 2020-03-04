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
    <link href="${basePath}css/bootstrap.min.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/style.css" media="all" rel="stylesheet" type="text/css"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
</head>
<body>
<div class="navbar navbar-fixed-top scroll-hide">
    <div class="container-fluid top-bar">
        <div class="pull-left appLogomarginLeft" ><img src="${basePath }images/logo.png"/></div>
        <div class="pull-right">
            <ul class="nav navbar-nav pull-right">
                <c:if test="${msgCount > 0 }">
                    <li class="dropdown notifications hidden-xs">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            <span aria-hidden="true" class="se7en-flag"></span>
                            <div class="sr-only">
                                消息提醒
                            </div>
                            <p class="counter">
                                    ${msgCount}
                            </p>
                        </a>
                        <ul class="dropdown-menu">
                            <c:forEach items="${msgList}" var="msg">
                                <li>
                                    <a href="${basePath}investment/detail?investmentId=${msg.investmentId}">
                                        <div class="notifications label label-info">
                                            查看
                                        </div>
                                        <p>
                                            有1条新的加息审核需要审批
                                        </p>
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </li>
                </c:if>
                <li class="dropdown user hidden-xs">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <img width="34" height="34"
                             src="${aPath}upload/${sessionScope.user.avatar }"/>${sessionScope.user.trueName }<b
                            class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="${basePath}user/edit/password">
                                <i class="iconfont">&#xe60f;</i>修改密码</a>
                        </li>
                        <li>
                            <a href="${basePath}user/edit/avatar">
                                <i class="iconfont">&#xe601;</i>上传头像</a>
                        </li>
                        <li>
                            <a href="${basePath}logout">
                                <i class="iconfont">&#xe610;</i>登出</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
        <button class="navbar-toggle">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
    </div>
    <div class="container-fluid main-nav clearfix">
        <div class="nav-collapse">
            <ul class="nav">
                <c:if test="${sessionScope.listResource != null }">
                    <c:forEach var="r" items="${sessionScope.listResource }">
                        <c:choose>
                            <c:when test="${empty r.resources }">
                                <li id="${r.pageId }">
                                    <a href="${basePath}${r.url}"><span aria-hidden="true"
                                                                        class="iconfont">${r.icon }</span>${r.name }</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="dropdown" id="${r.pageId }">
                                    <a data-toggle="dropdown" href="#">
				                                <span aria-hidden="true" class="iconfont">
                                                        ${r.icon }
                                                </span>${r.name }<b class="caret"></b>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <c:forEach var="rr" items="${r.resources }">
                                            <li>
                                                <a href="${basePath}${rr.url}">${rr.name }</a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:if>
            </ul>
        </div>
    </div>
</div>

<script src="${basePath}js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap.min.js" type="text/javascript"></script>
<script src="${basePath}js/modernizr.custom.js" type="text/javascript"></script>
<script src="${basePath}js/respond.js" type="text/javascript"></script>
<script src="${basePath}js/common/common.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        //mobile nav
        $('.navbar-toggle').click(function () {
            return $('body, html').toggleClass("nav-open");
        });
        if (window.location.port == '') {
            currentUrl = window.location.protocol + "//" + window.location.host + ":80" + window.location.pathname;
        } else {
            currentUrl = window.location.protocol + "//" + window.location.host + window.location.pathname;
        }
        $('.nav li').each(function () {
            var $this = $(this);
            var href = $this.find("a").attr("href");
            if (href == currentUrl) {
                $this.find('a').addClass("current");
                if ($this.parent().hasClass("dropdown-menu")) {
                    $this.parent().parent().children().eq(0).addClass('current');
                }
            }
        });

        $(".navbar.scroll-hide").mouseover(function () {
            $(".navbar.scroll-hide").removeClass("closed");
            return setTimeout((function () {
                return $(".navbar.scroll-hide").css({
                    overflow: "visible"
                });
            }), 150);
        });
        var delta, lastScrollTop;
        lastScrollTop = 0;
        delta = 50;
        return $(window).scroll(function (event) {
            var st;
            st = $(this).scrollTop();
            if (Math.abs(lastScrollTop - st) <= delta) {
                return;
            }
            if (st > lastScrollTop) {
                $('.navbar.scroll-hide').addClass("closed");
            } else {
                $('.navbar.scroll-hide').removeClass("closed");
            }
            return lastScrollTop = st;
        });
    });
</script>
</body>
</html>