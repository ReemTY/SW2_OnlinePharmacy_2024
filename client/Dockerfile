FROM ubuntu:latest
LABEL authors="hagar"

ENTRYPOINT ["top", "-b"]

# Stage 1: Build react app
FROM node:16 AS build
WORKDIR /app/frontend
COPY frontend/package*.json ./
RUN npm install
COPY frontend/ ./
RUN npm run build

# Stage 2: Serve react app with nginx
FROM nginx:alpine
COPY --from=build /app/frontend/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
