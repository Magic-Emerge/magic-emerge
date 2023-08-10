import { messageCount, messageToken, userCount } from "@/utils/api";
import { postFetcher } from ".";
import { AnalysisQuery } from "@/types/analysis";

export const queryMessageCountList = async (postData: AnalysisQuery) => {
  const { data, code, errorMsg } = await postFetcher(messageCount, postData);
  return {
    data,
    code,
    errorMsg,
  };
};

export const queryMessageTokenList = async (postData: AnalysisQuery) => {
  const { data, code, errorMsg } = await postFetcher(messageToken, postData);
  return {
    data,
    code,
    errorMsg,
  };
};

export const queryActiveUserList = async (postData: AnalysisQuery) => {
  const { data, code, errorMsg } = await postFetcher(userCount, postData);
  return {
    data,
    code,
    errorMsg,
  };
};
