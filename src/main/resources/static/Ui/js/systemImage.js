var method = "";
var listParam = "";
var saveParam = "";

$(function () {
// 加载表格数据
    $('#grid').datagrid({
        url: 'SystemImageListByPage',
        idField: idField,//指明哪一个字段是标识字段。
        title: title,
        columns: columns,
        frozenColumns: [[{
            field: 'ck',
            checkbox: true
        }, {
            title: '编号',
            field: idField,
            sortable: true,
            width: 100
        }]],
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
            text: '新增',
            iconCls: 'icon-add',
            handler: function () {
                var list = null;
                $.ajax({
                    url: "allSystemImageList",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {},
                    dataType: "json",
                    success: function (data) {
                        list = data;
                    }
                });
                if (list != null) {
                    $.messager.alert("消息提醒", "系统界面仅能显示一张图片！请删除其他图片后再进行导入！", "warning");
                    return;
                } else {
                    // 打开前清空表单
                    $('#importForm').form('clear');
                    // 设置保存按钮提交的方法为add
                    method = "add";
                    // 关闭编辑窗口
                    $('#importDlg').dialog('open');
                }
            }
        }, '-', {
            text: '修改',
            iconCls: 'icon-edit',
            handler: function () {
                $('#editForm').form('clear');
                // 设置保存按钮提交的方法为add
                method = "update";
                // 关闭编辑窗口
                $('#editDlg').dialog('open');
            }
        }, '-', {
            text: '删除',
            iconCls: 'icon-cut',
            handler: function () {
                // 获取被选中行的数据
                var selected = $('#grid').datagrid('getSelected');
                if (selected == null) {
                    $.messager.alert("消息提醒", "请选择删除行", "warning");
                    return;
                }
                del(selected);
            }
        }],
        onDblClickRow: function () {
            edit();
        },
    });
    var h = 600;
    var w = 800;
    if (typeof (height) != "undefined") {
        h = height;
    }
    if (typeof (width) != "undefined") {
        w = width;
    }

    $('#editDlg').dialog({
        title: '编辑',
        width: 350,
        height: 400,
        closed: true,
        modal: true

    });
    $('#importDlg').dialog({
        title: '添加',
        width: 350,
        height: 400,
        closed: true,
        modal: true

    });


    $('#btnSave').bind('click', function () {
        var isValid = $('#editForm').form('validate');
        if (isValid == false) {
            return;
        } else {
            var dada = new FormData($('#editForm')[0]);
            $.ajax({
                url: "SystemImageEdit",
                type: "post",
                async: false,
                cache: false,
                secureuri: false, //一般设置为false
                processData: false, //因为data值是FormData对象，不需要对数据做处理。
                contentType: false,
                data: dada,
                success: function (rtn) {
                    $.messager.alert("提示", rtn.msg, 'info', function () {
                        if (rtn.status == 200) {
                            // 成功的话，我们要关闭窗口
                            $('#editDlg').dialog('close');
                            // 刷新表格数据
                            $('#importDlg').dialog('close');
                            $('#importForm').form('clear');
                            $('#editForm').form('clear');
                            $('#grid').datagrid('reload');
                            location.reload();
                        }
                    });
                }
            });
        }
    });


    $('#btnSave2').bind('click', function () {

        var isValid = $('#importForm').form('validate');
        if (isValid == false) {
            return;
        } else {
            var dada = new FormData($('#importForm')[0]);
            $.messager.alert("消息提醒", dada, "warning");
            $.ajax({
                url: "SystemImageImport",
                type: "post",
                async: false,
                cache: false,
                secureuri: false, //一般设置为false
                processData: false, //因为data值是FormData对象，不需要对数据做处理。
                contentType: false,
                data: dada,
                success: function (rtn) {
                    $.messager.alert("提示", rtn.msg, 'info', function () {
                        if (rtn.status == 200) {
                            // 成功的话，我们要关闭窗口
                            $('#editDlg').dialog('close');
                            // 刷新表格数据
                            $('#importDlg').dialog('close');
                            $('#importForm').form('clear');
                            $('#editForm').form('clear');
                            $('#grid').datagrid('reload');
                            location.reload();
                        }
                    });
                }
            });

        }


    });


});


function del(selected) {
    $.messager.confirm("确认", "确认要删除吗？", function (yes) {
        if (yes) {
            var sd = 'deleteSystemImage';
            var id = selected.id;
            $.ajax({
                url: sd,
                data: {"id": id},
                dataType: 'json',
                type: 'post',
                success: function (rtn) {

                    $.messager.alert("提示", rtn.msg, 'info', function () {
                        location.reload();
                    });
                }
            });
        }
    });
}

function edit() {

    $('#editForm').form('clear');

    method = "update";
    var selected = $('#grid').datagrid('getSelected');

    if (selected == null) {
        $.messager.alert("消息提醒", "请选择行！", "warning");
        return;
    }

    $('#editDlg').dialog('open');
    document.getElementById("update_companyname").value = selected.companyname;
    document.getElementById("update_jiekou").value = selected.jiekou;
    document.getElementById("update_xieyi").value = selected.xieyi;
    document.getElementById("update_name").value = selected.name;
    document.getElementById("update_address").value = selected.address;
    document.getElementById("update_internet").value = selected.internet;
    document.getElementById("update_phone").value = selected.phone;
    document.getElementById("update_url").value = selected.url;
}
