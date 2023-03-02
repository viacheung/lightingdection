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
    <title>雷电防护实时在线监测系统</title>

    <link rel="stylesheet" href="../static/layui/css/layui.css">
    <link rel="stylesheet" href="../static/layui/css/modules/layui-icon-extend/iconfont.css">
    <link rel="stylesheet" type="text/css" href="../static/Ui/ui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../static/Ui/ui/themes/icon.css">
    <script type="text/javascript" src="../static/Ui/ui/jquery.min.js"></script>
    <script type="text/javascript" src="../static/Ui/ui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../static/Ui/ui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../static/Ui/ui/jquery.serializejson.min.js"></script>
    <script src="../static/layui/layui.js"></script>
    <script src="../static/layui/layui.all.js"></script>
    <style>
        * {
            font-family: Microsoft YaHei;
            font-style: normal;
        }

        html,
        body {
            width: 100%;
            height: 100%;
            display: inline-block;
        }

        table {
            background-color: #D2E5C4;
            width: 100%;
            border: none;
            text-align: center;
            border-collapse: collapse;
        }

        table th {
            border: none;
            background-color: #73c7f0;
            border-bottom: 0px solid #73c7f0;
            border-top: 5px solid #73c7f0;
        }

        table td {
            background-color: #fff;
            padding: 0px;
            border-bottom: 5px solid #D2E5C4;
            border-top: 5px solid #D2E5C4;
            border-left: 0;
            border-right: 0;
        }

        .btn {
            float: right;
        }

        button {
            background-color: white;
            /* Green */
            border: none;
            color: black;
            padding: 8px 12px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            margin: 4px 2px;
            -webkit-transition-duration: 0.4s;
            /* Safari */
            transition-duration: 0.4s;
            cursor: pointer;
            border-radius: 8px;
        }

        button :hover {
            background-color: #0091EA;
            color: white;
            border: 0px;
        }

        .btn {
            margin-top: 30px;
            margin-left: 220px;
        }

        .button1 {
            background-color: white;
            color: black;
            border: 2px solid gray;
        }

        .button1:hover {
            background-color: #0091EA;
            color: white;
        }

        .status1 {
            background: #6ae115;
        }

        .status2 {
            background: #fb0202;
        }

        .status3 {
            background: #ffc600;
        }

        checkbox {
            width: 100%;
            height: 100%;
        }

        checkbox:checked {
            width: 100%;
            height: 100%;
        }

        .left {
            position: absolute;
            background: #cceeff;
            width: 0;
            height: 100%;
            display: none;
        }

        .left_true {
            background: #cceeff;
            height: 100%;
            width: 20%;
            float: left;
        }

        .center {
            height: 100%;
            width: 1%;
            float: left;
            background-color: rgba(241, 237, 235, 0.911);
            display: flex;
        }

        .right {
            height: 100%;
            width: 99%;
            float: left;
        }

        .right_true {
            height: 100%;
            width: 79%;
            float: left;
        }

        i {
            margin-right: -20px !important;
            align-self: center;
        }
    </style>


</head>

<body>
<div id='left' class='left'>
    <table bordercolor='black' border='3px' width="100%">
        <thead>
        <tr height='50px'>
            <th width='25%'><input width=100% height=100% id="btnAll" type="checkbox"/>&nbsp;&nbsp;</th>

            <th width='45%' style='font-size:18px; font-weight:bold;text-align:left;'>&nbsp;&nbsp;&nbsp;区域</th>
            <th style='width:30%;font-weight:bold;font-size:18px;'>状态</th>
        </tr>
        </thead>
        <tbody id="localTable">

        </tbody>
    </table>

    <div class="btn">
        <button id="btnSure" type="button" class="button1">确定</button>
        <button id="btnCancel" type="button" class="button1">取消</button>
    </div>

</div>
<div class="center" id='center'>
    <i id="sidebar" class="layui-icon">&#xe65b;</i>
</div>

<iframe frameborder="0" scrolling="auto" id="aa2" class='right' name="aa2"></iframe>

<div id="pageId1" style="display:none">

</div>
<div id="pageId2" style="display:none">

</div>
<div id="pageId3" style="display:none">

</div>
<div id="pageId4" style="display:none">

</div>

<script type="text/javascript">
    var l = document.getElementsByClassName('left')[0];
    var r = document.getElementsByClassName('right')[0];
    var side = document.getElementById('center');
    var flag = true;
    side.onclick = function () {

        if (flag) {
            l.className = "left_true";
            r.className = "right_true";
            side.innerHTML = '<i id="sidebar" class="layui-icon">&#xe65a;</i> '
            flag = false;
        } else {
            l.className = "left";
            r.className = "right";
            flag = true;
            side.innerHTML = '<i id="sidebar" class="layui-icon">&#xe65b;</i> '
        }
    };
    var boolArr = [];
    var idArr = [];

    function TimerInit() {
        var parent;

        $.ajax({
            url: "<%=basePath%>user/parentList",
            type: "post",
            async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {},
            dataType: "json",
            success: function (data) {
                parent = data;
            }

        });
        //  $.messager.alert("提示",parent, 'error');
        var htmllist = "";
        var zt = "";
        var yanse = "";
        for (var i = 0; i < parent.length; i++) {
            if (parent[i].zhuangtai == 0) {
                zt = "正常";
            } else if (parent[i].zhuangtai == 1) {
                zt = "异常";
            } else {
                zt = "报警";
            }
            htmllist += "<tr id='tr" + parent[i].id + "' height='50px'>" +
                "<td><input type='checkbox'   name='duoxuanBox' id='duoxuanBox" + i + "' value='" + i + "' />&nbsp;&nbsp;</td>" +
                "<td  style='text-align:left;font-size:18px ' id='" + parent[i].id + "'>" + "&nbsp;&nbsp;&nbsp;" + parent[i].location + "</td>" +
                "<td  style='color:white;font-size:18px' id='tr" + parent[i].id + "zt' class='";
            if (parent[i].zhuangtai == 0) {
                htmllist += "status1'>" + zt;
            } else if (parent[i].zhuangtai == 1) {
                htmllist += "status2'>" + zt;
            } else {
                htmllist += "status3'>" + zt;
            }
            htmllist += "</td></tr>";
            //"<i class='iconfont layui-extend-zhengchang' style='margin-right:10px;color:"+yanse+";font-size:20px'></i>"+zt+";
            boolArr.push(false);
            idArr.push(parent[i].id);
        }
        ;


        document.getElementById("localTable").innerHTML = htmllist;
        for (var i = 0; i < parent.length; i++) {
            var arg = [], brg = [];
            arg[i] = document.getElementById(parent[i].id);
            arg[i].index = i;
            arg[i].addEventListener('click', function () {
                brg[this.index] = document.getElementById("duoxuanBox" + this.index);
                console.log(this.index)
                if (brg[this.index].checked) {
                    brg[this.index].checked = false;
                } else {
                    brg[this.index].checked = true;
                }
                boolArr[this.index] = true;
            })
            arg[i].addEventListener('dblclick', function () {
                brg[this.index] = document.getElementById("duoxuanBox" + this.index);
                console.log(this.index)
                if (brg[this.index] != null && !brg[this.index].checked) {
                    brg[this.index].checked = true;
                }

                // console.log("parent[i].id:"+this)
                document.getElementById("aa2").src = '../user/showDetail';
                var result = [];
                result.push(this.id);
                document.getElementById("pageId1").name = result.join("-");
                //双击事件在此添加
            })
        }

        document.getElementById("btnAll").checked = false;

        for (var i = 0; i < boolArr.length; i++) boolArr[i] = false;


        $("#btnAll").click(function () {
            var ck = this.checked;


            var boxes = document.getElementsByName("duoxuanBox");

            for (var i = 0; i < boxes.length; i++) {
                boxes[i].checked = ck;
                if (ck) boolArr[i] = true;
                else boolArr[i] = false;
            }
        });


        $("input[name='duoxuanBox']").click("click", function () {
            var index = $(this).val();
            if (this.checked) boolArr[index] = true;
            else boolArr[index] = false;

            var alen = $("input[name='duoxuanBox']").length;
            //获取选中的爱好框个数
            var clen = $("input[name='duoxuanBox']:checked").length;

            if (alen == clen) {
                document.getElementById("btnAll").checked = true;
            } else {
                document.getElementById("btnAll").checked = false;
            }

        });
        for (var i = 0; i < parent.length; i++) {
            var arg = [], brg = [];
            arg[i] = document.getElementById(parent[i].id);
            brg[i] = document.getElementById("duoxuanBox" + i);

            arg[i].addEventListener('click', function () {
                brg[i] = document.getElementById("duoxuanBox" + i);
                // console.log(this)
                if (brg[i] != null && brg[i].checked == true) {
                    brg[i].checked = false;
                } else {
                    if (brg[i] != null) {
                        brg[i].checked = true;
                    }
                }
            })
        }

    }

    function init() {
        TimerInit();
        setInterval(shuaxin, 1000);
        document.getElementById("aa2").src = "../user/showRight";
        // $.messager.alert("提示",document.getElementById("aa2").contentWindow, 'error');
        //document.getElementById("aa2").contentWindow.init();

    }

    function shuaxin() {
        // var normal=window.parent.normal;
        var parent;
        // 获取页数与数据总数
        $.ajax({
            url: "<%=basePath%>user/parentList",
            type: "post",
            async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {},
            dataType: "json",
            success: function (data) {
                parent = data;
            }

        });
        //  console.log(parent);
        var zt = "";
        var yanse = "";
        for (var i = 0; i < parent.length; i++) {
            if (parent[i].zhuangtai == 0) {
                zt = "正常";
                yanse = "#6ae115"
            } else if (parent[i].zhuangtai == 1) {
                zt = "异常";
                yanse = "#fb0202"
            } else {
                zt = "报警";
                yanse = "#ffc600"
            }

            /*if(document.getElementById("aa2").src!="showRight.html"&&document.getElementById("tr"+parent.get(i).getId()+"zt").style.backgroundColor=="yellow"){
yanse="yellow";
}*/
            document.getElementById("tr" + parent[i].id + "zt").innerHTML = zt;
            document.getElementById("tr" + parent[i].id + "zt").style.backgroundColor = yanse;


        }
        ;
    }

    function ToHTML(id) {
        document.getElementById("aa2").src = '../user/showDetail';
        document.getElementById("pageId1").name = id;
        /*   ids=id.split("-");
           length=ids.length;
           for(var i=0;i<length;i++){
               document.getElementById("tr"+ids[i]+"zt").style.backgroundColor="#ffc600";
               document.getElementById("tr"+ids[i]+"zt").style.color="black";
               document.getElementById("tr"+ids[i]).style.backgroundColor="#ffc600";
           }*/

    }

    init();
    $("#btnSure").click(function () {

        var result = [];
        for (var i = 0; i < idArr.length; i++) {
            if (boolArr[i]) result.push(idArr[i]);
        }
        if (result.length == 0) {
            $.messager.alert("消息提醒", "未进行选择！", "warning");
            return;
        }
        ToHTML(result.join("-"));
    });

    $("#btnCancel").click(function () {
        TimerInit();
        document.getElementById("aa2").src = "../user/showRight";
    });
</script>
</body>

</html>