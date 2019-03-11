/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.shardingsphere.quickstart.orchestration.zookeeper.raw.jdbc;

import io.shardingsphere.quickstart.common.jdbc.repository.OrderItemRepositoryImpl;
import io.shardingsphere.quickstart.common.jdbc.repository.OrderRepositoryImpl;
import io.shardingsphere.quickstart.common.jdbc.service.CommonServiceImpl;
import io.shardingsphere.quickstart.common.service.CommonService;
import io.shardingsphere.quickstart.orchestration.zookeeper.raw.jdbc.factory.YamlOrchestrationDataSourceFactory;
import io.shardingsphere.quickstart.type.ShardingType;
import org.apache.shardingsphere.shardingjdbc.jdbc.adapter.AbstractDataSourceAdapter;

import javax.sql.DataSource;

/*
 * 1. Please make sure master-slave data sync on MySQL is running correctly. Otherwise this example will query empty data from slave.
 * 2. Please make sure sharding-orchestration-reg-zookeeper-curator in your pom if registryCenterType = RegistryCenterType.ZOOKEEPER.
 * 3. Please make sure sharding-orchestration-reg-etcd in your pom if registryCenterType = RegistryCenterType.ETCD.
 */
public class YamlConfigurationExample {
    
    private static ShardingType shardingType = ShardingType.SHARDING_DATABASES;
//    private static ShardingType shardingType = ShardingType.SHARDING_TABLES;
//    private static ShardingType shardingType = ShardingType.SHARDING_DATABASES_AND_TABLES;
//    private static ShardingType shardingType = ShardingType.MASTER_SLAVE;
//    private static ShardingType shardingType = ShardingType.SHARDING_MASTER_SLAVE;
    
    private static boolean loadConfigFromRegCenter = false;
//    private static boolean loadConfigFromRegCenter = true;
    
    public static void main(final String[] args) throws Exception {
        DataSource dataSource = YamlOrchestrationDataSourceFactory.newInstance(shardingType, loadConfigFromRegCenter);
        CommonService commonService = getCommonService(dataSource);
        commonService.initEnvironment();
        commonService.processSuccess();
        commonService.cleanEnvironment();
        closeDataSource(dataSource);
    }
    
    private static CommonService getCommonService(final DataSource dataSource) {
        return new CommonServiceImpl(new OrderRepositoryImpl(dataSource), new OrderItemRepositoryImpl(dataSource));
    }
    
    private static void closeDataSource(final DataSource dataSource) throws Exception {
        if (dataSource instanceof AbstractDataSourceAdapter) {
            ((AbstractDataSourceAdapter) dataSource).close();
        }
    }
    
}
