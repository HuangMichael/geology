/**
 * 分页函数
 * pno--页数
 * psize--每页显示记录数
 * 分页部分是从真实数据行开始，因而存在加减某个常数，以确定真正的记录数
 * 纯js分页实质是数据行全部加载，通过是否显示属性完成分页功能
 **/




function goPage(pno, psize) {
    var itable = document.getElementById("nyc_graphics").getElementsByTagName("li");
    var num = itable.length;//所有li数
    var totalPage = 0;//总页数
    //总共分几页
    if (num / psize > parseInt(num / psize)) {
        totalPage = parseInt(num / psize) + 1;
    } else {
        totalPage = parseInt(num / psize);
    }
    var currentPage = pno;//当前页数
    var startRow = (currentPage - 1) * psize + 1;//开始显示的行  31
    var endRow = currentPage * psize;//结束显示的行   40
    endRow = (endRow > num) ? num : endRow;
    //遍历显示数据实现分页
    for (var i = 1; i < (num + 1); i++) {
        var irow = itable[i - 1];
        if (i >= startRow && i <= endRow) {
            irow.style.display = "block";
        } else {
            irow.style.display = "none";
        }
    }
    var pageEnd = document.getElementById("pageEnd");
    var tempStr = "<p>共" + num + "条 共" + totalPage + "页 当前第" + currentPage + "页</p>";
    if (currentPage > 1) {
        tempStr += "<a onClick=\"goPage(" + (1) + "," + psize + ")\">首页</a>";
        tempStr += "<i class='icon-angle-left'></i><a onClick=\"goPage(" + (currentPage - 1) + "," + psize + ")\">上一页</a>"

    } else {
        tempStr += "首页";
        tempStr += "<上一页";

    }

    if (currentPage < totalPage) {

        tempStr += "<a onClick=\"goPage(" + (currentPage + 1) + "," + psize + ")\">下一页</a><i class='icon-angle-right'></i>";
        tempStr += "<a onClick=\"goPage(" + (totalPage) + "," + psize + ")\">尾页</a>";
    } else {
        tempStr += "下一页>";
        tempStr += "尾页";

    }

    // tempStr += "</div>";
    document.getElementById("barcon").innerHTML = tempStr;

}

