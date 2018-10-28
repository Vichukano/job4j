[![Build Status](https://travis-ci.org/Vichukano/job4j.svg?branch=master)](https://travis-ci.org/Vichukano/job4j)
[![codecov](https://codecov.io/gh/Vichukano/job4j/branch/master/graph/badge.svg)](https://codecov.io/gh/Vichukano/job4j)
# Репозиторий Vichukano
Здесь собраны задания курса программирования job4j.

Модуль intern - базовый синтаксис java, ООП.

Модуль junior - коллекции, многопоточное программирование, SQL, JDBC, Servlet.

Модуль tracker - учебное приложение, трекер заявок.

Модуль parser - приложение для парсинга вакансий с сайта sql.ru. Вакансии собираются в базу данных.
При первом запуске собирает все вакансии за 2018 год, где требуются java разработчики. 
Запускается по таймеру разв день в 12.00. При последующем запуске добавляет в базу данных только уникальные вакансии.
