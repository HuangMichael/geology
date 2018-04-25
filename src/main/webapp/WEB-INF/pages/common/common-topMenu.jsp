<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/21 0021
  Time: 14:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="mainmenu">
    <div class="container-fluid">
        <ul class="nav" id="topMenu">
            <template v-for="menu in menus">
                <%--导航栏类似左右边框--%>
                <%--<li class="divider-vertical"></li>--%>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" v-bind:id="menu.id">
                        <%--导航栏小图标--%>
                        <%--<i v-bind:class="menu.class"></i>--%>
                        {{menu.menuName}}
                    </a>
                    <ul class="dropdown-menu">
                        <template v-for="m in menu.menus">
                            <li class="dropdown-submenu">
                                <a tabindex="-1" v-bind:id="m.id" v-bind:href="m.event">{{m.menuName}}</a>
                                <template v-if="m.menus">
                                    <ul class="dropdown-menu">
                                        <template v-for="subMenu in m.menus">
                                            <li>
                                                <a tabindex="-1" v-bind:id="subMenu.id" v-bind:href="subMenu.event">{{subMenu.menuName}}</a>
                                            </li>
                                        </template>
                                    </ul>
                                </template>
                            </li>
                        </template>
                    </ul>
                </li>
            </template>
        </ul>
    </div>
</div>
