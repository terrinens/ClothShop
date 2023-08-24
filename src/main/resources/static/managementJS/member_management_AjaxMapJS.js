// noinspection JSFileReferences,JSUnresolvedReference
// noinspection JSUnresolvedReference

const $buttonSearch = $('#button_search');
const $searchKeyword = $('#searchKeyword');
const $searchOption = $('#searchOption');
const $pageLink = $('.page-link');
const $htmlMemberPagingLocation = $('.memberPaging');
const $htmlPagingNumberBox = $('#pagingNumberBox');
const $htmlMemberTbody = $('#memberTbody');
const $mainNav = $('#mainNav');
const $bootstrapbundleJS = $('#bootstrapbundleJS');
let lodingCheck = false;

$pageLink.on('click', function () {
    const page = $(this).data('page');
    const keyword = $searchKeyword.val();
    const option = $searchOption.val();
    $mainNav.append($.getScript("/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"));
    $pageLink.off('click');
    this.remove();
    Ajax(page, keyword, option);
});

function Ajax(page, keyword, option) {
    let modalTarget = [];
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
            lodingCheck = true;
            const memberPaging = data.memberPaging;
            const member = memberPaging.content;
            const hasPrevious = data.hasPrevious;
            const hasNext = data.hasNext;

            $htmlMemberPagingLocation.add($htmlMemberTbody).add($htmlPagingNumberBox).empty();
            $.each(member, function (index, member) {
                const viewIndate = conversionDate(member.indate);
                modalTarget.push("memberDetail_" + member.id);
                let row = '<tr class="memberPaging" data-bs-toggle="modal" ' +
                    'data-bs-target="' + "#" + modalTarget.at(index) + '">' +
                    '<th>' + member.id + '</th>' +
                    '<td>' + member.name + '</td>' +
                    '<td>' + member.tel + '</td>' +
                    '<td>' + member.role + '</td>' +
                    '<td>' + viewIndate + '</td>' +
                    '</tr>';
                $htmlMemberTbody.append(row);
            });

            if (memberPaging.empty === false) {
                $htmlPagingNumberBox.append('<ul class="pagination justify-content-center">');

                if (memberPaging.totalPages > 2) {
                    const previousPage = memberPaging.number - 1;
                    if (hasPrevious === false) {
                        $htmlPagingNumberBox.children().append(
                            '<li class="disabled page-item">' +
                            '<a class="page-link" href="javascript:void(0)">' + "&lsaquo;" + '</a>' +
                            '</li>'
                        );
                    } else if (hasPrevious === true) {
                        $htmlPagingNumberBox.children().append(
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
                        $htmlPagingNumberBox.children().append(
                            '<li class="active page-item">' +
                            '<a class="page-link" href="javascript:void(0)" data-page="' + i + '">' + viewNumber + '</a>'
                            + '</li>'
                        );
                    } else {
                        $htmlPagingNumberBox.children().append(
                            '<li class="page-item">' +
                            '<a class="page-link" href="javascript:void(0)" data-page="' + i + '">' + viewNumber + '</a>'
                            + '</li>'
                        );
                    }
                }

                if (memberPaging.totalPages > 2) {
                    if (hasNext === false) {
                        $htmlPagingNumberBox.children().append(
                            '<li class="disabled page-item">' +
                            '<a class="page-link" href="javascript:void(0)">' + "&rsaquo;" + '</a>' +
                            '</li>'
                        );
                    } else if (hasNext === true) {
                        const nextpage = memberPaging.number + 1;
                        $htmlPagingNumberBox.children().append(
                            '<li class="page-item">' +
                            '<a class="page-link" href="javascript:void(0)" data-page="' + nextpage + '">'
                            + "&rsaquo;" +
                            '</a>' +
                            '</li>'
                        );
                    }
                }
                $htmlPagingNumberBox.append('</ul>');
            }

            const $modifyModalBox = $('#modifyModalBox');
            lodingCheck = false;
            for (let i = 0; i < modalTarget.length; i++) {
                const memberData = member[i];
                modalReLoad($modifyModalBox, modalTarget, memberData, i);
                generateRoleOption(memberData, i);
                const childTarget2 = $('.childTarget2').eq(i);
                childTarget2.append(
                    '<br><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>'
                    + '<button type="submit" class="btn btn-primary" id="button_modify">수정하기</button>'
                    + '<button type="reset" class="btn btn-warning" id="button_reset">값 되돌리기</button>'
                    + '<button th:data-uri="@{|/management/member/delete/${member.getId()}|}" type="button" class="call_delete_modal btn btn-outline-danger" id="call_delete_modal" data-bs-toggle="modal" data-bs-target="#delete_modal">해당 유저 삭제</button>'
                    + '</form>'
                    + '</div>'
                    + '</div>'
                    + '</div>'
                    + '</div>'
                );
            }
            lodingCheck = true;
        },
        error: function () {
            console.log('Ajax request failed');
        }
    });
}

function modalReLoad(modifyModalBox, modalTarget, memberData, index) {
    const i = index;
    modifyModalBox.append(
        '<div class="modal fade" id="' + modalTarget[i] + '" tabindex="-1" aria-hidden="true">'
        + '<div class="modal-dialog">'
        + '<div class="modal-content">'
        + '<div class="modal-header">'
        + '<h1 class="modal-title fs-5">' + memberData.id + "님의 정보 수정" + '</h1>'
        + '<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>'
        + '</div>'
        + '<div class="modal-body">'
        + '<form action="/management/member/modify" !object="MMForm" id="modifyForm" method="post">'
        + '<div th:replace="~{layout/formErrors::formErrorsFragment}"></div>'
        + '<div class="mb-3">'
        + '<fieldset disabled="">'
        + '<label for="recipient-id_view" class="col-form-label">ID(수정불가)</label>'
        + '<input th:name="id_view" type="text" class="form-control" id="recipient-id_view" value="' + memberData.id + '">'
        + '</fieldset>'
        + '<input th:field="*{id}" type="hidden" th:value="${member.getId()}">'
        + '</div>'
        + '<div class="mb-3">'
        + '<label for="recipient-pwd" class="col-form-label">비밀번호 수정</label>'
        + '<label for="recipient-pwdModify"></label>'
        + '<input th:name="pwdModify" type="text" class="form-control" id="recipient-pwdModify" placeholder="공백으로 설정시 기존 비밀번호로 유지됩니다.">'
        + '<input type="hidden" th:field="*{pwd}" id="recipient-pwd" !value="' + memberData.password + '">'
        + '</div>'
        + '<div class="mb-3">'
        + '<label for="recipient-name" class="col-form-label">이름 수정</label>'
        + '<input th:field="*{name}" type="text" class="form-control" id="recipient-name" value="' + memberData.name + '">'
        + '</div>'
        + '<div class="mb-3">'
        + '<label for="recipient-tel" class="col-form-label">연락처 수정</label>'
        + '<input th:name="tel" type="text" class="form-control" id="recipient-tel" value="' + memberData.tel + '">'
        + '</div>'
        + '<div class="mb-3 childTarget">'
        + '<label for="recipient-address" class="col-form-label">이메일 수정</label>'
        + '<input th:field="*{address}" type="text" class="form-control" id="recipient-address" value="' + memberData.address + '">'
        + '</div>'
    );
}

function generateRoleOption(memberData, index) {
    const childTarget = $('.childTarget').eq(index);

    if (memberData.role === "Admin") {
        console.log("자식 타겟 추가");
        return childTarget.append(
            '<div class="mb-3 childTarget2">'
            + '<label for="recipient-role" class="col-form-label">권한 수정</label>'
            + '<select class="role form-control" id="recipient-role" type="text">'
            + '<option name="admin" value="Admin" selected>Admin</option>'
            + '<option name="user" value="User">User</option>'
            + '</select>'
            + '</div>');
    } else {
        console.log("자식 타겟 추가");
        return childTarget.append(
            '<div class="mb-3 childTarget2">'
            + '<label for="recipient-role" class="col-form-label">권한 수정</label>'
            + '<select class="role form-control" id="recipient-role" type="text">'
            + '<option name="admin" value="Admin">Admin</option>'
            + '<option name="user" value="User" selected>User</option>'
            + '</select>'
            + '</div>');
    }
}

$searchKeyword.add($searchOption).on('keydown', function (event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        $buttonSearch.click();
    }
});

$buttonSearch.on('click', function (event) {
    event.preventDefault();

    const page = 0;
    const keyword = $searchKeyword.val();
    const option = $searchOption.val();

    Ajax(page, keyword, option);
});

$htmlPagingNumberBox.on('click', '.page-link', function () {
    const page = $(this).data('page');
    const keyword = $searchKeyword.val();
    const option = $searchOption.val();

    if (lodingCheck === true) {
        $('.modal.fade').remove();
    }
    Ajax(page, keyword, option);
});


