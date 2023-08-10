
'use client'

import React from 'react'
import { SpinStretch } from 'react-cssfx-loading'


type Props = {}

export default function Loading({}: Props) {
  return (
    <div className='flex flex-col justify-center align-middle items-center text-blue-primary'>
        <SpinStretch duration={'3s'} width="50px" height="50px"  />
    </div>
  )
}