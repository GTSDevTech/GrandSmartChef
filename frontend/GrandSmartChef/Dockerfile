# Etapa de build
FROM node:22 AS build
WORKDIR /app

# Copia package.json y package-lock.json e instala dependencias
COPY package*.json ./
RUN npm install

# Copia todo el proyecto y genera el build de producción
COPY . .
RUN npm run build -- --configuration=production

# Etapa de producción con Nginx
FROM nginx:alpine

# Copia configuración de Nginx mejorada
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Asegúrate de copiar desde la carpeta correcta de build
# Para Ionic/Angular, normalmente es 'www' o 'dist'
COPY --from=build /app/www /usr/share/nginx/html
RUN chmod -R 644 /usr/share/nginx/html/assets/ && \
    find /usr/share/nginx/html/assets/ -type d -exec chmod 755 {} \;


# Expone el puerto 80
EXPOSE 80
# Comando para iniciar Nginx
CMD ["nginx", "-g", "daemon off;"]
