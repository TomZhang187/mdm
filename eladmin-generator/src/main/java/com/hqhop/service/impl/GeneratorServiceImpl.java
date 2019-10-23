package com.hqhop.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.hqhop.domain.GenConfig;
import com.hqhop.domain.vo.ColumnInfo;
import com.hqhop.domain.vo.TableInfo;
import com.hqhop.exception.BadRequestException;
import com.hqhop.service.GeneratorService;
import com.hqhop.utils.GenUtil;
import com.hqhop.utils.PageUtil;
import com.hqhop.utils.StringUtils;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zheng Jie
 * @date 2019-01-02
 */
@Service
public class GeneratorServiceImpl implements GeneratorService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Object getTables(String name, int[] startEnd) {
        // 使用预编译防止sql注入
//        String sql = "select table_name ,create_time , engine, table_collation, table_comment from information_schema.tables " +
        String sql = "select table_name, '' as create_time , '' as engine,'' as table_collation, '' as table_comment from information_schema.tables " +
                "where table_schema = 'public' " +
                "and table_name like ?";
        Query query = em.createNativeQuery(sql);
        query.setFirstResult(startEnd[0]);
        query.setMaxResults(startEnd[1]-startEnd[0]);
        query.setParameter(1, StringUtils.isNotBlank(name) ? ("%" + name + "%") : "%%");
        List<Object[]> result = query.getResultList();
        List<TableInfo> tableInfos = new ArrayList<>();
        for (Object[] obj : result) {
            tableInfos.add(new TableInfo(obj[0],obj[1],obj[2],obj[3], ObjectUtil.isNotEmpty(obj[4])? obj[4] : "-"));
        }
        Query query1 = em.createNativeQuery("SELECT COUNT(*) from information_schema.tables where table_schema = 'public'");
        Object totalElements = query1.getSingleResult();
        return PageUtil.toPage(tableInfos,totalElements);
    }

    @Override
    public Object getColumns(String name) {
        // 使用预编译防止sql注入
        String sql = "select info.COLUMN_NAME, info.is_nullable, case info.data_type when 'character varying' then 'varchar' else info.data_type end as data_type, '' AS column_comment," +
                "case  when sub.column_name is null then '' else 'PRI' end as column_key, '' AS extra "+
                "FROM information_schema.COLUMNS info left join ( "+
                "SELECT kcu.table_name, kcu.column_name FROM information_schema.table_constraints AS tc "+
        "JOIN information_schema.key_column_usage AS kcu ON tc.CONSTRAINT_NAME = kcu.CONSTRAINT_NAME "+
        " WHERE tc.constraint_type = 'PRIMARY KEY'"+
        ") sub on sub.table_name=info.TABLE_NAME and sub.column_name=info.column_name " +
        "where info.table_name = ? and info.table_schema = 'public' order by ordinal_position";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, StringUtils.isNotBlank(name) ? name : null);
        List<Object[]> result = query.getResultList();
        List<ColumnInfo> columnInfos = new ArrayList<>();
        for (Object[] obj : result) {
            columnInfos.add(new ColumnInfo(obj[0],obj[1],obj[2],obj[3],obj[4],obj[5],null,"true"));
        }
        return PageUtil.toPage(columnInfos,columnInfos.size());
    }

    @Override
    public void generator(List<ColumnInfo> columnInfos, GenConfig genConfig, String tableName) {
        if(genConfig.getId() == null){
            throw new BadRequestException("请先配置生成器");
        }
        try {
            GenUtil.generatorCode(columnInfos,genConfig,tableName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
