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
    <style type="text/css">
        * {

            font-family: Microsoft YaHei;
            font-style: normal;
            font-size: 18px;
            font-weight: 100;

        }

        #btn_top1 {
            border-top: 15px;
            width: 100%;
            background: #ffffff;
        }

        #btn_top1 input {
            vertical-align: center;

        }

        #btn_top1 button {
            background-color: white; /* #6ae115 */
            border: 1px solid black;
            color: black;
            padding: 4px 6px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            margin: 15px 2px;
            -webkit-transition-duration: 0.4s; /* Safari */
            transition-duration: 0.4s;
            cursor: pointer;
            border-radius: 4px;
        }

        #btn_top1 button :hover {
            background-color: #0091EA;
            color: white;
            border: 0px;
        }

        img {
            border-top: 10px;
            border-bottom: 10px;
            margin-top: 10px;
            margin-bottom: 10px;
            max-width: 150px;
            max-height: 150px;
            min-height: 150px;
            min-width: 150px;
        }

        .tab1 {
            width: 98%;
            margin-top: 10px;
        }

        .tab1 table {

            box-shadow: inset 0 0 10px #CCC;
        }

        table {
            background: white;
            width: 100%;
            margin-left: 10px;
            margin-right: 10px;
            margin-top: 10px;
            border-collapse: collapse;
            border: 0px solid #999;
        }

        table th {
            border-top: 0;
            border-right: 1px solid #d9d9d9;
            border-bottom: 1px solid #d9d9d9;
            border-left: 0;
        }

        table tr.lastrow th {
            border-bottom: 0;
        }

        table tr th.lastCol {
            border-right: 0;
            height: 30px;
        }

        th {
            height: 60px;
        }
    </style>

</head>
<body>
<div id="dv" class="img">

</div>
<script type="text/javascript">

    var htmllist = "";
    var jumpIds = "";
    var boolArr = [];
    var idArr = [];
    init();

    function init() {
        TimerInit();
        setInterval(shuaxin, 1000);
    }

    function TimerInit() {

        htmllist += "<div id= 'btn_top1'> &nbsp;&nbsp; &nbsp;&nbsp;  &nbsp;&nbsp;<input id='btnAll' type='checkbox' />&nbsp;&nbsp;全选&nbsp;&nbsp;";

        htmllist += " &nbsp;&nbsp; &nbsp;&nbsp;<button  type='button' id='btnSure' >查看</button>";

        htmllist += " &nbsp;&nbsp; &nbsp;&nbsp;<button  type='button' id='btnBack' >返回</button>";
        htmllist += "</div>";
        htmllist += "<div  id='container2'>";
        // var normal=window.parent.parent.normal;

        var id = window.parent.document.getElementById("pageId2").name;

        var ids = id.split("-");
        var length = ids.length;


        for (var i = 0; i < length; i++) {
            //$.messager.alert("提示", "执行第"+i+"次", 'error');
            var realId = ids[i];
            if (realId == "guzhang") realId == "";
            if (realId == "") continue;
            if (id.indexOf("guzhang") > 0) {

                // var cDisplay=normal.getErrorChanpinDisplay(realId);
                var cDisplay = null;
                $.ajax({
                    url: "<%=basePath%>user/getErrorChanpinDisplay",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {chanpinId: realId},
                    dataType: "json",
                    success: function (data) {
                        cDisplay = data;
                    }
                });
                // $.messager.alert("提示", "cDisplay  ："+cDisplay, 'error');
                if (cDisplay != null) {
                    var son = cDisplay.son;
                    // var photo=normal.findCPbyChanpinId(realId);
                    var photo = null;
                    $.ajax({
                        url: "<%=basePath%>user/findCPbyChanpinId",
                        type: "post",
                        async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                        data: {chanpinId: realId},
                        dataType: "json",
                        success: function (data) {
                            photo = data;
                        }
                    });
                    if (photo != null) {
                        //var path=normal.getpath()+photo.getUrl();
                        var s = photo.url;

                    }

                    var temp = [];
                    var tempBool = [];

                    htmllist += "<div class='tab1'><table  >" +
                        "<tr >" +
                        "<th style='width:20%;'>产品图片</th>" +
                        "<th style='width:10%;'>产品型号</th>" +
                        "<th style='width:10%;'><input type='checkbox' name='ChooseAll' onclick='ChooseAll(" + i + ")'  id='ChooseAll" + i + "'/></th>" +
                        "<th style='width:10%;'>序号</th>" +
                        "<th style='width:10%;'>安装位置</th>" +
                        "<th style='width:10%;'>地址码</th>" +
                        "<th style='width:10%;'>状态</th>" +
                        "<th style='width:20%;'>事件时间</th>" +
                        "</tr>" +
                        "<tr >" +
                        "<th width=20% style='border-bottom:0;' rowspan='" + son.length + "' ><img alt='未给该产品分配图片' id='" + i + "image' src='../static/" + s + "' /></th>" +
                        "<th width=10% style='border-bottom:0;' rowspan='" + son.length + "' >" + cDisplay.model + "</th>";
                    if (son.length > 0) {
                        var zt = "";
                        var yanse = "";
                        if (son[0].zhuangtai == 0) {
                            zt = "正常";
                            yanse = "#6ae115";
                        } else if (son[0].zhuangtai == 1) {
                            zt = "异常";
                            yanse = "#fb0202";
                        } else {
                            zt = "报警";
                            yanse = "#ffc600";
                        }
                        htmllist += "<th  style='border-bottom:1px solid #d9d9d9;' ><input type='checkbox' name='" + i + "Duoxuan' onclick=DuoXuan(" + i + "," + 0 + ") /></th>" +
                            "<th   style='border-bottom:1px solid #d9d9d9;width:10%;'>" + son[0].id + "</th>" +
                            "<th   style='border-bottom:1px solid #d9d9d9;width:10%;'>" + son[0].installation + "</th>" +
                            "<th  style='border-bottom:1px solid #d9d9d9;width:10%;' >" + son[0].slaveId + "</th>" +
                            "<th style='color:white !important;width:10%;border-bottom:1px solid #d9d9d9;background-color:" + yanse + "' id='" + i + "" + 0 + "yanse'>" + zt + "</th>" +
                            "<th  style='width:20%;border-bottom:1px solid #d9d9d9;' id='" + i + "" + 0 + "time'>" + son[0].time + "</th>";
                        tempBool.push(false);
                        temp.push(son[0].id);

                    }
                    htmllist += "</tr>";
                    for (var j = 1; j < son.length; j++) {
                        var zt = "";
                        var yanse = "";
                        if (son[j].zhuangtai == 0) {
                            zt = "正常";
                            yanse = "#6ae115";
                        } else if (son[j].zhuangtai == 1) {
                            zt = "异常";
                            yanse = "#fb0202";
                        } else {
                            zt = "报警";
                            yanse = "#ffc600";
                        }
                        htmllist += "<tr >" +
                            "<th style='width:10%;'><input type='checkbox' name='" + i + "Duoxuan' onclick=DuoXuan(" + i + "," + j + ")  /></th>" +
                            "<th style='width:10%;'>" + son[j].id + "</th>" +
                            "<th style='width:10%;'>" + son[j].installation + "</th>" +
                            "<th style='width:10%;'>" + son[j].slaveId + "</th>" +
                            "<th style='width:10%;color:white!important;background-color:" + yanse + "' id='" + i + "" + j + "yanse'>" + zt + "</th>" +
                            "<th style='width:20%;' id='" + i + "" + j + "time'>" + son[j].time + "</th>" +
                            "</tr>";
                        tempBool.push(false);

                        temp.push(son[j].id);

                    }
                    htmllist += "</table></div>";
                    boolArr.push(tempBool);
                    idArr.push(temp);
                }
            } else {

                // var cDisplay=normal.getChanpinDisplay(realId);
                var cDisplay = null;
                $.ajax({
                    url: "<%=basePath%>user/getChanpinDisplay",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {chanpinId: realId},
                    dataType: "json",
                    success: function (data) {
                        cDisplay = data;
                    }
                });
                // var son=cDisplay.getSon();

                var son = cDisplay.son;
                // var photo=normal.findCPbyChanpinId(realId);
                var photo = null;
                $.ajax({
                    url: "<%=basePath%>user/findCPbyChanpinId",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {chanpinId: realId},
                    dataType: "json",
                    success: function (data) {
                        photo = data;
                    }
                });
                if (photo != null) {
                    //var path=normal.getpath()+photo.getUrl();
                    var s = photo.url;

                }

                var temp = [];
                var tempBool = [];

                htmllist += "<div class='tab1'><table  >" +
                    "<tr >" +
                    "<th style='width:20%;'>产品图片</th>" +
                    "<th style='width:10%;'>产品型号</th>" +
                    "<th style='width:10%;'><input type='checkbox' name='ChooseAll' onclick='ChooseAll(" + i + ")'  id='ChooseAll" + i + "'/></th>" +
                    "<th style='width:10%;'>序号</th>" +
                    "<th style='width:10%;'>安装位置</th>" +
                    "<th style='width:10%;'>地址码</th>" +
                    "<th style='width:10%;'>状态</th>" +
                    "<th style='width:20%;'>事件时间</th>" +
                    "</tr>" +
                    "<tr >" +
                    "<th width=20% style='border-bottom:0;' rowspan='" + son.length + "' ><img alt='未给该产品分配图片' id='" + i + "image' src='../static/" + s + "' /></th>" +
                    "<th width=10% style='border-bottom:0;' rowspan='" + son.length + "' >" + cDisplay.model + "</th>";
                if (son.length > 0) {
                    var zt = "";
                    var yanse = "";
                    if (son[0].zhuangtai == 0) {
                        zt = "正常";
                        yanse = "#6ae115";
                    } else if (son[0].zhuangtai == 1) {
                        zt = "异常";
                        yanse = "#fb0202";
                    } else {
                        zt = "报警";
                        yanse = "#ffc600";
                    }
                    htmllist += "<th  style='border-bottom:1px solid #d9d9d9;' ><input type='checkbox' name='" + i + "Duoxuan' onclick=DuoXuan(" + i + "," + 0 + ") /></th>" +
                        "<th   style='border-bottom:1px solid #d9d9d9;width:10%;'>" + son[0].id + "</th>" +
                        "<th   style='border-bottom:1px solid #d9d9d9;width:10%;'>" + son[0].installation + "</th>" +
                        "<th  style='border-bottom:1px solid #d9d9d9;width:10%;' >" + son[0].slaveId + "</th>" +
                        "<th style='color:white !important;width:10%;border-bottom:1px solid #d9d9d9;background-color:" + yanse + "' id='" + i + "" + 0 + "yanse'>" + zt + "</th>" +
                        "<th  style='width:20%;border-bottom:1px solid #d9d9d9;' id='" + i + "" + 0 + "time'>" + son[0].time + "</th>";
                    tempBool.push(false);
                    temp.push(son[0].id);

                }
                htmllist += "</tr>";
                for (var j = 1; j < son.length; j++) {
                    var zt = "";
                    var yanse = "";
                    if (son[j].zhuangtai == 0) {
                        zt = "正常";
                        yanse = "#6ae115";
                    } else if (son[j].zhuangtai == 1) {
                        zt = "异常";
                        yanse = "#fb0202";
                    } else {
                        zt = "报警";
                        yanse = "#ffc600";
                    }
                    htmllist += "<tr >" +
                        "<th style='width:10%;'><input type='checkbox' name='" + i + "Duoxuan' onclick=DuoXuan(" + i + "," + j + ")  /></th>" +
                        "<th style='width:10%;'>" + son[j].id + "</th>" +
                        "<th style='width:10%;'>" + son[j].installation + "</th>" +
                        "<th style='width:10%;'>" + son[j].slaveId + "</th>" +
                        "<th style='width:10%;color:white!important;background-color:" + yanse + "' id='" + i + "" + j + "yanse'>" + zt + "</th>" +
                        "<th style='width:20%;' id='" + i + "" + j + "time'>" + son[j].time + "</th>" +
                        "</tr>";
                    tempBool.push(false);

                    temp.push(son[j].id);

                }
                htmllist += "</table></div>";
                boolArr.push(tempBool);
                idArr.push(temp);
            }

        }
        htmllist += "</div>";
        document.getElementById("dv").innerHTML = htmllist;


    }

    function shuaxin() {
        //var normal=window.parent.parent.normal;

        var id = window.parent.document.getElementById("pageId2").name;

        var ids = id.split("-");
        var length = ids.length;


        for (var i = 0; i < length; i++) {

            //var realId=ids[i];

            //if(realId=="")continue;
            var realId = ids[i];
            if (realId == "guzhang") realId == "";
            if (realId == "") continue;
            if (id.indexOf("guzhang") > 0) {
                var cDisplay = null;
                $.ajax({
                    url: "<%=basePath%>user/getErrorChanpinDisplay",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {chanpinId: realId},
                    dataType: "json",
                    success: function (data) {
                        cDisplay = data;
                    }
                });
                var son = cDisplay.son;
                if (son.length > 0) {
                    var zt = "";
                    var yanse = "";
                    if (son.get[0].zhuangtai == 0) {
                        zt = "正常";
                        yanse = "#6ae115";
                    } else if (son.get[0].zhuangtai == 1) {
                        zt = "异常";
                        yanse = "#fb0202";
                    } else {
                        zt = "报警";
                        yanse = "#ffc600";
                    }
                    document.getElementById(i + "" + 0 + "yanse").innerHTML = zt;
                    document.getElementById(i + "" + 0 + "yanse").style.backgroundColor = yanse;
                    document.getElementById(i + "" + 0 + "time").innerHTML = son[0].time;

                }

                for (var j = 1; j < son.length; j++) {
                    var zt = "";
                    var yanse = "";
                    if (son.get[j].zhuangtai == 0) {
                        zt = "正常";
                        yanse = "#6ae115";
                    } else if (son.get[j].zhuangtai == 1) {
                        zt = "异常";
                        yanse = "#fb0202";
                    } else {
                        zt = "报警";
                        yanse = "#ffc600";
                    }
                    document.getElementById(i + "" + j + "yanse").innerHTML = zt;
                    document.getElementById(i + "" + j + "yanse").style.backgroundColor = yanse;
                    document.getElementById(i + "" + j + "time").innerHTML = son[j].time;

                }
            } else {
                var cDisplay = null;
                $.ajax({
                    url: "<%=basePath%>user/getChanpinDisplay",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {chanpinId: realId},
                    dataType: "json",
                    success: function (data) {
                        cDisplay = data;
                    }
                });
                var son = cDisplay.son;
                if (son.length > 0) {
                    var zt = "";
                    var yanse = "";
                    //var zhuang=son[0].zhuangtai;
                    if (son[0].zhuangtai == 0) {
                        zt = "正常";
                        yanse = "#6ae115";
                    } else if (son[0].zhuangtai == 1) {
                        zt = "异常";
                        yanse = "#fb0202";
                    } else {
                        zt = "报警";
                        yanse = "#ffc600";
                    }
                    document.getElementById(i + "" + 0 + "yanse").innerHTML = zt;
                    document.getElementById(i + "" + 0 + "yanse").style.backgroundColor = yanse;
                    document.getElementById(i + "" + 0 + "time").innerHTML = son[0].time;

                }

                for (var j = 1; j < son.length; j++) {
                    var zt = "";
                    var yanse = "";
                    //var zzz=son[j].zhuangtai;
                    if (son[j].zhuangtai == 0) {
                        zt = "正常";
                        yanse = "#6ae115";
                    } else if (son[j].zhuangtai == 1) {
                        zt = "异常";
                        yanse = "#fb0202";
                    } else {
                        zt = "报警";
                        yanse = "#ffc600";
                    }
                    document.getElementById(i + "" + j + "yanse").innerHTML = zt;
                    document.getElementById(i + "" + j + "yanse").style.backgroundColor = yanse;
                    document.getElementById(i + "" + j + "time").innerHTML = son[j].time;

                }


            }

        }

    }


    function ToHTML(id) {
        window.parent.document.getElementById("aa2").src = '../user/showDetail3';
        window.parent.document.getElementById("pageId3").name = id;
    }

    $("#btnBack").click(function () {
        window.parent.document.getElementById("aa2").src = '../user/showDetail';
    });

    $("#btnAll").click(function () {
        var ck = this.checked;

        var boxes = document.getElementsByName("ChooseAll");

        for (var i = 0; i < boxes.length; i++) {
            boxes[i].checked = ck;
            ChooseAll(i);
        }
    });

    function ChooseAll(i) {
        var ck = document.getElementById("ChooseAll" + i).checked;

        var boxes = document.getElementsByName(i + "Duoxuan");

        for (var j = 0; j < boxes.length; j++) {
            boxes[j].checked = ck;
            if (ck) boolArr[i][j] = true;
            else boolArr[i][j] = false;
        }

        var alen = $("input[name='ChooseAll']").length;
        //获取选中的爱好框个数
        var clen = $("input[name='ChooseAll']:checked").length;

        if (alen == clen) {
            document.getElementById("btnAll").checked = true;
        } else {
            document.getElementById("btnAll").checked = false;
        }
    }

    function DuoXuan(k, j) {

        var boxes = document.getElementsByName(k + "Duoxuan");


        if (boxes[j].checked) boolArr[k][j] = true;
        else boolArr[k][j] = false;

        var alen = $("input[name='" + k + "Duoxuan']").length;
        //获取选中的爱好框个数
        var clen = $("input[name='" + k + "Duoxuan']:checked").length;

        if (alen == clen) {
            document.getElementById("ChooseAll" + k).checked = true;
        } else {
            document.getElementById("ChooseAll" + k).checked = false;
        }


        var alen1 = $("input[name='ChooseAll']").length;
        //获取选中的爱好框个数
        var clen1 = $("input[name='ChooseAll']:checked").length;

        if (alen1 == clen1) {
            document.getElementById("btnAll").checked = true;
        } else {
            document.getElementById("btnAll").checked = false;
        }


    };


    $("#btnSure").click(function () {

        var result = [];
        for (var i = 0; i < idArr.length; i++) {
            for (var j = 0; j < idArr[i].length; j++) {
                if (boolArr[i][j]) result.push(idArr[i][j]);
            }
        }

        if (result.length == 0) {
            $.messager.alert("消息提醒", "未进行选择！", "warning");
            return;
        }
        ToHTML(result.join("-"));

        // $.messager.alert("",result,"");
    });
</script>
</body>
</html>