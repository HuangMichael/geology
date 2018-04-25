$(function () {

    initMenus("topMenu");
    // Append a close trigger for each block
    getData();

});




function getData() {
    //根据第一级的菜单查询子节点
//将子节点插入到dom中
//首先通过级别查询第一级的菜单
    var id = "";
    var url = "history/findByLevel/0";
    $.getJSON(url, function (data) {
        for (var x = 0; x < data.length; x++) {
            if (data[x]) {
                var html = '<h3>' + data[x]["title"] + '</h3><ul id="catalog'+x+'" >';
                var id = data[x]["id"];
                var subList = getChildByParentId(id);
                for (var i = 0; i < subList.length; i++) {
                    var cid = subList[i]["id"];
                    html += "<li id='"+ cid +"' onclick='loadPageContentById(" + cid + ")'><i></i>" + subList[i]["title"] + "</li>";
                }
                html += '</ul>';
                $("#treeContainer0").append(html);
            }
        }
    });

    var id = $("#catalog0").children('li').eq(0).attr('id');
    loadPageContentById(id);
}


/**
 *
 * @param id
 * @returns {Array}
 */
function getChildByParentId(id) {
    var url = "history/findByParentId/" + id;
    var subList = [];
    $.ajaxSettings.async = false;
    $.getJSON(url, function (childData) {
        subList = childData;
    });
    return subList;
}


/**
 *
 * @param id
 * @returns {Array}
 */
function getChildId(id) {
    var url = "history/findById/" + id;
    var child = null;
    $.ajaxSettings.async = false;
    $.getJSON(url, function (childData) {
        child = childData;
    });
    return child;
}


function loadPageContentById(child) {
    svgLoading();
    var Lid = "#"+child;
    console.log(Lid);
    $(".timeline-tree li").find(Lid).addClass('active').siblings().removeClass('active');
    var child = getChildId(child);
    if (child["mediaType"]["id"] == 2) {
        $("#media").html("<img class='img-responsive' src='" + child["fileRelativeUrl"] + "'>");
    }
    if (child["mediaType"]["id"] == 3) {
        $("#media").html("<video width='100%'; height='418px' controls='controls' src='" + child["fileRelativeUrl"] + "'></video>");
    }
    $("#title").html("<h2 >" + child['title'] + "</h2>");
    $("#historyDescDiv").html("<p >" + child['historyDesc'] + "</p>");
}

function svgLoading(){
    var pageWrap = $('.timeline-row'),

        pages =  $( '.timeline-content'),

        currentPage = 0,

        loader = new SVGLoader( document.getElementById( 'loader' ), { speedIn : 500, easingIn : mina.easeinout } );

    loader.show();

    setTimeout( function() {
        loader.hide();
        classie.removeClass( pages[ currentPage ], 'show' );

        // update..

        currentPage = currentPage ? 0 : 1;
        classie.addClass( pages[ currentPage ], 'show' );
    }, 1000 );
};