FROM node:alpine as builder
WORKDIR '/app'
COPY ./package.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:stable
LABEL author.name="Palash Ray"
LABEL author.email="paawak@gmail.com"
COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /app/build /usr/share/nginx/html/
COPY entrypoint.sh generate_config_js.sh /
RUN chmod +x entrypoint.sh generate_config_js.sh
 
ENTRYPOINT ["/entrypoint.sh"]
