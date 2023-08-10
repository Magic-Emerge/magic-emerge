import { ChatRequest } from "@/types/chat";
import { EmergeAIStream } from "@/utils";

export const runtime = "edge";

export async function POST(req: Request): Promise<Response> {
  try {
    const chatReq = (await req.json()) as ChatRequest;
    const stream = await EmergeAIStream(chatReq);
    return new Response(stream);
  } catch (error) {
    console.error(error);
    return new Response("Error", { status: 500 });
  }
}
