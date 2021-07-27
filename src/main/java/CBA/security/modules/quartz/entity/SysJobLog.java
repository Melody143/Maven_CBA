package CBA.security.modules.quartz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 定时任务执行日志表
 *
 * @author jjh
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_job_log")
public class SysJobLog extends Model<SysJobLog> {
    private static final long serialVersionUID = 1L;

    /**
     * 任务日志ID
     */
    @TableId(value = "job_log_id", type = IdType.AUTO)
    private Integer jobLogId;
    /**
     * 任务id
     */
    private Integer jobId;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务组名
     */
    private String jobGroup;
    /**
     * 组内执行顺利，值越大执行优先级越高，最大值9，最小值1
     */
    private String jobOrder;
    /**
     * 1、java类;2、spring bean名称;3、rest调用;4、jar调用;9其他
     */
    private String jobType;
    /**
     * job_type=3时，rest调用地址，仅支持post协议;job_type=4时，jar路径;其它值为空
     */
    private String executePath;
    /**
     * job_type=1时，类完整路径;job_type=2时，spring bean名称;其它值为空
     */
    private String className;
    /**
     * 任务方法
     */
    private String methodName;
    /**
     * 参数值
     */
    private String methodParamsValue;
    /**
     * cron执行表达式
     */
    private String cronExpression;
    /**
     * 日志信息
     */
    private String jobMessage;
    /**
     * 执行状态（0正常 1失败）
     */
    private String jobLogStatus;
    /**
     * 执行时间
     */
    private String executeTime;
    /**
     * 异常信息
     */
    private String exceptionInfo;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 租户id
     */
    private Integer tenantId;

}
