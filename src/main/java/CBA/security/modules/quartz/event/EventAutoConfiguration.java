package CBA.security.modules.quartz.event;


import CBA.security.modules.quartz.config.QuartzInvokeFactory;
import CBA.security.modules.quartz.service.SysJobLogService;
import CBA.security.modules.quartz.util.TaskInvokUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <p>
 * 多线程自动配置
 *
 * @author jinghong
 */
@EnableAsync
@Configuration
@ConditionalOnWebApplication
public class EventAutoConfiguration {

    @Autowired
    private TaskInvokUtil taskInvokUtil;

    @Autowired
    private SysJobLogService sysJobLogService;

    @Bean
    public SysJobListener sysJobListener() {
        return new SysJobListener(taskInvokUtil);
    }

    @Bean
    public QuartzInvokeFactory quartzInvokeFactory(ApplicationEventPublisher publisher) {
        return new QuartzInvokeFactory(publisher);
    }

    @Bean
    public SysJobLogListener sysJobLogListener() {
        return new SysJobLogListener(sysJobLogService);
    }

    @Bean
    public TaskInvokUtil taskInvokUtil(ApplicationEventPublisher publisher) {
        return new TaskInvokUtil(publisher);
    }

}
