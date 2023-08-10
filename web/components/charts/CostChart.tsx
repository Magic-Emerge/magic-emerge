import { FC, useCallback, useEffect, useState } from "react";
import Chart, { IBizChartProps } from "./Chart";
import { queryMessageTokenList } from "@/services/analysis";
import { MessageToken } from "@/types/analysis";

export const CostChart: FC<IBizChartProps> = async ({ period }) => {
  const [reqData, setreqData] = useState<Array<MessageToken>>([]);

  const fetchMessagetokenData = useCallback(async () => {
    const { data, code } = await queryMessageTokenList({
      params: period.name,
    });
    if (code === 200) {
      setreqData(data);
    }
  }, [period.name]);

  useEffect(() => {
    fetchMessagetokenData();
  }, [fetchMessagetokenData]);

  return (
    <Chart
      basicInfo={{
        title: "费用消耗",
        explanation: "请求GPT的Tokens花费，用于成本控制",
        timePeriod: period.name,
      }}
      chartData={reqData}
      chartType="costs"
      {...((!reqData || reqData.length === 0) && { yMax: 100 })}
    />
  );
};
