package com.yidian.shop.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @function:
 * @description: Generator.java
 * @date: 2021/01/17
 * @author: sunfayun
 * @version: 1.0
 */
@Slf4j
public class CodeGenerator {

    /**
     * 需要生成的表名
     */
    private static final String[] TABLE_NAME = {"t_delivery_fee_config"};

    /**
     * 使用的数据源
     */
    private static final DataSourceConfig DATA_SOURCE_CONFIG = getDbDataSource();

    /**
     * 表前缀
     */
    private static final String PACKAGE_NAME = "com.yidian.shop";


    public static void main(String[] args) {
        generateByTables();
    }


    private static void generateByTables() {
        GlobalConfig globalConfig = new GlobalConfig();
        StrategyConfig strategyConfig = new StrategyConfig();
        List<TableFill> tableFills = new ArrayList<>();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setLogicDeleteFieldName("del_flag")
                .setTableFillList(tableFills)
                .setTablePrefix("t_")
                .setInclude(TABLE_NAME);
        String srcPath = "src" + File.separator + "main" + File.separator + "java";
        log.info("srcPath = {}", srcPath);
        globalConfig.setActiveRecord(true)
                .setAuthor("dev")
                .setOutputDir(srcPath)
                .setEnableCache(false)
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setOpen(false)
                .setKotlin(false)
                .setActiveRecord(true)
                .setFileOverride(true)
                .setServiceName("%sService");
        new AutoGenerator().setGlobalConfig(globalConfig)
                .setDataSource(DATA_SOURCE_CONFIG)
                .setStrategy(strategyConfig)
                .setPackageInfo(new PackageConfig()
                        .setParent(PACKAGE_NAME)
                        .setEntity("entity")
                ).execute();
    }

    private static DataSourceConfig getDbDataSource() {
        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://192.168.223.9:3306/city_mall?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
        dsc.setUrl("jdbc:mysql://47.241.161.188:3306/city_mall?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setDbType(DbType.MYSQL);
        return dsc;
    }

}
