import {firstLodingAjax} from "../firstLodingAjax.js";
import {$searchKeyword, $searchOption, commonAction, validCheck, validCount} from "../commonAction.js";

const $allItemAjax = $('#allItemAjax');
const token = $("meta[name='_csrf']").attr("content")
const header = $("meta[name='_csrf_header']").attr("content");

commonAction(sendAjax, sendModifyAjax, sendDELAjax);
firstLodingAjax(sendAjax);

function sendAjax(page, keyword, option) {
    $allItemAjax.empty();
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
    $allItemAjax.empty();
    $.ajax({
        type: 'put',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        url: "/management/item/modify-Ajax",
        data: JSON.stringify(sendData),
        contentType: "application/json",
        success: function (html) {
            $allItemAjax.html(html);
        }
    });
}

function sendDELAjax(sendData) {
    $allItemAjax.empty();
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
    $allItemAjax.empty();
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

// noinspection JSUnusedGlobalSymbols
/**allitem Ajax html에서 import 할것 아이템 수정을 위한 버튼*/
export function itemButtonModifyMppaing() {
    const $item_button_modify = $('.item_button_modify');
    $item_button_modify.on('click', function (event) {
        event.preventDefault();
        const $modifyForm = $(this).parent($('.modifyForm'));
        const $recipientName = $modifyForm.find($('.recipient-name'));
        const $recipientPrice = $modifyForm.find($('.recipient-price'));
        const searchDataArray = {page: 0, keyword: $searchKeyword.val(), option: $searchOption.val()};
        const $imgInput = $modifyForm.find($('.recipient-img')).first();
        console.log($imgInput.attr('accept'));
        console.log($imgInput.length);
        const sendData = conversionCommonFormData($modifyForm, searchDataArray, $imgInput);

        validCheck($recipientName);
        validCheck($recipientPrice);

        const $modalBackdrop = $('.modal-backdrop');
        $modalBackdrop.remove();
        console.log(sendData);
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

/**이미지 파일을 따로 처리하기 위한 코드*/
function conversionCommonFormData(targetFormClass, searchDataArray, imgInputClass) {
    let formData = new FormData(targetFormClass[0]);
    formData.delete('img');
    const imgBase64 = convertImageToBase64(imgInputClass);
    console.log(imgBase64);
    return {
        formData: Object.fromEntries(formData.entries())
        , searchData: searchDataArray
        , imgBase64 : imgBase64
    };
}

function convertImageToBase64(imgInputClass) {
    const imgData = imgInputClass[0].files;
    console.log("클래스 넘버 지정하기")
    console.log(imgData);
    const imgDataA = imgData[0];
    console.log("해당 클래스 이미지 가져오기")
    console.log(imgDataA);
    const reader = new FileReader();
    reader.readAsDataURL(imgDataA);
    reader.onload = function (convert) {
        //콜백 함수로 바꿔볼것.
        return convert.target.result;
    }
}

