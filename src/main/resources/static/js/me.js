/*展开评论*/
let comment = $('.comments-none')
comment.click(function () {
    $(this).css('display', 'none')
    $(this).next('.comments').css('display', 'block')
})
/*收藏和评价hover*/
let like_rating = $('.like_rating')
like_rating.hover(function () {
    $(this).next('.ui.mini').css('display', 'inline-block')
},function (){
    $(this).next('.ui.mini').css('display', 'none')
});
//下拉列表
$('.ui.dropdown').dropdown({
    transition: 'drop'
});

$('.search_hover').hover(function () {
    let children = $(this).children('div');
    children.removeClass('m-color-background-tiffany')
    children.addClass('m-color-background-bondiblue')
},function () {
    let children = $(this).children('div');
    children.removeClass('m-color-background-bondiblue')
    children.addClass('m-color-background-tiffany')
})

