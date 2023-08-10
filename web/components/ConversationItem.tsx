"use client";

import React, { ChangeEvent, useEffect, useState } from "react";

import {
  ChatBubbleBottomCenterTextIcon,
  PencilSquareIcon,
  TrashIcon,
  CheckIcon,
  XMarkIcon,
} from "@heroicons/react/24/outline";

type Props = {
  isNewConversation?: boolean;
  converstaionList?: Array<Converstaion>;
  setSelectInfo?: (info: Converstaion) => void;
  onEdit?: (record: Converstaion) => void;
  onDel?: (id: string | null) => void;
  onSave?: (record: Converstaion) => void;
  removeFristItem?: () => void;
};

export interface Converstaion {
  id?: string | null;
  conversationName?: string;
  conversationId?: string;
}

export default function ConversationItem({
  isNewConversation,
  converstaionList,
  setSelectInfo,
  onEdit,
  onDel,
  onSave,
  removeFristItem
}: Props) {
  const [checkedId, setcheckedId] = useState<string | null>(null);
  const [editingId, setEditingId] = useState<string | null | undefined>(null);
  const [delId, setdelId] = useState<string | null | undefined>(null);
  const [title, settitle] = useState<string | undefined>("");

  const checkedIndex = (item: Converstaion) => {
    if (item.id) {
      setcheckedId(item.id);
      setSelectInfo && setSelectInfo(item);
      removeFristItem && removeFristItem();
    }
  };

  useEffect(() => {
    if (isNewConversation) {
      setcheckedId(null);
    }
  }, [isNewConversation])
  

  const handleOnEdit = (record: Converstaion) => {
    setEditingId(record.id);
    settitle(record.conversationName);
    onEdit && onEdit(record);
  };

  const handleOnConfirm = (record: Converstaion) => {
    setEditingId(null);
    setdelId(null);
    onSave && onSave({ ...record, conversationName: title });
    if (delId) {
      onDel && onDel(delId);
    }
  };

  const handleCancelEdit = () => {
    setEditingId(null);
    setdelId(null);
  };

  const handleInputName = (event: ChangeEvent<HTMLInputElement>) => {
    settitle(event.target.value);
  };

  const handleOnDel = (id?: string | null) => {
    setdelId(id);
  };

  return (
    <div className="flex flex-col">
      {converstaionList &&
        converstaionList.map((item, index) => {
          return item.id === null ? (
            <div
              onClick={() => checkedIndex(item)}
              tabIndex={index}
              key={item.id}
              className={`${
                item.id === checkedId ? "bg-blue-select" : "bg-[#F0F8FF]"
              }  cursor-pointer mt-4 flex h-8 items-center justify-between border-[1px] rounded-[4px] w-full`}
            >
              <div className="flex mx-3">
                <ChatBubbleBottomCenterTextIcon
                  className={`${
                    item.id === checkedId ? "text-white" : "text-[#737A80]"
                  }  h-4 text-[#737A80] mt-0.5 mr-1.5`}
                />
                <span
                  className={`${
                    item.id === checkedId ? "text-white" : "text-[#737A80]"
                  } text-sm font-sans text-[#737A80] break-all line-clamp-1`}
                >
                  {item.conversationName}
                </span>
              </div>
            </div>
          ) : (
            <div
              onClick={() => checkedIndex(item)}
              tabIndex={index}
              key={item.id}
              className={`${
                item.id === checkedId ? "bg-blue-select" : "bg-[#F0F8FF]"
              }  cursor-pointer mt-4 flex h-8 items-center justify-between border-[1px] rounded-[4px] w-full`}
            >
              <div className="flex mx-3">
                <ChatBubbleBottomCenterTextIcon
                  className={`${
                    item.id === checkedId ? "text-white" : "text-[#737A80]"
                  }  h-4 text-[#737A80] mt-0.5 mr-1.5`}
                />
                {item.id == editingId ? (
                  <input
                    name={`${item.conversationName}${item.id}`}
                    onChange={handleInputName}
                    className={`text-sm font-sans w-28 rounded-[4px] text-[#737A80] focus:outline-none focus:border-blue-primary focus:ring-1 focus:ring-blue-primary `}
                    value={title}
                  ></input>
                ) : (
                  <span
                    className={`${
                      item.id === checkedId ? "text-white" : "text-[#737A80]"
                    } text-sm font-sans text-[#737A80] break-all line-clamp-1`}
                  >
                    {item.conversationName}
                  </span>
                )}
              </div>
              <div className="flex mx-4">
                {editingId === item.id || delId === item.id ? (
                  <>
                    <CheckIcon
                      onClick={() => handleOnConfirm(item)}
                      className={`${
                        item.id === checkedId ? "text-white" : "text-[#737A80]"
                      } h-4  text-[#737A80] mr-1`}
                    />
                    <XMarkIcon
                      onClick={handleCancelEdit}
                      className={`${
                        item.id === checkedId ? "text-white" : "text-[#737A80]"
                      } h-4  text-[#737A80] mr-1`}
                    />
                  </>
                ) : (
                  <>
                    <PencilSquareIcon
                      onClick={() => handleOnEdit(item)}
                      className={`${
                        item.id === checkedId ? "text-white" : "text-[#737A80]"
                      } h-4  text-[#737A80] mr-1`}
                    />
                    <TrashIcon
                      onClick={() => handleOnDel(item.id)}
                      className={`${
                        item.id === checkedId ? "text-white" : "text-[#737A80]"
                      } h-4 text-[#737A80]`}
                    />
                  </>
                )}
              </div>
            </div>
          );
        })}
    </div>
  );
}
