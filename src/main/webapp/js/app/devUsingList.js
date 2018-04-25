/**
 * Created by Administrator on 2017/5/22 0022.
 */
dataTableName = "#dataTableList";
docName = "系统管理-基础数据管理-开发利用信息";
mainObject = "devUsing";
var areaDevUsingId;
appName = "开发利用信息";
formId = "#devUsingForm";
selectArray = ["#id", "#devUsingSelect"];


$(function () {
    initMenus("topMenu");

    initAutoComplete("titleSearch", "/devUsing/findAll", "title");

    //编辑或者新增界面的上级开发利用选择下拉列表
    var devUsingUrl = "devUsing/findByLevel/0";//返回值是一个列表，查找各个主标题的信息
    var devUsings = getSelectedData(devUsingUrl, "title");
    console.log("-------开发利用-------------" + JSON.stringify(devUsings));
    initSelectData("#devUsingSelect", {parentsArray: devUsings});

    var grid = $(dataTableName).bootgrid({
        selection: true,
        multiSelect: true,
        ajax: true,
        post: function () {
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed"
            };
        },
        url: "/devUsing/data",
        formatters: {
            "commands": function (column, row) {
                return "<button class='btn-sm command-edit' title='编辑' onclick='edit(" + row.id + ")'><i class='icon-edit'></i></button>" +
                    "<button class='btn-sm command-delete' title='删除' onclick='del(" + row.id + ")'><i class='icon-trash'></i></button>";
            },
            "upload": function (column, row) {
                return "<button class='btn-sm command-edit' title='上传' onclick='upload(" + row.id + ")'><i class='icon-upload'></i></button>"
            }
        }
    });

    //保存开发成果记录
    $("#saveBtn").on("click", function () {
        var obj = getFormJsonData("devUsingForm");
        console.log("obj-------------" + obj);
        var object = JSON.parse(obj);
        var devUsingParent = $("#devUsingSelect").val();
        if (devUsingParent.trim() == "") {//如果前台没有选择父亲的时候，前台的父id设置为null，后台去计算其他属性
            object["parentId"] = null;
        }
        console.log("object------devUsingForm-------" + JSON.stringify(object));
        var url = "devUsing/save";
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

    //上传多媒体文件
    Dropzone.options.myDropzone = {
        url: "/devUsing/uploadAndSave",
        paramName: "file",
        maxFilesize: 10.0,// MB
        maxFiles: 10,
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

                //传递参数时在sending事件中formData，需要在前端代码加enctype="multipart/form-data"属性
                formData.append('areaDevUsingId', areaDevUsingId);
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
    var url = "devUsing/findById/" + id;
    var devUsing = findByIdUrl(url);
    for (var key in devUsing) {
        if (devUsing[key]) {
            if (key == "parent") {
                var parentId = devUsing["parent"].id;
                $("#devUsingForm #devUsingSelect").val(parentId);
            } else {
                $("#devUsingForm #" + key).val(devUsing[key]);
            }
        }
    }
    $("#devUsingForm #devUsingSelect").select2({'width': '61%'});
}


//删除记录
function del(id) {
    var url = getMainObject() + "/delete/" + id;
    bootbox.confirm({
        message: "确定要删除该记录么?",
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
                $.ajax({
                    type: "GET",
                    url: url,
                    success: function (msg) {
                        console.log("del msg-------------" + JSON.stringify(msg));
                        if (msg["result"]) {
                            showMessageBox("info", msg["resultDesc"]);
                            $(dataTableName).bootgrid("reload");
                        } else {
                            showMessageBox("danger", msg["resultDesc"]);
                        }
                    },
                    error: function (msg) {
                        showMessageBox("danger", "信息无法删除，请联系管理员");
                    }
                });
            }
        }
    });
}

// /**
//  * 点击新增按钮
//  */
// function addNewData() {
//     var formStr = "#devUsingForm";//路丽民修改20170815
//     //点击新增按钮后，首先要清除表单内容，包括各种下拉列表
//     $(formStr)[0].reset();
//     $("#devUsingForm #id").val("").change();//清除表单中的id
//     $("#devUsingForm #devUsingSelect").val("").change();//清除表单中的下拉列表
//     $("#confirm_modal").modal("show");
// }
/**
 *
 * @param id
 * upload file
 */
function upload(id) {
    areaDevUsingId = id;
    console.log("areaDevUsingId =====" + areaDevUsingId);
    $("#import_modal").modal("show");
    $(".dz-complete").css("display", "none");
    $(".dz-message").css("display", "block");
}