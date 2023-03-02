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
        .img {
            position: absolute;
            width: 100%;
            height: 100%;
            z-index: 0;
        }

        .img .marker {
            position: absolute;
            width: 20px;
            height: 20px;
            border-radius: 50%
        }
    </style>

</head>
<body>

<div class="img" id="dv">
    <img id="image" height="100%" width="100%"/>
</div>

<script type="text/javascript">
    var ProportionHeightInImg; //鼠标所选位置相对于所选图片高度的比例
    var ProportionWidthInImg;//鼠标所选位置相对于所选图片宽度的比例
    var jumpIds = "";
    init();

    function createMarker(x, y, divName, i, name, size) {

        var aa = document.getElementById('' + i);
        if (aa != null) {
            document.getElementById(divName).removeChild(document.getElementById('' + i));
        }
        var div = document.createElement('yichang' + i);
        //var path='../static/dotImage/异常.png';
        div.className = 'marker';
        div.id = '' + i;
        div.style.left = x - (size / 2) + 'px';
        div.style.top = y - (size / 2) + 'px';
        div.innerHTML = "<a href='#' onclick='ToHTML(" + i + ")' >"
            + "<img src='" + "../static/dotImage/异常.png" + "' height='100%' width='100%' style='border-radius:30px'>" + "</a>";
        document.getElementById(divName).appendChild(div);
        div.style.width = size + 'px';
        div.style.height = size + 'px';
        document.getElementsByTagName('yichang' + i)[0].onmousemove = function () {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.open({
                    type: 2,
                    skin: 'layui-layer-demo', //样式类名
                    title: "" + name,
                    closeBtn: 1, //不显示关闭按钮
                    anim: 2,
                    id: 'LAY_layuipro',
                    area: ['550px', '500px'],
                    shadeClose: true, //开启遮罩关闭
                    content: '../user/showDetail4?id=' + i
                });
            });

        }
    }

    function createMarker_green(x, y, divName, i, name, size) {
        //console.log(i)
        var aa = document.getElementById('' + i);
        if (aa != null) {
            document.getElementById(divName).removeChild(document.getElementById('' + i));
        }
        var div = document.createElement('zhengchang' + i);
        //var path='WEB-INF/dotImage/正常.png';
        div.className = 'marker';
        div.id = '' + i;
        div.style.left = x - (size / 2) + 'px';
        div.style.top = y - (size / 2) + 'px';
        div.innerHTML = "<a href='#' onclick='ToHTML(" + i + ")' >"
            + "<img src='" + "../static/dotImage/正常.png" + "' height='100%' width='100%' style='border-radius:30px'>" + "</a>";
        document.getElementById(divName).appendChild(div);
        div.style.width = size + 'px';
        div.style.height = size + 'px';
        //console.log("id:"+ $(''+i));
        document.getElementsByTagName('zhengchang' + i)[0].onmousemove = function () {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.open({
                    type: 2,
                    skin: 'layui-layer-demo', //样式类名
                    title: "" + name,
                    closeBtn: 1, //不显示关闭按钮
                    anim: 2,
                    id: 'LAY_layuipro',
                    area: ['550px', '500px'],
                    shadeClose: true, //开启遮罩关闭
                    content: '../user/showDetail4?id=' + i
                });
            });

        }

    }

    function createMarker_yellow(x, y, divName, i, name, size) {

        var aa = document.getElementById('' + i);
        if (aa != null) {
            document.getElementById(divName).removeChild(document.getElementById('' + i));
        }
        var div = document.createElement('yujing' + i);
        // var path='WEB-INF/dotImage/预警.png';
        div.className = 'marker';
        div.id = '' + i;
        div.style.left = x - (size / 2) + 'px';
        div.style.top = y - (size / 2) + 'px';
        div.innerHTML = "<a href='#' onclick='ToHTML(" + i + ")' >"
            + "<img src='" + "../static/dotImage/预警.png" + "' height='100%' width='100%' style='border-radius:30px'>" + "</a>";
        document.getElementById(divName).appendChild(div);
        div.style.width = size + 'px';
        div.style.height = size + 'px';
        document.getElementsByTagName('yujing' + i)[0].onmousemove = function () {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.open({
                    type: 2,
                    skin: 'layui-layer-demo', //样式类名
                    title: "" + name,
                    closeBtn: 1, //不显示关闭按钮
                    anim: 2,
                    id: 'LAY_layuipro',
                    area: ['550px', '500px'],
                    shadeClose: true, //开启遮罩关闭
                    content: '../user/showDetail4?id=' + i
                });
            });

        }
    }


    function TimerInit() {
        //$.messager.alert("提示","执行一次", 'warning');
        //var normal=window.parent.parent.normal;
        var parent;
        $.ajax({
            url: "<%=basePath%>user/parentList",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {},
            dataType: "json",
            success: function (data) {
                parent = data;
            }
        });
        // var ima = '${image}';
        // var image=JSON.stringify(ima);
        // $.messager.alert("提示",image.url, 'warning');
        var url = '${image.url}';
        document.getElementById("image").src = "../static/" + url;
        var dots = '${image.dots}';
        var size = '${image.dotsize}';
        var list;
        $.ajax({
            url: "<%=basePath%>user/toDouble",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {dots},
            dataType: "json",
            success: function (data) {
                list = data;
            }
        });
        //$.messager.alert("提示",list, 'warning');
        var myImg = document.querySelector("#image");
        myImg.onload = function () {
            var currWidth = myImg.clientWidth;
            var currHeight = myImg.clientHeight;
            size = size * currWidth * currHeight;

            for (var i = 0; i < list.length; i++) {
                var ProportionWidthInImg = list[i].key;
                var ProportionHeightInImg = list[i].value;
                var x = currWidth * ProportionWidthInImg;
                var y = currHeight * ProportionHeightInImg;
                if (i >= parent.length) {
                    break;
                } else {
                    if (parent[i].zhuangtai == 0) {
                        createMarker_green(x, y, 'dv', parent[i].id, parent[i].name, size);
                    } else if (parent[i].zhuangtai == 1) {
                        createMarker(x, y, 'dv', parent[i].id, parent[i].name, size);
                    } else {
                        createMarker_yellow(x, y, 'dv', parent[i].id, parent[i].name, size);
                    }
                }
                //$.messager.alert("提示","执行一次", 'warning');

            }
        }


    }

    function init() {
        TimerInit();
        setInterval(TimerInit, 1000);
    }

    function ToHTML(id) {
        var result = [];
        var chanpins = null;
        $.ajax({
            url: "<%=basePath%>user/getChanpinByLocationId",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"locationId": id},
            dataType: "json",
            success: function (data) {
                chanpins = data;
            }
        });
        for (var i = 0; i < chanpins.length; i++) {
            result.push(chanpins[i].id);
        }
        var ids = result.join("-");
        window.parent.document.getElementById("pageId3").name = ids;
        window.parent.document.getElementById("aa2").src = '../user/showDetail3';

    }
</script>

</body>
</html>