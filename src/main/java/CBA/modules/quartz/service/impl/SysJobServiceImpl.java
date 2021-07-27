package CBA.modules.quartz.service.impl;

import CBA.modules.quartz.entity.SysJob;
import CBA.modules.quartz.mapper.SysJobMapper;
import CBA.modules.quartz.service.SysJobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 定时任务调度表
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements SysJobService {

}
