/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,ts}"],
  darkMode: "class",
  theme: {
    extend: {
      colors: {
        ck: {
          bg:           '#0e0e0e',
          surface:      '#181818',
          surfaceHover: '#222222',
          border:       '#2e2e2e',
          accent:       '#2563eb',
          accentHover:  '#1d4ed8',
          text:         '#f0f0f0',
          muted:        '#888888',
          price:        '#60a5fa',
        }
      },
      fontFamily: {
        sans: ['Inter', '-apple-system', 'BlinkMacSystemFont', 'Segoe UI', 'sans-serif'],
      },
      borderRadius: {
        DEFAULT: '4px',
        sm: '2px',
        md: '4px',
        lg: '8px',
      },
      boxShadow: {
        card: '0 1px 3px rgba(0,0,0,0.4)',
        cardHover: '0 4px 12px rgba(0,0,0,0.6)',
      },
      animation: {
        'fade-in': 'fadeIn 0.4s ease-out',
        'slide-up': 'slideUp 0.5s ease-out',
      },
      keyframes: {
        fadeIn:  { '0%': { opacity: '0' }, '100%': { opacity: '1' } },
        slideUp: { '0%': { transform: 'translateY(16px)', opacity: '0' }, '100%': { transform: 'translateY(0)', opacity: '1' } },
      }
    }
  },
  plugins: [],
};
