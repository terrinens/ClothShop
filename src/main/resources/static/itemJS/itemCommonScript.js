let pageCount = 0;

const $moreButtonBox = $('#moreButtonBox');
const $moreButton = $('#moreButton');
const $hiddenKindValueBox = $('#hiddenKindValueBox')
console.log($hiddenKindValueBox.attr('id'));

$moreButton.on('click', function () {
    const kindvalues = $hiddenKindValueBox.children('input');
    console.log(kindvalues.length);
    let sendData = {};
    let kindForm = new FormData();

    for (let i = 0; i < kindvalues.length; i++) {
        let targetKindValue = kindvalues[i];
        console.log("추가중...");
        /*kindForm.append("kind"+i, $(targetKindValue).data('th'));*/
        sendData['kind' + i] = $(targetKindValue).data('th');
        console.log(sendData['kind' + i] = $(targetKindValue).data('th'));
        console.log("추가됨...");
    }

    pageCount++;
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
        }
    });
}


