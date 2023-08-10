package ai.magicemerge.bluebird.app.dal.utils;

import ai.magicemerge.bluebird.app.dal.mapper.UserMapper;
import ai.magicemerge.bluebird.app.dal.model.User;
import ai.magicemerge.bluebird.app.service.common.utils.SpringContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserUtils {

	private static final UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);


	public static Map<String, String> userIdNameMap() {
		List<User> users = userMapper.selectList(new LambdaQueryWrapper<User>()
				.eq(User::getIsDeleted, false)
				.eq(User::getIsActive, true));

		Map<String, String> userIdNameMap = users.stream()
				.collect(Collectors.toMap(User::getId, User::getUsername));
		return userIdNameMap;
	}



}
