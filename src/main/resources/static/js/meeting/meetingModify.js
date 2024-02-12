//스마트 에디터 적용
let oEditors = [];

smartEditor = function() {
    console.log("Naver SmartEditor")
    nhn.husky.EZCreator.createInIFrame({
        oAppRef: oEditors,
        elPlaceHolder: "editorTxt",
        sSkinURI: "/ssw/SmartEditor2Skin.html",
        fCreator: "createSEditor2"
    });
};

$(document).ready(function() {
    smartEditor();
});

savePost = function() {
    oEditors.getById["editorTxt"].exec("UPDATE_CONTENTS_FIELD", []);
    let Content = document.getElementById("editorTxt").getIR();
    var url = document.querySelector('form').getAttribute('action');

    if(Content.trim() === '') {
        alert("내용을 입력해주세요.");
        oEditors.getById["editorTxt"].exec("FOCUS");
        return
    } else {
        let meetingBoard = {
        meet_content: Content
        };

        $.ajax({
            url: url
            , type: "POST"
            , data: meetingBoard
            , success: function(data) {
            console.log('success')
            alert('저장하였습니다.')
            }
            , error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR)
            alert('오류가 발생하였습니다.')
            }
        });


    }
};




// 일시 및 신청 마감일 설정
// 오늘의 날짜를 가져오는 함수
function getToday() {
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
    var yyyy = today.getFullYear();

    today = yyyy + '-' + mm + '-' + dd;
    return today;
}

// 오늘 이전의 날짜를 선택하지 못하게 하는 함수
function disablePastDates() {
    var scheduleDate = document.getElementById("schedule").value;
    var today = getToday();

    document.getElementById("schedule").setAttribute("min", today);
    document.getElementById("deadline").setAttribute("min", today);
    document.getElementById("deadline").setAttribute("max", scheduleDate);
}

// 페이지가 로드될 때와 일시 입력 상자에 변경 사항이 있을 때 실행
function handleDateChange() {
    var scheduleDate = document.getElementById("schedule").value;
    var deadlineInput = document.getElementById("deadline");

    // 일시가 선택되었는지 확인하고, 선택되지 않았으면 신청 마감일 입력 상자를 비활성화
    if (scheduleDate === "") {
        deadlineInput.disabled = true;
    } else {
        deadlineInput.disabled = false;
        // 일시가 선택되었을 때, 신청 마감일의 최소 날짜를 오늘로 설정하고 최대 날짜를 선택된 일시로 설정하여 오늘 이전의 날짜는 선택할 수 없도록 함
        deadlineInput.setAttribute("min", getToday());
        deadlineInput.setAttribute("max", scheduleDate);
        // "일시"가 변경될 때마다 "신청 마감일"을 초기화
        deadlineInput.value = "";
    }
}

// 페이지가 로드될 때 실행
window.onload = function() {
    disablePastDates();
}

// 일시 입력 상자에 변경 사항이 있을 때 실행
document.getElementById("schedule").addEventListener("change", function() {
    handleDateChange();
});

