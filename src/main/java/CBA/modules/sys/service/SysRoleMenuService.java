package CBA.modules.sys.service;

import CBA.modules.sys.model.entity.SysRoleMenu;
import CBA.modules.sys.model.parm.SysRoleMenuParm;
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
