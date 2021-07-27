package CBA.security.modules.sys.service;

import CBA.security.modules.framework.model.ComboBoxEntity;
import CBA.security.modules.sys.model.entity.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author jinghong 2019/10/17
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * 根据类型获取
     *
     * @param type
     * @return
     */
    List<ComboBoxEntity<String, String>> type(String type);
}
