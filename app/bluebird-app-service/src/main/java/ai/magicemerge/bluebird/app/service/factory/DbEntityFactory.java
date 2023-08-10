package ai.magicemerge.bluebird.app.service.factory;

import ai.magicemerge.bluebird.app.dal.model.User;
import ai.magicemerge.bluebird.app.service.common.utils.KeyUtil;
import cn.hutool.crypto.SecureUtil;
import lombok.Data;

@Data
public class DbEntityFactory {

    public static User buildUser(String wechat, String email, String phone, String password) {
        User user = new User();
        user.setId(KeyUtil.generateUsername());
        user.setUsername(KeyUtil.generateUsername());
        user.setOpenUserId(KeyUtil.generateUsername());
        user.setIsDeleted(false);
        user.setIsActive(true);
        user.setWechat(wechat);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        return user;
    }
}
