/**
 * Created by Administrator on 2017/5/22 0022.
 */

dataTableName = "#dataTableList";
docName = "数据ETL";
mainObject = "etl";
appName = "数据ETL";
var currentTableId = "";    //当前点击的table
//每个界面，在公共方法里面清空
formId = "#etlConfigForm";
selectArray = ["#id"];

var setting = {
    check: {
        enable: true,
        chkboxType: {
            "Y": "s",
            "N": "s"
        }
    },
    data: {
        key: {
            title: "t"
        },
        simpleData: {
            enable: true
        }
    },
    callback: {
        onClick: onClick
    }
};

/**
 * 点击某个位置节点的事件
 * @param event
 * @param treeId
 * @param treeNode
 * @param clickFlag
 */
function onClick(event, treeId, treeNode, clickFlag) {
    var id = treeNode.id;
    var url = "/etlTable/findById/" + id;
    $.get(url, function (data) {
        console.log(data);
        for (var key in data) {
            if (data[key]) {
                $("#etlForm #" + key).val(data[key]);
            }
        }
    })
}


function initZTree() {
    var zNodes = [];
    //使用ajax的get方法获取所有的行政区划：json数组
    $.get("/etlTable/findEtlTableTree", function (data) {
        zNodes[0] = {
            id: 0,//虚假值
            pId: null,//虚假值
            name: "基础表信息",
            t: "基础表信息",
            open: true
        };

        for (var x = 0; x < data.length; x++) {
            // console.log("=====x===" + JSON.stringify(data[x]["id"]));
            if (!isNaN(data[x]["id"])) {
                zNodes[x + 1] = {
                    id: data[x]["id"],
                    pId: 0,     //其他区块的pId全部设置为0
                    name: data[x]["name"],
                    t: data[x]["t"],
                    open: true
                }
            }
        }
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    });

}

$(function () {
    initMenus("topMenu");
    highLight();   //当前导航栏高亮显示
    initZTree();


    //初始化位置列表，并且添加编辑删除按钮
    var grid = $(dataTableName).bootgrid({
        selection: true,
        multiSelect: true,
        ajax: true,
        post: function () {
            return {
                id: "b0df282a-0d67-40e5-8558-c9e93b7befed"
            };
        },
        url: "/etl/data"
    });


    $("#saveBtn").on("click", function () {
        var obj = getFormJsonData("etlConfigForm");

        var object = JSON.parse(obj);
        object["landSize"] = object["landSize"] * 1;
        console.log("obj-------------" + obj);
        var url = "etl/save";
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
        url: "/etl/uploadAndSave",
        paramName: "file",
        maxFilesize: 10.0,// MB
        maxFiles: 1,
        acceptedFiles: ".xls,.xlsx",//预判断，限制文件类型，用户只能选择excel文件
        autoProcessQueue: false,//是否立马上传
        addRemoveLinks: true,//是否添加删除功能
        parallelUploads: 1, //最大并行处理量
        init: function () {
            myDropzone = this;
            this.on("addedfile", function (file) {
                //上传文件时触发的事件
                var fileName = file.name;
                console.log("上传文件===========" + fileName);
                //再次判断用户选择的文件类型，如果不是excel文件则警告
                if (fileName.indexOf(".xls") < 0 && fileName.indexOf(".xlsx") < 0) {
                    showMessageBox("danger", "文件类型错误！请上传Excel文件！");
                    return;
                }
            });
            this.on('sending', function (file, xhr, formData) {
                //传递参数时在sending事件中formData，需要在前端代码加enctype="multipart/form-data"属性
                formData.append('tableId', currentTableId);
            });
            $("#button_upload").on("click", function () {
                myDropzone.processQueue();
            });
            this.on("queuecomplete", function (file) {
                //上传完成后触发的方法
            });
            this.on("removedfile", function (file) {
                //删除文件时触发的方法
                console.log("删除一个文件");
            });
            this.on("success", function (file, data) {
                $("#import_modal").modal("hide");
                //上传完成后触发的方法
                console.log(data);
                if (data.result) {
                    showMessageBox("info", data["resultDesc"]);
                } else {
                    showMessageBox("danger", data["resultDesc"]);
                }

            });
        }
    }
});

// /**
//  * 点击新增按钮
//  */
// function addNewData() {
//     $('#etlConfigForm')[0].reset();
//
//     $("#confirm_modal").modal("show");
// }



/**
 * 导出excel模板，可批量导出excel文件
 */
function createExcel() {
    var tableIds = getSelectedMenuIds();
    if (tableIds == null || tableIds.length <= 0) {
        showMessageBox("danger", "务必勾选一个基础表！");
        return;
    }
    //删除数组中值为0的根节点：基础表信息
    for (var i = 0; i < tableIds.length; i++) {
        if (tableIds[i] == 0) {
            tableIds.splice(i, 1);//splice() 方法用于插入、删除或替换数组的元素。
        }
    }
    tableIds = tableIds.join(',');
    var url = "/etl/createExcels?tableIds=" + tableIds;
    window.location.href = url;
}

/**
 * 导入excel文件中的数据
 */
function importData() {
    var tableIds = getSelectedMenuIds();//获取用户选择的树节点id
    if (tableIds.length != 1) {
        showMessageBox("danger", "请勾选一个基础表！");
        return;
    }
    currentTableId = tableIds[0];
    //上传多媒体文件
    console.log("====uploadAndSave==currentTableId==" + currentTableId);
    $("#import_modal").modal("show");
}