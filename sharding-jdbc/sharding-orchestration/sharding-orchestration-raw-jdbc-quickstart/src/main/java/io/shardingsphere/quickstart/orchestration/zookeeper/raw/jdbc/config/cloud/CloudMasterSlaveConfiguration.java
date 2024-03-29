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

package io.shardingsphere.quickstart.orchestration.zookeeper.raw.jdbc.config.cloud;

import io.shardingsphere.quickstart.config.ExampleConfiguration;
import org.apache.shardingsphere.orchestration.config.OrchestrationConfiguration;
import org.apache.shardingsphere.orchestration.reg.api.RegistryCenterConfiguration;
import org.apache.shardingsphere.shardingjdbc.orchestration.api.OrchestrationMasterSlaveDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

public final class CloudMasterSlaveConfiguration implements ExampleConfiguration {
    
    private final RegistryCenterConfiguration registryCenterConfig;
    
    public CloudMasterSlaveConfiguration(final RegistryCenterConfiguration registryCenterConfig) {
        this.registryCenterConfig = registryCenterConfig;
    }
    
    @Override
    public DataSource getDataSource() throws SQLException {
        return OrchestrationMasterSlaveDataSourceFactory.createDataSource(new OrchestrationConfiguration("orchestration-master-slave-data-source", registryCenterConfig, false));
    }
}
