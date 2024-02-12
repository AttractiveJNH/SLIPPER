// 지역 및 카테고리 선택시 컨트롤러의 @GetMapping("/review/sorting") 실행
function selectForm() {
    var regionValue = document.getElementById('region').value;
    var categoryValue = document.getElementById('category').value;

    // localStorage에 선택한 값 저장
    localStorage.setItem('selectedRegion', regionValue);
    localStorage.setItem('selectedCategory', categoryValue);

    var actionUrl = "/review/sorting?region=" + regionValue + "&category=" + categoryValue + "&page=0";
    window.location.href = actionUrl;
}