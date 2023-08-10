package ai.magicemerge.bluebird.app.service;

import com.github.davidfantasy.mybatisplus.generatorui.GeneratorConfig;
import com.github.davidfantasy.mybatisplus.generatorui.MybatisPlusToolsApplication;
import com.github.davidfantasy.mybatisplus.generatorui.mbp.NameConverter;

public class GeberatorUIServer {

	public static void main(String[] args) {
		GeneratorConfig config = GeneratorConfig.builder().jdbcUrl("jdbc:postgresql://bluebird-app.cq7kksqrqhro.rds.cn-northwest-1.amazonaws.com.cn:5432/bluebird-app")
				.userName("magic_emerge_rds")
				.password("YVJAN0cZVtPZ1KsSgmd2")
				.driverClassName("org.postgresql.Driver")
				//数据库schema，MSSQL,PGSQL,ORACLE,DB2类型的数据库需要指定
//				.schemaName("PGSQL")
				//如果需要修改entity及其属性的命名规则，以及自定义各类生成文件的命名规则，可自定义一个NameConverter实例，覆盖相应的名称转换方法，详细可查看该接口的说明：
				.nameConverter(new NameConverter() {
					/**
					 * 自定义Service类文件的名称规则
					 */
					@Override
					public String serviceNameConvert(String tableName) {
						return this.entityNameConvert(tableName) + "Service";
					}

					/**
					 * 自定义Controller类文件的名称规则
					 */
					@Override
					public String controllerNameConvert(String tableName) {
						return this.entityNameConvert(tableName) + "Action";
					}
				})
				//所有生成的java文件的父包名，后续也可单独在界面上设置
				.basePackage("ai.magicemerge.bluebird")
				.port(8068)
				.build();
		MybatisPlusToolsApplication.run(config);
	}
}
