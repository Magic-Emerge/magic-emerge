"use client";

import MagicTable, { DataRow } from "@/components/MagicTable";
import { AppNews, AppStore, HeaderType } from "@/types/app";
import { IconSearch, IconX } from "@tabler/icons-react";
import React, { ChangeEvent, useEffect, useState, useCallback } from "react";
import { useForm } from "react-hook-form";
import { toast } from "react-hot-toast";
import Drawer from "react-modern-drawer";
import {
  createNewsInfo,
  getAppNewsInfo,
  getAppNewsList,
  updateNewsInfo,
} from "@/services/news";

interface IFormData {
  title: string;
  description: string;
  linkUrl: string;
  isPublic: boolean;
  newsSource: string;
  newsTime: string;
}

export default function AppNews() {
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [appResq, setAppResq] = useState<AppNews>({
    isPublic: true,
  });
  const [newsList, setnewsList] = useState<Array<DataRow>>([]);
  const [editInfo, seteditInfo] = useState<AppNews>({});

  const {
    register,
    handleSubmit,
    setValue,
    getValues,
    formState: { errors },
    reset,
  } = useForm<IFormData>();


  const fetchAppNewsList = useCallback(async () => {
    const { data, code } = await getAppNewsList(appResq);
    if (code === 200) {
      console.log("data", data);
      setnewsList(data);
    }
  }, [appResq]);

  const refesh = useCallback(() => {
    fetchAppNewsList();
  }, [fetchAppNewsList]);


  const toggleDrawer = useCallback(() => {
    setIsOpen((prevState) => !prevState);
    reset();
    refesh();
  },[refesh, reset]);

  const handleOnEdit = useCallback(async (id: number | string) => {
    if (id) {
      console.log("id", id);
      setIsOpen(true);
      const { data, code } = await getAppNewsInfo({ id });
      if (code === 200) {
        seteditInfo(data);
      }
    }
  },[]);

  const handleOnAdd = useCallback(() => {
    setIsOpen(true);
  }, []);


  const onSubmit = useCallback(async (formData: IFormData) => {
    if (editInfo && editInfo.id) {
      const { data, code } = await updateNewsInfo({
        ...editInfo,
        ...formData,
      });
      if (code === 200) {
        toast.success("update successful");
        reset();
        refesh();
      }
    } else {
      const { data, code } = await createNewsInfo({
        ...formData,
      });
      if (code === 200) {
        toast.success("create successful");
        reset();
        refesh();
      }
    }

    setIsOpen(false);
  }, [editInfo, refesh, reset]);

 

  const headers: Array<HeaderType> = [
    {
      name: "id",
      alias: "ID",
    },
    {
      name: "title",
      alias: "新闻标题",
    },
    {
      name: "description",
      alias: "新闻描述",
    },
    {
      name: "newsTime",
      alias: "新闻时间",
    },
    {
      name: "linkUrl",
      alias: "新闻链接",
    },
    {
      name: "isPublic",
      alias: "是否公开",
      renderCol: (isPublic) => {
        return isPublic ? "是" : "否";
      },
    },
    {
      name: "newsSource",
      alias: "新闻来源",
    },
  ];

  useEffect(() => {
    fetchAppNewsList();
  }, [appResq, fetchAppNewsList]);

  useEffect(() => {
    if (editInfo) {
      setValue("title", editInfo.title || "");
      setValue("description", editInfo.description || "");
      setValue("linkUrl", editInfo.linkUrl || "");
      setValue("isPublic", editInfo.isPublic || false);
      setValue("newsSource", editInfo.newsSource || "");
      setValue("newsTime", editInfo.newsTime || "");
    }
  }, [editInfo, setValue]);

  const handleOnSearch = useCallback((event: ChangeEvent<HTMLInputElement>) => {
    setAppResq({ ...appResq, title: event.target.value });
  }, [appResq]);

  return (
    <div className="relative flex flex-col px-6 align-middle items-center w-full">
      <div className="flex w-full justify-between">
        <div className="flex mt-4">
          <button
            onClick={handleOnAdd}
            className="h-[40px] px-6  bg-blue-primary shadow-4xl rounded-[4px] text-base font-sans font-normal text-white hover:bg-blue-suspend"
          >
            新增咨询
          </button>
        </div>
        <div className="flex mt-4 ">
          <div className="inline-block relative">
            <input
              className="w-[359px] h-[40px] rounded-[4px] shadow-md hover:shadow-xl pl-5 pr-10 font-sans text-sm font-normal text-gray-primary focus:text-black focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary"
              placeholder="请输入新闻标题"
              value={appResq.title}
              onChange={handleOnSearch}
            />
            <IconSearch className="absolute right-2 top-1/2 transform -translate-y-1/2 text-[#C0C6CC] hover:text-violet-100 h-5 w-5" />
          </div>
        </div>
      </div>
      {newsList && (
        <MagicTable data={newsList} headers={headers} onEdit={handleOnEdit} />
      )}
      <Drawer
        open={isOpen}
        onClose={toggleDrawer}
        direction="right"
        size={450}
        enableOverlay
        duration={500}
        lockBackgroundScroll={true}
      >
        <div className="flex flex-col mx-auto h-full">
          <div className="flex justify-start items-start cursor-pointer px-6 py-6 hover:text-gray-500">
            <IconX onClick={toggleDrawer} />
          </div>
          <div className="flex flex-col px-6 pb-4 relative overflow-auto">
            <form onSubmit={handleSubmit(onSubmit)}>
              <div className="mb-4">
                <label className="font-sans text-sm text-gray-primary font-medium">
                  新闻标题
                </label>
                <input
                  {...register("title", {
                    required: "请输入新闻标题",
                  })}
                  type="text"
                  name="title"
                  className="bg-gray-50 mt-2 h-[36px]  border-[0.5px] border-[#D8DFE6] text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                ></input>
                {errors.title && (
                  <p className="text-red-500 text-xs font-normal mt-1 absolute">
                    {errors.title.message}
                  </p>
                )}
              </div>
              <div className="mb-4">
                <label className="font-sans text-sm text-gray-primary font-medium">
                  新闻链接
                </label>
                <input
                  {...register("linkUrl", {
                    required: "请输入新闻链接",
                  })}
                  name="linkUrl"
                  className="bg-gray-50 h-[36px] mt-2 border-[0.5px] border-[#D8DFE6] disabled:text-gray-500 text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full  p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                ></input>
                {errors.linkUrl && (
                  <p className="text-red-500 text-xs font-normal mt-1 absolute">
                    {errors.linkUrl.message}
                  </p>
                )}
              </div>

              <div className="mb-4">
                <label className="font-sans text-sm text-gray-primary font-medium">
                  消息来源
                </label>
                <input
                  {...register("newsSource", {
                    required: "请输入新闻来雨",
                  })}
                  name="newsSource"
                  className="bg-gray-50 h-[36px] mt-2 border-[0.5px] border-[#D8DFE6] disabled:text-gray-500 text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full  p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                ></input>
                {errors.newsSource && (
                  <p className="text-red-500 text-xs font-normal mt-1 absolute">
                    {errors.newsSource.message}
                  </p>
                )}
              </div>

              <div className="mb-4">
                <label className="font-sans text-sm text-gray-primary font-medium">
                  新闻时间
                </label>
                <input
                  {...register("newsTime", {
                    required: "请输入新闻时间",
                  })}
                  name="newsTime"
                  className="bg-gray-50 h-[36px] mt-2 border-[0.5px] border-[#D8DFE6] disabled:text-gray-500 text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full  p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                ></input>
                {errors.newsTime && (
                  <p className="text-red-500 text-xs font-normal mt-1 absolute">
                    {errors.newsTime.message}
                  </p>
                )}
              </div>

              <div className="mb-4">
                <label className="font-sans text-sm text-gray-primary font-medium">
                  新闻描述
                </label>
                <textarea
                  {...register("description")}
                  rows={4}
                  name="description"
                  className="bg-gray-50 mt-2 border-[0.5px] border-[#D8DFE6] disabled:text-gray-500 text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full  p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                ></textarea>
              </div>

              <div className="mb-4">
                <label className="font-sans text-sm text-gray-primary font-medium">
                  是否公开
                </label>
                <select
                  {...register("isPublic", {
                    required: "请选择",
                  })}
                  name="isPublic"
                  className="bg-gray-50 h-[40px] mt-2 border-[0.5px] border-[#D8DFE6] disabled:text-gray-500 text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full  p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                >
                  <option value="true">是</option>
                  <option value="false">否</option>
                </select>
                {errors.isPublic && (
                  <p className="text-red-500 text-xs font-normal mt-1 absolute">
                    {errors.isPublic.message}
                  </p>
                )}
              </div>

              <div className="flex justify-end">
                <button
                  type="submit"
                  className="text-white mr-4 bg-blue-primary hover:bg-blue-select focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary font-medium rounded-[4px] text-xs  px-4 py-1.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                >
                  确认
                </button>
                <button
                  type="button"
                  onClick={toggleDrawer}
                  className="border-[0.5px] rounded-[4px] bg-gray-50 text-xs px-4 py-1.5 hover:bg-gray-300 focus:outline-none focus:border-gray-300 focus:ring-1 focus:ring-gray-50"
                >
                  取消
                </button>
              </div>
            </form>
          </div>
        </div>
      </Drawer>
    </div>
  );
}
