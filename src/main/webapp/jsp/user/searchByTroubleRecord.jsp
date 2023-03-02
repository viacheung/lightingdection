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
    <script type="text/javascript" src="../static/Echarts/dist/echarts.min.js"></script>
    <script type="text/javascript" src="../static/layui/layui.js"></script>
    <script type="text/javascript" src="../static/layui/layui.all.js"></script>
</head>
<style>

</style>

<body>
<div id="search" class="demoTable" style="position:fixed;margin-top:0.1%;height:6%;width:100%;">
    <div style="height:100%;float:left;margin-left:15%;display: flex;vertical-align:middle;">
        <select id="location" style="height:60%;width:100%;align-self: center;" onchange="changeCondition1(this.value)">
            <option value="">请选择区域</option>
        </select>
    </div>
    <div style="height:100%;float:left;display: flex;margin-left:2%">
        <select id="leibie" style="height:60%;width:100%;align-self: center;" onchange="changeCondition2(this.value)">
            <option value="">请选择产品类别</option>
        </select>
    </div>
    <div style="height:100%;float:left;display: flex;margin-left:2%">
        <select id="chanpin" style="height:60%;width:100%;align-self: center;" onchange="changeCondition3(this.value)">
            <option value="">请选择产品</option>
        </select>
    </div>
    <div style="height:100%;float:left;display: flex;margin-left:2%">
        <select id="datameaning" style="height:60%;width:100%;align-self: center;">
            <option value="">请选择查询信息</option>
        </select>
    </div>
    <div style="height:100%;float:left;display: flex;margin-left:2%">
        <input type="text" class="layui-input" placeholder="开始时间" id="startDate"
               style="height:53%;width:100%;align-self: center;" placeholder="起始时间">
    </div>
    <div style="height:100%;float:left;display: flex;margin-left:0.5%">
        <input type="text" class="layui-input" placeholder="结束时间" id="endDate"
               style="height:53%;width:100%;align-self: center;" placeholder="结束时间">
    </div>
    <div style="height:100%;float:left;margin-left:2%;display: flex;">
        <button type="button" class="layui-btn" data-type="reload" style="align-self: center;">搜索</button>
        <button type="file" lay-submit="" class="layui-btn layui-btn-normal" id="exportExcel"
                style="align-self: center;" onclick=download1()>
            <i class="layui-icon"></i>导出Excel
        </button>
        <button type="button" lay-submit="" class="layui-btn layui-btn-warm" id="exportPdf" style="align-self: center;"
                onclick=download2()>
            <i class="layui-icon"></i>导出PDF
        </button>
    </div>
</div>
<div style="position:fixed; width:100%;height:94.6%;margin-top:2.5%;overflow-y:scroll;overflow-x:hidden;">
    <table class="layui-hide" id="demo" lay-filter="demo" style="overflow:auto;"></table>
    </table>
</div>
<script>
    init();

    function init() {
        //var locations=normal.getAllLocations();
        var locations = null;
        $.ajax({
            url: "<%=basePath%>user/getAllLocations",
            type: "post",
            async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {},
            dataType: "json",
            success: function (data) {
                locations = data;
            }
        });
        if (locations != null) {
            //html1+=""
            for (var i = 0; i < locations.length; i++) {
                //先创建好select里面的option元素
                var option = document.createElement("option");
                //转换DOM对象为JQ对象,好用JQ里面提供的方法 给option的value赋值
                $(option).val(locations[i].id);
                //给option的text赋值,这就是你点开下拉框能够看到的东西
                $(option).text(locations[i].name);
                //获取select 下拉框对象,并将option添加进select
                $('#location').append(option);

            }
        }

    }

    function changeCondition1(can) {
        //$("#location").empty();
        $("#leibie").empty();
        $("#chanpin").empty();
        $("#datameaning").empty();
        var str = can;
        //var liebie=normal.getAllLeibies(str);
        var liebie = null;
        $.ajax({
            url: "<%=basePath%>user/getAllLeibies",
            type: "post",
            async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {
                "locationId": str
            },
            dataType: "json",
            success: function (data) {
                liebie = data;
            }
        });

        var option1 = document.createElement("option");
        //转换DOM对象为JQ对象,好用JQ里面提供的方法 给option的value赋值
        $(option1).val("");
        //给option的text赋值,这就是你点开下拉框能够看到的东西
        $(option1).text("请选择产品类别");
        //获取select 下拉框对象,并将option添加进select
        $('#leibie').append(option1);

        var option2 = document.createElement("option");
        //转换DOM对象为JQ对象,好用JQ里面提供的方法 给option的value赋值
        $(option2).val("");
        //给option的text赋值,这就是你点开下拉框能够看到的东西
        $(option2).text("请选择产品");
        //获取select 下拉框对象,并将option添加进select
        $('#chanpin').append(option2);

        var option3 = document.createElement("option");
        //转换DOM对象为JQ对象,好用JQ里面提供的方法 给option的value赋值
        $(option3).val("");
        //给option的text赋值,这就是你点开下拉框能够看到的东西
        $(option3).text("请选择查询信息");
        //获取select 下拉框对象,并将option添加进select
        $('#datameaning').append(option3);
        if (liebie != null) {

            //html1+=""
            for (var i = 0; i < liebie.length; i++) {
                //先创建好select里面的option元素
                var option = document.createElement("option");
                //转换DOM对象为JQ对象,好用JQ里面提供的方法 给option的value赋值
                $(option).val(liebie[i].id);
                //给option的text赋值,这就是你点开下拉框能够看到的东西
                $(option).text(liebie[i].name);
                //获取select 下拉框对象,并将option添加进select
                $('#leibie').append(option);

            }

        }


    }

    function changeCondition2(can) {
        //$("#location").empty();
        //$("#leibie").empty();
        $("#chanpin").empty();
        $("#datameaning").empty();
        var str = can;
        var select1 = document.getElementById("location");
        var index1 = select1.selectedIndex;
        var value = select1.options[index1].value;

        //var chanpin=normal.getAllChanpin(value,str);
        var chanpin = null;
        $.ajax({
            url: "<%=basePath%>user/getAllChanpin",
            type: "post",
            async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {
                "locationId": value,
                "leibieId": str
            },
            dataType: "json",
            success: function (data) {
                chanpin = data;
            }
        });

        var option2 = document.createElement("option");
        //转换DOM对象为JQ对象,好用JQ里面提供的方法 给option的value赋值
        $(option2).val("");
        //给option的text赋值,这就是你点开下拉框能够看到的东西
        $(option2).text("请选择产品");
        //获取select 下拉框对象,并将option添加进select
        $('#chanpin').append(option2);

        var option3 = document.createElement("option");
        //转换DOM对象为JQ对象,好用JQ里面提供的方法 给option的value赋值
        $(option3).val("");
        //给option的text赋值,这就是你点开下拉框能够看到的东西
        $(option3).text("请选择查询信息");
        //获取select 下拉框对象,并将option添加进select
        $('#datameaning').append(option3);
        if (chanpin != null) {

            //html1+=""
            for (var i = 0; i < chanpin.length; i++) {
                //先创建好select里面的option元素
                var option = document.createElement("option");
                //转换DOM对象为JQ对象,好用JQ里面提供的方法 给option的value赋值
                $(option).val(chanpin[i].id);
                //给option的text赋值,这就是你点开下拉框能够看到的东西
                $(option).text(chanpin[i].name);
                //获取select 下拉框对象,并将option添加进select
                $('#chanpin').append(option);

            }

        }


    }

    function changeCondition3(can) {
        //$("#location").empty();
        //$("#leibie").empty();
        //$("#chanpin").empty();
        $("#datameaning").empty();
        var str = can;
        var value1 = $("#location").find("option:selected").val();
        var value2 = $("#leibie").find("option:selected").val();
        //var index1=select1.selectedIndex;
        //var value1=select1.options[index1].value;

        //var select2=document.getElementById("leibie");
        //var index2=select2.selectedIndex;
        //var value2=select2.options[index1].value;

        //var datas=normal.getAllBaojingCanshu(value1,value2,str);
        var datas = null;
        $.ajax({
            url: "<%=basePath%>user/getAllCanshu",
            type: "post",
            async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {
                "locationId": value1,
                "leibieId": value2,
                "chanpinId": str
            },
            dataType: "json",
            success: function (data) {
                datas = data;
            }
        });

        var option3 = document.createElement("option");
        //转换DOM对象为JQ对象,好用JQ里面提供的方法 给option的value赋值
        $(option3).val("");
        //给option的text赋值,这就是你点开下拉框能够看到的东西
        $(option3).text("请选择查询信息");
        //获取select 下拉框对象,并将option添加进select
        $('#datameaning').append(option3);
        if (datas != null) {

            //html1+=""
            for (var i = 0; i < datas.length; i++) {
                //先创建好select里面的option元素
                var option = document.createElement("option");
                //转换DOM对象为JQ对象,好用JQ里面提供的方法 给option的value赋值
                $(option).val(datas[i].id);
                //给option的text赋值,这就是你点开下拉框能够看到的东西
                $(option).text(datas[i].name);
                //获取select 下拉框对象,并将option添加进select
                $('#datameaning').append(option);

            }

        }


    }

    function download1() {
        //var link = document.createElement('a');
        //  $.messager.alert("提示","1312", 'error');
        var chanpinId = $("#chanpin").find("option:selected").val();
        var datameaningId = $("#datameaning").find("option:selected").val();
        //var locationId=$("#location").find("option:selected").val();
        //var leibieId=$("#leibie").find("option:selected").val();
        var startDate = $('#startDate').val();
        var endDate = $('#endDate').val();
        //var link = document.createElement('a');
        //var a = document.getElementById("exportExcel");
        window.location.href = "${pageContext.request.contextPath}/user/exportExcel2?chanpinId=" + chanpinId + "&datameaningId=" + datameaningId + "&startDate=" + startDate + "&endDate=" + endDate;
        //link.click();
    }

    function download2() {
        //var link = document.createElement('a');
        //  $.messager.alert("提示","1312", 'error');
        var chanpinId = $("#chanpin").find("option:selected").val();
        var datameaningId = $("#datameaning").find("option:selected").val();
        //var locationId=$("#location").find("option:selected").val();
        //var leibieId=$("#leibie").find("option:selected").val();
        var startDate = $('#startDate').val();
        var endDate = $('#endDate').val();
        //var link = document.createElement('a');
        //var a = document.getElementById("exportExcel");
        window.location.href = "${pageContext.request.contextPath}/user/exportPdf2?chanpinId=" + chanpinId + "&datameaningId=" + datameaningId + "&startDate=" + startDate + "&endDate=" + endDate;
        //link.click();
    }
</script>
<script>
    layui.use(['table', 'form', 'layer', 'laydate'], function () {
        var table = layui.table;
        var layer = layui.layer;
        var laydate = layui.laydate;
        var $ = layui.jquery;
        laydate.render({
            elem: '#startDate' //指定元素
            ,
            type: 'datetime'
        });
        laydate.render({
            elem: '#endDate' //指定元素
            ,
            type: 'datetime'
        });
        //方法渲染
        var tableIns = table.render({
            elem: '#demo', //绑定table表格
            height: 'full-60',
            url: '${pageContext.request.contextPath}/user/showTroubleRecord' //数据接口
            ,
            page: true //开启分页
            ,
            cols: [
                [ //表头
                    {
                        type: 'numbers',
                        title: '序号',
                        fixed: 'left',
                        width: '3.3%'
                    }, {
                    field: 'chanpinId',
                    title: '产品Id',
                    hide: true
                }, {
                    field: 'datameaningId',
                    title: '解析Id',
                    hide: true
                }, {
                    field: 'locationName',
                    title: '区域',
                    width: '16%',
                    sort: true,
                    fixed: 'left'
                }, {
                    field: 'leibieName',
                    title: '类别',
                    width: '16%',
                    sort: true
                }, {
                    field: 'chanpinName',
                    title: '产品',
                    width: '16%',
                    sort: true
                }, {
                    field: 'name',
                    title: '监测项',
                    width: '16%',
                    sort: true
                }, {
                    field: 'value',
                    title: '监测值',
                    width: '16%',
                    sort: true
                }, {
                    field: 'time',
                    title: '时间',
                    width: '16%',
                    sort: true
                }
                ]
            ],
            id: 'testReload'
        });

        //数据表格重载（模糊查询）
        var $ = layui.$,
            active = {
                reload: function () {
                    var chanpinId = $("#chanpin").find("option:selected").val();
                    var datameaningId = $("#datameaning").find("option:selected").val();
                    var locationId = $("#location").find("option:selected").val();
                    var leibieId = $("#leibie").find("option:selected").val();
                    var startDate = $('#startDate').val();
                    var endDate = $('#endDate').val();
                    //layer.alert(chanpinId);
                    //执行重载
                    table.reload('testReload', {
                        page: true,
                        url: '${pageContext.request.contextPath}/user/findTroubleRecord',
                        method: 'post',
                        where: {
                            "locationId": locationId,
                            "leibieId": leibieId,
                            "chanpinId": chanpinId,
                            "datameaningId": datameaningId,
                            "startDate": startDate,
                            "endDate": endDate
                        } //把参数传入到后台
                    });
                }
            };
        $('.demoTable .layui-btn').on('click', function () {
            var type = $(this).data('type')
            active[type] ? active[type].call(this) : '';
        });

    });
</script>

</body>

</html>