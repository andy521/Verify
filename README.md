# verify 1.5

#### 项目介绍

**专门为中小软件提供的开源JAVA网络验证系统**

**目前的功能主要有：注册，登陆，绑定卡密，绑定机器，取软件版本，软件留言，修改密码，取卡密期限，rsa算法加密登陆，取软件信息**

**开放接口如下:**
1. 获取RSA公钥
2. 获取注册验证码
3. 用户-注册
4. 用户-登陆
5. 用户-绑定卡密
6. 用户-绑定机器
7. 用户-修改密码
8. 获取软件信息
9. 用户-提交留言(反馈)
10. 用户-查看卡密什么时候开始用的，什么时候结束的
11. 获取软件版本信息
12. 系统管理员-登陆
13. 系统管理员-退出

**后台页面6大模块**
1. 接口管理
2. 软件管理
3. 日志管理
4. 充值卡管理
5. 用户管理
6. 配置管理

**系统亮点：**
* 开放接口全部可以后台配置，进行ip限流配置管理，限制接口在 xx 分钟内容只能访问 xx 次
* 开放接口全部进行了严格认证处理
* 用户登陆注册日志看得见！图表展示，表格数据展示
* 软件用户全可控，系统后台配置一下就行
* 开源、稳定、安全的软件加密授权系统，让软件开发更简单、迅速

#### 软件架构
**项目采用spring boot + vue前后端分离架构开发，Idea ide开发**

1. admin(为admin-web提供服务，service,mapper层处理)
2. admin-web(提供给外部访问接口，controller)
3. api(提供给各个服务的vo,bean等等使用)
4. commom(自写的工具包)
5. buider(数据库和java bean一键生成的项目)
6. admin-web-page(后台管理页面(vue编写))
7. sdk(各大语言的sdk(1.易语言sdk))

**主要采用的技术有:**

1. dubbo(项目rpc通信)
2. mybatis plus(mapper访问层)
3. hibernate(用于生成数据库和验证vo)
4. shiro(登陆验证)
5. 前端采用(vue - element ui)
6. hutool(工具包)
7. redis(用作缓存)
8. mysql
9. rabbit mq队列(用于邮箱发送邮件)
10. mapstruct(对象之间的转换)
11. lombok(用于类自动生成get set方法)
12. zookeeper(用于dubbo和分布锁使用)

#### 版本日志

* 1.5 优化代码 增加运行日志，增加开放接口后台管理，增强软件日志，优化易语言demo,优化后台页面增加loading
* 1.3 修复易语言 demo和sdk,修复sql语句,增加批量删除卡密
* 1.2 修复充值，易语言demo更新，修复vo验证字段,修复RspHandleAspect(优化ip访问控制，限制接口在 xx 分钟内容只能访问 xx 次),优化后台页面
* 1.1 增加注册验证码，注册增加分布式锁，修复大量bug

#### 使用说明

1. 系统有个邮箱的概念，当别人发送软件留言的时候，可以通知到我们设置的邮箱号上面,我们设置的邮箱号是接收端，发送端是自己设置账号，重点来了
邮箱号的密码，不是那个QQ密码，QQ邮箱要开启POP3/SMTP服务，然后拿到授权码，那个授权码就是密码了

2. 系统对接了百度地图开放平台，用于拿到用户的ip地理位置
百度开放平台：http://lbsyun.baidu.com/apiconsole/key
进去创建应用，然后拿到那个ak，进入系统后台，设置下就可以了

3. 系统管理的账户密码自行到mysql 数据库中自行录入

4. builder项目运行主类就可以生成数据库了，记得先创建数据库，然后修改下application.properties配置，再运行

5. 项目使用了lombok，请先用idea安好lombok插件

#### 图片参考

1. 后台系统页面

![描述](https://open-source-orange.oss-cn-hangzhou.aliyuncs.com/%E7%A0%81%E4%BA%91/verify-%E7%BD%91%E7%BB%9C%E9%AA%8C%E8%AF%81/Snipaste_2018-12-27_23-44-43.png)

![描述](https://open-source-orange.oss-cn-hangzhou.aliyuncs.com/%E7%A0%81%E4%BA%91/verify-%E7%BD%91%E7%BB%9C%E9%AA%8C%E8%AF%81/Snipaste_2018-12-27_23-45-09.png)

![描述](https://open-source-orange.oss-cn-hangzhou.aliyuncs.com/%E7%A0%81%E4%BA%91/verify-%E7%BD%91%E7%BB%9C%E9%AA%8C%E8%AF%81/Snipaste_2018-12-27_23-45-25.png)

![描述](https://open-source-orange.oss-cn-hangzhou.aliyuncs.com/%E7%A0%81%E4%BA%91/verify-%E7%BD%91%E7%BB%9C%E9%AA%8C%E8%AF%81/Snipaste_2018-12-27_23-45-45.png)

![描述](https://open-source-orange.oss-cn-hangzhou.aliyuncs.com/%E7%A0%81%E4%BA%91/verify-%E7%BD%91%E7%BB%9C%E9%AA%8C%E8%AF%81/Snipaste_2018-12-27_23-45-57.png)

![描述](https://open-source-orange.oss-cn-hangzhou.aliyuncs.com/%E7%A0%81%E4%BA%91/verify-%E7%BD%91%E7%BB%9C%E9%AA%8C%E8%AF%81/Snipaste_2018-12-27_23-46-06.png)

![描述](https://open-source-orange.oss-cn-hangzhou.aliyuncs.com/%E7%A0%81%E4%BA%91/verify-%E7%BD%91%E7%BB%9C%E9%AA%8C%E8%AF%81/Snipaste_2018-12-27_23-46-18.png)

2. 易语言sdk

![描述](https://open-source-orange.oss-cn-hangzhou.aliyuncs.com/%E7%A0%81%E4%BA%91/verify-%E7%BD%91%E7%BB%9C%E9%AA%8C%E8%AF%81/Snipaste_2019-01-02_22-15-10.png)

3. 易语言demo

![描述](https://open-source-orange.oss-cn-hangzhou.aliyuncs.com/%E7%A0%81%E4%BA%91/verify-%E7%BD%91%E7%BB%9C%E9%AA%8C%E8%AF%81/Snipaste_2019-01-17_17-45-21.png)
![描述](https://open-source-orange.oss-cn-hangzhou.aliyuncs.com/%E7%A0%81%E4%BA%91/verify-%E7%BD%91%E7%BB%9C%E9%AA%8C%E8%AF%81/Snipaste_2019-01-17_17-45-50.png)
