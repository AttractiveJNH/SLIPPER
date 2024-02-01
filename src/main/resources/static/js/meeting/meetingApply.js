// 거절 목록 버튼
$(document).ready(function(){
    $(".refuse_list_content").hide(); // 페이지 로드 시 숨김

    $(".toggle-refuse-list").click(function(){
        $(".refuse_list_content").toggle();
        // 버튼 텍스트 변경
        var buttonText = $(this).text();
        if (buttonText === "거절 목록 보기") {
            $(this).text("거절 목록 접기");
        } else {
            $(this).text("거절 목록 보기");
        }
    });
});


