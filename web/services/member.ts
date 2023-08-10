


import {
    Members
   } from "@/types/member";
   import { createMember, memberDetail, members, updateMember } from "@/utils/api";
 
   import { postFetcher } from ".";
   
   
   export const getMembersList = async (postData: Members) =>  {
     const { data, code, errorMsg } = await postFetcher(members, postData);
     return {
       data,
       code,
       errorMsg,
     };
   };
   
   export const getMemberInfo = async (postData: Members) => {
     const { data, code, errorMsg } = await postFetcher(memberDetail, postData);
     return {
       data,
       code,
       errorMsg,
     };
   };
   
   
   
   export const createMemberInfo = async (postData: Members) => {
     const { data, code, errorMsg } = await postFetcher(createMember, postData);
     return {
       data,
       code,
       errorMsg,
     };
   };
   
 
   export const updateMemberInfo = async (postData: Members) => {
     const { data, code, errorMsg } = await postFetcher(updateMember, postData);
     return {
       data,
       code,
       errorMsg,
     };
   };
   