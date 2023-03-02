var method = "";
var listParam = "";
var saveParam = "";
$(function () {
    // 加载表格数据
    $('#grid').datagrid({
        url: 'ChanpinImageListByPage',
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
                // 打开前清空表单
                $('#importForm').form('clear');
                // 设置保存按钮提交的方法为add
                //method = "addUser";
                // 关闭编辑窗口
                $('#importDlg').dialog('open');
            }
        }, '-', {
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
        }],
        onDblClickRow: function () {
            edit();
        },
    });

    var h = 800;
    var w = 800;
    if (typeof (height) != "undefined") {
        h = height;
    }
    if (typeof (width) != "undefined") {
        w = width;
    }

    $('#editDlg').dialog({
        title: '编辑',
        width: 400,
        height: 400,
        closed: true,
        modal: true
    });


    $('#importDlg').dialog({
        title: '导入',
        width: 400,
        height: 400,
        closed: true,
        modal: true
    });


    $('#btnSave').bind('click', function () {
        var isValid = $('#editForm').form('validate');
        if (isValid == false) {
            return;
        }

        var id = document.getElementById("input_id").value;
        var chanpinId = document.getElementById("input_chanpinId").value;
        var name = document.getElementById("input_name").value;

        //var msg="";
        $.ajax({
            url: "updateChanpinPhoto",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"id": id, "chanpinId": chanpinId, "name": name},
            dataType: "json",
            success: function (rtn) {
                $.messager.alert("提示", rtn.msg, 'info', function () {
                    if (rtn.status == 200) {
                        $('#editDlg').dialog('close');
                        // 刷新表格数据
                        location.reload();
                    }
                });
            }
        });
    });

    //var importForm = document.getElementById('importForm');
    var importForm = document.getElementById('importForm');
    var formData = $('#importForm').serializeJSON();
    if (importForm) {
        $('#importDlg').dialog(
            {
                title: '导入',
                width: 400,
                height: 300,
                modal: true,
                closed: true,
                buttons: [{
                    text: '确认',
                    handler: function () {
                        //var msg="";
                        var chanpinId = document.getElementById("import_chanpinId").value;
                        var dada = new FormData($('#importForm')[0]);
                        $.ajax({
                            url: 'chanpinPhotoImport?chanpinId=' + chanpinId,
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
                                        $('#importDlg').dialog('close');
                                        $('#importForm').form('clear');
                                        $('#grid').datagrid('reload');
                                        location.reload();
                                    }
                                });
                            }
                        });
                    }
                }]
            });
    }

});


function del(selected) {
    $.messager.confirm("确认", "确认要删除吗？", function (yes) {
        if (yes) {
            var id = selected.id;
            //	var msg="";
//			msg=admin.deleteChanpinPhoto(id);
            //Id = ''+id
            $.ajax({
                url: "deleteChanpinPhoto",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {"id": id},
                dataType: "json",
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
    $('#editDlg').dialog('open');
    var selected = $('#grid').datagrid('getSelected');
    $('#editForm').form('load', selected);
}
