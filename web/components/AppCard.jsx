"use client";

import React from "react";
import Image, { StaticImageData } from "next/image";
import { StarIcon as StarOutLined } from "@heroicons/react/24/outline";
import { StarIcon } from "@heroicons/react/24/solid";
import HeartIcon from "@/assets/iconfonts/heart_solid.svg";
import HeartOutLined from "@/assets/iconfonts/heart_outline.svg";

import Rating from "react-rating";
import { useRouter } from "next/navigation";

export default function AppCard({
  id,
  avartaUrl,
  title,
  description,
  rating,
  onFavorite,
  author,
  isFavorite = false,
  version,
  appPersonalCollectionId
}) {
  const navigator = useRouter();

  const handleOnChat = (id) => {
    navigator.push(`/chat/${id}`);
  };

  const onClickFavorate = (id, appPersonalCollectionId) => {
    onFavorite(id, appPersonalCollectionId);
  };

  return (
    <div className="bg-white rounded-[4px] shadow-4xl group hover:shadow-lg hover:shadow-gray-300 p-4 relative">
      <div className="flex">
        <div className="grid grid-rows-3 grid-flow-col gap-2 gap-y-[0.5px]">
          <Image
            src={avartaUrl}
            alt="logo"
            width={36}
            height={36}
            className="row-span-3 rounded-[20px]"
          />
          <h5 className="col-span-1 whitespace-nowrap w-[64px] h-[22px] font-sans text-base font-medium text-[#45494] leading-[22px]">
            {title}
          </h5>
          <Rating
            className="row-span-2 col-span-2"
            initialRating={rating}
            emptySymbol={
              <StarOutLined className="text-[#FFD400] w-[10px] h-[10px]" />
            }
            fullSymbol={
              <StarIcon className="text-[#FFD400] h-[10px] w-[10px]" />
            }
            readonly
          />
        </div>
        <p className="absolute top-10 right-5 text-xs font-sans font-medium leading-[17px] text-[#737A80]">
          {version}
        </p>
      </div>
      <p className="line-clamp-2 text-[#A1AAB3] w-full h-[60px] text-[14px] font-normal font-sans leading-[20px]">
        {description}
      </p>
      <div className="flex mt-2">
        <p className="text-[12px] leading-[17px] font-sans font-normal text-gray-primary">
          <span className="text-[12px] leading-[17px] font-sans font-normal text-gray-primary mr-2">
            创作者:
          </span>
          {author}
        </p>
        <div className="cursor-pointer hidden group-hover:inline-flex absolute bottom-4 right-5">
          <button
            onClick={() => handleOnChat(id)}
            className="font-sans text-white text-[8px] px-3 bg-blue-primary focus:bg-blue-select transition duration-700 ease-in-out hover:-translate-y-0.5 hover:scale-120 hover:bg-blue-suspend rounded-l-full mr-[1.5px]"
          >
            点击对话
          </button>
          <button
            onClick={() => onClickFavorate(id, appPersonalCollectionId)}
            className="transition duration-700 ease-in-out hover:-translate-y-0.5 hover:scale-120 border border-blue-primary  rounded-r-full shadow-4xl"
          >
            {isFavorite ? (
              <HeartIcon className="text-[#FF6666] relative mx-2 my-1" />
            ) : (
              <HeartOutLined className="text-[#FF6666] relative mx-2 my-1" />
            )}
          </button>
        </div>
      </div>
    </div>
  );
}
