<%@ page contentType="text/html;charset=UTF-8" %>
<div id="menuContainer" style="display: inline"></div>
<script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
    $(function () {
        var menus = requestAppMenus(appName, "1");
        var html = "";
        for (var x = 0;x < menus.length; x++) {
            console.log(menus[x]);
            html += '<button type="button" class="btn-button"  title = ' + menus[x]["menuName"] + ' onclick="' + menus[x].event + '"><i class="' + menus[x].icon + '"></i></button>';
        }
        $("#menuContainer").html(html);
    })
</script>