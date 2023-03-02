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
</head>
<style>
    * {
        font-family: Microsoft YaHei;
        font-style: normal;
    }

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
<body>
<div id="main" style="width:100%;height:100%;">
</div>
<script type="text/javascript">
    init();

    function init() {
        Init();
        setInterval(shuaxin, 1000);
    }

    function Init() {
        var myDate = new Date();
        var locationId = "${locationId}";
        //console.log("locationId: "+locationId);
        var htmllist = "";
        htmllist += "<div><table width='100%'><tr >" +
            "<th style='width:20%;height:20px'>产品类别</th>" +
            "<th style='width:15%;'>产品名</th>" +
            "<th style='width:20%;'>监测项</th>" +
            "<th style='width:23%;'>状态/参数</th>" +
            "<th style='width:22%;'>时间</th>" +
            "</tr>";
        var leibies = null;
        $.ajax({
            url: "<%=basePath%>user/getChanpinLeibiesByLocationId",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"locationId": locationId},
            dataType: "json",
            success: function (data) {
                leibies = data;
            }
        });
        for (var i = 0; i < leibies.length; i++) {
            var leibie = leibies[i];
            if (leibie.name == "智能静电(漏电)监测仪") {
                var chanpins = null;
                var leiId = leibie.id;
                $.ajax({
                    url: "<%=basePath%>user/getChanpinsByLeibieId",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {"leibieId": leiId},
                    dataType: "json",
                    success: function (data) {
                        chanpins = data;
                    }
                });
                //console.log("chanpins.length: "+chanpins.length);
                htmllist += "<tr><td rowspan='" + chanpins.length * 2 + " width='20%'>" + "智能静电(漏电)监测仪" + "</td>";
                htmllist += "<td rowspan='2' width='15%'>" + chanpins[0].name + "</td>";
                htmllist += "<td rowspan='1' width='20%'>" + "电压值" + "</td>";
                htmllist += "<td rowspan='1' width='23%' id='chanpin" + chanpins[0].id + "datameaning658" + "'>" + "" + "</td>";
                htmllist += "<td rowspan='1' width='22%'id='chanpin" + chanpins[0].id + "datameaning658time" + "'>" + "" + "</td></tr>";
                htmllist += "<tr><td rowspan='1' width='20%'>" + "内部温度" + "</td>";
                htmllist += "<td rowspan='1' width='23%' id='chanpin" + chanpins[0].id + "datameaning662" + "'>" + "" + "</td>";
                htmllist += "<td rowspan='1' width='22%' id='chanpin" + chanpins[0].id + "datameaning662time" + "'>" + "" + "</td></tr>";

                if (chanpins.length > 1) {
                    for (var j = 1; j < chanpins.length; j++) {
                        htmllist += "<tr><td rowspan='2' width='15%'>" + chanpins[j].name + "</td>";
                        htmllist += "<td rowspan='1' width='20%'>" + "电压值" + "</td>";
                        htmllist += "<td rowspan='1' width='23%' id='chanpin" + chanpins[j].id + "datameaning658" + "'>" + "" + "</td>";
                        htmllist += "<td rowspan='1' width='22%' id='chanpin" + chanpins[j].id + "datameaning658time" + "'>" + "" + "</td></tr>";
                        htmllist += "<tr><td rowspan='1' width='20%'>" + "内部温度" + "</td>";
                        htmllist += "<td rowspan='1' width='23%' id='chanpin" + chanpins[j].id + "datameaning662" + "'>" + "" + "</td>";
                        htmllist += "<td rowspan='1' width='22%' id='chanpin" + chanpins[j].id + "datameaning662time" + "'>" + "" + "</td></tr>";
                    }
                }
            } else if (leibie.name == "智能接地电阻监测仪") {
                var chanpins = null;
                var leiId = leibie.id;
                $.ajax({
                    url: "<%=basePath%>user/getChanpinsByLeibieId",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {"leibieId": leiId},
                    dataType: "json",
                    success: function (data) {
                        chanpins = data;
                    }
                });
                htmllist += "<tr><td rowspan='" + chanpins.length + " width='20%'>" + "智能接地电阻监测仪" + "</td>";
                htmllist += "<td rowspan='1' width='15%'>" + chanpins[0].name + "</td>";
                htmllist += "<td rowspan='1' width='20%'>" + "回路电阻值" + "</td>";
                htmllist += "<td rowspan='1' width='23%' id='chanpin" + chanpins[0].id + "datameaning641" + "'>" + "" + "</td>";
                htmllist += "<td rowspan='1' width='22%' id='chanpin" + chanpins[0].id + "datameaning641time" + "'>" + "" + "</td></tr>";
                if (chanpins.length > 1) {
                    for (var j = 1; j < chanpins.length; j++) {
                        htmllist += "<tr><td rowspan='1' width='15%'>" + chanpins[j].name + "</td>";
                        htmllist += "<td rowspan='1' width='20%'>" + "回路电阻值" + "</td>";
                        htmllist += "<td rowspan='1' width='23%' id='chanpin" + chanpins[j].id + "datameaning641" + "'>" + "" + "</td>";
                        htmllist += "<td rowspan='1' width='22%' id='chanpin" + chanpins[j].id + "datameaning641time" + "'>" + "" + "</td></tr>";
                    }
                }
            }
            //增加全功能设备的实时显示部分
            else if (leibie.name == "全功能解析器") {//后序可修改
                var chanpins = null;
                var leiId = leibie.id;
                $.ajax({
                    url: "<%=basePath%>user/getChanpinsByLeibieId",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {"leibieId": leiId},
                    dataType: "json",
                    success: function (data) {
                        chanpins = data;
                    }
                });
                htmllist += "<tr><td rowspan='" + chanpins.length + " width='20%'>" + "环境信息采集器" + "</td>";
                htmllist += "<td rowspan='1' width='15%'>" + chanpins[0].name + "</td>";
                htmllist += "<td rowspan='1' width='20%'>" + "环境湿度" + "</td>";
                htmllist += "<td rowspan='1' width='23%' id='chanpin" + chanpins[0].id + "datameaning548" + "'>" + "" + "</td>";
                htmllist += "<td rowspan='1' width='22%' id='chanpin" + chanpins[0].id + "datameaning548time" + "'>" + "" + "</td></tr>";
                if (chanpins.length > 1) {
                    for (var j = 1; j < chanpins.length; j++) {
                        htmllist += "<tr><td rowspan='1' width='15%'>" + chanpins[j].name + "</td>";
                        htmllist += "<td rowspan='1' width='20%'>" + "环境湿度" + "</td>";
                        htmllist += "<td rowspan='1' width='23%' id='chanpin" + chanpins[j].id + "datameaning548" + "'>" + "" + "</td>";
                        htmllist += "<td rowspan='1' width='22%' id='chanpin" + chanpins[j].id + "datameaning548time" + "'>" + "" + "</td></tr>";
                    }
                }
            }

        }
        htmllist += "</table></div>";
        document.getElementById("main").innerHTML = htmllist;
        for (var i = 0; i < leibies.length; i++) {
            var leibie = leibies[i];
            var chanpins = null;
            var leiId = leibie.id;
            var rejisters = null;
            $.ajax({
                url: "<%=basePath%>user/getAllInputRejister",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {chanpinLeibieId: leiId},
                dataType: "json",
                success: function (data) {
                    rejisters = data;
                }
            });
            $.ajax({
                url: "<%=basePath%>user/getChanpinsByLeibieId",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {"leibieId": leiId},
                dataType: "json",
                success: function (data) {
                    chanpins = data;
                }
            });
            for (var j = 0; j < chanpins.length; j++) {
                var chan = chanpins[j];
                for (var k = 0; k < rejisters.length; k++) {
                    var displayData = null;
                    var chanid = chan.id;
                    var rejisterid = rejisters[k].id;
                    // $.messager.alert("","chanpinid :"+chanpinid+" rejisterid: "+rejisterid,"");
                    $.ajax({
                        url: "<%=basePath%>user/getDisplayData",
                        type: "post",
                        async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                        data: {"chanpinId": chanid, "registerId": rejisterid},
                        dataType: "json",
                        success: function (data) {
                            displayData = data;
                        }
                    });
                    for (var l = 0; l < displayData.length; l++) {
                        var sons = displayData[l].sons;
                        for (var o = 0; o < sons.length; o++) {
                            //console.log(sons[o])
                            var mark = sons[o].id + "";
                            switch (mark) {
                                case mark = "641":

                                    document.getElementById("chanpin" + chan.id + "datameaning641").innerHTML = sons[o].data;
                                    document.getElementById("chanpin" + chan.id + "datameaning641time").innerHTML = myDate.toLocaleString();
                                    break;
                                case mark = '658':

                                    document.getElementById("chanpin" + chan.id + "datameaning658").innerHTML = sons[o].data;
                                    document.getElementById("chanpin" + chan.id + "datameaning658time").innerHTML = myDate.toLocaleString();
                                    break;
                                case mark = '662':

                                    document.getElementById("chanpin" + chan.id + "datameaning662").innerHTML = sons[o].data;
                                    document.getElementById("chanpin" + chan.id + "datameaning662time").innerHTML = myDate.toLocaleString();
                                    break;
                                case mark = '548':

                                    document.getElementById("chanpin" + chan.id + "datameaning548").innerHTML = sons[o].data;
                                    document.getElementById("chanpin" + chan.id + "datameaning548time").innerHTML = myDate.toLocaleString();
                                    break;

                                default:
                                    break;
                            }
                        }
                    }
                }//输入寄存器遍历结束
            }	//产品遍历结束
        }
    }

    function shuaxin() {
        var myDate = new Date();
        var locationId = "${locationId}";
        var leibies = null;
        $.ajax({
            url: "<%=basePath%>user/getChanpinLeibiesByLocationId",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"locationId": locationId},
            dataType: "json",
            success: function (data) {
                leibies = data;
            }
        });
        for (var i = 0; i < leibies.length; i++) {
            var leibie = leibies[i];
            var chanpins = null;
            var leiId = leibie.id;
            var rejisters = null;
            $.ajax({
                url: "<%=basePath%>user/getAllInputRejister",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {chanpinLeibieId: leiId},
                dataType: "json",
                success: function (data) {
                    rejisters = data;
                }
            });
            $.ajax({
                url: "<%=basePath%>user/getChanpinsByLeibieId",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {"leibieId": leiId},
                dataType: "json",
                success: function (data) {
                    chanpins = data;
                }
            });
            for (var j = 0; j < chanpins.length; j++) {
                var chan = chanpins[j];
                for (var k = 0; k < rejisters.length; k++) {
                    var displayData = null;
                    var chanid = chan.id;
                    var rejisterid = rejisters[k].id;
                    // $.messager.alert("","chanpinid :"+chanpinid+" rejisterid: "+rejisterid,"");
                    $.ajax({
                        url: "<%=basePath%>user/getDisplayData",
                        type: "post",
                        async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                        data: {"chanpinId": chanid, "registerId": rejisterid},
                        dataType: "json",
                        success: function (data) {
                            displayData = data;
                        }
                    });
                    for (var l = 0; l < displayData.length; l++) {
                        var sons = displayData[l].sons;
                        for (var o = 0; o < sons.length; o++) {
                            //console.log(sons[o])
                            var mark = sons[o].id + "";
                            switch (mark) {
                                case mark = "641":

                                    document.getElementById("chanpin" + chan.id + "datameaning641").innerHTML = sons[o].data;
                                    document.getElementById("chanpin" + chan.id + "datameaning641time").innerHTML = myDate.toLocaleString();
                                    break;
                                case mark = '658':

                                    document.getElementById("chanpin" + chan.id + "datameaning658").innerHTML = sons[o].data;
                                    document.getElementById("chanpin" + chan.id + "datameaning658time").innerHTML = myDate.toLocaleString();
                                    break;
                                case mark = '662':

                                    document.getElementById("chanpin" + chan.id + "datameaning662").innerHTML = sons[o].data;
                                    document.getElementById("chanpin" + chan.id + "datameaning662time").innerHTML = myDate.toLocaleString();
                                    break;
                                case mark = '548':

                                    document.getElementById("chanpin" + chan.id + "datameaning548").innerHTML = sons[o].data;
                                    document.getElementById("chanpin" + chan.id + "datameaning548time").innerHTML = myDate.toLocaleString();
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }//输入寄存器遍历结束
            }	//产品遍历结束
        }
    }

</script>
</body>
</html>