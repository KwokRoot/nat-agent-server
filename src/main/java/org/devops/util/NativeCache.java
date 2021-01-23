package org.devops.util;

/**
 * @description:
 * @author: Kwok
 * @date: 2020/11/20
 */
public class NativeCache {
    /**
     * 代理默认端口不要与 Tomcat 监听端口一致，否则访问根路径 `/` 会出现循环调用！
     */
    public static String agentHost = "127.0.0.1:8080";

}
