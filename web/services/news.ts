import {
    appNewDetail, appNews, createAppNews, updateAppNews,
  } from "@/utils/api";
  import { postFetcher } from ".";
import { AppNews } from "@/types/app";



export const getAppNewsList = async (postData: AppNews) => {
    const { data, code, errorMsg } = await postFetcher(appNews, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };
  


  export const getAppNewsInfo = async (postData: AppNews) => {
    const { data, code, errorMsg } = await postFetcher(appNewDetail, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };
  

  export const createNewsInfo = async (postData: AppNews) => {
    const { data, code, errorMsg } = await postFetcher(createAppNews, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };

  export const updateNewsInfo = async (postData: AppNews) => {
    const { data, code, errorMsg } = await postFetcher(updateAppNews, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };
