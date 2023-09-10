let pageCount = 0;

const $moreButton = $('#moreButton');
const $hiddenKindValueBox = $('#hiddenKindValueBox')
const $beforeBox = $('#beforeBox');
const totalInputData = $('#totalInput').data('total');

$moreButton.on('click', function () {
    const kindvalues = $hiddenKindValueBox.children('input');
    let sendData = {};
    let kindForm = new FormData();

    for (let i = 0; i < kindvalues.length; i++) {
        let targetKindValue = kindvalues[i];
        sendData['kind' + i] = $(targetKindValue).data('th');
    }

    /**상품 페이지 한계까지 도달시 버튼을 비활성화 하기 위한 코드*/
    if (pageCount === totalInputData-1) {
        $moreButton.addClass('disabled');
        $moreButton.text('No More');
    } else if (pageCount < totalInputData) {
        pageCount++
    }
    sendData ['pageCount'] = pageCount;
    moreItemCallAjax(sendData);
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


