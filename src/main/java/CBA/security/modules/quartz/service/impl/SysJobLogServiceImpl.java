package CBA.security.modules.quartz.service.impl;

import CBA.security.modules.quartz.entity.SysJobLog;
import CBA.security.modules.quartz.mapper.SysJobLogMapper;
import CBA.security.modules.quartz.service.SysJobLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 定时任务执行日志表
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLog> implements SysJobLogService {

}
