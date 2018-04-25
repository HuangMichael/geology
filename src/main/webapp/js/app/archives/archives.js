/**
 * Created by huangbin on 2017/5/22 0022.
 */

dataTableName = "#dataTableList";
docName = "围垦区块信息";
mainObject = "vArea";
deleteObject = "area";
formName = "";
appName = "围垦区块";
formId = "#areaForm";
selectArray = ["#id", "#city", "#district", "#functionType", "#areaType"];
topValue = "18%";
authStatus = "";

var currentId = null;
var url = "";
var obj = {};

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
        url: "archives/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn-sm command-edit' title='编辑' onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>" +
                    "<button class='btn-sm command-delete' title='删除' onclick='del(" + row.id + ")'><i class='icon-trash'></i></button>";
            }
        },
        converters: {
            showStatus: {
                to: showStatus
            }
        }
    })
});


