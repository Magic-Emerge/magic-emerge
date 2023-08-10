
'use client'

import { ExternalLink } from './ExternalLink'
import { ArrowRightIcon } from '@heroicons/react/24/solid'

const exampleMessages = [
  {
    heading: 'Explain technical concepts',
    message: `What is a "serverless function"?`
  },
  {
    heading: 'Summarize an article',
    message: 'Summarize the following article for a 2nd grader: \n'
  },
  {
    heading: 'Draft an email',
    message: `Draft an email to my boss about the following: \n`
  }
]


interface Props {
    setInput: (message: string) => void
}


export function EmptyScreen({ setInput }: Props) {
  return (
    <div className="mx-auto max-w-2xl px-4">
      <div className="rounded-lg border bg-background p-8">
        <h1 className="mb-2 text-lg font-semibold">
          欢迎使用Emerge AI
        </h1>
        <p className="mb-2 leading-normal text-muted-foreground">
          This is an open source AI chatbot app template built with{' '}
          <ExternalLink href="https://nextjs.org">Next.js</ExternalLink> and{' '}
          <ExternalLink href="https://vercel.com/storage/kv">
            Vercel KV
          </ExternalLink>
          .
        </p>
        <p className="leading-normal text-muted-foreground">
          You can start a conversation here or try the following examples:
        </p>
        <div className="mt-4 flex flex-col items-start space-y-2">
          {exampleMessages.map((item, index) => (
            <button
              key={index}
              className="h-auto p-0 text-base"
              onClick={() => setInput(item.message)}
            >
              <ArrowRightIcon className="mr-2 text-muted-foreground" />
              {item.heading}
            </button>
          ))}
        </div>
      </div>
    </div>
  )
}
