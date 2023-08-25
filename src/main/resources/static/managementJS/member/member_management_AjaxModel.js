import {firstLodingAjax} from "../firstLodingAjax.js";
import {commonAction} from "../commonAction.js";

const $memberAjax = $('#memberAjax');

firstLodingAjax(sendAjax);
commonAction(sendAjax);

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