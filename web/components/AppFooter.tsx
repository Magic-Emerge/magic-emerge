'use client'

import React from "react";
import Copyright from "./Copyright";
import { usePathname } from "next/navigation";
import { notShowHeaderPath } from "@/utils";

export default function AppFooter() {
  const pathname = usePathname();

  return (
    <>
      {!notShowHeaderPath.includes(pathname) && (
        <footer className="flex bg-white rounded-lg my-8 dark:bg-gray-800 ">
          <Copyright />
        </footer>
      )}
    </>
  );
}
