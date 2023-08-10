export interface LoginReq {
  way: number,
  phoneNumber?: number;
  smsCode?: number;
  username?: string;
  password?: string;
}

export interface RegisterReq {
  phoneNumber: number;
  email: string;
  emailCode: number;
  authPassword: string;
  repeatPassword: string;
  smsCode: number;
}

export interface LoginResp {
  id: string;
  email: string;
  userId: number;
  username: string;
  openUserId: string;
  avatarUrl: string;
  phoneNumber: string;
}

export interface SmsReq {
  phoneNumber?: number;
  countryCode?: string;
  email?: string;
}


export interface CheckCaptcha {
  sessionId: string;
  sig: string;
  token: string;
  scene: string;
  remoteIp: string;
}

export interface User {
  id?: string | number;
  username?: string;
  email?: string;
  phoneNumber?: string | number;
  wechat?: string;
  createAt?: string;
  userRole?: string;
  openUserId?: string;
  isActive?: boolean;
  avatarUrl? : string;
}

export interface LoggedUser {
  id: string;
  email: string;
  username: string;
  openUserId: string;
  avatarUrl?: string;
  phoneNumber: string;
  userRole: 'SUPER_ADMIN' | 'ADMIN' | 'USER';
}