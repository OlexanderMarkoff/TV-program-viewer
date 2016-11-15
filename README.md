*************************************************************************************************
Зроблено:

Дані загружаються асинхронно і записуються у локальну SQL базу даних і вже звідти показуються користувачеві. Спочатку завантажити програму передач на сьогодні
*Опціонально. Для роботи з базою даних використати ContentProvider.

На головному екрані додатку розміщені TabLayout з назвами каналів і  ViewPager з програмами для цих каналів. По замовчуванню показується програма на сьогодні.
*Опціонально виділяти канали які знаходяться в обраному.


Меню складається з наступних пунктів:
-	список обраних каналів;
-	програма телепередач.
*Опціонально використати NavigationDrawer

https://drive.google.com/file/d/0B0kJsJ4V3BTYM1lSY2hWVzlUXzg/view?usp=sharing

*************************************************************************************************

Написати андроїд додаток для показу програми телеканалів.


Як джерело даних використати:
http://194.44.253.78:8090/ChanelAPI/chanels - список каналів
http://194.44.253.78:8090/ChanelAPI/categories - список категорій
http://194.44.253.78:8090/ChanelAPI/programs/<timestamp> - список програм для вказаного дня заданого (Unix Timestamp)


Дані загружаються асинхронно і записуються у локальну SQL базу даних і вже звідти показуються користувачеві. Спочатку завантажити програму передач на сьогодні, а потім на тиждень/місяць.

*Опціонально. Синхронізувати базу даних за запитом користувача

*Опціонально. Синхронізувати дані за сьогодні декілька разів на день автоматично

*Опціонально. Показувати прогрес завантаження/синхронізації.

*Опціонально. Показувати Notification поки виконується завантаження.

*Опціонально. Для роботи з базою даних використати ContentProvider.


На головному екрані додатку розміщені TabLayout з назвами каналів і  ViewPager з програмами для цих каналів. По замовчуванню показується програма на сьогодні.

*Опціонально можливість вибрати дату із доступних у базі, для якої показується програма телепередач.

*Опціонально виділяти канали які знаходяться в обраному.

*Опціонально сортування вкладок з каналами.


Меню складається з наступних пунктів:
категорії каналів;
список всіх каналів;
список обраних каналів;
програма телепередач.

*Опціонально використати NavigationDrawer

На екрані з категоріями відображаються всі категорії телеканалів. При кліку на категорію відображаються телеканали вибраної категорії з можливістю додати канал до обраних або видалити його звідти. Для списку всіх каналів і обраних каналів дії аналогічні.


