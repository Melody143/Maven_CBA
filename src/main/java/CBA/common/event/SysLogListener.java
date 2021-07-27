package CBA.common.event;

import CBA.modules.sys.model.entity.SysLog;
import CBA.modules.sys.service.SysLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author jinghong
 */
@Slf4j
@AllArgsConstructor
@Component
public class SysLogListener {
    private final SysLogService logService;

    /**
     * 异步保存日志
     *
     * @param event
     */
    @Async
    @Order
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent event) {
        SysLog log = event.getLog();
        logService.save(log);
    }
}