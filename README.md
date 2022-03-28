# RESTful и Консольная игра в крестики-нолики

- Игра расчитана только на двух игроков;                                                                               
- Имеется возможность записи истории игры (матча) в формате XML или JSON по определенному шаблону. Решение о записи принимают игроки в конце каждого матча. - Имеется возможность чтения истории игры из файлов XML и JSON по определенному шаблону.              
- Имеется возможность записи рейтинга в текстовый файл. Также решается икроками при выходе из игры.     

Rest:
- Для запуска REST приложения запустите класс GameXoApplication и перейдите в браузере по адресу http://localhost:8080 , где будет предложено перейти к игре или загрузить и вывести историю игры из файла XML или JSON.
- При переходе по адресу http://localhost:8080/gameplay - будет начата игра.

Консольная игра и чтение истории игры:
- Все возможные дейсвия заранее выписаны в класс "Main". При запуске начнется игра. Отдельных классов для чтения файлов не создавал, есть закомментированные варианты активации в классе Main.                                                        
