"use client";

import React, { useEffect, useState } from "react";

import Rentage1 from "@/assets/images/headers/rectangle1.svg";
import Rentage2 from "@/assets/images/headers/rectangle2.svg";
import Rentage3 from "@/assets/images/headers/rectangle3.svg";
import Rentage4 from "@/assets/images/headers/rectangle4.svg";
import Rentage5 from "@/assets/images/headers/rectangle5.svg";
import { usePathname, useRouter } from "next/navigation";
import { notShowHeaderPath } from "@/utils";
import { getToken } from "@/utils/auth";

export default function AppHeader() {
  const pathname = usePathname();
  const nav = useRouter();
  const token = getToken();

  useEffect(() => {
    if (!token) {
      nav.push("/login");
    }
  }, [nav, token]);

  return (
    <>
      {!notShowHeaderPath.includes(pathname) && (
        <div className="flex h-[100px] overflow-hidden w-full bg-blue-primary">
          <Rentage1 className="absolute mb-4" />
          <Rentage2 className="absolute ml-[1202px] mb-8" />
          <Rentage3 className="absolute ml-[169px] mt-[4px]" />
          <Rentage4 className="absolute ml-[335px] mt-0" />
          <Rentage5 className="absolute ml-[1035px] mt-[62px]" />
          <div className="z-10 w-full py-8 flex-col items-center flex">
            <h1 className="font-sans w-[312px] h-[36px] text-[40px] text-white font-bold tracking-[1.5px] leading-[36px]">
              Magic Emerge
            </h1>
          </div>
        </div>
      )}
    </>
  );
}
