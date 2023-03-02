// 初始化左侧
function InitLeftMenu() {
    var selectedPanelname = '';
    var menulist = '';
    menulist += '<ul "layui-nav layui-nav-tree"  lay-filter="test">';
    $.each(_menus.menus, function (i, n) {
        menulist += '<li class="layui-nav-item"><a class="" href="javascript:;" ><i class="' + n.icon + '"style="margin-right:20px"></i><span>' + n.menuname + '</span></a>';
        $.each(n.menus, function (j, o) {
            menulist += '<dl class="layui-nav-child"><dd><a href="' + o.url + '" target="aa"><i class="' + n.icon + '" style="margin-right:20px"></i><span>' + o.menuname + '</span></a></dd></dl></li>';
        })
        menulist += '</ul>';
    });
}
