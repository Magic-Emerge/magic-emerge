"use client";

import {
  ChangeEvent,
  FC,
  useState,
  KeyboardEvent,
  useEffect,
  useRef,
} from "react";
import PaperAirplaneIcon from "@heroicons/react/24/solid/PaperAirplaneIcon";
import { Tooltip } from "react-tooltip";
import toast from "react-hot-toast";
import { IconInfoCircleFilled } from "@tabler/icons-react";
import { Messaging } from "react-cssfx-loading";
import { Messages } from "@/types/chat";

interface Props {
  isShow: boolean;
  loading: boolean;
  onSend: (message: Messages) => void;
  closeShowExample: () => void;
}

export const ChatInput: FC<Props> = ({ isShow, onSend, loading, closeShowExample }) => {
  const [content, setConent] = useState<string>("");
  const [msgCount, setmsgCount] = useState<number>(0);
  const [textAreaHeight, setTextAreaHeight] = useState("auto");
  const textAreaRef = useRef<HTMLTextAreaElement>(null);

  const minTextAreaHeight = textAreaRef.current
    ? `${textAreaRef.current.scrollHeight}px`
    : "auto";

  useEffect(() => {
    if (textAreaRef.current) {
      textAreaRef.current.style.height = "auto";
      textAreaRef.current.style.height = `${textAreaRef.current.scrollHeight}px`;
    }
  }, [content]);

  const handleSend = () => {
    if (!content) {
      toast("Please enter a message", {
        duration: 2000,
        icon: <IconInfoCircleFilled className="text-gray-400" />,
      });
      return;
    }
    onSend({
      sender: "human",
      text: content,
    });
    setConent("");
    setmsgCount(0);
  };

  const handleOnInputMsg = (event: ChangeEvent<HTMLTextAreaElement>) => {
    setConent(event.target.value);
    setmsgCount(event.target.value?.length || 0);
    closeShowExample();
  };

  const handleKeyDown = (e: KeyboardEvent<HTMLTextAreaElement>) => {
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault();
      handleSend();
      setTextAreaHeight(minTextAreaHeight);
    }
  };

  const handleOnSend = () => {
    handleSend();
    setTextAreaHeight(minTextAreaHeight);
  };

  return (
    <>
      {isShow && (
        <div className="relative flex flex-col justify-between h-full">
          <textarea
            ref={textAreaRef}
            className="w-full overflow-hidden p-6 pr-12 bg-white shadow-lg shadow-slate-200 rounded-lg border font-sans text-sm  focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary"
            placeholder="与Emerge AI开始对话"
            style={{ resize: "none", height: textAreaHeight }}
            rows={1}
            value={content}
            disabled={loading}
            onChange={handleOnInputMsg}
            onKeyDown={handleKeyDown}
          />
          <div className="absolute right-3 bottom-3 text-sm text-gray-500 flex flex-col items-center">
            {loading ? (
              <Messaging className="my-2" width="5px" height="5px" />
            ) : (
              <PaperAirplaneIcon
                id="send-message"
                onClick={handleOnSend}
                className={`${
                  msgCount > 0 ? "text-blue-select" : "text-[#C0C6CC]"
                } text-[#C0C6CC] hover:text-blue-suspend h-5 mb-1 focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary focus:rounded`}
              />
            )}
            <p
              className={`${
                msgCount > 0 ? "text-gray-primary" : ""
              } hover:text-blue-primary`}
            >
              {msgCount}
            </p>
          </div>
          <Tooltip
            anchorSelect="#send-message"
            content="发送消息"
            place="top"
          />
        </div>
      )}
    </>
  );
};
