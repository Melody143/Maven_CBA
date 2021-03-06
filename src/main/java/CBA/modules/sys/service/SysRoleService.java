package CBA.modules.sys.service;

import CBA.modules.framework.model.ZtreeModel;
import CBA.modules.sys.model.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author jinghong 2019/10/17
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 角色拥有的菜单
     *
     * @param id 角色ID
     * @return
     */
    List<ZtreeModel> authTree(String id);
}
