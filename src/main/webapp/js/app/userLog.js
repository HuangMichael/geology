/**
 * Created by huangbin on 2017-08-17 13:58:17
 */
dataTableName = "#dataTableList";
docName = "用户日志信息";
mainObject = "userLog";
appName = "用户登录日志";
$(function () {
    initMenus("topMenu");
    var grid = $(dataTableName).bootgrid({
        selection: true,
        multiSelect: true,
        ajax: true,
        post: function () {
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed"
            };
        },
        url: "/userLog/data",
        converters: {//转换器，转换器由两种方法组成:将字符串转换为所需类型，或者将一个类型转换为一个具象性的字符串。
            datetime: {
                to: function (value) {
                    return transformDate(value);
                }
            }
        }
    });
});
