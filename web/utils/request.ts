"use client"

import axios, { AxiosRequestConfig } from 'axios';
import { isString, isUndef } from '.';
import { ApiResponse } from '@/types'
import { setToken, getToken, removeToken, getWorkspaceId, setWorkspaceId } from './auth'
import { toast } from 'react-hot-toast';
import { BASE_URL } from '@/global/const';
import React from 'react';
import { useRouter } from 'next/navigation';

const errorHandler = (error: {
  response: { status: number ; statusText: string }
}) => {
  return Promise.reject(error)
}

const instance = axios.create({
    baseURL: BASE_URL,
    validateStatus(status) {
      return status < 400;
    },
    responseType: 'json',
    withCredentials: true,
  });
  
  instance.interceptors.request.use(config => {
    const token = getToken();
    const workspaceId = getWorkspaceId();
    if (token && workspaceId) {
        if (!isUndef(config.headers)) {
            config.headers.Authorization = `Bearer ${token}`;
            config.headers.workspaceId = workspaceId;
        }
    }
    return config;
  }, errorHandler);
  
  instance.interceptors.response.use(response => {
    // refresh access token HTTP/2约束Header大小写, 后端会转化为小写
    const token = response.headers.authorization;
    const workspaceId = response.headers.workspaceId;
    const expireTime = response.headers.expireTime;


    if (token && isString(token)) {
      setToken(token.replace("Bearer ", ""), Number.parseInt(expireTime));
    }
    if (workspaceId) {
      setWorkspaceId(workspaceId)
    }
    const { data } = response;
    const { code, errorMsg = '接口异常' } = data

    if (code === 0 || code === 20000 || code === 50000) {
      toast.error(errorMsg)
    }
    if (code === 10000) {
      toast.error(errorMsg)
      removeToken()
      return Promise.reject(data)
    }
    return response;
  }, errorHandler);
  
  export function request<T = any> (
    url: string | AxiosRequestConfig,
    config?: AxiosRequestConfig,
  ): Promise<ApiResponse<T>> {
    const axiosPromise = typeof url === 'string' ? instance(url, config) : instance(url);
    return axiosPromise.then(response => response.data as ApiResponse<T>);
  }
  

  export function requestWithHeader<T = any, K = any>(
    url: string | AxiosRequestConfig,
    config?: AxiosRequestConfig,
  ): Promise<[ApiResponse<T>, K]> {
    const axiosPromise = typeof url === 'string' ? instance(url, config) : instance(url);
    return axiosPromise.then(response => {
      return [response.data as ApiResponse<T>, response.headers as unknown as K];
    });
  }
  
  export const getServerDomain = () => {
    return `${window.location.protocol}//${window.location.host}`;
  };
  