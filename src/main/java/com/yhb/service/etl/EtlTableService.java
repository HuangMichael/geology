package com.yhb.service.etl;

/**
 * Created by Administrator on 2017/8/16.
 * ETL元数据业务类
 */

import com.yhb.dao.etl.EtlMediaRepository;
import com.yhb.dao.etl.EtlTableRepository;
import com.yhb.dao.etl.EtlTableTreeRepository;
import com.yhb.dao.mediaType.MediaTypeRepository;
import com.yhb.domain.etl.EtlMedia;
import com.yhb.domain.etl.EtlTable;
import com.yhb.domain.etl.EtlTableConfig;
import com.yhb.domain.etl.EtlTableTree;
import com.yhb.domain.mediaType.MediaType;
import com.yhb.domain.types.LandUsingType;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.service.mediaType.MediaTypeService;
import com.yhb.utils.SessionUtil;
import com.yhb.utils.StringUtils;
import com.yhb.utils.UploadUtil;
import com.yhb.utils.importer.tool.ImportService;
import com.yhb.vo.ReturnObject;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Service
public class EtlTableService extends BaseService {
    @Autowired
    EtlTableRepository etlTableRepository;

    @Autowired
    EtlTableTreeRepository etlTableTreeRepository;

    @Autowired
    MediaTypeRepository mediaTypeRepository;

    @Autowired
    EtlMediaRepository etlMediaRepository;

    @Autowired
    CommonDataService commonDataService;

    @Autowired
    EtlService etlService;

    @Autowired
    ImportService importService;
    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * @param etlTable
     * @return 保存元数据表
     */
    public EtlTable save(EtlTable etlTable) {
        return etlTableRepository.save(etlTable);
    }

    /**
     * @return 查询所有的元数据表
     */
    public List<EtlTable> findAll() {
        return etlTableRepository.findAll();
    }

    /**
     * @param id
     * @return 根据id查询元数据表
     */
    public EtlTable findById(Long id) {
        return etlTableRepository.findOne(id);
    }

    /**
     * @param id
     * @return 根据id删除
     */
    public ReturnObject delete(Long id) {
        EtlTable etlTable = etlTableRepository.findOne(id);
        if (etlTable == null) {
            return commonDataService.getReturnType(false, "", "id为" + id + "的ETL元数据信息不存在,请确认！");
        }

        try {
            etlTableRepository.delete(etlTable);
            //再查询一次查看是否删除
            EtlTable etlTable1 = etlTableRepository.findOne(id);
            return commonDataService.getReturnType(etlTable1 == null, "ETL元数据信息删除成功！", "ETL元数据信息删除失败！");
        } catch (Exception e) {
            e.printStackTrace();
            return commonDataService.getReturnType(true, "ETL元数据信息删除成功!", "ETL元数据信息删除失败！");
        }
    }

    /**
     * @return 选择所有的id列表
     */
    public List<Long> selectAllIds() {
        return etlTableRepository.selectAllIds();
    }


    /**
     * @param tableIds 根据数据表id的列表创建多个excel
     * @return 批量创建excel
     */
    public List<Boolean> createExcels(String tableIds, HttpServletRequest request, HttpServletResponse response) {
        String[] tableIdsArray = tableIds.split(",");
        List<Boolean> resultList = new ArrayList<>();
        for (String idStr : tableIdsArray) {
            Long tableId = Long.parseLong(idStr);
            Boolean res = createOneExcel(tableId, request, response);
            if (res) {
                resultList.add(res);
            }
        }
        return resultList;
    }


    /**
     * @param tableId 根据一个数据表id创建excel
     * @return
     */
    public Boolean createOneExcel(Long tableId, HttpServletRequest request, HttpServletResponse response) {
        if (tableId == null) {
            return false;
        }
        EtlTable etlTable = etlTableRepository.findOne(tableId);
        if (etlTable == null) {
            return false;
        }
        List<EtlTableConfig> etlTableConfigList = etlService.findByEtlTableId(tableId);
        if (etlTableConfigList == null || etlTableConfigList.size() == 0) {
            return false;
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download");
            String fileName = etlTable.getTableDesc() + ".xls";
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HSSFSheet sheet = wb.createSheet(etlTable.getTableDesc());//以中文名称命名excel文件
        HSSFRow row = sheet.createRow((0));
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCell cell;
        EtlTableConfig etlTableConfig;
        String[] dataList;
        CellRangeAddressList regions;
        DVConstraint constraint;
        HSSFDataValidation dataValidation;
        String referenceTable;
        String referenceColName;
        for (int i = 0; i < etlTableConfigList.size(); i++) {
            etlTableConfig = etlTableConfigList.get(i);
            cell = row.createCell(i);
            cell.setCellValue(etlTableConfig.getBaseColDesc());
            cell.setCellStyle(style);
            int firstCol = etlTableConfig.getSortNo().intValue();
            referenceTable = etlTableConfig.getReferenceTable();
            referenceColName = etlTableConfig.getReferenceColName();
            if (referenceTable != null && etlTableConfig.getReferenceColName() != null) {
                dataList = queryExcelDataList(referenceTable, referenceColName);
                regions = new CellRangeAddressList(1, 100, firstCol - 1, firstCol - 1);
                //创建下拉列表数据
                constraint = DVConstraint.createExplicitListConstraint(dataList);
                //绑定
                dataValidation = new HSSFDataValidation(regions, constraint);
                sheet.addValidationData(dataValidation);
            }
        }

        Boolean result = false;//导出excel的结果，初始值默认为false
        try {
            OutputStream out = response.getOutputStream();
            wb.write(out);
            out.close();
            result = true;
            log.info("=====导出excel  成功====" + result);
        } catch (ServiceException e) {
            log.info("=====导出excel异常====" + e);
            result = false;
        } catch (Exception e1) {
            log.info("=====导出excel异常====" + e1);
            result = false;
        }
        return result;
    }


    /**
     * @param serviceTableName 业务表名称
     * @param request
     * @param response
     * @return 创建Excel模板
     */
    public Boolean createExcelTemplate(String serviceTableName, HttpServletRequest request, HttpServletResponse response) {
        if (serviceTableName == null) {
            return false;
        }

        //根据业务表查询 etlTable
        EtlTable etlTable = etlTableRepository.findByServiceTableName(serviceTableName);
        Long tableId = etlTable.getId();
        if (etlTable == null) {
            return false;
        }
        List<EtlTableConfig> etlTableConfigList = etlService.findByEtlTableId(tableId);
        if (etlTableConfigList == null || etlTableConfigList.size() == 0) {
            return false;
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download");
            String fileName = etlTable.getTableDesc() + ".xls";
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HSSFSheet sheet = wb.createSheet(etlTable.getTableDesc());//以中文名称命名excel文件
        HSSFRow row = sheet.createRow((0));
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCell cell;
        EtlTableConfig etlTableConfig;
        String[] dataList;
        CellRangeAddressList regions;
        DVConstraint constraint;
        HSSFDataValidation dataValidation;
        String referenceTable;
        String referenceColName;
        for (int i = 0; i < etlTableConfigList.size(); i++) {
            etlTableConfig = etlTableConfigList.get(i);
            cell = row.createCell(i);
            cell.setCellValue(etlTableConfig.getBaseColDesc());
            cell.setCellStyle(style);
            int firstCol = etlTableConfig.getSortNo().intValue();
            referenceTable = etlTableConfig.getReferenceTable();
            referenceColName = etlTableConfig.getReferenceColName();
            if (referenceTable != null && etlTableConfig.getReferenceColName() != null) {
                dataList = queryExcelDataList(referenceTable, referenceColName);
                regions = new CellRangeAddressList(1, 100, firstCol - 1, firstCol - 1);
                //创建下拉列表数据
                constraint = DVConstraint.createExplicitListConstraint(dataList);
                //绑定
                dataValidation = new HSSFDataValidation(regions, constraint);
                sheet.addValidationData(dataValidation);
            }
        }

        Boolean result = false;//导出excel的结果，初始值默认为false
        try {
            OutputStream out = response.getOutputStream();
            wb.write(out);
            out.close();
            result = true;
            log.info("=====导出excel  成功====" + result);
        } catch (ServiceException e) {
            log.info("=====导出excel异常====" + e);
            result = false;
        } catch (Exception e1) {
            log.info("=====导出excel异常====" + e1);
            result = false;
        }
        return result;
    }


    /**
     * @return 查询所有的ETL表。形成树状变为ztree的形式
     */
    public List<EtlTableTree> findEtlTableTree() {
        return etlTableTreeRepository.findAll();
    }


    /**
     * 向基础数据表中导入数据
     *
     * @param filePath
     * @param className
     * @param etlTableConfigList
     * @throws Exception
     */
    @Transactional
    public int[] importData(EtlTable etlTable, String filePath, String className, List<EtlTableConfig> etlTableConfigList) throws Exception {

        int importSize[] = importService.importDataFromExcel(etlTable, filePath, className, 1, etlTableConfigList);
        //数据要导入到基础表中
        return importSize;
    }


    /**
     * @param file
     * @param request
     * @return 上传文件到文件服务器，若上传成功则将文件路径等基本信息保存在数据库
     */
    @Transactional
    public String uploadAndSave(MultipartFile file, Long tableId, HttpServletRequest request) {
        String contextPath = SessionUtil.getContextPath(request);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStr = sdf.format(new Date());
        String dirStr = "mediaDocs\\etlMedia\\" + timeStr;//ETL多媒体存放的文件夹，加上时间戳来唯一标识该时间上传的所有的文件
        String realDir = contextPath + dirStr;//绝对目录路径
        if (!UploadUtil.createDirectory(realDir)) {//目录创建失败则返回null
            return null;
        }
        String fileName = file.getOriginalFilename();//文件名
        String filePath = realDir + "\\" + fileName;//绝对文件路径
        Long mediaTypeId = 1L;//多媒体类型默认为文档
        Boolean result = UploadUtil.uploadFile(file, filePath);//上传文件到ftp
        if (result) {
            //如果上传到ftp成功，就保存多媒体信息到数据库
            EtlMedia etlMedia = new EtlMedia();
            etlMedia.setFileName(fileName);
            etlMedia.setFileSize(file.getSize());
            etlMedia.setFileRelativeUrl(dirStr + "\\" + fileName);//设置文件的相对路径，"mediaDocs\\etlMedia"+文件名
            etlMedia.setFileAbsoluteUrl(filePath);//设置文件的绝对路径，精确到盘符，如："E:\bmis0628\....\webapp"+"mediaDocs\\etlMedia"+文件名
            etlMedia.setMediaType(mediaTypeRepository.findOne(mediaTypeId));
            EtlTable etlTable = etlTableRepository.findOne(tableId);
            etlMedia.setEtlTable(etlTable);
            etlMedia.setStatus("1");//默认设置为1,为已审核状态
            EtlMedia etlMedia1 = etlMediaRepository.save(etlMedia);
            if (etlMedia1 != null) {
                return filePath;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    /**
     * @param etlTableConfigList etl表配置信息
     * @return
     */
    public String getCreateTableContent(List<EtlTableConfig> etlTableConfigList) {

        StringBuilder sb = new StringBuilder();
        sb.append("id int8 NOT NULL,");
        Iterator<EtlTableConfig> it = etlTableConfigList.iterator();
        while (it.hasNext()) {
            EtlTableConfig config = it.next();
            sb.append(config.getBaseColName() + " ");
            sb.append(config.getDataType() + "(" + config.getLength() + ")");
            boolean isNull = config.getIsNull().equals("0");
            sb.append(isNull ? "" : " not null");
            sb.append(",");
        }
        String content = sb.toString();
        content = content.substring(0, content.length() - 1);
        return content;

    }


    /**
     * @param etlTableConfigList
     * @return
     */
    public String getInsertDataCols(List<EtlTableConfig> etlTableConfigList) {
        StringBuilder sb = new StringBuilder();
        List<String> colNameList = new ArrayList<>();
        sb.append("(id,");
        for (EtlTableConfig config : etlTableConfigList) {
            if (config.getReferenceType().equals("1")) {
                colNameList.add(config.getServiceColName());
            } else {
                colNameList.add(config.getServiceColName());
            }
        }
        sb.append(String.join(",", colNameList));
        sb.append(")");
        String content = sb.toString();
        return content;
    }

    /**
     * @param etlTableConfigList
     * @return
     */
    public String getSelectDataCols(String seqName,List<EtlTableConfig> etlTableConfigList) {
        StringBuilder sb = new StringBuilder();
//        String seqName = "seq_person";
        List<String> colNameList = new ArrayList<>();
        sb.append("nextval('" + seqName + "'),");
        for (EtlTableConfig config : etlTableConfigList) {
            //如果不参考  直接去 数据表字段
            switch (config.getReferenceType()) {
                case "0": {
                    String dateStr = config.getServiceColName();
                    if (config.getClassType().endsWith("Date")) {
                        dateStr = "to_date(" + config.getServiceColName() + ",'yyyy-MM-dd')";
                    }
                    colNameList.add(dateStr);
                }
                break;
                case "1": {
                    colNameList.add(config.getReferenceTable() + ".id");
                }
                break;
                default:
                    colNameList.add(config.getServiceColName());
                    break;
            }


            //如果参考静态值列表  根据静态值列表生成case when语句


            //如果参考动态值列表  根据关系 写出join语句


        }
        sb.append(String.join(",", colNameList));
        String content = sb.toString();
        return content;
    }


    /**
     * @param etlTableConfigList
     * @return
     */
    public String getJoinCondition(EtlTable etlTable, List<EtlTableConfig> etlTableConfigList) {
        StringBuilder sb = new StringBuilder();
        List<String> joinList = new ArrayList<>();
        for (EtlTableConfig config : etlTableConfigList) {
            if (config.getReferenceType().equals("1")) {
                String referenceTable = config.getReferenceTable();
                joinList.add(" join " + referenceTable + " on " + etlTable.getBaseTableName() + "." + config.getBaseColName() + "=" + referenceTable + "." + config.getReferenceColName());
            }
        }
        sb.append(String.join(" ", joinList));
        String content = sb.toString();
        return content;
    }


    /**
     * @param tableName
     * @param colName
     * @return 查询excel下拉列表
     */
    public String[] queryExcelDataList(String tableName, String colName) {
        String sql = "select distinct " + colName + " from " + tableName;
        List<String> stringList = jdbcTemplate.queryForList(sql, String.class);
        int size = stringList.size();
        return stringList.toArray(new String[size]);//使用了第二种接口，返回值和参数均为结果;
    }
}