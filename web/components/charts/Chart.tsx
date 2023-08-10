"use client";

import React from "react";
import ReactECharts from "echarts-for-react";
import type { EChartsOption } from "echarts";
import dayjs from "dayjs";
import { get } from "lodash-es";
import { formatNumber } from "@/utils";
import { SpinStretch } from "react-cssfx-loading";
import { ActiveUser, MessageCount, MessageToken } from "@/types/analysis";
import { IconInfoSquareRounded } from "@tabler/icons-react";

const valueFormatter = (v: string | number) => v;

const COLOR_TYPE_MAP = {
  green: {
    lineColor: "rgba(6, 148, 162, 1)",
    bgColor: ["rgba(6, 148, 162, 0.2)", "rgba(67, 174, 185, 0.08)"],
  },
  orange: {
    lineColor: "rgba(255, 138, 76, 1)",
    bgColor: ["rgba(254, 145, 87, 0.2)", "rgba(255, 138, 76, 0.1)"],
  },
  blue: {
    lineColor: "rgba(28, 100, 242, 1)",
    bgColor: ["rgba(28, 100, 242, 0.3)", "rgba(28, 100, 242, 0.1)"],
  },
};

const COMMON_COLOR_MAP = {
  label: "#9CA3AF",
  splitLineLight: "#F3F4F6",
  splitLineDark: "#E5E7EB",
};

type IColorType = "green" | "orange" | "blue";
type IChartType = "conversations" | "endUsers" | "costs";
type IChartConfigType = { colorType: IColorType; showTokens?: boolean };

const commonDateFormat = "MMM D, YYYY";
const tootipDateFormat = "YYYY-MM-D";

const CHART_TYPE_CONFIG: Record<string, IChartConfigType> = {
  conversations: {
    colorType: "green",
  },
  endUsers: {
    colorType: "orange",
  },
  costs: {
    colorType: "blue",
    showTokens: true,
  },
};

const sum = (arr: number[]): number => {
  return arr && arr.reduce((acr, cur) => {
    return acr + cur;
  });
};

const defaultPeriod = {
  start: dayjs().subtract(7, "day").format(commonDateFormat),
  end: dayjs().format(commonDateFormat),
};

export type PeriodParams = {
  name: string;
};

export type IBizChartProps = {
  period: PeriodParams;
  id?: string;
};

export type IChartProps = {
  className?: string;
  basicInfo?: { title: string; explanation: string; timePeriod: string };
  valueKey?: string;
  isAvg?: boolean;
  unit?: string;
  yMax?: number;
  chartType?: IChartType;
  chartData?:
    | Array<MessageCount>
    | Array<MessageToken>
    | Array<ActiveUser>
    | Array<{ datetime: string; count: number }>;
};

const Chart: React.FC<IChartProps> = ({
  basicInfo,
  chartType = "conversations",
  chartData,
  valueKey,
  isAvg,
  unit = "",
  yMax,
  className,
}) => {
  const statistics = chartData;
  if (statistics) {
    const statisticsLen = statistics.length || 0;
    const extraDataForMarkLine = new Array(
      statisticsLen >= 2 ? statisticsLen - 2 : statisticsLen
    ).fill("1");
    extraDataForMarkLine.push("");
    extraDataForMarkLine.unshift("");

    const xData = statistics.map(({ datetime }) => datetime);

    const yField =
      valueKey ||
      (statistics[0] &&
        Object.keys(statistics[0]).find((name) => name.includes("count"))) ||
      "";

    const yData = statistics.map((item: { [x: string]: any }) => {
      return item[yField] || 0;
    });

    const options: EChartsOption = {
      dataset: {
        dimensions: ["datetime", yField],
        source: statistics as any,
      },
      grid: { top: 8, right: 36, bottom: 0, left: 0, containLabel: true },
      tooltip: {
        trigger: "item",
        position: "top",
        borderWidth: 0,
      },
      xAxis: [
        {
          type: "category",
          boundaryGap: false,
          axisLabel: {
            color: COMMON_COLOR_MAP.label,
            hideOverlap: true,
            overflow: "break",
            formatter(value) {
              return dayjs(value).format(commonDateFormat);
            },
          },
          axisLine: { show: false },
          axisTick: { show: false },
          splitLine: {
            show: true,
            lineStyle: {
              color: COMMON_COLOR_MAP.splitLineLight,
              width: 1,
              type: [10, 10],
            },
            interval(index) {
              return index === 0 || index === xData.length - 1;
            },
          },
        },
        {
          position: "bottom",
          boundaryGap: false,
          data: extraDataForMarkLine,
          axisLabel: { show: false },
          axisLine: { show: false },
          axisTick: { show: false },
          splitLine: {
            show: true,
            lineStyle: {
              color: COMMON_COLOR_MAP.splitLineDark,
            },
            interval(index, value) {
              return !!value;
            },
          },
        },
      ],
      yAxis: {
        max: yMax ?? "dataMax",
        type: "value",
        axisLabel: { color: COMMON_COLOR_MAP.label, hideOverlap: true },
        splitLine: {
          lineStyle: {
            color: COMMON_COLOR_MAP.splitLineLight,
          },
        },
      },
      series: [
        {
          type: "line",
          showSymbol: true,
          // symbol: "circle",
          // triggerLineEvent: true,
          symbolSize: 4,
          lineStyle: {
            color:
              COLOR_TYPE_MAP[CHART_TYPE_CONFIG[chartType].colorType].lineColor,
            width: 2,
          },
          itemStyle: {
            color:
              COLOR_TYPE_MAP[CHART_TYPE_CONFIG[chartType].colorType].lineColor,
          },
          areaStyle: {
            color: {
              type: "linear",
              x: 0,
              y: 0,
              x2: 0,
              y2: 1,
              colorStops: [
                {
                  offset: 0,
                  color:
                    COLOR_TYPE_MAP[CHART_TYPE_CONFIG[chartType].colorType]
                      .bgColor[0],
                },
                {
                  offset: 1,
                  color:
                    COLOR_TYPE_MAP[CHART_TYPE_CONFIG[chartType].colorType]
                      .bgColor[1],
                },
              ],
              global: false,
            },
          },
          tooltip: {
            padding: [8, 12, 8, 12],
            formatter(params) {
              return `<div style='color:#6B7280;font-size:12px'>${dayjs(
                params.name
              ).format(tootipDateFormat)}</div>
                              <div style='font-size:14px;color:#1F2A37'>${valueFormatter(
                                (params.data as any)[yField]
                              )}
                                  ${
                                    !CHART_TYPE_CONFIG[chartType].showTokens
                                      ? ""
                                      : `<span style='font-size:12px'>
                                      <span style='margin-left:4px;color:#6B7280'>(</span>
                                      <span style='color:#FF8A4C'>~$${get(
                                        params.data,
                                        "totalprice",
                                        0
                                      )}</span>
                                      <span style='color:#6B7280'>)</span>
                                  </span>`
                                  }
                              </div>`;
            },
          },
        },
      ],
    };

    // const sumData = isAvg ? sum(yData) / yData.length : sum(yData);

    return (
      <div
        className={`flex flex-col w-full px-6 py-4 border-[0.5px] rounded-lg border-gray-200 shadow-sm ${
          className ?? ""
        }`}
      >
        <div className="group">
          <div
            className={`flex flex-row items-center text-sm font-semibold text-gray-700 group-hover:text-gray-900`}
          >
            {basicInfo?.title}
            {basicInfo?.explanation && (
              <div
                data-tooltip-id="chart-info"
                data-tooltip-content={basicInfo.explanation}
              >
                <IconInfoSquareRounded className="w-4 h-4 ml-1 my-2 text-gray-400" />
              </div>
            )}
          </div>
          <div
            className={`text-xs my-1 font-normal text-gray-500 group-hover:text-gray-700`}
          >
            {basicInfo?.timePeriod === "0"
              ? "今天"
              : `过去${basicInfo?.timePeriod}天`}
          </div>
        </div>
        <ReactECharts option={options} style={{ height: 164 }} />
      </div>
    );
  } else {
    return <SpinStretch />;
  }
};

export default Chart;
