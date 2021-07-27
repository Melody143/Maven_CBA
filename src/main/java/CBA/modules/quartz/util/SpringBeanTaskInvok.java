package CBA.modules.quartz.util;

import cn.hutool.core.util.StrUtil;
import CBA.common.utils.SpringContextHolder;
import CBA.modules.quartz.constant.enums.QuartzEnum;
import CBA.modules.quartz.entity.SysJob;
import CBA.modules.quartz.exception.TaskException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * 定时任务spring bean反射实现
 *
 * @author
 */
@Component("springBeanTaskInvok")
@Slf4j
public class SpringBeanTaskInvok implements ITaskInvok {
    @Override
    public void invokMethod(SysJob sysJob) throws TaskException {
        Object target = null;
        Method method = null;
        Object returnValue = null;
        //通过Spring上下文去找 也有可能找不到
        target = SpringContextHolder.getBean(sysJob.getClassName());
        try {
            if (StrUtil.isNotEmpty(sysJob.getMethodParamsValue())) {
                method = target.getClass().getDeclaredMethod(sysJob.getMethodName(), String.class);
                ReflectionUtils.makeAccessible(method);
                returnValue = method.invoke(target, sysJob.getMethodParamsValue());
            } else {
                method = target.getClass().getDeclaredMethod(sysJob.getMethodName());
                ReflectionUtils.makeAccessible(method);
                returnValue = method.invoke(target);
            }
            if (StrUtil.isEmpty(returnValue.toString()) || QuartzEnum.JOB_LOG_STATUS_FAIL.getType()
                    .equals(returnValue.toString())) {
                log.error("定时任务springBeanTaskInvok异常,执行任务：{}", sysJob.getClassName());
                throw new TaskException("定时任务springBeanTaskInvok业务执行失败,任务：" + sysJob.getClassName());
            }
        } catch (NoSuchMethodException e) {
            log.error("定时任务spring bean反射异常方法未找到", e);
            throw new TaskException("定时任务spring bean反射异常方法未找到,执行任务：" + sysJob.getClassName());
        } catch (IllegalAccessException e) {
            log.error("定时任务spring bean反射异常", e);
            throw new TaskException("定时任务spring bean反射异常,执行任务：" + sysJob.getClassName());
        } catch (InvocationTargetException e) {
            log.error("定时任务spring bean反射执行异常", e);
            throw new TaskException("定时任务spring bean反射执行异常,执行任务：" + sysJob.getClassName());
        }
    }
}
