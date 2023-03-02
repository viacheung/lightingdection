/**
 * esayui閫氱敤鎼滅储
 */
$(function () {
    // 鑷姩琛ュ叏

    //鍥炶溅浜嬩欢缁戝畾 鎼滅储妗嗘槸esayui鍔ㄦ�佺敓鎴愮殑<input type="text" class="combo-text validatebox-text" autocomplete="off" style="width: 167px; height: 20px; line-height: 20px;">
    $('.combo-text').bind('keyup', function (event) {
        if (event.keyCode == "13") {
            //鍥炶溅鎵ц鏌ヨ
            //$('#btnSearch').click();
            reloadgrid();
        }
    });
    // 鐐瑰嚮鏌ヨ鎸夐挳
    $('#btnSearch').bind('click', function () {
        reloadgrid();
    });

    // 鐐瑰嚮閲嶇疆鎸夐挳
    $('#btnReset').bind('click', function () {
        $('#searchForm').form('clear');
    });

    function reloadgrid() {
        // 鎶婅〃鍗曟暟鎹浆鎹㈡垚json瀵硅薄
        var formData = $('#searchForm').serializeJSON();
        $('#grid').datagrid('load', formData);
    }
})