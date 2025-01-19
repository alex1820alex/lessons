# Указываем базовый образ с установленной Java
FROM openjdk
# Устанавливаем рабочую директорию
WORKDIR /app
# Копируем JAR-файл приложения в контейнер
COPY src/SimpleRSA.java /app/SimpleRSA.java
#выполняем компиляцию вашего Java файла
RUN javac SimpleRSA.java
# Указываем команду для запуска приложения
CMD ["java", "SimpleRSA"]

