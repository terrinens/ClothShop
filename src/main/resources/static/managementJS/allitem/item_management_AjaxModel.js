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
        type: 'post',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        url: "/management/item/modify-Ajax",
        data: sendData,
        processData: false,
        contentType: false,
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
        data: sendData,
        processData: false,
        contentType: false,
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
        let partSendForm = new FormData();
        const $newItemImg = newItemFormSelect.find($('.newItemImg'));
        conversionJsonToBlob("formData", formData, partSendForm);
        conversionJsonToBlob("searchData", searchData, partSendForm);
        appendSingleFileToFormData("imgData" , $newItemImg, partSendForm)

        sendNewAjax(partSendForm);
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
        const searchDataObject = {page: "0", keyword: $searchKeyword.val(), option: $searchOption.val()};
        const $imgInput = $modifyForm.find($('.recipient-img'));

        validCheck($recipientName);
        validCheck($recipientPrice);

        if (validCount >= 1) {
            return false;
        }

        const formData = new FormData($modifyForm[0]);
        let partSendForm = new FormData();
        conversionJsonToBlob("formData", formData, partSendForm);
        conversionJsonToBlob("searchData", searchDataObject, partSendForm);
        appendSingleFileToFormData("imgData", $imgInput, partSendForm);

        const $modalBackdrop = $('.modal-backdrop');
        $modalBackdrop.remove();
        sendModifyAjax(partSendForm);
    })

    /**아이템 수정 이미지 인풋시 변경할 이미지 미리보기*/
    const $recipient_img = $('.recipient-img');
    $recipient_img.change(function () {
        const imgInput = this;
        const $falseValueImgBox = $(this).siblings('.accordion').find($('.falseValueImgBox'));
        const $modifyImg = $(this).siblings('.accordion').find($('.moidfyImg'));
        if (imgInput.files && imgInput.files[0]) {
            let reader = new FileReader();
            reader.readAsDataURL(imgInput.files[0]);
            reader.onload = function (event) {
                if ($modifyImg.length > 0) {
                    $modifyImg.attr('src', event.target.result);
                } else {
                    $falseValueImgBox.empty();
                    $falseValueImgBox.append(
                        '<img src="/noimg.png" class="savedImg rounded img-fluid img-thumbnail" style="width: 45%">'
                        + '<img src="/arrow-bar-right.svg">'
                        + '<img src="'+event.target.result+'" class="moidfyImg rounded img-fluid img-thumbnail" style="width: 45%">'
                    );
                }
            }
        }
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


/**JSON 데이터를 FormData에서 Blob으로 변환하여 targetSendForm에 추가하는 메서드 form 클래스나 key:value를 보낼것
 * @param {string} appendName - Blob을 append할 때 사용할 이름
 * @param {Object|FormData} targetData - 변환할 JSON 데이터나 FormData
 * @param {FormData} targetNewForm - Blob을 추가할 대상 FormData
 * @returns {void} - Blob을 대상 FormData에 추가.
 */
function conversionJsonToBlob(appendName, targetData, targetNewForm) {
    let form = new FormData;
    if (!(targetData instanceof FormData) && targetData instanceof Object) {
        let conversionNewForm = new FormData();
        for (const key in targetData) {
            conversionNewForm.append(key, targetData[key])
            form = conversionNewForm;
        }
    } else {
        if (targetData.has("img")) {
            form = targetData;
            form.delete("img");
        }
    }

    let object = {};
    form.forEach((value, key) => {
        object[key] = value;
    });
    const relust = new Blob([JSON.stringify(object)], {type: 'application/json'});
    return targetNewForm.append(appendName, relust, 'data.json');
}

/**해당 클래스에 이미지가 존재할시에 targetNewForm에 append하는 메서드
 * @param {string} appendName - append할 때 사용할 이름
 * @param {Class} targetInputClass - 해당 인풋 박스의 클래스
 * @param {FormData} targetNewForm - 이미지값을 추가할 대상 FormData
 * @return {void} - 이미지를 대상 FormData에 추가.*/
function appendSingleFileToFormData(appendName, targetInputClass, targetNewForm) {
    if (targetInputClass[0] && targetInputClass[0].files[0]) {
        targetNewForm.append(appendName, targetInputClass[0].files[0]);
    }
}



