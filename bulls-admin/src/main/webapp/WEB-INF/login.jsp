<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>login</title>
    <link href="css/bootstrap.min.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="css/style.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="css/ladda-themeless.min.css" media="all" rel="stylesheet" type="text/css"/>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
    <style type="text/css">
        .animated {
            -webkit-animation-duration: 700ms;
            animation-duration: 700ms;
            -webkit-animation-fill-mode: both;
            animation-fill-mode: both;
        }

        @-webkit-keyframes shake {
            0%, 100% {
                -webkit-transform: translateX(0);
            }
            10%, 30%, 50%, 70%, 90% {
                -webkit-transform: translateX(-10px);
            }
            20%, 40%, 60%, 80% {
                -webkit-transform: translateX(10px);
            }
        }

        @keyframes shake {
            0%, 100% {
                transform: translateX(0);
            }
            10%, 30%, 50%, 70%, 90% {
                transform: translateX(-10px);
            }
            20%, 40%, 60%, 80% {
                transform: translateX(10px);
            }
        }

        .shake {
            -webkit-animation-name: shake;
            animation-name: shake;
        }
    </style>
</head>
<body class="login1">
<!-- Login Screen -->
<div class="login-wrapper">
    <div class="login-container" style="height:500px;top:30%">
        <a href="javascript:void(0)">
            <img src="images/logo160.png"/>
        </a>

        <form action="<%=request.getContextPath()%>/user/login" id="form" method="post">
            <div class="form-group">
                <input class="form-control" placeholder="用户名" type="text" value="" id="username">
            </div>
            <div class="form-group">
                <input class="form-control" placeholder="密码" type="password" value="" id="password">
            </div>
            <div class="mg-top form-group" id="code-wrapper" <c:if test="${(sessionScope.count <3 || sessionScope.count == null)}">style="display:none"</c:if> >
                <input type="text" maxlength="4" placeholder="验证码" id="verify" name="verify" class="form-control vcode-input"/>
                <img src="${basePath }getCaptImg.do" id="vcode" class="vcode" width="120px" height="45px">
                <a href="javascript:void(0)" class="vcode refresh">看不清换一张</a>
            </div>
            <div class="form-options clearfix">
                <div class="text-left">
                    <label class="checkbox">
                        <input type="checkbox" id="remember">
                        <span>记住我</span>
                    </label>
                </div>
            </div>
            <div class="social-login clearfix">
                <a class="btn btn-primary ladda-button login" data-style="expand-right" href="javascript:void(0)">
                    <span class="ladda-label">登入</span>
                </a>
            </div>
        </form>
    </div>
</div>

<!-- End Login Screen -->

<script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<script src="js/modernizr.custom.js" type="text/javascript"></script>
<script src="js/respond.js" type="text/javascript"></script>
<script src="js/spin.min.js" type="text/javascript"></script>
<script src="js/ladda.min.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $(".login-container").addClass("active");

        $("#username").focus();

        $(".login").click(function () {
            login();
        });

        $("#form").keydown(function (e) {
            e = e || window.event;
            if (e.keyCode == 13) {
                return false;
            }
        });

        $("#password").keyup(function (e) {
            e = e || window.event;
            if (e.keyCode == 13) {
                login();
            }
        });
    });

    function login() {
        var remember = $("#remember").prop("checked");
        var user = {
            "username": $.trim($("#username").val()),
            "password": $.trim($("#password").val())
        };
        var l = Ladda.create($(".login").get(0));
        $.ajax({
            url: "login",
            type: "post",
            beforeSend: function () {
                l.start();
            },
            data: {"user": JSON.stringify(user), "remember": remember, "verify": $("#verify").val()},
            dataType: "json",
            success: function (data) {
                if (data.status) {
                    window.location.href = "index";
                } else {
                	shake();
                	alert(data.msg);
                	/* if(!data.leftCount){
                		alert(data.msg);
                	}else{
                		if(data.leftCount >= 5){
                			alert("您已输错5次, 账号已被锁定");
                		}else{
                			alert(data.msg + "(您已输错"  + (5 - data.leftCount) +  "次, 输错5次账号将被锁定)");
                		}

                	}

                	if(5 - data.leftCount >= 3){
                		$("#code-wrapper").show();
                	}
                	freshVcode() */
                }
            },
            complete: function () {
                l.stop();
            }
        });
    }

    function shake() {
        $(".login-container").addClass("animated shake");
        setTimeout('$(".login-container").removeClass("animated shake");', '1000');
    }

    function freshVcode() {
        $("#vcode").attr("src", "${basePath }getCaptImg.do?v=" + Math.random());
    }

    $(".vcode").click(freshVcode);
</script>
</body>
</html>
