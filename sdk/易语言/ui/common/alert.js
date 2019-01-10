
function loadingStart() {
    $("body").mLoading("show");
}
function loadingClose() {
    $("body").mLoading("hide");
}
function layerAlert(content) {
    layui.use("layer",function(){
        loadingClose();
        layer.msg(content);
    });
}
