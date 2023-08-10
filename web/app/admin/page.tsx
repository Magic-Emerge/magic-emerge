"use client";

import React, { useEffect, useState } from "react";
import { Tooltip } from "react-tooltip";
import { ConversationsChart } from "@/components/charts/ConversationsChart";
import { UserSatisfactionRate } from "@/components/charts/UserSatisfactionRate";
import { CostChart } from "@/components/charts/CostChart";
import { getLoginInfo } from "@/utils/auth";
import { LoggedUser } from "@/types/user";
import { useRouter } from "next/navigation";


export default function Analysis() {
  const [period, setperiod] = useState<string>("7");
  const loginInfo = getLoginInfo();
  const nav = useRouter();

  const handleOnPeriodChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setperiod(e.target.value);
  };

  useEffect(() => {
    const obj = (loginInfo && JSON.parse(loginInfo)) as LoggedUser;
    if (obj.userRole !== 'SUPER_ADMIN') {
      nav.push('/admin/app-store')
    }
  }, [loginInfo, nav])
  


  return (
    <div className="flex flex-col px-4 align-middle items-center">
      {/* <Image src={anlysis} alt="analysis"></Image> */}
      <div className="flex w-full justify-start mx-3 my-2">
        <span className="text-md text-gray-primary font-sans ml-2 mr-3 py-2">
          分析
        </span>
        <select
          className="bg-gray-200 px-10 py-1 border-[0.5px]  focus:outline-none border-[#D8DFE6] disabled:text-gray-500 text-[#131517] text-sm rounded-[4px] block  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
          placeholder="请选择"
          name="date"
          defaultValue={"7"}
          onChange={handleOnPeriodChange}
        >
          <option value={"0"}>今天</option>
          <option value={"7"}>七日</option>
          <option value={"30"}>一个月</option>
        </select>
      </div>
      <div className="grid grid-cols-2 gap-4 px-2 my-4 justify-start items-start w-full">
        <ConversationsChart period={{ name: period }} />
        <UserSatisfactionRate period={{ name: period }} />
      </div>
      <div className="w-full my-4 px-2">
        <CostChart period={{ name: period }} />
      </div>
      <Tooltip id="chart-info" variant="dark" place="right" />
    </div>
  );
}
