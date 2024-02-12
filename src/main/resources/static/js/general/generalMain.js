// 카테고리 선택시 컨트롤러의 @GetMapping("/general/sorting") 실행
function submitForm() {

    var categoryValue = document.getElementById('category').value;

    // localStorage에 선택한 값 저장
    localStorage.setItem('selectedCategory', categoryValue);

    var actionUrl = "/general/sorting?category=" + categoryValue + "&page=0";
    window.location.href = actionUrl;
}

