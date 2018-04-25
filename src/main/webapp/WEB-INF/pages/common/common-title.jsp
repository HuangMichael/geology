<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/21 0021
  Time: 14:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <img src="/img/logo.jpg" alt="">
        <div class="container-fluid">
            <div class="nav pull-left">
                <h2>
                    <a class="brand" href="#">河北水勘院地质档案管理系统</a>
                </h2>
                <h5>HEBEI SKY GEOLOGY ARCHIVES MANAGEMENT SYSTEM</h5>
            </div>
            <div class="nav pull-right">
                <form class="navbar-form">
                    <div class="input-append">
                        <%--<div class="collapsibleContent">--%>
                        <%--<a onclick="refreshPage()" class="sidebar">--%>
                        <%--<i class="icon-home" title="返回主界面"></i>--%>
                        <%--</a>--%>
                        <%--</div>--%>
                        <div class="collapsibleContent">
                            <a href="#profileContent" class="sidebar">
                                <i class="icon-user"></i>
                                <span id="username"></span>
                            </a>
                        </div>

                        <div class="collapsibleContent">
                            <a id="password_change" class="sidebar">
                                <i class="icon-lock"></i>
                                <span class="sidebar">修改密码</span>
                                <%--<a id="password_change" class="sidebar" title="修改密码">修改密码</a>--%>
                            </a>
                        </div>

                        <div class="collapsibleContent">
                            <a class="sidebar" onclick="off()">
                                <i class="icon-off"></i>
                                <span class="sidebar">退出系统</span>
                            </a>
                        </div>
                    </div>
                </form>

            </div>
            <!--/.nav-collapse -->
        </div>
    </div>

</div>

<div class="modal fade password_modal" id="password_modal">
    <div class="modal-body">修改密码</div>
    <form class="form-horizontal" id="passwordForm">
        <div class="row-fluid">
            <div class="form-group ">
                <label for="oldPassword" class="col-md-2 control-label">原密码：</label>
                <div class="col-md-3">
                    <input type="hidden" id="id" name="id" v-model="user.id"/>
                    <input type="password" class="form-control" id="oldPassword" name="oldPassword"
                           placeholder="请输入原密码">
                    <div style="display: none;color: green;font-size: 18px" class="icon-ok-circle"></div>
                    <div id="tip1" class="tip"></div>
                </div>
            </div>

            <div class="form-group ">
                <label for="newPassword" class="col-md-2 control-label">更改密码：</label>
                <div class="col-md-3">
                    <input type="password" class="form-control" id="newPassword" name="newPassword"
                           placeholder="请输入新密码">
                    <div style="display: none;color: green;font-size: 18px" class="icon-ok-circle"></div>
                    <div id="tip2" class="tip"></div>
                </div>
            </div>

            <div class="form-group ">
                <label for="rePassword" class="col-md-2 control-label"> 确认密码：</label>
                <div class="col-md-3">
                    <input type="password" class="form-control" id="rePassword" name="rePassword"
                           placeholder="请再次输入新密码">
                    <div style="display: none;color: green;font-size: 18px" class="icon-ok-circle"></div>
                    <div id="tip3" class="tip"></div>
                </div>
            </div>

            <div class="form-group ">
                <label for="rePassword" class="col-md-2 control-label"></label>
                <div class="col-md-3">
                    <div id="tip4" class="tip"></div>
                </div>
            </div>
        </div>
    </form>
    <div class="modal-footer">
        <a class="btn btn-danger" data-action="1" id="savePassword">确认修改</a>
        <a class="btn cancel" href="#" data-dismiss="modal">取消</a>
    </div>
</div>