"use client";

import AppBrandcrumb, { BreadscrumbItem } from "@/components/AppBrandcrumb";
import React, { useCallback, useEffect, useState, useRef } from "react";
import logo from "@/assets/logo/logo.jpeg";
import Image from "next/image";
import { DocumentTextIcon } from "@heroicons/react/24/outline";
import MyAppList from "@/components/MyAppList";
import ConversationItem, { Converstaion } from "@/components/ConversationItem";
import ChatPane from "@/components/ChatPane";
import { ChatInput } from "@/components/ChatInput";
import { AppStore } from "@/types/app";
import { getAppInfo, getCollectionApps } from "@/services/app";
import { usePathname } from "next/navigation";
import {
  ChatMessages,
  ChatRequest,
  Conversations,
  Messages,
} from "@/types/chat";
import {
  checkChatLimit,
  createConversationInfo,
  deleteConversationInfo,
  getChatMessageHistory,
  getConversationList,
  queryLatestMessages,
  updateConversationInfo,
  updateMessage,
} from "@/services/chat";
import { getLoginInfo, getToken, getWorkspaceId } from "@/utils/auth";
import { LoggedUser } from "@/types/user";
import { IconAlertSquareRounded, IconPlayerStop } from "@tabler/icons-react";
import { toast } from "react-hot-toast";

export default function Chat({ params }: { params: { id: number } }) {
  const [appInfo, setappInfo] = useState<AppStore>({});
  const pathname = usePathname();
  const [conversationReq, setconversationReq] = useState<Conversations>({
    appId: params.id,
  });
  const [appStoreList, setAppCardList] = useState<Array<AppStore>>([]);
  const [selectChatInfo, setSelectChatInfo] = useState<Converstaion | null>();
  const [conversations, setConversations] = useState<Array<Converstaion>>();
  const [messageList, setmessageList] = useState<Array<Messages>>([]);
  const [showWelcomePane, setshowPane] = useState<boolean>(false);
  const [showChatInput, setshowChatInput] = useState<boolean>(false);
  const [loadingMsg, setloadingMsg] = useState<boolean>(false);
  const userInfo = getLoginInfo();
  const token = getToken();
  const workspaceId = getWorkspaceId();
  const stopConversationRef = useRef<boolean>(false);
  const [showExample, setshowExample] = useState<boolean>(false);

  const breadscrumbItems: BreadscrumbItem[] = [
    {
      id: 1,
      name: "应用市场",
      url: "/",
    },
    {
      id: 2,
      name: `${appInfo.appName || ""}`,
      url: pathname,
    },
  ];

  const fetchCollectionApps = async () => {
    const { data, code } = await getCollectionApps({});
    if (code === 200) {
      setAppCardList(data);
    }
  };

  const fetchAppInfo = useCallback(async () => {
    if (params) {
      const { id } = params;
      const { data, code } = await getAppInfo({ id });
      if (code === 200) {
        setappInfo(data);
      }
    }
  }, [params]);

  const fetchConversationList = useCallback(async () => {
    const { data, code } = await getConversationList({ ...conversationReq });
    if (code === 200) {
      setConversations(data);
    }
  }, [conversationReq]);

  useEffect(() => {
    fetchConversationList();
  }, [conversationReq, fetchConversationList]);

  useEffect(() => {
    fetchAppInfo();
  }, [fetchAppInfo]);

  useEffect(() => {
    fetchCollectionApps();
  }, []);

  const handleOnSelectConversation = (record: Converstaion) => {
    setshowPane(false);
    setloadingMsg(false);
    setshowChatInput(true);
    setSelectChatInfo(record);
    if (record.conversationId) {
      fetchHistoryMessgae(record);
    }
  };

  const saveConversation = useCallback(
    async (record: Conversations) => {
      const { code } = await createConversationInfo(record);
      if (code === 200) {
        fetchConversationList();
      }
    },
    [fetchConversationList]
  );

  const updateConversation = useCallback(
    async (record: Conversations) => {
      const { code } = await updateConversationInfo(record);
      if (code === 200) {
        fetchConversationList();
      }
    },
    [fetchConversationList]
  );

  const handleSaveConversationItem = useCallback(
    (record: Converstaion) => {
      console.log("record", record);
      if (record && record.id) {
        updateConversation({
          id: record.id,
          conversationName: record.conversationName,
        });
      } else {
        saveConversation({
          appId: params.id,
          conversationName: record.conversationName,
          conversationId: record.conversationId,
        });
      }
    },
    [params.id, saveConversation, updateConversation]
  );

  const handleOnDelConversationItem = useCallback(
    async (id: string | null) => {
      const { code } = await deleteConversationInfo({ id });
      if (code === 200) {
        fetchConversationList();
      }
    },
    [fetchConversationList]
  );

  const handleOnRemoveFirstItem = () => {
    if (conversations && conversations.map((item) => item.id).includes(null)) {
      conversations.shift();
    }
  };

  const fetchHistoryMessgae = useCallback(
    async (item: Converstaion) => {
      const obj = userInfo && JSON.parse(userInfo);
      const { code, data } = await getChatMessageHistory({
        conversationId: item.conversationId,
        openUserId: obj.openUserId,
        appKey: appInfo.appKey,
      });
      if (code === 200) {
        const messagesList: ChatMessages[] = data;
        const messages: Array<Messages> = messagesList.map((message) => {
          return {
            messageId: message.id,
            rating: message.rating,
            sender: message.role === "assistant" ? "robot" : "human",
            text: message.messageContent || "",
          };
        });
        setmessageList(messages);
      }
    },
    [appInfo.appKey, userInfo]
  );

  const validChatLimit = useCallback(async () => {
    const obj = (userInfo && JSON.parse(userInfo)) as LoggedUser;
    const { data } = await checkChatLimit({ userId: obj.id });
    if (data) {
      toast.custom((t) => (
        <div
          className={`${
            t.visible ? "animate-enter" : "animate-leave"
          } max-w-md w-full bg-white shadow-lg rounded-lg pointer-events-auto flex ring-1 ring-black ring-opacity-5`}
        >
          <div className="flex-1 w-0 p-4">
            <div className="flex items-start">
              <div className="flex-shrink-0 pt-0.5">
                <IconAlertSquareRounded />
              </div>
              <div className="ml-3 flex-1">
                <p className="mt-1 text-sm text-gray-500">
                  您的试用次数已经结束
                </p>
                <p className="text-sm font-medium text-gray-900">
                  请联系管理员
                </p>
              </div>
            </div>
          </div>
          <div className="flex border-l border-gray-200">
            <button
              onClick={() => toast.dismiss(t.id)}
              className="w-full border border-transparent rounded-none rounded-r-lg p-4 flex items-center justify-center text-sm font-medium text-indigo-600 hover:text-indigo-500 focus:outline-none focus:ring-2 focus:ring-indigo-500"
            >
              关闭
            </button>
          </div>
        </div>
      ));

      setloadingMsg(false);
    }
  }, [userInfo]);

  const handleSend = useCallback(
    async (message: Messages, isWelcome?: boolean) => {
      validChatLimit();

      setloadingMsg(true);
      if (!isWelcome) {
        setmessageList((prevList) => [...prevList, message]);
      }
      const userObj: LoggedUser = userInfo && JSON.parse(userInfo);
      const req: ChatRequest = {
        inputs: {},
        query: message.text || "",
        appKey: appInfo.appKey,
        openUserId: userObj.openUserId,
        conversationId: (selectChatInfo && selectChatInfo.conversationId) || "",
        responsMode: "streaming",
        model: appInfo.model || "",
      };
      const controller = new AbortController();
      const response = await fetch("/api/chat", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        signal: controller.signal,
        body: JSON.stringify({ ...req, token, workspaceId }),
      });

      if (!response.ok) {
        setloadingMsg(false);
        throw new Error(response.statusText);
      }

      const data = response.body;
      if (!data) {
        return;
      }
      const reader = data.getReader();
      const decoder = new TextDecoder("utf-8");
      let done = false;
      let first = true;

      while (!done) {
        if (stopConversationRef.current === true) {
          controller.abort();
          done = true;
          setloadingMsg(false);
          break;
        }
        const { done: doneReading, value } = await reader.read();
        done = doneReading;
        const chunkValue = decoder.decode(value);

        if (first) {
          first = false;
          setmessageList((messages) => [
            ...messages,
            {
              sender: "robot",
              text: chunkValue,
            },
          ]);
        } else {
          setmessageList((messages) => {
            const lastMessage = messages[messages.length - 1];
            const updatedMessage = {
              ...lastMessage,
              text: lastMessage.text + chunkValue,
            };
            return [...messages.slice(0, -1), updatedMessage];
          });
        }
        //如果完成查询最近的消息，取出conversationId
      }
      setloadingMsg(false);
      if (selectChatInfo && selectChatInfo.conversationId) {
        fetchHistoryMessgae({
          conversationId: selectChatInfo.conversationId,
        });
      } else {
        const { data, code } = await queryLatestMessages({
          openUserId: userObj.openUserId,
          appKey: appInfo.appKey,
        });
        if (code === 200) {
          //获取converstaionId;
          const messagesList: ChatMessages[] = data;
          const conversationId = messagesList[0].conversationId || "";

          const { code } = await updateMessage({
            id: messagesList[1].id,
            conversationId,
          });
          if (code === 200) {
            fetchHistoryMessgae({
              conversationId: conversationId,
            });
            console.log("conversations", conversations);
            handleSaveConversationItem({
              conversationId: conversationId,
              conversationName: `新的对话${
                conversations && conversations.length
              }`,
            });
            setSelectChatInfo({ conversationId: conversationId });
          }
        }
      }
    },
    [
      appInfo.appKey,
      appInfo.model,
      conversations,
      fetchHistoryMessgae,
      handleSaveConversationItem,
      selectChatInfo,
      token,
      userInfo,
      validChatLimit,
      workspaceId,
    ]
  );

  const handleStopConversation = () => {
    stopConversationRef.current = true;
    setTimeout(() => {
      stopConversationRef.current = false;
    }, 1000);
  };

  const handleCreateNewSession = () => {
    if (conversations) {
      const newConversation: Converstaion = {
        id: null,
        conversationId: "",
        conversationName: "新的对话",
      };
      if (
        !conversations
          .map((item) => item.conversationName)
          .includes(newConversation.conversationName)
      ) {
        conversations.unshift(newConversation);
      }
      handleOnSelectConversation(newConversation);
      setshowPane(true);
      setshowChatInput(false);
      setmessageList([]);
      setSelectChatInfo(null);
    }
  };

  const handleOnStartToChat = useCallback(async () => {
    setshowPane(false);
    setshowChatInput(true);
    setshowExample(true);
  }, []);

  const reloadMessages = () => {
    fetchHistoryMessgae({ conversationId: selectChatInfo?.conversationId });
  };

  const handelOnCloseExample = () => {
    setshowExample(false);
  }

  return (
    <div
      key={params.id}
      className="w-full min-h-screen flex flex-col justify-between items-center"
    >
      <div className="grid grid-cols-20 gap-[0.5px] w-full">
        <div className="col-span-16 ml-8 mt-6 h-full ">
          <AppBrandcrumb
            homeItem={breadscrumbItems[0]}
            breadscrumbItems={breadscrumbItems.slice(1)}
          />
          <div className="flex flex-row py-6 w-full h-[800px]">
            <div className="flex flex-col shadow-4xl rounded-l-xl bg-white border-r-[1px] basis-1/4 ">
              <div className="flex ml-6 items-center">
                <Image
                  width={32}
                  height={32}
                  alt="avatar"
                  src={logo}
                  className="my-4 mr-4 object-cover"
                />
                <h1 className="font-sans text-gray-primary font-medium">
                  {appInfo.appName}
                </h1>
              </div>
              <div className="flex flex-col p-6 mt-3  overflow-y-auto overflow-x-hidden">
                <div
                  onClick={handleCreateNewSession}
                  className="group cursor-pointer flex py-1 items-center justify-start border-[1px] rounded-[4px] bg-white w-full hover:bg-blue-select"
                >
                  <DocumentTextIcon className="h-4 text-blue-primary ml-3 mr-1.5 group-hover:text-white" />
                  <span className="text-sm font-sans text-[#1A8BFF] group-hover:text-white">
                    新对话
                  </span>
                </div>
                <ConversationItem
                  isNewConversation={showWelcomePane}
                  converstaionList={conversations}
                  setSelectInfo={handleOnSelectConversation}
                  onSave={handleSaveConversationItem}
                  onDel={handleOnDelConversationItem}
                  removeFristItem={handleOnRemoveFirstItem}
                />
              </div>
            </div>
            <div className="relative shadow-4xl rounded-r-xl bg-white basis-3/4 ">
              <div className="border-b-[1px] h-[84px] flex justify-start align-middle items-center">
                <span className="ml-8 font-sans text-[18px] font-medium text-gray-primary">
                  {selectChatInfo?.conversationName}
                </span>
              </div>
              <div className="flex flex-col h-[706px] justify-between">
                <div className="flex h-full overflow-y-scroll overflow-x-hidden">
                  <ChatPane
                    showWelCome={showWelcomePane}
                    appName={appInfo.appName}
                    tutorialProfile={appInfo.tutorialProfile}
                    startToChat={handleOnStartToChat}
                    messages={messageList}
                    reloadMessages={reloadMessages}
                    showExample={showExample}
                    usecase={appInfo.useCase}
                  />
                </div>
                <div className="mx-8 mb-12 mt-8 relative flex flex-col">
                  <div className="flex justify-center items-center my-2">
                    {loadingMsg && (
                      <button
                        onClick={handleStopConversation}
                        className="shadow-lg border-gray-100 flex w-fit border rounded-md py-2 px-3 text-xs text-gray-500 font-sans hover:bg-slate-200"
                      >
                        <IconPlayerStop size={16} className="mr-1" /> 停止生成
                      </button>
                    )}
                  </div>
                  <ChatInput
                    onSend={handleSend}
                    loading={loadingMsg}
                    isShow={showChatInput}
                    closeShowExample={handelOnCloseExample}
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="flex justify-center">
          <div className="w-[1px] mt-8 bg-[#CFDBE6]" />
        </div>
        <div className="col-span-3 mr-8 mt-6">
          <h1 className="font-sans text-gray-primary font-medium">我的应用</h1>
          <div className="grid gap-4 grid-cols-2 my-6">
            <MyAppList tagOptions={appStoreList} />
          </div>
        </div>
      </div>
    </div>
  );
}
