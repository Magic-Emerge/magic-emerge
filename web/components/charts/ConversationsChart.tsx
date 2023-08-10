import { queryMessageCountList } from "@/services/analysis";
import Chart, { IBizChartProps } from "./Chart";
import { FC, useCallback, useEffect, useState } from "react";
import { MessageCount } from "@/types/analysis";



export const ConversationsChart: FC<IBizChartProps> = async ({ period }) => {
  const [reqData, setreqData] = useState<Array<MessageCount>>([]);


  const fetchMessagecountData = useCallback(async () => {
    const { data, code } = await queryMessageCountList({
      params: period.name,
    });
    if (code === 200) {
        setreqData(data)
    }
  }, [period.name]);

  useEffect(() => {
    fetchMessagecountData()
  }, [fetchMessagecountData]);

  return (
    <Chart
      basicInfo={{
        title: "全部消息数",
        explanation: "AI每回答一条用户的问题算一条消息",
        timePeriod: period.name,
      }}
      chartData={reqData}
      chartType="conversations"
      {...((!reqData || reqData.length === 0) && { yMax: 500 })}
    />
  );
};
