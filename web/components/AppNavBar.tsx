"use client";

import { IconArrowBack } from "@tabler/icons-react";
import Link from "next/link";
import { usePathname, useRouter } from "next/navigation";
import React, { useEffect, useState } from "react";
import { getLoginInfo } from "@/utils/auth";
import routes from "@/routes";
import { Perms } from "@/types";
import { Tooltip } from "react-tooltip";

type Props = {};

export default function AppNavBar({}: Props) {
  const router = useRouter();
  const pathname = usePathname();
  const loggedUserStr = getLoginInfo() || "";
  const [userRoleType, setuserRoleType] = useState<Perms>("USER");

  useEffect(() => {
    if (loggedUserStr) {
      const { userRole } = loggedUserStr && JSON.parse(loggedUserStr);
      setuserRoleType(userRole);
    }
  }, [loggedUserStr]);

  const handleOnBack = () => {
    router.push("/");
  };

  return (
    <div className="flex flex-col mb-8 justify-between items-center h-full w-full">
      <div className="flex">
        <nav className="bg-white flex">
          <div className="flex flex-col my-2">
            {routes.map((item, index) => (
              <>
                {item.permissions?.includes(userRoleType) && (
                  <Link
                    key={index}
                    href={{ pathname: item.href }}
                    prefetch={false}
                  >
                    <div
                      className={`group flex flex-col items-center px-2 py-4 hover:shadow-md`}
                    >
                      <div
                        className={`${
                          item.href === pathname
                            ? "text-blue-select"
                            : "text-gray-500"
                        }  group-hover:text-blue-suspend`}
                      >
                        {item.icon}
                      </div>
                      <span
                        className={` ${
                          item.href === pathname
                            ? "text-blue-select"
                            : "text-gray-primary"
                        }  text-sm font-sans  group-hover:text-blue-suspend font-normal whitespace-nowrap`}
                      >
                        {item.label}
                      </span>
                    </div>
                    <div className="my-2 w-full h-[1px] bg-slate-200"></div>
                  </Link>
                )}
              </>
            ))}
          </div>
        </nav>
      </div>
      <div
        data-tooltip-id="back-to-index"
        data-tooltip-content="返回应用列表"
        onClick={handleOnBack}
        className="flex px-1 py-1 cursor-pointer hover:text-blue-suspend bg-slate-100 rounded shadow-md"
      >
         <IconArrowBack id="back-to-index" />
      </div>
      <Tooltip id="back-to-index" place="right"/>
    </div>
  );
}
