
'use client'

import React from "react";
import Link from "next/link";
import { IconChevronRight } from "@tabler/icons-react";


type Props = {
  homeItem: BreadscrumbItem;
  breadscrumbItems: Array<BreadscrumbItem>;

};

export interface BreadscrumbItem {
  id: number;
  name: string;
  url: string;
}

export default function AppBrandcrumb({ homeItem, breadscrumbItems }: Props) {
  return (
    <nav className="flex" aria-label="Breadcrumb">
      <ol className="inline-flex items-center space-x-1">
        <li className="inline-flex items-center">
          <Link
            href={homeItem.url}
            className="inline-flex items-center text-base font-normal text-gray-primary hover:text-blue-primary dark:text-gray-400 dark:hover:text-white"
          >
            { homeItem.name }
          </Link>
        </li>
        {breadscrumbItems.map((item) => {
          return (
            <li key={item.id} className="inline-flex items-center">
              <IconChevronRight className="w-[14px] h-[14px] text-[#979797]"/>
              <Link
                href={item.url}
                replace
                className="inline-flex items-center text-base font-medium text-gray-primary hover:text-blue-primary dark:text-gray-400 dark:hover:text-white"
              >
               {item.name}
              </Link>
            </li>
          );
        })}
      </ol>
    </nav>
  );
}
