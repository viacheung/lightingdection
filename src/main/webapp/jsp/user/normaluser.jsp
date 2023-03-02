<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>主界面</title>
    <link rel="stylesheet" href="../static/layui/css/layui.css">
    <link rel="stylesheet" href="../static/layui/css/modules/layui-icon-extend/iconfont.css">
    <script src="../static/easyui/jquery.min.js"></script>
    <script src="../static/layui/layui.js"></script>
    <script src="../static/layui/layui.all.js"></script>
    <link rel="stylesheet" href="../static/layui/css/layui.css">
    <link href="../static/Ui/css/default.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="../static/Ui/adminjs/themes/gray/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="../static/Ui/adminjs/themes/icon.css"/>
    <link rel="stylesheet" type="text/css" href="../static/Ui/css/test.css"/>
    <script type="text/javascript" src="../static/Ui/adminjs/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="../static/Ui/adminjs/jquery.easyui.1.2.6.min.js"></script>
    <style type="text/css">
        * {

            font-family: Microsoft YaHei;
            font-style: normal;

        }

        #header {

            width: 100%;
            height: 25px;
            text-align: center;
            background-color: #178ce3;

        }

        html, body {
            background: #f8f8fa;
            width: 100%;
            height: 100%;
        }

        #footer {
            width: 100%;
            height: 45px;
            background-color: white;
            text-align: center;
            display: table;
            vertical-align: middle;
            float: bottom;
            box-shadow: inset 0 0 10px #CCC;

        }

        .aaa {

            display: block;
            position: relative;
        }

        .bbb {
            font-size: 12px;
            position: absolute;
            top: 5px;
            right: 3px;
            min-width: 8px;
            height: 30px;
            width: 20px;
            line-height: 30px;
            margin-top: 14px;
            padding: 0 6px;
            font-weight: normal;
            color: white;
            text-align: center;
            text-shadow: 0 1px rgba(0, 0, 0, 0.2);
            background: #e23442;
            border: 1px solid #911f28;
            border-radius: 18px;
            background-image: -webkit-linear-gradient(top, #e8616c, #dd202f);
            background-image: -moz-linear-gradient(top, #e8616c, #dd202f);
            background-image: -o-linear-gradient(top, #e8616c, #dd202f);
            background-image: linear-gradient(to bottom, #e8616c, #dd202f);
            -webkit-box-shadow: inset 0 0 1px 1px rgba(255, 255, 255, 0.1), 0 1px rgba(0, 0, 0, 0.12);
            box-shadow: inset 0 0 1px 1px rgba(255, 255, 255, 0.1), 0 1px rgba(0, 0, 0, 0.12);
        }

        .ccc {
            background: #e8616c;
            border: 1px solid #dd202f;
            background-image: -webkit-linear-gradient(top, #e8616c, #dd202f);
            background-image: -moz-linear-gradient(top, #e8616c, #dd202f);
            background-image: -o-linear-gradient(top, #e8616c, #dd202f);
            background-image: linear-gradient(to bottom, #e8616c, #dd202f);
        }

        #ulhov li a {
            color: black;
        }

        #ulhov li a:hover {
            color: white !important
        }

        #footer sapn {
            font-size: 15px;
            font-weight: bold;
        }

        #fuzhu {
            width: 100%;
            background-color: #178ce3;
            height: 30px;
        }
        /*table{*/
        /*    border: 1px solid black;*/
        /*    border-collapse: collapse;*/
        /*}*/
        /*td,th{*/
        /*    border: 1px solid black;*/
        /*}*/

        td {
            width: 80px;
            height: 60px;
            text-align: center;
            line-height: 100%;
            border: 1px solid rgb(116, 116, 116);
        }

        table {
            border: 0px solid black;
            color: black;
            text-align: center;
            border-collapse: collapse;
            background: white;
            border: 0px solid #D2E5C4;
            width: 96%;
            margin-left: 2%;
            margin-right: 2%;
            box-shadow: inset 0 0 10px #27292b;
        }

        table th {
            width: 80px;
            height: 60px;
            border: none;
            color: white;
            background-color: #178ce3;
            border-bottom: 0px solid #73c7f0;
        }

        table tr:last-child td {
            border-bottom: none;
        }

        table tr:nth-child(2) td {
            border-top: none;
        }

        table tr td:first-child {
            border-left: none;
        }

        table tr td:last-child {
            border-right: none;
        }

    </style>
</head>
<body style="background-color:white">

<div id="airDevise" display:none>
    <table width='100%' style="display: none">
        <tr>
            <th style='width:12%;height:20px'>站点id</th>
            <th style='width:28%;height:20px'>最后通讯时间</th>
            <th style='width:12%;height:20px'>最大值</th>
            <th style='width:12%;height:20px'>平均值</th>
            <th style='width:12%;height:20px'>预警等级</th>
            <th style='width:12%;height:20px'>闪电次数</th>
            <th style='width:12%;height:20px'>在选状态</th>
        </tr>
    </table>
</div>

<div id="fuzhu"></div>
<div id="header" style="display:table;">
    <span id="title_name" style="display:table-cell;vertical-align:middle;font-size:50px;color:white">${name}</span>
</div>
<div id="nav" style="background:#178ce3">
    <ul class="lfet">
        <li><a href="javascript:;" style="color:white">信息查看与搜索</a>
            <ul id="ulhov">
                <li><a href="<%=basePath%>user/searchByBaojingRecord" target="aa" style="color:black">报警信息查询</a></li>
                <li><a href="<%=basePath%>user/searchByTroubleRecord" target="aa" style="color:black">故障信息查询</a></li>
                <li><a href="<%=basePath%>user/searchByInfoRecord" target="aa" style="color:black">重要记录信息查询</a></li>
                <!-- <li><a href="<%=basePath%>user/searchByResistance" target="aa" style="color:black">电阻计算信息查询</a></li> -->

            </ul>
        </li>
        <li><a href="<%=basePath%>user/OnlineMonitor" style="color:white" target="aa">在线监测</a>
        </li>
        <li><a href="javascript:;" id="airDeviseButton" onclick=airDevise() style="color:white">大气电场仪</a></li>
        <li><a href="javascript:;" style="color:white">帮助</a>
            <ul id="help"></ul>
        </li>
        <li><a href="" style="color:white">返回主界面</a></li>
    </ul>

    <ul class="right">
        <li><a id="error" class="aaa" href="#" style="color:white"><img src="../static/images/alarm.png" width="20"
                                                                        height="20"/>
            <div id="num" class="bbb ccc" style="display:none"></div>
            <div id="sound"></div>
        </a></li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <li><a href="#" id="editpass" style="color:white">修改密码</a></li>
        <li><a href="<%=basePath%>user/login_out" style="color:white">退出</a></li>
    </ul>

</div>


<!--修改密码窗口-->
<div id="w" class="easyui-dialog">
    <div class="easyui-layout" fit="true">
        <div region="center" border="false" style="padding: 10px; background: #fff;">
            <table cellpadding=3>
                <tr>
                    <td>旧密码：</td>
                    <td><input id="txtOldPass" type="password" class="txt01"/></td>
                </tr>
                <tr>
                    <td>新密码：</td>
                    <td><input id="txtNewPass" type="password" class="txt01"/></td>
                </tr>
                <tr>
                    <td>确认密码：</td>
                    <td><input id="txtRePass" type="password" class="txt01"/></td>
                </tr>
            </table>
        </div>
    </div>
</div>

<div style="width: 100%;height:80%;">
    <!-- 内容 主体区域 -->
    <iframe src="<%=basePath%>user/showInfomation" frameborder="0" scrolling="auto" style="width: 100% ;height:100%"
            id="aa" name="aa">
    </iframe>
</div>

<div id="footer">
    <p id="foot_info" style="border:none;
    	width:100%;
	font-size:15px;
	display:table-cell;
	vertical-align:middle;
	color:black;">
        <span style='font-size:15px;font-weight:bold;'>公司名称:${companyname}</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
        &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
        <span style='font-size:15px;font-weight:bold;'>地址:${address}</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
        &nbsp; &nbsp; &nbsp; &nbsp;
        <span style='font-size:15px;font-weight:bold;'>网址:${internet}</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
        &nbsp; &nbsp; &nbsp; &nbsp;
        <span style='font-size:15px;font-weight:bold;'>服务电话:${phone}</span>
    </p>
</div>
<div id="ad" style="width:100%;height:100%;">
</div>
<script type="text/javascript">
    //JavaScript代码区域
    //JavaScript代码区域
    layui.use(['layer', 'table', 'element', 'laypage', 'jquery'], function () {
        var element = layui.element;
        var layer = layui.layer; //弹层
        var table = layui.table; //表格
        var laypage = layui.laypage; //分页
        var $ = layui.jquery;

    });

    // 弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
    function msgShow(title, msgString, msgType) {
        $.messager.alert(title, msgString, msgType);
    }
    var count = 1;

    function airDevise(){
        $.ajax({
            url: "<%=basePath%>user/getAirDeviseDataByPost",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            dataType: "json",
            success: function (data) {
                var airDeviseData = data
                var airDeviseLayer = layer.open({
                    type:1,
                    title:"大气电场仪",
                    area:['800px','400px'],
                    shadeClose: true,
                    content:$("#airDevise"),
                    success:function (index,layero){

                        var table = document.getElementsByTagName('table')[0];
                        table.style.display=''
                        for (var i = 0; i <airDeviseData.length; i++)
                        {
                            var tr = table.insertRow(table.rows.length);
                            var obj = airDeviseData[i];
                            for (var p in obj)
                            {
                                var td = tr.insertCell(tr.cells.length);
                                td.align='center'
                                if (obj[p] == true && obj[p] != 0)
                                {
                                    td.innerText = "在选"
                                }
                                else if (obj[p] == false && obj[p] != 0)
                                {
                                    td.innerText = "未选"
                                }
                                else{
                                    td.innerText = obj[p];

                                }
                            }
                        }
                    },
                    cancel:function(index,layero){
                        var table = document.getElementsByTagName('table')[0];
                        table.style.display='none'
                        $("tr:not(:first)").remove();
                        layer.close(index);
                        return false;
                    },
                    end:function(index,layero){
                        var table = document.getElementsByTagName('table')[0];
                        table.style.display='none'
                        $("tr:not(:first)").remove();
                        layer.close(index);
                        return false;
                    },
                })
            }
        })
    }

    // 设置登录窗口
    function openPwd() {
        $('#w').dialog({
            title: '修改密码',
            width: 800,
            height: 300,
            modal: true,
            closed: true,
            buttons: [{
                text: '保存',
                iconCls: 'icon-save',
                handler: function () {
                    // 提交保存
                    var oldPwd = $('#txtOldPass').val();
                    var newPwd = $('#txtNewPass').val();
                    var rePwd = $('#txtRePass').val();

                    if (oldPwd === '') {
                        $.messager.alert('提示', '原密码不能为空', 'info');
                        return;
                    }

                    if (newPwd === '') {
                        $.messager.alert('提示', '新密码不能为空', 'info');
                        return;
                    }

                    if (rePwd !== newPwd) {
                        $.messager.alert('提示', '确认密码不一致', 'info');
                        return;
                    }
                    var msg = "";
                    msg = admin.updatepwd(newPwd);
                    $('#w').dialog('close');
                    // 清空内容
                    $('#txtOldPass').val('');
                    $('#txtNewPass').val('');
                    $('#txtRePass').val('');
                    $.messager.confirm("提示", msg, function (yes) {
                        // 刷新表格数据
                        location.reload();
                    })

                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {

                }
            }]
        });
    }

    // 关闭登录窗口
    function closePwd() {
        $('#w').window('close');
    }

    // 修改密码
    function serverLogin() {
        var $newpass = $('#txtNewPass');
        var $rePass = $('#txtRePass');

        if ($newpass.val() === '') {
            msgShow('系统提示', '请输入密码！', 'warning');
            return false;
        }
        if ($rePass.val() === '') {
            msgShow('系统提示', '请在一次输入密码！', 'warning');
            return false;
        }

        if ($newpass.val() !== $rePass.val()) {
            msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
            return false;
        }

        msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + msg, 'info');
        $newpass.val('');
        $rePass.val('');
        close();

    }

    $(function () {
        openPwd();
        $('#editpass').click(function () {
            $('#w').window('open');
        });
        $('#btnEp').click(function () {
            serverLogin();
        });
        $('#btnCancel').click(function () {
            closePwd();
        });
        $('#error').click(function () {
            //var chanpins=normal.getAllErrorChanpin();
            var chanpins;
            $.ajax({
                url: "<%=basePath%>user/getAllErrorChanpin",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {},
                dataType: "json",
                success: function (data) {
                    chanpins = data;
                }

            });
            if (chanpins.length > 0) {
                var result = [];
                for (var i = 0; i < chanpins.length; i++) {
                    result.push(chanpins[i].id);
                }
                document.getElementById("aa").contentDocument.getElementById("pageId3").name = result.join("-");
                document.getElementById("aa").contentDocument.getElementById("aa2").src = '../user/showDetail3';
                // document.getElementById("sound").innerHTML='<audio src="${pageContext.request.contextPath}/user/playerSound"   autoplay="autoplay" >';
            } else {
                $.messager.alert("消息提醒", "当前无故障设备！", "warning");
            }

        });
    });


    function change() {
        //$.messager.alert("提示","执行一次", 'warning');
        var chanpins;
        $.ajax({
            url: "<%=basePath%>user/getAllErrorChanpin",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {},
            dataType: "json",
            success: function (data) {
                chanpins = data;
            }

        });
        if (chanpins != null) {
            var size = chanpins.length;
            if (size > 0) {
                document.getElementById("num").style.display = '';
                document.getElementById("num").innerHTML = size;
                document.getElementById("sound").innerHTML = '<audio src="${pageContext.request.contextPath}/user/playerSound"   autoplay="autoplay" >';


            } else {
                document.getElementById("num").style.display = 'none';

            }
        } else {
            document.getElementById("num").style.display = 'none';

        }


    }


    function init() {
        setInterval(change, 1000);
        // $.messager.alert("消息提醒",document.getElementById("aa").contentDocument.getElementById("aa2"), "warning");
        // document.getElementById("username").innerHTML="<img src='../photo/user.jpg' class='layui-nav-img'>"+
        //normal.getUser().getUser_name();
        var htmllist = '<li><a href="instruction.html" target="aa">产品说明书</a></li>';
        var htmllist = '';
        var list;
        $.ajax({
            url: "<%=basePath%>user/appManages",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {},
            dataType: "json",
            success: function (data) {
                list = data;
            }

        });
        for (var i = 0; i < list.length; i++) {
            htmllist += "<li><a href='${pageContext.request.contextPath}/user/openFile?path=" + list[i].url + "'>" + list[i].type + "</a></li>";
        }

        document.getElementById("help").innerHTML = htmllist;
        // document.getElementById("title_name").innerHTML=a;
        //document.getElementById("aa").contentWindow.init();
    }

    //菜单循环遍历打印拼接操作

    function openFile(url) {
        $.messager.alert("提示", "执行", 'warning');
        var a;
        $.ajax({
            url: "<%=basePath%>user/openFile",
            type: "post",
            async: false,
            data: {url},
            dataType: "json",
            success: function (data) {
                a = data;
            }

        });
        $.messager.alert("提示", a, 'warning');
    }

    init();

</script>
</body>
</html>