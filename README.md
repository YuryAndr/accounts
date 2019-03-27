# accounts
### accounts-server - rmi сервер, реализающий интерфейс. В качестве БД - postgres
1) Конфигурационные параметры в resources/application.yml
2) Схема для Postgres - resources/schema.sql
3) Мониторинг - в JMX Mbeans в домене ru.andryakov.accounts.common.service.AccountService
      - addAmount - метрики для добавления amount
      - getAmount - метрики для получения amount
      - reset - операция для сброса
     
### accounts-server
1) Конфигурационные параметры в resources/application.yml
 - ``accounts.client.emulator.uri`` - подключение к серверу
 - ``accounts.client.emulator.numberOfThreadsAddAmount`` число потоков для добавления amount
 - ``accounts.client.emulator.numberOfThreadsGetAmount`` число потокв для поулчения amount
 - ``accounts.client.emulator.ids`` множетсво id в формате 1..20 или 1, 2, 3, 4, 5
