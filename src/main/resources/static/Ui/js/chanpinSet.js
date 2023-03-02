var admin = window.parent.admin;
var normal = window.parent.parent.normal;

/**
 * 处理左侧菜单
 */
function disposeTree() {
    // 加载左侧菜单
    $('#tt').tree({
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

    var chanpin = null;
    var Id = '' + id;
    $.ajax({
        url: "getChanpinById",
        type: "post",
        async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
        data: {"id": Id},
        dataType: "json",
        success: function (data) {
            chanpin = data;
        }
    });

    if (chanpin != null && chanpin.isparent == 0) {
        // var leibie=normal.getChanpinById(chanpin.getPid());
        var leibiId = chanpin.pid;
        var leibie = null;
        $.ajax({
            url: "getChanpinById",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"id": leibiId},
            dataType: "json",
            success: function (data) {
                leibie = data;
            }
        });
        // var photo=normal.findCPbyChanpinId(leibie.getId());
        var photo = null;
        $.ajax({
            url: "findChanpinPhotobyChanpinId",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"id": leibiId},
            dataType: "json",
            success: function (data) {
                photo = data;
            }
        });
        var len1 = null;
        $.ajax({
            url: "getAllSonsLength",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"type": "3", "leibeiId": leibiId},
            dataType: "json",
            success: function (data) {
                len1 = data;
            }
        });
        var len2 = null;
        $.ajax({
            url: "getAllSonsLength",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"type": "2", "leibeiId": leibiId},
            dataType: "json",
            success: function (data) {
                len2 = data;
            }
        });
        var len = len1 + len2 + 1;


        var allholdingRejisters = null;
        $.ajax({
            url: "getAllHoldingRejister",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"chanpinLeibieId": leibiId},
            dataType: "json",
            success: function (data) {
                allholdingRejisters = data;
            }
        });

        var allcoilRejisters = null;
        $.ajax({
            url: "getAllCoilRejister",
            type: "post",
            async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
            data: {"chanpinLeibieId": leibiId},
            dataType: "json",
            success: function (data) {
                allcoilRejisters = data;
            }
        });


        htmllist += "<table bordercolor='black' border='1px' width='98%'>";
        htmllist += "<tr>" +
            "<th width='20%'style='border-right:0px;'><img  alt='未给该产品分配图片' id='" + '4' + "image' src='showPhotoByPath?path=" + photo.url + "' />&nbsp;&nbsp;&nbsp;</th>" +
            "<th width='20%' style='border-left:0px;'><ul><li>&nbsp;&nbsp;&nbsp;区域:" + leibie.location + "</li>" +
            "<li>&nbsp;&nbsp;&nbsp;产品型号:" + leibie.model + "</li>" +

            "<li>&nbsp;&nbsp;&nbsp;产品类别名:" + leibie.name + "</li>" +
            "<li>&nbsp;&nbsp;&nbsp;产品名:" + chanpin.name + "</li>" +
            "<li>&nbsp;&nbsp;&nbsp;地址码:" + chanpin.slaveId + "</li></ul></th>" +
            "<th  width='30%'>" + "名称" + "</th>" +
            "<th  width='30%'>" + "操作" + "</th></tr>";

        for (var i = 0; i < allcoilRejisters.length; i++)//先遍历所有的线圈
        {
            //获取到所有的inputDisplay
            var inputDisplays = null;
            var allcoilRejistersid = allcoilRejisters[i].id;
            $.ajax({
                url: "getDisplayData4",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {"chanpinId": allcoilRejistersid},
                dataType: "json",
                success: function (data) {
                    inputDisplays = data;
                }
            });


            //遍历得到的inputDisplay

            for (var j = 0; j < inputDisplays.length; j++) {
                var sons = inputDisplays[j].sons;
                var length = sons.length;
                htmllist += "<tr><th colspan=2 rowspan='" + length + "'>" + inputDisplays[j].name + "</th>";
                htmllist += "<th  rowspan='1'>" + sons[0].name + "</th>";
                htmllist += "<th  rowspan='1'>" + "<button class='btn' style='background:#0091ea;color:white; border:none;margin-top:10px; margin-bottom:10px;' onclick=Coilsend(" + sons[0].id + "," + id + ") type='button' >发送指令</button>" + "</th></tr>";
                for (var l = 1; l < length; l++) {
                    htmllist += "<tr><th >" + sons[l].name + "</th>";
                    htmllist += "<th >" + "<button class='btn' style='background:#0091ea;color:white;border:none; margin-top:10px; margin-bottom:10px;' onclick=Coilsend(" + sons[l].id + "," + id + ") type='button' >发送指令</button>" + "</th></tr>";
                    // $.messager.alert("提示", "inputDisplays.get(j).getName():  "+inputDisplays.get(j).getName(), 'error');
                }


            }


        }


        //遍历保持寄存器

        for (var i = 0; i < allholdingRejisters.length; i++) {
            //获取到所有的inputDisplay
            var inputDisplays = null;
            var allholdingRejistersid = allholdingRejisters[i].id;
            $.ajax({
                url: "getDisplayData3",
                type: "post",
                async: false,//此处需要注意的是要想获取ajax返回的值这个async属性必须设置成同步的，否则获取不到返回值
                data: {"chanpinId": allholdingRejistersid},
                dataType: "json",
                success: function (data) {
                    inputDisplays = data;
                }
            });
            //var inputDisplays=null;
//normal.getDisplayData3(allholdingRejisters.get(i).getId());
            //遍历得到的inputDisplay
            //  $.messager.alert("提示", "inputDisplays.get(0).getName():  "+inputDisplays.size(), 'error');

            for (var j = 0; j < inputDisplays.length; j++) {
                var sons = inputDisplays[j].sons;
                var length = sons.length;
                htmllist += "<tr><th colspan='2' rowspan='" + length + "'>" + inputDisplays[j].name + "</th>";
                htmllist += "<th  rowspan='1'>" + sons[0].name + "</th>";
                if (sons[0].isInput == 0) {
                    htmllist += "<th rowspan='1'>" + "<button class='btn' style='background:#0091ea;color:white;border:none; margin-top:10px; margin-bottom:10px;' onclick='HRsend(" + sons[0].id + "," + '0' + "," + id + ");' type='button' >发送指令</button>" + "</th></tr>";
                } else {
                    htmllist +=
                        "<th>" + "<input id='HRinput_" + sons[0].id + "' type='text' />" +
                        "<button class='btn' style='background:#0091ea;color:white; margin-top:10px;border:none; margin-bottom:10px;' onclick='HRsend(" + sons[0].id + "," + '1' + "," + id + ");' type='button' >发送指令</button>" + "</th></tr>";
                }
                for (var l = 1; l < length; l++) {
                    htmllist += "<tr><th>" + sons[l].name + "</th>";
                    if (sons[l].isInput == 0) {
                        htmllist += "<th rowspan='1'>" + "<button class='btn' style='background:#0091ea;border:none; color:white;margin-top:10px; margin-bottom:10px;' onclick='HRsend(" + sons[l].id + "," + '0' + "," + id + ");' type='button' >发送指令</button>" + "</th></tr>";
                    } else {
                        htmllist +=
                            "<th >" + "<input id='HRinput_" + sons[l].id + "' type='text' />" +
                            "<button class='btn' style=' background:#0091ea;color:white;border:none;margin-top:10px; margin-bottom:10px;' onclick='HRsend(" + sons[l].id + "," + '1' + "," + id + ");' type='button' >发送指令</button>" + "</th></tr></div>";
                    }
                }


            }

        }
        document.getElementById("dv").innerHTML = htmllist;
    } else {
        document.getElementById("dv").innerHTML = htmllist;
        $.messager.alert("提示", "请选中三级菜单！", 'error');
    }


}

function HRsend(id, Isinput, chanpinId) {
    // var normal=window.parent.normal;
//$.messager.alert("消息提醒","id: "+id+"   chanpinId:  "+chanpinId+" input:"+document.getElementById("HRinput_"+id).value, "warning");
    var input = "";
    if (Isinput == 1) {
        input = document.getElementById("HRinput_" + id).value;

        if (input == null || input == "") {
            $.messager.alert("消息提醒", "请进行输入后再发送指令！", "warning");
            return;
        }
    }
    //var s=normal.HRsendZhiling(id,input,chanpinId);
    $.messager.progress({
        title: "请等待",
        text: "发送中......"
    });
    $.ajax({
        url: "HRsendZhiling",
        data: {"id": id, "input": input, "chanpinId": chanpinId},
        dataType: 'json',
        type: 'post',
        success: function (rtn) {
            $.messager.alert("提示", rtn.msg, 'info', function () {
                $(".messager-body").window('close');
            });
        }
    });
}

function Coilsend(id, chanpinId) {
    $.messager.progress({
        title: "请等待",
        text: "发送中......"
    });
    $.ajax({
        url: "CoilsendZhiling",
        data: {"id": id, "chanpinId": chanpinId},
        dataType: 'json',
        type: 'post',
        success: function (rtn) {
            $.messager.alert("提示", rtn.msg, 'info', function () {
                $(".messager-body").window('close');
            });
        }
    });

}