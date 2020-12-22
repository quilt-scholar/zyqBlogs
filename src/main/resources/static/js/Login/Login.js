$(function () {
    var inp=$("input");
    for(var i=0;i<$("input").length-2;i++){
        inp.eq(i).focus(function(){
            $(this).animate({
                width:'262px'
            },200);
        })
        inp.eq(i).blur(function(){
            $(this).animate({
                width:'232px'
            },200);
        });
    }
});
