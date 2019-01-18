
var speedyC;

function add(flag) {
    switch (flag) {
        case 0:
            layer.open({
                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['520px', '340px'], //宽高
                title: '提交留言',
                content: $("#smsg").html(),
            });
            break;
        case 1:
            layer.alert('QQ群号: 767477379');
            break;
        case 2:
            layer.alert('微信号: xiaocry1314');
            break;
        case 3:
            layer.alert('QQ号: 1067357662');
            break;
        case 4:
            layui.use(['form'], function(){
                var form = layui.form
                    ,layer = layui.layer;

                layer.open({
                    title: '设置',
                    type: 1,
                    skin: 'layui-layer-rim', //加上边框
                    area: ['520px', '340px'], //宽高
                    content: $("#setting").html(),
                });

                //监听指定开关
                form.on('switch(speedy)', function(data){
                    callSpeedy(this.checked);
                });

                //表单初始赋值
                form.val('setting', {
                    "speedy": speedyC
                });

                form.render();

            });
            break;
    }
}

function winMin() {
    callWinMin();
}

function winMax() {
    layui.use("layer",function(){
        layer.msg('点击这个按钮是无效滴！兄dei~');
    });
}

function winClose() {

    layui.use("layer",function(){
        layer.confirm('确定关闭软件吗？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            callWinClose();
        }, function(){
        });
    });

}
