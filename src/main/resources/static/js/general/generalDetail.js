//댓글 관련

var textarea = document.querySelector('.comment_inbox_text');
var currentCharCountElement = document.querySelector('.current_char_count');
var maxLength = 100;

function updateCharCount() {
    var currentCharCount = textarea.value.length;
    currentCharCountElement.textContent = currentCharCount;
}

function limitLength() {
    if (textarea.value.length > maxLength) {
        textarea.value = textarea.value.substring(0, maxLength);
    }
}

function adjustHeight() {
    textarea.style.height = 'auto';
    textarea.style.height = Math.min(textarea.scrollHeight, 300) + 'px';
}

textarea.addEventListener('input', function() {
    limitLength();
    updateCharCount();
    adjustHeight();
});

// 페이지 로드 시 초기 글자 수 업데이트
updateCharCount();




//삭제 버튼 클릭 시
function confirmDelete() {
    // confirm 함수를 사용하여 확인 대화 상자를 표시
    var result = confirm("정말 삭제하시겠습니까?");

    // 사용자가 확인을 클릭하면 true를 반환하고, 취소를 클릭하면 false를 반환
    if (result) {
        // 확인을 클릭한 경우, 폼을 서브밋합니다.

        document.getElementById("deleteForm").submit();
    } else {
        // 취소를 클릭한 경우, 폼 서브밋을 막고 알림 메시지를 표시합니다.

        event.preventDefault();
    }
}

//수정 버튼 클릭 시
function confirmModify() {
    // confirm 함수를 사용하여 확인 대화 상자를 표시
    var result = confirm("게시글 수정 페이지로 이동하시겠습니까?");

    // 사용자가 확인을 클릭하면 true를 반환하고, 취소를 클릭하면 false를 반환
    if (result) {
        // 확인을 클릭한 경우, 폼을 제출

        document.getElementById("modifyForm").submit();
    } else {
        // 취소를 클릭한 경우, 폼 제출을 막는다

        event.preventDefault();
    }
}