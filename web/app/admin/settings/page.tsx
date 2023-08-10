"use client";

import { querySystemNotify, updateNotify } from "@/services/alert";
import { Notify } from "@/types/alert";
import * as React from "react";
import { useCallback, useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { toast } from "react-hot-toast";

interface IFormData {
  id: string;
  alertType: string;
  alertInfo: string;
}

const Alert: React.FC = () => {
  const [alertInfo, setalertInfo] = useState<Notify>();
  const [isEdit, setisEdit] = useState<boolean>(false);

  const fetchSystemInfo = useCallback(async () => {
    const { data, code } = await querySystemNotify({
      alertType: "SYSTEM",
    });
    if (code === 200) {
      setalertInfo(data[0]);
      setisEdit(false);
    }
  }, []);

  useEffect(() => {
    fetchSystemInfo();
  }, [fetchSystemInfo]);

  const {
    register,
    handleSubmit,
    setValue,
    getValues,
    formState: { errors },
    reset,
  } = useForm<IFormData>();

  const onSubmit = useCallback(async (formData: IFormData) => {
    if (formData.id !== "") {
      const { data, code } = await updateNotify({ ...formData });
      if (code === 200) {
        toast.success(data);
        setisEdit(false);
      }
    }
  }, []);

  useEffect(() => {
    if (alertInfo) {
      setValue("id", alertInfo.id || "");
      setValue("alertInfo", alertInfo.alertInfo || "");
      setValue("alertType", alertInfo.alertType || "SYSTEM");
    }
  }, [alertInfo, setValue]);

  const handleOnEdit = () => {
    setisEdit(false);
  }

  const handleOnCancel = () => {
    setisEdit(true);
  }

  return (
    <div
      className={`flex flex-col my-4 px-6 mx-6 py-4 border-[0.5px] rounded-lg border-gray-200 shadow-sm`}
    >
      <div className="flex flex-col mx-6  justify-start items-stretch relative overflow-auto">
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="mb-4">
            <label className="font-sans text-sm text-gray-primary font-medium">
              通告信息
            </label>
            <textarea
              {...register("alertInfo", {
                required: "请输入通告信息",
              })}
              rows={4}
              name="alertInfo"
              disabled={!isEdit}
              className="bg-gray-50 mt-2 border-[0.5px] border-[#D8DFE6] disabled:text-gray-500 text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full  p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            ></textarea>
            {errors.alertInfo && (
              <p className="text-red-500 text-xs font-normal mt-1 absolute">
                {errors.alertInfo.message}
              </p>
            )}
          </div>

          <div className="mb-4">
            <label className="font-sans text-sm text-gray-primary font-medium">
              通知类型
            </label>
            <select
              {...register("alertType", {
                required: "请选择",
              })}
              name="alertType"
              disabled={!isEdit}
              className="bg-gray-50 h-[40px] mt-2 border-[0.5px] border-[#D8DFE6] disabled:text-gray-500 text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full  p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            >
              <option value="SYSTEM">系统消息</option>
              {/* <option value="APP">应用消息</option> */}
            </select>
            {errors.alertType && (
              <p className="text-red-500 text-xs font-normal mt-1 absolute">
                {errors.alertType.message}
              </p>
            )}
          </div>

          <div className="flex justify-start">
            <button
              type="submit"
              className="text-white mr-4 bg-blue-primary hover:bg-blue-select focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary font-medium rounded-[4px] text-xs  px-4 py-1.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
            >
              确认
            </button>
            {isEdit ? (
              <button
                onClick={handleOnEdit}
                type="button"
                className="border-[0.5px] rounded-[4px] bg-gray-50 text-xs px-4 py-1.5 hover:bg-gray-300 focus:outline-none focus:border-gray-300 focus:ring-1 focus:ring-gray-50"
              >
                 取消
              </button>
            ) : (
              <button
                onClick={handleOnCancel}
                type="button"
                className="border-[0.5px] rounded-[4px] bg-gray-50 text-xs px-4 py-1.5 hover:bg-gray-300 focus:outline-none focus:border-gray-300 focus:ring-1 focus:ring-gray-50"
              >
               编辑
              </button>
            )}
          </div>
        </form>
      </div>
    </div>
  );
};

export default Alert;
