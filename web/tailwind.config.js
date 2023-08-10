/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: 'class',
  content: [
    './pages/**/*.{js,ts,jsx,tsx,mdx}',
    './components/**/*.{js,ts,jsx,tsx,mdx}',
    './app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  theme: {
    extend: {
      backgroundImage: {
        'gradient-radial': 'radial-gradient(var(--tw-gradient-stops))',
        'gradient-conic':
          'conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))',
      },
      colors: {
        blue: {
          primary: '#1A8BFF',
          select: '#0072E5',
          suspend: '#3399FF'
        },
        gray: {
          primary: '#45494D',
        }
      },
      boxShadow: {
        '3xl': '0px 12px 40px 0px #0065CC;',
        '4xl': '0px 1px 2px 0px #D8DFE6;'
      },
      gridTemplateColumns: {
        // Simple 16 column grid
        '16': 'repeat(16, minmax(0, 1fr))',
        '18': 'repeat(18, minmax(0, 1fr))',
        '20': 'repeat(20, minmax(0, 1fr))'
      },
      gridColumn: {
        'span-15': 'span 15 / span 15',
        'span-14': 'span 14 / span 14',
        'span-16': 'span 16 / span 16',
        'span-17': 'span 17 / span 17',
        'span-18': 'span 18 / span 18',
      }
    }
  },
  plugins: [
    require('@headlessui/tailwindcss'),
    require('@tailwindcss/typography'),
    require('tailwindcss-animate'),
  ],
}
