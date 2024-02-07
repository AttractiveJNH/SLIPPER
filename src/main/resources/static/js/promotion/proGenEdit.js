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

    if(Content.trim() === '') {
        alert("내용을 입력해주세요.");
        oEditors.getById["editorTxt"].exec("FOCUS");
        return
    } else {
        let promotionBoard = {
        promoBrdContent: Content
        };

        $.ajax({
            url: "/promotion/genEditSave/{Id}(Id=${genEdit.promoBrdPostId})"
            , type: "POST"
            , data: promotionBoard
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

