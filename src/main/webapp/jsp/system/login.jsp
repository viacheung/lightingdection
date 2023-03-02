<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="shortcut icon" href="favicon.ico"/>
    <link rel="bookmark" href="favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="../static/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../static/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../static/Ui/css/index.css">
    <link rel="stylesheet" href="../static/layui/css/layui.css">
    <link rel="stylesheet" href="../static/layui/css/modules/layui-icon-extend/iconfont.css">
    <script type="text/javascript" src="../static/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../static/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../static/layui/layui.js"></script>
    <script type="text/javascript" src="../static/layui/layui.all.js"></script>
    <title>防雷、防静电、防漏电在线监测系统</title>
    <meta name="keywords" content="防雷、防静电、防漏电在线监测系统">
</head>
<body>
<script>
    $(document).ready(function () {
        var whei = $(window).width()
        $("html").css({fontSize: whei / 24});
        $(window).resize(function () {
            var whei = $(window).width();
            $("html").css({fontSize: whei / 24})
        });
    });
</script>
<div class="main">
    <div class="header">
        <div class="header-center fl">
            <div class="header-title">
                登录界面
            </div>
            <div class="header-img"></div>
        </div>
        <div class="header-bottom fl"></div>
    </div>
    <div class="content">
        <div class="content-left">
        </div>
        <div class="content-right">
            <div class="right-infp">
                <form id="form" class="form form-horizontal" method="post">
                    <div class="right-infp-name">

                        <input type="text" id="username" name="username" placeholder="请输入用户名" maxlength="12"
                               required="true" value="" autocomplete="off">
                    </div>
                    <div class="right-infp-name">
                        <input type="text" id="password" name="password" placeholder="请输入密码" required="true"
                               autocomplete="off">
                    </div>
                    <div>
                        &emsp; &emsp; <label><input id="radio1" type="radio" name="type" value="1"/>系统管理员&emsp; &emsp;
                        &emsp;&emsp; &emsp; </label>
                        <label><input type="radio" id="radio2" name="type" value="2"/>系统用户</label>
                        <br><br>
                    </div>
                </form>
                <div class="right-infp-btn">
                    <button id="submitBtn" class="btn">登录</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    //登录
    $("#submitBtn").click(function () {
        var data = $("#form").serialize();
        var uname = document.getElementById("username").value.replace(/\s*/g, "");
        var psw = document.getElementById("password").value.replace(/\s*/g, "");
        var check = $("input[name='type']:checked").val();
        if (uname == null || uname == undefined || uname == "" || psw == null || psw == undefined || psw == "") {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert("<span style='color:#000000'>" + "用户名或密码不能为空！" + "</span>", {
                    icon: 5,
                    title: "提示"
                });
            });
        } else if (check == null || check == undefined) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert("<span style='color:#000000'>" + "用户类型不能为空！" + "</span>", {
                    icon: 5,
                    title: "提示"
                });
            });
        } else {
            $.ajax({
                type: "post",
                url: "login",
                data: data,
                dataType: "json", //返回数据类型
                success: function (data) {
                    if (("success" == data.type) && ("1" == data.catelog)) {
                        //$.messager.alert("消息提醒",data.catelog, "warning");
                        window.location.href = "../admin/adminMenu";
                    } else if (("success" == data.type) && ("2" == data.catelog)) {
                        window.parent.location.href = "../user/normaluser";
                    } else {

                        layui.use('layer', function () {
                            var layer = layui.layer;
                            layer.alert("<span style='color:#000000'>" + data.msg + "</span>", {
                                icon: 5,
                                title: "提示"
                            });
                        });
                    }
                }

            });
        }
    });
</script>
</body>
</html>