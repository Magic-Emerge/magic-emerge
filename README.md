![](./images/img.png)
<p align="center">
  <a href="./README_CN.md">简体中文</a>
  <a href="./README_EN.md">English</a>
</p>

### Features

1. 开箱即用的专业版的聊天对话应用
2. 可视化的应用数据分析
3. 支持代码、表格的聊天内容展示
4. 支持dify应用配置以及接口访问量限制


### QuickStart

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
    app-key:
      app-CkonWxLxvDOytIKztx0aDQYA


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


3. 使用docker部署

```shell

# 进入docker目录
cd docker

# 使用下列命令一键启动
docker-compose up -d

```

4. 初始化数据库

```sql

-- 初始化超级管理员
INSERT INTO public.sys_user (username, auth_password, wechat, avatar_url, phone_number, user_role, is_deleted, email,
                             is_active)
VALUES ('admin', 'xBDZ8qiR1aP24rkFBo4ncw==', '', '', 'xxxxx', 'SUPER_ADMIN', false, 'admin@magicemerge.ai', true);

-- manager_id 必须是超级用户的id
INSERT INTO public.workspace (id, name, is_valid, remark, create_by, create_at, update_by, update_at, is_deleted,
                              is_public, manager_id)
VALUES (1, 'Magic Emerge''s 默认空间', true, '默认空间', '', false, false, '1673979417059454978');

-- user_id 必须是超级用户的id
INSERT INTO public.workspace_members (id, workspace_id, username, user_id, user_type, create_by, update_by, is_deleted,
                                      is_active)
VALUES (1, 1, 'admin', '1673979417059454978', 'OWNER', '', '', false, true);


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




