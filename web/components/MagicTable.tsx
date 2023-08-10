import React, { useState } from "react";
import { HeaderType } from "@/types/app";

export interface DataRow {
  id: number;
  [key: string]: string | number;
}

interface TableProps {
  data: DataRow[];
  headers: Array<HeaderType>;
  onEdit?: (id: number | string) => void;
  onDelete?: (id: number | string) => void;
}

const MagicTable: React.FC<TableProps> = ({
  data,
  headers,
  onEdit,
  onDelete,
}) => {
  const handleOnDel = async (id: number | string) => {
    onDelete && onDelete(id);
  };

  const renderCellContent = (row: { [x: string]: any }, header: HeaderType) => {
    if (header.renderCol) {
      return header.renderCol(row[header.name]);
    }
    // 对于其他情况，只返回基本的数据内容
    return row[header.name];
  };

  return (
    <div className="relative flex flex-col w-full my-4 mx-2">
      <div className="overflow-x-auto -my-2 ">
        <div className="py-2 align-middle inline-block min-w-full">
          <div className="shadow overflow-hidden border-b border-gray-200">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  {headers.map((key, idx) => (
                    <th
                      key={idx}
                      scope="col"
                      className="py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider"
                    >
                      {key.alias}
                    </th>
                  ))}
                  <th
                    scope="col"
                    className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    操作
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {data.map((row, idx) => (
                  <tr key={idx} className={`${idx % 2 === 0 && "bg-slate-50"}`}>
                    {headers.map((header, idx) => (
                      <td
                        key={idx}
                        className={`px-6 py-4 text-center whitespace-nowrap text-sm font-sans font-normal text-gray-500`}
                      >
                        <span>{renderCellContent(row, header)}</span>
                      </td>
                    ))}
                    <td className="px-6 py-4 w-[150px] whitespace-nowrap text-left text-sm font-normal">
                      {onEdit && (
                        <button
                          className="text-sm font-sans font-normal text-blue-primary hover:text-blue-select mr-3"
                          onClick={() => onEdit(row.id)}
                        >
                          编辑
                        </button>
                      )}
                      {onDelete && (
                        <button
                          onClick={() => handleOnDel(row.id)}
                          className="text-sm font-sans font-normal text-blue-primary hover:text-blue-select"
                        >
                          删除
                        </button>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MagicTable;
