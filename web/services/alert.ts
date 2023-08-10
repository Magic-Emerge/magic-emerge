import { Notify } from "@/types/alert";
import { postFetcher } from ".";
import { alertDetail, alertList, alertUpate } from "@/utils/api";




export const queryNotifyList = async (postData: Notify) => {
    const { data, code, errorMsg } = await postFetcher(alertList, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };
  

  export const querySystemNotify = async (postData: Notify) => {
    const { data, code, errorMsg } = await postFetcher(alertList, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };


  export const queryAlertInfo = async (postData: Notify) => {
    const { data, code, errorMsg } = await postFetcher(alertDetail, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };


  export const updateNotify = async (postData: Notify) => {
    const { data, code, errorMsg } = await postFetcher(alertUpate, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };