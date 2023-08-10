package ai.magicemerge.bluebird.app.service.common.utils;

import ai.magicemerge.bluebird.app.service.common.enums.SysUserRoleEnum;

public class AuthUtils {

	public static Boolean isUser() {
		String userRole = WebContextUtil.getUserSession().getUserRole();
		return SysUserRoleEnum.USER.name().equals(userRole);
	}

	public static Boolean isAdmin() {
		String userRole = WebContextUtil.getUserSession().getUserRole();
		return SysUserRoleEnum.ADMIN.name().equals(userRole);
	}

	public static Boolean isSuperAdmin() {
		String userRole = WebContextUtil.getUserSession().getUserRole();
		return SysUserRoleEnum.SUPER_ADMIN.name().equals(userRole);
	}

}
