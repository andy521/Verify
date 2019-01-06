# verify

#### 项目介绍

**专门为中小软件提供的开源JAVA网络验证系统**

**目前的功能主要有：注册，登陆，绑定卡密，绑定机器，取软件版本，软件留言，修改密码，取卡密期限，rsa算法加密登陆，取软件信息**

**开放接口全部进行了严格验证**

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

#### 安装教程

1. 运行环境必须有redis和rabbit mq才可以运行

#### 使用说明

1. 系统有个邮箱的概念，当别人发送软件留言的时候，可以通知到我们设置的邮箱号上面,我们设置的邮箱号是接收端，发送端是自己设置账号，重点来了
邮箱号的密码，不是那个QQ密码，QQ邮箱要开启POP3/SMTP服务，然后拿到授权码，那个授权码就是密码了

2. 系统对接了百度地图开放平台，用于拿到用户的ip地理位置
百度开放平台：http://lbsyun.baidu.com/apiconsole/key
进去创建应用，然后拿到那个ak，进入系统后台，设置下就可以了

3. 系统管理的账户密码自行到mysql 数据库中自行录入

4. builder项目运行主类就可以生成数据库了，记得先创建数据库，然后修改下application.properties配置，再运行

5. 易语言sdk打开可能会报找不到map支持库错误，请看此文章:https://bbs.125.la/forum.php?mod=viewthread&tid=14149200&highlight=map

6. 项目使用了lombok，请先用idea安好lombok插件

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

![描述](https://open-source-orange.oss-cn-hangzhou.aliyuncs.com/%E7%A0%81%E4%BA%91/verify-%E7%BD%91%E7%BB%9C%E9%AA%8C%E8%AF%81/Snipaste_2019-01-06_19-33-53.png)

![描述](https://open-source-orange.oss-cn-hangzhou.aliyuncs.com/%E7%A0%81%E4%BA%91/verify-%E7%BD%91%E7%BB%9C%E9%AA%8C%E8%AF%81/Snipaste_2019-01-06_19-35-12.png)
