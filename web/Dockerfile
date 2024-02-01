# ---- Base Node ----
FROM node:18.5.0-alpine AS base
WORKDIR /app
COPY package*.json ./

# ---- Dependencies ----
RUN npm install -g npm@9.8.1
FROM base AS dependencies
# 国内换成淘宝源
RUN  npm install --registry=http://registry.npmmirror.com

# ---- Build ----
FROM dependencies AS build
COPY . .
RUN npm run build

# ---- Production ----
FROM node:18.5.0-alpine AS production
WORKDIR /app
COPY --from=dependencies /app/node_modules ./node_modules
COPY --from=build /app/.next ./.next
COPY --from=build /app/public ./public
COPY --from=build /app/package*.json ./
COPY --from=build /app/next.config.js ./next.config.js
COPY --from=build /app/.env ./.env

# Expose the port the app will run on
EXPOSE 3000

# Start the application
CMD ["npm", "start"]
