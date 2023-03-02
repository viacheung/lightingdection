/**
 * esayui通用增删改查以及导入导出
 */
//提交的方法名称
var method = "";
var listParam = "";
var saveParam = "";
$(function () {
    // 加载表格数据
    $('#grid').datagrid({
        url: 'ImageListByPage',
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
            text: '编辑',
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
        }, '-', {
            text: '导入',
            iconCls: 'icon-save',
            handler: function () {
                //var list=admin.ImageListByPage(null,null,null);
                var list = null;
                $.ajax({
                    url: "allImageList",
                    type: "post",
                    async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                    data: {},
                    dataType: "json",
                    success: function (data) {
                        list = data;
                    }
                });
                if (list.length > 0) {
                    $.messager.alert("消息提醒", "用户界面仅能显示一张图片！请删除其他图片后再进行导入！", "warning");
                    return;
                }
                document.getElementById("import_size").value = 5;
                $('#importDlg').dialog('open');
            }
        }, '-', {
            text: '改变点位样式',
            iconCls: 'icon-edit',
            handler: function () {
                document.getElementById("ZCDotImg").src = "showPhotoByPath?path=dotImage/正常.png";
                document.getElementById("YCDotImg").src = "showPhotoByPath?path=dotImage/异常.png";
                document.getElementById("YJDotImg").src = "showPhotoByPath?path=dotImage/预警.png";
                $('#ChangDotImgDlg').dialog('open');
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

    // 初始化编辑窗口
    $('#editDlg').dialog({
        title: '编辑',// 窗口标题
        width: 1000,// 窗口宽度
        height: 800,// 窗口高度
        closed: true,// 窗口是是否为关闭状态, true：表示关闭
        modal: true
        // 模式窗口
    });

    // 初始化编辑窗口
    $('#changeDlg').dialog({
        title: '改变大小',// 窗口标题
        width: 300,// 窗口宽度
        height: 200,// 窗口高度
        closed: true,// 窗口是是否为关闭状态, true：表示关闭
        modal: true
        // 模式窗口
    });

    // 初始化编辑窗口
    $('#changeDlg2').dialog({
        title: '改变大小',// 窗口标题
        width: 300,// 窗口宽度
        height: 200,// 窗口高度
        closed: true,// 窗口是是否为关闭状态, true：表示关闭
        modal: true
        // 模式窗口
    });
    // 初始化窗口
    $('#ChangDotImgDlg').dialog({
        title: '改变点位样式',// 窗口标题
        width: 400,// 窗口宽度
        height: 700,// 窗口高度
        closed: true,// 窗口是是否为关闭状态, true：表示关闭
        modal: true,
        buttons: [{
            text: '保存',
            handler: function () {
                //var tag1=admin.setZhengchang();
                //var tag2=admin.setYichang();
                //var tag3=admin.setYujing();
                var dada = new FormData($('#CDI')[0]);

                $.ajax({
                    url: 'dianweiPhotoImport',
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
                                $('#ChangDotImgDlg').dialog('close');
                            }
                        });
                    }
                });
            }
        }, {
            text: '重载图片',
            handler: function () {
                $('#ChangDotImgDlg').dialog('close');
                document.getElementById("ZCDotImg").src = "showPhotoByPath?path=dotImage/正常.png";
                document.getElementById("YCDotImg").src = "showPhotoByPath?path=dotImage/异常.png";
                document.getElementById("YJDotImg").src = "showPhotoByPath?path=dotImage/预警.png";
                $('#ChangDotImgDlg').dialog('open');
            }
        }]
    });

    // 点击保存按钮
    $('#btnSave').bind('click', function () {
        var isValid = $('#editForm').form('validate');
        if (isValid == false) {
            return;
        }

        //获取图片的高度和宽度
        var myImg = document.querySelector("#dv");
        var currWidth = myImg.clientWidth;
        var currHeight = myImg.clientHeight;

        var size = document.getElementById("input_size").value;
        //$.messager.alert("消息提醒",size, "warning");
        var dianweis = document.getElementsByClassName("marker");
        var dot = "";
        for (var p = 0; p < dianweis.length; p++) {
            var x = dianweis[p].style.marginLeft;
            var y = dianweis[p].style.marginTop;
            x = x.substr(0, x.indexOf("p"));
            y = y.substr(0, y.indexOf("p"));
            x = parseInt(x);
            y = parseInt(y);
            x = x + (size / 2);
            y = y + (size / 2);
            ProportionWidthInImg = x / currWidth;
            ProportionHeightInImg = y / currHeight;

            dot += "(" + ProportionWidthInImg + "," + ProportionHeightInImg + ")-";
        }

        var id = document.getElementById("input_id").value;
        var name = document.getElementById("input_name").value;
        var dotsize = 0.00003;
        if (size < 0.001) {
            dotsize = size;
        } else {
            dotsize = size / (currWidth * currHeight);
        }
        //var msg="";
        //msg=admin.updateImage(id,name,dot,dotsize);

        //$('#editDlg').dialog('close');


        $.ajax({
            url: "updateImage",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"id": id, "name": name, "dots": dot, "dotsize": dotsize},
            dataType: "json",
            success: function (rtn) {
                $.messager.alert("提示", rtn.msg, 'info', function () {
                    if (rtn.status == 200) {
                        // 成功的话，我们要关闭窗口
                        $('#editDlg').dialog('close');
                        // 刷新表格数据
                        location.reload();
                    }
                });
            }
        });

    });

    // 点击重载按钮
    $('#btnChongZai').bind('click', function () {
        $('#editDlg').dialog('close');
        //$.messager.alert("消息提醒",dot, "warning");
        //dot="";
        // 设置保存按钮提交的方法为update
        method = "update";
        var selected = $('#grid').datagrid('getSelected');
        document.getElementById("dv").innerHTML = "";
        var path = selected.url;
        document.getElementById("dv").style.backgroundImage = "url('showPhotoByPath?path=" + path + "')";
        // 弹出窗口
        $('#editDlg').dialog('open');

    });

    // 点击改变大小按钮
    $('#btnChange').bind('click', function () {
        $('#changeDlg').dialog('open');
        document.getElementById("change_size").value = document.getElementById("input_size").value;

    });

    $('#btnAdd').bind('click', function () {
        draw1();
        $('#editDlg').dialog('close');
        $('#editDlg').dialog('open');

    });

    $('#Change').bind('click', function () {
        $('#changeDlg').dialog('close');
        $('#editDlg').dialog('close');
        var size = document.getElementById("change_size").value;
        size = 500 * 310 * size;
        var selected = $('#grid').datagrid('getSelected');
        document.getElementById("dv").innerHTML = "";
        var path = selected.url;
        document.getElementById("dv").style.backgroundImage = "url('showPhotoByPath?path=" + path + "')";
        var dot = selected.dots;
        var list = null;
        $.ajax({
            url: "toDouble",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"dots": dot},
            dataType: "json",
            success: function (data) {
                list = data;
            }
        });
        //var myImg = document.querySelector("#input_Img");
        //myImg.onload=function(){
        var currWidth = 500;
        var currHeight = 310;
        //$.messager.alert("消息提醒",currWidth, "warning");
        for (var i = 0; i < list.length; i++) {
            var ProportionWidthInImg = list[i].key;
            var ProportionHeightInImg = list[i].value;
            var x = currWidth * ProportionWidthInImg;
            var y = currHeight * ProportionHeightInImg;

            createMarker(x, y, 'dv', size);
        }
        //}

        $('#editDlg').dialog('open');
        document.getElementById("input_size").value = size;

    });

    $('#Change2').bind('click', function () {
        $('#changeDlg2').dialog('close');
        $('#importDlg').dialog('close');

        var size = document.getElementById("change_size2").value;

        var file = admin.getFile();
        if (file == null || dot == "") {
            $('#importDlg').dialog('open');
            document.getElementById("import_size").value = size;
            return;
        }

        document.getElementById("dv2").innerHTML = "";
        document.getElementById("dv2").innerHTML = "<img id='import_Img' src='" + "data:image/jpeg|png|gif;base64," + admin.getImageByPath(file.toString())
            + "'  height='60%' width='100%'>";
        var list = admin.toDouble(dot);
        var myImg = document.querySelector("#import_Img");
        myImg.onload = function () {
            var currWidth = myImg.clientWidth;
            var currHeight = myImg.clientHeight;

            for (var i = 0; i < list.size(); i++) {
                var ProportionWidthInImg = list.get(i).getKey();
                var ProportionHeightInImg = list.get(i).getValue();
                var x = currWidth * ProportionWidthInImg;
                var y = currHeight * ProportionHeightInImg;

                createMarker(x, y, 'dv2', size);
            }
        }


        $('#importDlg').dialog('open');
        document.getElementById("import_size").value = size;

    });


    // 判断是否有导入的功能
    var importForm = document.getElementById('importForm');
    if (importForm) {
        $('#importDlg').dialog(
            {
                title: '导入数据',
                width: 1000,
                height: 800,
                modal: true,
                closed: true,
                buttons: [{
                    text: '导入',
                    handler: function () {
                        var msg = "";
                        // var myImg = document.querySelector("#import_Img");
                        var currWidth = 500;
                        var currHeight = 310;


                        var size = document.getElementById("import_size").value;

                        var dianweis = document.getElementsByClassName("marker");

                        for (var p = 0; p < dianweis.length; p++) {
                            var x = dianweis[p].style.marginLeft;
                            var y = dianweis[p].style.marginTop;
                            x = x.substr(0, x.indexOf("p"));
                            y = y.substr(0, y.indexOf("p"));
                            x = parseInt(x);
                            y = parseInt(y);
                            x = x + (size / 2);
                            y = y + (size / 2);
                            ProportionWidthInImg = x / currWidth;
                            ProportionHeightInImg = y / currHeight;

                            dot += "(" + ProportionWidthInImg + "," + ProportionHeightInImg + ")-";
                        }
                        var dotsize = document.getElementById("import_size").value / (currWidth * currHeight);
                        console.log("size:" + dotsize);
                        var dada = new FormData($('#importForm')[0]);
                        dada.append("dots", dot);
                        dada.append("size", dotsize);
                        $.ajax({
                            url: "Import",
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
                                        $('#grid').datagrid('reload');
                                        location.reload();
                                    }
                                });
                            }
                        });

                    }
                }, {
                    text: '重载',
                    handler: function () {
                        var dada = new FormData($('#importForm')[0]);
                        var file = $('#import_file').get(0).files[0];
                        //$.messager.alert("消息提醒","file:"+$('#import_file').get(0).files[0], "warning");
                        if (file) {
                            document.getElementById("dv").innerHTML = "";
                            var msg = null;
                            $.ajax({
                                url: "uploadTempPhoto",
                                type: "post",
                                async: false,
                                cache: false,
                                secureuri: false, //一般设置为false
                                processData: false, //因为data值是FormData对象，不需要对数据做处理。
                                contentType: false,
                                data: dada,
                                success: function (rtn) {
                                    msg = rtn;
                                }
                            });
                            if (msg.status == 200) {
                                //$('#importDlg').dialog('close');
                                //$('#importForm').form('clear');
                                document.getElementById("import_size").value = 5;
                                document.getElementById("dv2").style.backgroundImage = "url('showTempPhoto')";
                                //$('#importDlg').dialog('open');
                            }

                        } else {
                            //$.messager.alert("消息提醒", "进入", "warning");
                            $('#importDlg').dialog('close');
                            $('#importForm').form('clear');
                            document.getElementById("dv2").innerHTML != ""
                            document.getElementById("dv").innerHTML = "";
                            document.getElementById("dv2").style.backgroundImage = "";
                            $('#importDlg').dialog('open');
                        }
                        //	document.getElementById("dv2").style.backgroundImage="url('getTempPhoto?file="+file+"')";

                        //	$.messager.alert("消息提醒",data.get("import_file"), "warning");
                        /*	if(file==null){
                                $.messager.alert("消息提醒", "未选择图片！", "warning");
                                return ;
                            }
                            $('#importDlg').dialog('close');
                            document.getElementById("dv2").innerHTML="";
                            document.getElementById("dv2").innerHTML="<img id='import_Img' src='"+"data:image/jpeg|png|gif;base64,"+admin.getImageByPath(file.toString())
                            +"'  height='60%' width='100%'>";
                            // 弹出窗口
                            $('#importDlg').dialog('open');*/
                    }
                }, {
                    text: '改变点位大小',
                    handler: function () {
                        $('#changeDlg2').dialog('open');
                        document.getElementById("change_size2").value = document.getElementById("import_size").value;

                    }
                }, {
                    text: '增加点位',
                    handler: function () {
                        draw2();
                        $('#importDlg').dialog('close');
                        $('#importDlg').dialog('open');

                    }
                }]
            });
    }

});

/**
 * 删除
 */
function del(selected) {
    if (selected != null && selected.id != "") {
        $.ajax({
            url: "deleteImage",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"id": selected.id},
            dataType: "json",
            success: function (rtn) {
                $.messager.alert("提示", rtn.msg, 'info', function () {
                    if (rtn.status == 200) {
                        // 成功的话，我们要关闭窗口
                        // 刷新表格数据
                        location.reload();
                    }
                });
            }
        });
    } else {
        $.messager.alert("消息提醒", "请选择信息！", "warning");
    }
}

function edit() {
    // 清空表单内容
    $('#editForm').form('clear');
    // 设置保存按钮提交的方法为update
    method = "update";

    var selected = $('#grid').datagrid('getSelected');
    document.getElementById("dv").innerHTML = "";
    var path = selected.url;
    document.getElementById("dv").style.backgroundImage = "url('showPhotoByPath?path=" + path + "')";
    //$.messager.alert("消息提醒",document.getElementById("dv").style.background, "warning");
    var dot = selected.dots;
    //var list=admin.toDouble(selected.dots);
    var list = null;
    $.ajax({
        url: "toDouble",
        type: "post",
        async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
        data: {"dots": dot},
        dataType: "json",
        success: function (data) {
            list = data;
        }
    });
    var size = selected.dotsize;
    //var myImg = document.querySelector("#dv");
    //var currWidth = myImg.clientWidth;
    //var currHeight = myImg.clientHeight;
    size = 500 * 310 * size;
//$.messager.alert("消息提醒",myImg.clientWidth, "warning");

    document.getElementById("input_size").value = size;
    //$.messager.alert("消息提醒",document.getElementById("input_size").value, "warning");
    for (var i = 0; i < list.length; i++) {
        var ProportionWidthInImg = list[i].key;
        var ProportionHeightInImg = list[i].value;
        var x = 500 * ProportionWidthInImg;
        var y = 310 * ProportionHeightInImg;
        createMarker(x, y, 'dv', size);
    }

    // 弹出窗口
    $('#editDlg').dialog('open');
    // 获取被选中行的数据
    // 加载数据
    $('#editForm').form('load', selected);
}
