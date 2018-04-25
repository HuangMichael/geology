<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="/">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>河北水勘院地质档案管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1, user-scalable=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- STYLESHEETS --><!--[if lt IE 9]>
    <!--<script src="js/flot/excanvas.min.js"></script>-->
    <script src="/js/html5.js"></script>
    <script src="/js/css3-mediaqueries.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="css/cloud-admin.css">
    <link rel="stylesheet" type="text/css" href="css/bootstrap-responsive.css">
    <link rel="stylesheet" type="text/css" href="font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="js/uniform/css/uniform.default.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/animatecss/animate.min.css"/>
</head>
<body class="login">
<!-- PAGE -->
<section id="page">
    <section id="login" class="visible">
        <div class="container">
            <h2 class="bigintro"><i><img src="img/logo.jpg" alt=""></i>河北水勘院地质档案管理系统</h2>
            <h5>HEBEI SKY GEOLOGY ARCHIVES MANAGEMENT SYSTEM</h5>
            <div class="row">
                <div class="col-md-4 col-md-offset-3">
                    <div class="login-box-plain">
                        <div class="left">
                            <img src="img/2.png" alt="">
                        </div>
                        <div class="right">
                            <form role="form">
                                <%--<h4>Welcome!</h4>--%>
                                <p>欢迎登录</p>
                                <div class="form-group">
                                    <i class="fa fa-user"></i>
                                    <span style="display:block;width:200px;height:30px"></span>
                                    <input type="text" class="form-control" id="userName" placeholder="请输入用户名">
                                </div>
                                <div class="form-group">
                                    <i class="fa fa-lock"></i>
                                    <span style="display:block;height:30px"></span>
                                    <input type="password" class="form-control" id="password" placeholder="请输入密码">
                                </div>
                                <div class="form-group">
                                    <input type="checkbox" class="uniform" value="" checked="checked"/>
                                    <label>记住密码</label>
                                </div>
                                <div class="form-actions">
                                    <button type="button" class="btn btn-danger" onclick="login()">
                                        登&nbsp;&nbsp;&nbsp;录
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </div>
    </section>
    <!--/LOGIN -->
</section>
<!--/PAGE -->
<!-- JAVASCRIPTS -->
<!-- Placed at the end of the document so the pages load faster -->
<!-- JQUERY -->
<script src="js/jquery/jquery-2.0.3.min.js"></script>
<!-- JQUERY UI-->
<script src="js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
<!-- BOOTSTRAP -->
<script src="bootstrap-dist/js/bootstrap.min.js"></script>
<!-- UNIFORM -->
<script type="text/javascript" src="js/uniform/jquery.uniform.min.js"></script>
<script type="text/javascript" src="js/backstretch/jquery.backstretch.min.js"></script>
<script type="text/javascript" src="/js/jQuery-Cookie/jquery.cookie.min.js"></script>
<script type="text/javascript" src="/js/base64.js"></script>
<!-- CUSTOM SCRIPT -->
<script src="js/script.js"></script>

<script>
    //输入框非空验证
    function check() {
        $('#userName').blur(function () {

            if ($(this).val() == "") {
                $("#userName").prev().html("<font color=\"red\" size=\"2\">用户名不能为空</font>");
            } else {
                $("#userName").prev().html("");
            }
        })

        $('#password').blur(function () {
            if ($(this).val() == "") {
                $("#password").prev().html("<font color=\"red\" size=\"2\">密码不能为空</font>");
            } else {
                $("#password").prev().html("");
            }
        })

    }

    check();


    /**实现功能，保存用户的登录信息到cookie中。当登录页面被打开时，就查询cookie**/
    $(function () {
        var userNameValue = getCookieValue("userName");
        document.getElementById("userName").value = userNameValue;
        var userPassValue = getCookieValue("password");
        document.getElementById("password").value = userPassValue;
    });

    //点击登录按钮
    function login() {

        //获取用户名和密码
        var userName = $('#userName').val();
        var password = $('#password').val();
        var objChk = $(".uniform");

        var user = {
            userName: userName,
            password: password
        };

        var url = "${basePath}/login";

        if (objChk.is(':checked')) {
            //添加cookie
            addCookie("userName", userName, 7, "/");
            addCookie("password", password, 7, "/");
            console.log("记住了你的密码登录。");
        } else {
            console.log("不记密码登录。");
        }
        $.post(url, user, function (data) {
            if (data.result) {
                $("#userName").prev().html("<font color=\"green\" size=\"2\">用户登录成功</font>");
                $("#password").prev().html("");
                $(".btn-danger").html("登录中...");
                setTimeout(function () {
                    window.location.href = '/main/index';
                }, 1000);

                localStorage.setItem("userName", userName);
            } else {
                $("#password").prev().html(data["resultDesc"]);
                $("#userName").prev().html("");
            }

        });
    }

    function addCookie(name, value, days, path) {
        /**添加设置cookie**/
        var b = new Base64();
        var name = b.encode(name);
        var value = b.encode(value);
        var expires = new Date();
        expires.setTime(expires.getTime() + days * 3600000 * 24);
        //path=/，表示cookie能在整个网站下使用，path=/temp，表示cookie只能在temp目录下使用
        path = path == "" ? "" : ";path=" + path;
        //GMT(Greenwich Mean Time)是格林尼治平时，现在的标准时间，协调世界时是UTC
        //参数days只能是数字型
        var _expires = (typeof days) == "string" ? "" : ";expires=" + expires.toUTCString();
        document.cookie = name + "=" + value + _expires + path;
    }

    function getCookieValue(name) {
        /**获取cookie的值，根据cookie的键获取值**/
            //用处理字符串的方式查找到key对应value
        var b = new Base64();
        var name = b.encode(name);
        //读cookie属性，这将返回文档的所有cookie
        var allcookies = document.cookie;
        //查找名为name的cookie的开始位置
        name += "=";
        var pos = allcookies.indexOf(name);
        //如果找到了具有该名字的cookie，那么提取并使用它的值
        if (pos != -1) {                                             //如果pos值为-1则说明搜索"version="失败
            var start = pos + name.length;                  //cookie值开始的位置
            var end = allcookies.indexOf(";", start);        //从cookie值开始的位置起搜索第一个";"的位置,即cookie值结尾的位置
            if (end == -1) end = allcookies.length;        //如果end值为-1说明cookie列表里只有一个cookie
            var value = allcookies.substring(start, end); //提取cookie的值
            var b = new Base64();
            return (b.decode(value));                        //对它解码
        } else {  //搜索失败，返回空字符串
            return "";
        }
    }

    function deleteCookie(name, path) {
        /**根据cookie的键，删除cookie，其实就是设置其失效**/
        var b = new Base64();
        var name = b.encode(name);
        var expires = new Date(0);
        path = path == "" ? "" : ";path=" + path;
        document.cookie = name + "=" + ";expires=" + expires.toUTCString() + path;
    }

    document.onkeydown = function (event) {
        var e = event || window.event || arguments.callee.caller.arguments[0];

        if (e && e.keyCode == 13) {
            login()
        }
    };


</script>
<!-- /JAVASCRIPTS -->
</body>
</html>