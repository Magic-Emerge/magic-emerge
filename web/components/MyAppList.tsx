"use client";

import { AppStore } from "@/types/app";
import { useParams, usePathname, useRouter } from "next/navigation";
import React, { useState } from "react";
import { Tooltip } from "react-tooltip";

type Props = {
  tagOptions: Array<AppStore>;
};

export default function MyAppList({ tagOptions }: Props) {
  const [checkedId, setcheckedId] = useState<number | string>("");
  const nav = useRouter()
  const pathname = usePathname();
  const params = useParams();

  const handleOnClickTag = (pathId: number | string | undefined) => {
    if (pathId) {
      setcheckedId(pathId);
      const { id } = params;
      const url = `${pathname.replace(id, '')}/${pathId}`
      nav.push(url)
    }
  };

  const appList = tagOptions.map((item, index) => {
    return (
      <div
        key={item.id}
        data-tooltip-id="my-app"
        data-tooltip-content={item.appName}
        tabIndex={index}
        className="relative group"
      >
        <button
          onClick={() => handleOnClickTag(item.id)}
          className={`${
            item.id === checkedId ? "bg-blue-select" : "bg-white"
          } h-[40px] group-hover:bg-blue-suspend  px-2 py-2 hover:rounded-md rounded-[4px] shadow-4xl w-full`}
        >
          <p
            className={`${
              item.id === checkedId ? "text-white" : "text-[#45494D]"
            } text-sm  group-hover:text-white whitespace-nowrap line-clamp-1 font-sans font-normal leading-[22px] text-[#45494D]`}
          >
            #{item.appName}
          </p>
        </button>
      </div>
    );
  });

  return (
    <>
      {appList}
      <Tooltip id="my-app" place="left"></Tooltip>
    </>
  );
}
