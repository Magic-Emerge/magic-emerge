'use client'

import RegisterForm from "@/components/RegisterForm";
import { useRouter } from "next/navigation";
import React from "react";

import Rantage1 from "@/assets/images/login/rectangle1.svg";
import Rantage2 from "@/assets/images/login/rectangle2.svg";
import Rantage3 from "@/assets/images/login/rectangle3.svg";
import Rantage4 from "@/assets/images/login/rectangle4.svg";
import Rantage5 from "@/assets/images/login/rectangle5.svg";
import Rantage6 from "@/assets/images/login/rectangle6.svg";
import Rantage7 from "@/assets/images/login/rectangle7.svg";
import Rantage8 from "@/assets/images/login/rectangle8.svg";
import Rantage9 from "@/assets/images/login/rectangle9.svg";
import Rantage10 from "@/assets/images/login/rectangle10.svg";

type Props = {};

export default function Register({}: Props) {
  const router = useRouter();

  const handleOnLogin = (val: Boolean) => {
    if (val) {
      router.push("/login");
    }
  };

  return (
    <main className="flex relative overflow-hidden h-screen w-full  bg-blue-primary">
      <Rantage2 className="absolute ml-[1273px] top-0 " />
      <Rantage9 className="absolute ml-[1078px] mt-[123px]" />
      <Rantage7 className="absolute ml-[1317px] mt-[200px]" />
      <Rantage1 className="absolute ml-[922px] mt-[284px]" />
      <Rantage3 className="absolute ml-[1065px] mt-[440px]" />
      <Rantage5 className="absolute ml-[1107px] mt-[766px]" />
      <Rantage6 className="absolute ml-[933px] mt-[607px]"/>
      <Rantage8 className="absolute ml-[1341px] mt-[659px]"/>
      <Rantage10 className="absolute ml-[1215px] mt-[506px]"/>
      <Rantage4 className="absolute ml-[1226px] mt-[879px]"/>
      <div className="z-10 w-full flex-col items-center justify-center font-mono text-sm lg:flex">
        <span className="mb-12 font-sans text-2xl text-white font-medium tracking-[1px] leading-[45px]">
          请注册使用 Magic Emerge!
        </span>
        <RegisterForm onLogin={handleOnLogin} />
      </div>
    </main>
  );
}
