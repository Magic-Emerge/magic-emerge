"use client";

import MagicPagination from "@/components/MagicPagnation";
import MagicTable from "@/components/MagicTable";
import {
  createAppInfo,
  deleteAppInfo,
  getAppCategoryList,
  getAppInfo,
  getAppList,
  updateAppInfo,
} from "@/services/app";
import {
  AppStore,
  AppStorePage,
  AppStoreResp,
  AppTag,
  HeaderType,
} from "@/types/app";
import { IconSearch, IconX } from "@tabler/icons-react";
import React, { ChangeEvent, useCallback, useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "react-hot-toast";
import Drawer from "react-modern-drawer";
import { switchAppType } from "@/utils";
import { Uploaded, Pending, ImageForm } from "@/components/upload";
import { getToken, getWorkspaceId } from "@/utils/auth";

interface IFormData {
  appName: string;
  appKey: string;
  appType: number;
  appLevel: number;
  appState: number;
  appPrice: number;
  model: string;
  appVersion: string;
  expertRating: number;
  userRating: number;
  tutorialProfile: string;
  useCase: string;
  appDesc: string;
}

export default function AppStore() {
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [appResq, setAppResq] = useState<AppStorePage>({
    page: 1,
    pageSize: 10,
  });
  const [appList, setappList] = useState<AppStoreResp>({});
  const [editInfo, seteditInfo] = useState<AppStore>({});
  const [appTags, setappTags] = useState<Array<AppTag>>([]);

  const [isPending, setIsPending] = useState(false);
  const [image, setImage] = useState(null);
  const [url, setUrl] = useState<string>("");

  const token = getToken() || "";
  const workspaceId = getWorkspaceId() || "";

  const {
    register,
    handleSubmit,
    setValue,
    getValues,
    formState: { errors },
    reset,
  } = useForm<IFormData>();

  const toggleDrawer = () => {
    setIsOpen((prevState) => !prevState);
    reset();
    setUrl("");
    refesh();
  };

  const handleOnDel = async (id: number | string) => {
    if (id) {
      const { data } = await deleteAppInfo({ id });
      if (data) {
        toast.success("删除成功");
        refesh();
      }
    }
  };

  const handleOnEdit = async (id: number | string) => {
    if (id) {
      console.log("id", id);
      setIsOpen(true);
      const { data, code } = await getAppInfo({ id });
      if (code === 200) {
        seteditInfo(data);
        setUrl(data.appAvatar || "");
      }
    }
  };

  const handleOnAdd = () => {
    setIsOpen(true);
  };

  const refesh = () => {
    fetchAppStoreList();
  };

  const onSubmit = async (formData: IFormData) => {
    if (editInfo && editInfo.id) {
      const { data, code } = await updateAppInfo({
        ...editInfo,
        ...formData,
        appAvatar: url || "",
      });
      if (code === 200) {
        toast.success(data);
        reset();
        setUrl("")
        refesh();
      }
    } else {
      const { data, code } = await createAppInfo({
        ...formData,
        appAvatar: url || "",
      });
      if (code === 200) {
        toast.success(data);
        reset();
        setUrl("")
        refesh();
      }
    }

    setIsOpen(false);
  };

  const fetchAppStoreList = useCallback(async () => {
    const { data, code } = await getAppList(appResq);
    if (code === 200) {
      setappList(data);
    }
  }, [appResq]);

  const headers: Array<HeaderType> = [
    {
      name: "id",
      alias: "ID",
    },
    {
      name: "appKey",
      alias: "应用Key",
    },
    {
      name: "appName",
      alias: "应用名称",
    },
    {
      name: "appType",
      alias: "应用类型",
      renderCol: (appType: number) => {
        return appType && <span>{switchAppType(appType)}</span>;
      },
    },
    {
      name: "appLevel",
      alias: "应用级别",
      renderCol: (appLevel: number) => {
        return appLevel && <span>{appLevel >= 2 ? "专业" : "普通"}</span>;
      },
    },
    {
      name: "appState",
      alias: "应用状态",
      renderCol: (appState: number) => {
        return (
          appState && (
            <span>
              {appState === 1 ? "上架" : appState === 2 ? "下架" : "审核中"}
            </span>
          )
        );
      },
    },
    {
      name: "appVersion",
      alias: "应用版本",
    },
    {
      name: "appDesc",
      alias: "应用描述",
    },

    {
      name: "model",
      alias: "模型名称",
    },
    {
      name: "expertRating",
      alias: "专家评分",
    },
    {
      name: "useCase",
      alias: "使用案例",
    },
    {
      name: "tutorialProfile",
      alias: "使用教程",
    },
    {
      name: "createAt",
      alias: "创建时间",
    },
  ];

  const fetchAppCategories = useCallback(async () => {
    const { data, code } = await getAppCategoryList(appResq);
    if (code === 200) {
      setappTags(data);
    }
  }, [appResq]);

  useEffect(() => {
    fetchAppStoreList();
  }, [appResq, fetchAppStoreList]);

  useEffect(() => {
    fetchAppCategories();
  }, [fetchAppCategories]);

  useEffect(() => {
    if (editInfo) {
      setValue("appName", editInfo.appName || "");
      setValue("appDesc", editInfo.appDesc || "");
      setValue("appType", editInfo.appType || 0);
      setValue("appState", editInfo.appState || 0);
      setValue("appKey", editInfo.appKey || "");
      setValue("appLevel", editInfo.appLevel || 0);
      setValue("appVersion", editInfo.appVersion || "");
      setValue("model", editInfo.model || "");
      setValue("expertRating", editInfo.expertRating || 0);
      setValue("useCase", editInfo.useCase || "");
      setValue("tutorialProfile", editInfo.tutorialProfile || "");
    }
  }, [editInfo, setValue]);

  const appTypeOptions = appTags.map((item) => {
    return (
      <option key={item.id} value={item.id}>
        {item.categoryName}
      </option>
    );
  });

  const handleOnSearch = (event: ChangeEvent<HTMLInputElement>) => {
    setAppResq({ ...appResq, appName: event.target.value });
  };

  return (
    <div className="relative flex flex-col px-6 align-middle items-center w-full">
      <div className="flex w-full justify-between">
        <div className="flex mt-4">
          <button
            onClick={handleOnAdd}
            className="h-[40px] px-6  bg-blue-primary shadow-4xl rounded-[4px] text-base font-sans font-normal text-white hover:bg-blue-suspend"
          >
            新增应用
          </button>
        </div>
        <div className="flex mt-4 ">
          <div className="inline-block relative">
            <input
              className="w-[359px] h-[40px] rounded-[4px] shadow-md hover:shadow-xl pl-5 pr-10 font-sans text-sm font-normal text-gray-primary focus:text-black focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary"
              placeholder="请输入应用名称"
              value={appResq.appName}
              onChange={handleOnSearch}
            />
            <IconSearch className="absolute right-2 top-1/2 transform -translate-y-1/2 text-[#C0C6CC] hover:text-violet-100 h-5 w-5" />
          </div>
        </div>
      </div>
      {appList.records && (
        <MagicTable
          data={appList.records}
          headers={headers}
          onDelete={handleOnDel}
          onEdit={handleOnEdit}
        />
      )}
      <MagicPagination
        totalSize={appList.total}
        totalPages={appList.pageSize || 1}
        currentPage={appList.page || 1}
        onPageChange={(page) => console.log(`Navigating to page ${page}`)}
      />
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
          <div className="flex flex-col px-6 pb-6 relative overflow-auto">
            <form onSubmit={handleSubmit(onSubmit)}>
              <div className="mb-4">
                <label className="font-sans text-sm text-gray-primary font-medium">
                  应用名称
                </label>
                <input
                  {...register("appName", {
                    required: "请输入应用名称",
                  })}
                  type="text"
                  name="appName"
                  className="bg-gray-50 mt-2 h-[36px]  border-[0.5px] border-[#D8DFE6] text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                ></input>
                {errors.appName && (
                  <p className="text-red-500 text-xs font-normal mt-1 absolute">
                    {errors.appName.message}
                  </p>
                )}
              </div>
              <div className="mb-4">
                <label className="font-sans text-sm text-gray-primary font-medium">
                  应用版本
                </label>
                <input
                  {...register("appVersion", {
                    required: "请输入应用版本",
                  })}
                  type="appVersion"
                  name="appVersion"
                  className="bg-gray-50 h-[36px] mt-2 border-[0.5px] border-[#D8DFE6] disabled:text-gray-500 text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full  p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                ></input>
                {errors.appVersion && (
                  <p className="text-red-500 text-xs font-normal mt-1 absolute">
                    {errors.appVersion.message}
                  </p>
                )}
              </div>

              <div className="mb-4">
                <label className="font-sans text-sm text-gray-primary font-medium">
                  应用类型
                </label>
                <select
                  {...register("appType", {
                    required: "请输入应用级别",
                  })}
                  name="appType"
                  className="bg-gray-50 h-[40px] p-2 mt-2 border-[0.5px] border-[#D8DFE6] disabled:text-gray-500 text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                >
                  {appTypeOptions}
                </select>
                {errors.appType && (
                  <p className="text-red-500 text-xs font-normal mt-1 absolute">
                    {errors.appType.message}
                  </p>
                )}
              </div>

              <div className="mb-4">
                <label className="font-sans text-sm text-gray-primary font-medium">
                  appKey
                </label>
                <input
                  {...register("appKey", {
                    required: "请输入appKey",
                  })}
                  type="text"
                  name="appKey"
                  className="bg-gray-50 h-[36px] mt-2 border-[0.5px] border-[#D8DFE6] disabled:text-gray-500 text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full  p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                ></input>
                {errors.appKey && (
                  <p className="text-red-500 text-xs font-normal mt-1 absolute">
                    {errors.appKey.message}
                  </p>
                )}
              </div>

              <div className="mb-4">
                <label className="font-sans text-sm text-gray-primary font-medium">
                  应用级别
                </label>
                <select
                  {...register("appLevel", {
                    required: "请输入应用级别",
                  })}
                  name="appLevel"
                  className="bg-gray-50 h-[40px] mt-2 border-[0.5px] border-[#D8DFE6] disabled:text-gray-500 text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full  p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                >
                  <option value="1">普通</option>
                  <option value="2">专业</option>
                </select>
                {errors.appLevel && (
                  <p className="text-red-500 text-xs font-normal mt-1 absolute">
                    {errors.appLevel.message}
                  </p>
                )}
              </div>

              <div className="mb-4">
                <label className="font-sans text-sm text-gray-primary font-medium">
                  应用状态
                </label>
                <select
                  {...register("appState", {
                    required: "请输入应用状态",
                  })}
                  name="appState"
                  className="bg-gray-50 h-[40px] mt-2 border-[0.5px] border-[#D8DFE6] disabled:text-gray-500 text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full  p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                >
                  <option value="1">上架</option>
                  <option value="2">下架</option>
                  <option value="3">审核中</option>
                </select>
                {errors.appState && (
                  <p className="text-red-500 text-xs font-normal mt-1 absolute">
                    {errors.appState.message}
                  </p>
                )}
              </div>

              <div className="mb-4">
                <label className="font-sans text-sm text-gray-primary font-medium">
                  模型名称
                </label>
                <select
                  {...register("model", {
                    required: "请输入模型名称",
                  })}
                  name="model"
                  className="bg-gray-50 h-[36px] mt-2 border-[0.5px] border-[#D8DFE6] disabled:text-gray-500 text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full  p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                >
                  <option value="GPT3.5">GPT3.5</option>
                  <option value="GPT3.5-16K">GPT3.5-16K</option>
                  <option value="GPT4">GPT4</option>
                </select>
                {errors.model && (
                  <p className="text-red-500 text-xs font-normal mt-1 absolute">
                    {errors.model.message}
                  </p>
                )}
              </div>

              <div className="mb-4">
                <label className="font-sans text-sm text-gray-primary font-medium">
                  专家评分
                </label>
                <input
                  {...register("expertRating", {
                    required: "请输入模型名称",
                  })}
                  type="number"
                  name="expertRating"
                  max={5}
                  step={0.1}
                  min={0}
                  className="bg-gray-50 h-[36px] mt-2 border-[0.5px] border-[#D8DFE6] disabled:text-gray-500 text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full  p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                ></input>
                {errors.expertRating && (
                  <p className="text-red-500 text-xs font-normal mt-1 absolute">
                    {errors.expertRating.message}
                  </p>
                )}
              </div>

              <div className="mb-4">
                <label className="font-sans text-sm text-gray-primary font-medium">
                  教程简介
                </label>
                <textarea
                  {...register("tutorialProfile", {
                    required: "请输入教程简介",
                  })}
                  rows={4}
                  name="tutorialProfile"
                  className="bg-gray-50 mt-2 border-[0.5px] border-[#D8DFE6] disabled:text-gray-500 text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full  p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                ></textarea>
                {errors.tutorialProfile && (
                  <p className="text-red-500 text-xs font-normal mt-1 absolute">
                    {errors.tutorialProfile.message}
                  </p>
                )}
              </div>

              <div className="mb-4">
                <label className="font-sans text-sm text-gray-primary font-medium">
                  使用案例
                </label>
                <textarea
                  {...register("useCase", {
                    required: "请输入使用案例",
                  })}
                  rows={4}
                  name="useCase"
                  className="bg-gray-50 mt-2 border-[0.5px] border-[#D8DFE6] disabled:text-gray-500 text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full  p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                ></textarea>
                {errors.useCase && (
                  <p className="text-red-500 text-xs font-normal mt-1 absolute">
                    {errors.useCase.message}
                  </p>
                )}
              </div>

              <div className="mb-4">
                <label className="font-sans text-sm text-gray-primary font-medium">
                  应用描述
                </label>
                <textarea
                  {...register("appDesc")}
                  rows={4}
                  name="appDesc"
                  className="bg-gray-50 mt-2 border-[0.5px] border-[#D8DFE6] disabled:text-gray-500 text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full  p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                ></textarea>
              </div>

              <div className="mb-4">
                <label className="font-sans text-sm text-gray-primary font-medium">
                  应用logo
                </label>

                {isPending ? (
                  <Pending />
                ) : image && url ? (
                  <Uploaded image={image} url={url} />
                ) : (
                  <ImageForm
                    image={image}
                    isPending={isPending}
                    setImage={setImage}
                    setIsPending={setIsPending}
                    url={url}
                    setUrl={setUrl}
                    token={token}
                    workspaceId={workspaceId}
                  />
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
