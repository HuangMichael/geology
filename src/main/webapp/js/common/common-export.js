function exportTemplate() {
    var url = "/" + mainObject + "/oneKeyExport";
    window.location.href = url;
}


/**
 * 导入excel文件中的数据
 */
function importExcelData() {
    $("#import_modal").modal("show");
}

/**
 *
 * @param url  上传路径
 * @param tableId  业务表id  对应etlTable中的id
 */
function upLoadData(url, tableId) {

    Dropzone.options.myDropzone = {
        url: url,
        paramName: "file",
        maxFilesize: 10.0,// MB
        maxFiles: 10000,
        acceptedFiles: null,
        autoProcessQueue: false,//是否立马上传
        addRemoveLinks: true,//是否添加删除功能
        parallelUploads: 10000, //最大并行处理量
        init: function () {
            myDropzone = this;
            this.on("addedfile", function (file) {
                //上传文件时触发的事件
                $(".dz-message").css("display", "none");
            });
            this.on('sending', function (file, xhr, formData) {
                //传递参数时在sending事件中formData，需要在前端代码加enctype="multipart/form-data"属性
                formData.append('tableId', tableId);
            });

            $("#button_upload").on("click", function () {
                myDropzone.processQueue();
            });
            this.on("success", function (file, data) {
                //上传完成后触发的方法
                console.log(data);
                $("#import_modal").modal("hide");
            });
        }
    };

}