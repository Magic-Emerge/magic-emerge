


import {
   Workspace
  } from "@/types/workspace";
  import {
    workspaceDetail,
    createWorkspace,
    updateWorkspace,
    workspaces
  } from "@/utils/api";

  import { postFetcher } from ".";
  
  
  export const getWorkspaceList = async (postData: Workspace) =>  {
    const { data, code, errorMsg } = await postFetcher(workspaces, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };
  
  export const getWorkspaceInfo = async (postData: Workspace) => {
    const { data, code, errorMsg } = await postFetcher(workspaceDetail, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };
  
  
  
  export const createWorkspaceInfo = async (postData: Workspace) => {
    const { data, code, errorMsg } = await postFetcher(createWorkspace, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };
  

  export const updateWorkspaceInfo = async (postData: Workspace) => {
    const { data, code, errorMsg } = await postFetcher(updateWorkspace, postData);
    return {
      data,
      code,
      errorMsg,
    };
  };
  