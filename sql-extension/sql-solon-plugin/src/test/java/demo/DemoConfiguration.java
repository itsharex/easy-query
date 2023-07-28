package demo;

import com.zaxxer.hikari.HikariDataSource;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

import javax.sql.DataSource;

/**
 * create time 2023/7/25 10:39
 * 文件说明
 *
 * @author xuejiaming
 */
@Configuration
public class DemoConfiguration {
    @Bean(value = "db1")
    public DataSource db1DataSource(@Inject("${db1}") HikariDataSource dataSource){
        return dataSource;
    }

//    /**
//     * 配置额外插件,比如自定义逻辑删除,加密策略,拦截器,分片初始化器,值转换,原子追踪更新
//     * @param configuration
//     */
//    @Bean(value = "db1")
//    public void db1QueryConfiguration(@Db("db1") QueryConfiguration configuration){
//        configuration.applyLogicDeleteStrategy(new MyLogicDelStrategy());
//        configuration.applyEncryptionStrategy(...);
//        configuration.applyInterceptor(...);
//        configuration.applyShardingInitializer(...);
//        configuration.applyValueConverter(...);
//        configuration.applyValueUpdateAtomicTrack(...);
//    }

//    /**
//     * 添加分表或者分库的路由,分库数据源
//     * @param runtimeContext
//     */
//    @Bean(value = "db1")
//    public void db1QueryRuntimeContext(@Db("db1") QueryRuntimeContext runtimeContext){
//        TableRouteManager tableRouteManager = runtimeContext.getTableRouteManager();
//        DataSourceRouteManager dataSourceRouteManager = runtimeContext.getDataSourceRouteManager();
//        tableRouteManager.addRoute(...);
//        dataSourceRouteManager.addRoute(...);
//
//        DataSourceManager dataSourceManager = runtimeContext.getDataSourceManager();
//
//        dataSourceManager.addDataSource(key, dataSource, poolSize);
//    }
}