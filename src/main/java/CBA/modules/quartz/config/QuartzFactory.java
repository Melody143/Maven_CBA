package CBA.modules.quartz.config;

import CBA.modules.quartz.constant.enums.QuartzEnum;
import CBA.modules.quartz.entity.SysJob;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <p>
 * 动态任务工厂
 */
@Slf4j
@DisallowConcurrentExecution
public class QuartzFactory implements Job {

    @Autowired
    private QuartzInvokeFactory quartzInvokeFactory;


    @Override
    @SneakyThrows
    public void execute(JobExecutionContext jobExecutionContext) {
        SysJob sysJob = (SysJob) jobExecutionContext.getMergedJobDataMap().get(QuartzEnum.SCHEDULE_JOB_KEY.getType());
        quartzInvokeFactory.init(sysJob, jobExecutionContext.getTrigger());
    }
}
