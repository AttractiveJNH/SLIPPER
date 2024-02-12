// 신청하기 or 신청자 보기
// 링크 클릭 시 실행되는 함수
function openWindow(url) {
  // 화면 중앙을 기준으로 창의 위치 계산
  var screenWidth = window.screen.width;
  var screenHeight = window.screen.height;

  var windowWidth = 850;
  var windowHeight = 700;


  var left = (screenWidth - windowWidth) / 2;
  var top = (screenHeight - windowHeight) / 2;



  // 새로운 창 열기
  var newWindow = window.open(url, '_blank', 'width=850,height=700,left=' + left + ',top=' + top);

  // 창이 차단되었거나 팝업 창을 지원하지 않는 경우
  if (!newWindow || newWindow.closed || typeof newWindow.closed == 'undefined') {
    alert('팝업 창이 차단되었습니다. 팝업 차단을 해제하고 다시 시도하세요.');
  }
}


// 신청 하기 이미지 클릭 시 폼 제출
function applySubmitForm(promoBrdPostId) {
    document.getElementById('promotion_apply').submit();
}




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
