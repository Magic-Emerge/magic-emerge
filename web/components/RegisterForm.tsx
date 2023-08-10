"use client";

import React, { useEffect, useState } from "react";
import MailIcon from "@/assets/iconfonts/mail.svg";
import YinsiIcon from "@/assets/iconfonts/yinsibaohu.svg";
import PasswdIcon from "@/assets/iconfonts/jianguanfengkong.svg";
import UserIcon from "@/assets/iconfonts/user.svg";
import { useForm } from "react-hook-form";
import toast from "react-hot-toast";
import { useRouter } from "next/navigation";
import { registerUser, sendSms } from "@/services/user";
import { IconDeviceMobile } from "@tabler/icons-react";


type Props = {
  onLogin: (val: boolean) => void;
};

interface IFormData {
  username: string;
  phoneNumber: number;
  email: string;
  emailCode: number;
  password: string;
  repeatPassword: string;
  smsCode: number;
}

export default function RegisterForm({ onLogin }: Props) {
  const [accqureCode, setaccqureCode] = useState<boolean>(false);
  const [getEmailCode, setgetEmailCode] = useState(false);
  const [mailTimeLeft, setmailTimeLeft] = useState<number>(60);
  const [timeLeft, setTimeLeft] = useState<number>(60);
  const nav = useRouter();

  const {
    register,
    handleSubmit,
    setError,
    getValues,
    reset,
    formState: { errors },
  } = useForm<IFormData>();

  const onSubmit = async (formData: IFormData) => {
    if (formData.password !== formData.repeatPassword) {
      setError("repeatPassword", {
        type: "validate",
        message: "密码与第一次输入的不相同",
      });
    }
    const { data, errorMsg } = await registerUser({
      ...formData,
      authPassword: formData.password,
    });
    if (!data) {
      toast.error(errorMsg, { duration: 1500, position: "top-center" });
    } else {
      toast.success("用户注册成功, 返回登录");
      reset();
      nav.push("/login");
    }
  };

  const handleClickVerifyCode = async () => {
    const phoneNumber = getValues().phoneNumber;
    const { code, errorMsg } = await sendSms({
      phoneNumber,
      countryCode: "86",
    });
    if (code === 0) {
      toast.error(errorMsg, { duration: 1500, position: "top-center" });
    }
    toast.success("发送手机验证码成功");
    setaccqureCode(true);
    setTimeLeft(60);
  };

  const handleClickEmailCode = async () => {
    const email = getValues().email;
    const { code, errorMsg } = await sendSms({ email });
    if (code === 0) {
      toast.error(errorMsg, { duration: 1500, position: "top-center" });
    }
    toast.success("发送邮箱验证码成功");
    setgetEmailCode(true);
    setmailTimeLeft(60);
  };

  const handleOnLogin = () => {
    onLogin(true);
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

  useEffect(() => {
    // 如果 timeLeft 为 0，则停止定时器
    if (mailTimeLeft === 0) {
      setgetEmailCode(false);
      setmailTimeLeft(60);
      return;
    }

    // 创建一个新的定时器，每秒减少 timeLeft 的值
    const timerId = setInterval(() => {
      setmailTimeLeft(mailTimeLeft - 1);
    }, 1000);

    // 当组件卸载或者 timeLeft 改变时，清除定时器
    return () => {
      clearInterval(timerId);
    };
  }, [mailTimeLeft]);

  return (
    <div className="bg-white w-[480px] h-[684px] rounded-[12px] shadow-3xl  hover:shadow-indigo-500/40 relative">
      <form
        className="p-10"
        onSubmit={handleSubmit(onSubmit)}
        autoComplete="false"
      >
        <div className="mb-8 relative">
          <UserIcon className="absolute left-2 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-500" />
          <input
            {...register("username", {
              required: "请输入用户名",
              maxLength: {
                value: 6,
                message: '不能超过6个字符'
              }
            })}
            name="username"
            className="bg-gray-50 border-[0.5px] border-[#D8DFE6] text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="用户名"
          />
          {errors.username && (
            <p className="text-red-500 text-xs  font-normal mt-2 absolute">
              {errors.username.message}
            </p>
          )}
        </div>
        <div className="mb-8 relative">
          <MailIcon className="absolute left-2 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-500" />
          <input
            {...register("email", {
              required: "请输入你的邮箱",
              pattern: {
                value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i,
                message: "请输入正确的邮箱",
              },
            })}
            name="email"
            className="bg-gray-50 border-[0.5px] border-[#D8DFE6] text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="邮箱"
          />
          {errors.email && (
            <p className="text-red-500 text-xs  font-normal mt-2 absolute">
              {errors.email.message}
            </p>
          )}
        </div>
        <div className="mb-8 relative">
          <YinsiIcon className="absolute left-2 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-500" />
          <input
            {...register("emailCode", {
              required: "请输入邮箱验证码",
              pattern: {
                value: /^\d{6}$/,
                message: "请输入正确邮箱验证码",
              },
            })}
            name="emailCode"
            className="bg-gray-50 border-[0.5px] border-[#D8DFE6] text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="邮箱验证码"
          />
          {errors.emailCode && (
            <p className="text-red-500 text-xs  font-normal mt-2 absolute">
              {errors.emailCode.message}
            </p>
          )}
          <button
            type="button"
            disabled={getEmailCode}
            onClick={handleClickEmailCode}
            className="absolute w-32 top-0 right-0 p-2.5 text-sm font-medium text-blue-primary rounded-r-lg border hover:bg-blue-primary hover:text-white focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary"
          >
            {getEmailCode ? `${mailTimeLeft}s后重试` : "获取邮箱验证码"}
          </button>
        </div>
        <div className="mb-8 relative">
          <PasswdIcon className="absolute left-2 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-500" />
          <input
            {...register("password", {
              required: "请输入密码",
            })}
            type="password"
            name="password"
            className="bg-gray-50 border-[0.5px] border-[#D8DFE6] text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="设置登录密码"
          />
          {errors.password && (
            <p className="text-red-500 text-xs  font-normal mt-2 absolute">
              {errors.password.message}
            </p>
          )}
        </div>
        <div className="mb-8 relative">
          <PasswdIcon className="absolute left-2 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-500" />
          <input
            {...register("repeatPassword", {
              required: "请输入重复密码",
            })}
            type="password"
            name="repeatPassword"
            className="bg-gray-50 border-[0.5px] border-[#D8DFE6] text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="重复密码"
          />
          {errors.repeatPassword && (
            <p className="text-red-500 text-xs  font-normal mt-2 absolute">
              {errors.repeatPassword.message}
            </p>
          )}
        </div>
        <div className="mb-8 relative">
          <IconDeviceMobile className="absolute left-2 top-1/2 transform -translate-y-1/2 h-5 w-5 text-[#1677FF]" />
          <input
            {...register("phoneNumber", {
              required: "请输入你的手机号",
              maxLength: {
                value: 11,
                message: "只支持11位的手机号码"
              },
              pattern: {
                value: /^1[3-9]\d{9}$/,
                message: "请输入正确的手机号码",
              },
            })}
            type="tel"
            name="phoneNumber"
            className="bg-gray-50 border-[0.5px] border-[#D8DFE6] text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="手机号"
          />
          {errors.phoneNumber && (
            <p className="text-red-500 text-xs  font-normal mt-2 absolute">
              {errors.phoneNumber.message}
            </p>
          )}
        </div>
        <div className="mb-8 relative">
          <YinsiIcon className="absolute left-2 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-500" />
          <input
            {...register("smsCode", {
              required: "请输入手机验证码",
              pattern: {
                value: /^\d{6}$/,
                message: "请输入正确手机验证码",
              },
            })}
            type="text"
            name="smsCode"
            className="bg-gray-50 border-[0.5px] border-[#D8DFE6] text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full pl-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder="手机验证码"
          />
          <button
            type="button"
            disabled={accqureCode}
            onClick={handleClickVerifyCode}
            className="absolute w-32 top-0 right-0 p-2.5 text-sm font-medium text-blue-primary rounded-r-lg border hover:bg-blue-primary hover:text-white focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary"
          >
            {accqureCode ? `${timeLeft}s后重试` : "获取验证码"}
          </button>
          {errors.smsCode && (
            <p className="text-red-500 text-xs  font-normal mt-2 absolute">
              {errors.smsCode.message}
            </p>
          )}
        </div>
        <button
          type="submit"
          className="text-white w-[400px] h-[40px] bg-blue-primary hover:bg-[#2353B2] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        >
          注册
        </button>
      </form>
      <div className="flex w-[350px]">
        <span
          onClick={handleOnLogin}
          className="cursor-pointer text-[14px] font-normal text-blue-primary ml-10 mr-2 leading-[22px]"
        >
          使用已有账户登录
        </span>
      </div>
    </div>
  );
}
