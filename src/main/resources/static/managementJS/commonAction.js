const $buttonSearch = $('#button_search');
const $searchKeyword = $('#searchKeyword');
const $searchOption = $('#searchOption');

let commonAjax = null;
/**검색시 공통적으로 사용되는 키 맵핑*/
export function commonAction(sendAjax) {
    console.log("공통 키 액션 할당시작");
    commonAjax = sendAjax;
    $buttonSearch.on('click', function (event) {
        event.preventDefault();

        const page = 0;
        const keyword = $searchKeyword.val();
        const option = $searchOption.val();

        sendAjax(page, keyword, option);
    });

    $searchKeyword.add($searchOption).on('keydown', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            $buttonSearch.click();
        }
    });

    let uri = null;
    $('.call_delete_modal').on('click', function () {
        uri = $(this).data('uri');
        console.log("삭제 재확인 모달 불러오기");
        console.log(uri);
    });

    $('.button_delete').on('click', function () {
        location.href = uri;
    });

/*
메모 : 여기서 ajaxhtml이 아니라 그냥 html에서 작동하고 있음 수정할것.
$(function () {
    const $pageLink = $('.page-link');

    console.log("페이지 링크 할당 시작");
    console.log($pageLink.prop('class'));

    $pageLink.on('click', function () {
        console.log("페이지 링크 할당 블락")
        const page = $(this).data('page');
        const keyword = $searchKeyword.val();
        const option = $searchOption.val();

        sendAjax(page, keyword, option);
    });
    */

}