import {
  CheckCaptcha,
  LoginReq,
  RegisterReq,
  SmsReq,
  User,
} from "@/types/user";
import {
  updateUser,
  user,
  userCheckCaptcha,
  userDetail,
  userLogin,
  userRegister,
  userSendSms,
} from "@/utils/api";
import { postFetcher } from ".";



export const loginUser = async (postData: LoginReq) => {
  const { data, code, errorMsg } = await postFetcher(userLogin, postData);
  return {
    data,
    code,
    errorMsg,
  };
};

export const sendSms = async (postData: SmsReq) => {
  const { data, code, errorMsg } = await postFetcher(userSendSms, postData);
  return {
    data,
    code,
    errorMsg,
  };
};

export const registerUser = async (postData: RegisterReq) => {
  const { data, code, errorMsg } = await postFetcher(userRegister, postData);
  return {
    data,
    code,
    errorMsg,
  };
};

export const validateCaptcha = async (postData: CheckCaptcha) => {
  const { data, code, errorMsg } = await postFetcher(
    userCheckCaptcha,
    postData
  );
  return {
    data,
    code,
    errorMsg,
  };
};

export const updateUserInfo = async (postData: User) =>  {
  const { data, code, errorMsg } = await postFetcher(updateUser, postData);
  return {
    data,
    code,
    errorMsg,
  };
};

export const getUserInfo = async (postData: User) => {
  const { data, code, errorMsg } = await postFetcher(userDetail, postData);
  return {
    data,
    code,
    errorMsg,
  };
};



export const getUserList = async (postData: User) => {
  const { data, code, errorMsg } = await postFetcher(user, postData);
  return {
    data,
    code,
    errorMsg,
  };
};


