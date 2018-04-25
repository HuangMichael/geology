var areaVue;

appName ="开发成果";
$(function () {

    initMenus("topMenu");

    getDataByLevel();

    //页面整体楼梯滑动效果
    $("#devUsingTree a").click(function() {
        $(this).addClass('active').siblings().removeClass('active');
        var tTop = $("#kfcgContainer div").eq(0).offset().top;
        var oTop = $("#kfcgContainer div").eq($(this).index()).offset().top;
        var sTop = oTop - tTop;

        $("#kfcgContainer").animate({
            "scrollTop":sTop
        }, 500);
    });
});

function getDataByLevel() {
//首先通过级别查询第一级的菜单
    var url = "devUsing/findByLevel/0";
    $.getJSON(url, function (data) {
        for (var x = 0; x < data.length; x++) {
            if (data[x]) {
                // var html = '<h3>' + data[x]["title"] + '</h3>';
                var html = data[x]["title"] ;
                var id = data[x]["id"];
                var subList = getChildByParentId(id);
                for (var i = 0; i < subList.length; i++) {
                    var cid = subList[i]["id"];
                    html += "<a id='"+ cid +"'>" + subList[i]["title"] + "</a>";
                    getChildId(cid);
                }
                $("#devUsingTree").append(html);//将子节点插入到dom中，左侧tree
            }
        }
    });
}

/**
 *
 * @param id
 * @returns {Array}
 */
//根据第一级的菜单查询子节点
function getChildByParentId(id) {
    var url = "devUsing/findByParentId/" + id;
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
function getChildId(cid) {
    var url = "devUsing/findById/" + cid;
    $.ajaxSettings.async = false;
    $.getJSON(url, function (childData) {
        var html = "<div>" +
            "<img src='"+ childData['fileRelativeUrl'] +"' />" +
            "<span class='desc'><h5>"+ childData['subTitle'] +"</h5>"+ childData['devUsingDesc'] +"</span>" +
            "</div>";
        $("#kfcgContainer").append(html);//将子节点插入到dom中，右侧div
    })

}