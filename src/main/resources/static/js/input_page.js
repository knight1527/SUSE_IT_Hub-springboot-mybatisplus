/*md编辑器js*/
var contentEditor;
$(function() {
    contentEditor = editormd("md-content", {
        width   : "100%",
        height  : 640,
        syncScrolling : "single",
        path    : "../static/lib/editormd/lib/",
        saveHTMLToTextarea : true, // 保存 HTML 到 Textarea
        toolbarAutoFixed:true,//工具栏自动固定定位的开启与禁用
    });

    /*
    // or
    testEditor = editormd({
        id      : "test-editormd",
        width   : "90%",
        height  : 640,
        path    : "../lib/"
    });
    */
});
/*文件上传*/
function select_file(){
    $("#file").trigger("click");
}
function fileUpload(){
    var file =  $('#file')[0].files[0]; //获取文件对象
    //检测文件是不是图片
    if(file.type.indexOf('image') === -1){
        alert("您拖的不是图片！");
        return false;
    }

    //拖拉图片到浏览器，可以实现预览功能
    var img = window.URL.createObjectURL(file);
    var filename = file.name; //图片名称
    var filesize = Math.floor((file.size)/1024);
    if(filesize>5120){
        alert("上传大小不能超过5MB.");
        return false;
    }
    var str = "<img src='"+img+"'width='400px'height='240px'><p>图片名称："+filename+"</p><p>大小："+filesize+"KB</p>";
    $("#preview").html(str);
    // 创建formdata对象
    var formData = new FormData();
    // 给formData对象添加<input>标签,注意与input标签的ID一致
    formData.append('file',file);
    $.ajax({
        url : '/upload',//这里写你的url
        type : 'POST',
        data : formData,
        contentType: false,// 当有文件要上传时，此项是必须的，否则后台无法识别文件流的起始位置
        processData: false,// 是否序列化data属性，默认true(注意：false时type必须是post)
        dataType: 'json',//这里是返回类型，一般是json,text等
        clearForm: true,//提交后是否清空表单数据
        success: function(data) {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
            aler('上传成功');
        },
        error: function(data, status, e) {  //提交失败自动执行的处理函数。
            console.error(e);
        }
    });
}
$(function(){
    //阻止浏览器默认行。
    $(document).on({
        dragleave:function(e){    //拖离
            e.preventDefault();
        },
        drop:function(e){  //拖后放
            e.preventDefault();
        },
        dragenter:function(e){    //拖进
            e.preventDefault();
        },
        dragover:function(e){    //拖来拖去
            e.preventDefault();
        }
    });
    var box = document.getElementById('drop_area'); //拖拽区域
    box.addEventListener("drop",function(e){
        e.preventDefault(); //取消默认浏览器拖拽效果
        var fileList = e.dataTransfer.files; //获取文件对象
        //检测是否是拖拽文件到页面的操作
        if(fileList.length == 0){
            return false;
        }
        //检测文件是不是图片
        if(fileList[0].type.indexOf('image') === -1){
            alert("您拖的不是图片！");
            return false;
        }

        //拖拉图片到浏览器，可以实现预览功能
        var img = window.URL.createObjectURL(fileList[0]);
        var filename = fileList[0].name; //图片名称
        var filesize = Math.floor((fileList[0].size)/1024);
        if(filesize>5120){
            alert("上传大小不能超过5MB.");
            return false;
        }
        var str = "<img src='"+img+"'width='400px'height='240px'><p>图片名称："+filename+"</p><p>大小："+filesize+"KB</p>";
        $("#preview").html(str);

        //上传
        xhr = new XMLHttpRequest();
        xhr.open("post", "upload", true);
        xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");

        var fd = new FormData();
        fd.append('mypic', fileList[0]);

        xhr.send(fd);
    },false);
});
/*
<script>
    /!*md编辑器js*!/
    var contentEditor;
    $(function() {
    contentEditor = editormd("md-content", {
        width   : "100%",
        height  : 640,
        syncScrolling : "single",
        path    : "../static/lib/editormd/lib/",
        saveHTMLToTextarea : true, // 保存 HTML 到 Textarea
        toolbarAutoFixed:true,//工具栏自动固定定位的开启与禁用
    });

    /!*
    // or
    testEditor = editormd({
        id      : "test-editormd",
        width   : "90%",
        height  : 640,
        path    : "../lib/"
    });
    *!/
});
    /!*文件上传*!/
    function select_file(){
    $("#file").trigger("click");
}
    function fileUpload(){
    var file =  $('#file')[0].files[0]; //获取文件对象
    //检测文件是不是图片
    if(file.type.indexOf('image') === -1){
    alert("您拖的不是图片！");
    return false;
}

    //拖拉图片到浏览器，可以实现预览功能
    var img = window.URL.createObjectURL(file);
    var filename = file.name; //图片名称
    var filesize = Math.floor((file.size)/1024);
    if(filesize>5120){
    alert("上传大小不能超过5MB.");
    return false;
}
    var str = "<img src='"+img+"'width='400px'height='240px'><p>图片名称："+filename+"</p><p>大小："+filesize+"KB</p>";
    $("#preview").html(str);
    // 创建formdata对象
    var formData = new FormData();
    // 给formData对象添加<input>标签,注意与input标签的ID一致
    formData.append('file',file);
    $.ajax({
    url : '/upload',//这里写你的url
    type : 'POST',
    data : formData,
    contentType: false,// 当有文件要上传时，此项是必须的，否则后台无法识别文件流的起始位置
    processData: false,// 是否序列化data属性，默认true(注意：false时type必须是post)
    dataType: 'json',//这里是返回类型，一般是json,text等
    clearForm: true,//提交后是否清空表单数据
    success: function(data) {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
    aler('上传成功');
},
    error: function(data, status, e) {  //提交失败自动执行的处理函数。
    console.error(e);
}
});
}
    $(function(){
    //阻止浏览器默认行。
    $(document).on({
        dragleave:function(e){    //拖离
            e.preventDefault();
        },
        drop:function(e){  //拖后放
            e.preventDefault();
        },
        dragenter:function(e){    //拖进
            e.preventDefault();
        },
        dragover:function(e){    //拖来拖去
            e.preventDefault();
        }
    });
    var box = document.getElementById('drop_area'); //拖拽区域
    box.addEventListener("drop",function(e){
    e.preventDefault(); //取消默认浏览器拖拽效果
    var fileList = e.dataTransfer.files; //获取文件对象
    //检测是否是拖拽文件到页面的操作
    if(fileList.length == 0){
    return false;
}
    //检测文件是不是图片
    if(fileList[0].type.indexOf('image') === -1){
    alert("您拖的不是图片！");
    return false;
}

    //拖拉图片到浏览器，可以实现预览功能
    var img = window.URL.createObjectURL(fileList[0]);
    var filename = fileList[0].name; //图片名称
    var filesize = Math.floor((fileList[0].size)/1024);
    if(filesize>5120){
    alert("上传大小不能超过5MB.");
    return false;
}
    var str = "<img src='"+img+"'width='400px'height='240px'><p>图片名称："+filename+"</p><p>大小："+filesize+"KB</p>";
    $("#preview").html(str);

    //上传
    xhr = new XMLHttpRequest();
    xhr.open("post", "upload", true);
    xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");

    var fd = new FormData();
    fd.append('mypic', fileList[0]);

    xhr.send(fd);
},false);
});
</script>*/
