/**
 * Created by Administrator on 2017/5/22 0022.
 */
dataTableName = "#dataTableList";
docName = "系统管理-基础数据管理-历史沿革信息";
mainObject = "history";
deleteObject = "history";
appName = "历史沿革信息";
var historyId;

formId = "#historyForm";
selectArray = ["#id","#historySelect"];


$(function () {
    initMenus("topMenu");

    initAutoComplete("titleSearch", "/history/findAll", "title");

    //编辑或者新增界面的上级历史沿革选择下拉列表
    var historyUrl = "history/findByLevel/0";//返回值是一个列表，查找全国、江苏省的信息
    var histories = getSelectedData(historyUrl, "title");
    console.log("---全国和江苏省---历史沿革-------------" + JSON.stringify(histories));
    initSelectData("#historySelect", {parentsArray: histories});

    var grid = $(dataTableName).bootgrid({
        selection: true,
        multiSelect: true,
        ajax: true,
        post: function () {
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed"
            };
        },
        url: "/history/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn-sm command-edit' title='编辑' onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>" +
                    "<button class='btn-sm command-delete' title='删除' onclick='del(" + row.id + ")'><i class='icon-trash'></i></button>";
            },
            "upload": function (column, row) {
                return "<button class='btn-xm command-upload' title='上传'  onclick='upload(" + row.id + ")'><i class='icon-upload'></i></button>"
            }
        }
    });

    //保存历史沿革记录
    $("#saveBtn").on("click", function () {
        var obj = getFormJsonData("historyForm");
        var object = JSON.parse(obj);
        var historyParent = $("#historySelect").val();
        if( historyParent.trim() == "" ){//如果前台没有选择父亲的时候，前台的父id设置为null，后台去计算其他属性
            object["parentId"] = null;
        }
        // console.log("=====history/save=======object=====" + JSON.stringify(object));
        var url = "history/save";
        $.post(url, object, function (data) {
            if (data.result) {
                $(dataTableName).bootgrid("reload");
                $("#confirm_modal").modal("hide");
                showMessageBox("info", data["resultDesc"]);
            } else {
                showMessageBox("danger", data["resultDesc"]);
            }
        })
    });

    Dropzone.options.myDropzone = {
        url: "/history/uploadAndSave",
        paramName: "file",
        maxFilesize: 10.0,// MB
        maxFiles: 1,
        acceptedFiles: null,
        autoProcessQueue: false,//是否立马上传
        addRemoveLinks: true,//是否添加删除功能
        parallelUploads: 100, //最大并行处理量
        init: function () {
            myDropzone = this;
            this.on("addedfile", function (file) {
                //上传文件时触发的事件
                $(".dz-message").css("display", "none");
            });
            this.on('sending', function (file, xhr, formData) {
                // console.log(file);
                //传递参数时在sending事件中formData，需要在前端代码加enctype="multipart/form-data"属性
                formData.append('historyId', historyId);
            });
            $("#button_upload").on("click", function () {
                myDropzone.processQueue();
            });
            this.on("success", function (file, data) {
                $("#import_modal").modal("hide");
                //上传完成后触发的方法
                console.log(data);
                if (data.result) {
                    $(dataTableName).bootgrid("reload");
                    showMessageBox("info", data["resultDesc"]);
                } else {
                    showMessageBox("danger", data["resultDesc"]);
                }

            });
        }
    }
});

//编辑记录
function edit(id) {
    cleanData();  //清空下拉列表的默认选项
    var url = "history/findById/" + id;
    var history = findByIdUrl(url);
    for (var key in history) {
        if (history[key]) {
            if (key == "parent") {
                var parentId = history["parent"].id;
                $("#historyForm #historySelect").val(parentId);
            } else {
                $("#historyForm #" + key).val(history[key]);
            }
        }
    }
    $("#historyForm #historySelect").select2({'width':'61%'});
}


/**
 *
 * @param id
 * upload file
 */
function upload(id) {
    historyId = id;
    console.log("historyId ====="+ historyId);
    $("#import_modal").modal("show");
    $(".dz-complete").css("display", "none");
    $(".dz-message").css("display", "block");
}


