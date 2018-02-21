# spring-todomvc

* Сервер запускается на порту 9999
* Фронтэнд выполнен в виде одностраничного приложения `/frontend/index.html`. За основу взята реализация todomvc.com на VueJS.
* Поддерживаются все доступные в интерфейсе операции.
* Для хранения данных используется Embedded H2
* Для доступа к данным используется Hibernate
* Доступны следующие запросы к API:
  * GET /todo
  * PUT /todo
    * body: {"**title**": "...", "**completed**": false}
  * POST /todo
    * body: {"**id**": 1, "title": "...", "completed": false}
  * DELETE /todo
    * body: {"**id**": 1, "title": "...", "completed": false}
  * POST /todo/mark_all
    * body: true/false
  * POST /todo/clear_marked
