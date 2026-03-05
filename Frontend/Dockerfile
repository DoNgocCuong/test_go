# ===== Build stage =====
FROM node:20-alpine AS build
WORKDIR /app

COPY test-golden-fe ./test-golden-fe
WORKDIR /app/test-golden-fe

RUN npm install
RUN npm run build


# ===== Production stage =====
FROM nginx:alpine
COPY --from=build /app/test-golden-fe/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]