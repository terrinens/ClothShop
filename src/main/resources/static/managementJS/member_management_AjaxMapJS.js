// noinspection JSUnresolvedReference
// noinspection JSUnresolvedReference

const buttonSearch = $('#button_search');
const searchKeyword = $('#searchKeyword');
const searchOption = $('#searchOption');
const pageLink = $('.page-link');
const htmlMemberPagingLocation = $('.memberPaging');
const htmlPagingNumberBox = $('#pagingNumberBox');
const htmlMemberTbody = $('#memberTbody');

pageLink.on('click', function () {
    const page = $(this).data('page');
    const keyword = searchKeyword.val();
    const option = searchOption.val();
    pageLink.off('click');
    Ajax(page, keyword, option);
});

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
                    const previousPage = memberPaging.number - 1;
                    if (hasPrevious === false) {
                        htmlPagingNumberBox.children().append(
                            '<li class="disabled page-item">' +
                            '<a class="page-link" href="javascript:void(0)">' + "&lsaquo;" + '</a>' +
                            '</li>'
                        );
                    } else if (hasPrevious === true) {
                        htmlPagingNumberBox.children().append(
                            '<li class="page-item">' +
                            '<a class="page-link" href="javascript:void(0)" data-page="' + previousPage + '">'
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
                    if (i === page) {
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
                    } else if (hasNext === true) {
                        const nextpage = memberPaging.number + 1;
                        htmlPagingNumberBox.children().append(
                            '<li class="page-item">' +
                            '<a class="page-link" href="javascript:void(0)" data-page="' + nextpage + '">'
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

searchKeyword.add(searchOption).on('keydown', function (event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        buttonSearch.click();
    }
});

buttonSearch.on('click', function (event) {
    event.preventDefault();

    const page = 0;
    const keyword = searchKeyword.val();
    const option = searchOption.val();

    Ajax(page, keyword, option);
});

htmlPagingNumberBox.on('click', '.page-link', function () {
    const page = $(this).data('page');
    const keyword = searchKeyword.val();
    const option = searchOption.val();
    Ajax(page, keyword, option);
    pageLink.empty();
});

