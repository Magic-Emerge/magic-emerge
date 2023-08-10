package ai.magicemerge.bluebird.app.service.common.utils;

import ai.magicemerge.bluebird.app.service.common.UserToken;
import com.alibaba.fastjson.JSONObject;


public class WebContextUtil {

	//本地线程缓存token
	private static final ThreadLocal<String> userLogin = new ThreadLocal<>();


	/**
	 * 设置token信息
	 * @param content
	 */
	public static void setUserToken(String content){
		userLogin.set(content);
	}

	/**
	 * 获取token信息
	 * @return
	 */
	public static UserToken getUserSession(){
		UserToken userToken = new UserToken();
		if(userLogin.get() != null){
			userToken = JSONObject.parseObject(userLogin.get() , UserToken.class);
			return userToken;
		}
		return userToken;
	}


	/**
	 * 移除token信息
	 * @return
	 */
	public static void removeUserToken(){
		if(userLogin.get() != null){
			userLogin.remove();
		}
	}


}
