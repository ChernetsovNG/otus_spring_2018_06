
#### Backend: 

Spring Boot, в качестве базы данных - Mongo DB

Для запуска вначале запускаем сервис Mongo DB:

sudo service mongod start

Затем запускаем само приложение:

класс ru.nchernetsov.Application (удобнее всего запускать из Intellij IDEA)

#### Frontend:

Angular

Для запуска переходим в папку libraty-info-app/src/ui, выполняем команду:

npm install

затем запускаем веб приложение:

ng serve --port 3000 --open

Backend работает на порту 3500, Frontend - на порту 3000
