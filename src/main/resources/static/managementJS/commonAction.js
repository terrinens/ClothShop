/*import {Modal} from "../assets/vendor/bootstrap/js/bootstrap.esm.js";*/

const $buttonSearch = $('#button_search');
const $searchKeyword = $('#searchKeyword');
const $searchOption = $('#searchOption');

let commonAjax = null;
let commonModifyAjax = null;
let commonDELAjax = null

/**검색시 공통적으로 사용되는 키 맵핑*/
export function commonAction(sendAjax, sendModifyAjax, sendDELAjax) {
    commonAjax = sendAjax;
    commonModifyAjax = sendModifyAjax;
    commonDELAjax = sendDELAjax;

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

    const btn_reset = $('#button_reset');
    btn_reset.on('click', function () {
        $('#modifyForm')[0].reset();
    });
}


/**commonAction에 할당된것들에 의존한다. Ajax가 불러들이는 html에서 import할것*/
export function commonLink() {
    let uri = null;
    $('.call_delete_modal').on('click', function () {
        uri = $(this).data('uri');
    });

    $('.button_delete').on('click', function () {
        location.href = uri;
    });

    const $pageLink = $('.page-link');

    $pageLink.on('click', function () {
        const page = $(this).data('page');
        const keyword = $searchKeyword.val();
        const option = $searchOption.val();

        commonAjax(page, keyword, option);
    });

    const $buttonModify = $('.button_modify');

    $buttonModify.on('click', function (event) {
        event.preventDefault();
        const formSelect = $(this).closest("form")
        const formData = new FormData(formSelect[0]);
        const $originPwd = formSelect.find('.recipient-pwd');
        const $modifyPwd = formSelect.find('.recipient-pwdModify');

        if (!$modifyPwd.val() > 1) {
            alert("비밀번호를 정확히 입력해주세요.");
        } else {
            /*const modal = new Modal(formSelect.find('.modal.fade').toggle());*/
            $originPwd.val($modifyPwd.val());
            console.log("수정된 비밀번호 ::: " + $originPwd.val());
            let serachData = {
                page: 0
                , keyword: $searchKeyword.val()
                , option: $searchOption.val()
            };
            const sendData = {
                formData: Object.fromEntries(formData.entries())
                , serachData: serachData
            };
            commonModifyAjax(sendData);
        }
    });
}