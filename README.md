![](./images/img.png)
<p align="center">
  <a href="./README.md">简体中文</a> | 
  <a href="./README_EN.md">English</a>
</p>

# What's New

👋 嘿！我是〇一
大家好，我是Magic Emerge的创始人，一个对产品有点“上头”的AI狂热分子。新技术？轻松驾驭！搞定它们就像喝咖啡一样简单，偶尔一不小心还能彻夜忘食，只为了把一个新想法变成现实。☕💻

作为一个对代码有着超乎常人的热情的程序员，我常常一头扎进技术的海洋里，直到把想法雕琢成精美的AI产品。而我坚信科技不该只是冷冰冰的工具，它应该是服务于人的，温暖的，充满善意的。

正因如此，我一直秉持“心存善念，学会克制”的信念。这不仅是个人生活的哲理，更是我设计产品的指导方针。AI再强大，也必须有边界；再智能，也不能失去人性。科技的力量应当为人类创造更美好的生活，而非替代或超越它。

有兴趣和我一起探讨AI如何改变世界吗？准备好笑声与思考的碰撞吧！😎

***我将ME以及更新到1.1.0版本，该版本将原本需要本地构建的jar包放在容器里，而且还支持初始化sql，真正做到一键运行。***

为什么要起ME，我希望自己在探索AI的能从自身出发


## Product Remote URL

[Magic Emerge 官方地址](https://app.magicemerge.ai/)

## Product View


![Alt Text](./images/product.gif)


## Features 
1. 基于[dify](https://github.com/langgenius/dify) API进行开发
2. 开箱即用的专业版的聊天对话应用
3. 可视化的应用数据分析
4. 支持代码、表格的聊天内容展示
5. 支持dify应用配置以及接口调用次数限制


## QuickStart

1. 配置app springboot应用的启动信息 在`app/bluebird-app-start`的资源目录下找到`application.yml`

2. 配置`application-prod.yml` 文件内容, 替换下列内容

```yaml

spring:
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/bluebird-app
    username: postgres
    password: bluebird123456
    

....

使用dify的cloud可以不替换


dify:
  openapi:
    url:
      https://api.dify.ai/
    version:
      v1/
    chat-path:
      chat-messages
    completion-path:
      completion-messages

....


腾讯短信平台

tencent:
  sms:
    secret-id: xxxxx
    secret-key: xxxxxxx
    endpoint: sms.tencentcloudapi.com
    timeout: 30000
    sdk-app-id: xxxx
    sign-name: xxxx
    template-id: xxxx


```
3. 进入app目录下使用maven构建jar包
```shell

# 进入app目录
cd app

# 使用下列命令构建jar包, 构建成功后返回根目录
mvn clean package -Dmaven.test.skip=true
```

4. 使用docker部署

```shell

# 进入docker目录
cd docker

# 使用下列命令一键启动
docker-compose up -d

```

5. 初始化数据库

```sql

-- 初始化超级管理员
INSERT INTO public.sys_user (id, username, auth_password, wechat, avatar_url, phone_number, user_role, is_deleted, email,
                             is_active)
VALUES ('1', 'admin', 'xBDZ8qiR1aP24rkFBo4ncw==', '', '', 'xxxxx', 'SUPER_ADMIN', false, 'admin@magicemerge.ai', true);

-- manager_id 必须是超级用户的id
INSERT INTO public.workspace (id, name, is_valid, remark, create_by, is_deleted,
                              is_public, manager_id)
VALUES (1, 'Magic Emerge''s 默认空间', true, '默认空间', '', false, false, '1');

-- user_id 必须是超级用户的id
INSERT INTO public.workspace_members (id, workspace_id, username, user_id, user_type, create_by, update_by, is_deleted,
                                      is_active)
VALUES (1, 1, 'admin', '1', 'OWNER', '', '', false, true);


-- 增加应用类型

INSERT INTO public.app_category (id, category_name, is_valid)
VALUES (1, '客服', true);
INSERT INTO public.app_category (id, category_name, is_valid)
VALUES (2, '电商', true);
INSERT INTO public.app_category (id, category_name, is_valid)
VALUES (3, '角色扮演', true);
INSERT INTO public.app_category (id, category_name, is_valid)
VALUES (5, '法律', true);
INSERT INTO public.app_category (id, category_name, is_valid)
VALUES (6, '家装/工业设计', true);
INSERT INTO public.app_category (id, category_name, is_valid)
VALUES (4, '小工具', true);
INSERT INTO public.app_category (id, category_name, is_valid)
VALUES (8, 'UI设计', true);
INSERT INTO public.app_category (id, category_name, is_valid)
VALUES (7, '法律法规', true);
INSERT INTO public.app_category (id, category_name, is_valid)
VALUES (9, '政策解读', true);
INSERT INTO public.app_category (id, category_name, is_valid)
VALUES (10, '经济财报', true);
INSERT INTO public.app_category (id, category_name, is_valid)
VALUES (11, 'QA问答', true);
INSERT INTO public.app_category (id, category_name, is_valid)
VALUES (12, '代码助手', true);
INSERT INTO public.app_category (id, category_name, is_valid)
VALUES (13, '教育', true);

```


## Backend stack

- Java
- spring boot
- mybatis-plus

## Frontend stack

- next.js
- taliwind css
- echarts.js


## Roadmap

### ME1.0 - MVP

- 实现基于Prompt的对话型精选应用、支持应用权限管理
- 支持应用监控、活跃用户数，消息数、消耗token数以及费用
- 支持默认工作空间以及成员管理、成员角色管理
- 支持注册用户管理、包括用户激活，角色管理

### ME2.0 
- 支持响应式布局，兼容手机端
- 支持应用创建、自定义Prompt以及多种参数形式的输入
- 支持文本生成型对话
- 支持应用创建保存草稿箱、以及公开/私有权限
- 支持上传文档PDF、HTML、TXT、DOC作为上下文
- 支持对话开场白以及对话的语气方式以及语言
- 支持多种模型的选择
- 支持应用多标签
- 支持语音输入，text-to-audio
- 支持管理创建工作空间且可以对用户公开邀请/已注册用户邀请进入空间

### ME3.0

- 支持小插件/webapp方式嵌入第三方页面
- 支持自定义嵌入页面的样式
- 支持聊天内容的分享
- 支持多个问题的建议（看优先级可以放在2.0实现）
- 支持接入MJ，text-to-image, image-to-image
- 支持图像私密分享以及设置生成图像的公开/私有权限


### ME4.0

- 支持多种应用的编排能力-实现标准化的Agents


## Contributer


项目负责人/全栈开发: [@Zean Ma](https://github.com/GZ315200)

UI/UX设计: [@Emir](https://www.zcool.com.cn/u/22000389)




## Contact us

- 在我们的 [GitHub Repo](https://github.com/Magic-Emerge/magic-emerge) 上提交 Issue 或 PR
- 在我们的 [Discord 社区](https://discord.gg/SSpD6kbc) 上加入讨论
- 发送邮件至 offical@xuanyong.ai
- 作者: wechat

<img src="./images/wechat.jpg" width="30%">




<a href="https://www.producthunt.com/posts/magic-emerge?utm_source=badge-featured&utm_medium=badge&utm_souce=badge-magic&#0045;emerge" target="_blank"><img src="https://api.producthunt.com/widgets/embed-image/v1/featured.svg?post_id=409397&theme=light" alt="Magic&#0032;Emerge - Building&#0032;the&#0032;best&#0032;application&#0032;services&#0032;for&#0032;AIGC | Product Hunt" style="width: 250px; height: 54px;" width="250" height="54" /></a>


## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=Magic-Emerge/magic-emerge&type=Date)](https://star-history.com/#Magic-Emerge/magic-emerge&Date)






