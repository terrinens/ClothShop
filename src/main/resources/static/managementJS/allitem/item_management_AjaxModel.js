import {firstLodingAjax} from "../firstLodingAjax.js";
import {$searchKeyword, $searchOption, commonAction} from "../commonAction.js";

const $allItemAjax = $('#allItemAjax');
const token = $("meta[name='_csrf']").attr("content")
const header = $("meta[name='_csrf_header']").attr("content");

commonAction(sendAjax, sendModifyAjax, sendDELAjax);
firstLodingAjax(sendAjax);

function sendAjax(page, keyword, option) {
    if (page === null) {
        page = 0
    }
    if (option === null) {
        option = ""
    }
    $.ajax({
        type: 'get'
        , url: "/management/allitem-Ajax"
        , data: {page: page, option: option, keyword: keyword}
        , success: function (html) {
            $allItemAjax.html(html);
        }
    });
}

function sendModifyAjax(sendData) {
    $.ajax({
        type: 'put'
        , beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        }
        , url: "/management/item/modify-Ajax"
        , data: JSON.stringify(sendData)
        , contentType: "application/json"
        , async: false
        , success: function (html) {
            $allItemAjax.html(html);
        }
    });
}

function sendDELAjax(sendData) {
    $.ajax({
        type: 'delete'
        , beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        }
        , url: "/management/item/Delete-Ajax"
        , data: JSON.stringify(sendData)
        , contentType: "application/json"
        , success: function (html) {
            $allItemAjax.html(html);
        }
    })
}

function sendNewAjax(sendData) {
    $.ajax({
        type:'post'
        , beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        }
        , url: "/management/item/new-Ajax"
        , data: JSON.stringify(sendData)
        , contentType: "application/json"
        , success: function (html) {
            $allItemAjax.html(html);
        }
    });
}

const $buttonNewItem = $('#buttonNewItem');
const $newItemName = $('#newItemName');
const $newItemPrice = $('#newItemPrice');

/**새상품 등록 버튼 맵핑*/
$buttonNewItem.on('click', function (evnet) {
    evnet.preventDefault();
    const newItemFormSelect = $(this).closest("form");
    const formData = new FormData(newItemFormSelect[0]);

    validCheck($newItemName);
    validCheck($newItemPrice);

    const searchData = {page : 0, keyword : $searchKeyword.val(), option: $searchOption.val()};
    const sendData = {
        formData : Object.fromEntries(formData.entries())
        , searchData: searchData
    };

    sendNewAjax(sendData);
    newItemInputValueEmpty();
})

const $buttonNewItemCancel = $('#buttonNewItemCancel');
$buttonNewItemCancel.on('click', function () {
    $('.is-invalid').removeClass('is-invalid');
})

/**타겟 class OR id 값에 유효성 검사추가*/
function validCheck(targetSelector) {
    if (targetSelector.val().length === 0) {
        targetSelector.addClass('is-invalid');
        $('.is-invalid').first().focus();
        return false;
    } else {
        if (targetSelector.hasClass('is-invalid')) {
            targetSelector.removeClass('is-invalid');
        }
        targetSelector.addClass('is-valid');
    }
}

const newItemBoxModal = new bootstrap.Modal($('#newItemBox'));
/**상품 등록 성공시 기존 값들을 없애기 위한 메서드*/
function newItemInputValueEmpty() {
    newItemBoxModal.hide();
    $newItemName.removeClass('is-valid');
    $newItemName.val(null);
    $newItemPrice.removeClass('is-valid');
    $newItemPrice.val(null);
    $('#newItemContents').val(null);
}

