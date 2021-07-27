package CBA.security.modules.sys.service;

import CBA.security.modules.sys.model.entity.SysRoleMenu;
import CBA.security.modules.sys.model.parm.SysRoleMenuParm;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author jinghong 2019/10/17
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 保存角色和菜单关系
     *
     * @param parm
     */
    void saveRoleMenu(SysRoleMenuParm parm);
}
