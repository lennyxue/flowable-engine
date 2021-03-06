/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flowable.engine.impl.history.async.json.transformer;

import static org.flowable.job.service.impl.history.async.util.AsyncHistoryJsonUtil.getDateFromJson;
import static org.flowable.job.service.impl.history.async.util.AsyncHistoryJsonUtil.getStringFromJson;

import java.util.Collections;
import java.util.List;

import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.history.async.HistoryJsonConstants;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.job.service.impl.persistence.entity.HistoryJobEntity;
import org.flowable.task.service.impl.BaseHistoricTaskLogEntryBuilderImpl;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author martin.grofcik
 */
public class HistoricUserTaskLogRecordJsonTransformer extends AbstractHistoryJsonTransformer {

    @Override
    public List<String> getTypes() {
        return Collections.singletonList(HistoryJsonConstants.TYPE_HISTORIC_TASK_LOG_RECORD);
    }

    @Override
    public boolean isApplicable(ObjectNode historicalData, CommandContext commandContext) {
        return true;
    }

    @Override
    public void transformJson(HistoryJobEntity job, ObjectNode historicalData, CommandContext commandContext) {
        BaseHistoricTaskLogEntryBuilderImpl taskLogEntryBuilder = new BaseHistoricTaskLogEntryBuilderImpl();

        taskLogEntryBuilder.data(getStringFromJson(historicalData, HistoryJsonConstants.LOG_ENTRY_DATA));
        taskLogEntryBuilder.processInstanceId(getStringFromJson(historicalData, HistoryJsonConstants.PROCESS_INSTANCE_ID));
        taskLogEntryBuilder.executionId(getStringFromJson(historicalData, HistoryJsonConstants.EXECUTION_ID));
        taskLogEntryBuilder.processDefinitionId(getStringFromJson(historicalData, HistoryJsonConstants.PROCESS_DEFINITION_ID));
        taskLogEntryBuilder.taskId(getStringFromJson(historicalData, HistoryJsonConstants.TASK_ID));
        taskLogEntryBuilder.tenantId(getStringFromJson(historicalData, HistoryJsonConstants.TENANT_ID));
        taskLogEntryBuilder.timeStamp(getDateFromJson(historicalData, HistoryJsonConstants.CREATE_TIME));
        taskLogEntryBuilder.type(getStringFromJson(historicalData, HistoryJsonConstants.LOG_ENTRY_TYPE));
        taskLogEntryBuilder.userId(getStringFromJson(historicalData, HistoryJsonConstants.USER_ID));

        CommandContextUtil.getHistoricTaskService().createHistoricTaskLogEntry(taskLogEntryBuilder);
    }
}
