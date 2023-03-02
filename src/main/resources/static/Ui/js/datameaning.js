$(function () {

    // 添加数据对话框
    $('#insertDlg').dialog({
        title: '添加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                // 访问后台数据
                var a = '1';
                insertData(a);
            }
        }, {
            text: '关闭',
            handler: function () {
                // 关闭对话框
                $("#insertDlg").dialog('close');
            }
        }]
    });


    $('#insertDlgHold').dialog({
        title: '添加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                // 访问后台数据
                var a = '1';
                insertData(a);
            }
        }, {
            text: '关闭',
            handler: function () {
                // 关闭对话框
                $("#insertDlgHold").dialog('close');
            }
        }]
    });


    $('#insertDlgCoil').dialog({
        title: '添加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                // 访问后台数据
                var a = '1';
                insertData(a);
            }
        }, {
            text: '关闭',
            handler: function () {
                // 关闭对话框
                $("#insertDlgCoil").dialog('close');
            }
        }]
    });

    $('#insertDlgInit').dialog({
        title: '添加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                var pid = '0';
                var bit = '';
                var name = '';
                var type = '';
                var functionId = '';
                var isBaojingPanduan = '';
                var isShown = '';
                var byteNum = '';
                var byteAddress = '';
                var bitAddress = '';
                var isInput = '';
                var functionCode = '';
                var isHex = '';
                var scale = '';
                var unit = '';
                var isPositive = '';
                var isJiexi = '';
                var gongshi = '';
                var isPanduan = '';
                var isBaojing = '';
                var isRecord = '';
                var pattern = '';
                var zero = '';
                var first = '';
                var isparent = '1';
                var formData4 = $('#insertFormInit').serializeJSON();
                name = formData4.name;

                var msg = "添加失败";
                msg = admin.addDatameaning(name, type, functionId, byteAddress, byteNum, bitAddress, isInput, functionCode, isHex, scale, unit, isPositive, isJiexi, gongshi, isPanduan, isBaojing, isBaojingPanduan, isShown, isRecord, pattern, zero, first, pid, isparent, bit);
                $('#insertDlgInit').dialog('close');
                $.messager.confirm("提示", msg, function (yes) {

                    history.go(0);//强制刷新
                })
                //$.messager.alert("提示", "formData4："+formData4.name, 'error');
            }
        }, {
            text: '关闭',
            handler: function () {
                // 关闭对话框
                $("#insertDlgInit").dialog('close');
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
                // 访问后台数据
                var a = '1';
                insertData(a);
            }
        }, {
            text: '关闭',
            handler: function () {
                // 关闭对话框
                $("#insertDlg2").dialog('close');
            }
        }]
    });

    $('#insertDlg3').dialog({
        title: '添加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                // 访问后台数据
                var a = '1';
                insertData(a);

            }
        }, {
            text: '关闭',
            handler: function () {
                // 关闭对话框
                $("#insertDlg3").dialog('close');
            }
        }]
    });

    // 添加数据对话框
    $('#insertDlg1').dialog({
        title: '添加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                // 访问后台数据
                var a = '2';
                insertData(a);
            }
        }, {
            text: '关闭',
            handler: function () {
                // 关闭对话框
                $("#insertDlg1").dialog('close');
            }
        }]
    });


    $('#insertDlgHold1').dialog({
        title: '添加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                // 访问后台数据
                var a = '2';
                insertData(a);
            }
        }, {
            text: '关闭',
            handler: function () {
                // 关闭对话框
                $("#insertDlgHold1").dialog('close');
            }
        }]
    });


    $('#insertDlgCoil1').dialog({
        title: '添加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                // 访问后台数据
                var a = '2';
                insertData(a);
            }
        }, {
            text: '关闭',
            handler: function () {
                // 关闭对话框
                $("#insertDlgCoil1").dialog('close');
            }
        }]
    });

    $('#insertDlgInit1').dialog({
        title: '添加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                var pid = '0';
                var isPaint = '';
                //var chanpinId='';
                var bit = '';
                //var locationId='';
                var name = '';
                var type = '';
                var functionId = '';
                var isBaojingPanduan = '';
                var isShown = '';

                var byteNum = '';
                var byteAddress = '';
                var bitAddress = '';
                var isInput = '';
                var functionCode = '';
                var isHex = '';
                var scale = '';
                var unit = '';
                var isPositive = '';
                var isJiexi = '';
                var gongshi = '';
                var isPanduan = '';
                var isBaojing = '';
                //var model='';
                var pattern = '';
                var isRecord = '';
                var zero = '';
                var first = '';
                var isparent = '1';
                var formData4 = $('#insertFormInit1').serializeJSON();
                name = formData4.name;
                locationId = formData4.locationId;
                model = formData4.model;
                $.ajax({
                    url: "addDatameaning",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {
                        "name": name, "type": type, "functionId": functionId, "byteAddress": byteAddress,
                        "byteNum": byteNum, "bitAddress": bitAddress, "isInput": isInput, "functionCode": functionCode,
                        "isHex": isHex, "scale": scale, "unit": unit, "isPositive": isPositive, "isPaint": isPaint,
                        "isJiexi": isJiexi, "gongshi": gongshi, "isPanduan": isPanduan, "isBaojing": isBaojing,
                        "isBaojingPanduan": isBaojingPanduan, "isShown": isShown, "isRecord": isRecord,
                        "pattern": pattern, "zero": zero, "first": first, "pid": pid, "isparent": isparent, "bit": bit
                    },
                    dataType: "json",
                    success: function (rtn) {
                        $.messager.alert("提示", rtn.msg, 'info', function () {
                            if (rtn.status == 200) {
                                $('#insertDlgInit').dialog('close');
                                history.go(0);
                            }
                        });
                    }
                });//$('#insertDlg').dialog('close');

                //$.messager.alert("提示", "formData4："+formData4.name, 'error');
            }
        }, {
            text: '关闭',
            handler: function () {
                // 关闭对话框
                $("#insertDlgInit1").dialog('close');
            }
        }]
    });

    $('#insertDlg22').dialog({
        title: '添加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                // 访问后台数据
                var a = '2';
                insertData(a);
            }
        }, {
            text: '关闭',
            handler: function () {
                // 关闭对话框
                $("#insertDlg22").dialog('close');
            }
        }]
    });

    $('#insertDlg33').dialog({
        title: '添加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                // 访问后台数据
                var a = '2';
                insertData(a);

            }
        }, {
            text: '关闭',
            handler: function () {
                // 关闭对话框
                $("#insertDlg33").dialog('close');
            }
        }]
    });

    // 批量添加数据对话框
    $('#batchDlg').dialog({
        title: '批量增加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                // 访问后台数据
                var a = '1';
                batchAddData(a);
            }
        }, {
            text: '关闭',
            handler: function () {
                // 关闭对话框
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
                // 访问后台数据
                var a = '1';
                batchAddData(a);
            }
        }, {
            text: '关闭',
            handler: function () {
                // 关闭对话框
                $("#batchDlg1").dialog('close');
            }
        }]
    });

    $('#batchDlgTwo').dialog({
        title: '批量增加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                // 访问后台数据
                var a = '2';
                batchAddData(a);
            }
        }, {
            text: '关闭',
            handler: function () {
                // 关闭对话框
                $("#batchDlgTwo").dialog('close');
            }
        }]
    });

    $('#batchDlgTwo1').dialog({
        title: '批量增加数据',
        width: 400,
        height: 400,
        closed: true,
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                // 访问后台数据
                var a = '2';
                batchAddData(a);
            }
        }, {
            text: '关闭',
            handler: function () {
                // 关闭对话框
                $("#batchDlgTwo1").dialog('close');
            }
        }]
    });
});


/**
 * 处理左侧菜单
 */
function disposeTree() {
    // 加载左侧菜单
    $('#tt').tree({
        "state": "closed",
        onClick: function (node) {
            // 显示子菜单到datagrid
            loadDataGrid(node.id);
        }
    });
}

/**
 * 加载表格数据 menuid：菜单id
 */
function loadDataGrid(id) {
    var htmllist = "";
    var htmllist1 = "";
    document.getElementById("div2").style.display = "none";
    /*   for (var i=0;i<1;i++)
        {
			window.location.reload();
		}*/
    //document.getElementById("datameaningList").innerHTML=htmllist;
    //document.getElementById("datameaningList1").innerHTML="";

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
        //singleSelect : true
        //multiple:'true'
    });

    $('#grid1').datagrid('hideColumn', 'id');
    $('#grid1').datagrid('hideColumn', 'name');
    $('#grid1').datagrid('hideColumn', 'type');
    $('#grid1').datagrid('hideColumn', 'functionId');
    $('#grid1').datagrid('hideColumn', 'byteNum');
    $('#grid1').datagrid('hideColumn', 'byteAddress');
    $('#grid1').datagrid('hideColumn', 'bitAddress');
    $('#grid1').datagrid('hideColumn', 'pattern');
    $('#grid1').datagrid('hideColumn', 'zero');
    $('#grid1').datagrid('hideColumn', 'first');
    $('#grid1').datagrid('hideColumn', 'isInput');
    $('#grid1').datagrid('hideColumn', 'functionCode');
    $('#grid1').datagrid('hideColumn', 'isHex');
    $('#grid1').datagrid('hideColumn', 'scale');
    $('#grid1').datagrid('hideColumn', 'unit');
    $('#grid1').datagrid('hideColumn', 'isPositive');
    $('#grid1').datagrid('hideColumn', 'isJiexi');
    $('#grid1').datagrid('hideColumn', 'gongshi');
    $('#grid1').datagrid('hideColumn', 'isPanduan');
    $('#grid1').datagrid('hideColumn', 'isBaojing');
    $('#grid1').datagrid('hideColumn', 'isBaojingPanduan');
    $('#grid1').datagrid('hideColumn', 'isRecord');
    $('#grid1').datagrid('hideColumn', 'isShown');
    $('#grid1').datagrid('hideColumn', 'bit');
    $('#grid1').datagrid('hideColumn', 'pid');
    $('#grid1').datagrid('hideColumn', 'isparent');

    if (id != "-1") {
        document.getElementById("div2").style.display = "block";
        //var list=admin.findDatameaningListById(id);
        var list = null;
        $.ajax({
            url: "findDatameaningListById",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"id": id},
            dataType: "json",
            success: function (data) {
                list = data;
            }
        });
        //var datameaning=admin.findDatameaningById(id);
        var datameaning = null;
        $.ajax({
            url: "findDatameaningById",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"id": id},
            dataType: "json",
            success: function (data) {
                datameaning = data;
            }
        });
        //var pardata=admin.findDatameaningById(datameaning.getPid());
        var pardata = null;
        var dpid = datameaning.pid;
        $.ajax({
            url: "findDatameaningById",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"id": dpid},
            dataType: "json",
            success: function (data) {
                pardata = data;
            }
        });
        //var list1=admin.getAllSons2(id);
        var list1 = null;
        $.ajax({
            url: "getAllSons2",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"pid": id},
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
                    if (treeselected == null) {//打开一级的添加窗口
                        $('#insertFormInit1').form('clear');
                        $("#insertDlgInit1").dialog('open');
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
                            //var par1=admin.findDatameaningById(selected.id);
                            var par1 = null;
                            var seid = selected.id;
                            $.ajax({
                                url: "findDatameaningById",
                                type: "post",
                                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                                data: {"id": seid},
                                dataType: "json",
                                success: function (data) {
                                    par1 = data;
                                }
                            });
                            if (selected.isparent == 0) {//提示四级菜单下无法增加
                                $.messager.alert("提示", "该菜单目录暂时不支持四级以上的菜单", 'warning');
                            } else {
                                var pid = selected.pid;
                                // var par=admin.findDatameaningById(pid);
                                var par = null;
                                $.ajax({
                                    url: "findDatameaningById",
                                    type: "post",
                                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                                    data: {"id": pid},
                                    dataType: "json",
                                    success: function (data) {
                                        par = data;
                                    }
                                });
                                if (selected.pid == 0) {
                                    $('#insertForm22').form('clear');
                                    $("#insertDlg22").dialog('open');
                                }
                                    //再判断三级
                                /*	var pid1=par.getPid();
						$.messager.alert("提示",pid1, 'error');*/
                                else if (par.pid == 0) {
                                    $('#insertForm33').form('clear');
                                    $("#insertDlg33").dialog('open');
                                } else {//再处理四级
                                    //判断是哪三种类型的寄存器
                                    if (par1.type == 1) {
                                        $('#insertForm1').form('clear');
                                        $("#insertDlg1").dialog('open');
                                    } else if (par1.type == 2) {
                                        $('#insertFormHold1').form('clear');
                                        $("#insertDlgHold1").dialog('open');
                                    } else {
                                        $('#insertFormCoil1').form('clear');
                                        $("#insertDlgCoil1").dialog('open');
                                    }
                                }
                            }
                        }
                    }
                }
            }, '-', {
                iconCls: 'icon-cut',
                text: '删除',
                handler: function () {
                    // 删除
                    //var rowData = $('#grid1').datagrid('getSelected');;
                    //$.messager.alert("提示", "selected1.id: "+rowData.id, 'warning');
                    //$.messager.alert("提示", "selected1.name: "+rowData.name, 'warning');
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
                    // 打开添加窗口
                    var treeselected = $('#tt').tree('getSelected');
                    if (treeselected == null) {
                        $('#batchFormTwo').form('clear');
                        $("#batchDlgTwo").dialog('open');
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
                                $.messager.alert("提示", "该菜单目录暂时不支持四级以上菜单的添加", 'warning');
                            } else {
                                $('#batchFormTwo1').form('clear');
                                $("#batchDlgTwo1").dialog('open');
                            }
                        }
                    }
                }
            }, '-', {
                iconCls: 'icon-save',
                text: '行内修改',
                handler: function () {
                    // 删除
                    var rowData = $('#grid1').datagrid('getSelected');
                    ;
                    if (null != rowData) {
                        var a = '2';
                        updateData1(a);
                    } else {
                        $.messager.alert("提示", "请选中要修改的数据", 'error');
                    }
                }
            }], onDblClickRow: function (rowIndex, rowData) {

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
            if (list[i].type != null) {
                htmllist += list[i].type + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 3 + '' + list[i].id + " name=" + 3 + '' + list[i].id + " value=";
            if (list[i].functionId != null) {
                htmllist += list[i].functionId + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 4 + '' + list[i].id + " name=" + 4 + '' + list[i].id + " value=";
            if (list[i].byteAddress != null) {
                htmllist += list[i].byteAddress + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 5 + '' + list[i].id + " name=" + 5 + '' + list[i].id + " value=";
            if (list[i].byteNum != null) {
                htmllist += list[i].byteNum + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }

            htmllist +=
                "<td><input id=" + 6 + '' + list[i].id + " name=" + 6 + '' + list[i].id + " value=";
            if (list[i].bitAddress != null) {
                htmllist += list[i].bitAddress + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 7 + '' + list[i].id + " name=" + 7 + '' + list[i].id + " value=";
            if (list[i].pattern != null) {
                htmllist += list[i].pattern + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 8 + '' + list[i].id + " name=" + 8 + '' + list[i].id + " value=";
            if (list[i].zero != null) {
                htmllist += list[i].zero + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 9 + '' + list[i].id + " name=" + 9 + '' + list[i].id + " value=";
            if (list[i].first != null) {
                htmllist += list[i].first + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 10 + '' + list[i].id + " name=" + 10 + '' + list[i].id + " value=";
            if (list[i].isInput != null) {
                htmllist += list[i].isInput + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 11 + '' + list[i].id + " name=" + 11 + '' + list[i].id + " value=";
            if (list[i].functionCode != null) {
                htmllist += list[i].functionCode + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 12 + '' + list[i].id + " name=" + 12 + '' + list[i].id + " value=";
            if (list[i].isHex != null) {
                htmllist += list[i].isHex + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 13 + '' + list[i].id + " name=" + 13 + '' + list[i].id + " value=";
            if (list[i].scale != null) {
                htmllist += list[i].scale + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 14 + '' + list[i].id + " name=" + 14 + '' + list[i].id + " value=";
            if (list[i].unit != null) {
                htmllist += list[i].unit + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 15 + '' + list[i].id + " name=" + 15 + '' + list[i].id + " value=";
            if (list[i].isPositive != null) {
                htmllist += list[i].isPositive + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 16 + '' + list[i].id + " name=" + 16 + '' + list[i].id + " value=";
            if (list[i].isJiexi != null) {
                htmllist += list[i].isJiexi + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 17 + '' + list[i].id + " name=" + 17 + '' + list[i].id + " value=";
            if (list[i].gongshi != null) {
                htmllist += list[i].gongshi + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 18 + '' + list[i].id + " name=" + 18 + '' + list[i].id + " value=";
            if (list[i].isPanduan != null) {
                htmllist += list[i].isPanduan + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 19 + '' + list[i].id + " name=" + 19 + '' + list[i].id + " value=";
            if (list[i].isBaojing != null) {
                htmllist += list[i].isBaojing + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 20 + '' + list[i].id + " name=" + 20 + '' + list[i].id + " value=";
            if (list[i].isBaojingPanduan != null) {
                htmllist += list[i].isBaojingPanduan + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 21 + '' + list[i].id + " name=" + 21 + '' + list[i].id + " value=";
            if (list[i].isShown != null) {
                htmllist += list[i].isShown + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 22 + '' + list[i].id + " name=" + 22 + '' + list[i].id + " value=";
            if (list[i].isRecord != null) {
                htmllist += list[i].isRecord + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 23 + '' + list[i].id + " name=" + 23 + '' + list[i].id + " value=";
            if (list[i].isPaint != null) {
                htmllist += list[i].isPaint + "></input></td>";
            } else {
                htmllist += "></input></td>";
            }
            htmllist +=
                "<td><input id=" + 24 + '' + list[i].id + " name=" + 24 + '' + list[i].id + " value=";
            if (list[i].bit != null) {
                htmllist += list[i].bit + "></input></td>";
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
            if (list1[i].type != null) {
                htmllist1 += list1[i].type + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 3 + '' + list1[i].id + " name=" + 3 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].functionId != null) {
                htmllist1 += list1[i].functionId + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 4 + '' + list1[i].id + " name=" + 4 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].byteAddress != null) {
                htmllist1 += list1[i].byteAddress + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 5 + '' + list1[i].id + " name=" + 5 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].byteNum != null) {
                htmllist1 += list1[i].byteNum + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 6 + '' + list1[i].id + " name=" + 6 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].bitAddress != null) {
                htmllist1 += list1[i].bitAddress + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 7 + '' + list1[i].id + " name=" + 7 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].pattern != null) {
                htmllist1 += list1[i].pattern + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 8 + '' + list1[i].id + " name=" + 8 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].zero != null) {
                htmllist1 += list1[i].zero + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 9 + '' + list1[i].id + " name=" + 9 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].first != null) {
                htmllist1 += list1[i].first + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 10 + '' + list1[i].id + " name=" + 10 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].isInput != null) {
                htmllist1 += list1[i].isInput + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 11 + '' + list1[i].id + " name=" + 11 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].functionCode != null) {
                htmllist1 += list1[i].functionCode + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 12 + '' + list1[i].id + " name=" + 12 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].isHex != null) {
                htmllist1 += list1[i].isHex + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 13 + '' + list1[i].id + " name=" + 13 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].scale != null) {
                htmllist1 += list1[i].scale + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 14 + '' + list1[i].id + " name=" + 14 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].unit != null) {
                htmllist1 += list1[i].unit + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 15 + '' + list1[i].id + " name=" + 15 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].isPositive != null) {
                htmllist1 += list1[i].isPositive + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 16 + '' + list1[i].id + " name=" + 16 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].isJiexi != null) {
                htmllist1 += list1[i].isJiexi + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 17 + '' + list1[i].id + " name=" + 17 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].gongshi != null) {
                htmllist1 += list1[i].gongshi + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 18 + '' + list1[i].id + " name=" + 18 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].isPanduan != null) {
                htmllist1 += list1[i].isPanduan + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 19 + '' + list1[i].id + " name=" + 19 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].isBaojing != null) {
                htmllist1 += list1[i].isBaojing + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 20 + '' + list1[i].id + " name=" + 20 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].isBaojingPanduan != null) {
                htmllist1 += list1[i].isBaojingPanduan + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 21 + '' + list1[i].id + " name=" + 21 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].isShown != null) {
                htmllist1 += list1[i].isShown + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 22 + '' + list1[i].id + " name=" + 22 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].isRecord != null) {
                htmllist1 += list1[i].isRecord + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 23 + '' + list1[i].id + " name=" + 23 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].isPaint != null) {
                htmllist1 += list1[i].isPaint + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }
            htmllist1 +=
                "<td><input id=" + 24 + '' + list1[i].id + " name=" + 24 + '' + list1[i].id + '' + 2 + " value=";
            if (list1[i].bit != null) {
                htmllist1 += list1[i].bit + "></input></td>";
            } else {
                htmllist1 += "></input></td>";
            }

            htmllist1 +=
                "<td>" + list1[i].pid + "</td>" +
                "<td>" + list1[i].isparent + "</td>" +
                "</tr>";
        }

        if (datameaning.pid == 0) {//点击一级菜单，显示所有二级子菜单

            $('#grid').datagrid('showColumn', 'type');
            $('#grid').datagrid('showColumn', 'functionId');
            $('#grid').datagrid('showColumn', 'byteNum');
            $('#grid').datagrid('showColumn', 'byteAddress');
            $('#grid').datagrid('showColumn', 'bitAddress');
            $('#grid').datagrid('showColumn', 'pattern');
            $('#grid').datagrid('showColumn', 'zero');
            $('#grid').datagrid('showColumn', 'first');
            $('#grid').datagrid('showColumn', 'isInput');
            $('#grid').datagrid('showColumn', 'functionCode');
            $('#grid').datagrid('showColumn', 'isHex');
            $('#grid').datagrid('showColumn', 'scale');
            $('#grid').datagrid('showColumn', 'unit');
            $('#grid').datagrid('showColumn', 'isPositive');
            $('#grid').datagrid('showColumn', 'isJiexi');
            $('#grid').datagrid('showColumn', 'gongshi');
            $('#grid').datagrid('showColumn', 'isPanduan');
            $('#grid').datagrid('showColumn', 'isBaojingPanduan');
            $('#grid').datagrid('showColumn', 'isRecord');
            $('#grid').datagrid('showColumn', 'isBaojing');
            $('#grid').datagrid('showColumn', 'isShown');
            $('#grid').datagrid('showColumn', 'isPaint');
            $('#grid').datagrid('showColumn', 'bit');

            $('#grid').datagrid('hideColumn', 'type');
            $('#grid').datagrid('hideColumn', 'functionId');
            $('#grid').datagrid('hideColumn', 'byteAddress');
            $('#grid').datagrid('hideColumn', 'bitAddress');
            $('#grid').datagrid('hideColumn', 'pattern');
            $('#grid').datagrid('hideColumn', 'zero');
            $('#grid').datagrid('hideColumn', 'first');
            $('#grid').datagrid('hideColumn', 'isInput');
            $('#grid').datagrid('hideColumn', 'functionCode');
            $('#grid').datagrid('hideColumn', 'isHex');
            $('#grid').datagrid('hideColumn', 'scale');
            $('#grid').datagrid('hideColumn', 'unit');
            $('#grid').datagrid('hideColumn', 'isPositive');
            $('#grid').datagrid('hideColumn', 'isJiexi');
            $('#grid').datagrid('hideColumn', 'gongshi');
            $('#grid').datagrid('hideColumn', 'isPanduan');
            $('#grid').datagrid('hideColumn', 'isBaojingPanduan');
            $('#grid').datagrid('hideColumn', 'isRecord');
            $('#grid').datagrid('hideColumn', 'isShown');
            $('#grid').datagrid('hideColumn', 'isPaint');
            $('#grid').datagrid('hideColumn', 'isBaojing');
            $('#grid').datagrid('hideColumn', 'bit');
            $('#grid1').datagrid('hideColumn', 'isPaint');
            $('#grid1').datagrid('showColumn', 'id');
            $('#grid1').datagrid('showColumn', 'name');
            $('#grid1').datagrid('showColumn', 'functionId');
            $('#grid1').datagrid('showColumn', 'type');
            $('#grid1').datagrid('showColumn', 'pid');
            $('#grid1').datagrid('showColumn', 'isparent');
            document.getElementById("datameaningList").innerHTML = htmllist;
            document.getElementById("datameaningList1").innerHTML = htmllist1;
        } else if (pardata.pid == 0) {//点击二级菜单，显示所有三级子菜单

            $('#grid').datagrid('showColumn', 'type');
            $('#grid').datagrid('showColumn', 'functionId');
            $('#grid').datagrid('showColumn', 'byteNum');
            $('#grid').datagrid('showColumn', 'byteAddress');
            $('#grid').datagrid('showColumn', 'bitAddress');
            $('#grid').datagrid('showColumn', 'pattern');
            $('#grid').datagrid('showColumn', 'zero');
            $('#grid').datagrid('showColumn', 'first');
            $('#grid').datagrid('showColumn', 'isInput');
            $('#grid').datagrid('showColumn', 'functionCode');
            $('#grid').datagrid('showColumn', 'isHex');
            $('#grid').datagrid('showColumn', 'scale');
            $('#grid').datagrid('showColumn', 'unit');
            $('#grid').datagrid('showColumn', 'isPositive');
            $('#grid').datagrid('showColumn', 'isJiexi');
            $('#grid').datagrid('showColumn', 'gongshi');
            $('#grid').datagrid('showColumn', 'isPanduan');
            $('#grid').datagrid('showColumn', 'isBaojing');
            $('#grid').datagrid('showColumn', 'isBaojingPanduan');
            $('#grid').datagrid('showColumn', 'isRecord');
            $('#grid').datagrid('showColumn', 'isShown');
            $('#grid').datagrid('showColumn', 'isPaint');
            $('#grid').datagrid('showColumn', 'bit');

            $('#grid').datagrid('hideColumn', 'type');
            $('#grid').datagrid('hideColumn', 'functionId');
            $('#grid').datagrid('hideColumn', 'byteNum');
            $('#grid').datagrid('hideColumn', 'byteAddress');
            $('#grid').datagrid('hideColumn', 'bitAddress');
            $('#grid').datagrid('hideColumn', 'pattern');
            $('#grid').datagrid('hideColumn', 'zero');
            $('#grid').datagrid('hideColumn', 'first');
            $('#grid').datagrid('hideColumn', 'isInput');
            $('#grid').datagrid('hideColumn', 'functionCode');
            $('#grid').datagrid('hideColumn', 'isHex');
            $('#grid').datagrid('hideColumn', 'scale');
            $('#grid').datagrid('hideColumn', 'unit');
            $('#grid').datagrid('hideColumn', 'isPositive');
            $('#grid').datagrid('hideColumn', 'isJiexi');
            $('#grid').datagrid('hideColumn', 'gongshi');
            $('#grid').datagrid('hideColumn', 'isPanduan');
            $('#grid').datagrid('hideColumn', 'isBaojing');
            $('#grid').datagrid('hideColumn', 'isBaojingPanduan');
            $('#grid').datagrid('hideColumn', 'isRecord');
            $('#grid').datagrid('hideColumn', 'isPaint');
            $('#grid').datagrid('hideColumn', 'isShown');
            $('#grid').datagrid('hideColumn', 'bit');

            $('#grid1').datagrid('hideColumn', 'isPaint');
            $('#grid1').datagrid('showColumn', 'id');
            $('#grid1').datagrid('showColumn', 'name');
            $('#grid1').datagrid('showColumn', 'pid');
            $('#grid1').datagrid('showColumn', 'isparent');


            document.getElementById("datameaningList").innerHTML = htmllist;
            document.getElementById("datameaningList1").innerHTML = htmllist1;
        } else if (datameaning.isparent == 0) {//点击四级菜单
            //  $(".datagrid-header-check").html("");

            $('#grid').datagrid('showColumn', 'type');
            $('#grid').datagrid('showColumn', 'functionId');
            $('#grid').datagrid('showColumn', 'byteNum');
            $('#grid').datagrid('showColumn', 'byteAddress');
            $('#grid').datagrid('showColumn', 'bitAddress');
            $('#grid').datagrid('showColumn', 'pattern');
            $('#grid').datagrid('showColumn', 'zero');
            $('#grid').datagrid('showColumn', 'first');
            $('#grid').datagrid('showColumn', 'isInput');
            $('#grid').datagrid('showColumn', 'functionCode');
            $('#grid').datagrid('showColumn', 'isHex');
            $('#grid').datagrid('showColumn', 'scale');
            $('#grid').datagrid('showColumn', 'unit');
            $('#grid').datagrid('showColumn', 'isPositive');
            $('#grid').datagrid('showColumn', 'isJiexi');
            $('#grid').datagrid('showColumn', 'gongshi');
            $('#grid').datagrid('showColumn', 'isPanduan');
            $('#grid').datagrid('showColumn', 'isBaojing');
            $('#grid').datagrid('showColumn', 'isBaojingPanduan');
            $('#grid').datagrid('showColumn', 'isRecord');
            $('#grid').datagrid('showColumn', 'isPaint');
            $('#grid').datagrid('showColumn', 'isShown');

            $('#grid').datagrid('showColumn', 'bit');


            $('#grid').datagrid('hideColumn', 'type');
            $('#grid').datagrid('hideColumn', 'functionId');

            if (datameaning.type == 1) {

                $('#grid').datagrid('hideColumn', 'isInput');
                $('#grid').datagrid('hideColumn', 'functionCode');

            } else if (datameaning.type == 2) {

                $('#grid').datagrid('hideColumn', 'byteNum');

                $('#grid').datagrid('hideColumn', 'bitAddress');
                $('#grid').datagrid('hideColumn', 'pattern');
                $('#grid').datagrid('hideColumn', 'zero');
                $('#grid').datagrid('hideColumn', 'first');

                $('#grid').datagrid('hideColumn', 'scale');
                $('#grid').datagrid('hideColumn', 'unit');
                $('#grid').datagrid('hideColumn', 'isPositive');
                $('#grid').datagrid('hideColumn', 'isJiexi');
                $('#grid').datagrid('hideColumn', 'gongshi');
                $('#grid').datagrid('hideColumn', 'isPanduan');
                $('#grid').datagrid('hideColumn', 'isBaojing');
                $('#grid').datagrid('hideColumn', 'isBaojingPanduan');
                $('#grid').datagrid('hideColumn', 'isShown');
                $('#grid').datagrid('hideColumn', 'isRecord');
                $('#grid').datagrid('hideColumn', 'isPaint');
                $('#grid').datagrid('hideColumn', 'bit');
            } else {
                $('#grid').datagrid('hideColumn', 'type');
                $('#grid').datagrid('hideColumn', 'functionId');
                $('#grid').datagrid('hideColumn', 'byteNum');
                $('#grid').datagrid('hideColumn', 'bit');
                $('#grid').datagrid('hideColumn', 'bitAddress');
                $('#grid').datagrid('hideColumn', 'pattern');
                $('#grid').datagrid('hideColumn', 'zero');
                $('#grid').datagrid('hideColumn', 'first');
                $('#grid').datagrid('hideColumn', 'isInput');

                $('#grid').datagrid('hideColumn', 'isHex');
                $('#grid').datagrid('hideColumn', 'scale');
                $('#grid').datagrid('hideColumn', 'unit');
                $('#grid').datagrid('hideColumn', 'isPositive');
                $('#grid').datagrid('hideColumn', 'isJiexi');
                $('#grid').datagrid('hideColumn', 'gongshi');
                $('#grid').datagrid('hideColumn', 'isPanduan');
                $('#grid').datagrid('hideColumn', 'isBaojing');
                $('#grid').datagrid('hideColumn', 'isBaojingPanduan');
                $('#grid').datagrid('hideColumn', 'isShown');
                $('#grid').datagrid('hideColumn', 'isRecord');
                $('#grid').datagrid('hideColumn', 'isPaint');
            }
            document.getElementById("datameaningList").innerHTML = htmllist;
            document.getElementById("div2").style.display = "none";
            //document.getElementById("datameaningList1").innerHTML="";
            //history.go(0);
            //location.reload();

        } else {

            $('#grid').datagrid('showColumn', 'type');
            $('#grid').datagrid('showColumn', 'functionId');
            $('#grid').datagrid('showColumn', 'byteNum');
            $('#grid').datagrid('showColumn', 'byteAddress');
            $('#grid').datagrid('showColumn', 'bitAddress');
            $('#grid').datagrid('showColumn', 'pattern');
            $('#grid').datagrid('showColumn', 'zero');
            $('#grid').datagrid('showColumn', 'first');
            $('#grid').datagrid('showColumn', 'isInput');
            $('#grid').datagrid('showColumn', 'functionCode');
            $('#grid').datagrid('showColumn', 'isHex');
            $('#grid').datagrid('showColumn', 'scale');
            $('#grid').datagrid('showColumn', 'unit');
            $('#grid').datagrid('showColumn', 'isPositive');
            $('#grid').datagrid('showColumn', 'isJiexi');
            $('#grid').datagrid('showColumn', 'gongshi');
            $('#grid').datagrid('showColumn', 'isPanduan');
            $('#grid').datagrid('showColumn', 'isBaojing');
            $('#grid').datagrid('showColumn', 'isPaint');
            $('#grid').datagrid('showColumn', 'bit');

            $('#grid').datagrid('hideColumn', 'type');
            $('#grid').datagrid('hideColumn', 'functionId');
            $('#grid').datagrid('hideColumn', 'byteNum');
            $('#grid').datagrid('hideColumn', 'byteAddress');
            $('#grid').datagrid('hideColumn', 'bitAddress');
            $('#grid').datagrid('hideColumn', 'pattern');
            $('#grid').datagrid('hideColumn', 'zero');
            $('#grid').datagrid('hideColumn', 'first');
            $('#grid').datagrid('hideColumn', 'isInput');
            $('#grid').datagrid('hideColumn', 'functionCode');
            $('#grid').datagrid('hideColumn', 'isHex');
            $('#grid').datagrid('hideColumn', 'scale');
            $('#grid').datagrid('hideColumn', 'unit');
            $('#grid').datagrid('hideColumn', 'isPositive');
            $('#grid').datagrid('hideColumn', 'isJiexi');
            $('#grid').datagrid('hideColumn', 'gongshi');
            $('#grid').datagrid('hideColumn', 'isPanduan');
            $('#grid').datagrid('hideColumn', 'isBaojing');
            $('#grid').datagrid('hideColumn', 'isBaojingPanduan');
            $('#grid').datagrid('hideColumn', 'isRecord');
            $('#grid').datagrid('hideColumn', 'isPaint');
            $('#grid').datagrid('hideColumn', 'isShown');

            $('#grid').datagrid('hideColumn', 'bit');

            if (datameaning.type == 1) {

                $('#grid1').datagrid('showColumn', 'id');
                $('#grid1').datagrid('showColumn', 'name');
                $('#grid1').datagrid('showColumn', 'pid');
                $('#grid1').datagrid('showColumn', 'isparent');
                $('#grid1').datagrid('showColumn', 'byteNum');
                $('#grid1').datagrid('showColumn', 'byteAddress');
                $('#grid1').datagrid('showColumn', 'bitAddress');
                $('#grid1').datagrid('showColumn', 'pattern');
                $('#grid1').datagrid('showColumn', 'zero');
                $('#grid1').datagrid('showColumn', 'first');

                $('#grid1').datagrid('showColumn', 'isHex');
                $('#grid1').datagrid('showColumn', 'scale');
                $('#grid1').datagrid('showColumn', 'unit');
                $('#grid1').datagrid('showColumn', 'isPositive');
                $('#grid1').datagrid('showColumn', 'isJiexi');
                $('#grid1').datagrid('showColumn', 'gongshi');
                $('#grid1').datagrid('showColumn', 'isPanduan');
                $('#grid1').datagrid('showColumn', 'isBaojing');
                $('#grid1').datagrid('showColumn', 'isBaojingPanduan');
                $('#grid1').datagrid('showColumn', 'isShown');
                $('#grid1').datagrid('showColumn', 'isRecord');
                $('#grid1').datagrid('showColumn', 'isPaint');
                $('#grid1').datagrid('showColumn', 'bit');

            } else if (datameaning.type == 2) {
                $('#grid1').datagrid('showColumn', 'id');
                $('#grid1').datagrid('showColumn', 'name');
                $('#grid1').datagrid('showColumn', 'pid');
                $('#grid1').datagrid('showColumn', 'isparent');
                $('#grid1').datagrid('showColumn', 'byteAddress');
                $('#grid1').datagrid('showColumn', 'isHex');
                $('#grid1').datagrid('showColumn', 'isInput');
                $('#grid1').datagrid('showColumn', 'functionCode');
            } else {
                $('#grid1').datagrid('showColumn', 'id');
                $('#grid1').datagrid('showColumn', 'name');
                $('#grid1').datagrid('showColumn', 'byteAddress');
                $('#grid1').datagrid('showColumn', 'pid');
                $('#grid1').datagrid('showColumn', 'isparent');
                $('#grid1').datagrid('showColumn', 'functionCode');
            }
            document.getElementById("datameaningList").innerHTML = htmllist;
            document.getElementById("datameaningList1").innerHTML = htmllist1;

        }


    }

    $('#grid').datagrid({
        toolbar: [{
            iconCls: 'icon-add',
            text: '添加',
            handler: function () {
                var treeselected = $('#tt').tree('getSelected');
                if (treeselected == null) {//打开一级的添加窗口
                    $('#insertFormInit1').form('clear');
                    $("#insertDlgInit1").dialog('open');
                } else {
                    var checkedItems = $('#grid').datagrid('getChecked');
                    var selected1 = [];
                    $.each(checkedItems, function (index, item) {
                        selected1.push(item);
                    });
                    if (selected1.length > 1) {
                        $.messager.alert("提示", "该菜单下不支持多选", 'warning');
                    } else {
                        var selected = selected1[0];
                        //var par1=admin.findDatameaningById(selected.id);
                        var selecid = selected.id;
                        var par1 = null;
                        $.ajax({
                            url: "findDatameaningById",
                            type: "post",
                            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                            data: {"id": selecid},
                            dataType: "json",
                            success: function (data) {
                                par1 = data;
                            }
                        });
                        if (selected.isparent == 0) {//提示四级菜单下无法增加
                            $.messager.alert("提示", "该菜单目录暂时不支持四级以上的菜单", 'warning');
                        } else {
                            //$('#insertForm').form('clear');
                            //$("#insertDlg").dialog('open');
                            //开始判断二级、三级、四级菜单的增加
                            //先判断二级
                            var pid = selected.pid;
                            //   var par=admin.findDatameaningById(pid);
                            var par = null;
                            $.ajax({
                                url: "findDatameaningById",
                                type: "post",
                                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                                data: {"id": pid},
                                dataType: "json",
                                success: function (data) {
                                    par = data;
                                }
                            });

                            if (selected.pid == 0) {
                                $('#insertForm2').form('clear');
                                $("#insertDlg2").dialog('open');
                            }
                                //再判断三级
                            /*	var pid1=par.getPid();
						$.messager.alert("提示",pid1, 'error');*/
                            else if (par.pid == 0) {
                                $('#insertForm3').form('clear');
                                $("#insertDlg3").dialog('open');
                            } else {//再处理四级
                                //判断是哪三种类型的寄存器
                                if (par1.type == 1) {
                                    $('#insertForm').form('clear');
                                    $("#insertDlg").dialog('open');
                                } else if (par1.type == 2) {
                                    $('#insertFormHold').form('clear');
                                    $("#insertDlgHold").dialog('open');
                                } else {
                                    $('#insertFormCoil').form('clear');
                                    $("#insertDlgCoil").dialog('open');
                                }
                            }
                        }
                    }
                }
            }
        }, '-', {
            iconCls: 'icon-cut',
            text: '删除',
            handler: function () {
                // 删除
                var rowData = $('#grid').datagrid('getSelected');
                ;
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
                // 打开添加窗口
                var treeselected = $('#tt').tree('getSelected');
                if (treeselected == null) {
                    $('#batchForm').form('clear');
                    $("#batchDlg").dialog('open');
                } else {
                    var selected = $('#grid').datagrid('getSelected');
                    ;
                    //$.messager.alert('My Title',selected.pid,'info');
                    if (selected.isparent == 0) {
                        $.messager.alert("提示", "该菜单目录暂时不支持四级以上菜单的添加", 'warning');
                    } else {
                        $('#batchForm1').form('clear');
                        $("#batchDlg1").dialog('open');
                    }
                }
            }
        }, '-', {
            iconCls: 'icon-save',
            text: '行内修改',
            handler: function () {
                // 删除
                var rowData = $('#grid').datagrid('getSelected');
                ;
                if (null != rowData) {
                    var a = '1';
                    updateData1(a);
                } else {
                    $.messager.alert("提示", "请选中要修改的数据", 'error');
                }
            }
        }], onDblClickRow: function (rowIndex, rowData) {

        }
    });


}


/**
 * 添加数据
 */
function insertData(a) {
    //var a=$('#grid').datagrid('getSelected');
    //var b=$('#grid').datagrid('getSelected');
    var rowData = "";
    if (a == '1') {
        rowData = $('#grid').datagrid('getSelected');
        // 提交添加数据的表单
        var pid = '';
        var bit = '';
        var isBaojingPanduan = '';
        var isShown = '';
        var name = '';
        var type = '';
        var functionId = '';
        var byteNum = '';
        var byteAddress = '';
        var bitAddress = '';
        var isInput = '';
        var functionCode = '';
        var isHex = '';
        var scale = '';
        var unit = '';
        var isPositive = '';
        var isJiexi = '';
        var gongshi = '';
        var isPanduan = '';
        var isBaojing = '';
        var isRecord = '';
        var isPaint = '';
        var pattern = '';
        var zero = '';
        var first = '';

        var isparent = '';
        var formData1 = $('#insertForm').serializeJSON();
        var formData2 = $('#insertFormHold').serializeJSON();
        var formData3 = $('#insertFormCoil').serializeJSON();
        var formData4 = $('#insertFormInit').serializeJSON();
        var formData5 = $('#insertForm2').serializeJSON();
        var formData6 = $('#insertForm3').serializeJSON();

        //var parents1=admin.findDatameaningById(rowData.id);
        var parents1 = null;
        var roid = rowData.id;
        $.ajax({
            url: "findDatameaningById",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"id": roid},
            dataType: "json",
            success: function (data) {
                parents1 = data;
            }
        });
        //var par=admin.findDatameaningById(rowData.pid);
        var par = null;
        var ropid = rowData.pid;
        $.ajax({
            url: "findDatameaningById",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"id": ropid},
            dataType: "json",
            success: function (data) {
                par = data;
            }
        });
        //$.messager.alert("提示", "par："+par.getId(), 'error');
        //$.messager.alert("提示", "formData4："+formData4.name, 'error');
        //$.messager.alert("提示", "$('#tt').tree('getSelected')："+$('#tt').tree('getSelected'), 'error');
        if ($('#tt').tree('getSelected') == null) {
            //$.messager.alert("提示", "一级", 'error');
            //此处为一级菜单的添加
            pid = '0';
            name = formData4.name;
            //locationId=formData4.locationId;
            //model=formData4.model;
            isparent = '1';
        } else {
            pid = rowData.id;
            //locationId=parents1.getLocationId();
            //chanpinId=parents1.getChanpinId();
            // $.messager.alert("提示", "par.getPid():"+par.getPid(), 'error');
            if (rowData.pid == 0) {//二级菜单
                name = formData5.name;
                type = formData5.type;
                functionId = formData5.functionId;
                isparent = '1';
            } else if (par.pid == 0) {//三级菜单
                name = formData6.name;
                type = parents1.type;
                functionId = parents1.functionId;
                isparent = '1';
            } else {//四级菜单
                isparent = '0';
                //$.messager.alert("提示", "parents1.getType():"+parents1.getType(), 'error');
                if (parents1.type == 1) {
                    name = formData1.name;
                    pattern = formData1.pattern;
                    //startRejister=formData1.startRejister;
                    // rejisterNum=formData1.rejisterNum;
                    byteNum = formData1.byteNum;
                    byteAddress = formData1.byteAddress;
                    isHex = formData1.isHex;
                    scale = formData1.scale;
                    unit = formData1.unit;
                    isPositive = formData1.isPositive;
                    isJiexi = formData1.isJiexi;
                    gongshi = formData1.gongshi;
                    isPanduan = formData1.isPanduan;
                    isBaojing = formData1.isBaojing;
                    isBaojingPanduan = formData1.isBaojingPanduan;
                    isRecord = formData1.isRecord;
                    isPaint = formData1.isPaint;
                    isShown = formData1.isShown;
                    zero = formData1.zero;
                    first = formData1.first;
                    bitAddress = formData1.bitAddress;
                    bit = formData1.bit;
                    type = parents1.type;
                    functionId = parents1.functionId;
                } else if (parents1.type == 2) {
                    name = formData2.name;
                    byteAddress = formData2.byteAddress;
                    isInput = formData2.isInput;
                    functionCode = formData2.functionCode;
                    isHex = formData2.isHex;
                    type = parents1.type;
                    functionId = parents1.functionId;
                } else {
                    name = formData3.name;
                    byteAddress = formData3.byteAddress;
                    functionCode = formData3.functionCode;
                    type = parents1.type;
                    functionId = parents1.functionId;
                }
            }
        }
    } else {

        rowData = $('#grid1').datagrid('getSelected');
        // 提交添加数据的表单
        var pid = '';
        var bit = '';
        //var chanpinId='';
        //var locationId='';
        var isBaojingPanduan = '';
        var isShown = '';
        var name = '';
        var type = '';
        var functionId = '';
        var byteNum = '';
        var byteAddress = '';
        var bitAddress = '';
        var isInput = '';
        var functionCode = '';
        var isHex = '';
        var scale = '';
        var unit = '';
        var isPositive = '';
        var isJiexi = '';
        var gongshi = '';
        var isPanduan = '';
        var isBaojing = '';
        var isRecord = '';
        var isPaint = '';
        var pattern = '';
        var zero = '';
        var first = '';

        var isparent = '';
        var formData1 = $('#insertForm1').serializeJSON();
        var formData2 = $('#insertFormHold1').serializeJSON();
        var formData3 = $('#insertFormCoil1').serializeJSON();
        var formData4 = $('#insertFormInit1').serializeJSON();
        var formData5 = $('#insertForm22').serializeJSON();
        var formData6 = $('#insertForm33').serializeJSON();

        var parents1 = null;
        var roid = rowData.id;
        $.ajax({
            url: "findDatameaningById",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"id": roid},
            dataType: "json",
            success: function (data) {
                parents1 = data;
            }
        });
        //var par=admin.findDatameaningById(rowData.pid);
        var par = null;
        var ropid = rowData.pid;
        $.ajax({
            url: "findDatameaningById",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"id": ropid},
            dataType: "json",
            success: function (data) {
                par = data;
            }
        });
        //$.messager.alert("提示", "par："+par.getId(), 'error');
        //$.messager.alert("提示", "formData4："+formData4.name, 'error');
        //$.messager.alert("提示", "$('#tt').tree('getSelected')："+$('#tt').tree('getSelected'), 'error');
        if ($('#tt').tree('getSelected') == null) {
            //$.messager.alert("提示", "一级", 'error');
            //此处为一级菜单的添加
            pid = '0';
            name = formData4.name;
            //locationId=formData4.locationId;
            //model=forData4.model;
            isparent = '1';
        } else {
            pid = rowData.id;
            //locationId=parents1.getLocationId();
            //chanpinId=parents1.getChanpinId();
            // $.messager.alert("提示", "par.getPid():"+par.getPid(), 'error');
            if (rowData.pid == 0) {//二级菜单
                name = formData5.name;
                type = formData5.type;
                functionId = formData5.functionId;
                isparent = '1';
            } else if (par.id == 0) {//三级菜单
                name = formData6.name;
                type = parents1.type;
                functionId = parents1.functionId;
                isparent = '1';
            } else {//四级菜单
                isparent = '0';
                //$.messager.alert("提示", "parents1.getType():"+parents1.getType(), 'error');
                if (parents1.type == 1) {
                    name = formData1.name;
                    pattern = formData1.pattern;
                    //startRejister=formData1.startRejister;
                    // rejisterNum=formData1.rejisterNum;
                    byteNum = formData1.byteNum;
                    byteAddress = formData1.byteAddress;
                    isHex = formData1.isHex;
                    scale = formData1.scale;
                    unit = formData1.unit;
                    isPositive = formData1.isPositive;
                    isJiexi = formData1.isJiexi;
                    gongshi = formData1.gongshi;
                    isPanduan = formData1.isPanduan;
                    isBaojing = formData1.isBaojing;
                    isShown = formData1.isShown;
                    isBaojingPanduan = formData1.isBaojingPanduan;
                    isRecord = formData1.isRecord;
                    isPaint = formData1.isPaint;
                    zero = formData1.zero;
                    first = formData1.first;
                    bitAddress = formData1.bitAddress;
                    bit = formData1.bit;
                    type = parents1.type;
                    functionId = parents1.functionId;
                } else if (parents1.type == 2) {
                    name = formData2.name;
                    byteAddress = formData2.byteAddress;
                    isInput = formData2.isInput;
                    functionCode = formData2.functionCode;
                    isHex = formData2.isHex;
                    type = parents1.type;
                    functionId = parents1.functionId;
                } else {
                    name = formData3.name;
                    byteAddress = formData3.byteAddress;
                    functionCode = formData3.functionCode;
                    type = parents1.type;
                    functionId = parents1.functionId;
                }
            }
        }

    }
    $.ajax({
        url: "addDatameaning",
        type: "post",
        async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
        data: {
            "name": name, "type": type, "functionId": functionId, "byteAddress": byteAddress,
            "byteNum": byteNum, "bitAddress": bitAddress, "isInput": isInput, "functionCode": functionCode,
            "isHex": isHex, "scale": scale, "unit": unit, "isPositive": isPositive, "isPaint": isPaint,
            "isJiexi": isJiexi, "gongshi": gongshi, "isPanduan": isPanduan, "isBaojing": isBaojing,
            "isBaojingPanduan": isBaojingPanduan, "isShown": isShown, "isRecord": isRecord,
            "pattern": pattern, "zero": zero, "first": first, "pid": pid, "isparent": isparent, "bit": bit
        },
        dataType: "json",
        success: function (rtn) {
            $.messager.alert("提示", rtn.msg, 'info', function () {
                if (rtn.status == 200) {
                    $('#insertDlg').dialog('close');
                    $('#insertDlgHold').dialog('close');
                    $('#insertDlgCoil').dialog('close');
                    $('#insertDlg1').dialog('close');
                    $('#insertDlg2').dialog('close');
                    $('#insertDlg3').dialog('close');
                    history.go(0);
                }
            });
        }
    });

}

/**
 * 删除数据
 */
/**
 * 删除数据
 */
function deleteData(id) {
    //$.messager.alert("消息提醒",id, "warning");
    $.messager.confirm("确认", "确认要删除吗？", function (yes) {
        if (yes) {
            var Id = '' + id;
            $.ajax({
                url: "deleteDatamenaing",
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
        var rowData = '';
        var rowData1 = '';
        // 提交添加数据的表单
        var pid = '';
        var isparent = '';
        //var location='';
        var formData = $('#batchForm').serializeJSON();
        var formData1 = $('#batchForm1').serializeJSON();
        var num = '';
        var chanpinId = '';
        var type = '';
        var functionId = '';
        var locationId = '';
        //$.messager.alert("提示", "formData:"+formData.num, 'error');
        //$.messager.alert("提示", "formData1:"+formData1.num, 'error');
        //var formData1 = $('#insertForm1').serializeJSON();
        if ($('#tt').tree('getSelected') == null) {
            pid = '0';
            isparent = '1';
            num = formData.num;
            //locationId=formData.locationId;
        } else {
            //$.messager.alert("提示", "formData1:"+formData1.num, 'error');
            num = formData1.num;

            rowData1 = $('#grid').datagrid('getSelected');

            var ids = rowData1.id;
            //$.messager.alert("提示", "ids:"+ids, 'error');
            //rowData=admin.findDatameaningById(ids);
            $.ajax({
                url: "findDatameaningById",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {"id": ids},
                dataType: "json",
                success: function (data) {
                    rowData = data;
                }
            });
            pid = rowData1.id;
            //var par=admin.findDatameaningById(rowData1.pid);
            var par = null;
            var ropid = rowData1.pid;
            $.ajax({
                url: "findDatameaningById",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {"id": ropid},
                dataType: "json",
                success: function (data) {
                    par = data;
                }
            });
            //$.messager.alert("提示", "rowData.getId():"+rowData.getId(), 'error');
            //$.messager.alert("提示", "pid:"+rowData.gePid(), 'error');
            //$.messager.alert("提示", "rowData.getPid():"+rowData.getPid(), 'error');
            if (rowData.pid == 0) {//二级菜单
                //chanpinId=rowData.getChanpinId();
                //type=formData5.type;
                //locationId=rowData.getLocationId();
                isparent = '1';
            } else if (par.pid == 0) {//三级菜单
                //chanpinId=rowData.getChanpinId();
                type = rowData.type;
                functionId = rowData.functionId;
                //locationId=rowData.getLocationId();
                isparent = '1';
            } else {//四级菜单
                isparent = '0';
                //chanpinId=rowData.getChanpinId();
                type = rowData.type;
                functionId = rowData.functionId;
                //locationId=rowData.getLocationId();
            }
        }
    } else {
        var rowData = '';
        var rowData1 = '';
        // 提交添加数据的表单
        var pid = '';
        var isparent = '';
        //var location='';
        var formData = $('#batchFormTwo').serializeJSON();
        var formData1 = $('#batchFormTwo1').serializeJSON();
        var num = '';
        var chanpinId = '';
        var type = '';
        var functionId = '';
        var locationId = '';
        //$.messager.alert("提示", "formData:"+formData.num, 'error');
        //$.messager.alert("提示", "formData1:"+formData1.num, 'error');
        //var formData1 = $('#insertForm1').serializeJSON();
        if ($('#tt').tree('getSelected') == null) {
            pid = '0';
            isparent = '1';
            num = formData.num;
            //locationId=formData.locationId;
        } else {
            //$.messager.alert("提示", "formData1:"+formData1.num, 'error');
            num = formData1.num;

            rowData1 = $('#grid1').datagrid('getSelected');

            var ids = rowData1.id;
            //$.messager.alert("提示", "ids:"+ids, 'error');
            $.ajax({
                url: "findDatameaningById",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {"id": ids},
                dataType: "json",
                success: function (data) {
                    rowData = data;
                }
            });
            pid = rowData1.id;
            //var par=admin.findDatameaningById(rowData1.pid);
            var par = null;
            var ropid = rowData1.pid;
            $.ajax({
                url: "findDatameaningById",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {"id": ropid},
                dataType: "json",
                success: function (data) {
                    par = data;
                }
            });
            //$.messager.alert("提示", "rowData.getId():"+rowData.getId(), 'error');
            //$.messager.alert("提示", "pid:"+rowData.gePid(), 'error');
            //$.messager.alert("提示", "rowData.getPid():"+rowData.getPid(), 'error');
            if (rowData.pid == 0) {//二级菜单
                //chanpinId=rowData.getChanpinId();
                //type=formData5.type;
                //locationId=rowData.getLocationId();
                isparent = '1';
            } else if (par.pid == 0) {//三级菜单
                //chanpinId=rowData.getChanpinId();
                type = rowData.type;
                functionId = rowData.functionId;
                //locationId=rowData.getLocationId();
                isparent = '1';
            } else {//四级菜单
                isparent = '0';
                //chanpinId=rowData.getChanpinId();
                type = rowData.type;
                functionId = rowData.functionId;
                // locationId=rowData.getLocationId();
            }
        }


    }
    //var msg="批量添加失败";
    $.ajax({
        url: "batchAddDatameaning",
        type: "post",
        async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
        data: {"num": num, "type": type, "functionId": functionId, "pid": pid, "isparent": isparent},
        dataType: "json",
        success: function (rtn) {
            $.messager.alert("提示", rtn.msg, 'info', function () {
                if (rtn.status == 200) {
                    // 成功的话，我们要关闭窗口
                    $('#batchDlg').dialog('close');
                    // 刷新表格数据
                    history.go(0);
                }
            });
        }
    });

}

/**
 * 行内修改数据(grid1里面可以同时修改多行的数据)
 */
function updateData1(a) {
    var rowData1 = "";
    if (a == '1') {
        rowData1 = $('#grid').datagrid('getSelected');
        var id = rowData1.id;
        var pid = rowData1.pid;

        var isShown = '';
        var isBaojingPanduan = '';
        var name = '';
        var type = '';
        var functionId = '';
        var byteNum = '';
        var byteAddress = '';
        var isInput = '';
        var functionCode = '';
        var isHex = '';
        var scale = '';
        var unit = '';
        var isPositive = '';
        var isJiexi = '';
        var gongshi = '';
        var isPanduan = '';
        var isBaojing = '';
        var isPaint = '';
        var pattern = '';
        var zero = '';
        var first = '';
        var bitAddress = '';
        var isRecord = '';
        var bit = '';
        var isparent = rowData1.isparent;
        //var pids=rowData.pid;
        //var par=admin.findDatameaningById(pids);

        //var rowData = $('#tt').tree('getSelected');
        //$.messager.alert("提示", "已进入一次！", 'error');

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
        var a17 = 17 + '' + rowData1.id;
        var a18 = 18 + '' + rowData1.id;
        var a19 = 19 + '' + rowData1.id;
        var a20 = 20 + '' + rowData1.id;
        var a21 = 21 + '' + rowData1.id;
        var a22 = 22 + '' + rowData1.id;
        var a23 = 23 + '' + rowData1.id;
        var a24 = 24 + '' + rowData1.id;
        name = $('input[name=' + a1 + ']').val();
        type = $('input[name=' + a2 + ']').val();
        functionId = $('input[name=' + a3 + ']').val();
        byteNum = $('input[name=' + a5 + ']').val();
        byteAddress = $('input[name=' + a4 + ']').val();
        bitAddress = $('input[name=' + a6 + ']').val();
        pattern = $('input[name=' + a7 + ']').val();
        zero = $('input[name=' + a8 + ']').val();
        first = $('input[name=' + a9 + ']').val();
        isInput = $('input[name=' + a10 + ']').val();
        functionCode = $('input[name=' + a11 + ']').val();
        isHex = $('input[name=' + a12 + ']').val();
        scale = $('input[name=' + a13 + ']').val();
        unit = $('input[name=' + a14 + ']').val();
        isPositive = $('input[name=' + a15 + ']').val();
        isJiexi = $('input[name=' + a16 + ']').val();
        gongshi = $('input[name=' + a17 + ']').val();
        isPanduan = $('input[name=' + a18 + ']').val();
        isBaojing = $('input[name=' + a19 + ']').val();
        isBaojingPanduan = $('input[name=' + a20 + ']').val();
        isShown = $('input[name=' + a21 + ']').val();
        isRecord = $('input[name=' + a22 + ']').val();
        isPaint = $('input[name=' + a23 + ']').val();
        bit = $('input[name=' + a24 + ']').val();
        //var msg="修改失败";
        $.ajax({
            url: "updateDatameaning",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {
                "id": id, "name": name, "type": type, "functionId": functionId, "byteAddress": byteAddress,
                "byteNum": byteNum, "bitAddress": bitAddress, "isInput": isInput, "functionCode": functionCode,
                "isHex": isHex, "scale": scale, "unit": unit, "isPositive": isPositive, "isPaint": isPaint,
                "isJiexi": isJiexi, "gongshi": gongshi, "isPanduan": isPanduan, "isBaojing": isBaojing,
                "isBaojingPanduan": isBaojingPanduan, "isShown": isShown, "isRecord": isRecord,
                "pattern": pattern, "zero": zero, "first": first, "pid": pid, "isparent": isparent, "bit": bit
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
        //msg=admin.updateDatameaning(id,name,type,functionId,byteAddress,byteNum,bitAddress,isInput,functionCode,isHex,scale,unit,isPositive,isJiexi,gongshi,isPanduan,isBaojing,isBaojingPanduan,isShown,isRecord,pattern,zero,first,pid,isparent,bit);

    } else {
        //获得所有选中行的数据
        var count = 0;
        var checkedItems = $('#grid1').datagrid('getChecked');
        var selected1 = [];
        $.each(checkedItems, function (index, item) {
            selected1.push(item);
        });
        //$.messager.alert("提示", "selected1.length"+selected1.length, 'error');
        for (var i = 0; i < selected1.length; i++) {

            var rowData1 = selected1[i];
            var id = rowData1.id;
            var pid = rowData1.pid;
            var isShown = '';
            var isBaojingPanduan = '';
            var name = '';
            var type = '';
            var functionId = '';
            var byteNum = '';
            var byteAddress = '';
            var isInput = '';
            var functionCode = '';
            var isHex = '';
            var scale = '';
            var unit = '';
            var isPositive = '';
            var isJiexi = '';
            var gongshi = '';
            var isPanduan = '';
            var isBaojing = '';
            var pattern = '';
            var zero = '';
            var first = '';
            var bitAddress = '';
            var isRecord = '';
            var bit = '';
            var isparent = rowData1.isparent;
            var isPaint = '';
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
            var a17 = 17 + '' + rowData1.id + 2;
            var a18 = 18 + '' + rowData1.id + 2;
            var a19 = 19 + '' + rowData1.id + 2;
            var a20 = 20 + '' + rowData1.id + 2;
            var a21 = 21 + '' + rowData1.id + 2;
            var a22 = 22 + '' + rowData1.id + 2;
            var a23 = 23 + '' + rowData1.id + 2;
            var a24 = 24 + '' + rowData1.id + 2;
            name = $('input[name=' + a1 + ']').val();
            type = $('input[name=' + a2 + ']').val();
            functionId = $('input[name=' + a3 + ']').val();
            byteNum = $('input[name=' + a5 + ']').val();
            byteAddress = $('input[name=' + a4 + ']').val();
            bitAddress = $('input[name=' + a6 + ']').val();
            pattern = $('input[name=' + a7 + ']').val();
            zero = $('input[name=' + a8 + ']').val();
            first = $('input[name=' + a9 + ']').val();
            isInput = $('input[name=' + a10 + ']').val();
            functionCode = $('input[name=' + a11 + ']').val();
            isHex = $('input[name=' + a12 + ']').val();
            scale = $('input[name=' + a13 + ']').val();
            unit = $('input[name=' + a14 + ']').val();
            isPositive = $('input[name=' + a15 + ']').val();
            isJiexi = $('input[name=' + a16 + ']').val();
            gongshi = $('input[name=' + a17 + ']').val();
            isPanduan = $('input[name=' + a18 + ']').val();
            isBaojing = $('input[name=' + a19 + ']').val();
            isBaojingPanduan = $('input[name=' + a20 + ']').val();
            isShown = $('input[name=' + a21 + ']').val();
            isRecord = $('input[name=' + a22 + ']').val();
            isPaint = $('input[name=' + a23 + ']').val();
            bit = $('input[name=' + a24 + ']').val();
            //var msg="修改失败";
            //msg=admin.updateDatameaning(id,name,type,functionId,byteAddress,byteNum,bitAddress,isInput,functionCode,isHex,scale,unit,isPositive,isJiexi,gongshi,isPanduan,isBaojing,isBaojingPanduan,isShown,isRecord,pattern,zero,first,pid,isparent,bit);
            $.ajax({
                url: "updateDatameaning",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {
                    "id": id, "name": name, "type": type, "functionId": functionId, "byteAddress": byteAddress,
                    "byteNum": byteNum, "bitAddress": bitAddress, "isInput": isInput, "functionCode": functionCode,
                    "isHex": isHex, "scale": scale, "unit": unit, "isPositive": isPositive, "isPaint": isPaint,
                    "isJiexi": isJiexi, "gongshi": gongshi, "isPanduan": isPanduan, "isBaojing": isBaojing,
                    "isBaojingPanduan": isBaojingPanduan, "isShown": isShown, "isRecord": isRecord,
                    "pattern": pattern, "zero": zero, "first": first, "pid": pid, "isparent": isparent, "bit": bit
                },
                dataType: "json",
                success: function (rtn) {
                    //$.messager.alert("提示", rtn.msg, 'info', function() {
                    if (rtn.status == 200) {
                        // 成功的话，我们要关闭窗口
                        //$('#updateDlg1').dialog('close');
                        // 刷新表格数据
                        count += 1;
                    }
                    //});
                }
            });
            //if(msg=="更新成功") {count+=1;}
        }
//$.messager.alert("提示", "count"+count, 'error');
        console.log("count" + count);
        if (count > 0) {
            $.messager.confirm("提示", "批量修改成功", function (yes) {
                history.go(0);
            })
        } else {
            $.messager.confirm("提示", "批量修改失败", function (yes) {
                history.go(0);
            })
        }
    }

}




