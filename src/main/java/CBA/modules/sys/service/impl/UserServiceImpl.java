package CBA.modules.sys.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import CBA.common.config.JesimsProperties;
import CBA.common.constant.SecurityConstants;
import CBA.common.utils.SpringContextHolder;
import CBA.common.utils.TreeUtils;
import CBA.modules.sys.mapper.UserMapper;
import CBA.modules.sys.model.dto.SysUserMenu;
import CBA.modules.sys.model.entity.SysUser;
import CBA.modules.sys.model.parm.SysUserParm;
import CBA.modules.sys.service.SysUserRoleService;
import CBA.modules.sys.service.UserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jinghong 2019/10/17
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final SysUserRoleService userRoleService;
    private final JesimsProperties properties;
    private final ClientDetailsService clientDetailsService;

    @Override
    public List<SysUserMenu> userMenus(@NonNull String uid) {
        List<SysUserMenu> menus = baseMapper.userMenus(uid);
        List<SysUserMenu> collect = menus.stream()
                .filter(e -> !parentIdNotNull(e.getPid()))
                .map(e -> TreeUtils.findChildren(e, menus))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void install(SysUserParm user) {
        SysUser sysUser = user.convert(SysUser.class);
        // ??????????????????
        if (StrUtil.isBlank(sysUser.getPassword())) {
            sysUser.setPassword(passwordEncoder.encode(SecurityConstants.DEFAULT_PASSWORD));
        } else {
            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        }
        super.save(sysUser);

        userRoleService.saveUserRole(sysUser.getId(), user.getRoleId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(SysUserParm user) {
        SysUser sysUser = user.convert(SysUser.class);
        // ???????????????
        if (StrUtil.isNotBlank(sysUser.getPassword())) {
            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        } else {
            sysUser.setPassword(null);
        }
        boolean b = super.updateById(sysUser);

        Assert.isTrue(b, "??????????????????");

        userRoleService.saveUserRole(sysUser.getId(), user.getRoleId());
    }

    @Override
    public List<String> userAuthority(String uid) {
        return baseMapper.userAuthority(uid);
    }


    private OAuth2AccessToken getOAuth2AccessToken(UserDetails user) throws RuntimeException {
        String clientId = properties.getSecurity().getSocialClientId();
        ClientDetails clientDetails;
        try {
            clientDetails = clientDetailsService.loadClientByClientId(clientId);
        } catch (Exception e) {
            throw new RuntimeException("??????????????????????????????Client??????");
        }

        if (clientDetails == null) {
            throw new RuntimeException("?????????????????????????????????Client");
        }

        String authorizedGrantTypes = StrUtil.join(StrUtil.COMMA, clientDetails.getAuthorizedGrantTypes());
        TokenRequest tokenRequest = new TokenRequest(new HashMap<>(5), clientId, clientDetails.getScope(), authorizedGrantTypes);
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
        AuthorizationServerTokenServices authorizationServerTokenServices = SpringContextHolder.getBean(AuthorizationServerTokenServices.class);
        OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        return oAuth2AccessToken;
    }

    private boolean parentIdNotNull(String parentId) {
        return Objects.nonNull(parentId) && !StrUtil.equals(parentId, SecurityConstants.PARENT_ID);
    }

    /**
     * ?????????????????????????????????
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = super.getOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUsername, username));
        if (user == null) {
            throw new UsernameNotFoundException("????????????????????????");
        }
        return setUserAuthorities(user);
    }

    private UserDetails setUserAuthorities(SysUser user) throws UsernameNotFoundException {
        // ??????
        List<String> authorities = this.userAuthority(user.getId());
        Set<SimpleGrantedAuthority> authoritiesSet = Sets.newHashSet();
        authorities.forEach(authority -> {
            String[] split = StrUtil.split(authority, StrUtil.COMMA);
            Arrays.stream(split).filter(StrUtil::isNotBlank).map(SimpleGrantedAuthority::new).forEach(authoritiesSet::add);
        });
        user.setAuthorities(authoritiesSet);
        return user;
    }
}
