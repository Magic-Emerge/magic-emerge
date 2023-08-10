'use client'

import { Fragment, useEffect, useRef, useState } from "react";
import { Menu, Transition } from "@headlessui/react";
import { ChevronDownIcon } from "@heroicons/react/20/solid";
import logo from "@/assets/logo/logo.jpeg";
import Image, { StaticImageData } from "next/image";
import Link from "next/link";
import { removeLoginInfo, removeToken, removeWorkspaces } from "@/utils/auth";

type Props = {
  settingInfo: SettingInfo;
  onUserSetting: (val: SettingInfo) => void;
};

export interface SettingInfo {
  userId: string;
  username?: string;
  email?: string;
  workspaceId?: string;
  workspaceName?: string;
  userRole?: string;
}

export default function SettingsMenu({ onUserSetting, settingInfo }: Props) {

  const handleOnUserSetting = (usersettings: SettingInfo) => {
    onUserSetting({
      ...usersettings,
    });
  };

  const handleOnLogout = () => {
    removeToken();
    removeLoginInfo();
    removeWorkspaces();
  };

  return (
    <div className="h-[40px] w-full flex z-10">
      <Menu as="div" className="relative inline-block text-left w-full">
        <div className="flex">
          <Image src={logo} alt="avatar" width={40} className="mr-2"></Image>
          <Menu.Button className="inline-flex  whitespace-nowrap font-sans h-[40px] justify-center w-full shadow-4xl rounded-[4px] bg-white px-2 py-2.5 text-sm font-medium text-[#45494D] hover:bg-slate-200  hover:shadow-lg focus:outline-none focus-visible:ring-2 focus-visible:ring-white focus-visible:ring-opacity-75">
            {`你好, ${settingInfo.username}`}
            <ChevronDownIcon
              className="ml-2 -mr-1 h-5 w-5 text-[#C0C6CC] hover:text-violet-100"
              aria-hidden="true"
            />
          </Menu.Button>
        </div>
        <Transition
          as={Fragment}
          enter="transition ease-out duration-100"
          enterFrom="transform opacity-0 scale-95"
          enterTo="transform opacity-100 scale-100"
          leave="transition ease-in duration-75"
          leaveFrom="transform opacity-100 scale-100"
          leaveTo="transform opacity-0 scale-95"
        >
          <Menu.Items
            className={
              "z-10 absolute w-full right-0 mt-2 origin-top-right divide-y divide-gray-100 rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none"
            }
          >
            <div className="p-4">
              <Menu.Item>
                {({ active }) => (
                  <span
                    className={`${
                      active ? "bg-gray-100 text-[#45494D]" : "text-[#45494D]"
                    } group cursor-auto flex w-full items-center rounded-md px-2 py-2 text-sm`}
                  >
                    {settingInfo.email}
                  </span>
                )}
              </Menu.Item>
            </div>
            <div className="p-4">
              <Menu.Item>
                {({ active }) => (
                  <span
                    className={`${
                      active ? "bg-gray-100 text-[#45494D]" : "text-[#45494D]"
                    } group cursor-defaultflex w-full items-center rounded-md px-2 py-2 text-sm`}
                  >
                    工作空间
                  </span>
                )}
              </Menu.Item>
              <Menu.Item>
                {({ active }) => (
                  <span
                    className={`${
                      active ? "bg-gray-100 text-[#45494D]" : "text-[#45494D]"
                    } group cursor-default whitespace-normal flex w-full items-center rounded-md px-2 py-2 text-sm`}
                  >
                    {settingInfo.workspaceName}
                  </span>
                )}
              </Menu.Item>
            </div>
            <div className="p-4">
              <Menu.Item>
                {({ active }) => (
                  <button
                    onClick={() => handleOnUserSetting(settingInfo)}
                    className={`${
                      active ? "bg-gray-100 text-[#45494D]" : "text-[#45494D]"
                    } group cursor-default flex w-full items-center rounded-md px-2 py-2 text-sm`}
                  >
                    账户设置
                  </button>
                )}
              </Menu.Item>
              <Menu.Item>
                {({ active }) => (
                  <div className="flex justify-between">
                    <span
                      className={`${
                        active ? "bg-gray-100 text-[#45494D]" : "text-[#45494D]"
                      } cursor-default group flex w-full items-center rounded-md px-2 py-2 text-sm`}
                    >
                      关于
                    </span>
                    <div className="flex justify-between items-center">
                      <span className="bg-green-400 h-2 w-2 rounded-full mr-1"></span>
                      <span
                        className={`${
                          active
                            ? "bg-gray-100 text-[#45494D]"
                            : "text-[#45494D]"
                        } cursor-default flex items-center rounded-md text-sm`}
                      >
                        v1.0
                      </span>
                    </div>
                  </div>
                )}
              </Menu.Item>
            </div>
            { settingInfo.userRole !== 'USER' && (
              <div className="p-4">
                <Menu.Item>
                  {({ active }) => (
                    <Link
                      className={`${
                        active ? "bg-gray-100 text-[#45494D]" : "text-[#45494D]"
                      } group cursor-default flex w-full items-center rounded-md px-2 py-2 text-sm`}
                      href={"/admin"}
                    >
                      后台管理
                    </Link>
                  )}
                </Menu.Item>
              </div>
            )}
            <div className="p-4">
              <Menu.Item>
                {({ active }) => (
                  <Link
                    onClick={handleOnLogout}
                    className={`${
                      active ? "bg-gray-100 text-[#45494D]" : "text-[#45494D]"
                    } group cursor-default flex w-full items-center rounded-md px-2 py-2 text-sm`}
                    href={"/login"}
                  >
                    退出
                  </Link>
                )}
              </Menu.Item>
            </div>
          </Menu.Items>
        </Transition>
      </Menu>
    </div>
  );
}
