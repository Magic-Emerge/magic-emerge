
export interface ChatMessages {
    id?: string | null;
    messageId?: string;
    createAt?: string;
    conversationId?: string | null;
    rating?: 'like' | 'unlike';
    role?: 'user' | 'assistant';
    messageContent?: string;
    openUserId?: string;
    appKey?: string;
    appId?: number;
}

export interface StreamChatResponse {
    id?: string;
    event?: string;
    taskId?: string;
    createdTime?: string;
    conversationId?: string;
    openUserId?: string;
    role?: string;
    answer: string;
}



export interface ChatRequest {
    inputs: Object;
    query: string;
    responsMode: 'blocking' | 'streaming';
    conversationId?: string;
    openUserId?: string;
    appKey?: string;
    token?: string;
    workspaceId?: string;
    model: string;
}



export interface Conversations {
    id?: string | null;
    conversationName?: string;
    conversationId?: string;
    createAt?: string;
    isDeleted?: boolean;
    appId?: number;
}

export interface ConversationHistory {
    messages: Array<ChatMessages>;
    conversationDto: Conversations | null;
}

export type RatingType = "like" | "unlike";

export interface Messages {
  messageId?: string | null;
  sender: "human" | "robot";
  text: string;
  rating?: "like" | "unlike";
  conversationId?: string;
}


export interface CheckLimit {
    userId: string;
}