package CBA.modules.quartz.util;


import CBA.modules.quartz.entity.SysJob;
import CBA.modules.quartz.exception.TaskException;

/**
 * 定时任务反射实现接口类
 *
 * @author
 */
public interface ITaskInvok {

    /**
     * 执行反射方法
     *
     * @param sysJob 配置类
     * @throws TaskException
     */
    void invokMethod(SysJob sysJob) throws TaskException;
}
