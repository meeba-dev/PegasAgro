# Technical task from PegasAgro:

- Написать программу, которая берет из файла nmea.log координаты и считает по ним пройденный путь.
Если скорость равна нулю, изменение координат в пройденном пути не учитывать.

- Дополнительное задание - сделать простейшую веб-форму для решения этой же задачи.

# User manual

1. Используйте данные из файла ***nmea.log*** в каталоге logs или предоставьте аналогичный дамп от другого объекта.
2. Программа рассчитывает пройденное расстояние по формуле Хаверсина:

    $2r \space arcsin \sqrt{sin²(Δlat/2) + cos(lat1) * cos(lat2) * sin²(Δlng/2)}$

3. После запуска программы в консоли вы увидите результаты расчета расстояния в формате: 
$ [время, широта, долгота, скорость] $

4. В файле логируются результаты формата:
$ [время, (координаты \space A) \& (координаты \space B), V: скорость, S: расстояние] $

5. Справочная информация для понимания гео данных:
[NMEA-0183](https://wiki.iarduino.ru/page/NMEA-0183)
