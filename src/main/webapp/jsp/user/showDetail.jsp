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

        table {
            table-layout: fixed;
        }

        table th {
            font-size: 20px;
            font-weight: 100;
        }

        #dv {
            width: 100%;
            background-color: #f8f8fa !important;
        }

        #btn_top {
            border-top: 20px;
            width: 100%;
            background: #ffffff;
        }

        #btn_top input {
            vertical-align: center;
        }

        #btn_top button {
            background-color: white; /* Green */
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

        #btn_top button :hover {
            background-color: #0091EA;
            color: white;
            border: 0px;
        }

        .button2 {
            background-color: white;
            color: black;
            border: 2px solid gray;
        }

        .button2:hover {
            background-color: #0091EA;
            color: white;
        }

        #table_container {
            border: none;
            width: 100%;
            margin: 0px;
            background-color: #f8f8fa !important;
            text-align: center;
        }

        .tab {
            font-size: 18px;
            margin-top: 1px !important;
            padding: 0px;
            width: 100%;
            background: #f8f8fa;
        }

        .detail_table {
            font-size: 18px;
            width: 32%;
            text-align: center;
            border: none;
            float: left;
            margin-left: 1% !important;
            margin-right: 0px !important;
            margin-top: 20px;
            background: white;

            box-shadow: inset 0 0 10px #CCC;
        }

        th {
            height: 40px !important;
        }

        img {
            margin-bottom: 15px;
            max-width: 150px;
            max-height: 150px;
            min-height: 150px;
            min-width: 150px;
        }
    </style>

</head>
<body>
<div id="dv" class="img">
</div>
<script type="text/javascript" defer="true">

    var ProportionHeightInImg; //鼠标所选位置相对于所选图片高度的比例
    var ProportionWidthInImg;//鼠标所选位置相对于所选图片宽度的比例
    var htmllist = "";
    var boolArr = [];
    var idArr = [];
    init();

    function init() {
        TimerInit();
        // $.messager.alert("提示", document.getElementsByClassName('10error'), 'error');

        setInterval(shuaxin, 1000);
    }

    function TimerInit() {
        // var normal=window.parent.parent.normal;

        var id = 0;
        id = window.parent.document.getElementById("pageId1").name;
        // $.messager.alert("提示",id, 'error');
        var length = 1;
        var ids;
        var num = 0;
        var isDanxuan = false;

        ids = id.split("-");
        length = ids.length;
        // $.messager.alert("提示",length, 'error');
        htmllist += "<div id= 'btn_top'> &nbsp;&nbsp; &nbsp;&nbsp;  &nbsp;&nbsp;<input id='btnAll' type='checkbox'  />&nbsp;&nbsp;全选 &nbsp;&nbsp;";
        htmllist += " &nbsp;&nbsp; &nbsp;&nbsp;<button class='button2' type='button' id='btnSure' >查看所有</button> &nbsp;&nbsp; &nbsp;&nbsp; ";
        htmllist += "<button class='button2' type='button' id='btnGuzhang' >查看故障</button> &nbsp;&nbsp; &nbsp;&nbsp;";
        htmllist += "<button class='button2' type='button' id='btnBack' >返回</button>";
        htmllist += "</div>";
        htmllist += "<div id='table_container' >";
        // console.log(length);
        for (var i = 0; i < length; i++) {
            var ParentList;
            //$.messager.alert("提示",ids[i], 'error');
            if (ids[i] == "") continue;
            htmllist += "<div id='" + i + "' style='float:left'>";
            //ParentList=normal.getChanpinByParentId(ids[i]);
            //$.messager.alert("提示",ids.length, 'error');
            var idsi = '0';
            idsi = '' + ids[i];
            // $.messager.alert("提示",idsi, 'error');
            $.ajax({
                url: "<%=basePath%>user/getChanpinByParentId",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {id: idsi},
                dataType: "json",
                success: function (data) {
                    ParentList = data;
                }
            });
            //$.messager.alert("提示","ParentList:"+ParentList, 'error');
            for (var o = 0; o < ParentList.length; o++) {
                //	var Leibie=normal.getChanpinLeibie(ParentList.get(o).getId());
                //	var photo=normal.findCPbyChanpinId(ParentList.get(o).getId());
                var Leibie = null;
                var photo = null;
                var ParentListoid = '' + ParentList[o].id;
                $.ajax({
                    url: "<%=basePath%>user/getChanpinLeibie",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {chanpinId: ParentListoid},
                    dataType: "json",
                    success: function (data) {
                        Leibie = data;
                    }
                });
                $.ajax({
                    url: "<%=basePath%>user/findCPbyChanpinId",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {chanpinId: ParentListoid},
                    dataType: "json",
                    success: function (data) {
                        photo = data;
                    }
                });
                if (photo != null) {
                    //var path=normal.getpath()+photo.getUrl();
                    var s = photo.url;
                }

                htmllist += "<table style='margin-left:1% !important;margin-right:0px !important;' class='detail_table'>" +
                    //"<tr ><th width='10%'>1</th><th width='45%'>1</th><th width='45%'>1</th></tr>"+
                    "<tr ><th style='height:1%;width:14%'></th><th style='height:1%;width:43%'></th><th style='height:1%;width:43%'></th></tr>" +
                    "<tr>" +
                    "<th colspan='3' style='border-bottom:1px solid gray;'><img  alt='未给该产品分配图片' id='" + i + "" + o + "image' src='../static/" + s + "' /></th></tr>" +


                    "<tr><th  rowspan='5' style='border-right:1px solid #d9d9d9;'><input type='checkbox' name='duoxuan' value='" + num + "' /></th>" +
                    "<th  style='border-right:1px solid #d9d9d9;border-bottom:1px solid #d9d9d9;'>&nbsp;&nbsp;&nbsp;产品所属区域&nbsp;&nbsp;&nbsp;  </th>" +
                    "<th style='border-bottom:1px solid #d9d9d9;' >" + Leibie.location + "</th>" +
                    "</tr>" +
                    "<tr>" +
                    "<th style='border-bottom:1px solid #d9d9d9;border-right:1px solid #d9d9d9;' >产品类别名</th>" +
                    "<th style='border-bottom:1px solid #d9d9d9;' >" + Leibie.name + "</th>" +
                    "</tr>" +
                    "<tr>" +
                    "<th style='border-bottom:1px solid #d9d9d9;border-right:1px solid #d9d9d9;' >产品型号</th>" +
                    "<th  style='border-bottom:1px solid #d9d9d9;'>" + Leibie.model + "</th>" +
                    "</tr>" +
                    "<tr>" +
                    "<th style='border-bottom:1px solid #d9d9d9;border-right:1px solid #d9d9d9;' >设备数量</th>" +
                    "<th  style='border-bottom:1px solid #d9d9d9;'>" + Leibie.chanpinNum + "</th>" +
                    "</tr>" +
                    "<tr>" +
                    "<th  style='border-right:1px solid #d9d9d9;'>故障数量</th>" +
                    "<th  ><span id='" + i + "" + o + "error' class='" + i + "" + o + "error'  style='color: red'>" + Leibie.errorNum + "</span></th>" +
                    "</tr>";//+
                //"</table><br>";

                boolArr.push(false);
                idArr.push(ParentList[o].id);
                num++;
            }
            htmllist += "</table>";
            htmllist += "</div>";
        }
        htmllist += "</div>";
        document.getElementById("dv").innerHTML = htmllist;

    }

    function shuaxin() {
        // var normal=window.parent.parent.normal;

        var id = 0;
        if (window.location.href.indexOf("?") == -1) id = window.parent.document.getElementById("pageId1").name;
        else id = window.location.href.split("?")[1].split("=")[1];
        var length = 1;
        var ids;
        var num = 0;
        var isDanxuan = false;
        if (id.indexOf("-") != -1) {
            ids = id.split("-");
            length = ids.length;

        } else {
            isDanxuan = true;

        }
        for (var i = 0; i < length; i++) {
            var ParentList = null;

            if (isDanxuan == true) {
                //ParentList=normal.getChanpinByParentId(id);
                $.ajax({
                    url: "<%=basePath%>user/getChanpinByParentId",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {id: id},
                    dataType: "json",
                    success: function (data) {
                        ParentList = data;
                    }
                });
            } else {
                //ParentList=normal.getChanpinByParentId(ids[i]);
                var idsi = ids[i];
                $.ajax({
                    url: "<%=basePath%>user/getChanpinByParentId",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {id: idsi},
                    dataType: "json",
                    success: function (data) {
                        ParentList = data;
                    }
                });
            }


            for (var o = 0; o < ParentList.length; o++) {
                var Leibie = null;
                var ParentListoid = ParentList[o].id;
                $.ajax({
                    url: "<%=basePath%>user/getChanpinLeibie",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {chanpinId: ParentListoid},
                    dataType: "json",
                    success: function (data) {
                        Leibie = data;
                    }
                });
                //$.messager.alert("提示","Leibie:"+Leibie, 'error');
                if (Leibie == null) {
                    // var aaa='0';
                    //       var iiii=i+""+o+"error";
                    // $.messager.alert("提示","aaa:"+ iiii+"    "+document.getElementById(iiii), 'error');
                    document.getElementsByClassName(i + "" + o + "error").innerHTML = '0';
                } else {
                    document.getElementById(i + "" + o + "error").innerHTML = Leibie.errorNum;
                }


            }
        }
    }

    function ToHTML(id) {
        window.parent.document.getElementById("aa2").src = '../user/showDetail2';
        window.parent.document.getElementById("pageId2").name = id;

    }

    $("#btnBack").click(function () {
        window.parent.TimerInit();
        window.parent.document.getElementById("aa2").src = '../user/showRight';
    });

    $("#btnAll").click(function () {
        var ck = this.checked;

        var boxes = document.getElementsByName("duoxuan");

        for (var i = 0; i < boxes.length; i++) {
            boxes[i].checked = ck;
            if (ck) boolArr[i] = true;
            else boolArr[i] = false;
        }
    });


    $("input[name='duoxuan']").click(function () {
        var index = $(this).val();
        if (this.checked) boolArr[index] = true;
        else boolArr[index] = false;

        var alen = $("input[name='duoxuan']").length;
        //获取选中的爱好框个数
        var clen = $("input[name='duoxuan']:checked").length;

        if (alen == clen) {
            document.getElementById("btnAll").checked = true;
        } else {
            document.getElementById("btnAll").checked = false;
        }

    });

    $("#btnSure").click(function () {

        var result = [];
        for (var i = 0; i < idArr.length; i++) {
            if (boolArr[i]) result.push(idArr[i]);
        }
        //$.messager.alert("",result,"");
        if (result.length == 0) {
            $.messager.alert("消息提醒", "未进行选择！", "warning");
            return;
        }
        ToHTML(result.join("-"));
    });

    $("#btnGuzhang").click(function () {
        var result = [];
        for (var i = 0; i < idArr.length; i++) {
            if (boolArr[i]) result.push(idArr[i]);
        }
        if (result.length == 0) {
            $.messager.alert("消息提醒", "未进行选择！", "warning");
            return;
        }
        ToHTML(result.join("-") + "-guzhang");
    });


</script>
</body>
</html>