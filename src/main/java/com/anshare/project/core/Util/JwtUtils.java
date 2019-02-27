package com.anshare.project.core.Util;

import com.anshare.project.configurer.JwtConfig;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
* @ClassName JwtUtils
* @Description JwtUtils
* @Author wukunfan
* @Date 2018/11/12 17:31
* @UpdateUser:
* @UpdateDate:     2018/11/12 17:31
* @UpdateRemark:   修改内容
* @Version 1.0
**/
public class JwtUtils {
	//拿到jwt中暂存的信息
	public static String[] GetDetails(){
		String[] auth = ((String[]) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		return auth;
	}

	/**
	 * 密钥
	 */
	private static final String SECRET = "anshare.qdlq#";
	/**
	 * 默认字段key:exp
	 */
	private static final String EXP = "exp";
	/**
	 * 默认字段key:payload
	 */
	private static final String PAYLOAD = "payload";

	/**
	 * 加密
	 * 
	 * @param object
	 *            加密数据
	 * @param maxTime
	 *            有效期（毫秒数）
	 * @param <T>
	 * @return
	 */
	public static <T> String encode(T object, long maxTime) {
		try {
			final JWTSigner signer = new JWTSigner(SECRET);
			final Map<String, Object> data = new HashMap<>(10);
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = objectMapper.writeValueAsString(object);
			data.put(PAYLOAD, jsonString);
			data.put(EXP, System.currentTimeMillis() + maxTime);
			return signer.sign(data);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 数据解密
	 * 
	 * @param jwt
	 *            解密数据
	 * @param tClass
	 *            解密类型
	 * @param <T>
	 * @return
	 */
	public static <T> T decode(String jwt, Class<T> tClass) {
		final JWTVerifier jwtVerifier = new JWTVerifier(SECRET);
		try {
			final Map<String, Object> data = jwtVerifier.verify(jwt);
			// 判断数据是否超时或者符合标准
			if (data.containsKey(EXP) && data.containsKey(PAYLOAD)) {
				long exp = (long) data.get(EXP);
				long currentTimeMillis = System.currentTimeMillis();
				if (exp > currentTimeMillis) {
					String json = (String) data.get(PAYLOAD);
					ObjectMapper objectMapper = new ObjectMapper();
					return objectMapper.readValue(json, tClass);
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public static  String parseJWT(HttpServletRequest request) {
		String jwt = request.getHeader("auth");
		Claims claims = Jwts.parser()
				.setSigningKey(JwtConfig.SIGNING_KEY)
				.parseClaimsJws(jwt).getBody();
		System.out.println("ID: " + claims.getId());
		System.out.println("Subject: " + claims.getSubject());
		System.out.println("Issuer: " + claims.getIssuer());
		System.out.println("Expiration: " + claims.getExpiration());
		return claims.getSubject();
	}

	public static class User {
		String id;
		String name;
		String password;

		public User() {
		}

		public User(String id, String name, String password) {
			this.id = id;
			this.name = name;
			this.password = password;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		@Override
		public String toString() {
			return "User{" + "id=" + id + ", name='" + name + '\'' + ", password='" + password + '\'' + '}';
		}
	}

}