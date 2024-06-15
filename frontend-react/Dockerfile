# # Etapa 1: Construir la aplicación React
# FROM node:12.22.9

# WORKDIR /app

# # Copiar los archivos de dependencias y el package.json
# COPY package.json .
# COPY package-lock.json .

# # Instalar las dependencias del proyecto
# RUN npm install

# # Copiar el código fuente de la aplicación
# COPY . .

# # Construir la versión optimizada de producción
# #RUN npm run build

# CMD ["npm", "run", "build"]

# EXPOSE 3000

# #CMD ["npm", "start"]
##############################################################33

FROM node:18-alpine
WORKDIR /civilo-roller-fe
COPY package*.json ./
RUN npm install
EXPOSE 3000
CMD [ "npm", "start" ]
COPY . ./