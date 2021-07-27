package CBA.modules.sys.mapper;

import CBA.modules.framework.model.ZtreeModel;
import CBA.modules.sys.model.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 角色
 *
 * @author jinghong 2019/10/17
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 角色拥有的菜单
     *
     * @param id 角色ID
     * @return
     */
    List<ZtreeModel> authTree(String id);
}
