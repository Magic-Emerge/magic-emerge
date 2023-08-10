import { FC, useCallback, useEffect, useState } from "react";
import Chart, { IBizChartProps } from "./Chart";
import { queryActiveUserList } from "@/services/analysis";
import { ActiveUser } from "@/types/analysis";

export const UserSatisfactionRate: FC<IBizChartProps> = async ({ period }) => {
  const [reqData, setreqData] = useState<Array<ActiveUser>>([]);

  const fetchActiveUserData = useCallback(async () => {
    const { data, code } = await queryActiveUserList({
      params: period.name,
    });
    if (code === 200) {
      setreqData(data);
    }
  }, [period.name]);

  useEffect(() => {
    fetchActiveUserData();
  }, [fetchActiveUserData]);

  return (
    <Chart
      basicInfo={{
        title: "活跃用户数",
        explanation: "含有一问一答的活跃用户数",
        timePeriod: period.name,
      }}
      chartData={reqData}
      valueKey="userscount"
      chartType="endUsers"
      isAvg
      {...((!reqData || reqData.length === 0) && { yMax: 1000 })}
    />
  );
};
