package CBA.security.modules.sys.model.dto;

import CBA.security.modules.framework.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TreeNode extends BaseEntity {

    protected String pid;

    protected List<TreeNode> subMenus = new ArrayList<>();
}
