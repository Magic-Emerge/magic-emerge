package ai.magicemerge.bluebird.app.dal.mapper.ext;

import ai.magicemerge.bluebird.app.dal.model.User;
import org.apache.ibatis.annotations.Param;

public interface ExtUserMapper {
    boolean updateUserInfoByUserId(@Param("userId") Long userId,
                                   @Param("username") String username,
                                   @Param("avatar") String avatar);
    User queryUserByUserId(@Param("userId") Long userId);

    User queryUserByKey(@Param("key") String key,@Param("value") String value);
}
