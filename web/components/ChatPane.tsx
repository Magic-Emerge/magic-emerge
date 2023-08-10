"use client";

import Image from "next/image";
import React, { useState, useEffect, useRef } from "react";
import robot from "@/assets/images/robot.png";
import human from "@/assets/images/human.png";
import { toast } from "react-hot-toast";
import { MemoizedReactMarkdown } from "./MemoizedReactMarkdown";
import { CodeBlock } from "./CodeBlock";
import remarkGfm from "remark-gfm";
import remarkMath from "remark-math";
import { IconCopy, IconThumbDown, IconThumbUp } from "@tabler/icons-react";
import { setChatFeedback } from "@/services/chat";
import { Messages, RatingType } from "@/types/chat";
import copy from "copy-to-clipboard";

type ChatProps = {
  showWelCome?: boolean;
  appName?: string;
  tutorialProfile?: string;
  messages?: Array<Messages>;
  startToChat?: () => void;
  reloadMessages?: () => void;
  showExample?: boolean;
  usecase?: string;
};

const ChatPane: React.FC<ChatProps> = ({
  showWelCome,
  appName,
  tutorialProfile,
  messages,
  startToChat,
  reloadMessages,
  showExample,
  usecase,
}: ChatProps) => {
  //   const [messages, setMessages] = useState<Message[]>([]);
  const messagesEndRef = useRef<null | HTMLDivElement>(null);

  // Scroll to bottom whenever messages update
  useEffect(() => {
    if (messages && messages.length > 0 && messagesEndRef.current) {
      messagesEndRef.current.scrollIntoView({ behavior: "smooth" });
    }
  }, [messages]);

  const [hoveredMessageId, setHoveredMessageId] = useState<
    string | undefined | null
  >("");
  const timerRef = useRef<null | NodeJS.Timeout>(null);

  const handleMouseLeave = () => {
    timerRef.current = setTimeout(() => {
      setHoveredMessageId("");
    }, 1500); // 设定延迟为1秒，可以根据需要修改
  };

  const handleMouseMove = (messageId: string | null | undefined) => {
    if (timerRef.current) {
      clearTimeout(timerRef.current);
    }
    setHoveredMessageId(messageId);
  };

  const handleCopy = async (text: string) => {
    try {
      copy(text);
      toast.success("复制成功");
    } catch (err) {
      toast.error("复制失败");
    }
  };

  const setFeedback = async (
    rating?: RatingType,
    messageId?: string | null
  ) => {
    if (messageId) {
      const { code } = await setChatFeedback({ rating, messageId });
      if (code === 200) {
        toast.success("feedback success");
        reloadMessages && reloadMessages();
      }
    }
  };

  const ChatMessage = (message: Messages) => {
    return message.sender === "human" ? (
      <div className={`flex justify-end my-8 relative`}>
        <div
          className={`p-4 whitespace-pre-wrap text-sm font-sans text-[#737A80] font-normal  rounded-[8px] bg-[#E6F2FF]`}
        >
          {message.text || ""}
        </div>
        <Image
          src={human}
          alt="avatar"
          className="rounded-full  w-9 h-9 ml-3 flex flex-shrink-0"
        />
      </div>
    ) : (
      <div
        key={message.messageId}
        className={`flex justify-start my-8`}
        onMouseMove={() => handleMouseMove(message.messageId)}
        onMouseLeave={() => handleMouseLeave()}
      >
        <Image
          src={robot}
          alt="avatar"
          className="rounded-full w-9 h-9 flex flex-shrink-0 mr-3"
        />
        <div
          className={`p-4 relative whitespace-pre-wrap text-sm font-san text-[#737A80] font-normal rounded-[8px] bg-[#F0F2F5]`}
        >
          <MemoizedReactMarkdown
            className="prose dark:prose-invert whitespace-pre-wrap text-sm font-sans text-[#737A80] font-normal "
            remarkPlugins={[remarkGfm, remarkMath]}
            components={{
              code({ node, inline, className, children, ...props }) {
                const match = /language-(\w+)/.exec(className || "");

                return !inline && match ? (
                  <CodeBlock
                    key={Math.random()}
                    language={match[1]}
                    value={String(children).replace(/\n$/, "")}
                    {...props}
                  />
                ) : (
                  <code className={className} {...props}>
                    {children}
                  </code>
                );
              },
              table({ children }) {
                return (
                  <table className="border-collapse border divide-y divide-gray-200 py-1 px-3 dark:border-white">
                    {children}
                  </table>
                );
              },
              th({ children }) {
                return (
                  <th className="break-words border  bg-gray-500 py-1 px-3 text-white dark:border-white">
                    {children}
                  </th>
                );
              },
              td({ children }) {
                return (
                  <td className="break-words border py-1 px-3 dark:border-white">
                    {children}
                  </td>
                );
              },
            }}
          >
            {message.text || ""}
          </MemoizedReactMarkdown>
          {hoveredMessageId === message.messageId && (
            <div className="absolute -bottom-6 right-0 space-x-1 flex cursor-pointer">
              {message.rating !== "like" ? (
                <IconThumbUp
                  size={18}
                  onClick={() => setFeedback("like", message.messageId)}
                />
              ) : (
                <IconThumbUp className="text-blue-primary" size={18} />
              )}
              {message.rating !== "unlike" ? (
                <IconThumbDown
                  size={18}
                  onClick={() => setFeedback("unlike", message.messageId)}
                />
              ) : (
                <IconThumbDown className="text-red-500" size={18} />
              )}
              <IconCopy
                size={18}
                onClick={() => handleCopy(message.text || "")}
              />
            </div>
          )}
        </div>
      </div>
    );
  };

  const WelcomePane = () => (
    <div className="flex mx-8 my-8 px-6 py-6 bg-gray-100 lg:w-[720px] sm:w-[420px] shadow-4xl rounded-lg ">
      <div className="flex flex-col items-start">
        <p className="font-sans text-sm text-gray-primary font-medium tracking-wide ">
          欢迎使用
          <span className="font-semibold text-gray-800">{appName}</span>
          进行对话
        </p>
        {tutorialProfile?.split("\n").map((item, index) => {
          return (
            <p
              key={index}
              className="mt-3 font-sans font-normal text-sm text-[#A1AAB3] line-clamp-5"
            >
              {item}
            </p>
          );
        })}

        <button
          onClick={startToChat}
          className="mt-3 font-sans text-sm text-blue-primary underline"
        >
          开始对话
        </button>
      </div>
    </div>
  );

  const ExamplePane = () => (
    <div className="flex mx-8 my-8 px-6 py-6 bg-gray-100 lg:w-[720px] sm:w-[420px] shadow-4xl rounded-lg ">
      <div className="flex flex-col items-start">
        <p className="font-sans text-sm text-gray-primary font-medium tracking-wide ">
          以下是一些
          <span className="font-semibold text-gray-800">{appName}</span>
          的使用案例
        </p>
        {usecase?.split("\n").map((item, index) => {
          return (
            <span
              key={index}
              className="mt-3 font-sans font-normal underline underline-offset-4 text-sm text-[#A1AAB3] line-clamp-5"
            >
              {item}
            </span>
          );
        })}
      </div>
    </div>
  );

  return (
    <div className="p-6 w-full h-full">
      {messages &&
        messages.map((message, index) => {
          return <ChatMessage key={index} {...message} />;
        })}
      <div ref={messagesEndRef} />
      {showWelCome && <WelcomePane />}
      {showExample && <ExamplePane />}
    </div>
  );
};

export default ChatPane;
