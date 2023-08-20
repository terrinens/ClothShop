$(function() {
    const searchKeyword = $('#searchKeyword');
    const searchOption = $('#searchOption');
    const searchForm = $('#searchForm');
    const buttonSearch = $('#button_search');

    buttonSearch.on('click', function() {
        $('#keyword').val(searchKeyword.val());
        $('#option').val(searchOption.val());
        $('#page').val(0);
        searchForm.submit();
    });

    searchKeyword.add(searchOption).on('keydown', function(event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            buttonSearch.click();
        }
    });
});