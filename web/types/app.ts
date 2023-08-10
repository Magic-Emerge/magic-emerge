import { DataRow } from "@/components/MagicTable";

export interface AppStore {
  id?: number | string | undefined;
  appName?: string;
  appKey?: string;
  appType?: number | null;
  appLevel?: number | null;
  appState?: number | null;
  appPrice?: number;
  appAvatar?: string;
  model?: string;
  author?: string;
  appVersion?: string;
  expertRating?: number;
  userRating?: number;
  tutorialProfile?: string;
  useCase?: string;
  appDesc?: string;
  isFavorite?: boolean;
  appPersonalCollectionId?: number;
}

export interface AppStorePage extends AppStore {
  page?: number;
  pageSize?: number;
}

export interface AppStoreResp {
  page?: number;
  pageSize?: number;
  records?: Array<DataRow>;
  total?: number;
}

export type AppTag = {
  id: number;
  categoryName: string;
};

export type HeaderType = {
  name: string;
  alias: string;
  renderCol?: (value: any) => React.ReactNode;
};

export interface AppNews {
    id?: number | string;
    title?: string;
    description?: string;
    linkUrl?: string;
    isPublic?: boolean;
    newsSource?: string;
    newsTime?:string;
}