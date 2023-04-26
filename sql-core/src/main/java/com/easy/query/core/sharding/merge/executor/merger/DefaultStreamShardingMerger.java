package com.easy.query.core.sharding.merge.executor.merger;

import com.easy.query.core.basic.jdbc.executor.internal.AffectedRowsExecuteResult;
import com.easy.query.core.sharding.merge.StreamMergeContext;
import com.easy.query.core.sharding.merge.abstraction.StreamResult;
import com.easy.query.core.basic.jdbc.executor.internal.ExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.QueryExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.unit.ShardingMerger;
import com.easy.query.core.sharding.merge.impl.MultiOrderStreamMergeResult;
import com.easy.query.core.util.ArrayUtil;

import java.util.Collection;
import java.util.List;

/**
 * create time 2023/4/20 22:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultStreamShardingMerger implements ShardingMerger<ExecuteResult> {
    private static final DefaultStreamShardingMerger instance=new DefaultStreamShardingMerger();
    public static DefaultStreamShardingMerger getInstance(){
        return instance;
    }

    @Override
    public ExecuteResult streamMerge(StreamMergeContext streamMergeContext, Collection<ExecuteResult> parallelResults) {
        ExecuteResult executeResult = parallelResults.iterator().next();
        if(executeResult instanceof QueryExecuteResult){
            List<StreamResult> streamResults = ArrayUtil.select(parallelResults, (o, i) -> ((QueryExecuteResult) o).getStreamResult());
            MultiOrderStreamMergeResult multiOrderStreamMergeResult = new MultiOrderStreamMergeResult(streamMergeContext, streamResults);
            return new QueryExecuteResult(multiOrderStreamMergeResult);

        }else{
            long recordsAffected=0;
            for (ExecuteResult parallelResult : parallelResults) {
                AffectedRowsExecuteResult affectedRowsExecuteResult = (AffectedRowsExecuteResult) parallelResult;
                recordsAffected+=affectedRowsExecuteResult.getRows();
            }
            return new AffectedRowsExecuteResult(recordsAffected);
        }
    }

    @Override
    public void inMemoryMerge(StreamMergeContext streamMergeContext, Collection<ExecuteResult> beforeInMemoryResults, Collection<ExecuteResult> parallelResults) {
        beforeInMemoryResults.addAll(parallelResults);
    }
}