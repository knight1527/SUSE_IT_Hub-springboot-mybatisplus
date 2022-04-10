/*点赞*/
$('#dianzan').click(function () {
    let id = $('#ajaxTool').val()
    $.ajax({
        url: '/dianzan',
        type: 'GET',
        data:{
            id
        },
        dataType: 'text',
        success: function(data){
            $('#dianzan').html(data)
            $("#dianzanTips").show().delay(2000).hide(0);
        }
    })
});
/*收藏*/
$('#shoucang').click(function () {
    let className = $('#collect').attr('class');
    let id = $('#ajaxTool').val()
    $.ajax({
        url: '/shoucang',
        type: 'GET',
        data:{
            className,
            id
        },
        dataType: 'text',
        success: function (data){
            $('#shoucang').html(data)
        }
    })
})
/*评分完成*/
$('#rating_cancel').click(function () {
    $('.special.modal')
        .modal('hide');
})
$('#rating_ok').click(function () {
    let id = $('#ajaxTool').val()
    let score = $('.ui.star.huge.rating').rating("get rating");
    $.ajax({
       url:'/rating',
       type: 'GET',
       data:{
           id,
           score
       } ,
        dataType: 'text',
        success: function (data){
           /*alert(data)*/
           $('#rating-box').rating("set rating",score);
           $('#rating-box').rating("disable");
        }
    });
    $('.special.modal')
        .modal('hide');
});
/*分享*/
$('#link').text(window.location.href)
$("#share-btn").popup({
    popup: $('.ui.flowing.popup.top'),
    on: 'click',
    delay: {
        show: 300,
        hide: 800
    }
});
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
           /* alert("success!!");*/
            let selector = "."+id;
            $(selector).html(data)
            let selector2 = selector+"likesTips"
            $(selector2).show().delay(2000).hide(0);
        }
    })
})
