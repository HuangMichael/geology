/**
 * Created by Administrator on 2017/5/26.
 */

/**
 * 返回主界面
 */
function refreshPage() {
    console.log("----------------返回主界面------------------");
    window.location.href = "/main/index";
}
var userName = localStorage.getItem("userName");

if (userName == null) {
    $(".sidebar span").html("请登录");
    $(".sidebar span").click(function () {
        window.location.href = "/";
    })
} else {
    var userName_menu = $(".userName_menu");
    $(".sidebar span").click(function () {
        if (userName_menu.css("display") == "none") {
            userName_menu.show();
        } else {
            userName_menu.hide();
        }
    });
}






function off() {
    console.log("----------------返回主界面------------------" + location.pathname);
    var pathname = location.pathname;
    if(pathname == "/main/index"){
        bootbox.confirm({
            message: "确定退出本系统吗?",
            buttons: {
                confirm: {
                    label: '是',
                    className: 'btn-success'
                },
                cancel: {
                    label: '否',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
                if (result) {
                    localStorage.removeItem("userName");
                    $("#username").html("请登录");
                    window.location.href = "/";
                }
            }
        });
    }else {
        refreshPage();
    }
}


//修改密码
$("#password_change").on("click", function () {
    $("#passwordForm")[0].reset();
    $(".tip").empty();
    $("#password_modal").modal("show");
});

$(function () {
    //原密码框失焦时验证原密码是否正确
    $("#oldPassword").blur(function () {
        var userName = localStorage.getItem("userName");
        console.log(userName);
        var param = $("#oldPassword").val();
        if (param != "") {
            $.ajax({
                type: 'POST',
                url: "/user/verifyPassword ",
                data: {userName: userName, password: param},
                success: function (data) {
                    console.log("-----data---------" + data["resultDesc"]);
                    if (data["result"]) {
                        $("#tip1").prev().css("display","inline");
                        $("#tip1").html("");
                        // $("#tip1").html("<font color=\"green\" size=\"2\">验证成功</font>");
                    }
                    else {
                        $("#tip1").html("<font color=\"red\" size=\"2\">验证失败</font>");
                    }
                }
            });

        } else {
            $("#tip1").html("<font color=\"red\" size=\"2\">不能为空</font>");
        }

    });
    //新密码框失焦时验证新密码格式是否正确
    $("#newPassword").blur(function () {
        var newPwd = $("#newPassword").val();
        var num = $("#newPassword").val().length;
        if (newPwd == "") {
            $("#tip2").html("<font color=\"red\" size=\"2\">不能为空</font>");
        } else if (num < 6) {
            $("#tip2").html("<font color=\"red\" size=\"2\">密码太短</font>");
        }
        else if (num > 18) {
            $("#tip2").html("<font color=\"red\" size=\"2\">密码太长</font>");
        }
        else {
            $("#tip2").prev().css("display","inline");
            $("#tip2").html("");
            // $("#tip2").html("<font color=\"green\" size=\"2\">正确</font>");
        }


    });
    //再次输入密码框失焦时验证两次输入的密码是否一致
    $("#rePassword").blur(function () {
        var tmp = $("#newPassword").val();
        var num = $("#rePassword").val().length;
        if (tmp == "") {
            $("#tip3").html("<font color=\"red\" size=\"2\">不能为空</font>");
        } else if ($("#rePassword").val() != tmp) {
            $("#tip3").html("<font color=\"red\" size=\"2\">密码不一致</font>");
        } else if ($("#rePassword").val() == tmp) {
            $("#tip3").prev().css("display","inline");
            $("#tip3").html("");
            // $("#tip3").html("<font color=\"green\" size=\"2\">密码一致</font>");
        } else if (num >= 6 && num <= 18) {
            $("#tip3").html("<font color=\"green\" size=\"2\">长度正确</font>");
        }
        else {
            $("#tip3").html("<font color=\"red\" size=\"2\">格式不正确</font>");
        }
    });
    //保存新密码
    $("#savePassword").click(function () {
        var flag = true;
        var userName = localStorage.getItem("userName");
        var old = $("#oldPassword").val();
        var pass = $("#newPassword").val();
        var pass2 = $("#rePassword").val();
        var num1 = $("#newPassword").val().length;
        var num2 = $("#rePassword").val().length;
        if (num1 != num2 || num1 < 6 || num2 < 6 || num1 > 18 || num2 > 18 || pass != pass2) {
            flag = false;
        }
        else {
            flag = true;
        }
        if (flag) {
            $.ajax({
                type: 'POST',
                url: "/user/modifyPassword ",
                data: {userName: userName, oldPassword: old, newPassword: pass},
                success: function (data) {
                    console.log(data);
                    if (data["result"]) {
                        $("#tip4").html("<font color=\"green\" size=\"3\">密码修改成功，请重新登录!</font>");

                        setTimeout(function () {
                            window.location.href = "/";
                        }, 1000)
                    }
                    else {
                        $("#tip4").html(data["resultDesc"]);
                    }
                }
            });
        } else {

            $("#tip4").show().html("<font color=\"red\" size=\"3\">按提示修改!</font>");
        }
    });
});

