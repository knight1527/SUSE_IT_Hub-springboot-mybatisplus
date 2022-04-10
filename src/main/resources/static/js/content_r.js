/*关注*/
$('#focus_on').click(function () {
    let tep = $('#focus_on').children("span")
    let tepi = $("#focus_on").children("i")
    if(tep.css("display") === "none"){
        /*alert('取消关注')*/
        tep.css("display", "block")
        tepi.css("display", "none")
        focusAjax(false)
    }else{
        /*alert("添加关注")*/
        tep.css("display", "none")
        tepi.css("display", "block")
        focusAjax(true)
    }
})


/*评分*/
$(".ui.rating").rating();
$("#rating").popup({
    popup: $('.ui.flowing.popup.top'),
    on: 'click',
    delay: {
        show: 300,
        hide: 800
    }
});
$('#link').text(window.location.href)
$("#share-btn").popup({
    popup: $('.ui.flowing.popup.top'),
    on: 'click',
    delay: {
        show: 300,
        hide: 800
    }
});
$('#rating_ok').click(function () {
    let id = $('#ajaxTool').val();
    let score = $('.ui.star.huge.rating').rating("get rating");
    $.ajax({
        url: '/rating',
        type: 'GET',
        data: {
            id,
            score
        },
        datatype: 'text',
        success: function (data){
           /* alert(data)*/
            $('.ui.star.huge.rating').rating("set rating",score);
            $('.ui.star.huge.rating').rating("disable");
        }
    })
    $('#rating').trigger('click');
})
/*点赞*/
$('#dianzan').click(function () {
    let id = $('#ajaxTool').val();
    $.ajax({
        url: '/dianzan',
        type: 'GET',
        data:{
            id
        },
        dataType: 'text',
        success: function(data){
        }
    })
    let bool =  $(this).children("button");
    if(bool.hasClass("m-color-background-tiffany")) {
        bool.removeClass("m-color-background-tiffany");
    }else{
        bool.addClass("m-color-background-tiffany");
    }
})
/*收藏*/
$('#shoucang').click(function () {
    let className =  $(this).children("button").attr("class");
    let id = $('#ajaxTool').val();
    $.ajax({
        url: '/shoucang',
        type: 'GET',
        data:{
            className,
            id
        },
        dataType: 'text',
        success: function (data){
            let bool =  $("#shoucang").children("button");
            if(bool.hasClass("m-color-background-jianghuang")) {
                bool.removeClass("m-color-background-jianghuang");
            }else{
                bool.addClass("m-color-background-jianghuang");
            }
        }
    })
})
/*评论点赞*/
$('.commentLikes').click(function () {
    let id = $(this).data('commentid');
    /*alert(id)*/
    $.ajax({
        url : '/commentlikes',
        type: 'GET',
        data:{
            id
        },
        datatype: 'text',
        success: function(data){
            /*alert("success!!");*/
            let selector = "."+id;
            $(selector).html(data)
            let selector2 = selector+"likesTips"
            $(selector2).show().delay(2000).hide(0);
        }
    })
})