package com.jeeplus.qc.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.redis.RedisUtils;
import com.jeeplus.common.utils.RequestUtils;
import com.jeeplus.common.utils.net.HttpUtil;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.DictUtils;

import com.jeeplus.sys.utils.UserUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class GetBpToken {
    public static final String CACHE_NAME_BP_TOKEN = "user:cache:bpToken";
    public static final String SYS_BP_WEB_JOB_USER_INFO = "sys_bp_webjob_user_info";
    public static final String BOWKER_BASEPORTAL_TOKEN = "bowker_baseportal_token";
    public static final String BOWKER_BASEPORTAL = "bowker_baseportal";
    public static final String TOKEN = "token";
    public static final String TOKEN_TYPE = "tokenType";
    public static final String REFRESH_TOKEN = "refreshToken";
    public static final String EXPIRE_TIME = "expireTime";
    public GetBpToken() {
    }

    private byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        if (salt != null) {
            digest.update(salt);
        }

        byte[] result = digest.digest(input);

        for(int i = 1; i < iterations; ++i) {
            digest.reset();
            result = digest.digest(result);
        }

        return result;
    }

    private String sha256Hex(String source) throws NoSuchAlgorithmException {
        return Hex.encodeHexString(this.digest(source.getBytes(StandardCharsets.UTF_8), "SHA-256", (byte[])null, 1));
    }

    /**
     * 清空缓存token
     */
    public void clearToken(){
        UserDTO userDTO = UserUtils.getCurrentUserDTO();
        RedisUtils.getInstance().delete(CACHE_NAME_BP_TOKEN,userDTO.getId());
    }

    /**
     * 把token存储在缓存中
     * @param token
     */
    public void setToken(String token){
        setToken(token,UserUtils.getCurrentUserDTO());
    }
    public void setToken(String token,UserDTO userDTO){
        if(StringUtils.isNotBlank(token)){
            this.clearToken();
            RedisUtils.getInstance().set(CACHE_NAME_BP_TOKEN,userDTO.getId(), token);
        }
    }

    /**
     * 获取token
     * 1、获取缓存token：app登录token & portal登录token
     * 2、获取portal登录token
     * 3、获取默认账号密码登录token
     * @return
     */
    public String getToken(){
        return getToken(BOWKER_BASEPORTAL_TOKEN);
    }
    public String getToken(String tokenKey){
        UserDTO userDTO = UserUtils.getCurrentUserDTO();
        if(null != userDTO && StringUtils.isNotBlank(userDTO.getId())){
            String token = (String)RedisUtils.getInstance().get(CACHE_NAME_BP_TOKEN, userDTO.getId());
            if(StringUtils.isBlank(token)){
                token = getTokenByHeader(RequestUtils.getRequest(),tokenKey);
            }
            setToken(token);
            return  token;
        }
        return getTokenByDefaultUser();
    }
    public String getTokenByHeader(HttpServletRequest request){
        return getTokenByHeader(request,TOKEN);
    }
    public String getTokenByHeader(HttpServletRequest request,String tokenKey){
        String token0 = request.getParameter(tokenKey);
        String token1 = request.getHeader(tokenKey);
        String token2 = CookieUtils.getCookie(request, tokenKey);
        if(StringUtils.isNotBlank(token0)){
            return token0;
        }
        if(StringUtils.isNotBlank(token1)){
            return token1;
        }
        if(StringUtils.isNotBlank(token2)){
            return token2;
        }
        return null;
    }
    public String getTokenByDefaultUser(){
        String username = DictUtils.getDictValue("username", SYS_BP_WEB_JOB_USER_INFO, "syscosting");
        String password = DictUtils.getDictValue("password", SYS_BP_WEB_JOB_USER_INFO, "zm@2021");
        return getToken(username, password);
    }
    /**
     * 获取账号密码登录token
     * @param username
     * @param password
     * @return
     */
    public String getToken(String username,String password) {
        String url = DictUtils.getDictValue("url", SYS_BP_WEB_JOB_USER_INFO, "http://8.218.91.144:8080/baseportal/api/authorize");
        try {
            Map<String, String> parameter = this.getloginParameter(username, password);
            String thhpBady = HttpUtil.post(url, parameter, new HashMap());
            JSONObject loginResult = JSON.parseObject(thhpBady);
            if ("0".equals(loginResult.getString("returnCode"))) {
                String token = loginResult.getString(TOKEN);
                UserDTO userDTO = UserUtils.getByLoginName (username );
                if(userDTO !=null){
                    this.setToken(token,userDTO);
                }
                return token;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public Map<String, String> getloginParameter(String username, String password) throws NoSuchAlgorithmException {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateString = formatter.format(currentTime);
        String sign = this.sha256Hex(username + this.sha256Hex(password) + dateString + username);
        Map<String, String> parameter = new HashMap<>();
        parameter.put("userName", username);
        parameter.put("timestamp", dateString);
        parameter.put("sign", sign);
        return parameter;
    }

}

