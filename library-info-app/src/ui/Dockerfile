FROM node

RUN mkdir -p /usr/src/library-info-app

COPY dist /usr/src/library-info-app/app

COPY deploy-server.js /usr/src/library-info-app/server.js
COPY deploy-package.json /usr/src/library-info-app/package.json

WORKDIR /usr/src/library-info-app

RUN npm install

EXPOSE 3000
EXPOSE 3500

CMD ["npm", "start"]