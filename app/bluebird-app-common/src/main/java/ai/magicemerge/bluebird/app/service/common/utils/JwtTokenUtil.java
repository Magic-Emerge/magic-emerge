package ai.magicemerge.bluebird.app.service.common.utils;

import ai.magicemerge.bluebird.app.service.common.constant.ResCode;
import ai.magicemerge.bluebird.app.service.common.exceptions.AuthenticationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
public class JwtTokenUtil {

	public static final String AUTH_HEADER_KEY = "Authorization";

	public static final String WORKSPACE_ID = "Workspaceid";

	public static final String TOKEN_PREFIX = "Bearer ";

	//签名密钥
	public static final String KEY = "q3t6w9z$C&F)J@NcQfTjWnZr4u7x";

	//有效期默认为 1天
	public static final Long EXPIRATION_TIME = 1L;


	/**
	 * 创建token
	 *
	 * @param content
	 * @return
	 */
	public static String createToken(String content) {
		return TOKEN_PREFIX + JWT.create()
				.withSubject(content)
				.withExpiresAt(Instant.now().plus(EXPIRATION_TIME, ChronoUnit.DAYS))
				.sign(Algorithm.HMAC512(KEY));
	}



	/**
	 * 验证token
	 * @param token
	 */
	public static String verifyToken(String token) throws Exception {
		try {
			DecodedJWT verify = JWT.require(Algorithm.HMAC512(KEY))
					.build()
					.verify(token.replace(TOKEN_PREFIX, ""));
			log.info("Token过期时间 => {}", verify.getExpiresAt());
			return verify.getSubject();
		} catch (TokenExpiredException e){
			throw new AuthenticationException(ResCode.NEED_LOGIN.getCode(), "token已失效，请重新登录");
		} catch (JWTVerificationException e) {
			throw new AuthenticationException(ResCode.NEED_LOGIN.getCode(), "token验证失败");
		}
	}







}
