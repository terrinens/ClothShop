const $buttonSearch = $('#button_search');
const $searchKeyword = $('#searchKeyword');
const $searchOption = $('#searchOption');
export {$searchKeyword, $searchOption};

let commonAjax = null;
let commonModifyAjax = null;
let commonDELAjax = null;

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

    /**삭제시 빠른작업을 위해 Esc 혹은 Enter이벤트 추가*/
    const $delete_modal = $('#delete_modal');
    const $button_delete = $delete_modal.find($('.button_delete'));
    const $button_cancel = $delete_modal.find($('.button_cancel'));
    $delete_modal.on('keydown', function (event) {
        if (event.key === 'Enter') {
            $button_delete.focus();
            $button_delete.click();
        } else if (event.key === 'Escape') {
            $button_cancel.focus();
            $button_cancel.click();
        }
    })
}


// noinspection JSUnusedGlobalSymbols
/**commonAction에 할당된것들에 의존한다. 컨트롤에서 보내는 html에서 import할것*/
export function commonLink() {
    const $button_delete = $('.button_delete');
    let targetId = null;
    $('.call_delete_modal').on('click', function () {
        targetId = $(this).data('id');
        $(this).off('click');
    });

    $button_delete.off('click');
    $button_delete.on('click', function (event) {
            event.preventDefault();
            const $modalBackdrop = $('.modal-backdrop');
            $modalBackdrop.remove();
            let searchData = null;
            searchData = {
                page: 0
                , keyword: $searchKeyword.val()
                , option: $searchOption.val()
            };
            let sendData = null;
            sendData = {
                targetId: targetId
                , searchData: searchData
            };
            commonDELAjax(sendData);
    });

    const $pageLink = $('.page-link');
    $pageLink.off('click');
    $pageLink.on('click', function () {
        const page = $(this).data('page');
        const keyword = $searchKeyword.val();
        const option = $searchOption.val();
        commonAjax(page, keyword, option);
    });
}

/**오류로 인해 임시적으로 commonaAction에 할당함 멤버 페이지에서만 사용할것*/
export function memberButtonModify() {
    const $buttonModify = $('.button_modify');
    $buttonModify.on('click', function (event) {
        event.preventDefault();
        const formSelect = $(this).closest("form")
        const formData = new FormData(formSelect[0]);
        const $originPwd = formSelect.find('.recipient-pwd');
        const $modifyPwd = formSelect.find('.recipient-pwdModify');
        const saveOriginPwd = $originPwd.val();
        let searchData;
        let sendData = null;

        if ($modifyPwd.val().length <= 3 && $modifyPwd.val().length >= 1) {
            alert("비밀번호를 정확히 입력해주세요.");
            $originPwd.val(saveOriginPwd.val());
        } else {
            const $modalBackdrop = $('.modal-backdrop');
            $modalBackdrop.remove();
            searchData = {page: 0, keyword: $searchKeyword.val(), option: $searchOption.val()};
            sendData = {
                formData: Object.fromEntries(formData.entries())
                , searchData
            };
            commonModifyAjax(sendData);
            $buttonModify.off();
        }
    });
}

export let validCount = 0;

/**타겟 class OR id 값에 유효성 검사추가 validCount를 활용해 brack코드 작성할것 limtMinlenth의 디폴트값은 0*/
export function validCheck(targetSelector, limtMinlenth) {
    let minlenth;
    if (limtMinlenth == null) {
        minlenth = Number(0);
    } else {
        minlenth = Number(limtMinlenth);
    }

    if (targetSelector.val().length <= minlenth) {
        targetSelector.addClass('is-invalid');
        $('.is-invalid').first().focus();
        return validCount++;
    } else {
        if (targetSelector.hasClass('is-invalid')) {
            targetSelector.removeClass('is-invalid');
        }
        targetSelector.addClass('is-valid');
        return validCount = 0;
    }
}

