import { NavItem } from "@/types";
import {
    IconApps,
    IconNews,
    IconUsers,
    IconLayoutGrid,
    IconDashboard,
    IconUserPlus,
    IconSettings
  } from "@tabler/icons-react";


const routes: Array<NavItem> = [
    {
      href: "/admin",
      label: "概览",
      icon: <IconDashboard />,
      permissions: ['SUPER_ADMIN']
    },
    {
      href: "/admin/app-store",
      label: "应用管理",
      icon: <IconApps />,
      permissions: ['SUPER_ADMIN', 'ADMIN']
    },
    {
      href: "/admin/app-news",
      label: "咨询管理",
      icon: <IconNews />,
      permissions: ['SUPER_ADMIN', 'ADMIN']
    },
    {
      href: "/admin/users",
      label: "用户管理",
      icon: <IconUsers />,
      permissions: ['SUPER_ADMIN']
    },
    {
      href: "/admin/workspaces",
      label: "空间管理",
      icon: <IconLayoutGrid />,
      permissions: ['SUPER_ADMIN', 'ADMIN']
    },
    {
      href: "/admin/members",
      label: "成员管理",
      icon: <IconUserPlus />,
      permissions: ['ADMIN']
    },
    {
      href: "/admin/settings",
      label: "系统管理",
      icon: <IconSettings />,
      permissions: ['SUPER_ADMIN', 'ADMIN']
    },
  ];

  export default routes;