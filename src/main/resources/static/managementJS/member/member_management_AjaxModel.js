import {firstLodingAjax} from "../firstLodingAjax.js";
import {commonAction} from "../commonAction.js";

const $memberAjax = $('#memberAjax');
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
        , url: "/management/member-Ajax"
        , data: {page: page, option: option, keyword: keyword}
        , success: function (html) {
            $memberAjax.html(html);
        }
    });
}

function sendModifyAjax(sendData) {
    $.ajax({
        type: 'post'
        , beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        }
        , url: "/management/member/modify-Ajax"
        , data: JSON.stringify(sendData)
        , contentType: "application/json"
        , async: false
        , success: function (html) {
            $memberAjax.html(html);
        }
    });
}

function sendDELAjax(sendData) {
    $.ajax({
        type: 'delete'
        , beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        }
        , url: "/management/member/delete-Ajax"
        , data: JSON.stringify(sendData)
        , contentType: "application/json"
        , async: false
        , success: function (html) {
            $memberAjax.html(html);
        }
    })
}