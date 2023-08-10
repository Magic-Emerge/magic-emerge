import Cookies from 'js-cookie'
import { StorageKeys } from '@/global/const'

type CookiesSetFn = Parameters<typeof Cookies.set>
type Token = Extract<CookiesSetFn[1], string>
type Expires = NonNullable<CookiesSetFn[2]>['expires']

export const setToken = (token: Token, expires?: Expires) => {
  Cookies.set(StorageKeys.AuthorizationToken, token, { expires })
}

export const getToken = () => {
  return Cookies.get(StorageKeys.AuthorizationToken)
}

export const removeToken = () => {
  Cookies.remove(StorageKeys.AuthorizationToken)
}

export const getLoginInfo = () =>  {
  if (typeof window !== 'undefined') {
    return window.localStorage.getItem(StorageKeys.LoggedInUser)
  }
}

export const setLoginInfo = (loginInfo: any) => {
  if (typeof window !== 'undefined') {
    window.localStorage.setItem(StorageKeys.LoggedInUser, loginInfo);
  }
}

export const removeLoginInfo = () => {
  if (typeof window !== 'undefined') {
    window.localStorage.removeItem(StorageKeys.LoggedInUser);
  }
}

export const setWorkspaces = (workspaces: any) => {
  if (typeof window !== 'undefined') {
    window.localStorage.setItem(StorageKeys.LoggedInWorkspace, workspaces);
  }
}


export const getWorkspaces = () => {
  if (typeof window !== 'undefined') {
    return window.localStorage.getItem(StorageKeys.LoggedInWorkspace)
  }
}

export const removeWorkspaces = () => {
  if (typeof window !== 'undefined') {
    window.localStorage.removeItem(StorageKeys.LoggedInWorkspace);
  }
}

export const setWorkspaceId = (worksapceId: string) => {
  if (typeof window !== 'undefined') {
    window.localStorage.setItem(StorageKeys.WORKSPACE_ID, worksapceId);
  }
}

export const removeWorkspaceId = () => {
  if (typeof window !== 'undefined') {
    window.localStorage.removeItem(StorageKeys.WORKSPACE_ID);
  }
}

export const getWorkspaceId = () => {
  if (typeof window !== 'undefined') {
    return localStorage.getItem(StorageKeys.WORKSPACE_ID);
  }
}