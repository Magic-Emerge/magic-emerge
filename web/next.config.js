
/** @type {import('next').NextConfig} */

const withMDX = require('@next/mdx')({
    extension: /\.mdx?$/,
    options: {
        // If you use remark-gfm, you'll need to use next.config.mjs
        // as the package is ESM only
        // https://github.com/remarkjs/remark-gfm#install
        remarkPlugins: [],
        rehypePlugins: [],
        // If you use `MDXProvider`, uncomment the following line.
        // providerImportSource: "@mdx-js/react",
    },
})

const nextConfig = {
    productionBrowserSourceMaps: false, // enable browser source map generation during the production build
    // Configure pageExtensions to include md and mdx
    pageExtensions: ['ts', 'tsx', 'js', 'jsx', 'md', 'mdx'],
    eslint: {
        // Warning: This allows production builds to successfully complete even if
        // your project has ESLint errors.
        ignoreDuringBuilds: true,
    },
    webpack(config, { dev, isServer }) {

        //生产移除console log
        if (!dev && !isServer) {
            const TerserPlugin = require('terser-webpack-plugin');

            config.optimization.minimizer.push(
                new TerserPlugin({
                    terserOptions: {
                        compress: {
                            drop_console: true,
                        },
                    },
                }),
            );
        }
        config.experiments = {
            asyncWebAssembly: true,
            layers: true,
        };
        config.module.rules.push({
            test: /\.svg$/i,
            issuer: /\.[jt]sx?$/,
            use: ['@svgr/webpack'],
        })
        return config
    },
    async rewrites() {
        return [
            {
                source: '/api/v1/:path*',
                destination: `${process.env.APP_BACKEND_URL}/:path*`
            }
        ]
    },
    // distDir: 'build',
    // docker部署时需要用到standalone
    experimental: {
        appDir: true
    },
    typescript: {
        ignoreBuildErrors: true
    },
    images: {
        remotePatterns: [
            {
                protocol: 'http',
                hostname: 'backend',
                port: '5000',
                pathname: '/profile/images/**',
            },
        ],
    },
}

module.exports = withMDX(nextConfig)
