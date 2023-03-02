/**
 * esayui通用增删改查以及导入导出
 */
//提交的方法名称
var method = "";
var listParam = "";
var saveParam = "";
var tiaoshi = window.parent.tiaoshi;
$(function () {
    init();
    // 加载表格数据
    $('#grid').datagrid({
        idField: idField,//指明哪一个字段是标识字段。
        frozenColumns: [[{
            field: 'ck',
            checkbox: true
        }]],
        title: title,
        fitColumns: true,
        iconCls: 'icon-tip',
        singleSelect: true,// 如果为true，则只允许选择一行。
        pagination: true,// 如果为true，则在DataGrid控件底部显示分页工具栏。
        striped: true,// 是否显示斑马线效果。
        collapsible: true,	//定义是否显示可折叠按钮。
        rownumbers: true,//如果为true，则显示一个行号列。
        nowrap: true,//如果为true，则在同一行中显示数据。设置为true可以提高加载性能。
        sortName: idField,//定义哪些列可以进行排序。
        sortOrder: 'asc',//定义列的排序顺序，只能是'asc'或'desc'。
        remoteSort: false,//定义从服务器对数据进行排序。
        loading: true,//显示载入状态。
        loadMsg: '数据加载中...',// 在从远程站点加载数据的时候显示提示消息。
        pageNumber: 1,// 在设置分页属性的时候初始化页码。
        pageSize: 50,// 在设置分页属性的时候初始化页面大小。
        pageList: [10, 20, 30, 40, 50],//在设置分页属性的时候 初始化页面大小选择列表。
        toolbar: [{
            text: '修改',
            iconCls: 'icon-edit',
            handler: function () {
                edit();
            }
        }, '-', {
            text: '删除',
            iconCls: 'icon-cut',
            handler: function () {
                // 获取被选中行的数据
                var selected = $('#grid').datagrid('getSelected');
                del(selected);
            }
        }, {
            text: '导入',
            iconCls: 'icon-save',
            handler: function () {
                $('#importDlg').dialog('open');
            }
        }],
        onDblClickRow: function () {
            edit();
        },
    });

    var h = 300;
    var w = 400;
    if (typeof (height) != "undefined") {
        h = height;
    }
    if (typeof (width) != "undefined") {
        w = width;
    }

    // 初始化编辑窗口
    $('#editDlg').dialog({
        title: '编辑',// 窗口标题
        width: w,// 窗口宽度
        height: h,// 窗口高度
        closed: true,// 窗口是是否为关闭状态, true：表示关闭
        modal: true
        // 模式窗口
    });

    // 点击保存按钮
    $('#btnSave').bind('click', function () {
        var isValid = $('#editForm').form('validate');
        if (isValid == false) {
            return;
        }

        var id = document.getElementById("input_id").value;
        var type = document.getElementById("input_type").value;
        var msg = "";

        msg = tiaoshi.updateApp(id, type);
        $('#editDlg').dialog('close');
        $.messager.confirm("提示", msg, function (yes) {
            // 刷新表格数据
            location.reload();
        })

    });


    // 判断是否有导入的功能
    var importForm = document.getElementById('importForm');
    if (importForm) {
        $('#importDlg').dialog(
            {
                title: '导入数据',
                width: 330,
                height: 250,
                modal: true,
                closed: true,
                buttons: [{
                    text: '导入',
                    handler: function () {
                        var type = document.getElementById("import_type").value;
                        var msg = "";
                        msg = tiaoshi.AppImport(type);
                        $('#importDlg').dialog('close');
                        $('#importForm').form('clear');
                        $('#grid').datagrid('reload');
                        $.messager.confirm("提示", msg, function (yes) {
                            // 刷新表格数据
                            location.reload();
                        });
                    }
                }]
            });
    }

});

function init() {
    var list = tiaoshi.appListByPage(null, null, null);
    var htmllist = "";
    for (var i = 0; i < list.size(); i++) {
        htmllist += "<tr>" +
            "<td></td>" +
            "<td>" + list.get(i).getId() + "</td>" +
            "<td>" + list.get(i).getType() + "</td>" +
            "<td>" + list.get(i).getUrl() + "</td>" +
            "</tr>";
    }
    ;
    document.getElementById("appList").innerHTML = htmllist;
}

/**
 * 删除
 */
function del(selected) {
    $.messager.confirm("确认", "确认要删除吗？", function (yes) {
        if (yes) {
            var id = selected.id;
            var msg = "";
            msg = tiaoshi.deleteApp(id);
            $.messager.confirm("提示", msg, function (yes) {
                // 刷新表格数据
                location.reload();
            })

        }
    });
}

function edit() {
    // 清空表单内容
    $('#editForm').form('clear');
    // 设置保存按钮提交的方法为update
    method = "update";
    // 弹出窗口
    $('#editDlg').dialog('open');
    // 获取被选中行的数据
    var selected = $('#grid').datagrid('getSelected');
    // 加载数据
    $('#editForm').form('load', selected);
}
