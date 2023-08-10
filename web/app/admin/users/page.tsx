"use client";

import MagicTable, { DataRow } from "@/components/MagicTable";
import { AppNews, HeaderType } from "@/types/app";
import { IconSearch, IconX } from "@tabler/icons-react";
import React, { ChangeEvent, useCallback, useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "react-hot-toast";
import Drawer from "react-modern-drawer";
import { getUserInfo, getUserList, updateUserInfo } from "@/services/user";
import { User } from "@/types/user";

interface IFormData {
  username: string;
  email: string;
  phoneNumber: number;
  wechat: string;
  createAt: string;
  userRole: string;
  openUserId: string;
  isActive: boolean;
  avatarUrl: string;
}

export default function Users() {
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [userResq, setUserResq] = useState<User>({
    isActive: true,
  });
  const [userList, setuserList] = useState<Array<DataRow>>([]);
  const [editInfo, seteditInfo] = useState<User>({});

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
    refesh();
  };

  const handleOnEdit = async (id: string | number) => {
    setIsOpen(true);
    const { data, code } = await getUserInfo({ id });
    if (code === 200) {
      seteditInfo(data);
    }
  };

  const fetchUsersList = useCallback(async () => {
    const { data, code } = await getUserList(userResq);
    if (code === 200) {
      setuserList(data.records);
    }
  }, [userResq]);


  const refesh = () => {
    fetchUsersList();
  };

  const onSubmit = async (formData: IFormData) => {
    if (editInfo && editInfo.id) {
      const { code } = await updateUserInfo({
        ...editInfo,
        ...formData,
      });
      if (code === 200) {
        toast.success("update successful");
        reset();
        refesh();
      }
    }
    setIsOpen(false);
  };



  const headers: Array<HeaderType> = [
    {
      name: "id",
      alias: "ID",
    },
    {
      name: "username",
      alias: "用户名",
    },
    {
      name: "wechat",
      alias: "微信号",
    },
    {
      name: "phoneNumber",
      alias: "手机号",
    },
    {
      name: "email",
      alias: "邮箱",
    },
    {
      name: "userRole",
      alias: "用户角色",
    },
    {
      name: "openUserId",
      alias: "公开用户ID",
    },
    {
      name: "isActive",
      alias: "是否激活",
      renderCol: (isActive) => {
        return isActive ? "是" : "否";
      },
    }
  ];

  useEffect(() => {
    fetchUsersList();
  }, [fetchUsersList, userResq]);

  useEffect(() => {
    if (editInfo) {
      setValue("isActive", editInfo.isActive || false);
      setValue("userRole", editInfo.userRole || '');
    }
  }, [editInfo, setValue]);

  const handleOnPhoneSearch = (event: ChangeEvent<HTMLInputElement>) => {
    setUserResq({ ...userResq, phoneNumber: event.target.value });
  };

  const handleOnUsernameSearch = (event: ChangeEvent<HTMLInputElement>) => {
    setUserResq({ ...userResq, username: event.target.value });
  };

  return (
    <div className="relative flex flex-col px-6 align-middle items-center w-full">
      <div className="flex w-full justify-between">
        <div className="flex mt-4 ">
        <div className="inline-block relative mr-4">
            <input
              className="w-full h-[40px] px-4  rounded-[4px] shadow-md hover:shadow-xl pl-5 pr-10 font-sans text-sm font-normal text-gray-primary focus:text-black focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary"
              placeholder="请输入手机号"
              value={userResq.phoneNumber}
              onChange={handleOnPhoneSearch}
            />
            <IconSearch className="absolute right-2 top-1/2 transform -translate-y-1/2 text-[#C0C6CC] hover:text-violet-100 h-5 w-5" />
          </div>
          <div className="inline-block relative">
            <input
              className="w-full h-[40px] rounded-[4px] shadow-md hover:shadow-xl pl-5 pr-10 font-sans text-sm font-normal text-gray-primary focus:text-black focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary"
              placeholder="请输入用户名"
              value={userResq.username}
              onChange={handleOnUsernameSearch}
            />
            <IconSearch className="absolute right-2 top-1/2 transform -translate-y-1/2 text-[#C0C6CC] hover:text-violet-100 h-5 w-5" />
          </div>
        </div>
      </div>
      {userList && (
        <MagicTable data={userList} headers={headers} onEdit={handleOnEdit} />
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
                  是否激活
                </label>
                <select
                  {...register("isActive", {
                    required: "请选择",
                  })}
                  name="isActive"
                  className="bg-gray-50 h-[40px] mt-2 border-[0.5px] border-[#D8DFE6] disabled:text-gray-500 text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full  p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                >
                  <option value="true">是</option>
                  <option value="false">否</option>
                </select>
                {errors.isActive && (
                  <p className="text-red-500 text-xs font-normal mt-1 absolute">
                    {errors.isActive.message}
                  </p>
                )}
              </div>

              <div className="mb-4">
                <label className="font-sans text-sm text-gray-primary font-medium">
                  用户角色
                </label>
                <select
                  {...register("userRole", {
                    required: "请选择",
                  })}
                  name="userRole"
                  className="bg-gray-50 h-[40px] mt-2 border-[0.5px] border-[#D8DFE6] disabled:text-gray-500 text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full  p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                >
                  <option value="USER">普通用户</option>
                  <option value="ADMIN">管理员</option>
                </select>
                {errors.userRole && (
                  <p className="text-red-500 text-xs font-normal mt-1 absolute">
                    {errors.userRole.message}
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
