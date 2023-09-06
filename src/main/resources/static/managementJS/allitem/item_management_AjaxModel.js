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
        /*contentType: "application/json",*/
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
        const searchDataObject = {page: "0", keyword: $searchKeyword.val(), option: $searchOption.val()};
        const $imgInput = $modifyForm.find($('.recipient-img'));
        const sendData = conversionCommonFormData($modifyForm, searchDataObject, $imgInput);

        validCheck($recipientName);
        validCheck($recipientPrice);

        if (validCount >= 1) {
            return false;
        }

        const $modalBackdrop = $('.modal-backdrop');
        $modalBackdrop.remove();
        sendModifyAjax(sendData);
    })

    /**이미지 인풋시 변경할 이미지 미리보기*/
    const $recipient_img = $('.recipient-img');
    $recipient_img.change(function () {
        const imgInput = this;
        const $falseValueImgBox = $(this).siblings('.accordion').find('.falseValueImgBox');
        const $modifyImg = $(this).siblings('.accordion').find('.moidfyImg');
        if (imgInput.files && imgInput.files[0]) {
            let reader = new FileReader();
            reader.readAsDataURL(imgInput.files[0]);
            reader.onload = function (event) {
                if ($modifyImg.hasClass('moidfyImg')) {
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

/**이미지 파일을 따로 처리하기 위한 코드 form Class의 경우 FormData로 변환 시켜 넘길것
 * null처리를 완벽하게 구현 못했으니 컨트롤에서 Optional로 받을것.*/
function conversionCommonFormData(targetFormClass, searchDataObject, targetInputClass) {
    function conversionJsonToBlob(targetData) {
        let form = new FormData;
        if (!(targetData instanceof FormData) && targetData instanceof Object) {
            let conversionNewForm = new FormData();
            for (const key in targetData) {
                conversionNewForm.append(key, targetData[key])
                form = conversionNewForm;
            }
        } else {
            if (formData.has("img")) {
                formData.delete("img");
            }
            form = targetData;
        }

        let object = {};
        form.forEach((value, key) => {
            object[key] = value;
        });
        return new Blob([JSON.stringify(object)], {type: 'application/json'});
    }

    let formData = new FormData(targetFormClass[0]);
    let formDataBlob = conversionJsonToBlob(formData);
    let searchDataBlob = conversionJsonToBlob(searchDataObject);

    let partSendForm = new FormData();
    partSendForm.append("formData", formDataBlob, 'data.json');
    partSendForm.append("searchData", searchDataBlob, 'data.json');

    if (targetInputClass[0] && targetInputClass[0].files[0]) {
        partSendForm.append("imgData", targetInputClass[0].files[0]);
    } else {
    }

    return partSendForm;
}



