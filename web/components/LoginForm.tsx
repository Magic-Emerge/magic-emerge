
'use client'

import React, { useCallback, useEffect, useState } from "react";
import UserIcon from "@/assets/iconfonts/user.svg";
import YinsiIcon from "@/assets/iconfonts/yinsibaohu.svg";
import WechatIcon from "@/assets/iconfonts/wechat.svg";
import { useForm } from "react-hook-form";
import { useRouter } from "next/navigation";
import { loginUser, sendSms, validateCaptcha } from "@/services/user";
import { CheckCaptcha } from "@/types/user";
import { getServerDomain } from "@/utils/request";
import { toast } from "react-hot-toast";
import { setLoginInfo, setToken, setWorkspaceId, setWorkspaces } from "@/utils/auth";

type Props = {
  onRegister: (val: boolean) => void;
};

interface IFormData {
  phoneNumber?: number;
  verifyCode?: number;
  username?: string;
  password?: string;
}

export default function LoginForm({ onRegister }: Props) {
  const [timeLeft, setTimeLeft] = useState(60);
  const [accqureCode, setaccqureCode] = useState(false);
  const [loginType, setloginType] = useState<number>(0);
  const [showCaptcha, setShowCaptcha] = useState<boolean>(false);
  const [isvalidate, setValidate] = useState<boolean>(false);

  const {
    register,
    handleSubmit,
    getValues,
    setValue,
    setError,
    reset,
    formState: { errors },
  } = useForm<IFormData>();

  const navigator = useRouter();

  const onSubmit = async (data: IFormData) => {
    // navigator.push("/")
    if (!isvalidate) {
      setShowCaptcha(true);
    }
    if (isvalidate) {
      const { phoneNumber, verifyCode, username, password } = data;
      if (phoneNumber && verifyCode) {
        const { data, code, errorMsg } = await loginUser({
          way: 1,
          phoneNumber,
          smsCode: verifyCode,
        });
        if (code !== 200) {
          toast.error(errorMsg, { duration: 1500 });
        } else {
          handleLogin(data);
        }
      
      }

      if (username && password) {
        const { data, code, errorMsg } = await loginUser({
          way: 2,
          username,
          password,
        });
        if (code !== 200) {
          toast.error(errorMsg, { duration: 1500 });
        } else {
          handleLogin(data);
        }
      }
    }
  };


  const handleLogin = (data: any) => {
    const { token, user, workspaceList } = data;
    setToken(token.replace("Bearer ", ""));
    setLoginInfo(JSON.stringify(user));
    if (Array.isArray(workspaceList) && workspaceList.length > 0) {
      setWorkspaces(JSON.stringify(workspaceList));
      //进入默认空间
      setWorkspaceId(workspaceList[0].id)
    }
    navigator.push("/")
  }


  const handleClickVerifyCode = async () => {
    const phoneNumber = getValues().phoneNumber;
    const { data, errorMsg } = await sendSms({
      phoneNumber,
      countryCode: "86",
    });
    if (!data) {
      toast.error(errorMsg, { duration: 1500 });
    }
    setaccqureCode(true);
    setTimeLeft(60);
  };

  useEffect(() => {
    // 如果 timeLeft 为 0，则停止定时器
    if (timeLeft === 0) {
      setaccqureCode(false);
      setTimeLeft(60);
      return;
    }

    // 创建一个新的定时器，每秒减少 timeLeft 的值
    const timerId = setInterval(() => {
      setTimeLeft(timeLeft - 1);
    }, 1000);

    // 当组件卸载或者 timeLeft 改变时，清除定时器
    return () => {
      clearInterval(timerId);
    };
  }, [timeLeft]);

  const handleOnRegister = () => {
    onRegister(true);
  };

  const handleOnWechatLogin = () => {
    navigator.push("/wechat-login");
  };

  const handleChangeLoginType = (loginType: number) => {
    setloginType(loginType);
    reset();
    setShowCaptcha(false);
    setValidate(false);
  };

  const handleOnCaptcha = useCallback(() => {
    if (showCaptcha) {
      // 实例化nc
      (window as any).AWSC?.use("ic", function (state: any, module: any) {
        // 初始化
        (window as any).ic = module?.init({
          // 应用类型标识。它和使用场景标识（scene字段）一起决定了滑动验证的业务场景与后端对应使用的策略模型。您可以在阿里云验证码控制台的配置管理页签找到对应的appkey字段值，请务必正确填写。
          appkey: "FFFF0N0000000000B687",
          //使用场景标识。它和应用类型标识（appkey字段）一起决定了滑动验证的业务场景与后端对应使用的策略模型。您可以在阿里云验证码控制台的配置管理页签找到对应的scene值，请务必正确填写。
          scene: "ic_login",
          height: "40px",
          width: "400px",
          // 声明滑动验证需要渲染的目标ID。
          renderTo: "nc",
          //前端滑动验证通过时会触发该回调参数。您可以在该回调参数中将会话ID（sessionId）、签名串（sig）、请求唯一标识（token）字段记录下来，随业务请求一同发送至您的服务端调用验签。
          success: async function (result: any) {
            const req: CheckCaptcha = {
              sessionId: result.sessionId,
              sig: result.sig,
              token: result.token,
              scene: "ic_login",
              remoteIp: getServerDomain(),
            };
            const { data } = await validateCaptcha(req);
            if (data) {
              setValidate(true);
            }
          },
          // 滑动验证失败时触发该回调参数。
          fail: function (failCode: any) {
            window.console && console.log(failCode);
          },
          // 验证码加载出现异常时触发该回调参数。
          error: function (errorCode: any) {
            window.console && console.log(errorCode);
          },
        });
      });
    }
  }, [showCaptcha]);

  useEffect(() => {
    handleOnCaptcha();
  }, [handleOnCaptcha, showCaptcha]);

  return (
    <div className="bg-white w-[480px] h-[440px] rounded-[12px] shadow-3xl  hover:shadow-indigo-500/40 relative">
      <form className="p-9" onSubmit={handleSubmit(onSubmit)}>
        <div className="flex">
          {loginType === 0 && (
            <div className="flex">
              <h5
                onClick={() => handleChangeLoginType(0)}
                className={`text-[20px] font-normal cursor-pointer  text-blue-primary p-1 leading-10 mb-8 mr-4 dark:text-white`}
              >
                手机号登录
              </h5>
              <h5
                onClick={() => handleChangeLoginType(1)}
                className={`text-[18px] font-light cursor-pointer  text-blue-primary p-1 leading-10 mb-8 dark:text-white`}
              >
                账号登录
              </h5>
            </div>
          )}
          {loginType === 1 && (
            <div className="flex">
              <h5
                onClick={() => handleChangeLoginType(1)}
                className={`text-[20px] font-normal cursor-pointer  text-blue-primary p-1 leading-10 mb-8 mr-4 dark:text-white`}
              >
                账号登录
              </h5>
              <h5
                onClick={() => handleChangeLoginType(0)}
                className={`text-[18px] font-light cursor-pointer  text-blue-primary p-1 leading-10 mb-8  dark:text-white`}
              >
                手机号登录
              </h5>
            </div>
          )}
        </div>
        {loginType === 0 && (
          <div className="mb-8 relative">
            <UserIcon className="absolute left-2 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-500" />
            <input
              {...register("phoneNumber", {
                required: "请输入你的手机号",
                pattern: {
                  value: /^1[3-9]\d{9}$/,
                  message: "请输入正确的手机号码",
                },
              })}
              type="text"
              name="phoneNumber"
              className="bg-gray-50 border-[0.5px] border-[#D8DFE6] text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="手机号"
            />
            {errors.phoneNumber && (
              <p className="text-red-500  text-xs font-normal mt-2 absolute">
                {errors.phoneNumber.message}
              </p>
            )}
          </div>
        )}
        {loginType === 1 && (
          <div className="mb-8 relative">
            <UserIcon className="absolute left-2 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-500" />
            <input
              {...register("username", {
                required: "请输入你的用户名",
              })}
              type="text"
              name="username"
              className="bg-gray-50 border-[0.5px] border-[#D8DFE6] text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="用户名"
            />
            {errors.phoneNumber && (
              <p className="text-red-500  text-xs font-normal mt-2 absolute">
                {errors.phoneNumber.message}
              </p>
            )}
          </div>
        )}
        {loginType === 0 && (
          <div className="mb-8 relative">
            <YinsiIcon className="absolute left-2 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-500" />
            <input
              {...register("verifyCode", {
                required: "请输入验证码",
                pattern: {
                  value: /^\d{6}$/,
                  message: "请输入正确验证码",
                },
              })}
              type="text"
              name="verifyCode"
              className="bg-gray-50 border-[0.5px] border-[#D8DFE6] text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="验证码"
            />
            {errors.verifyCode && (
              <p className="text-red-500 text-xs font-normal mt-2 absolute">
                {errors.verifyCode.message}
              </p>
            )}
            <button
              type="button"
              disabled={accqureCode}
              onClick={handleClickVerifyCode}
              className="absolute w-32 top-0 right-0 p-2.5 text-sm font-medium text-blue-primary rounded-r-lg border hover:bg-blue-primary hover:text-white focus:ring-1 focus:outline-none focus:ring-[#0065CC]"
            >
              {accqureCode ? `${timeLeft}s后重试` : "获取验证码"}
            </button>
          </div>
        )}
        {loginType === 1 && (
          <div className="mb-8 relative">
            <YinsiIcon className="absolute left-2 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-500" />
            <input
              {...register("password", {
                required: "请输入你的密码",
              })}
              type="password"
              name="password"
              className="bg-gray-50 border-[0.5px] border-[#D8DFE6] text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="密码"
            />
            {errors.phoneNumber && (
              <p className="text-red-500  text-xs font-normal mt-2 absolute">
                {errors.phoneNumber.message}
              </p>
            )}
          </div>
        )}
        <button
          type="submit"
          className="text-white w-full h-[40px] mb-3 bg-blue-primary hover:bg-[#2353B2] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        >
          登录
        </button>
        {showCaptcha && <div id="nc"></div>}
      </form>
      <div className="flex w-[450px] justify-around">
        <div className="flex w-[350px]">
          <span className="text-[14px] font-normal text-[#A1AAB3] ml-10 mr-2 leading-10">
            其他方式登录
          </span>
          <div onClick={handleOnWechatLogin}>
            <WechatIcon className="cursor-pointer h-[20px] w-[20px] mt-[10px]" />
          </div>
        </div>
        <span
          onClick={handleOnRegister}
          className="relative font-medium cursor-pointer text-sm p-2.5 mr-6 text-blue-primary leading-2"
        >
          注册
        </span>
      </div>
    </div>
  );
}
