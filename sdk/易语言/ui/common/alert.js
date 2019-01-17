
var index;

loadingStart();
function loadingStart() {
    //$("body").mLoading("show");
    layui.use("layer",function(){
        index = layer.load(1, {
            shade: [0.1,'#919191']
        });
    });

}
function loadingClose() {
    //$("body").mLoading("hide");
    layui.use("layer",function(){
        layer.close(index);
    });
}
function layerAlert(content) {
    layui.use("layer",function(){
        loadingClose();
        layer.msg(content);
    });
}


