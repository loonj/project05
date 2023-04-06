package cn.zhanx.project05.utils;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;

import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        generate2();
    }

    private static void generate2(){
        DataSourceConfig dsc=
                new DataSourceConfig.Builder("jdbc:mysql://m.jutrade.cn:3306/ticket?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC&useAffectedRows=true",
                        "root", "fdfdsa32324fddf$hjjkkjk7gf565%fdjkljk43@1988dfzdf68#pd3")
                        .build();

        GlobalConfig gc=
                new GlobalConfig.Builder()
                        .fileOverride()
                        .outputDir("/Users/fang/IdeaProjects/20230118/project05/src/main/java")
                        .author("金先生")
                        .enableSwagger()
                        .build();

        PackageConfig pc=
                new PackageConfig.Builder()
                        .parent("cn.zhanx.project05")
                        .moduleName("")
                        .entity("domain")
                        .service("service")
                        .serviceImpl("service.impl")
                        .mapper("mapper")
                        .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "/Users/fang/IdeaProjects/20230118/project05/src/main/resources/mapper"))
                        .build();

        StrategyConfig sc=
                new StrategyConfig.Builder()
                        //注意代码生成完了要改名，因为不小心运行了，代码就被覆盖了
                        .addInclude(new String[]{"wd_web_tcjf_notify5454"})
                        //这么传值也可以 new String[]{"t_rd_", "sys_"}
                        .addTablePrefix("t_rd_", "sys_")
                        .entityBuilder().enableLombok()  //lombok支持
                        .controllerBuilder().enableRestStyle()//RestController风格
                        .build();

        AutoGenerator autoGenerator=new AutoGenerator(dsc).global(gc).packageInfo(pc).strategy(sc);
        autoGenerator.execute();
    }
}
