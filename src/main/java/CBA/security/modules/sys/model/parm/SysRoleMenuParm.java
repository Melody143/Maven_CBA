package CBA.security.modules.sys.model.parm;

import CBA.security.modules.framework.model.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class SysRoleMenuParm extends BaseEntity {

    private List<String> menuIds;
}
