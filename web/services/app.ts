

import {
    updateApp,
    createApp,
    appDetail,
    appStore,
    deletedApp,
    getCategorys,
    starApp,
    unstarApp,
    collections,
  } from "@/utils/api";
  import { postFetcher } from ".";
import { AppStore, AppStorePage } from "@/types/app";
  

  export const getAppInfo = async (postData: AppStore) => {
    const { data, code, errorMsg } = await postFetcher(appDetail, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };
  

  export const createAppInfo = async (postData: AppStore) => {
    const { data, code, errorMsg } = await postFetcher(createApp, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };

  export const updateAppInfo = async (postData: AppStore) => {
    const { data, code, errorMsg } = await postFetcher(updateApp, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };

  export const deleteAppInfo = async (postData: AppStore) => {
    const { data, code, errorMsg } = await postFetcher(deletedApp, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };
  
  

  export const getAppList = async (postData: AppStorePage) => {
    const { data, code, errorMsg } = await postFetcher(appStore, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };
  

  export const getAppCategoryList = async (postData: AppStorePage) => {
    const { data, code, errorMsg } = await postFetcher(getCategorys, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };

  export const starAppInfo = async (postData: AppStorePage) => {
    const { data, code, errorMsg } = await postFetcher(starApp, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };

  export const upstarAppInfo = async (postData: AppStorePage) => {
    const { data, code, errorMsg } = await postFetcher(unstarApp, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };
  

  export const getCollectionApps = async (postData: AppStorePage) => {
    const { data, code, errorMsg } = await postFetcher(collections, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };
  
