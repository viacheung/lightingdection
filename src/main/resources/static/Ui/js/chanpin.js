$(function () {
    $('#insertDlg').dialog({
        title: '添加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                var a = '1';
                insertData(a);
            }
        }, {
            text: '关闭',
            handler: function () {
                $("#insertDlg").dialog('close');
            }
        }]
    });


    $('#insertDlg1').dialog({
        title: '添加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                var a = '1';
                insertData(a);
            }
        }, {
            text: '关闭',
            handler: function () {
                $("#insertDlg1").dialog('close');
            }
        }]
    });

    $('#insertDlg2').dialog({
        title: '添加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                var a = '1';
                insertData(a);
            }
        }, {
            text: '关闭',
            handler: function () {
                $("#insertDlg2").dialog('close');
            }
        }]
    });


    $('#insertDlgTwo').dialog({
        title: '添加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                var a = '2';
                insertData(a);
            }
        }, {
            text: '关闭',
            handler: function () {
                $("#insertDlgTwo").dialog('close');
            }
        }]
    });


    $('#insertDlgTwo1').dialog({
        title: '添加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                var a = '2';
                insertData(a);
            }
        }, {
            text: '关闭',
            handler: function () {
                $("#insertDlgTwo1").dialog('close');
            }
        }]
    });

    $('#insertDlgTwo2').dialog({
        title: '添加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                var a = '2';
                insertData(a);
            }
        }, {
            text: '关闭',
            handler: function () {
                $("#insertDlgTwo2").dialog('close');
            }
        }]
    });
    $('#batchDlg').dialog({
        title: '批量增加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                var a = '1';
                batchAddData(a);
            }
        }, {
            text: '关闭',
            handler: function () {
                $("#batchDlg").dialog('close');
            }
        }]
    });
    $('#batchDlg1').dialog({
        title: '批量增加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                var a = '2';
                batchAddData(a);
            }
        }, {
            text: '关闭',
            handler: function () {
                $("#batchDlg1").dialog('close');
            }
        }]
    });

});


function disposeTree() {
    $('#tt').tree({
        onClick: function (node) {
            loadDataGrid(node.id);
        }
    });
}

function loadDataGrid(id) {
    var htmllist = "";
    var htmllist1 = "";
    document.getElementById("div2").style.display = "none";
    $('#grid1').datagrid({
        frozenColumns: [[{
            field: 'ck',
            checkbox: true
        }]],
        loading: true,
        striped: true,
        rownumbers: true,
        //singleSelect : true
    });

    $('#grid').datagrid({
        frozenColumns: [[{
            field: 'ck',
            checkbox: true
        }]],
        loading: true,
        striped: true,
        rownumbers: true,
        singleSelect: true
    });
    if (id != "-1") {
        document.getElementById("div2").style.display = "block";
        var list = null
        var Id = '' + id

        $.ajax({
            url: "findChanpinListById",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"id": Id},
            dataType: "json",
            success: function (data) {
                list = data;
            }
        });

//		var chanpin=admin.findChanpinById(id);
        var chanpin = null
        //var Id = ''+id
        $.ajax({
            url: "findChanpinById",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"id": Id},
            dataType: "json",
            success: function (data) {
                chanpin = data;
            }
        });

        //var pardata=admin.findChanpinById(chanpin.getPid());
//		var list1=admin.getAllSons(id);

        var list1 = null
        var pid = '' + id

        $.ajax({
            url: "getAllSons",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"pid": pid},
            dataType: "json",
            success: function (data) {
                list1 = data;
            }
        });


        $('#grid1').datagrid({
            toolbar: [{
                iconCls: 'icon-add',
                text: '添加',
                handler: function () {
                    var treeselected = $('#tt').tree('getSelected');
                    if (treeselected == null) {
                        $('#insertFormTwo1').form('clear');
                        $("#insertDlgTwo1").dialog('open');
                    } else {
                        var checkedItems = $('#grid1').datagrid('getChecked');
                        var selected1 = [];
                        $.each(checkedItems, function (index, item) {
                            selected1.push(item);
                        });
                        if (selected1.length > 1) {
                            $.messager.alert("提示", "该菜单下不支持多选", 'warning');
                        } else {
                            var selected = selected1[0];
                            //$.messager.alert('My Title',selected.pid,'info');
                            if (selected.isparent == 0) {
                                $.messager.alert("提示", "该菜单目录暂时不支持三级以上的菜单", 'warning');
                            } else if (selected.pid == 0) {
                                $('#insertFormTwo2').form('clear');
                                $("#insertDlgTwo2").dialog('open');
                            } else {
                                $('#insertFormTwo').form('clear');
                                $("#insertDlgTwo").dialog('open');
                            }
                        }
                    }
                }
            }, '-', {
                iconCls: 'icon-save',
                text: '行内修改',
                handler: function () {
                    var rowData = $('#grid1').datagrid('getSelected');
                    if (null != rowData) {
                        var a = '2';
                        updateData(a);
                    } else {
                        $.messager.alert("提示", "请选中要修改的行", 'error');
                    }
                }
            }, '-', {
                iconCls: 'icon-cut',
                text: '删除',
                handler: function () {
                    var checkedItems = $('#grid1').datagrid('getChecked');
                    var selected1 = [];
                    $.each(checkedItems, function (index, item) {
                        selected1.push(item);
                    });
                    if (selected1.length > 1) {
                        $.messager.alert("提示", "该菜单下不支持多选", 'warning');
                    } else {
                        var rowData = selected1[0];
                        if (null != rowData) {
                            //$.messager.alert("提示",rowData.id, 'error');
                            deleteData(rowData.id);

                        } else {
                            $.messager.alert("提示", "请选中要删除数据", 'error');
                        }
                    }
                }
            }, '-', {
                iconCls: 'icon-add',
                text: '批量添加',
                handler: function () {
                    var treeselected = $('#tt').tree('getSelected');
                    if (treeselected == null) {
                        $('#batchForm1').form('clear');
                        $("#batchDlg1").dialog('open');
                    } else {
                        var selected = $('#grid1').datagrid('getSelected');
                        //$.messager.alert('My Title',selected.pid,'info');
                        if (selected.isparent == 0) {
                            $.messager.alert("提示", "该菜单目录暂时不支持三级以上菜单的添加", 'warning');
                        } else {
                            $('#batchForm1').form('clear');
                            $("#batchDlg1").dialog('open');
                        }
                    }
                }
            }],
            onDblClickRow: function (rowIndex, rowData) {
            }
        });
        for (var i = 0; i < list.length; i++) {
            htmllist += "<tr>" +
                "<td></td>" +
                "<td>" + list[i].id + "</td>" +
                "<td><input id=" + 1 + '' + list[i].id + " name=" + 1 + '' + list[i].id + " value=";
            if (list[i].name != null) {
                htmllist += list[i].name + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 2 + '' + list[i].id + " name=" + 2 + '' + list[i].id + " value=";
            if (list[i].location != null) {
                htmllist += list[i].location + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 3 + '' + list[i].id + " name=" + 3 + '' + list[i].id + " value=";
            if (list[i].zhuangtai != null) {
                htmllist += list[i].zhuangtai + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 4 + '' + list[i].id + " name=" + 4 + '' + list[i].id + " value=";
            if (list[i].model != null) {
                htmllist += list[i].model + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 5 + '' + list[i].id + " name=" + 5 + '' + list[i].id + " value=";
            if (list[i].installation != null) {
                htmllist += list[i].installation + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 6 + '' + list[i].id + " name=" + 6 + '' + list[i].id + " value=";
            if (list[i].slaveid != null) {
                htmllist += list[i].slaveid + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 7 + '' + list[i].id + " name=" + 7 + '' + list[i].id + " value=";
            if (list[i].way != null) {
                htmllist += list[i].way + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 8 + '' + list[i].id + " name=" + 8 + '' + list[i].id + " value=";
            if (list[i].address != null) {
                htmllist += list[i].address + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 9 + '' + list[i].id + " name=" + 9 + '' + list[i].id + " value=";
            if (list[i].port != null) {
                htmllist += list[i].port + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 10 + '' + list[i].id + " name=" + 10 + '' + list[i].id + " value=";
            if (list[i].connecttime != null) {
                htmllist += list[i].connecttime + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 11 + '' + list[i].id + " name=" + 11 + '' + list[i].id + " value=";
            if (list[i].readtime != null) {
                htmllist += list[i].readtime + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 12 + '' + list[i].id + " name=" + 12 + '' + list[i].id + " value=";
            if (list[i].portname != null) {
                htmllist += list[i].portname + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 13 + '' + list[i].id + " name=" + 13 + '' + list[i].id + " value=";
            if (list[i].boto != null) {
                htmllist += list[i].boto + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 14 + '' + list[i].id + " name=" + 14 + '' + list[i].id + " value=";
            if (list[i].boxid != null) {
                htmllist += list[i].boxid + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 15 + '' + list[i].id + " name=" + 15 + '' + list[i].id + " value=";
            if (list[i].comment != null) {
                htmllist += list[i].comment + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 16 + '' + list[i].id + " name=" + 16 + '' + list[i].id + " value=";
            if (list[i].datameaningid != null) {
                htmllist += list[i].datameaningid + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td>" + list[i].pid + "</td>" +
                "<td>" + list[i].isparent + "</td>" +
                "</tr>";
        }

        for (var i = 0; i < list1.length; i++) {
            htmllist1 += "<tr>" +
                "<td></td>" +
                "<td>" + list1[i].id + "</td>" +
                "<td><input id=" + 1 + '' + list1[i].id + " name=" + 1 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].name != null) {
                htmllist1 += list1[i].name + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 2 + '' + list1[i].id + " name=" + 2 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].location != null) {
                htmllist1 += list1[i].location + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 3 + '' + list1[i].id + " name=" + 3 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].zhuangtai != null) {
                htmllist1 += list1[i].zhuangtai + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 4 + '' + list1[i].id + " name=" + 4 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].model != null) {
                htmllist1 += list1[i].model + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 5 + '' + list1[i].id + " name=" + 5 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].installation != null) {
                htmllist1 += list1[i].installation + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 6 + '' + list1[i].id + " name=" + 6 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].slaveid != null) {
                htmllist1 += list1[i].slaveid + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 7 + '' + list1[i].id + " name=" + 7 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].way != null) {
                htmllist1 += list1[i].way + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 8 + '' + list1[i].id + " name=" + 8 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].address != null) {
                htmllist1 += list1[i].address + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 9 + '' + list1[i].id + " name=" + 9 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].port != null) {
                htmllist1 += list1[i].port + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 10 + '' + list1[i].id + " name=" + 10 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].connecttime != null) {
                htmllist1 += list1[i].connecttime + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 11 + '' + list1[i].id + " name=" + 11 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].readtime != null) {
                htmllist1 += list1[i].readtime + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 12 + '' + list1[i].id + " name=" + 12 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].portname != null) {
                htmllist1 += list1[i].portname + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 13 + '' + list1[i].id + " name=" + 13 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].boto != null) {
                htmllist1 += list1[i].boto + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 14 + '' + list1[i].id + " name=" + 14 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].boxid != null) {
                htmllist1 += list1[i].boxid + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 15 + '' + list1[i].id + " name=" + 15 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].comment != null) {
                htmllist1 += list1[i].comment + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 16 + '' + list1[i].id + " name=" + 16 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].datameaningid != null) {
                htmllist1 += list1[i].datameaningid + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td>" + list1[i].pid + "</td>" +
                "<td>" + list1[i].isparent + "</td>" +
                "</tr>";
        }
        if (chanpin.pid == 0) {
            $('#grid').datagrid('showColumn', 'id');
            $('#grid').datagrid('showColumn', 'zhuangtai');
            $('#grid').datagrid('showColumn', 'location');
            $('#grid').datagrid('showColumn', 'model');
            $('#grid').datagrid('showColumn', 'installation');
            $('#grid').datagrid('showColumn', 'name');
            $('#grid').datagrid('showColumn', 'slaveId');
            $('#grid').datagrid('showColumn', 'way');
            $('#grid').datagrid('showColumn', 'address');
            $('#grid').datagrid('showColumn', 'port');
            $('#grid').datagrid('showColumn', 'portName');
            $('#grid').datagrid('showColumn', 'boto');
            $('#grid').datagrid('showColumn', 'boxId');
            $('#grid').datagrid('showColumn', 'comment');
            $('#grid').datagrid('showColumn', 'datameaningId');
            $('#grid').datagrid('showColumn', 'pid');
            $('#grid').datagrid('showColumn', 'isparent');
            $('#grid').datagrid('showColumn', 'connectTime');
            $('#grid').datagrid('showColumn', 'readTime');
            $('#grid1').datagrid('hideColumn', 'id');
            $('#grid1').datagrid('hideColumn', 'zhuangtai');
            $('#grid1').datagrid('hideColumn', 'model');
            $('#grid1').datagrid('hideColumn', 'installation');
            $('#grid1').datagrid('hideColumn', 'location');
            $('#grid1').datagrid('hideColumn', 'name');
            $('#grid1').datagrid('hideColumn', 'slaveId');
            $('#grid1').datagrid('hideColumn', 'way');
            $('#grid1').datagrid('hideColumn', 'address');
            $('#grid1').datagrid('hideColumn', 'port');
            $('#grid1').datagrid('hideColumn', 'portName');
            $('#grid1').datagrid('hideColumn', 'boto');
            $('#grid1').datagrid('hideColumn', 'boxId');
            $('#grid1').datagrid('hideColumn', 'comment');
            $('#grid1').datagrid('hideColumn', 'datameaningId');
            $('#grid1').datagrid('hideColumn', 'pid');
            $('#grid1').datagrid('hideColumn', 'isparent');
            $('#grid1').datagrid('hideColumn', 'connectTime');
            $('#grid1').datagrid('hideColumn', 'readTime');


            $('#grid').datagrid('hideColumn', 'zhuangtai');
            $('#grid').datagrid('hideColumn', 'model');
            $('#grid').datagrid('hideColumn', 'installation');
            $('#grid').datagrid('hideColumn', 'slaveId');
            $('#grid').datagrid('hideColumn', 'way');
            $('#grid').datagrid('hideColumn', 'address');
            $('#grid').datagrid('hideColumn', 'port');
            $('#grid').datagrid('hideColumn', 'portName');
            $('#grid').datagrid('hideColumn', 'boto');
            $('#grid').datagrid('hideColumn', 'boxId');
            $('#grid').datagrid('hideColumn', 'comment');
            $('#grid').datagrid('hideColumn', 'datameaningId');
            $('#grid').datagrid('hideColumn', 'connectTime');
            $('#grid').datagrid('hideColumn', 'readTime');

            $('#grid1').datagrid('showColumn', 'id');
            $('#grid1').datagrid('showColumn', 'model');
            $('#grid1').datagrid('showColumn', 'name');
            $('#grid1').datagrid('showColumn', 'datameaningId');
            $('#grid1').datagrid('showColumn', 'pid');
            $('#grid1').datagrid('showColumn', 'isparent');


            document.getElementById("chanpinList").innerHTML = htmllist;
            document.getElementById("chanpinList1").innerHTML = htmllist1;
        } else if (chanpin.isparent == 0) {
            $('#grid').datagrid('showColumn', 'id');
            $('#grid').datagrid('showColumn', 'zhuangtai');
            $('#grid').datagrid('showColumn', 'location');
            $('#grid').datagrid('showColumn', 'model');
            $('#grid').datagrid('showColumn', 'installation');
            $('#grid').datagrid('showColumn', 'name');
            $('#grid').datagrid('showColumn', 'slaveId');
            $('#grid').datagrid('showColumn', 'way');
            $('#grid').datagrid('showColumn', 'address');
            $('#grid').datagrid('showColumn', 'port');
            $('#grid').datagrid('showColumn', 'portName');
            $('#grid').datagrid('showColumn', 'boto');
            $('#grid').datagrid('showColumn', 'boxId');
            $('#grid').datagrid('showColumn', 'comment');
            $('#grid').datagrid('showColumn', 'datameaningId');
            $('#grid').datagrid('showColumn', 'pid');
            $('#grid').datagrid('showColumn', 'isparent');
            $('#grid').datagrid('showColumn', 'connectTime');
            $('#grid').datagrid('showColumn', 'readTime');
            $('#grid1').datagrid('hideColumn', 'id');
            $('#grid1').datagrid('hideColumn', 'zhuangtai');
            $('#grid1').datagrid('hideColumn', 'model');
            $('#grid1').datagrid('hideColumn', 'installation');
            $('#grid1').datagrid('hideColumn', 'location');
            $('#grid1').datagrid('hideColumn', 'name');
            $('#grid1').datagrid('hideColumn', 'slaveId');
            $('#grid1').datagrid('hideColumn', 'way');
            $('#grid1').datagrid('hideColumn', 'address');
            $('#grid1').datagrid('hideColumn', 'port');
            $('#grid1').datagrid('hideColumn', 'portName');
            $('#grid1').datagrid('hideColumn', 'boto');
            $('#grid1').datagrid('hideColumn', 'boxId');
            $('#grid1').datagrid('hideColumn', 'comment');
            $('#grid1').datagrid('hideColumn', 'datameaningId');
            $('#grid1').datagrid('hideColumn', 'pid');
            $('#grid1').datagrid('hideColumn', 'isparent');
            $('#grid1').datagrid('hideColumn', 'connectTime');
            $('#grid1').datagrid('hideColumn', 'readTime');


            $('#grid').datagrid('hideColumn', 'zhuangtai');
            $('#grid').datagrid('hideColumn', 'model');
            $('#grid').datagrid('hideColumn', 'location');
            document.getElementById("chanpinList").innerHTML = htmllist;
            document.getElementById("div2").style.display = "none";
        } else {
            $('#grid').datagrid('showColumn', 'id');
            $('#grid').datagrid('showColumn', 'zhuangtai');
            $('#grid').datagrid('showColumn', 'location');
            $('#grid').datagrid('showColumn', 'model');
            $('#grid').datagrid('showColumn', 'installation');
            $('#grid').datagrid('showColumn', 'name');
            $('#grid').datagrid('showColumn', 'slaveId');
            $('#grid').datagrid('showColumn', 'way');
            $('#grid').datagrid('showColumn', 'address');
            $('#grid').datagrid('showColumn', 'port');
            $('#grid').datagrid('showColumn', 'portName');
            $('#grid').datagrid('showColumn', 'boto');
            $('#grid').datagrid('showColumn', 'boxId');
            $('#grid').datagrid('showColumn', 'comment');
            $('#grid').datagrid('showColumn', 'datameaningId');
            $('#grid').datagrid('showColumn', 'pid');
            $('#grid').datagrid('showColumn', 'isparent');
            $('#grid').datagrid('showColumn', 'connectTime');
            $('#grid').datagrid('showColumn', 'readTime');
            $('#grid1').datagrid('hideColumn', 'id');
            $('#grid1').datagrid('hideColumn', 'zhuangtai');
            $('#grid1').datagrid('hideColumn', 'model');
            $('#grid1').datagrid('hideColumn', 'installation');
            $('#grid1').datagrid('hideColumn', 'location');
            $('#grid1').datagrid('hideColumn', 'name');
            $('#grid1').datagrid('hideColumn', 'slaveId');
            $('#grid1').datagrid('hideColumn', 'way');
            $('#grid1').datagrid('hideColumn', 'address');
            $('#grid1').datagrid('hideColumn', 'port');
            $('#grid1').datagrid('hideColumn', 'portName');
            $('#grid1').datagrid('hideColumn', 'boto');
            $('#grid1').datagrid('hideColumn', 'boxId');
            $('#grid1').datagrid('hideColumn', 'comment');
            $('#grid1').datagrid('hideColumn', 'datameaningId');
            $('#grid1').datagrid('hideColumn', 'pid');
            $('#grid1').datagrid('hideColumn', 'isparent');
            $('#grid1').datagrid('hideColumn', 'connectTime');
            $('#grid1').datagrid('hideColumn', 'readTime');


            $('#grid').datagrid('hideColumn', 'zhuangtai');
            $('#grid').datagrid('hideColumn', 'location');
            $('#grid').datagrid('hideColumn', 'slaveId');
            $('#grid').datagrid('hideColumn', 'installation');
            $('#grid').datagrid('hideColumn', 'way');
            $('#grid').datagrid('hideColumn', 'address');
            $('#grid').datagrid('hideColumn', 'port');
            $('#grid').datagrid('hideColumn', 'portName');
            $('#grid').datagrid('hideColumn', 'boto');
            $('#grid').datagrid('hideColumn', 'boxId');
            $('#grid').datagrid('hideColumn', 'comment');
            $('#grid').datagrid('hideColumn', 'connectTime');
            $('#grid').datagrid('hideColumn', 'readTime');

            $('#grid1').datagrid('showColumn', 'id');
            $('#grid1').datagrid('showColumn', 'name');
            $('#grid1').datagrid('showColumn', 'slaveId');
            $('#grid1').datagrid('showColumn', 'installation');
            $('#grid1').datagrid('showColumn', 'way');
            $('#grid1').datagrid('showColumn', 'address');
            $('#grid1').datagrid('showColumn', 'port');
            $('#grid1').datagrid('showColumn', 'portName');
            $('#grid1').datagrid('showColumn', 'boto');
            $('#grid1').datagrid('showColumn', 'boxId');
            $('#grid1').datagrid('showColumn', 'comment');
            $('#grid1').datagrid('showColumn', 'connectTime');
            $('#grid1').datagrid('showColumn', 'datameaningId');
            $('#grid1').datagrid('showColumn', 'readTime');

            $('#grid1').datagrid('showColumn', 'pid');
            $('#grid1').datagrid('showColumn', 'isparent');
            document.getElementById("chanpinList").innerHTML = htmllist;
            document.getElementById("chanpinList1").innerHTML = htmllist1;

        }


    }


    $('#grid').datagrid({
        toolbar: [{
            iconCls: 'icon-add',
            text: '添加',
            handler: function () {
                var treeselected = $('#tt').tree('getSelected');
                if (treeselected == null) {
                    $('#insertForm1').form('clear');
                    $("#insertDlg1").dialog('open');
                } else {
                    var selected = $('#grid').datagrid('getSelected');
                    //$.messager.alert('My Title',selected.pid,'info');
                    if (selected != null && selected.isparent == 0) {
                        $.messager.alert("提示", "该菜单目录暂时不支持三级以上的菜单", 'warning');
                    } else if (selected != null && selected.pid == 0) {
                        $('#insertForm2').form('clear');
                        $("#insertDlg2").dialog('open');
                    } else {
                        $('#insertForm').form('clear');
                        $("#insertDlg").dialog('open');
                    }
                }
            }
        }, '-', {
            iconCls: 'icon-save',
            text: '行内修改',
            handler: function () {
                var rowData = $('#grid').datagrid('getSelected');
                if (null != rowData) {
                    var a = '1';
                    updateData(a);

                } else {
                    $.messager.alert("提示", "请选中要修改的行", 'error');
                }
            }
        }, '-', {
            iconCls: 'icon-cut',
            text: '删除',
            handler: function () {
                var rowData = $('#tt').tree('getSelected');
                if (null != rowData) {

                    deleteData(rowData.id);

                } else {
                    $.messager.alert("提示", "请选中要删除数据", 'error');
                }
            }
        }, '-', {
            iconCls: 'icon-add',
            text: '批量添加',
            handler: function () {
                var treeselected = $('#tt').tree('getSelected');
                if (treeselected == null) {
                    $('#batchForm').form('clear');
                    $("#batchDlg").dialog('open');
                } else {
                    var selected = $('#grid').datagrid('getSelected');
                    //$.messager.alert('My Title',selected.pid,'info');
                    if (selected.isparent == 0) {
                        $.messager.alert("提示", "该菜单目录暂时不支持三级以上菜单的添加", 'warning');
                    } else if (selected.pid == 0) {
                        $('#batchForm').form('clear');
                        $("#batchDlg").dialog('open');
                    } else {
                        $('#batchForm').form('clear');
                        $("#batchDlg").dialog('open');
                    }
                }
            }
        }],
        onDblClickRow: function (rowIndex, rowData) {
        }
    });


}


function insertData(a) {
    if (a == '1') {
        var rowData = $('#grid').datagrid('getSelected');
        var pid = '';
        var location = '';
        var name = '';
        var zhuangtai = '';
        var model = '';
        var connectTime = '';
        var readTime = '';
        var installation = '';
        var way = '';
        var address = '';
        var port = '';
        var portName = '';
        var datameaningId = '';
        var boxId = '';
        var comment = '';
        var slaveId = '';
        var boto = '';
        var isparent = '';
        var formData = $('#insertForm').serializeJSON();
        var formData1 = $('#insertForm1').serializeJSON();
        var formData2 = $('#insertForm2').serializeJSON();
        if ($('#tt').tree('getSelected') == null) {
            pid = '0';
            name = formData1.name;
            location = formData1.name;
            isparent = '1';
        } else {
            var chanpin = null
            var id = null;
            if (rowData != null) id = '' + rowData.id;
            $.ajax({
                url: "findChanpinById",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {"id": id},
                dataType: "json",
                success: function (data) {
                    chanpin = data;
                }
            });

            if (chanpin != null && chanpin.pid == 0) {
                rowData = $('#grid').datagrid('getSelected');
                pid = rowData.id;
                //$.messager.alert("消息提醒",pid, "warning");
                //var data1=admin.findChanpinById(pid);
                //location=data1.getLocation();
                //model=data1.getModel();
                isparent = '1';
                name = formData2.name;
                datameaningId = formData2.datameaningId;
                model = formData2.model;

            } else {
                rowData = $('#grid').datagrid('getSelected');
                pid = rowData.id;
                var data1 = null
                var id = null;
                if (rowData != null) id = '' + rowData.id;
                $.ajax({
                    url: "findChanpinById",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {"id": id},
                    dataType: "json",
                    success: function (data) {
                        data1 = data;
                    }
                });

                location = data1.location;
                model = data1.model;
                isparent = '0';
                name = formData.name;
                way = formData.way;
                address = formData.address;
                installation = formData.installation;
                port = formData.port;
                portName = formData.portName;
                boxId = formData.boxId;
                connectTime = formData.connectTime;
                readTime = formData.readTime;
                comment = formData.comment;
                slaveId = formData.slaveId;
                boto = formData.boto;
            }
        }
        // var id='';

        zhuangtai = '0';

    } else {
        var rowData = $('#grid1').datagrid('getSelected');
        var chanpin = admin.findChanpinById(rowData.id);
        var pid = '';
        var location = '';
        var name = '';
        var zhuangtai = '';
        var model = '';
        var connectTime = '';
        var readTime = '';
        var installation = '';
        var way = '';
        var address = '';
        var port = '';
        var portName = '';
        var datameaningId = '';
        var boxId = '';
        var comment = '';
        var slaveId = '';
        var boto = '';
        var isparent = '';
        var formData = $('#insertFormTwo').serializeJSON();
        var formData1 = $('#insertFormTwo1').serializeJSON();
        var formData2 = $('#insertFormTwo2').serializeJSON();
        if ($('#tt').tree('getSelected') == null) {

            pid = '0';
            name = formData1.name;
            location = formData1.name;
            isparent = '1';
        } else {
//	var chanpin=admin.findChanpinById(rowData.id);
            var chanpin = null
            id = '' + rowData.id
            $.ajax({
                url: "findChanpinById",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {"id": id},
                dataType: "json",
                success: function (data) {
                    chanpin = data;
                }
            });

            if (chanpin.getPid() == 0) {
                rowData = $('#grid').datagrid('getSelected');
                pid = rowData.id;
                //$.messager.alert("消息提醒",pid, "warning");
                //var data1=admin.findChanpinById(pid);
                //location=data1.getLocation();
                //model=data1.getModel();
                isparent = '1';
                name = formData2.name;
                datameaningId = formData2.datameaningId;
                model = formData2.model;

            } else {
                rowData = $('#grid').datagrid('getSelected');
                pid = rowData.id;
                //	$.messager.alert("消息提醒",pid, "warning");
//		var data1=admin.findChanpinById(pid);
                var data1 = null
                id = '' + pid
                $.ajax({
                    url: "findChanpinById",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {"id": id},
                    dataType: "json",
                    success: function (data) {
                        data1 = data;
                    }
                });

                location = data1.location;
                model = data1.model;
                isparent = '0';
                name = formData.name;
                way = formData.way;
                address = formData.address;
                installation = formData.installation;
                port = formData.port;
                portName = formData.portName;
                boxId = formData.boxId;
                connectTime = formData.connectTime;
                readTime = formData.readTime;
                comment = formData.comment;
                slaveId = formData.slaveId;
                boto = formData.boto;
            }
        }
        // var id='';

        zhuangtai = '0';

    }
    //var msg="添加失败";
//		msg=admin.addChanpin(name,location,zhuangtai,way,model
//				,installation,address,port,portName,boto,boxId,comment,
//				slaveId,datameaningId,connectTime,readTime,pid,isparent);
    var Name = '' + name;
    var Location = '' + location;
    var Zhuangtai = '' + zhuangtai;
    var Way = '' + way;
    var Model = '' + model;
    var Installation = '' + installation;
    var Address = '' + address;
    var Port = '' + port;
    var PortName = '' + portName;
    var Boto = '' + boto;
    var BoxId = '' + boxId;
    var Comment = '' + comment;
    var SlaveId = '' + slaveId;
    var DatameaningId = '' + datameaningId;
    var ConnectTime = '' + connectTime;
    var ReadTime = '' + readTime;
    var Pid = '' + pid;
    var Isparent = '' + isparent;
    //$.messager.alert("消息提醒",Pid, "warning");
    $.ajax({
        url: "addChanpin",
        type: "post",
        async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
        data: {
            "name": Name, "location": Location, "zhuangtai": Zhuangtai, "way": Way, "model": Model
            , "installation": Installation, "address": Address, "port": Port, "portName": PortName, "boto": Boto
            , "boxId": BoxId, "comment": Comment, "slaveId": SlaveId, "datameaningId": DatameaningId
            , "connectTime": ConnectTime, "readTime": ReadTime, "pid": Pid, "isparent": Isparent
        },
        dataType: "json",
        success: function (rtn) {
            $.messager.alert("提示", rtn.msg, 'info', function () {
                if (rtn.status == 200) {
                    $('#insertDlg').dialog('close');
                    $('#insertDlg1').dialog('close');
                    $('#insertDlg2').dialog('close');
                    $('#insertDlgTwo').dialog('close');
                    $('#insertDlgTwo1').dialog('close');
                    $('#insertDlgTwo2').dialog('close');
                    history.go(0);
                }
            });
        }
    });
}

function deleteData(id) {
    //$.messager.alert("消息提醒",id, "warning");
    $.messager.confirm("确认", "确认要删除吗？", function (yes) {
        if (yes) {
            var Id = '' + id;
            $.ajax({
                url: "deleteChanpin",
                data: {"id": Id},
                dataType: 'json',
                type: 'post',
                success: function (rtn) {

                    $.messager.alert("提示", rtn.msg, 'info', function () {
                        // 刷新表格数据
                        location.reload();
                    });
                }
            });
        }
    });
}


function batchAddData(a) {
    if (a == '1') {
        var rowData;
        var pid = '';
        var datameaningId = '-1';
        var isparent = '';
        var location = '';
        var formData = $('#batchForm').serializeJSON();
        var num = formData.num;

        if ($('#tt').tree('getSelected') == null) {

            pid = '0';
            isparent = '1';
        } else {
            rowData = $('#grid').datagrid('getSelected');
            var data1 = null;
            var Id = '' + rowData.id;
            $.ajax({
                url: "findChanpinById",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {"id": Id},
                dataType: "json",
                success: function (data) {
                    data1 = data;
                }
            });
            //$.messager.alert("消息提醒",data1, "warning");
            pid = rowData.id;
            location = data1.location;
            datameaningId = data1.datameaningId;
            if (data1.pid == 0) {
                isparent = '1';
            } else {
                isparent = '0';
            }

            //name=formData.name;
        }
    } else {
        var rowData;
        var pid = '';
        var isparent = '';
        var location = '';
        var datameaningId = '-1';
        var formData = $('#batchForm1').serializeJSON();
        var num = formData.num;

        if ($('#tt').tree('getSelected') == null) {

            pid = '0';
            isparent = '1';
        } else {
            rowData = $('#grid1').datagrid('getSelected');
            //var data1=admin.findChanpinById(rowData.id);
            var data1 = null;
            var Id = '' + rowData.id;
            $.ajax({
                url: "findChanpinById",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {"id": Id},
                dataType: "json",
                success: function (data) {
                    data1 = data;
                }
            });
            pid = rowData.id;
            isparent = '0';
            location = data1.location;
            datameaningId = data1.datameaningId;
            //name=formData.name;
        }

    }
    //  var msg="";
    var Num = '' + num;
    Location = '' + location;
    DatameaningId = '' + datameaningId;
    Pid = '' + pid;
    Isparent = '' + isparent;
    $.ajax({
        url: "batchAddChanpin",
        type: "post",
        async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
        data: {"num": Num, "location": Location, "datameaningId": DatameaningId, "pid": Pid, "isparent": Isparent},
        dataType: "json",
        success: function (rtn) {
            $.messager.alert("提示", rtn.msg, 'info', function () {
                if (rtn.status == 200) {
                    // 成功的话，我们要关闭窗口
                    $('#batchDlg1').dialog('close');
                    // 刷新表格数据
                    history.go(0);
                }
            });
        }
    });


}

function updateData(a) {
    if (a == '1') {
        var rowData1 = $('#grid').datagrid('getSelected');
        var id = rowData1.id;
        var pid = rowData1.pid;
        var name = '';
        var location = ''
        var model = '';
        var installation = '';
        var address = '';
        var connectTime = '';
        var readTime = '';
        var port = '';
        var portName = '';
        var boxId = '';
        var comment = '';
        var slaveId = '';
        var boto = '';
        var datameaningId = '';
        var way = '';
        var zhuangtai = '';
        var isparent = rowData1.isparent;

        var a1 = 1 + '' + rowData1.id;
        var a2 = 2 + '' + rowData1.id;
        var a3 = 3 + '' + rowData1.id;
        var a4 = 4 + '' + rowData1.id;
        var a5 = 5 + '' + rowData1.id;
        var a6 = 6 + '' + rowData1.id;
        var a7 = 7 + '' + rowData1.id;
        var a8 = 8 + '' + rowData1.id;
        var a9 = 9 + '' + rowData1.id;
        var a10 = 10 + '' + rowData1.id;
        var a11 = 11 + '' + rowData1.id;
        var a12 = 12 + '' + rowData1.id;
        var a13 = 13 + '' + rowData1.id;
        var a14 = 14 + '' + rowData1.id;
        var a15 = 15 + '' + rowData1.id;
        var a16 = 16 + '' + rowData1.id;
        name = $('input[name=' + a1 + ']').val();
        location = $('input[name=' + a2 + ']').val();
        zhuangtai = $('input[name=' + a3 + ']').val();
        model = $('input[name=' + a4 + ']').val();
        installation = $('input[name=' + a5 + ']').val();
        slaveId = $('input[name=' + a6 + ']').val();
        way = $('input[name=' + a7 + ']').val();
        address = $('input[name=' + a8 + ']').val();
        port = $('input[name=' + a9 + ']').val();
        connectTime = $('input[name=' + a10 + ']').val();
        readTime = $('input[name=' + a11 + ']').val();
        portName = $('input[name=' + a12 + ']').val();
        boto = $('input[name=' + a13 + ']').val();
        boxId = $('input[name=' + a14 + ']').val();
        comment = $('input[name=' + a15 + ']').val();
        datameaningId = $('input[name=' + a16 + ']').val();
        //var msg="";
//		msg=admin.updateChanpin(id,name,location,zhuangtai,model,installation,way,address
//				,port,portName,boto,boxId,comment,slaveId,datameaningId,connectTime,readTime,pid,isparent);

        Id = '' + id
        Name = '' + name
        Location = '' + location
        Zhuangtai = '' + zhuangtai
        Way = '' + way
        Model = '' + model
        Installation = '' + installation
        Address = '' + address
        Port = '' + port
        PortName = '' + portName
        Boto = '' + boto
        BoxId = '' + boxId
        Comment = '' + comment
        SlaveId = '' + slaveId
        DatameaningId = '' + datameaningId
        ConnectTime = '' + connectTime
        ReadTime = '' + readTime
        Pid = '' + pid
        Isparent = '' + isparent
        $.ajax({
            url: "updateChanpin",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {
                "id": Id, "name": Name, "location": Location, "zhuangtai": Zhuangtai, "way": Way, "model": Model
                , "installation": Installation, "address": Address, "port": Port, "portName": PortName, "boto": Boto
                , "boxId": BoxId, "comment": Comment, "slaveId": SlaveId, "datameaningId": DatameaningId
                , "connectTime": ConnectTime, "readTime": ReadTime, "pid": Pid, "isparent": Isparent
            },
            dataType: "json",
            success: function (rtn) {
                $.messager.alert("提示", rtn.msg, 'info', function () {
                    if (rtn.status == 200) {
                        // 成功的话，我们要关闭窗口
                        $('#updateDlg1').dialog('close');
                        // 刷新表格数据
                        history.go(0);
                    }
                });
            }
        });

    } else {
        var count1 = 0;

        var checkedItems = $('#grid1').datagrid('getChecked');
        var selected1 = [];
        $.each(checkedItems, function (index, item) {
            selected1.push(item);
        });
        //$.messager.alert("鎻愮ず", "selected1.length"+selected1.length, 'error');
        for (var i = 0; i < selected1.length; i++) {
            var rowData1 = selected1[i];
            var id = rowData1.id;
            var pid = rowData1.pid;
            var name = '';
            var location = '';
            var model = '';
            var installation = '';
            var address = '';
            var port = '';
            var portName = '';
            var boxId = '';
            var comment = '';
            var slaveId = '';
            var connectTime = '';
            var readTime = '';
            var boto = '';
            var datameaningId = '';
            var way = '';
            var zhuangtai = '';
            var isparent = rowData1.isparent;

            var a1 = 1 + '' + rowData1.id + 2;
            var a2 = 2 + '' + rowData1.id + 2;
            var a3 = 3 + '' + rowData1.id + 2;
            var a4 = 4 + '' + rowData1.id + 2;
            var a5 = 5 + '' + rowData1.id + 2;
            var a6 = 6 + '' + rowData1.id + 2;
            var a7 = 7 + '' + rowData1.id + 2;
            var a8 = 8 + '' + rowData1.id + 2;
            var a9 = 9 + '' + rowData1.id + 2;
            var a10 = 10 + '' + rowData1.id + 2;
            var a11 = 11 + '' + rowData1.id + 2;
            var a12 = 12 + '' + rowData1.id + 2;
            var a13 = 13 + '' + rowData1.id + 2;
            var a14 = 14 + '' + rowData1.id + 2;
            var a15 = 15 + '' + rowData1.id + 2;
            var a16 = 16 + '' + rowData1.id + 2;

            name = $('input[name=' + a1 + ']').val();
            location = $('input[name=' + a2 + ']').val();
            zhuangtai = $('input[name=' + a3 + ']').val();
            model = $('input[name=' + a4 + ']').val();
            installation = $('input[name=' + a5 + ']').val();
            slaveId = $('input[name=' + a6 + ']').val();
            way = $('input[name=' + a7 + ']').val();
            address = $('input[name=' + a8 + ']').val();
            port = $('input[name=' + a9 + ']').val();
            connectTime = $('input[name=' + a10 + ']').val();
            readTime = $('input[name=' + a11 + ']').val();
            portName = $('input[name=' + a12 + ']').val();
            boto = $('input[name=' + a13 + ']').val();
            boxId = $('input[name=' + a14 + ']').val();
            comment = $('input[name=' + a15 + ']').val();
            datameaningId = $('input[name=' + a16 + ']').val();
            //var msg="";
//				msg=admin.updateChanpin(id,name,location,zhuangtai,model,installation,way,address,port,portName,boto,boxId,comment,slaveId,datameaningId,connectTime,readTime,pid,isparent);
            Id = '' + id
            Name = '' + name
            Location = '' + location
            Zhuangtai = '' + zhuangtai
            Way = '' + way
            Model = '' + model
            Installation = '' + installation
            Address = '' + address
            Port = '' + port
            PortName = '' + portName
            Boto = '' + boto
            BoxId = '' + boxId
            Comment = '' + comment
            SlaveId = '' + slaveId
            DatameaningId = '' + datameaningId
            ConnectTime = '' + connectTime
            ReadTime = '' + readTime
            Pid = '' + pid
            Isparent = '' + isparent
            $.ajax({
                url: "updateChanpin",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {
                    "id": Id, "name": Name, "location": Location, "zhuangtai": Zhuangtai, "way": Way, "model": Model
                    , "installation": Installation, "address": Address, "port": Port, "portName": PortName, "boto": Boto
                    , "boxId": BoxId, "comment": Comment, "slaveId": SlaveId, "datameaningId": DatameaningId
                    , "connectTime": ConnectTime, "readTime": ReadTime, "pid": Pid, "isparent": Isparent
                },
                dataType: "json",
                success: function (rtn) {
                    if (rtn.status == 200) {
                        count1 += 1;
                    }
                }
            });


        }
        if (count1 == selected1.length) {
            $.messager.confirm("提示", "批量修改成功", function (yes) {
                history.go(0);
            })
        } else {
            $.messager.confirm(("提示", "批量修改失败", function (yes) {
                history.go(0);
            }))
        }


    }
}
