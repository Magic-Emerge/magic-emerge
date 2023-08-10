export interface Message {
  role: Role;
  content: string;
}

export type Role = "assistant" | "user";

export interface ApiResponse<T> {
  code: number;
  errorMsg: string;
  data: T;
}

export interface RouteItem {
  /** 显示在侧边栏的名称 */
  title: string;
  /** 图标 */
  icon?: React.ReactNode;
  /** 路径 */
  path?: string;
  /** 子菜单路由 */
  children?: RouteItem[];
  /** 权限列表 */
  permissions?: string[];
  /** 控制是否在侧边栏中显示 */
  hidden?: boolean;
  key?: string;
}

export type Perms = 'SUPER_ADMIN' | 'ADMIN' | 'USER';

export interface NavItem {
  href: string;
  label: string;
  icon: React.ReactNode;
  /** 权限列表 */
  permissions?: Perms[];
  /** 控制是否在侧边栏中显示 */
  hidden?: boolean;
}


export interface AppState {
  isCollApp: boolean;
}