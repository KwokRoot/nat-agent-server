# nat-agent-server
------
HttpGet、HttpPost、HttpDelete Agent .

1.设置安全访问IP白名单，系统`system.properties` 中的 `ip_white_list`值。
2.获取代理路径：GET  IP:port/settings。
  设置代理路径：POST IP:port/settings。
3.可代理 GET、POST、DELETE 请求。
