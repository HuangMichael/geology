//查询当前用户具有的模块应用权限
function getNavMenus(userId) {
    var url = "/menu/findAuthMenuByUserId/" + userId;
    var navMenus = [];
    $.ajaxSettings.async = false;
    $.get(url, function (data) {
        navMenus = data[0]["menus"];
    });
    return navMenus;
}

//根据id获取菜单
function getMenuById(menus, id) {

    for (var m in menus) {
        if (menus[m]["id"] == id) {
            return menus[m];
        }
    }
}


/**
 * 初始化菜单
 *
 * type top 顶部导航菜单 block 方块菜单
 * @param menuId
 */
function initMenus(menuId) {
    var user = getLoginUser();
    if ($(menuId)) {
        var menus = getNavMenus(user["id"]);
        var topMenuVue = new Vue({
            el: "#" + menuId,
            data: {
                menus: menus
            }
        });
    }
    else {
        console.log("#menuId不存在----------------");
    }

    //调用导航菜单高亮函数
    highLight();

    console.log("initMenus -----------" + user.personName);
    $(".collapsibleContent #username").html(user.personName);
}


//使一二级菜单高亮显示
function highLight() {
    //设置变量urlstr为当前地址
    var urlstr = location.href;
    var urlstatus = false;
    //循环遍历HTML DOM里所有的a标签
    $(".dropdown-menu li  a").each(function () {
        //如果当前url与a标签href属性相等并且当前href属性不为空
        if ((urlstr + '/').indexOf($(this).attr('href')) > -1 && $(this).attr('href') != '') {
            //为当前a标签与a标签的一二级父标签添加cur样式，并获取一二级标签内容添加到页面标题中，设置urlstatus变量为true
            $(this).addClass('current');
            var firstTitle =$(this).parent().parent().prev('a').parent().parent().prev('a');
            var secondTitle = $(this).parent().parent().prev('a');
            firstTitle.addClass('current');
            secondTitle.addClass('current');

            var htmlTitle = firstTitle.html() + "-" + secondTitle.html() + "-" + $(this).html();
            if(firstTitle.html() == undefined){
                 htmlTitle = secondTitle.html() + "-" + $(this).html();
            }
            $(".tableHeadline h2").html(htmlTitle);
            urlstatus = true;
        } else {
            //否则为当前a标签移除cur样式
            $(this).removeClass('current');
        }
    });
}


/**
 *
 * @returns {*} 获取当前登录的用户
 */
function getLoginUser() {
    var userName = localStorage.getItem("userName");
    $.ajaxSettings.async = false;
    var user = null;
    var param = {userName: userName};
    $.post("/user/findUserByUserName", param, function (data) {
        user = data;
    });
    return user;
}

/**
 *  根据appName查询出当前用户在该应用中的权限
 * @param appName
 */
function requestAppMenus(appName, menuPosition) {
    var menus = [];
    $.ajaxSettings.async = false;
    var url = "menu/requestAppMenus";
    var params = {
        appName: appName,
        menuPosition: menuPosition
    }
    $.post(url, params, function (data) {
        menus = data;
    });
    return menus;
}










