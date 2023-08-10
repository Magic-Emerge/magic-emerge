import { request } from "@/utils/request";


export const postFetcher = async (url: string, data: Object) => {
    const response = await request({ url: url, data, method: "POST" });
    return response;
};