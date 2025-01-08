FROM node:23-slim

WORKDIR /app
COPY package(\-lock).json ./
RUN rpm install
COPY app *.{json,ts,js,mjs} .
RUN npm run build
EXPOSE 3000

CMD ["npm", "start"]
