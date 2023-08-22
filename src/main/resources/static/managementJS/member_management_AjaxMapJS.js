// noinspection DuplicatedCode

const buttonSearch = $('#button_search');
const searchKeyword = $('#searchKeyword');
const searchOption = $('#searchOption');

function Ajax(page, keyword, option) {
    $.ajax({
        type: 'GET',
        url: "/management/member-Ajax",
        /*beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },*/
        data: {page: page, option: option, keyword: keyword},
        dataType: "json",
        success: function (data) {
            console.log('Ajax success');
            const memberPagingHtml = $('.memberPaging');
            const member = data.memberPaging.content;
            const memberTbody = $('#memberTbody');
            const pagingNumberBox = $('#pagingNumberBox');

            memberPagingHtml.add(memberTbody).add(pagingNumberBox).empty();
            $.each(member, function (index, member) {
                const id = member.id;
                const name = member.name;
                const tel = member.tel;
                const role = member.role;
                const indate = member.indate;
                const viewIndate = conversionDate(indate);
                let row = '<tr>' +
                    '<td>' + id + '</td>' +
                    '<td>' + name + '</td>' +
                    '<td>' + tel + '</td>' +
                    '<td>' + role + '</td>' +
                    '<td>' + viewIndate + '</td>' +
                    '</tr>';
                memberTbody.append(row);
            });
        },
        error: function () {
            console.log('Ajax request failed');
        }
    });
}

buttonSearch.on('click', function (event) {
    event.preventDefault();

    const page = 0;
    const keyword = searchKeyword.val();
    const option = searchOption.val();

    Ajax(page, keyword, option);
});

searchKeyword.add(searchOption).on('keydown', function (event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        buttonSearch.click();
    }
});
