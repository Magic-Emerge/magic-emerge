"use client"; // This is a client component

import { MagnifyingGlassIcon } from "@heroicons/react/24/outline";

import AppCard from "@/components/AppCard";
import Recommand from "@/components/Recommand";
import SettingsDialog from "@/components/SettingsDialog";
import { ChangeEvent, useEffect, useState, useCallback } from "react";
import SettingsMenu, { SettingInfo } from "@/components/SettingsMenu";
import {
  getLoginInfo,
  getToken,
  getWorkspaces,
  removeToken,
  removeWorkspaceId,
  removeWorkspaces,
  removeLoginInfo,
  setLoginInfo,
} from "@/utils/auth";
import { useRouter } from "next/navigation";
import { getUserInfo } from "@/services/user";
import { AppNews, AppStore, AppStorePage, AppTag } from "@/types/app";
import {
  getAppCategoryList,
  getAppList,
  getCollectionApps,
  starAppInfo,
  upstarAppInfo,
} from "@/services/app";
import logo from "@/assets/logo/logo.jpeg";
import { toast } from "react-hot-toast";
import { getAppNewsList } from "@/services/news";
import Marquee from "react-fast-marquee";
import { getAppState, setAppState } from "@/utils";
import { IconInfoSquareRounded } from "@tabler/icons-react";
import { Notify } from "@/types/alert";
import { querySystemNotify } from "@/services/alert";


export default function Page() {
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [settingsInfo, setsettingsInfo] = useState<SettingInfo>({
    userId: "",
    username: "",
  });
  const [appStoreList, setAppCardList] = useState<Array<AppStore>>([]);
  const [appTags, setappTags] = useState<Array<AppTag>>([]);
  const [isCollApp, setisCollApp] = useState<boolean>(false);
  const [newsList, setnewsList] = useState<Array<AppNews>>([]);
  const [newsResq, setNewsResq] = useState<AppNews>({
    isPublic: true,
  });

  const [appResq, setAppResq] = useState<AppStorePage>({
    page: 1,
    pageSize: 100,
  });

  const [checkedId, setcheckedId] = useState<number>(0);
  const token = getToken() || "";
  const loginInfo = getLoginInfo() || "";
  const workspaceList = getWorkspaces() || "";
  const nav = useRouter();

  const [alertInfo, setalertInfo] = useState<Notify>();

  const fetchSystemInfo = useCallback(async () => {
    const { data, code } = await querySystemNotify({
      alertType: "SYSTEM",
    });
    if (code === 200) {
      setalertInfo(data[0]);
    }
  }, []);

  useEffect(() => {
    fetchSystemInfo();
  }, [fetchSystemInfo])
  

  const handleOnClickTag = (typeId: number) => {
    setcheckedId(typeId);
    setAppResq({ ...appResq, appType: typeId });
  };

  useEffect(() => {
    if (!token) {
      nav.push("/login");
      removeToken();
      removeLoginInfo();
      removeWorkspaceId();
      removeWorkspaces();
    } else {
      const obj = loginInfo && JSON.parse(loginInfo);
      const workspaces = workspaceList && JSON.parse(workspaceList);
      setsettingsInfo({
        username: obj.username,
        email: obj.email,
        userId: obj.id,
        workspaceId:
          (Array.isArray(workspaces) &&
            workspaces.length > 0 &&
            workspaces[0].id) ||
          "",
        workspaceName:
          (Array.isArray(workspaces) &&
            workspaces.length > 0 &&
            workspaces[0].name) ||
          "",
        userRole: obj.userRole,
      });
    }
  }, [nav, token, loginInfo, workspaceList]);

  const tagList = appTags.map((item) => {
    return (
      <div
        key={item.id}
        className={`flex group my-1 mx-2`}
        tabIndex={item.id}
        onClick={() => handleOnClickTag(item.id)}
      >
        <button
          className={`${
            item.id === checkedId ? "bg-blue-select" : "bg-white"
          }  group-hover:bg-blue-suspend hover:rounded-md rounded-[4px] shadow-4xl px-3 py-2`}
        >
          <p
            className={`${
              item.id === checkedId ? "text-white" : "text-[#45494D]"
            } text-base whitespace-nowrap  group-hover:text-white font-sans font-normal leading-[22px] text-[#45494D]`}
          >
            #{item.categoryName}
          </p>
        </button>
      </div>
    );
  });

  const fetchAppnewsList = useCallback(async () => {
    const { data, code } = await getAppNewsList(newsResq);
    if (code === 200) {
      console.log("data", data);
      setnewsList(data);
    }
  }, [newsResq]);

  const handleOnFavorite = async (
    id: number,
    appPersonalCollectionId: number
  ) => {
    if (id !== null && appPersonalCollectionId == null) {
      const { data } = await starAppInfo({ id });
      if (data) {
        toast.success("收藏成功");
        const isColl = getAppState()?.isCollApp || false;
        if (isColl) {
          fetchCollectionApps();
        } else {
          fetchAppStoreList();
        }
      }
    } else {
      const { data } = await upstarAppInfo({ id, appPersonalCollectionId });
      if (data) {
        toast.success("已取消收藏");
        const isColl = getAppState()?.isCollApp || false;
        if (isColl) {
          fetchCollectionApps();
        } else {
          fetchAppStoreList();
        }
      }
    }
  };

  const appCardList = appStoreList.map((item) => {
    return (
      <div key={item.id}>
        <AppCard
          id={item.id}
          avartaUrl={item.appAvatar || logo}
          title={item.appName}
          description={item.appDesc}
          rating={item.expertRating}
          author={item.author}
          version={item.appVersion}
          isFavorite={item.isFavorite}
          onFavorite={handleOnFavorite}
          appPersonalCollectionId={item.appPersonalCollectionId}
        />
      </div>
    );
  });

  const handleOnSetting = (settingInfo: SettingInfo) => {
    setIsOpen(true);
  };

  const handleOnCloseDialog = async (val: boolean) => {
    setIsOpen(val);
    //更新设置列表
    const { data, code } = await getUserInfo({ id: settingsInfo.userId });
    if (code == 200) {
      setsettingsInfo({ ...settingsInfo, userId: data.id, username: data.username });
      setLoginInfo(JSON.stringify(data));
    }
  };

  const fetchAppStoreList = useCallback(async () => {
    const { data, code } = await getAppList(appResq);
    if (code === 200) {
      setAppCardList(data.records);
    }
  }, [appResq]);

  const fetchAppCategories = useCallback(async () => {
    const { data, code } = await getAppCategoryList(appResq);
    if (code === 200) {
      setappTags(data);
    }
  }, [appResq]);

  const handleAllCategories = () => {
    setAppResq({ ...appResq, appType: null });
    setcheckedId(0);
  };

  const handleOnSearch = (event: ChangeEvent<HTMLInputElement>) => {
    setAppResq({ ...appResq, appName: event.target.value });
  };

  const fetchCollectionApps = useCallback(async () => {
    const { data, code } = await getCollectionApps(appResq);
    if (code === 200) {
      setAppCardList(data);
    }
  }, [appResq]);

  const handleOnCollApp = (isColl: boolean) => {
    setisCollApp(isColl);
    setAppState({ isCollApp: isColl });
    setcheckedId(0);
    setAppResq({ ...appResq, appType: null });
  };

  useEffect(() => {
    const isColl = getAppState()?.isCollApp || false;
    setisCollApp(isColl);
    if (isColl) {
      fetchCollectionApps();
    } else {
      fetchAppStoreList();
    }
  }, [appResq, isCollApp, checkedId, fetchCollectionApps, fetchAppStoreList]);

  useEffect(() => {
    fetchAppCategories();
  }, [fetchAppCategories]);

  useEffect(() => {
    fetchAppnewsList();
  }, [fetchAppnewsList, newsResq]);

  return (
    <main className="w-full min-h-screen flex flex-col justify-between items-center">
      <Marquee pauseOnHover gradient={false} className="py-1 bg-slate-200">
        <IconInfoSquareRounded className="text-red-400 h-5 w-5 mr-2">
        </IconInfoSquareRounded>
        <span className="text-xs text-blue-500 font-sans font-extralight">
          { alertInfo?.alertInfo }
        </span>
      </Marquee>
      <div className="grid grid-cols-20 w-full h-full ">
        <div className="col-span-16 ml-8 mt-6 h-full ">
          <div className="flex justify-between mb-6">
            <div className="flex justify-start">
              <button
                onClick={() => handleOnCollApp(false)}
                className={`${
                  !isCollApp ? "font-normal" : "font-extralight"
                }  cursor-pointer hover:bg-slate-200 px-2 hover:rounded-md w-full h-[40px] font-sans text-2xl text-gray-primary leading-[33px] mr-[24.5px]`}
              >
                应用市场
              </button>
              <div className="w-[1px] mt-2 h-[30px] bg-[#acbcc9]"></div>
              <button
                onClick={() => handleOnCollApp(true)}
                className={`${
                  isCollApp ? "font-normal" : "font-extralight"
                } cursor-pointer  hover:bg-slate-200 px-2 hover:rounded-md w-full h-[40px] font-sans text-2xl text-gray-primary leading-[33px] ml-[24.5px]`}
              >
                应用收藏
              </button>
            </div>
            <div className="inline-block relative">
              <input
                className="w-[359px] h-[40px] rounded-[4px] shadow-4xl hover:shadow-lg pl-5 pr-10 font-sans text-sm font-normal text-gray-primary focus:text-black focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary"
                placeholder="请输入应用名称"
                value={appResq.appName}
                onChange={handleOnSearch}
              />
              <MagnifyingGlassIcon className="absolute right-2 top-1/2 transform -translate-y-1/2 text-[#C0C6CC] hover:text-violet-100 h-5 w-5" />
            </div>
          </div>
          <div className="flex flex-wrap justify-stretch items-stretch  mb-5">
            <button
              onClick={handleAllCategories}
              className="px-4 py-2 mr-2 border border-blue-primary text-blue-primary hover:bg-blue-primary hover:text-white shadow-4xl rounded-[4px] text-base font-sans font-medium focus:bg-blue-primary focus:text-white focus:outline-none"
            >
              #所有分类
            </button>
            {tagList}
          </div>
          <div className="grid gap-8 grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-3 ">
            {appCardList}
          </div>
        </div>
        <div className="flex justify-center">
          <div className="w-[1px] mt-8 bg-[#CFDBE6] h-[848px]" />
        </div>
        <div className="col-span-3 mt-6 mr-8">
          <SettingsMenu
            settingInfo={settingsInfo}
            onUserSetting={handleOnSetting}
          />
          <div className="flex flex-col">
            <div className="flex flex-col mt-4">
              <h1 className="text-2xl font-sans font-medium leading-[33px] whitespace-nowrap">
                应用咨询
              </h1>
            </div>
            <div className="mt-5 bg-white h-[719px] shadow-4xl overflow-y-auto overflow-x-hidden rounded-[4px] relative">
              <div className="flex flex-col">
                <h2 className="my-5 px-6 w-full h-[22px] text-base font-sans font-normal text-[#454940] leading-[22px]">
                  #推荐阅读
                </h2>
                <div className="relative w-[85%] ml-4 h-[1px] bg-[#D8DFE6]" />
              </div>
              {newsList.map((item) => {
                return (
                  <Recommand
                    key={item.id}
                    content={item.title || ""}
                    description={item.description || ""}
                    link={item.linkUrl || ""}
                    newsTime={item.newsTime || ""}
                  />
                );
              })}
            </div>
          </div>
        </div>
      </div>
      <SettingsDialog
        userId={settingsInfo.userId}
        onOpen={handleOnCloseDialog}
        isOpen={isOpen}
        username={settingsInfo.username}
        email={settingsInfo.email}
      />
    </main>
  );
}
