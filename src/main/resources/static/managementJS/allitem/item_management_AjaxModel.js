import {firstLodingAjax} from "../firstLodingAjax.js";
import {handleSearchButtonClick} from "../commonAction.js";

const $allItemAjax = $('#allItemAjax');

firstLodingAjax(sendAjax);
handleSearchButtonClick(sendAjax);

export function sendAjax(page, keyword, option) {
    if (page === null) {page = 0} if (option === null) {option = ""}
    $.ajax({
        type: 'get'
        ,url: "/management/allitem-Ajax"
        ,data: {page: page, option: option, keyword: keyword}
        ,success: function (html) {
            $allItemAjax.html(html);
        }
    });
}