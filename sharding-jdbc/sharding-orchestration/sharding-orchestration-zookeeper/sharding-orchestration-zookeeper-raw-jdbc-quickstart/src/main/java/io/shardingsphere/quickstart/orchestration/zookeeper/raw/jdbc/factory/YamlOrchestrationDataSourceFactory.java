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

package io.shardingsphere.quickstart.orchestration.zookeeper.raw.jdbc.factory;

import io.shardingsphere.quickstart.type.ShardingType;
import org.apache.shardingsphere.shardingjdbc.orchestration.api.yaml.YamlOrchestrationMasterSlaveDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.orchestration.api.yaml.YamlOrchestrationShardingDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class YamlOrchestrationDataSourceFactory {
    
    public static DataSource newInstance(final ShardingType shardingType, final boolean loadConfigFromRegCenter) throws SQLException, IOException {
        String yamlFilePath;
        switch (shardingType) {
            case SHARDING_DATABASES:
                yamlFilePath = String.format("/META-INF/%s/sharding-databases.yaml", loadConfigFromRegCenter ? "cloud" : "local");
                return YamlOrchestrationShardingDataSourceFactory.createDataSource(getFile(yamlFilePath));
            case SHARDING_TABLES:
                yamlFilePath = String.format("/META-INF/%s/sharding-tables.yaml", loadConfigFromRegCenter ? "cloud" : "local");
                return YamlOrchestrationShardingDataSourceFactory.createDataSource(getFile(yamlFilePath));
            case SHARDING_DATABASES_AND_TABLES:
                yamlFilePath = String.format("/META-INF/%s/sharding-databases-tables.yaml", loadConfigFromRegCenter ? "cloud" : "local");
                return YamlOrchestrationShardingDataSourceFactory.createDataSource(getFile(yamlFilePath));
            case MASTER_SLAVE:
                yamlFilePath = String.format("/META-INF/%s/master-slave.yaml", loadConfigFromRegCenter ? "cloud" : "local");
                return YamlOrchestrationMasterSlaveDataSourceFactory.createDataSource(getFile(yamlFilePath));
            case SHARDING_MASTER_SLAVE:
                yamlFilePath = String.format("/META-INF/%s/sharding-master-slave.yaml", loadConfigFromRegCenter ? "cloud" : "local");
                return YamlOrchestrationShardingDataSourceFactory.createDataSource(getFile(yamlFilePath));
            default:
                throw new UnsupportedOperationException(shardingType.name());
        }
    }
    
    private static File getFile(final String fileName) {
        return new File(Thread.currentThread().getClass().getResource(fileName).getFile());
    }
    
}
