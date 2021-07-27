package CBA.modules.sys.model.parm;

import CBA.modules.framework.model.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class SysRoleMenuParm extends BaseEntity {

    private List<String> menuIds;
}
