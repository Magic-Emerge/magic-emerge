
// 对话

import { Conversations, ChatMessages, CheckLimit } from "@/types/chat";
import { postFetcher } from ".";
import { chatFeedback, checkLimit, conversationCreate, conversationDelete, conversationDetail, conversationUpdate, conversations, getHistoryMessages, getLatestMessages, updateMessages } from "@/utils/api";

export const getConversationInfo = async (postData: Conversations) => {
    const { data, code, errorMsg } = await postFetcher(conversationDetail, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };
  

  export const createConversationInfo = async (postData: Conversations) => {
    const { data, code, errorMsg } = await postFetcher(conversationCreate, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };

  export const updateConversationInfo = async (postData: Conversations) => {
    const { data, code, errorMsg } = await postFetcher(conversationUpdate, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };

  export const deleteConversationInfo = async (postData: Conversations) => {
    const { data, code, errorMsg } = await postFetcher(conversationDelete, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };
  
  

  export const getConversationList = async (postData: Conversations) => {
    const { data, code, errorMsg } = await postFetcher(conversations, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };


 // chat

 export const getChatMessageHistory = async (postData: ChatMessages) => {
  const { data, code, errorMsg } = await postFetcher(getHistoryMessages, postData);
  return {
    data,
    code,
    errorMsg,
  };
};


export const setChatFeedback = async (postData: ChatMessages) => {
  const { data, code, errorMsg } = await postFetcher(chatFeedback, postData);
  return {
    data,
    code,
    errorMsg,
  };
};


export const queryLatestMessages = async (postData: ChatMessages) => {
  const { data, code, errorMsg } = await postFetcher(getLatestMessages, postData);
  return {
    data,
    code,
    errorMsg,
  };
};


export const updateMessage = async (postData: ChatMessages) => {
  const { data, code, errorMsg } = await postFetcher(updateMessages, postData);
  return {
    data,
    code,
    errorMsg,
  };
};


export const checkChatLimit = async (postData: CheckLimit) => {
  const { data, code, errorMsg } = await postFetcher(checkLimit, postData);
  return {
    data,
    code,
    errorMsg,
  };
};
