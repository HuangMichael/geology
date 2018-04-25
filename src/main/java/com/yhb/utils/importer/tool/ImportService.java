package com.yhb.utils.importer.tool;


import com.yhb.domain.etl.EtlTable;
import com.yhb.domain.etl.EtlTableConfig;
import com.yhb.domain.mediaType.MediaType;
import com.yhb.service.base.BaseService;
import com.yhb.service.mediaType.MediaTypeService;
import com.yhb.utils.StringUtils;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 导入实现业务类
 **/
@Service
public class ImportService implements ImporTable {
    @Autowired
    JdbcTemplate jdbcTemplate;
    protected Log log = LogFactory.getLog(this.getClass());

    /**
     * @param filePath           excel文档的路径
     * @param className          类名
     * @param startRow           开始行
     * @param etlTableConfigList 对应的字段配置列表
     * @return
     * @throws IOException
     */
    public int[] importDataFromExcel(EtlTable etlTable, String filePath, String className, Integer startRow, List<EtlTableConfig> etlTableConfigList) throws IOException {


        String serviceTableName = etlTable.getServiceTableName();

        String seqName = "seq_";

        if (serviceTableName != null && !serviceTableName.equals("")) {
            seqName += serviceTableName.toLowerCase().split("t_")[1];
        }
        int[] size = {};
        try {
            InputStream is = new FileInputStream(filePath);
            Workbook rwb = Workbook.getWorkbook(is);
            Sheet rs = rwb.getSheet(0);
            int rowNum = rs.getRows();
            startRow = (startRow != null) ? startRow : 0;
            //从第一行开始  跳过标题行
            EtlTableConfig config;
            String sql = "";
            sql += "insert into  " + etlTable.getBaseTableName();
            sql += "(";
            sql += "id,";
            for (int c = 0; c < etlTableConfigList.size(); c++) {
                config = etlTableConfigList.get(c);
                sql += config.getBaseColName() + ",";
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += ")";
            sql += "values";
            for (int r = startRow; r < rowNum; r++) {
                log.info("seqName---------"+seqName);
                sql += "(nextval('" + seqName + "'),";
                for (int c = 0; c < etlTableConfigList.size(); c++) {
                    sql += "'" + rs.getCell(c, r).getContents().trim() + "',";
                }
                sql = sql.substring(0, sql.length() - 1);
                sql += "),";
            }
            sql = sql.substring(0, sql.length() - 1);
            size = jdbcTemplate.batchUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * @param etlTable           etl表信息
     * @param etlTableConfigList etl表配置信息
     * @return
     * @throws IOException
     */
    public int[] importDataToBaseTable(EtlTable etlTable, List<EtlTableConfig> etlTableConfigList) throws IOException {
        String sql = "";
        //组装sql语句
        sql += "insert into table (colsStr) values";
        sql += "(";
        sql += ")";
        int size[] = jdbcTemplate.batchUpdate(sql);
        return size;
    }
}
