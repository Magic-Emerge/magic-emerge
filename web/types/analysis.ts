

export interface AnalysisQuery {
    params: string;
    openUserId?: string;
}

export interface MessageCount {
    datetime: string;
    messages: number;
}


export interface MessageToken {
    datetime: string;
    tokens: number;
}


export interface ActiveUser {
    datetime: string;
    users: number;
}
