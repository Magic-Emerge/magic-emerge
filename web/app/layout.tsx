import { Toaster } from "react-hot-toast";
import "./globals.css";
import { Inter } from "next/font/google";
import Script from "next/script";
import AppHeader from "@/components/AppHeader";
import AppFooter from "@/components/AppFooter";
import 'react-modern-drawer/dist/index.css';
import 'react-tooltip/dist/react-tooltip.css'


const inter = Inter({ subsets: ["latin"] });


export const metadata = {
  title: "Magic Emerge",
  description: "Fast to build AI native app",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html>
      <head>
        <meta charSet="utf-8" />
        <Script src="https://g.alicdn.com/AWSC/AWSC/awsc.js"></Script>
      </head>
      <body className={inter.className}>
        <AppHeader />
        {children}
        <AppFooter />
        <Toaster reverseOrder={false} />
      </body>
    </html>
  );
}
