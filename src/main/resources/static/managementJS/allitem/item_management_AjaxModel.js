import {firstLodingAjax} from "../firstLodingAjax.js";
import {$searchKeyword, $searchOption, commonAction, validCheck, validCount} from "../commonAction.js";

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
        type: 'get',
        url: "/management/allitem-Ajax",
        data: {page: page, option: option, keyword: keyword},
        success: function (html) {
            $allItemAjax.html(html);
        }
    });
}

function sendModifyAjax(sendData) {
    $.ajax({
        type: 'put',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        url: "/management/item/modify-Ajax",
        data: JSON.stringify(sendData),
        contentType: "application/json",
        async: false,
        success: function (html) {
            $allItemAjax.html(html);
        }
    });
}

function sendDELAjax(sendData) {
    $.ajax({
        type: 'delete',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        url: "/management/item/Delete-Ajax",
        data: JSON.stringify(sendData),
        contentType: "application/json",
        success: function (html) {
            $allItemAjax.html(html);
        }
    })
}

function sendNewAjax(sendData) {
    $.ajax({
        type: 'post',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        url: "/management/item/new-Ajax",
        data: JSON.stringify(sendData),
        contentType: "application/json",
        success: function (html) {
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
    if (validCount > 0 || validCount == null) {
        return false;
    } else {
        const searchData = {page: 0, keyword: $searchKeyword.val(), option: $searchOption.val()};
        const sendData = {
            formData: Object.fromEntries(formData.entries()), searchData: searchData
        };
        sendNewAjax(sendData);
        newItemInputValueEmpty();
    }
})

const $buttonNewItemCancel = $('#buttonNewItemCancel');
$buttonNewItemCancel.on('click', function () {
    $('.is-invalid').removeClass('is-invalid');
})

/**allitem Ajax html에서 import 할것 아이템 수정을 위한 버튼*/
export function itemButtonModifyMppaing() {
    const $item_button_modify = $('.item_button_modify');
    $item_button_modify.on('click', function (event) {
        event.preventDefault();
        const $modifyForm = $(this).parent($('.modifyForm'));
        const formData = new FormData($modifyForm[0]);
        const $recipientName = $modifyForm.find($('.recipient-name'));
        const $recipientPrice = $modifyForm.find($('.recipient-price'));
        validCheck($recipientName);
        validCheck($recipientPrice);
        const searchData = {page: 0, keyword: $searchKeyword.val(), option: $searchOption.val()};
        const sendData = {
            formData: Object.fromEntries(formData.entries()), searchData: searchData
        };
        sendModifyAjax(sendData);
    })
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

