// 지역 및 카테고리 선택시 컨트롤러의 @GetMapping("/promotion/sorting") 실행
function selectForm() {
    var regionValue = document.getElementById('region').value;
    var categoryValue = document.getElementById('category').value;

    // localStorage에 선택한 값 저장
    localStorage.setItem('selectedRegion', regionValue);
    localStorage.setItem('selectedCategory', categoryValue);

    var actionUrl = "/promotion/sorting?region=" + regionValue + "&category=" + categoryValue + "&page=0";
    window.location.href = actionUrl;
}

// 남은 날짜 계산 함수
$(document).ready(function() {
    if ($(".card-EventEndDate").hasClass("experience-card")) {
        displayRemainingExpDay();
    } else if ($(".card-EventEndDate").hasClass("event-card")) {
        displayRemainingDays();
    }
});

// promoBrdCategory가 체험(2)인 경우 체험일과 현재 날짜 비교.
function displayRemainingExpDay() {
    $(".card-EventEndDate").each(function() {
        var promoBrdExperienceDate = new Date($(this).text()); // 체험일을 Date 객체로 변환
        var currentDate = new Date(); // 현재 날짜를 Date 객체로 변환

        // 남은 날짜 계산.
        var timeDifference = promoBrdExperienceDate.getTime() - currentDate.getTime();
        var remainingDays = Math.ceil(timeDifference / (1000 * 3600 * 24));

        // 결과 표시함.
        if (remainingDays > 0) {
            $(this).append(" (남은 날짜: " + remainingDays + "일)");
        } else {
            $(this).append(" (종료된 이벤트입니다.)");
        }
    });
}

// promoBrdCategory가 이벤트(3)인 경우 이벤트 종료일과 현재 날짜 비교하여 남은 날짜를 표시하는 함수.
function displayRemainingDays() {
    $(".card-EventEndDate").each(function() {
        var eventEndDate = new Date($(this).text()); // 이벤트 종료일을 Date 객체로 변환
        var currentDate = new Date(); // 현재 날짜를 Date 객체로 변환

        // 남은 날짜 계산.
        var timeDifference = eventEndDate.getTime() - currentDate.getTime();
        var remainingDays = Math.ceil(timeDifference / (1000 * 3600 * 24));

        // 결과 표시함.
        if (remainingDays > 0) {
            $(this).append(" (남은 날짜: " + remainingDays + "일)");
        } else {
            $(this).append(" (종료된 이벤트입니다.)");
        }
    });
}
