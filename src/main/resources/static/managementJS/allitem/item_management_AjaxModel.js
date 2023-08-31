import {firstLodingAjax} from "../firstLodingAjax.js";
import {commonAction} from "../commonAction.js";

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
        , async: false
        , success: function (html) {
            $allItemAjax.html(html);
        }
    })
}