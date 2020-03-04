<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
-------------提现----------------------
<form action="" method="post" id="form">
    token:<input type="text" name="token" value="8d732652d6ec8423bb62996c785c2d235eb0c90209c28f3849ccf1f2db336d44">
    金额：<input type="text" name="amount" value="1">
    版本号：<input type="text" name="appVersion" value="3.1.0">
    通道：<input type="text" name="tunnel" value="1">
    <input type="button" value="提现" id="w">
</form>

-------------充值----------------------
<form action="" method="post" id="form2">
    token:<input type="text" name="token" value="34755f4a6e284b47503a1b6ecaa57f7967a7be90d44aa3e5237e610c812a52b3">
    金额：<input type="text" name="amount" value="100000">
    版本号：<input type="text" name="appVersion" value="3.1.0">
    通道：<input type="text" name="source" value="1">
    <input type="button" value="充值" id="r">
</form>
<script src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
    $(function () {
        $('#w').click(function () {
            $.ajax({
                url: 'http://localhost/assets/withdrawNew',
                data: $('#form').serialize(),
                type: 'post',
                dataType: 'json',
                success: function (data) {
                    if (data.code == '1') {
                        var html = data.data;
                        document.write(html);
                    } else {
                        alert(data.msg);
                    }

                }
            });
        });

        $('#r').click(function () {
            $.ajax({
                url: 'http://localhost/assets/rechargeNew',
                data: $('#form2').serialize(),
                type: 'post',
                dataType: 'json',
                success: function (data) {
                    if (data.code == '1') {
                        var html = data.data;
                        document.write(html);
                    } else {
                        alert(data.msg);
                    }

                }
            });
        });
    });
</script>
</body>
</html>