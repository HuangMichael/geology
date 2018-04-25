/**
 * Created by Administrator on 2017/11/8.
 */
dataTableName = "#dataTableList";
docName = "系统管理-系统监控管理-应用查询日志";
mainObject = "appQueryLog";
appName = "应用查询日志";
$(function () {
    initMenus("topMenu");
    highLight();

    var grid = $(dataTableName).bootgrid({
        selection: true,
        multiSelect: true,
        ajax: true,
        post: function () {
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed",
                searchPhrase: getSearchParams()
            };
        },
        url: "/appQueryLog/data",
        converters: {//转换器，转换器由两种方法组成:将字符串转换为所需类型，或者将一个类型转换为一个具象性的字符串。
            datetime: {
                to: function (value) {
                    return transformDate(value);
                }
            }
        }
    })

});
