const $buttonSearch = $('#button_search');
const $searchKeyword = $('#searchKeyword');
const $searchOption = $('#searchOption');
const $pageLink = $('.page-link');
const $memberAjax = $('#memberAjax');
const $htmlMemberPagingLocation = $('.memberPaging');
const $htmlPagingNumberBox = $('#pagingNumberBox');
const $htmlMemberTbody = $('#memberTbody');
let lodingCheck = false;

$(function () {
    sendAjax(0, "", "all");
})

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

function sendAjax(page, keyword, option) {
    if (page === null) {page = 0} if (option === null) {option = ""}
    $.ajax({
        type: 'get'
        ,url: "/management/member-Ajax"
        ,data: {page: page, option: option, keyword: keyword}
        ,success: function (html) {
            $memberAjax.html(html);
        }
    });
}

export {sendAjax};