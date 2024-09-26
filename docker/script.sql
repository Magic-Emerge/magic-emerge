create table if not exists workspace
(
	id bigserial not null
		constraint workspace_pk
			primary key,
	name varchar default ''::character varying not null,
	is_valid boolean default true not null,
	remark varchar default ''::character varying not null,
	create_by varchar default ''::character varying not null,
	create_at timestamp default CURRENT_TIMESTAMP not null,
	update_by varchar default ''::character varying not null,
	update_at timestamp default CURRENT_TIMESTAMP not null,
	is_deleted boolean default false not null,
	is_public boolean default false not null,
	manager_id varchar(50) default ''::character varying not null
);


comment on column workspace.manager_id is '管理员ID';

alter table workspace owner to magic_emerge_rds;

create table if not exists app_store
(
	id bigserial not null
		constraint app_store_pk
			primary key,
	sign_id varchar default ''::character varying not null,
	app_type smallint default 0 not null,
	app_level smallint default 1 not null,
	app_state smallint default 0 not null,
	app_price numeric(10,2) default 0 not null,
	model varchar default ''::character varying not null,
	author varchar default ''::character varying not null,
	app_version varchar default ''::character varying not null,
	expert_rating double precision default 0.0 not null,
	user_rating double precision default 0.0 not null,
	tutorial_profile text default ''::text not null,
	download_count bigint default 0 not null,
	create_at timestamp default CURRENT_TIMESTAMP not null,
	update_at timestamp default CURRENT_TIMESTAMP,
	create_by varchar default ''::character varying not null,
	update_by varchar default ''::character varying not null,
	is_deleted boolean default false not null,
	app_key varchar(100) default ''::character varying not null,
	workspace_id bigint default 0 not null,
	use_case varchar default ''::character varying not null,
	app_name varchar default ''::character varying not null,
	app_desc varchar default ''::character varying not null,
	app_avatar varchar default ''::character varying not null
);

comment on column app_store.use_case is '使用案例';

comment on column app_store.app_name is '应用名称';

comment on column app_store.app_desc is '应用描述';

comment on column app_store.app_avatar is '应用头像';

alter table app_store owner to magic_emerge_rds;

create index if not exists app_store_app_kye_index
	on app_store (app_key);

create unique index if not exists app_store_app_key_index
	on app_store (app_key);

create unique index if not exists app_store_sign_id_index
	on app_store (sign_id);

create table if not exists app_sub_record
(
	id bigserial not null
		constraint app_operate_record_pk
			primary key,
	app_sign_id varchar default ''::character varying not null,
	app_name varchar default ''::character varying not null,
	app_sub_price numeric(10,2) default 0.00 not null,
	sub_user_id varchar default ''::character varying not null,
	unsub_user_id varchar default ''::character varying,
	sub_at timestamp,
	unsub_at timestamp,
	unsub_reason text default ''::text not null,
	app_sub_points integer default 0 not null
);

alter table app_sub_record owner to magic_emerge_rds;

create table if not exists app_subscriber
(
	id bigint not null,
	user_id varchar default ''::character varying not null,
	username varchar default ''::character varying not null,
	is_active boolean default false not null,
	is_premium boolean default false not null,
	points integer default 0 not null,
	date_of_birth date default CURRENT_DATE not null,
	gender varchar default 'male'::character varying not null,
	country varchar default ''::character varying not null,
	city varchar default ''::character varying not null,
	address varchar default ''::character varying not null,
	phone_number varchar default ''::character varying not null,
	wechat integer,
	registration_date timestamp default CURRENT_TIMESTAMP not null,
	create_at timestamp default CURRENT_TIMESTAMP not null,
	update_at timestamp default CURRENT_TIMESTAMP not null,
	create_by varchar default ''::character varying not null,
	update_by varchar default ''::character varying not null,
	is_deleted boolean default false not null,
	membership_level smallint default 0 not null
);

alter table app_subscriber owner to magic_emerge_rds;

create table if not exists recharge_record
(
	id bigserial not null
		constraint recharge_record_pk
			primary key,
	user_id varchar default ''::character varying not null,
	username varchar default ''::character varying not null,
	recharge_amount numeric(10,2) default 0.00 not null,
	recharge_time timestamp default CURRENT_TIMESTAMP not null,
	message_count integer default 0 not null
);

alter table recharge_record owner to magic_emerge_rds;

create table if not exists app_payment_record
(
	id bigserial not null
		constraint app_payment_record_pk
			primary key,
	app_id bigint default 0 not null,
	app_name varchar default ''::character varying not null,
	user_id varchar default ''::character varying not null,
	username varchar default ''::character varying not null,
	fee numeric(10,2) default 0.00 not null,
	payment_time timestamp default CURRENT_TIMESTAMP not null
);

alter table app_payment_record owner to magic_emerge_rds;

create table if not exists app_personal_collection
(
	id bigserial not null
		constraint app_personal_collection_pk
			primary key,
	app_id bigint default 0 not null,
	app_name varchar default ''::character varying not null,
	app_remark text default ''::text not null,
	collect_of_user_id varchar default ''::character varying not null,
	create_at timestamp default CURRENT_TIMESTAMP not null,
	update_at timestamp default CURRENT_TIMESTAMP not null,
	create_by varchar default ''::character varying not null,
	update_by varchar default ''::character varying not null,
	is_deleted boolean default false not null,
	workspace_id bigint default 0 not null,
	app_type smallint default 0 not null
);

comment on column app_personal_collection.app_type is '应用类型';

alter table app_personal_collection owner to magic_emerge_rds;

create table if not exists law_search
(
	id bigserial not null
		constraint law_search_pk
			primary key,
	search_text_id varchar default ''::character varying not null,
	response text default ''::text not null,
	user_id varchar(30) default ''::character varying not null,
	search_time timestamp default CURRENT_TIMESTAMP not null
);

alter table law_search owner to magic_emerge_rds;

create table if not exists workspace_members
(
	id bigserial not null
		constraint workspace_members_pk
			primary key,
	workspace_id bigint default 0 not null,
	username varchar(30) default ''::character varying not null,
	user_id varchar default ''::character varying not null,
	user_type varchar(20) default ''::character varying not null,
	create_by varchar(30) default ''::character varying not null,
	update_by varchar(30) default ''::character varying not null,
	update_at timestamp default CURRENT_TIMESTAMP not null,
	create_at timestamp default CURRENT_TIMESTAMP not null,
	is_deleted boolean default false not null,
	is_active boolean default false not null
);

comment on column workspace_members.workspace_id is '工作空间id';

comment on column workspace_members.username is '用户名';

comment on column workspace_members.user_id is '用户id';

comment on column workspace_members.user_type is 'OWNER、PARTNER';

alter table workspace_members owner to magic_emerge_rds;

create table if not exists sys_user
(
	id varchar default ''::character varying not null
		constraint sys_user_pk
			primary key,
	username varchar default ''::character varying not null,
	auth_password varchar default ''::character varying not null,
	wechat varchar default ''::character varying not null,
	avatar_url varchar default ''::character varying not null,
	phone_number varchar default ''::character varying not null,
	create_at timestamp default CURRENT_TIMESTAMP not null,
	update_at timestamp default CURRENT_TIMESTAMP,
	create_by varchar default ''::character varying not null,
	update_by varchar default ''::character varying not null,
	user_role varchar default ''::character varying not null,
	is_deleted boolean default false not null,
	email varchar default ''::character varying not null,
	open_user_id varchar default ''::character varying not null,
	is_active boolean default false not null
);

alter table sys_user owner to magic_emerge_rds;

create table if not exists app_news
(
	id varchar not null
		constraint app_news_pk
			primary key,
	title varchar default ''::character varying not null,
	description varchar default ''::character varying,
	news_time timestamp default CURRENT_TIMESTAMP not null,
	link_url varchar default ''::character varying,
	is_public boolean default false,
	news_source varchar default ''::character varying not null
);

comment on table app_news is '应用咨询';

comment on column app_news.title is '新闻标题';

comment on column app_news.news_time is '新闻创作时间';

comment on column app_news.link_url is '新闻链接地址';

comment on column app_news.is_public is '是否公开';

comment on column app_news.news_source is '消息来源';

alter table app_news owner to magic_emerge_rds;

create table if not exists app_category
(
	id serial not null
		constraint app_category_pk
			primary key,
	category_name varchar default ''::character varying not null,
	is_valid boolean default true not null
);

comment on table app_category is '应用类型';

comment on column app_category.category_name is '名称';

alter table app_category owner to magic_emerge_rds;

create table if not exists app_notify
(
	id bigserial not null
		constraint app_notify_pk
			primary key,
	warning_msg varchar(300) default ''::character varying not null,
	alert_type varchar(10) default ''::character varying,
	is_handle boolean default false,
	open_user_id varchar(30) default ''::character varying not null,
	alert_info varchar(500) default ''::character varying,
	create_at timestamp default CURRENT_TIMESTAMP not null,
	update_at timestamp default CURRENT_TIMESTAMP not null
);

comment on table app_notify is '应用通知';

comment on column app_notify.warning_msg is '告警信息';

comment on column app_notify.alert_type is 'SYSTEM / APP (系统告警/应用告警)';

comment on column app_notify.is_handle is '是否处理';

comment on column app_notify.open_user_id is '公开用户id';

comment on column app_notify.alert_info is '提示信息：比如系统提示';

comment on column app_notify.create_at is '创建时间';

comment on column app_notify.update_at is '修改时间，处理时间';

alter table app_notify owner to magic_emerge_rds;

create table if not exists conversations
(
	id varchar default ''::character varying not null
		constraint conversations_pk
			primary key,
	conversation_name varchar(20) default ''::character varying not null,
	conversation_id varchar(100) default ''::character varying not null,
	create_at timestamp default CURRENT_TIMESTAMP not null,
	update_at timestamp default CURRENT_TIMESTAMP not null,
	create_by varchar(30) default ''::character varying not null,
	update_by varchar(30) default ''::character varying not null,
	is_deleted boolean default false not null,
	app_id bigint default 0 not null,
	open_user_id varchar(50) default ''::character varying not null
);

comment on table conversations is '对话列表';

comment on column conversations.conversation_name is '对话名称';

comment on column conversations.conversation_id is '与messages里的conversation_id一致';

comment on column conversations.is_deleted is '是否删除';

comment on column conversations.app_id is '应用id';

alter table conversations owner to magic_emerge_rds;

create table if not exists chat_messages
(
	id varchar default ''::character varying not null
		constraint chat_messages_pk
			primary key,
	message_id varchar(100) default ''::character varying not null,
	created_at timestamp,
	conversation_id varchar(100) default ''::character varying not null,
	inputs json,
	rating varchar(10) default ''::character varying not null,
	message_content text default ''::text not null,
	open_user_id varchar(50) default ''::character varying not null,
	role varchar(10) default ''::character varying not null,
	app_key varchar(100) default ''::character varying not null,
	tokens bigint default 0 not null,
	workspace_id bigint default 0 not null,
	fee numeric(10,10) default 0 not null
);

comment on column chat_messages.rating is '消息评分like / dislike';

comment on column chat_messages.tokens is '消费token数';

comment on column chat_messages.fee is '消耗的费用';

alter table chat_messages owner to magic_emerge_rds;

create unique index if not exists chat_messages_id_uindex
	on chat_messages (id);

create index if not exists chat_messages_message_id_index
	on chat_messages (message_id);

create index if not exists chat_messages_conversation_id_index
	on chat_messages (conversation_id);


