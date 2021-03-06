package com.anshare.project.configurer.filter;

import com.anshare.project.configurer.JwtConfig;
import com.anshare.project.core.Util.RedisUtil;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 自定义JWT认证过滤器
 * 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter{
    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    @Resource
	private RedisUtil redisUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("auth");

        if (redisUtil == null) {
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            redisUtil = (RedisUtil) factory.getBean("redisUtil");
        }

        if (header == null) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request,response);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
        String[] test = ((String[]) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        logger.info(test[0]);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
        if (redisUtil == null) {
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            redisUtil = (RedisUtil) factory.getBean("redisUtil");
        }

        long start = System.currentTimeMillis();
        String token = request.getHeader("auth");
        if (token == null || token.isEmpty()) {
            throw new SecurityException("Token为空", new Exception());
        }
        logger.info("GET AUTH");

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(JwtConfig.SIGNING_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println("ID: " + claims.getId());
            System.out.println("Subject: " + claims.getSubject());
            System.out.println("Issuer: " + claims.getIssuer());
            System.out.println("Expiration: " + claims.getExpiration());
            String[] user = claims.getSubject().split(",");
            long end = System.currentTimeMillis();
            logger.info("执行时间: {}", (end - start) + " 毫秒");

            if (user != null) {
                //如果redis中不存在该token
                if (redisUtil.getStr(user[0]) == null) {
                    throw new SecurityException("Token已失效");
                } else {
                    ArrayList<GrantedAuthority> authorities = new ArrayList<>();
                    return new UsernamePasswordAuthenticationToken(user, null, authorities);
                }
            }
        } catch (ExpiredJwtException e) {
            logger.error("Token已过期: {} " + e);
            throw new SecurityException("Token已过期");
        } catch (UnsupportedJwtException e) {
            logger.error("Token格式错误: {} " + e);
            throw new SecurityException("Token格式错误");
        } catch (MalformedJwtException e) {
            logger.error("Token没有被正确构造: {} " + e);
            throw new SecurityException("Token没有被正确构造");
        } catch (SignatureException e) {
            logger.error("签名失败: {} " + e);
            throw new SecurityException("签名失败");
        } catch (IllegalArgumentException e) {
            logger.error("非法参数异常: {} " + e);
            throw new SecurityException("非法参数异常");
        }
        return null;
    }
}
