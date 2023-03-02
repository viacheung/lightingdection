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
    <style>
        * {
            margin: 0;
            padding: 0;
        }

        html,
        body {
            width: 100%;
            height: 100%;
            position: fixed;
        }

        .head {
            height: 1%;
            width: 100%;
            background-color: white;
            text-align: center;
            position: relative;
            border: none;
        }

        .searchBox {
            height: 5%;
            width: 100%;
            background-color: white;
            display: inline-block;
            position: relative;
            border: none;
        }

        .out {
            height: 5%;
            width: 100%;
            background-color: white;
            display: inline-block !important;
            position: relative;
            border: none;
        }

        .fuzhu {
            height: 100%;
            width: 30%;
            text-align: center;
            background-color: white;
            border: none;
        }

        .dateRange {
            height: 100%;
            width: 100%;
            background-color: white;
            display: none;
            border: none;
        }

        .searchResultShow {
            height: 91%;
            width: 100%;
            background-color: white;
            text-align: center;
            overflow: auto;
            border: none;
            border: none;
        }

        /* .searchBox input {
width: 400px;
height: 30px;
border: none;
/* 输入时显示的外框 */
        /* outline: none;
border-bottom: 1px solid black;
} */

        .dateRange input {
            display: none;
        }
    </style>
</head>

<body>

<div class="head"></div>
<div class="searchBox" id="searchBox">
                    <span style="height:80%;display:inline-block;width:100%;">
    
   
    <select name="datatype" id="datatype" style="height:100%;width:10%;margin-left:22%">
      <option value="null" selected disabled="disabled">请选择数据类型</option>
      <option value="实时数据">实时数据</option>
        <!-- 改变下方颜色为红色 -->
      <option value="历史数据">历史数据</option>
        <!-- 改变下方颜色为绿色 -->
    </select>
   
        <select name="Protype" id="Protype" style="height:100%;width:10%;margin-left:2%">
            <option value="null" selected>全部</option>
              <option value="区域">按区域筛选</option>
              <option value="类型">按产品类型筛选</option>
            </select> 
  
    <select name="select2" id="select2" style="height:100%;width:10%;margin-left:2%">
      <option value="null" selected>全部</option>
    </select> 
    <button type="reset" class="layui-btn layui-btn-warm  layui-btn-sm" style="margin-left:2%">重置</button>
    <button type="submit" id="sure" class="layui-btn layui-btn-normal  layui-btn-sm">确定</button>
    <input type="search" style="height:100%;width:10%;margin-left:2%" name="" id="idse" value='输入产品地址码查询'
           onClick="if(this.value=='输入产品地址码查询')this.value=''">
    <button id='search' class="layui-btn  layui-btn-sm ">搜索</button></span>
</div>
<div class="out">

    <div class='dateRange'>
        <input type="text" class="layui-input" placeholder="开始时间" id="startTime"
               style="height:70%;width:10%;margin-left:22%">--
        <input type="text" class="layui-input" placeholder="结束时间" id="endTime" style="height:70%;width:10%;">
    </div>
</div>
<div class="searchResultShow" id="searchResultShow">

</div>
<script type="text/javascript">
    layui.use(['laydate'], function () {
        var laydate = layui.laydate;
        var $ = layui.jquery;
        laydate.render({
            elem: '#startTime' //指定元素
            ,
            type: 'datetime'
        });
        laydate.render({
            elem: '#endTime' //指定元素
            ,
            type: 'datetime'
        });
    });

    function addEventHandle(idSelector, callFunc) {
        var o = document.getElementById(idSelector);
        if (!o) return;
        (function () {
            o.onclick = function () {
                var evt = window.event;
                var selectObj = evt ? evt.srcElement : null;
                // IE Only
                if (evt && selectObj && evt.offsetY && evt.button != 2 && (evt.offsetY > selectObj.offsetHeight || evt.offsetY < 0)) {
                    setTimeout(function () {
                        var option = selectObj.options[selectObj.selectedIndex];
                        callFunc(option)
                    }, 60);
                }
            }
        })();
    }

    /**
     * 回调函数
     * @param option
     */
    function clickFunc(option) {
        var val = option.getAttribute('value');
        // console.log(val);
        if (val === '实时数据') {
            // 选择实时数据需要进行的处理
            // show.style.backgroundColor = 'red';
            // show.innerHTML = '选择实时数据这里就会立马获取并显示全部的实时数据';
            p.style.display = 'none';
        } else if (val === '历史数据') {
            // 选择历史数据需要进行的处理
            //  show.style.backgroundColor = 'green';
            p.style.display = 'inline-block';
            // confirm('请选择您要查看的时间范围');
            //show.innerHTML = '选择历史数据等选择完日期范围并确认这里才显示全部的实时数据';
            for (let i = 0; i < pchildren.length; i++) {
                pchildren[i].style.display = 'inline';
            }
        } else if (val === '区域') {
            //show.innerHTML = '区域';
            var allLocations = null;
            $.ajax({
                url: "<%=basePath%>user/getAllLocations",
                type: "post",
                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {},
                dataType: "json",
                success: function (data) {
                    allLocations = data;
                }
            });
            $('#select2').empty();
            var option1 = document.createElement("option");
            //转换DOM对象为JQ对象,好用JQ里面提供的方法 给option的value赋值
            $(option1).val('0');
            //给option的text赋值,这就是你点开下拉框能够看到的东西
            $(option1).text("全部");
            $('#select2').append(option1);

            for (var i = 0; i < allLocations.length; i++) {
                //先创建好select里面的option元素
                var option = document.createElement("option");
                //转换DOM对象为JQ对象,好用JQ里面提供的方法 给option的value赋值
                $(option).val(allLocations[i].id);
                //给option的text赋值,这就是你点开下拉框能够看到的东西
                $(option).text(allLocations[i].name);
                //获取select 下拉框对象,并将option添加进select
                $('#select2').append(option);

            }
        } else if (val === '类型') {
            //show.innerHTML = '区域';
            var allLeibies = null;
            $.ajax({
                url: "<%=basePath%>user/getAllChanpinLeibies",
                type: "post",
                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {},
                dataType: "json",
                success: function (data) {
                    allLeibies = data;
                }
            });
            $('#select2').empty();
            var option1 = document.createElement("option");
            //转换DOM对象为JQ对象,好用JQ里面提供的方法 给option的value赋值
            $(option1).val('0');
            //给option的text赋值,这就是你点开下拉框能够看到的东西
            $(option1).text("全部");
            $('#select2').append(option1);

            for (var i = 0; i < allLeibies.length; i++) {
                //先创建好select里面的option元素
                var option = document.createElement("option");
                //转换DOM对象为JQ对象,好用JQ里面提供的方法 给option的value赋值
                $(option).val(allLeibies[i].id);
                //给option的text赋值,这就是你点开下拉框能够看到的东西
                $(option).text(allLeibies[i].name);
                //获取select 下拉框对象,并将option添加进select
                $('#select2').append(option);

            }
        } else if (val === 'null') {
            $('#select2').empty();
            var option1 = document.createElement("option");
            //转换DOM对象为JQ对象,好用JQ里面提供的方法 给option的value赋值
            $(option1).val('0');
            //给option的text赋值,这就是你点开下拉框能够看到的东西
            $(option1).text("全部");
            $('#select2').append(option1);
        }
    }

    var show = document.querySelector('.searchResultShow');
    var p = document.querySelector('.dateRange');
    var pchildren = p.querySelectorAll('input');

    // console.log(pchildren);
    window.onload = function () {
        //给selector绑定option点击

        addEventHandle("datatype", clickFunc);
        addEventHandle("Protype", clickFunc);
    }


    // 获取搜索按钮并添加事件
    var search = document.querySelector('#search');
    var sure = document.querySelector('#sure');
    var searchInput = document.querySelector('#idse');
    var startTime = document.querySelector('#startTime');
    var endTime = document.querySelector('#endTime');

    search.addEventListener('click', function () {
        //show.innerHTML = '搜索的产品是' + searchInput.value;
        var datatype = $("#datatype").find("option:selected").val();
        var startDate = document.getElementById('startTime').value;
        var endDate = document.getElementById('endTime').value;
        var slaveId = searchInput.value;
        if ("undefined" != typeof shua) {
            window.clearInterval(shua);
        }
        $("#searchResultShow").html("");
        if (datatype == 'null') {
            confirm('请选择您要查看的数据类型！');
        } else {
            var chanpins = null;
            $.ajax({
                url: "<%=basePath%>user/getChanpinBySlaveId",
                type: "post",
                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {
                    "slaveId": slaveId
                },
                dataType: "json",
                success: function (data) {
                    chanpins = data;
                }
            });
            if (chanpins != null) {

                var result1 = [];
                for (var c = 0; c < chanpins.length; c++) {
                    result1.push(chanpins[c].id);
                }
                var aid = result1.join("-");
                //var aid=window.parent.document.getElementById("pageId3").name;
                //var aids=aid.split("-");
                //$.messager.alert("",aid,"");
                var recordShows = null; //初始化当前所有的需要动态刷新的产品
                $.ajax({
                    url: "<%=basePath%>user/recordShowInit",
                    type: "post",
                    async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {
                        "aids": aid
                    },
                    dataType: "json",
                    success: function (data) {
                        recordShows = data;
                    }
                });
                tableInit(chanpins);
                if (datatype == '历史数据') {

                    historyTable(chanpins, startDate, endDate);

                } else {
                    //ini();
                    shua = window.setInterval(function () {
                        shuaxin2(recordShows, chanpins)
                    }, 1000);

                }
            }

        }
    });
    sure.addEventListener('click', function () {
        //show.innerHTML = '搜索的产品是' + searchInput.value;
        var datatype = $("#datatype").find("option:selected").val();
        var Protype = $("#Protype").find("option:selected").val();
        var select2 = $("#select2").find("option:selected").val();
        var startDate = document.getElementById('startTime').value;
        var endDate = document.getElementById('endTime').value;
        if ("undefined" != typeof shua) {
            window.clearInterval(shua);
        }
        $("#searchResultShow").html("");
        if (datatype == 'null') {
            confirm('请选择您要查看的数据类型！');
        } else {
            var chanpins = null;
            $.ajax({
                url: "<%=basePath%>user/getAllChanpins",
                type: "post",
                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {
                    "Protype": Protype,
                    "select2": select2
                },
                dataType: "json",
                success: function (data) {
                    chanpins = data;
                }
            });
            if (chanpins != null) {
                var result1 = [];
                for (var c = 0; c < chanpins.length; c++) {
                    result1.push(chanpins[c].id);
                }
                var aid = result1.join("-");
                //var aid=window.parent.document.getElementById("pageId3").name;
                //var aids=aid.split("-");
                //$.messager.alert("",aid,"");
                var recordShows = null; //初始化当前所有的需要动态刷新的产品
                $.ajax({
                    url: "<%=basePath%>user/recordShowInit",
                    type: "post",
                    async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {
                        "aids": aid
                    },
                    dataType: "json",
                    success: function (data) {
                        recordShows = data;
                    }
                });
                tableInit(chanpins);
                if (datatype == '历史数据') {

                    historyTable(chanpins, startDate, endDate);

                } else {
                    //ini();
                    shua = window.setInterval(function () {
                        shuaxin2(recordShows, chanpins)
                    }, 1000);

                }
            }
        }
    });

    function tableInit(chanpins) {
        var htmllist = "";
        var dataty = $("#datatype").find("option:selected").val();
        for (var i = 0; i < chanpins.length; i++) { //产品遍历开始
            var realId = chanpins[i].id; //获得的产品的id
            var chanpin = null;
            $.ajax({
                url: "<%=basePath%>user/getChanpinById",
                type: "post",
                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {
                    id: realId
                },
                dataType: "json",
                success: function (data) {
                    chanpin = data;
                }
            });
            //var leibie=normal.getChanpinById(chanpin.getPid());//获得对应的产品类别
            var leibie = null;
            var cpid = chanpin.pid;
            $.ajax({
                url: "<%=basePath%>user/getChanpinById",
                type: "post",
                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {
                    id: cpid
                },
                dataType: "json",
                success: function (data) {
                    leibie = data;
                }
            });
            // var rejisters=normal.getAllInputRejister(leibie.getId());//获得所有的输入寄存器
            var rejisters = null;
            var leiid = leibie.id;
            //$.messager.alert("","leiid: "+leiid,"");
            $.ajax({
                url: "<%=basePath%>user/getAllInputRejister",
                type: "post",
                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {
                    chanpinLeibieId: leiid
                },
                dataType: "json",
                success: function (data) {
                    rejisters = data;
                }
            });

            var reid = rejisters[0].id; //用户获取展示数据的寄存器id
            var disData = null;
            $.ajax({
                url: "<%=basePath%>user/getDisplayData",
                type: "post",
                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {
                    "chanpinId": realId,
                    "registerId": reid
                },
                dataType: "json",
                success: function (data) {
                    disData = data;
                }
            });
            htmllist += "<div style='width:98%;height:25em ;margin-left:1%;margin-top:0.5%;'><table style='height:100%;width:100%;'><tr>";
            var lenn = 0;
            for (var k = 0; k < disData.length; k++) {
                var sons = disData[k].sons;
                for (var o = 0; o < sons.length; o++) {
                    if (sons[o].isShown == 1 && sons[o].isPaint == 1) {

                        lenn++;
                    }
                }
            }
            var width = Math.floor(100 / lenn);
            //console.log(width);
            for (var k = 0; k < disData.length; k++) {
                var sons = disData[k].sons;
                for (var o = 0; o < sons.length; o++) {
                    if (sons[o].isShown == 1 && sons[o].isPaint == 1) {
                        if (dataty == "历史数据") {
                            htmllist += "<td   style = 'width:" + width + "%;height:100%;' ><div id='" + chanpin.id + "a" + sons[o].id + "' style = 'width:100%;height:85%;'>";
                            htmllist += "</div> <div id='' style = 'width:100%;height:15%;'>"
                            htmllist += "<button onclick='yearView(this)' id= 'first" + chanpin.id + "a" + sons[o].id + "' class='layui-btn layui-btn-primary layui-btn-radius'>按年查看</button>";
                            htmllist += "<button onclick='monthView(this)' id= 'second" + chanpin.id + "a" + sons[o].id + "' class='layui-btn layui-btn-primary layui-btn-radius'>按月查看</button>";
                            htmllist += "<button onclick='dayView(this)' id= 'third" + chanpin.id + "a" + sons[o].id + "' class='layui-btn layui-btn-primary layui-btn-radius'>按日查看</button>";
                            htmllist += "<button onclick='defaultViews(this)' id= 'fourth" + chanpin.id + "a" + sons[o].id + "' class='layui-btn layui-btn-warm layui-btn-radius'>重置</button></div></td>";
                        } else {
                            htmllist += "<td   id = '" + chanpin.id + "b" + sons[o].id + "' style = 'width:" + width + "%;height:100%;'></td>";
                        }

                    }
                }
            }

            htmllist += "</tr></table></div><br>"
        } //产品遍历结束
        document.getElementById("searchResultShow").innerHTML = htmllist;
    }


    //按照年份查看数据
    function yearView(pram) {
        //console.log(pram)
        var str = pram.id;
        var echartDivId = str.match(/first(\S*)/)[1];
        var chanpinId = str.match(/first(\S*)a/)[1];
        var datameaningId = str.match(/a(\S*)/)[1];
        var datas = null;
        var start = document.getElementById('startTime').value;
        var end = document.getElementById('endTime').value;
        $.ajax({
            url: "<%=basePath%>user/getChanAndDataShai",
            type: "post",
            async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {
                "type": "1",
                "chanpinId": chanpinId,
                "datameaningId": datameaningId,
                "startDate": start,
                "endDate": end
            },
            dataType: "json",
            success: function (data) {
                datas = data;
            }
        });

        var sonDatas = datas.sonData;
        var year = [];
        var list = [{
            name: '年内最小值',
            type: 'bar',
            value: []
        }, {
            name: '年内平均值',
            type: 'bar',
            value: []
        }, {
            name: '年内最大值',
            type: 'bar',
            value: []
        }];
        for (var i = 0; i < sonDatas.length; i++) {
            list[0].value.push(sonDatas[i].minData);
            list[1].value.push(sonDatas[i].aveData);
            list[2].value.push(sonDatas[i].maxData);
            year.push(sonDatas[i].time + "年")
        }
        //document.getElementById(echartDivId).innerHTML="";
        var mychart = echarts.init(document.getElementById(echartDivId));

        var option = {
            title: {
                text: "按年查看的历史图" //chanpins[i].name + " " + leibie.name + " 历史数据",
            },
            tooltip: {
                show: true,
                trigger: 'axis'
            },

            toolbox: {
                show: true,
                orient: 'horizontal',
                left: 'right',
                top: 'top',
                color: ['#1e90ff', '#22bb22', '#4b0082', '#d2691e'],
                backgroundColor: 'rgba(0,0,0,0)', // 工具箱背景颜色
                borderColor: '#ccc', // 工具箱边框颜色
                borderWidth: 0, // 工具箱边框线宽
                padding: 5, // 工具箱内边距
                showTitle: true,
                feature: {
                    mark: {
                        show: true,
                        title: {
                            mark: '辅助线-开关',
                            markUndo: '辅助线-删除',
                            markClear: '辅助线-清空'
                        },
                        lineStyle: {
                            width: 1,
                            color: '#1e90ff',
                            type: 'dashed'
                        }
                    },
                    dataZoom: {
                        show: true,
                        title: '数据视图',
                        readOnly: true,
                    },
                    dataView: {
                        show: true,
                        readOnly: false
                    },
                    magicType: {
                        show: true,
                        title: {
                            line: '动态类型切换-折线图',
                            tiled: '动态类型切换-平铺'
                        },
                        type: ['line', 'tiled']
                    },
                    restore: {
                        show: true,
                        title: '还原',
                        color: 'black'
                    },
                    saveAsImage: {
                        show: true,
                        title: '保存为图片',
                        type: 'jpeg',
                        lang: ['点击本地保存']
                    }
                }
            },
            calculable: true,
            xAxis: [{
                type: 'category',
                data: year,
                boundaryGap: true,
                axisLabel: {
                    interval: 'auto'
                }


            }],
            yAxis: [{
                type: 'value',

            }],
            series: [{
                name: list[0].name,
                type: 'bar',
                data: list[0].value
            }, {
                name: list[1].name,
                type: 'bar',
                data: list[1].value
            }, {
                name: list[2].name,
                type: 'bar',
                data: list[2].value
            }]
        }
        mychart.setOption(option);
    }

    //按照月份份查看数据
    function monthView(pram) {
        //console.log(pram)
        var str = pram.id;
        var echartDivId = str.match(/second(\S*)/)[1];
        var chanpinId = str.match(/second(\S*)a/)[1];
        var datameaningId = str.match(/a(\S*)/)[1];
        var datas = null;
        var start = document.getElementById('startTime').value;
        var end = document.getElementById('endTime').value;
        $.ajax({
            url: "<%=basePath%>user/getChanAndDataShai",
            type: "post",
            async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {
                "type": "2",
                "chanpinId": chanpinId,
                "datameaningId": datameaningId,
                "startDate": start,
                "endDate": end
            },
            dataType: "json",
            success: function (data) {
                datas = data;
            }
        });

        var sonDatas = datas.sonData;
        // console.log(sonDatas)
        var month = [];
        var list = [{
            name: '月内最小值',

            value: []
        }, {
            name: '月内平均值',
            value: []
        }, {
            name: '月内最大值',
            value: []
        }];
        for (var i = 0; i < sonDatas.length; i++) {
            list[0].value.push(sonDatas[i].minData);
            list[1].value.push(sonDatas[i].aveData);
            list[2].value.push(sonDatas[i].maxData);
            month.push(sonDatas[i].time)
        }
        // console.log(list
        // 		)
        //document.getElementById(echartDivId).innerHTML="";
        var mychart = echarts.init(document.getElementById(echartDivId));

        var option = {
            title: {
                text: "按月查看的历史图" //chanpins[i].name + " " + leibie.name + " 历史数据",
            },
            tooltip: {
                show: true,
                trigger: 'axis'
            },

            toolbox: {
                show: true,
                orient: 'horizontal',
                left: 'right',
                top: 'top',
                color: ['#1e90ff', '#22bb22', '#4b0082', '#d2691e'],
                backgroundColor: 'rgba(0,0,0,0)', // 工具箱背景颜色
                borderColor: '#ccc', // 工具箱边框颜色
                borderWidth: 0, // 工具箱边框线宽
                padding: 5, // 工具箱内边距
                showTitle: true,
                feature: {
                    mark: {
                        show: true,
                        title: {
                            mark: '辅助线-开关',
                            markUndo: '辅助线-删除',
                            markClear: '辅助线-清空'
                        },
                        lineStyle: {
                            width: 1,
                            color: '#1e90ff',
                            type: 'dashed'
                        }
                    },
                    dataZoom: {
                        show: true,
                        title: '数据视图',
                        readOnly: true,
                    },
                    dataView: {
                        show: true,
                        readOnly: false
                    },
                    magicType: {
                        show: true,
                        title: {
                            line: '动态类型切换-折线图',
                            tiled: '动态类型切换-平铺'
                        },
                        type: ['line', 'tiled']
                    },
                    restore: {
                        show: true,
                        title: '还原',
                        color: 'black'
                    },
                    saveAsImage: {
                        show: true,
                        title: '保存为图片',
                        type: 'jpeg',
                        lang: ['点击本地保存']
                    }
                }
            },
            calculable: true,
            xAxis: [{
                type: 'category',
                data: month,
                boundaryGap: true,
                axisLabel: {
                    interval: 'auto'
                }


            }],
            yAxis: [{
                type: 'value',

            }],
            series: [{
                name: list[0].name,
                type: 'bar',
                data: list[0].value
            }, {
                name: list[1].name,
                type: 'bar',
                data: list[1].value
            }, {
                name: list[2].name,
                type: 'bar',
                data: list[2].value
            }]
        }
        mychart.setOption(option);
    }

    //按照日查看数据
    function dayView(pram) {
        //console.log(pram)
        var str = pram.id;
        var echartDivId = str.match(/third(\S*)/)[1];
        var chanpinId = str.match(/third(\S*)a/)[1];
        var datameaningId = str.match(/a(\S*)/)[1];
        var datas = null;
        var start = document.getElementById('startTime').value;
        var end = document.getElementById('endTime').value;
        $.ajax({
            url: "<%=basePath%>user/getChanAndDataShai",
            type: "post",
            async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {
                "type": "3",
                "chanpinId": chanpinId,
                "datameaningId": datameaningId,
                "startDate": start,
                "endDate": end
            },
            dataType: "json",
            success: function (data) {
                datas = data;
            }
        });
        var sonDatas = datas.sonData;
        // console.log(sonDatas)
        var day = [];
        var list = [{
            name: '日内最小值',

            value: []
        }, {
            name: '日内平均值',
            value: []
        }, {
            name: '日内最大值',
            value: []
        }];
        for (var i = 0; i < sonDatas.length; i++) {
            list[0].value.push(sonDatas[i].minData);
            list[1].value.push(sonDatas[i].aveData);
            list[2].value.push(sonDatas[i].maxData);
            day.push(sonDatas[i].time)
        }
        // console.log(list
        // 		)
        //document.getElementById(echartDivId).innerHTML="";
        var mychart = echarts.init(document.getElementById(echartDivId));
        var option = {
            title: {
                text: "按日查看的历史图" //chanpins[i].name + " " + leibie.name + " 历史数据",
            },
            tooltip: {
                show: true,
                trigger: 'axis'
            },

            toolbox: {
                show: true,
                orient: 'horizontal',
                left: 'right',
                top: 'top',
                color: ['#1e90ff', '#22bb22', '#4b0082', '#d2691e'],
                backgroundColor: 'rgba(0,0,0,0)', // 工具箱背景颜色
                borderColor: '#ccc', // 工具箱边框颜色
                borderWidth: 0, // 工具箱边框线宽
                padding: 5, // 工具箱内边距
                showTitle: true,
                feature: {
                    mark: {
                        show: true,
                        title: {
                            mark: '辅助线-开关',
                            markUndo: '辅助线-删除',
                            markClear: '辅助线-清空'
                        },
                        lineStyle: {
                            width: 1,
                            color: '#1e90ff',
                            type: 'dashed'
                        }
                    },
                    dataZoom: {
                        show: true,
                        title: '数据视图',
                        readOnly: true,
                    },
                    dataView: {
                        show: true,
                        readOnly: false
                    },
                    magicType: {
                        show: true,
                        title: {
                            line: '动态类型切换-折线图',
                            tiled: '动态类型切换-平铺'
                        },
                        type: ['line', 'tiled']
                    },
                    restore: {
                        show: true,
                        title: '还原',
                        color: 'black'
                    },
                    saveAsImage: {
                        show: true,
                        title: '保存为图片',
                        type: 'jpeg',
                        lang: ['点击本地保存']
                    }
                }
            },
            calculable: true,
            xAxis: [{
                type: 'category',
                data: day,
                boundaryGap: true,
                axisLabel: {
                    interval: 'auto'
                }


            }],
            yAxis: [{
                type: 'value',

            }],
            series: [{
                name: list[0].name,
                type: 'bar',
                data: list[0].value
            }, {
                name: list[1].name,
                type: 'bar',
                data: list[1].value
            }, {
                name: list[2].name,
                type: 'bar',
                data: list[2].value
            }]
        }
        mychart.setOption(option);
    }

    //按照默认视图查看数据
    function defaultViews(pram) {
        console.log("执行defaultViews");
        var str = pram.id;
        //var echartDivId = str.match(/third(\S*)/)[1];
        var chanId = str.match(/fourth(\S*)a/)[1];
        var dameanId = str.match(/a(\S*)/)[1];
        var datas = null;
        var start = document.getElementById('startTime').value;
        var end = document.getElementById('endTime').value;
        TableChartInit(chanId, dameanId, start, end);

    }

    function TableChartInit(chanId, dameanId, startDate, endDate) {
        var htmllist = "";
        var realId = chanId; //获得的产品的id
        var chanpin = null;
        $.ajax({
            url: "<%=basePath%>user/getChanpinById",
            type: "post",
            async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {
                id: realId
            },
            dataType: "json",
            success: function (data) {
                chanpin = data;
            }
        });
        var leibie = null;
        var cpid = chanpin.pid;
        $.ajax({
            url: "<%=basePath%>user/getChanpinById",
            type: "post",
            async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {
                id: cpid
            },
            dataType: "json",
            success: function (data) {
                leibie = data;
            }
        });
        var locati = null;
        var leipid = leibie.pid;
        $.ajax({
            url: "<%=basePath%>user/getChanpinById",
            type: "post",
            async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {
                id: leipid
            },
            dataType: "json",
            success: function (data) {
                locati = data;
            }
        });
        var rejisters = null;
        var leiid = leibie.id;
        $.ajax({
            url: "<%=basePath%>user/getAllInputRejister",
            type: "post",
            async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {
                chanpinLeibieId: leiid
            },
            dataType: "json",
            success: function (data) {
                rejisters = data;
            }
        });
        //开始进行数据截取的相关操作
        var recordShow = null; //获得折线图中所有需要被使用到的历史数据
        $.ajax({
            url: "<%=basePath%>user/getRecordShow1",
            type: "post",
            async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {
                "chanpinId": realId,
                "startDate": startDate,
                "endDate": endDate
            },
            dataType: "json",
            success: function (data) {
                recordShow = data;
            }
        });
        var reid = rejisters[0].id; //用户获取展示数据的寄存器id
        var disData = null;
        $.ajax({
            url: "<%=basePath%>user/getDisplayData",
            type: "post",
            async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {
                "chanpinId": realId,
                "registerId": reid
            },
            dataType: "json",
            success: function (data) {
                disData = data;
            }
        });


        for (var k = 0; k < disData.length; k++) {
            var sons = disData[k].sons;
            for (var o = 0; o < sons.length; o++) {
                if (sons[o].isShown == 1 && sons[o].isPaint == 1) { //判断是否展示部分开始
                    if (recordShow != null) {
                        var recordShow2s = recordShow.sons;
                        var recordShow2 = null;
                        for (var x = 0; x < recordShow2s.length; x++) {
                            if (recordShow2s[x].datameaningId == sons[o].id && recordShow2s[x].datameaningId == dameanId) {
                                recordShow2 = recordShow2s[x];
                            }
                        }
                        if (recordShow2 != null) { //填充开始
                            var recos = recordShow2.sons;
                            var dameaning = null;
                            $.ajax({
                                url: "<%=basePath%>user/getDatameaningById",
                                type: "post",
                                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                                data: {
                                    id: dameanId
                                },
                                dataType: "json",
                                success: function (data) {
                                    dameaning = data;
                                }
                            });
                            var day = [];
                            var list = [{
                                name: dameaning.name,
                                value: []
                            }, {
                                name: '预警值',
                                value: []
                            }, {
                                name: '报警值',
                                value: []
                            }];

                            for (var z = 0; z < recos.length; z++) {
                                list[0].value.push(recos[z].data);
                                day.push(recos[z].time)
                            }
                            if (recordShow2.isExistYujing == 1) {
                                for (var z = 0; z < recos.length; z++) {
                                    list[1].value.push(recos[z].yujingData);
                                }
                            }
                            if (recordShow2.isExistBaojing == 1) {
                                for (var z = 0; z < recos.length; z++) {
                                    list[2].value.push(recos[z].baojingData);
                                }
                            }
                            var mychart = echarts.init(document.getElementById(chanpin.id + 'a' + sons[o].id));
                            //var mychart = echarts.init(document.getElementById(echartDivId));
                            //mychart.resize()
                            mychart.xData = [];
                            mychart.YDATA = [];
                            var option = {
                                title: {
                                    text: locati.name + "(" + chanpin.name + ")" + leibie.name + " 历史数据",
                                },
                                tooltip: {
                                    show: true,
                                    trigger: 'axis'
                                },
                                toolbox: {
                                    show: true,
                                    orient: 'horizontal',
                                    left: 'right',
                                    top: 'top',
                                    color: ['#1e90ff', '#22bb22', '#4b0082', '#d2691e'],
                                    backgroundColor: 'rgba(0,0,0,0)', // 工具箱背景颜色
                                    borderColor: '#ccc', // 工具箱边框颜色
                                    borderWidth: 0, // 工具箱边框线宽
                                    padding: 5, // 工具箱内边距
                                    showTitle: true,
                                    feature: {
                                        mark: {
                                            show: true,
                                            title: {
                                                mark: '辅助线-开关',
                                                markUndo: '辅助线-删除',
                                                markClear: '辅助线-清空'
                                            },
                                            lineStyle: {
                                                width: 1,
                                                color: '#1e90ff',
                                                type: 'dashed'
                                            }
                                        },
                                        dataZoom: {
                                            show: true,
                                            title: '数据视图',
                                            readOnly: true,
                                        },
                                        dataView: {
                                            show: true,
                                            readOnly: false
                                        },
                                        magicType: {
                                            show: true,
                                            title: {
                                                line: '动态类型切换-折线图',
                                                tiled: '动态类型切换-平铺'
                                            },
                                            type: ['line', 'tiled']
                                        },
                                        restore: {
                                            show: true,
                                            title: '还原',
                                            color: 'black'
                                        },
                                        saveAsImage: {
                                            show: true,
                                            title: '保存为图片',
                                            type: 'jpeg',
                                            lang: ['点击本地保存']
                                        }
                                    }
                                },
                                calculable: true,
                                xAxis: [{
                                    type: 'category',
                                    data: day,
                                    boundaryGap: false,
                                    axisLabel: {
                                        interval: 'auto'
                                    }


                                }],
                                yAxis: [{
                                    type: 'value',

                                }],
                                series: [{
                                    name: list[0].name,
                                    type: 'line',
                                    symbol: 'none',
                                    smooth: true,
                                    data: list[0].value
                                }, {
                                    name: list[1].name,
                                    type: 'line',
                                    symbol: 'none',
                                    smooth: true,
                                    data: list[1].value
                                }, {
                                    name: list[2].name,
                                    type: 'line',
                                    symbol: 'none',
                                    smooth: true,
                                    data: list[2].value
                                }]
                            }
                            mychart.setOption(option);

                        } //填充结束
                    }
                } //判断是否展示部分结束
            }
        }


    }

    function historyTable(chanpins, startDate, endDate) {
        var htmllist = "";
        for (var i = 0; i < chanpins.length; i++) { //产品遍历开始
            var realId = chanpins[i].id; //获得的产品的id
            var chanpin = null;
            $.ajax({
                url: "<%=basePath%>user/getChanpinById",
                type: "post",
                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {
                    id: realId
                },
                dataType: "json",
                success: function (data) {
                    chanpin = data;
                }
            });
            var leibie = null;
            var cpid = chanpin.pid;
            $.ajax({
                url: "<%=basePath%>user/getChanpinById",
                type: "post",
                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {
                    id: cpid
                },
                dataType: "json",
                success: function (data) {
                    leibie = data;
                }
            });
            var locati = null;
            var leipid = leibie.pid;
            $.ajax({
                url: "<%=basePath%>user/getChanpinById",
                type: "post",
                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {
                    id: leipid
                },
                dataType: "json",
                success: function (data) {
                    locati = data;
                }
            });
            var rejisters = null;
            var leiid = leibie.id;
            $.ajax({
                url: "<%=basePath%>user/getAllInputRejister",
                type: "post",
                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {
                    chanpinLeibieId: leiid
                },
                dataType: "json",
                success: function (data) {
                    rejisters = data;
                }
            });
            //开始进行数据截取的相关操作
            var recordShow = null; //获得折线图中所有需要被使用到的历史数据
            $.ajax({
                url: "<%=basePath%>user/getRecordShow1",
                type: "post",
                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {
                    "chanpinId": realId,
                    "startDate": startDate,
                    "endDate": endDate
                },
                dataType: "json",
                success: function (data) {
                    recordShow = data;
                }
            });
            var reid = rejisters[0].id; //用户获取展示数据的寄存器id
            var disData = null;
            $.ajax({
                url: "<%=basePath%>user/getDisplayData",
                type: "post",
                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {
                    "chanpinId": realId,
                    "registerId": reid
                },
                dataType: "json",
                success: function (data) {
                    disData = data;
                }
            });

            for (var k = 0; k < disData.length; k++) {
                var sons = disData[k].sons;
                for (var o = 0; o < sons.length; o++) {
                    if (sons[o].isShown == 1 && sons[o].isPaint == 1) { //判断是否展示部分开始
                        if (recordShow != null) {
                            var recordShow2s = recordShow.sons;
                            var recordShow2 = null;
                            for (var x = 0; x < recordShow2s.length; x++) {
                                if (recordShow2s[x].datameaningId == sons[o].id) {
                                    recordShow2 = recordShow2s[x];
                                }
                            }
                            if (recordShow2 != null) { //填充开始
                                var mychart = echarts.init(document.getElementById(chanpin.id + 'a' + sons[o].id));
                                //var mychart1 = echarts.init(document.getElementById(chanpin.id+'b'+sons[o].id));
                                var option = {
                                    title: {
                                        text: locati.name + "(" + chanpins[i].name + ") " + leibie.name + " 历史数据",
                                    },
                                    tooltip: {
                                        show: true,
                                        trigger: 'axis'
                                    },

                                    toolbox: {
                                        show: true,
                                        orient: 'horizontal',
                                        left: 'right',
                                        top: 'top',
                                        color: ['#1e90ff', '#22bb22', '#4b0082', '#d2691e'],
                                        backgroundColor: 'rgba(0,0,0,0)', // 工具箱背景颜色
                                        borderColor: '#ccc', // 工具箱边框颜色
                                        borderWidth: 0, // 工具箱边框线宽
                                        padding: 5, // 工具箱内边距
                                        showTitle: true,
                                        feature: {
                                            mark: {
                                                show: true,
                                                title: {
                                                    mark: '辅助线-开关',
                                                    markUndo: '辅助线-删除',
                                                    markClear: '辅助线-清空'
                                                },
                                                lineStyle: {
                                                    width: 1,
                                                    color: '#1e90ff',
                                                    type: 'dashed'
                                                }
                                            },
                                            dataZoom: {
                                                show: true,
                                                title: '数据视图',
                                                readOnly: true,
                                            },
                                            dataView: {
                                                show: true,
                                                readOnly: false
                                            },
                                            magicType: {
                                                show: true,
                                                title: {
                                                    line: '动态类型切换-折线图',
                                                    tiled: '动态类型切换-平铺'
                                                },
                                                type: ['line', 'tiled']
                                            },
                                            restore: {
                                                show: true,
                                                title: '还原',
                                                color: 'black'
                                            },
                                            saveAsImage: {
                                                show: true,
                                                title: '保存为图片',
                                                type: 'jpeg',
                                                lang: ['点击本地保存']
                                            }
                                        }
                                    },
                                    calculable: true,


                                    xAxis: [{
                                        type: 'category',
                                        boundaryGap: false,
                                        axisLabel: {
                                            interval: 'auto'
                                        }


                                    }],
                                    yAxis: [{
                                        type: 'value'
                                    }],
                                    series: function () {
                                        var serie = [];
                                        //var recordShow2s=recordShow.sons;
                                        //var recordShow2=recordShow2s[0];
                                        //var recordShow2=recordShow2s[0];
                                        var datameaningId = recordShow2.datameaningId;
                                        //var name=normal.getDatameaningById(datameaningId).getName();
                                        var datamean = null;
                                        $.ajax({
                                            url: "<%=basePath%>user/getDatameaningById",
                                            type: "post",
                                            async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                                            data: {
                                                id: datameaningId
                                            },
                                            dataType: "json",
                                            success: function (data) {
                                                datamean = data;
                                            }
                                        });
                                        var name = datamean.name;
                                        var recordShow3s = recordShow2.sons;
                                        var arr = [];
                                        for (var j = 0; j < recordShow3s.length; j++) {
                                            var recordShow3 = recordShow3s[j];
                                            var item1 = [recordShow3.time, recordShow3.data];
                                            //var item2=[recordShow3.time,3];
                                            //var item3=[recordShow3.time,5];
                                            arr.push(item1);
                                            //arr.push(item2);
                                            //arr.push(item3);
                                        }
                                        var item = {
                                            name: name,
                                            type: 'line',
                                            //stack:'总量',
                                            symbol: 'none',
                                            smooth: true,
                                            data: arr
                                        };
                                        serie.push(item);

                                        if (recordShow2.isExistYujing == 1) {
                                            var arr1 = [];
                                            for (var j = 0; j < recordShow3s.length; j++) {
                                                var recordShow3 = recordShow3s[j];
                                                var item1 = [recordShow3.time, recordShow3.yujingData];
                                                //var item2=[recordShow3.time,3];
                                                //var item3=[recordShow3.time,5];
                                                arr1.push(item1);
                                                //arr.push(item2);
                                                //arr.push(item3);
                                            }
                                            var item1 = {
                                                name: name + "预警值",
                                                type: 'line',
                                                //stack:'总量',
                                                symbol: 'none',
                                                smooth: true,
                                                data: arr1
                                            };
                                            serie.push(item1);
                                        }

                                        if (recordShow2.isExistBaojing == 1) {
                                            var arr2 = [];
                                            for (var j = 0; j < recordShow3s.length; j++) {
                                                var recordShow3 = recordShow3s[j];
                                                var item1 = [recordShow3.time, recordShow3.baojingData];
                                                //var item2=[recordShow3.time,3];
                                                //var item3=[recordShow3.time,5];
                                                arr2.push(item1);
                                                //arr.push(item2);
                                                //arr.push(item3);
                                            }
                                            var item2 = {
                                                name: name + "报警值",
                                                type: 'line',
                                                //stack:'总量',
                                                symbol: 'none',
                                                smooth: true,
                                                data: arr2
                                            };
                                            serie.push(item2);
                                        }
                                        //}
                                        return serie;
                                    }()

                                };

                                mychart.setOption(option);

                            } //填充结束
                        }
                    } //判断是否展示部分结束
                }
            }

        } //产品遍历结束
    }

    function shuaxin2(recordShows, chanpins) {

        var result = [];
        for (var c = 0; c < chanpins.length; c++) {
            result.push(chanpins[c].id);
        }
        var aaid = result.join("-");
        //$.messager.alert("",aaid,"");
        //var aids=aid.split("-");
        //$.messager.alert("",aid,"");
        var recordShows1 = null; //初始化当前所有的需要动态刷新的产品
        $.ajax({
            url: "<%=basePath%>user/recordShowInit",
            type: "post",
            async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {
                "aids": aaid
            },
            dataType: "json",
            success: function (data) {
                recordShows1 = data;
            }
        });

        for (var i = 0; i < recordShows.length; i++) {
            var recordShow = recordShows[i];
            var recordShow1 = null;
            for (var j = 0; j < recordShows1.length; j++) {
                if (recordShows1[j].chanpinId == recordShow.chanpinId) {
                    recordShow1 = recordShows1[j];
                    break;
                }
            }
            var recordShow2 = recordShow.sons;
            var recordShow22 = recordShow1.sons;
            for (var j = 0; j < recordShow22.length; j++) {
                for (var k = 0; k < recordShow2.length; k++) {
                    if (recordShow2[k].datameaningId == recordShow22[j].datameaningId) {
                        var record3s = recordShow22[j].sons;
                        var record3 = record3s[0];
                        if (recordShows[i].sons[k].sons.length < 15) {
                            recordShows[i].sons[k].sons.push(record3);
                        } else {

                            recordShows[i].sons[k].sons.push(record3);
                            recordShows[i].sons[k].sons.shift(recordShows[i].sons[k].sons[0]);
                        }
                        //$.messager.alert("",recordShows[i].sons[k].sons.length,"");
                    }
                }
            } //遍历所有的datameaning
        }

        //刷新开始

        //var id=window.parent.document.getElementById("pageId3").name;
        ///  var ids=id.split("-");
        //  var length=ids.length;
        for (var i = 0; i < chanpins.length; i++) {
            var realId = chanpins[i].id; //获得的产品的id
            var chanpin = null;
            $.ajax({
                url: "<%=basePath%>user/getChanpinById",
                type: "post",
                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {
                    id: realId
                },
                dataType: "json",
                success: function (data) {
                    chanpin = data;
                }
            });
            var leibie = null;
            var cpid = chanpin.pid;
            $.ajax({
                url: "<%=basePath%>user/getChanpinById",
                type: "post",
                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {
                    id: cpid
                },
                dataType: "json",
                success: function (data) {
                    leibie = data;
                }
            });
            var locati = null;
            var leipid = leibie.pid;
            $.ajax({
                url: "<%=basePath%>user/getChanpinById",
                type: "post",
                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {
                    id: leipid
                },
                dataType: "json",
                success: function (data) {
                    locati = data;
                }
            });
            var rejisters = null;
            var leiid = leibie.id;
            $.ajax({
                url: "<%=basePath%>user/getAllInputRejister",
                type: "post",
                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {
                    chanpinLeibieId: leiid
                },
                dataType: "json",
                success: function (data) {
                    rejisters = data;
                }
            });
            //开始进行数据截取的相关操作
            var recordShow = null; //获得折线图中所有需要被使用到的历史数据
            $.ajax({
                url: "<%=basePath%>user/getRecordShow",
                type: "post",
                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {
                    chanpinId: realId
                },
                dataType: "json",
                success: function (data) {
                    recordShow = data;
                }
            });
            var reid = rejisters[0].id; //用户获取展示数据的寄存器id
            var disData = null;
            $.ajax({
                url: "<%=basePath%>user/getDisplayData",
                type: "post",
                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {
                    "chanpinId": realId,
                    "registerId": reid
                },
                dataType: "json",
                success: function (data) {
                    disData = data;
                }
            });

            for (var k = 0; k < disData.length; k++) {
                var sons = disData[k].sons;
                for (var o = 0; o < sons.length; o++) {
                    if (sons[o].isShown == 1 && sons[o].isPaint == 1) {
                        if (recordShow != null) {
                            var recordShow2s = recordShow.sons;
                            var recordShow2 = null;
                            for (var x = 0; x < recordShow2s.length; x++) {
                                if (recordShow2s[x].datameaningId == sons[o].id) {
                                    recordShow2 = recordShow2s[x];
                                }
                            }
                            if (recordShow2 != null) {
                                //	var mychart = echarts.init(document.getElementById(chanpin.id+'a'+sons[o].id));

                                var el = document.getElementById(chanpin.id + 'b' + sons[o].id);
                                if (el != null) {
                                    var mychart1 = echarts.init(document.getElementById(chanpin.id + 'b' + sons[o].id));
                                    var option1 = {
                                        title: {
                                            text: locati.name + "(" + chanpin.name + ") " + leibie.name + " 实时数据",
                                        },
                                        tooltip: {
                                            show: true,
                                            trigger: 'axis'
                                        },

                                        toolbox: {
                                            show: true,
                                            orient: 'horizontal',
                                            left: 'right',
                                            top: 'top',
                                            color: ['#1e90ff', '#22bb22', '#4b0082', '#d2691e'],
                                            backgroundColor: 'rgba(0,0,0,0)', // 工具箱背景颜色
                                            borderColor: '#ccc', // 工具箱边框颜色
                                            borderWidth: 0, // 工具箱边框线宽
                                            padding: 5, // 工具箱内边距
                                            showTitle: true,
                                            feature: {
                                                mark: {
                                                    show: true,
                                                    title: {
                                                        mark: '辅助线-开关',
                                                        markUndo: '辅助线-删除',
                                                        markClear: '辅助线-清空'
                                                    },
                                                    lineStyle: {
                                                        width: 1,
                                                        color: '#1e90ff',
                                                        type: 'dashed'
                                                    }
                                                },
                                                dataZoom: {
                                                    show: true,
                                                    title: '数据视图',
                                                    readOnly: true,
                                                },
                                                dataView: {
                                                    show: true,
                                                    readOnly: false
                                                },
                                                magicType: {
                                                    show: true,
                                                    title: {
                                                        line: '动态类型切换-折线图',
                                                        tiled: '动态类型切换-平铺'
                                                    },
                                                    type: ['line', 'tiled']
                                                },
                                                restore: {
                                                    show: true,
                                                    title: '还原',
                                                    color: 'black'
                                                },
                                                saveAsImage: {
                                                    show: true,
                                                    title: '保存为图片',
                                                    type: 'jpeg',
                                                    lang: ['点击本地保存']
                                                }
                                            }
                                        },
                                        calculable: true,
                                        dataZoom: {
                                            show: true,
                                            realtime: true,
                                            start: 0,
                                            end: 100
                                        },
                                        xAxis: [{
                                            type: 'category',
                                            boundaryGap: false,
                                            axisLabel: {
                                                interval: 'auto'
                                            }
                                        }],
                                        yAxis: [{
                                            type: 'value'
                                        }],
                                        series: function () {
                                            var serie = [];
                                            //var recordShow2s=recordShow.sons;
                                            //var recordShow2=recordShow2s[0];
                                            //var recordShow2=recordShow2s[0];
                                            var datameaningId = recordShow2.datameaningId;
                                            //var name=normal.getDatameaningById(datameaningId).getName();
                                            var datamean = null;
                                            $.ajax({
                                                url: "<%=basePath%>user/getDatameaningById",
                                                type: "post",
                                                async: false, //此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                                                data: {
                                                    id: datameaningId
                                                },
                                                dataType: "json",
                                                success: function (data) {
                                                    datamean = data;
                                                }
                                            });
                                            var name = datamean.name;
                                            var reshow = null;
                                            for (var h = 0; h < recordShows.length; h++) {
                                                var res = recordShows[h];
                                                //$.messager.alert("提示","res.chanpinId: "+res.chanpinId+" chanpin.pid: "+chanpin.pid, 'error');
                                                if (res.chanpinId == chanpin.id) {
                                                    reshow = res;
                                                    break;
                                                }
                                            }
                                            //$.messager.alert("提示", reshow, 'error');
                                            if (reshow != null) {
                                                var reshow2 = reshow.sons;
                                                var reshow3 = null;
                                                for (var y = 0; y < reshow2.length; y++) {
                                                    //$.messager.alert("提示","reshow2[y].datameaningId: "+reshow2[y].datameaningId+" datameaningId: "+datameaningId, 'error');
                                                    var a = reshow2[y].datameaningId;
                                                    if (a == datameaningId) {
                                                        //$.messager.alert("提示",a==datameaningId, 'error');
                                                        //$.messager.alert("提示",reshow2[y], 'error');
                                                        reshow3 = reshow2[y];
                                                        break;
                                                    }
                                                }
                                                //$.messager.alert("提示",reshow3, 'error');
                                                if (reshow3 != null) {
                                                    var recordShow3s = reshow3.sons;
                                                    var arr = [];
                                                    //$.messager.alert("提示",recordShow3s, 'error');
                                                    for (var j = 0; j < recordShow3s.length; j++) {
                                                        var recordShow3 = recordShow3s[j];
                                                        var item1 = [recordShow3.time, recordShow3.data];
                                                        //var item2=[recordShow3.time,3];
                                                        //var item3=[recordShow3.time,5];
                                                        arr.push(item1);
                                                        //arr.push(item2);
                                                        //arr.push(item3);
                                                    }
                                                    var item = {
                                                        name: name,
                                                        type: 'line',
                                                        //stack:'总量',
                                                        symbol: 'none',
                                                        smooth: true,
                                                        data: arr
                                                    };
                                                    serie.push(item);

                                                    if (recordShow2.isExistYujing == 1) {
                                                        var arr1 = [];
                                                        for (var j = 0; j < recordShow3s.length; j++) {
                                                            var recordShow3 = recordShow3s[j];
                                                            var item1 = [recordShow3.time, recordShow3.yujingData];
                                                            //var item2=[recordShow3.time,3];
                                                            //var item3=[recordShow3.time,5];
                                                            arr1.push(item1);
                                                            //arr.push(item2);
                                                            //arr.push(item3);
                                                        }
                                                        var item1 = {
                                                            name: name + "预警值",
                                                            type: 'line',
                                                            //stack:'总量',
                                                            symbol: 'none',
                                                            smooth: true,
                                                            data: arr1
                                                        };
                                                        serie.push(item1);
                                                    }

                                                    if (recordShow2.isExistBaojing == 1) {
                                                        var arr2 = [];
                                                        for (var j = 0; j < recordShow3s.length; j++) {
                                                            var recordShow3 = recordShow3s[j];
                                                            var item1 = [recordShow3.time, recordShow3.baojingData];
                                                            //var item2=[recordShow3.time,3];
                                                            //var item3=[recordShow3.time,5];
                                                            arr2.push(item1);
                                                            //arr.push(item2);
                                                            //arr.push(item3);
                                                        }
                                                        var item2 = {
                                                            name: name + "报警值",
                                                            type: 'line',
                                                            //stack:'总量',
                                                            symbol: 'none',
                                                            smooth: true,
                                                            data: arr2
                                                        };
                                                        serie.push(item2);
                                                    }
                                                }
                                            }
                                            return serie;
                                        }()

                                    };
                                    //mychart.setOption(option);
                                    mychart1.setOption(option1);
                                }
                            } //填充截止

                        }
                    }
                }
            }
        }

        //刷新结束

    }
</script>


</body>

</html>