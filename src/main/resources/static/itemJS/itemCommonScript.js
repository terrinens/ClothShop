let pageCount = 0;

const $moreButton = $('#moreButton');
const $hiddenKindValueBox = $('#hiddenKindValueBox')
const $beforeBox = $('#beforeBox');
const $totalPageInput = $('#totalPageInput');

$moreButton.on('click', function () {
    const kindvalues = $hiddenKindValueBox.children('input');
    let sendData = {};
    let kindForm = new FormData();

    for (let i = 0; i < kindvalues.length; i++) {
        let targetKindValue = kindvalues[i];
        sendData['kind' + i] = $(targetKindValue).data('th');
    }

    pageCount++;
    sendData ['pageCount'] = pageCount;
    moreItemCallAjax(sendData);
    if (pageCount) {
    }
});

function moreItemCallAjax(sendData) {
    $.ajax({
        type: 'get'
        , url: "/products/cloth/moreInfo"
        , data: sendData
        , contentType: "application/json"
        , success: function (result) {
            $beforeBox.before(result);
        }
    });
}


