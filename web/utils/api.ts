

// 用户
export const user = '/user';
export const userDetail = `${user}/detail`;
export const userLogin = `${user}/login`;
export const userRegister = `${user}/register`;
export const userSendSms = `${user}/send-sms`;
export const updateUser = `${user}/update`;
export const createUser = `${user}/create`;
export const userCheckCaptcha = `${user}/validate-captcha`

// 应用
export const appStore = '/store'
export const appDetail = `${appStore}/detail`;
export const updateApp = `${appStore}/update`;
export const createApp = `${appStore}/create`;
export const deletedApp = `${appStore}/delete`;
export const getCategorys = `${appStore}/categories`;
export const starApp = `${appStore}/star`;
export const unstarApp = `${appStore}/unstar`;
export const collections = `${appStore}/collections`;

//新闻
export const appNews = '/news';
export const appNewDetail = `${appNews}/detail`;
export const createAppNews = `${appNews}/create`;
export const updateAppNews = `${appNews}/update`;


//工作空间
export const workspaces = '/workspace';
export const workspaceDetail = `${workspaces}/detail`;
export const createWorkspace = `${workspaces}/create`;
export const updateWorkspace = `${workspaces}/update`;

//对话列表
export const conversations = '/conversations';
export const conversationDetail = `${conversations}/detail`;
export const conversationCreate = `${conversations}/create`;
export const conversationUpdate = `${conversations}/update`;
export const conversationDelete = `${conversations}/delete`;


//基于dify的对话
export const messages = '/messages';
export const chatFeedback = `${messages}/feedback`;
export const getHistoryMessages = `${messages}/history`;
export const getLatestMessages = `${messages}/latest`;
export const updateMessages = `${messages}/update`;

//充值校验

export const chargeRecord = '/record';
export const checkLimit = `${chargeRecord}/check-limit`;


// 成员
export const members = '/members';
export const memberDetail = `${members}/detail`;
export const createMember = `${members}/create`;
export const updateMember = `${members}/update`;

// 分析
export const analysis = '/analysis';
export const messageCount = `${analysis}/message-count`;
export const messageToken = `${analysis}/message-token`
export const userCount = `${analysis}/user-count`

// 告警
export const alert = '/notify';
export const alertList = `${alert}/list`;
export const alertDetail = `${alert}/detail`;
export const alertUpate = `${alert}/update`;