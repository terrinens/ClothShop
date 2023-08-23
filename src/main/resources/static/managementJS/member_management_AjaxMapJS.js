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
            const htmlMemberPagingLocation = $('.memberPaging');
            const htmlPagingNumberBox = $('#pagingNumberBox');
            const htmlMemberTbody = $('#memberTbody');
            const memberPaging = data.memberPaging;
            const member = memberPaging.content;
            const hasPrevious = data.hasPrevious;
            const hasNext = data.hasNext;

            htmlMemberPagingLocation.add(htmlMemberTbody).add(htmlPagingNumberBox).empty();
            $.each(member, function (index, member) {
                const viewIndate = conversionDate(member.indate);
                let row = '<tr>' +
                    '<th>' + member.id + '</th>' +
                    '<td>' + member.name + '</td>' +
                    '<td>' + member.tel + '</td>' +
                    '<td>' + member.role + '</td>' +
                    '<td>' + viewIndate + '</td>' +
                    '</tr>';
                htmlMemberTbody.append(row);
            });

            if (memberPaging.empty === false) {
                htmlPagingNumberBox.append('<ul class="pagination justify-content-center">');

                if (memberPaging.totalPages > 2) {
                    if (hasPrevious === false) {
                        console.log("false 도달함");
                        htmlPagingNumberBox.children().append(
                            '<li class="disabled page-item">' +
                            '<a class="page-link" href="javascript:void(0)">' + "&lsaquo;" + '</a>' +
                            '</li>'
                        );
                    } else if (hasPrevious === true) {
                        htmlPagingNumberBox.children().append(
                            '<li class="page-item">' +
                            '<a class="page-link" href="javascript:void(0)" data-page="' + (memberPaging.number -= 1) + '">'
                            + "&lsaquo;" +
                            '</a>' +
                            '</li>'
                        );
                    }
                }

                const previouse = Math.max(0, memberPaging.number - 10);
                const Next = Math.min(memberPaging.number + 10, memberPaging.totalPages - 1);
                for (let i = previouse; i <= Next; i++) {
                    let viewNumber = i + 1;
                    if (i === memberPaging.number) {
                        htmlPagingNumberBox.children().append(
                            '<li class="active page-item">' +
                            '<a class="page-link" href="javascript:void(0)" data-page="' + i + '">' + viewNumber + '</a>'
                            + '</li>'
                        );
                    } else {
                        htmlPagingNumberBox.children().append(
                            '<li class="page-item">' +
                            '<a class="page-link" href="javascript:void(0)" data-page="' + i + '">' + viewNumber + '</a>'
                            + '</li>'
                        );
                    }
                }

                if (memberPaging.totalPages > 2) {
                    if (hasNext === false) {
                        htmlPagingNumberBox.children().append(
                            '<li class="disabled page-item">' +
                            '<a class="page-link" href="javascript:void(0)">' + "&rsaquo;" + '</a>' +
                            '</li>'
                        );
                    } else if (hasNext === true && memberPaging.empty === false) {
                        htmlPagingNumberBox.children().append(
                            '<li class="page-item">' +
                            '<a class="page-link" href="javascript:void(0)" data-page="' + (memberPaging.number += 1) + '">'
                            + "&rsaquo;" +
                            '</a>' +
                            '</li>'
                        );
                    }
                }

                htmlPagingNumberBox.append('</ul>');
            }
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
