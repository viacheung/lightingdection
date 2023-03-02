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
    <link rel="stylesheet" type="text/css" href="../static/Ui/ui/themes/icon.css">
    <script type="text/javascript" src="../static/Ui/ui/jquery.min.js"></script>
    <script type="text/javascript" src="../static/Ui/ui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../static/Ui/ui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../static/Ui/ui/jquery.serializejson.min.js"></script>
    <script type="text/javascript" src="../static/Echarts/dist/echarts.min.js"></script>
    <style>
        * {

            font-family: Microsoft YaHei;
            font-style: normal;
            font-size: 18px;

        }

        img {
            border-top: 5px;

            max-width: 150px;
            max-height: 150px;
            min-height: 150px;
            min-width: 150px;
            margin-left: 5%;
            margin-top: 10px;
        }

        table {
            table-layout: fixed;
        }

        table th {
            font-size: 18px;
            font-weight: 100;
        }

        button {
            background-color: white; /* Green */
            border: 1px solid black;
            color: black;
            padding: 4px 6px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            margin: 15px 10px;
            -webkit-transition-duration: 0.4s; /* Safari */
            transition-duration: 0.4s;
            cursor: pointer;
            border-radius: 4px;
        }

        table {
            background-color: #D2E5C4;
            text-align: center;

            border-collapse: collapse;
            border: 0px solid #D2E5C4;

            width: 98%;
            margin-left: 10px;
            margin-right: 10px;
            margin-top: 10px;
            box-shadow: inset 10px 10px 10px #D2E5C4;
        }

        table th {
            line-height: 50px;
            border: none;
            background-color: #73c7f0;
            border-bottom: 0px solid #73c7f0;
            border-top: 5px solid #D2E5C4;
        }

        table td {
            background: #ffffff;
            padding: 0px;
            border-bottom: 5px solid #D2E5C4;
            border-top: 5px solid #D2E5C4;
            border-left: 0;
            border-right: 0;
        }

        table tr.lastrow th {
            border-bottom: 0;
        }

        table tr th.lastCol {
            border-right: 0;

        }

        .circle {

            width: 10px;
            height: 10px;
            background-color: black;
            border-radius: 50%;

            display: inline-block;
            margin-right: 5px;
        }

        ul {
            margin-top: 20px;
            margin-left: 15%;
            margin-bottom: 20px;
        }

        ul li {
            text-align: left;
        }

        .ttt {
            min-height: 50px !important
        }
    </style>

</head>
<body>
<div id="dv" class="img">
</div>

<script type="text/javascript" defer="defer">

    var aid = window.parent.document.getElementById("pageId3").name;
    //var aids=aid.split("-");
    //$.messager.alert("",aid,"");
    var recordShows = null;//初始化当前所有的需要动态刷新的产品
    $.ajax({
        url: "<%=basePath%>user/recordShowInit",
        type: "post",
        async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
        data: {"aids": aid},
        dataType: "json",
        success: function (data) {
            recordShows = data;
        }
    });


    var htmllist = "";
    init();

    function init() {
        ini();
        ini1();
        //ini2();
        setInterval(shuaxin, 1000);
        setInterval(shuaxin2, 1000);
    }

    function ini() {

        htmllist += "<button  type='button' id='btnBack' >返回</button>";

        var id = window.parent.document.getElementById("pageId3").name;
        var ids = id.split("-");
        var length = ids.length;

        for (var i = 0; i < length; i++) {
            var realId = ids[i];//获得的产品的id
            // var chanpin=normal.getChanpinById(realId);//用于获得当前是哪一个产品
            var chanpin = null;
            $.ajax({
                url: "<%=basePath%>user/getChanpinById",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {id: realId},
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
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {id: cpid},
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
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {chanpinLeibieId: leiid},
                dataType: "json",
                success: function (data) {
                    rejisters = data;
                }
            });
            //$.messager.alert("",rejisters,"");
            // var photo=normal.findCPbyChanpinId(leibie.getId());
            var photo = null;
            $.ajax({
                url: "<%=basePath%>user/findCPbyChanpinId",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {chanpinId: leiid},
                dataType: "json",
                success: function (data) {
                    photo = data;
                }
            });
            if (photo != null) {
                //var path=normal.getpath()+photo.getUrl();
                var s = photo.url;

            }

            for (var j = 0; j < rejisters.length; j++) {
                //var displayData=normal.getDisplayData(chanpin.getId(),rejisters.get(j).getId());  //获得一个产品的输入寄存器的数据,rejisterId为输入寄存器的id
                var displayData = null;
                var chanpinid = chanpin.id;
                var rejisterid = rejisters[j].id;
                // $.messager.alert("","chanpinid :"+chanpinid+" rejisterid: "+rejisterid,"");
                $.ajax({
                    url: "<%=basePath%>user/getDisplayData",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {"chanpinId": chanpinid, "registerId": rejisterid},
                    dataType: "json",
                    success: function (data) {
                        displayData = data;
                    }
                });
                // var len=normal.getDisplayDataLength(chanpin.getId(),rejisters.get(j).getId())+1;
                var ll = null;
                $.ajax({
                    url: "<%=basePath%>user/getDisplayDataLength",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {"chanpinId": chanpinid, "registerId": rejisterid},
                    dataType: "json",
                    success: function (data) {
                        ll = data;
                    }
                });
                var len = ll + 1;
                var len1 = ll;
                var hei = 30 + 40 * len1;
                //  $.messager.alert("","ll :"+ll+" len: "+len+" len1: "+len1+" hei: "+hei,"");
                htmllist += "<div><table bordercolor='black' border='1px' >";
                htmllist += "<tr >" +
                    "<th  rowspan='" + len + " width='25%' style='border:none;line-height:30px;border-collapse: collapse;background:#ffffff;margin:0;border-top:5px solid #D2E5C4;border-bottom:5px solid #D2E5C4;border-right:1px solid #D2E5C4;'><img alt='未给该产品分配图片' id='" + chanpin.id + "' src='../static/" + s + "' />"
                    + "<ul >" +
                    "<li><div class='circle'></div>区域:" + leibie.location + "</li>" +
                    "<li><div class='circle'></div>产品型号:" + leibie.model + "</li>" +
                    //"<li><div class='circle'></div>产品类别名:"+leibie.getName()+"</li>"+
                    // "<li><div class='circle'></div>产品类别名:"+leibie.getName()+"</li>"+
                    "<li><div class='circle'></div>产品名称:" + leibie.name + "</li>" +
                    "<li><div class='circle'></div>地址码:" + chanpin.slaveId + "</li><li><div id ='cirle" + chanpinid + rejisterid + "' class='circle' ></div>通讯状态(绿色代表接通,红色代表断开)</li></ul></th>" +
                    "<th height:30px;rowspan='1' line-height:50px width='25%'>" + "监测项" + "</th>" +
                    "<th height:30px; rowspan='1'line-height:50px width='25%'>" + "状态/参数" + "</th>" +
                    "<th height:30px; rowspan='1' line-height:50px width='25%'>" + "故障时间" + "</th>" + "</tr>";
                for (var k = 0; k < displayData.length; k++) {
                    var sons = displayData[k].sons;
                    for (var o = 0; o < sons.length; o++) {
                        var hei2 = 50 / len1;
                        var height_th = 330 / len1;
                        if (sons[o].isShown == 1 && sons[o].id != 641) {
                            var hei3 = 40 + hei2;
                            htmllist += "<tr  class='ttt' style=' height:" + height_th + "px'><td id='" + i + "" + j + "" + k + "" + o + "name'  >" + sons[o].name + "</td>";
                            //$.messager.alert("提示", sons.get(o).getTime(), 'error');
                            if (sons[o].zhuangtai == 0) {

                                htmllist += "<td  ><div  style='background-color:#6ae115;color:white;line-height:50px;width:100%;height:50px;margin-left:0%' id='" + i + "" + j + "" + k + "" + o + "data'>" + sons[o].data + "</div></td>";
                                htmllist += "<td id='" + i + "" + j + "" + k + "" + o + "time'  ></td></tr>";

                            } else if (sons[o].zhuangtai == 1) {

                                htmllist += "<td ><div style='background-color:#fb0202;color:white;line-height:50px;width:100%;height:50px;margin-left:0%' id='" + i + "" + j + "" + k + "" + o + "data'>" + sons[o].data + "</div></td>";
                                htmllist += "<td  id='" + i + "" + j + "" + k + "" + o + "time'  >" + sons[o].time + "</td></tr>";


                            } else {

                                htmllist += "<td ><div style='background-color:#ffc600;color:white;line-height:50px;width:100%;height:50px;margin-left:0%' id='" + i + "" + j + "" + k + "" + o + "data'>" + sons[o].data + "</div></td>";
                                htmllist += "<td id='" + i + "" + j + "" + k + "" + o + "time'  >" + sons[o].time + "</td></tr>";


                            }

                        } else if (sons[o].isShown == 1 && sons[o].id == 641) {
                            var hei3 = 40 + hei2;
                            htmllist += "<tr  class='ttt' style=' height:" + height_th + "px'><td id='" + i + "" + j + "" + k + "" + o + "name'  >" + sons[o].name + "</td>";
                            //$.messager.alert("提示", sons.get(o).getTime(), 'error');
                            if (sons[o].zhuangtai == 0) {
                                htmllist += "<td  ><div  style='background-color:#6ae115;color:white;line-height:50px;width:100%;height:50px;margin-left:0%' id='" + i + "" + j + "" + k + "" + o + "data'>" + sons[o].data + "</div></td>";
                                htmllist += "<td id='" + i + "" + j + "" + k + "" + o + "time'  ></td></tr>";
                            } else if (sons[o].zhuangtai == 1) {
                                htmllist += "<td ><div style='background-color:#fb0202;color:white;line-height:50px;width:100%;height:50px;margin-left:0%' id='" + i + "" + j + "" + k + "" + o + "data'>" + sons[o].data + "</div></td>";
                                htmllist += "<td  id='" + i + "" + j + "" + k + "" + o + "time'  >" + sons[o].time + "</td></tr>";
                            } else {
                                htmllist += "<td ><div style='background-color:#ffc600;color:white;line-height:50px;width:100%;height:50px;margin-left:0%' id='" + i + "" + j + "" + k + "" + o + "data'>" + sons[o].data + "</div></td>";
                                htmllist += "<td id='" + i + "" + j + "" + k + "" + o + "time'  >" + sons[o].time + "</td></tr>";
                            }
                            htmllist += "<tr  class='ttt' style=' height:" + height_th + "px'><td id='" + i + "" + j + "" + k + "" + o + "name'  >" + "电阻值" + "</td>";
                            if (sons[o].zhuangtai == 0) {
                                htmllist += "<td  ><div  style='background-color:#6ae115;color:white;line-height:50px;width:100%;height:50px;margin-left:0%' id='" + i + "" + j + "" + k + "" + o + "solveData'>" + sons[o].solveData + "</div></td>";
                                htmllist += "<td id='" + i + "" + j + "" + k + "" + o + "solveTime'  ></td></tr>";
                            } else if (sons[o].zhuangtai == 1) {
                                htmllist += "<td ><div style='background-color:#fb0202;color:white;line-height:50px;width:100%;height:50px;margin-left:0%' id='" + i + "" + j + "" + k + "" + o + "solveData'>" + sons[o].solveData + "</div></td>";
                                htmllist += "<td  id='" + i + "" + j + "" + k + "" + o + "solveTime'  >" + sons[o].time + "</td></tr>";
                            } else {
                                htmllist += "<td ><div style='background-color:#ffc600;color:white;line-height:50px;width:100%;height:50px;margin-left:0%' id='" + i + "" + j + "" + k + "" + o + "solveData'>" + sons[o].solveData + "</div></td>";
                                htmllist += "<td id='" + i + "" + j + "" + k + "" + o + "solveTime'  >" + sons[o].time + "</td></tr>";
                            }
                        }
                    }
                }
                htmllist += "</table>";//数据展示表格结束，开始进行折线图的绘制
                htmllist += "<table>";
                var reid = rejisters[0].id;//用户获取展示数据的寄存器id
                var disData = null;
                $.ajax({
                    url: "<%=basePath%>user/getDisplayData",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {"chanpinId": realId, "registerId": reid},
                    dataType: "json",
                    success: function (data) {
                        disData = data;
                    }
                });
                // var lenn=0;
                for (var k = 0; k < disData.length; k++) {
                    var sons = disData[k].sons;
                    for (var o = 0; o < sons.length; o++) {
                        if (sons[o].isShown == 1 && sons[o].isPaint == 1) {
                            htmllist += "<tr><td   id = '" + chanpin.id + "a" + sons[o].id + "' style = 'width:300px;height:300px;'></td><td  id = '" + chanpin.id + "b" + sons[o].id + "' style = 'width:300px;height:300px;'></td></tr>";
                        }
                    }
                }
                htmllist += "</table>"
                htmllist += "</div><br>";//数据展示表格+折线图结束
            }
        }//填充htmllist的截止处
        document.getElementById("dv").innerHTML = htmllist;
    }

    function ini1() {
        var id = window.parent.document.getElementById("pageId3").name;
        var ids = id.split("-");
        var length = ids.length;
        for (var i = 0; i < length; i++) {
            var realId = ids[i];//获得的产品的id
            var chanpin = null;
            $.ajax({
                url: "<%=basePath%>user/getChanpinById",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {id: realId},
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
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {id: cpid},
                dataType: "json",
                success: function (data) {
                    leibie = data;
                }
            });
            var rejisters = null;
            var leiid = leibie.id;
            $.ajax({
                url: "<%=basePath%>user/getAllInputRejister",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {chanpinLeibieId: leiid},
                dataType: "json",
                success: function (data) {
                    rejisters = data;
                }
            });
            //开始进行数据截取的相关操作
            var recordShow = null;//获得折线图中所有需要被使用到的历史数据
            $.ajax({
                url: "<%=basePath%>user/getRecordShow",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {chanpinId: realId},
                dataType: "json",
                success: function (data) {
                    recordShow = data;
                }
            });
            var reid = rejisters[0].id;//用户获取展示数据的寄存器id
            var disData = null;
            $.ajax({
                url: "<%=basePath%>user/getDisplayData",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {"chanpinId": realId, "registerId": reid},
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
                                var mychart = echarts.init(document.getElementById(chanpin.id + 'a' + sons[o].id));
                                var mychart1 = echarts.init(document.getElementById(chanpin.id + 'b' + sons[o].id));
                                var datameaning = null;
                                var isod = sons[o].id;
                                $.ajax({
                                    url: "<%=basePath%>user/getDatameaningById",
                                    type: "post",
                                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                                    data: {"id": isod},
                                    dataType: "json",
                                    success: function (data) {
                                        datameaning = data;
                                    }
                                });

                                var option = {
                                    title: {
                                        text: leibie.name + "历史数据(单位:" + datameaning.unit + ")",
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
                                        backgroundColor: 'rgba(0,0,0,0)',  // 工具箱背景颜色
                                        borderColor: '#ccc',  // 工具箱边框颜色
                                        borderWidth: 0,  // 工具箱边框线宽
                                        padding: 5,  // 工具箱内边距
                                        showTitle: true,
                                        feature: {
                                            mark: {
                                                show: true,
                                                title: {mark: '辅助线-开关', markUndo: '辅助线-删除', markClear: '辅助线-清空'},
                                                lineStyle: {width: 1, color: '#1e90ff', type: 'dashed'}
                                            },
                                            dataZoom: {
                                                show: true, title: '数据视图', readOnly: true,
                                            },
                                            dataView: {show: true, readOnly: false},
                                            magicType: {
                                                show: true,
                                                title: {line: '动态类型切换-折线图', tiled: '动态类型切换-平铺'},
                                                type: ['line', 'tiled']
                                            },
                                            restore: {show: true, title: '还原', color: 'black'},
                                            saveAsImage: {show: true, title: '保存为图片', type: 'jpeg', lang: ['点击本地保存']}
                                        }
                                    },
                                    calculable: true,
                                    dataZoom: {
                                        show: true,
                                        realtime: true,
                                        start: 0,
                                        end: 100
                                    },
                                    xAxis: [
                                        {
                                            type: 'category',
                                            boundaryGap: false,
                                            axisLabel: {
                                                interval: 'auto'
                                            }


                                        }
                                    ],
                                    yAxis: [
                                        {
                                            type: 'value'
                                        }
                                    ],
                                    series: function () {
                                        var serie = [];
                                        var datameaningId = recordShow2.datameaningId;

                                        var name = datameaning.name + "(单位 :" + datameaning.unit + ")";
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

                                var option1 = {
                                    title: {
                                        text: leibie.name + "实时数据(单位:" + datameaning.unit + ")",
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
                                        backgroundColor: 'rgba(0,0,0,0)',  // 工具箱背景颜色
                                        borderColor: '#ccc',  // 工具箱边框颜色
                                        borderWidth: 0,  // 工具箱边框线宽
                                        padding: 5,  // 工具箱内边距
                                        showTitle: true,
                                        feature: {
                                            mark: {
                                                show: true,
                                                title: {mark: '辅助线-开关', markUndo: '辅助线-删除', markClear: '辅助线-清空'},
                                                lineStyle: {width: 1, color: '#1e90ff', type: 'dashed'}
                                            },
                                            dataZoom: {
                                                show: true, title: '数据视图', readOnly: true,
                                            },
                                            dataView: {show: true, readOnly: false},
                                            magicType: {
                                                show: true,
                                                title: {line: '动态类型切换-折线图', tiled: '动态类型切换-平铺'},
                                                type: ['line', 'tiled']
                                            },
                                            restore: {show: true, title: '还原', color: 'black'},
                                            saveAsImage: {show: true, title: '保存为图片', type: 'jpeg', lang: ['点击本地保存']}
                                        }
                                    },
                                    calculable: true,
                                    dataZoom: {
                                        show: true,
                                        realtime: true,
                                        start: 0,
                                        end: 100
                                    },
                                    xAxis: [
                                        {
                                            type: 'category',
                                            boundaryGap: false,
                                            axisLabel: {
                                                interval: 'auto'
                                            }
                                        }
                                    ],
                                    yAxis: [
                                        {
                                            type: 'value'
                                        }
                                    ],
                                    series: function () {
                                        var serie = [];
                                        var datameaningId = recordShow2.datameaningId;

                                        var name = datameaning.name + "(单位 :" + datameaning.unit + ")";
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
                                mychart.setOption(option);
                                mychart1.setOption(option1);
                            }//填充截止

                        }
                    }
                }
            }
        }
    }//函数截止

    function shuaxin() {

        //$.messager.alert("提示","执行一次刷新", 'error');
        //  var normal=window.parent.parent.normal;
        var id = window.parent.document.getElementById("pageId3").name;
        var ids = id.split("-");
        var length = ids.length;

        for (var i = 0; i < length; i++) {
            var realId = ids[i];//获得的产品的id
            var chanpin = null;
            $.ajax({
                url: "<%=basePath%>user/getChanpinById",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {id: realId},
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
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {id: cpid},
                dataType: "json",
                success: function (data) {
                    leibie = data;
                }
            });
            // var rejisters=normal.getAllInputRejister(leibie.getId());//获得所有的输入寄存器
            var rejisters = null;
            var leiid = leibie.id;
            $.ajax({
                url: "<%=basePath%>user/getAllInputRejister",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {chanpinLeibieId: leiid},
                dataType: "json",
                success: function (data) {
                    rejisters = data;
                }
            });
            //var photo=normal.findCPbyChanpinId(leibie.getId());

            for (var j = 0; j < rejisters.length; j++) {
                var displayData = null;
                var chanpinid = chanpin.id;
                var rejisterid = rejisters[j].id;
                var zhuangtai = 0;
                $.ajax({
                    url: "<%=basePath%>user/getChanpinZhuangtai",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {"chanpinId": chanpinid, "registerId": rejisterid},
                    dataType: "json",
                    success: function (data) {
                        zhuangtai = data;
                    }
                });
                // document.getElementById("cirle"+chanpinid+rejisterid).innerHTML="";
                if (zhuangtai == 0) {
                    document.getElementById("cirle" + chanpinid + rejisterid).style.background = "red";
                } else {
                    document.getElementById("cirle" + chanpinid + rejisterid).style.background = "green";
                }
                $.ajax({
                    url: "<%=basePath%>user/getDisplayData",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {"chanpinId": chanpinid, "registerId": rejisterid},
                    dataType: "json",
                    success: function (data) {
                        displayData = data;
                    }
                });
                for (var k = 0; k < displayData.length; k++) {
                    var sons = displayData[k].sons;

                    for (var o = 0; o < sons.length; o++) {
                        //document.getElementById(i+""+j+""+k+""+o+"name").innerHTML=sons.get(o).getName();
                        //$.messager.alert("提示", sons.get(o).getTime(), 'error');
                        if (sons[o].isShown == 1 && sons[o].id == 641) {
                            document.getElementById(i + "" + j + "" + k + "" + o + "data").innerHTML = sons[o].data;
                            document.getElementById(i + "" + j + "" + k + "" + o + "solveData").innerHTML = sons[o].solveData;
                            if (sons[o].zhuangtai == 0) {
                                //$.messager.alert("提示", document.getElementById(i+""+j+""+k+""+o+"data"), 'error');
                                // $.messager.alert("提示", sons.get(o).getTime(), 'error');
                                document.getElementById(i + "" + j + "" + k + "" + o + "data").style.background = "#6ae115";
                                document.getElementById(i + "" + j + "" + k + "" + o + "time").innerHTML = "";
                                // $.messager.alert("提示", sons.get(o).getTime(), 'error');
                                document.getElementById(i + "" + j + "" + k + "" + o + "solveData").style.background = "#6ae115";
                                document.getElementById(i + "" + j + "" + k + "" + o + "solveTime").innerHTML = "";
                            } else if (sons[o].zhuangtai == 1) {
                                document.getElementById(i + "" + j + "" + k + "" + o + "data").style.background = "#fb0202";
                                document.getElementById(i + "" + j + "" + k + "" + o + "time").innerHTML = sons[o].time;
                                document.getElementById(i + "" + j + "" + k + "" + o + "solveData").style.background = "#fb0202";
                                document.getElementById(i + "" + j + "" + k + "" + o + "solveTime").innerHTML = sons[o].time;
                            } else {
                                document.getElementById(i + "" + j + "" + k + "" + o + "data").style.background = "#ffc600";
                                document.getElementById(i + "" + j + "" + k + "" + o + "time").innerHTML = sons[o].time;
                                // $.messager.alert("提示", sons.get(o).getTime(), 'error');
                                document.getElementById(i + "" + j + "" + k + "" + o + "solveData").style.background = "#ffc600";
                                document.getElementById(i + "" + j + "" + k + "" + o + "solveTime").innerHTML = sons[o].time;
                            }
                            //document.getElementById(i+""+j+""+k+""+o+"time").innerHTML=sons.get(o).getTime();
                            //$.messager.alert("提示", sons.get(o).getTime(), 'error');
                        } else if (sons[o].isShown == 1 && sons[o].id != 641) {
                            if (sons[o].zhuangtai == 0) {
                                //$.messager.alert("提示", document.getElementById(i+""+j+""+k+""+o+"data"), 'error');
                                document.getElementById(i + "" + j + "" + k + "" + o + "data").innerHTML = sons[o].data;

                                // $.messager.alert("提示", sons.get(o).getTime(), 'error');
                                document.getElementById(i + "" + j + "" + k + "" + o + "data").style.background = "#6ae115";
                                document.getElementById(i + "" + j + "" + k + "" + o + "time").innerHTML = "";

                            } else if (sons[o].zhuangtai == 1) {
                                document.getElementById(i + "" + j + "" + k + "" + o + "data").innerHTML = sons[o].data;

                                document.getElementById(i + "" + j + "" + k + "" + o + "data").style.background = "#fb0202";
                                document.getElementById(i + "" + j + "" + k + "" + o + "time").innerHTML = sons[o].time;

                            } else {
                                document.getElementById(i + "" + j + "" + k + "" + o + "data").innerHTML = sons[o].data;

                                document.getElementById(i + "" + j + "" + k + "" + o + "data").style.background = "#ffc600";
                                document.getElementById(i + "" + j + "" + k + "" + o + "time").innerHTML = sons[o].time;


                            }


                        }
                    }
                }

            }
        }//填充htmllist的截止处

    }

    function shuaxin2() {
        var aaid = window.parent.document.getElementById("pageId3").name;
        //var aids=aid.split("-");
        //$.messager.alert("",aid,"");
        var recordShows1 = null;//初始化当前所有的需要动态刷新的产品
        $.ajax({
            url: "<%=basePath%>user/recordShowInit",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"aids": aaid},
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
            }//遍历所有的datameaning
        }

        //刷新开始

        var id = window.parent.document.getElementById("pageId3").name;
        var ids = id.split("-");
        var length = ids.length;
        for (var i = 0; i < length; i++) {
            var realId = ids[i];//获得的产品的id
            var chanpin = null;
            $.ajax({
                url: "<%=basePath%>user/getChanpinById",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {id: realId},
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
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {id: cpid},
                dataType: "json",
                success: function (data) {
                    leibie = data;
                }
            });
            var rejisters = null;
            var leiid = leibie.id;
            $.ajax({
                url: "<%=basePath%>user/getAllInputRejister",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {chanpinLeibieId: leiid},
                dataType: "json",
                success: function (data) {
                    rejisters = data;
                }
            });
            //开始进行数据截取的相关操作
            var recordShow = null;//获得折线图中所有需要被使用到的历史数据
            $.ajax({
                url: "<%=basePath%>user/getRecordShow",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {chanpinId: realId},
                dataType: "json",
                success: function (data) {
                    recordShow = data;
                }
            });
            var reid = rejisters[0].id;//用户获取展示数据的寄存器id
            var disData = null;
            $.ajax({
                url: "<%=basePath%>user/getDisplayData",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {"chanpinId": realId, "registerId": reid},
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
                                var mychart1 = echarts.init(document.getElementById(chanpin.id + 'b' + sons[o].id));
                                var datameaning = null;
                                var isod = sons[o].id;
                                $.ajax({
                                    url: "<%=basePath%>user/getDatameaningById",
                                    type: "post",
                                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                                    data: {"id": isod},
                                    dataType: "json",
                                    success: function (data) {
                                        datameaning = data;
                                    }
                                });
                                var option1 = {
                                    title: {
                                        text: leibie.name + "实时数据(单位:" + datameaning.unit + ")",
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
                                        backgroundColor: 'rgba(0,0,0,0)',  // 工具箱背景颜色
                                        borderColor: '#ccc',  // 工具箱边框颜色
                                        borderWidth: 0,  // 工具箱边框线宽
                                        padding: 5,  // 工具箱内边距
                                        showTitle: true,
                                        feature: {
                                            mark: {
                                                show: true,
                                                title: {mark: '辅助线-开关', markUndo: '辅助线-删除', markClear: '辅助线-清空'},
                                                lineStyle: {width: 1, color: '#1e90ff', type: 'dashed'}
                                            },
                                            dataZoom: {
                                                show: true, title: '数据视图', readOnly: true,
                                            },
                                            dataView: {show: true, readOnly: false},
                                            magicType: {
                                                show: true,
                                                title: {line: '动态类型切换-折线图', tiled: '动态类型切换-平铺'},
                                                type: ['line', 'tiled']
                                            },
                                            restore: {show: true, title: '还原', color: 'black'},
                                            saveAsImage: {show: true, title: '保存为图片', type: 'jpeg', lang: ['点击本地保存']}
                                        }
                                    },
                                    calculable: true,
                                    dataZoom: {
                                        show: true,
                                        realtime: true,
                                        start: 0,
                                        end: 100
                                    },
                                    xAxis: [
                                        {
                                            type: 'category',
                                            boundaryGap: false,
                                            axisLabel: {
                                                interval: 'auto'
                                            }
                                        }
                                    ],
                                    yAxis: [
                                        {
                                            type: 'value'
                                        }
                                    ],
                                    series: function () {
                                        var serie = [];

                                        var datameaningId = recordShow2.datameaningId;

                                        var name = "(单位 :" + datameaning.unit + ")";
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
                            }//填充截止

                        }
                    }
                }
            }
        }

        //刷新结束

    }

    $("#btnBack").click(function () {

        var a = window.parent.document.getElementById("pageId2").name;
        //$.messager.alert("提示",a,'error');
        if (typeof a == "undefined") {
            window.parent.document.getElementById("aa2").src = '../user/showRight';
        } else {
            window.parent.document.getElementById("aa2").src = '../user/showDetail2';
        }
    });

    /* $("img").bind("click",function(e){
    	 var index= $(e.target).attr( "id" );
    	 window.parent.document.getElementById("aa2").src='../user/showDetail4';
   	     window.parent.document.getElementById("pageId4").name=index;
	})*/
</script>
</body>
</html>