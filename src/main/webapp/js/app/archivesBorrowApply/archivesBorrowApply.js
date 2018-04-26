/**
 * Created by huangbin on 2017/5/22 0022.
 */

dataTableName = "#dataTableList";
deleteObject = "area";
appName = "围垦区块";
formId = "#areaForm";
selectArray = ["#id", "#city", "#district", "#functionType", "#areaType"];
topValue = "18%";
authStatus = "";


docName = "档案借阅申请信息";
mainObject = "archivesBorrowApply";
formName = "#form";

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
                return "<button class='btn-sm command-edit' title='借阅' onclick='borrow(" + row.id + ")'><i class='icon-edit'></i></button>"
            }
        },
        converters: {
            showStatus: {
                to: showStatus
            }
        }
    });


    $("#saveBtn").trigger("click");


    var validateConfigs = {};
    validateForm.call(validateConfigs);
});


/**
 *
 * @param id
 */
function borrow(id) {
    $("#apply_modal").modal("show");

}


