/*标签AJAX*/
$('.typeObj').click(function () {
    let value = $(this).data("value");
    $.ajax({
        url:'/self/tagTypes',
        type: 'GET',
        data:{
            value
        },
        dataType: 'text',
        success: function(data){
            $('#tagTypes').html(data)
        }
    })
})
//下拉列表
$('.ui.dropdown').dropdown({
    transition: 'drop'
});