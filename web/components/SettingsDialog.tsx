'use client'

import React, {
  useState,
  Fragment,
  useRef,
  ChangeEvent,
  useEffect,
} from "react";
import { Dialog, Transition } from "@headlessui/react";
import { StaticImageData } from "next/image";
import logo from "@/assets/logo/logo.jpeg";
import { useForm } from "react-hook-form";
import { updateUserInfo } from "@/services/user";
import { toast } from "react-hot-toast";

type Props = {
  avatarUrl?: string;
  username?: string;
  email?: string;
  userId: string;
  onOpen: (val: boolean) => void;
  isOpen: boolean;
};

interface IFormData {
  avatarUrl: string;
  username: string;
  email: string;
}

export default function SettingsDialog({
  avatarUrl,
  userId,
  username,
  email,
  onOpen,
  isOpen,
}: Props) {
  const {
    register,
    handleSubmit,
    setValue,
    getValues,
    formState: { errors },
    reset,
  } = useForm<IFormData>();

  const handleOnOpen = (val: boolean) => {
    onOpen(val);
    reset();
  };

  let completeButtonRef = useRef(null);

  const onSubmit = async (formData: IFormData) => {
    const { data } = await updateUserInfo({ ...formData, id: userId });
    if (data) {
      toast.success("更新账户成功")
    }
    handleOnOpen(false);
  };

  const [selectedImage, setSelectedImage] = useState<
    string | null | StaticImageData
  >(logo);

  const [showUploadButton, setShowUploadButton] = useState(false);

  const handleImageUpload = (event: ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (file) {
      setSelectedImage(URL.createObjectURL(file));
    }
  };

  const handleImageClick = () => {
    setShowUploadButton(true); // When the image is clicked, show the upload button
  };

  const handleUpload = () => {
    setSelectedImage(null);
  };

  useEffect(() => {
    if (isOpen) {
      if (username) {
        setValue("username", username);
      }
      if (email) {
        setValue("email", email);
      }
    }
  }, [email, setValue, username, isOpen]);

  return (
    <div>
      <Transition appear show={isOpen} as={Fragment}>
        <Dialog
          as="div"
          className="relative z-10"
          onClose={() => handleOnOpen(false)}
        >
          <Transition.Child
            as={Fragment}
            enter="ease-out duration-300"
            enterFrom="opacity-0"
            enterTo="opacity-100"
            leave="ease-in duration-200"
            leaveFrom="opacity-100"
            leaveTo="opacity-0"
          >
            <div className="fixed inset-0 bg-black bg-opacity-25" />
          </Transition.Child>

          <div className="fixed inset-0 overflow-y-auto">
            <div className="flex min-h-full items-center justify-center p-2 text-center">
              <Transition.Child
                as={Fragment}
                enter="ease-out duration-300"
                enterFrom="opacity-0 scale-95"
                enterTo="opacity-100 scale-100"
                leave="ease-in duration-200"
                leaveFrom="opacity-100 scale-100"
                leaveTo="opacity-0 scale-95"
              >
                <Dialog.Panel className="w-full max-w-md transform overflow-hidden rounded-2xl bg-white p-8 text-left align-middle shadow-xl transition-all">
                  <Dialog.Title
                    as="h3"
                    className="text-2xl font-sans font-medium leading-6 text-gray-primary"
                  >
                    设置
                  </Dialog.Title>
                  <form className="mt-8" onSubmit={handleSubmit(onSubmit)}>
                    <div className="mb-4">
                      {/* {selectedImage ? (
                        <div onClick={handleUpload}>
                          <Image
                            width={48}
                            height={48}
                            alt="avatar"
                            src={selectedImage}
                            className="mt-4 object-cover"
                          />
                        </div>
                      ) : (
                        <label className="flex flex-col items-center px-2 py-4 bg-white rounded-lg tracking-wide uppercase border cursor-pointer">
                          <input
                            type="file"
                            className="hidden"
                            onChange={handleImageUpload}
                          />
                          上传头像
                        </label>
                      )} */}
                      <div className="flex bg-blue-primary rounded-full mt-2 w-14 h-14 items-center justify-center">
                        <div className=" text-white text-1xl scale-[0.8] whitespace-nowrap">
                          {username}
                        </div>
                      </div>
                    </div>
                    <div className="mb-6">
                      <label className="font-sans text-sm text-gray-primary font-medium">
                        用户名
                      </label>
                      <input
                        {...register("username", {
                          required: "请输入你的用户名",
                        })}
                        type="text"
                        name="username"
                        className="bg-gray-50 mt-2 h-[36px]  border-[0.5px] border-[#D8DFE6] text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                      ></input>
                      {errors.username && (
                        <p className="text-red-500 text-xs font-normal mt-1 absolute">
                          {errors.username.message}
                        </p>
                      )}
                    </div>
                    <div className="mb-6">
                      <label className="font-sans text-sm text-gray-primary font-medium">
                        邮箱
                      </label>
                      <input
                        {...register("email", {
                          required: "请输入你的邮箱",
                          pattern: {
                            value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i,
                            message: "请输入正确的邮箱",
                          },
                        })}
                        disabled
                        type="email"
                        name="email"
                        className="bg-gray-50 h-[36px] mt-2 border-[0.5px] border-[#D8DFE6] disabled:text-gray-500 text-[#131517] text-sm rounded-[4px] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary block w-full  p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                      ></input>
                      {errors.email && (
                        <p className="text-red-500 text-xs font-normal mt-1 absolute">
                          {errors.email.message}
                        </p>
                      )}
                    </div>
                    <button
                      type="submit"
                      ref={completeButtonRef}
                      className="text-white mr-4 bg-blue-primary hover:bg-blue-select focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary font-medium rounded-[4px] text-xs  px-4 py-1.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                    >
                      确认
                    </button>
                    <button
                      type="button"
                      onClick={() => handleOnOpen(false)}
                      className="border-[0.5px] rounded-[4px] bg-gray-50 text-xs px-4 py-1.5 hover:bg-gray-300 focus:outline-none focus:border-gray-300 focus:ring-1 focus:ring-gray-50"
                    >
                      取消
                    </button>
                  </form>
                </Dialog.Panel>
              </Transition.Child>
            </div>
          </div>
        </Dialog>
      </Transition>
    </div>
  );
}
