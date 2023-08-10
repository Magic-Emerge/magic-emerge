'use client'

import Image from "next/image";
import React from "react";
import qrCode from "@/assets/images/login/qrcode_starsurge.jpg";

type Props = {};

export default function WeChatLogin({}: Props) {
  return (
    <div className="flex flex-col items-center justify-center bg-white w-[480px] h-[400px] rounded-[12px] shadow-3xl  hover:shadow-indigo-500/40 ">
      <h5 className="text-[20px] font-normal text-center py-4 text-blue-primary leading-10 dark:text-white">
        微信扫码登录
      </h5>
      <Image
        className="text-center  dark:invert"
        src={qrCode}
        width={200}
        alt={"wechat"}
        priority
      />
    </div>
  );
}
