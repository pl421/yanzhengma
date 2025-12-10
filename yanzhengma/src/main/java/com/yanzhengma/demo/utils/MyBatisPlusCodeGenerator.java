package com.yanzhengma.demo.utils;



import java.util.List;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

//MyBatisPlusCodeGenerator.java
public class MyBatisPlusCodeGenerator {

 public static void generate(String jdbcUrl, String username, String password, List<String> tableNames) {

	// 1. 配置数据源
	 DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder(
			 jdbcUrl,
			 username,
			 password
	 ).build();
	 AutoGenerator generator = new AutoGenerator(dataSourceConfig); 
     // 数据源配置
//     DataSourceConfig.Builder dsc = new DataSourceConfig.Builder(jdbcUrl, username, password);
//     generator.dataSource(dsc.build());

     // 全局配置
     GlobalConfig.Builder globalConfig = new GlobalConfig.Builder()
             .outputDir(System.getProperty("user.dir")) // 输出到当前项目
             .author("testName")
             .enableSwagger() // 可选
             .commentDate("yyyy-MM-dd");
     generator.global(globalConfig.build());

     // 包配置
     PackageConfig.Builder packageConfig = new PackageConfig.Builder()
             .parent("com.example.generated.")
             .entity("entity")
             .mapper("mapper")
             .service("service")
             .controller("controller");
     generator.packageInfo(packageConfig.build());

     // 策略配置
     StrategyConfig strategyConfig = new StrategyConfig.Builder()
    		 .addInclude(tableNames)
             .entityBuilder().enableLombok()
             .serviceBuilder()
             .controllerBuilder().enableRestStyle()
             .build();
     generator.strategy(strategyConfig);

     // 执行生成
     generator.execute(new FreemarkerTemplateEngine());
 }
}