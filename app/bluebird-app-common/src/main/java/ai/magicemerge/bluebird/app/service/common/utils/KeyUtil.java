package ai.magicemerge.bluebird.app.service.common.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.SecureUtil;
import org.springframework.util.StringUtils;

public class KeyUtil {
    /**
     * 手机验证码 key
     *
     * @param phoneNumber 手机号
     * @return key
     */
    public static String generateKeyOfPhoneNumberCode(String phoneNumber) {
        return SecureUtil.md5().digestHex("phoneNumber-" + phoneNumber);
    }

    public static String generateKeyOfEmailCode(String email) {
        return SecureUtil.md5().digestHex("email-" + email);
    }

    /**
     * 生成用户名
     *
     * @return username
     */
    public static String generateUsername() {
        return StringUtils.replace(UUID.fastUUID().toString(), "-", "");
    }

}
