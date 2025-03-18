import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import path from 'path';


// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],

  define: {
    'process.env': {}, // to make sure process is available
    global: 'window', // to polyfill global
  },
  resolve: {
    alias: {
      buffer: path.resolve(__dirname, 'node_modules/buffer/'),
      process: path.resolve(__dirname, 'node_modules/process/'),
    },
  },
  optimizeDeps: {
    include: ['buffer', 'process'],
  },
})
