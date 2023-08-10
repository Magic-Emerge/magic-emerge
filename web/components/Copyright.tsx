
'use client'

import React from 'react';

export default function Copyright() {
  return (
    <div className="flex w-full p-4 items-center justify-center">
          <span className="text-sm text-gray-500 sm:text-center dark:text-gray-400">
            © 2023{" "}
            <a href="https://offical.xingyong.tech/" className="hover:underline">
              Magic Emerge
            </a>
            . All Rights Reserved.
          </span>
    </div>
  )
}