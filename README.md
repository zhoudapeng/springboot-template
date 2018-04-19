这是springboot的模版工程
快速体验：直接运行com.zdp.springboot.template.bootstrap.Bootstrap类的main方法即可
功能：
1.自动参数检测&登录检测
2.全局异常控制&返回值拼装
3.直接在Controller方法中获取当前登录用户对象，开发者无需关注当前关注者如何获取
4.支持MDC日志，一次请求中的日志可以通过一个traceId查到所有相关日志
5.返回值为json格式，且将通讯状态封装到了bstatus域，真实数据放入data域
