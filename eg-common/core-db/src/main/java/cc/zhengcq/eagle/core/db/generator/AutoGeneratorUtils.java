package cc.zhengcq.eagle.core.db.generator;

import cc.zhengcq.eagle.core.common.utils.PropertiesLoader;
import cc.zhengcq.eagle.core.db.base.BaseModel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengcq
 */
public class AutoGeneratorUtils {

    static final String SUPER_ENTITY_CLASS = "cc.zhengcq.eagle.core.db.base.BaseModel";
    static final String SUPER_MAPPER_CLASS = "cc.zhengcq.eagle.core.db.base.BaseDao";
    static final String SUPER_SERVICE_CLASS = "cc.zhengcq.eagle.core.db.base.BaseService";
    static final String SUPER_SERVICE_IMPL_CLASS = "cc.zhengcq.eagle.core.db.base.BaseServiceImpl";

    static final String[] TABLE_TO_GENERATE_ARRAY = new String[]{"tb_vendor_po", "tb_vendor_po_line"};


    /**
     * 模块名称
     */
    static final  String MODULE_NAME = "vendor";
    //公共包名称
    static final String PACKAGE_PARENT = "cc.zhengcq.eagle";

    public static void main(String[] args) {
        AutoGeneratorUtils.getGenerator();
    }

    public static AutoGenerator getGenerator() {
        AutoGenerator mpg = new AutoGenerator();

        PropertiesLoader properties = new PropertiesLoader("db/application.properties");

//        System.out.println( properties.getProperty("spring.datasource.url") );

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("./");
        gc.setFileOverride(true);
        gc.setActiveRecord(false);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setAuthor("johnking");


        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sDao");
        // gc.setXmlName("%sDao");
        // gc.setServiceName("MP%sService");
        gc.setServiceImplName("%sService");
        gc.setIdType(IdType.ASSIGN_UUID);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert());
        dsc.setDriverName(properties.getProperty("spring.datasource.driver-class-name"));
        dsc.setUsername(properties.getProperty("spring.datasource.username"));
        dsc.setPassword(properties.getProperty("spring.datasource.password"));
        dsc.setUrl(properties.getProperty("spring.datasource.url"));
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 此处可以修改为您的表前缀
        strategy.setTablePrefix(new String[]{"tb_"});
        // 表名生成策略,去除前缀后，驼峰命名
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setInclude(TABLE_TO_GENERATE_ARRAY); // 需要生成的表
        //strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 自定义实体父类
        strategy.setSuperEntityClass(BaseModel.class, NamingStrategy.underline_to_camel);
        // 自定义实体，公共字段
        strategy.setSuperEntityColumns(new String[]{"idx", "status", "version", "create_by_member_idx", "create_time", "update_by_member_idx", "update_time", "remark"});
        // 自定义 mapper 父类
        strategy.setSuperMapperClass(SUPER_MAPPER_CLASS);
        // 自定义 service 父类
        strategy.setSuperServiceClass(SUPER_SERVICE_CLASS);
        // 自定义 service 实现类父类
        strategy.setSuperServiceImplClass(SUPER_SERVICE_IMPL_CLASS);
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuliderModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(PACKAGE_PARENT);
        pc.setModuleName(MODULE_NAME);
        pc.setEntity("model");
        pc.setMapper("dao"); // 输出目录到Dao
        pc.setXml("mapper"); // 输出Xml目录到Mapper
        pc.setServiceImpl("service");
        mpg.setPackageInfo(pc);
        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };
        mpg.setCfg(cfg);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/template 下面内容修改，
        // 放置自己项目的 src/main/resources/template 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        TemplateConfig tc = new TemplateConfig();
//         tc.setController("...");
        tc.setEntity("/tpl/model.java.vm");
        tc.setMapper("/tpl/dao.java.vm");
        tc.setServiceImpl("/tpl/serviceImpl.java.vm");
        tc.setXml("/tpl/mapper.xml.vm");
//         tc.setService("...");
//         tc.setServiceImpl("...");
        mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();

        return mpg;
    }
}
