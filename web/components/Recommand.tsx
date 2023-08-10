"use client";

import React from "react";

interface Props {
  content: string;
  description: string;
  link: string;
  newsTime: string
}

export default function Recommand({ content, description, link, newsTime }: Props) {
  return (
    <>
      <a
        href={link}
        target="_blank"
        className="flex flex-col mx-4 my-1 group rounded-md p-2 bg-white space-y-3 hover:bg-blue-primary"
      >
        <p className="w-full group-hover:text-white h-[40px] text-sm font-sans line-clamp-2 font-normal text-[#45494D] leading-[20px] mt-2 cursor-pointer ">
          {content}
        </p>
      </a>
      <span className="mx-2 px-4 w-full h-[40px] group-hover:text-white  whitespace-normal line-clamp-2 text-sm font-sans font-normal text-[#A1AAB3] leading-[20px]">
        {description}
      </span>

      <span className="mx-2 px-4 py-4 w-full h-[40px] group-hover:text-white  whitespace-normal line-clamp-2 text-sm font-sans font-normal text-[#A1AAB3] leading-[20px]">
        {newsTime}
      </span>

      <div className="mt-4 mb-4 w-[85%] ml-4 h-[1px] bg-[#D8DFE6]" />
    </>
  );
}
