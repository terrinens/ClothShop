
function firstLodingAjax(sendAjax) {
    lodingAjax(sendAjax);
}

function lodingAjax(sendAjax) {
    return $(function () {
        sendAjax(0, "", "all");
    })
}

export {firstLodingAjax};
