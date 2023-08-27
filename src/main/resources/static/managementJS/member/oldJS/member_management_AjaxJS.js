/*   @GetMapping("/member-Ajax") @ResponseBody
    public ModelAndView managementMemberAjax(
            @RequestParam(value = "page", defaultValue = "0") String page,
            @RequestParam(value = "option", defaultValue = "") String option,
            @RequestParam(value = "keyword", defaultValue = "") String keyword) {

        Object[] requestParam = new Object[]{page, option, keyword};
        Page<Member> paging = mService.managementGetAutoPagingAjax(requestParam);

        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        ModelAndView modelAndView = new ModelAndView(jsonView);
        modelAndView.addObject("memberPaging", paging);
        modelAndView.addObject("page", page);
        modelAndView.addObject("totalPage", paging.getTotalPages());

        paging.hasNext();

        return modelAndView;
    }*/


/*
const buttonSearch = $('#button_search');
const searchKeyword = $('#searchKeyword');
const searchOption = $('#searchOption');

function Ajax(page, keyword, option) {
    $.ajax({
        type: 'GET',
        url: "/management/member-Ajax",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        dataType: "json",
        data: JSON.stringify(params),
        data: {page: page, keyword: keyword, option: option},
        contentType: "application/json",
        success: function (data) {
            console.log('Ajax success');
            const memberPagingHtml = $('.memberPaging');
            const member = data.memberPaging.content;
            const memberTbody = $('#memberTbody');
            const pagingNumberBox = $('#pagingNumberBox');

            memberPagingHtml.empty();
            memberTbody.empty();
            pagingNumberBox.empty();
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

           const ul = '<ul class="pagination justify-content-center">';
            pagingNumberBox.append(ul);
            $.each(totalPage, function (i, totalPage) {

                if (!totalPage.)
                pagingNumberBox.append('<li class="page-item">' + i);
                pagingNumberBox.append('</li>');
                pagingNumberBox.append('</ul>');
            })
        },
        error: function () {
            console.log('Ajax fail');
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
});*/