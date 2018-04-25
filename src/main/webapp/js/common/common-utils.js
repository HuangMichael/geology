function transformDate(timestamp) {
    var date = new Date(timestamp);
    var dateStr = "";
    dateStr += date.getFullYear() + "-";
    dateStr += parseInt(date.getMonth() + 1) + "-";
    dateStr += date.getDate() + " ";
    dateStr += (date.getHours() < 10) ? "0" + date.getHours() + ":" : date.getHours() + ":";
    dateStr += (date.getMinutes() < 10) ? "0" + date.getMinutes() + ":" : date.getMinutes() + ":";
    dateStr += (date.getSeconds() < 10) ? "0" + date.getSeconds() : date.getSeconds();
    return dateStr
}

function transformYMD(timestamp) {
    var date = new Date(timestamp);
    var dateStr = "";
    dateStr += date.getFullYear() + "-";
    dateStr += (parseInt(date.getMonth() + 1) < 10) ? "0" + parseInt(date.getMonth() + 1) + "-" : parseInt(date.getMonth() + 1) + "-";
    dateStr += (date.getDate() < 10) ? "0" + date.getDate() : date.getDate();
    return dateStr
}


function transformDateHMS(timestamp) {
    var date = new Date(timestamp);
    var dateStr = "";
    if (date) {
        dateStr += (date.getHours() < 10) ? "0" + date.getHours() + ":" : date.getHours() + ":";
        dateStr += (date.getMinutes() < 10) ? "0" + date.getMinutes() + ":" : date.getMinutes() + ":";
        dateStr += (date.getSeconds() < 10) ? "0" + date.getSeconds() : date.getSeconds();
    }
    return dateStr;
}

/**
 *
 * @param interval 间隔可为正负整数 不传默认为0
 * @param dtDate 传入日期 不传默认为当前日期
 * @returns {string}
 */
function addMonth(interval, dtDate) {
    var date = (dtDate != null) ? new Date(dtDate) : new Date();
    interval = (interval) ? parseInt(interval) : 0;//间隔
    date.setMonth(date.getMonth() + interval);
    var month = (date.getMonth() + 1 < 10) ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
    return date.getFullYear() + '-' + month;
}

function addMonthToday(interval, dtDate) {
    var date = (dtDate != null) ? new Date(dtDate) : new Date();
    interval = (interval) ? parseInt(interval) : 0;//间隔
    date.setMonth(date.getMonth() + interval);
    var month = (date.getMonth() + 1 < 10) ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
    var day = (date.getDate() + 1 < 10) ? "0" + (date.getDate() + 1) : date.getDate() + 1;
    return date.getFullYear() + '-' + month + "-" + day;
}

/**
 * 新增或者编辑项目时对项目编号进行验证，公共方法——by lulimin 2017-11-07
 *
 * @param formAndProjectNoId 项目表单以及项目编号的id，方便jquery查询
 * @param projectNoReg 项目编号的正则表达式
 * @param currentProjectId 当前项目的id
 * @returns {boolean} 返回验证结果
 */
function verifyProjectNo(formAndProjectNoId, projectNoReg, currentProjectId) {
    var projectNo_ok = false;//项目编号的验证结果初始化为false
    var $projectNo_error = $(".projectNo_error");
    if ($(formAndProjectNoId).val() == "") {
        $projectNo_error.css("color", "red");
        $projectNo_error.html("请输入项目编号");
        projectNo_ok = false;
    } else if (!$(formAndProjectNoId).val().match(projectNoReg)) {
        // 有特殊字符
        $projectNo_error.css("color", "red");
        $projectNo_error.html("格式错误");
        projectNo_ok = false;
    } else {
        //新增时验证项目编号
        if (currentProjectId == null) {
            var projectNo = $(formAndProjectNoId).val();
            url = "project/findByProjectNo";
            obj = {projectNo: projectNo};

            $.post(url, obj, function (data) {
                if (data) {
                    console.log("-----新增data--------" + data);
                    $projectNo_error.css("color", "red");
                    $projectNo_error.html("项目编号已存在");
                    projectNo_ok = false;
                } else {
                    $projectNo_error.html("");
                    projectNo_ok = true;
                }
            })
        } else {
            var projectNo = $(formAndProjectNoId).val();
            url = "project/findProjectDuplicateByNoAndId";
            obj = {projectNo: projectNo, id: currentProjectId};

            $.post(url, obj, function (data) {
                if (data) {
                    console.log("-----编辑data--------" + data);
                    $projectNo_error.css("color", "red");
                    $projectNo_error.html("项目编号已存在");
                    projectNo_ok = false;
                } else {
                    $projectNo_error.html("");
                    projectNo_ok = true;
                }
            })
        }
    }
    return projectNo_ok;
}


/**
 * 新增或者编辑项目时对项目名称进行验证，公共方法——by lulimin 2017-11-07
 *
 * @param formAndProjectNameId 项目表单以及项目名称的id，方便jquery查询
 * @param projectNameReg 项目编号的正则表达式
 * @param currentProjectId 当前项目的id
 * @returns {boolean} 返回验证结果
 */
function verifyProjectName(formAndProjectNameId, projectNameReg, currentProjectId) {
    var projectName_ok = false;
    //新增和编辑时验证项目名称
    var $projectName_error = $(".projectName_error");
    if ($(formAndProjectNameId).val() == "") {
        $projectName_error.css("color", "red");
        $projectName_error.html("请输入项目名称");
        projectName_ok = false;
    }
    else if (!$(formAndProjectNameId).val().match(projectNameReg)) {
        // 有特殊字符
        $projectName_error.css("color", "red");
        $projectName_error.html("格式错误");
        projectName_ok = false;
    }
    else {
        //验证新增项目名称是否已存在
        if (currentProjectId == null) {
            var projectName = $(formAndProjectNameId).val();
            url = "project/findByProjectName";
            obj = {projectName: projectName};

            $.post(url, obj, function (data) {
                if (data) {
                    console.log("-----新增data--------" + data);
                    $projectName_error.html("项目名称已存在");
                    $projectName_error.css("color", "red");
                    roleName_ok = false;
                } else {
                    $projectName_error.html("");
                    projectName_ok = true;
                }
            })
        } else {
            var projectName = $(formAndProjectNameId).val();
            url = "project/findProjectDuplicateByNameAndId";
            obj = {projectName: projectName, id: currentProjectId};

            $.post(url, obj, function (data) {
                if (data) {
                    console.log("-----编辑data--------" + data);
                    $projectName_error.html("项目名称已存在");
                    $projectName_error.css("color", "red");
                    projectName_ok = false;
                } else {
                    $projectName_error.html("");
                    projectName_ok = true;
                }
            })
        }
    }
    return projectName_ok;
}

/**
 * 验证用户输入的数值是否正确，比如：面积、投资金额、长度之类的数值，不能为空，不能小于0
 * 20171124 by 路丽民
 * @return {boolean}
 * @param selector 选择器，此处为数值元素的类名，如".forVerify"
 */
function verifyInputNumbers(selector) {
    var verifyResult = true;//验证之前默认为true，只要有一个验证不通过，则设置为false
    for (var i = 0; i < $(selector).length; i++) {
        var currentItem = $(selector).eq(i);
        if (currentItem.val() == "") {
            currentItem.next().css("color", "red").html("数值不能为空！");
            verifyResult = false;
        } else if (currentItem.val() < 0) {
            currentItem.next().css("color", "red").html("数值不能小于0！");
            verifyResult = false;
        } else {
            currentItem.next().html("");
        }
    }
    return verifyResult;
}


/**
 * 验证负责人的联系方式，手机号或者座机号均需要通过验证
 * @returns {boolean}
 */
function verifyContact(selector) {
    var verifyResult = true;//验证之前默认为true，只要有一个验证不通过，则设置为false
    var currentItem = $(selector);
    if (currentItem.val() == "") {
        return true;
    }
    if (!(currentItem.val().match(mobileContact_reg)) && !(currentItem.val().match(phoneContact_reg))) {
        verifyResult = false;
        currentItem.next().css("color", "red").html("联系方式不正确！");
    } else {
        verifyResult = true;
        currentItem.next().html("");
    }
    return verifyResult;
}

function getFormJsonData(formId) {
    var array = $("#" + formId).serializeArray();
    var objStr = "{";
    for (var x in array) {
        var name = array[x]["name"];
        var value = array[x]["value"];
        if (name && value) {
            objStr += '"' + name + '"';
            objStr += ":";
            objStr += '"' + value + '",'
        }
    }
    objStr = objStr.substring(0, objStr.length - 1);
    objStr += "}";
    return objStr
}

/**
 *
 * @param formId formId 已经包含#
 * @returns {string}
 */
function getFormDataAsJSON(formId) {
    var array = $(formId).serializeArray();
    console.log("array-------------" + JSON.stringify(array));
    var objStr = "{";
    for (var x in array) {
        var name = array[x]["name"];
        var value = array[x]["value"];

        console.log(name + ":" + value + "\n");
        if (name && value) {
            objStr += '"' + name + '"';
            objStr += ":";
            objStr += '"' + value + '",'
        }
    }
    objStr = objStr.substring(0, objStr.length - 1);
    objStr += "}";
    return objStr;
}


function fillForm(data, formId) {
    var formArray = $(formId).serializeArray();
    var attrName;
    for (var x in formArray) {
        attrName = formArray[x]["name"];
        if (attrName) {
            $("input[name='" + attrName + "']").val(data[attrName] ? data[attrName] : null)
        }
    }
}

function showMessageBox(type, message) {
    $.bootstrapGrowl(message, {type: type, align: "right", stackup_spacing: 30})
}

function loadTableData(t, c, d) {
    var thead = $("#thead");
    var tbody = $("#tbody");
    if (thead) {
        var html = "<tr>";
        for (var x in c) {
            if (c[x]["title"]) {
                html += ('<th  data-column-id="' + c[x]["name"] + '">' + c[x]["title"] + "</th>")
            }
        }
        html += "</tr>";
        thead.html(html)
    }
    var html1 = "<tr>";
    for (var i in d) {
        for (var a in c) {
            if (d[i][c[a].name]) {
                html1 += "<td>" + d[i][c[a].name] + "</td>"
            }
        }
        html1 += "<tr>"
    }
    tbody.html(html1)
}

function addNodeAfterOperation(tree, childNode) {
    var zTree = (tree == null) ? getTreeRoot() : tree;
    var parentZNode = zTree.getNodeByParam("id", getSelectedNodeId(), null);
    zTree.addNodes(parentZNode, childNode, true);
    zTree.selectNode(zTree.getNodeByParam("id", childNode.id))
}

/**
 *
 * @param tree 树节点
 * @param childNode 子节点
 * @param parentId 重新选择的上级
 */
function addNodeAfterChangeOperation(tree, childNode, parentId) {
    var zTree = (tree == null) ? getTreeRoot() : tree;
    var parentZNode = zTree.getNodeByParam("id", parentId, null);
    zTree.addNodes(parentZNode, childNode, true);
    zTree.selectNode(zTree.getNodeByParam("id", childNode.id))
}


function updateNode(tree, nodeName) {
    var zTree = (tree == null) ? getTreeRoot() : tree;
    var node = getSelectedNode();
    node.name = nodeName.name;
    zTree.updateNode(node)
}

function getTreeRoot() {
    var zTree = $.fn.zTree.getZTreeObj("tree");
    return zTree;
}

function getSelectedNodeId() {
    var zTree = $.fn.zTree.getZTreeObj("tree");
    var selectedNode = zTree.getSelectedNodes()[0];
    var id = selectedNode.id;
    return id
}

function getSelectedNode() {
    var zTree = $.fn.zTree.getZTreeObj("tree");
    var selectedNode = zTree.getSelectedNodes()[0];
    return selectedNode
}

/**
 *
 * @param type  info  danger
 * @param message center
 */
function showMessageBox(type, message) {
    $.bootstrapGrowl(message, {type: type, align: "right", stackup_spacing: 50})
}

/**
 *
 * @param type info  danger
 * @param position left center right
 * @param message
 */
function showMessageBoxCenter(type, position, message) {
    $.bootstrapGrowl(message, {type: type, align: position, stackup_spacing: 30})
}

Array.prototype.indexOf = function (val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};


/**
 * 移除指定的元素
 * */
Array.prototype.remove = function (val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};


/**
 * 移除指定的元素
 * */
Array.prototype.removeButThis = function (val) {
    for (var x in this) {
        //如果值有重复
        if (this[x] == val) {
            this.clear();
            this.push(val);
        }

    }
};

Array.prototype.clear = function () {
    this.splice(0, this.length);
};


/**
 *  返回bootgrid 列表中所有的ID数组
 */
function getAllTableIdsByTableId(tableName) {
    var records = [];
    $("#" + tableName + " input[type='checkbox']").each(function (i) {
        records.push($(this).val());
    });
    console.log(records);
    return records;

}


Array.prototype.unique = function () {
    var n = {}, r = []; //n为hash表，r为临时数组
    for (var i = 0; i < this.length; i++) //遍历当前数组
    {
        if (!n[this[i]]) //如果hash表中没有当前项
        {
            n[this[i]] = true; //存入hash表
            r.push(this[i]); //把当前数组的当前项push到临时数组里面
        }
    }
    return r;
};


function sortArr(m, n) {
    return m > n ? 1 : (m < n ? -1 : 0);
}


/**
 *
 * @param obj
 * @returns {*}
 */
function getFilePath(obj) {
    if (obj) {

        if (window.navigator.userAgent.indexOf("MSIE") >= 1) {
            obj.select();

            return document.selection.createRange().text;
        }

        else if (window.navigator.userAgent.indexOf("Firefox") >= 1) {
            if (obj.files) {

                return obj.files.item(0).getAsDataURL();
            }
            return obj.value;
        }
        return obj.value;
    }
}

/**
 *
 * @param formId 设置form为只读
 */
// function setFormReadStatus(formId, formLocked, except) {
//     if (formLocked) {
//         $(formId + " input").attr("readonly", "readonly");
//         $(formId + " select").attr("disabled", "disabled");
//     } else {
//         $(formId + " input").attr("readonly", "readonly").removeAttr("readonly");
//         $(formId + " select").attr("disabled", "disabled").removeAttr("disabled");
//         // $(formId + " #status").attr("disabled", "disabled");
//         for (var x in except) {
//             $("#" + except[x]).attr("readonly", "readonly");
//         }
//     }
// }


//判断数组是否包含元素

Array.prototype.containObj = function (obj) {
    var i = this.length;
    while (i--) {
        if (this[i] === obj) {
            return true;
        }
    }
    return false;
}


/**
 *
 * @param value
 * @returns {string}
 */
var showStatus = function (value) {
    var array = ["无效", "有效"];
    return array[value];
}
