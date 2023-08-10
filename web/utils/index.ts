import { StorageKeys } from "@/global/const";
import { AppState } from "@/types";
import {
  createParser,
  ParsedEvent,
  ReconnectInterval,
} from "eventsource-parser";
import { StreamChatResponse, ChatRequest } from "@/types/chat";

export const EmergeAIStream = async (chatReq: ChatRequest) => {
  const encoder = new TextEncoder();
  const decoder = new TextDecoder();
  const bastURL = process.env.APP_BACKEND_URL;

  const res = await fetch(`${bastURL}/dify/chat-messages`, {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${chatReq.token}`,
      Workspaceid: `${chatReq.workspaceId}`,
      // Include any other headers here, such as Authorization for JWT
    },
    method: "POST",
    body: JSON.stringify(chatReq),
  });

  if (res.status !== 200) {
    throw new Error("returned an error");
  }

  const stream = new ReadableStream({
    async start(controller) {
      const onParse = (event: ParsedEvent | ReconnectInterval) => {
        if (event.type === "event") {
          const data = event.data;
          if (data === '"[DONE]"') {
            console.log('结束了')
            controller.close();
            return;
          }
          try {
            const json = JSON.parse(data) as StreamChatResponse;
            const text = json.answer;
            const queue = encoder.encode(text);
            controller.enqueue(queue);
          } catch (e) {
            controller.error(e);
          }
        }
      };
      const parser = createParser(onParse);
      for await (const chunk of res.body as any) {
        parser.feed(decoder.decode(chunk));
      }
    },
  });
  return stream;
};

export const isObject = (value: unknown): value is Record<any, any> =>
  value !== null && typeof value === "object";
export const isFunction = (value: unknown): value is Function =>
  typeof value === "function";
export const isString = (value: unknown): value is string =>
  typeof value === "string";
export const isBoolean = (value: unknown): value is boolean =>
  typeof value === "boolean";
export const isNumber = (value: unknown): value is number =>
  typeof value === "number";
export const isUndef = (value: unknown): value is undefined =>
  typeof value === "undefined";

export const isJSON = (str: string): boolean => {
  try {
    JSON.parse(str);
  } catch (e) {
    return false;
  }
  return true;
};

export const notShowHeaderPath = ["/login", "/register", "/wechat-login"];

export const switchAppType = (type: number) => {
  switch (type) {
    case 1:
      return "客服";
    case 2:
      return "电商";
    case 3:
      return "角色扮演";
    case 4:
      return "代码工具";
    case 5:
      return "法律";
    case 6:
      return "家装/工业设计";
    default:
      return "其他";
  }
};

export const setAppState = (appState: AppState) => {
  if (typeof window !== "undefined") {
    return window.localStorage.setItem(
      StorageKeys.AppState,
      JSON.stringify(appState)
    );
  }
};

export const getAppState = () => {
  if (typeof window !== "undefined") {
    const str = window.localStorage.getItem(StorageKeys.AppState);
    if (str && str !== null && str !== "") {
      const obj: AppState = JSON.parse(str);
      return obj;
    }
  }
};


export const formatNumber = (num: number | string) => {
  if (!num) return num;
  let parts = num.toString().split(".");
  parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  return parts.join(".");
}