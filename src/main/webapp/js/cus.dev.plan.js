$(function() {
    var saleChanceId = $("#saleChanceId").val();
    $("#dg").edatagrid({
        url:'list?saleChanceId=' + saleChanceId,
        saveUrl:'add?saleChanceId=' + saleChanceId,
        updateUrl:'update',
        destroyUrl:'delete'
    });
});

function updateSaleChanceDevResult(devResult) {
    var param = {};
    param.saleChanceId = $("#saleChanceId").val();
    param.devResult = devResult;
    $.post("update_dev_result", param, function(result) {
        if(result.resultCode == 1) {
            $.messager.alert("系统提示", "操作成功！");
        } else {
            $.messager.alert("系统提示","操作失败！");
        }
    });
}
