// $(function () {






initMenus("topMenu");
initMenus("navMenu");


    // Append a close trigger for each block



    $('.menu .content').append('<span class="close">x</span>');

    // Show window

    function showContent(elem) {

        hideContent();

        elem.find('.content').addClass('expanded');

        //elem.addClass('cover');

    }

    // Reset all

    function hideContent() {

        $('.menu .content').removeClass('expanded');

        $('.menu li').removeClass('cover');

    }

    // When a li is clicked, show its content window and position it above all

    $('.menu li').on("mouseenter", function () {
        showContent($(this));
    }).on("mouseleave", function () {
        hideContent($(this));
    });

    // When tabbing, show its content window using ENTER key

    $('.menu li').keypress(function (e) {

        if (e.keyCode == 13) {
            showContent($(this));
        }

    });

    // When right upper close element is clicked  - reset all

    $('.menu .close').click(function (e) {

        e.stopPropagation();

        hideContent();

    });

    // Also, when ESC key is pressed - reset all

    $(document).keyup(function (e) {

        if (e.keyCode == 27) {
            hideContent();

        }

    });

    //绑定事件

    $(".navSubMenu").on("click", function () {
        var url = $(this).data("url");
        loadPage(url);
    });









// });

function loadPage(url) {
    //$("#contentContainer").load(url);
    window.location.href = url;

}


