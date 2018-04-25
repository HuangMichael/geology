package com.yhb.utils.importer.tool;

import java.util.List;

/**
 * ETL从外部数据数据源导入到基础表中
 */
public interface EtlImportDataService {
    /**
     * 向基础数据表中导入数据
     *
     * @param filePath      文件路径
     * @param tableName     基础表名
     * @param resourceCols  外部数据源的列名集合
     * @param baseTableName 目的基础表名称
     */
    void importData(String filePath, String tableName, List<String> resourceCols, String baseTableName) throws Exception;


    /**
     * 从基础表转到业务表中
     *
     * @param baseTableName    基础表名
     * @param resourceCols     数据源字段列名集合
     * @param serviceTableName 业务表名
     * @param targetCols       业务表字段名集合
     */
    void transAndLoad(String baseTableName, List<String> resourceCols, String serviceTableName, List<String> targetCols) throws Exception;


}
