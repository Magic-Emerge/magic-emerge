"use client";

import React from "react";
import { usePathname, useRouter } from "next/navigation";
import AppBrandcrumb, { BreadscrumbItem } from "./AppBrandcrumb";
import routes from "@/routes";



type Props = {
  children: React.ReactNode;
};

export default function AppContent({ children }: Props) {
  const pathname = usePathname();

  const homeItem: BreadscrumbItem = {
    id: 1,
    name: "主页",
    url: "/admin",
  };

  const breadscrumbItems: Array<BreadscrumbItem> = [
    {
      id: 2,
      name: routes.filter(item => item.href === pathname)[0].label,
      url: `${pathname}`,
    },
  ];

  return (
    <div className="flex flex-col">
      <div className="px-6 py-4">
        <AppBrandcrumb
          homeItem={homeItem}
          breadscrumbItems={breadscrumbItems}
        />
      </div>
      <div className="border-b-[1px] border-gray-200"></div>
      {children}
    </div>
  );
}
