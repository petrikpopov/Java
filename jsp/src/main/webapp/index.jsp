<%@ page import="java.util.*, java.text.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/table.css">
    <title>Таблица пользователей</title>
</head>
 
<body>
    <div class="container">
        <%-- блок формы заполнения данных --%>
        <!-- на строчку выше - пример комментария JSP -->
        <div class="block">
            <h2>Добавить нового пользователя</h2>
            <form name="f1" method="post" class="user-form">
                <label for="name">Имя пользователя:</label>
                <input id="name" name="name" type="text" maxlength="20" value="Alexander"/>
                
                <label for="email">E-mail:</label>
                <input id="email" name="email" type="email" maxlength="20" value="sunmeatrich@gmail.com"/>
                
                <input type="submit" name="submit" value="Добавить пользователя" class="submit-button" />
            </form>
        </div>

        <%-- блок таблицы пользователей --%>
        <div class="block">
            <h2>Существующие пользователи:</h2>
            
            <%-- инициализация (объявления JSP) --%>
            <%! List<String> name_array = new ArrayList<String>(); int x = 10; %>
            <%! List<String> email_array = new ArrayList<String>(); %>
            <%! List<Date> reg_date_array = new ArrayList<Date>(); %>

            <%-- проверка на наличие параметров формы и добавление их в списки --%>
            <%-- считывание параметров запроса (скриплеты JSP) --%>
            <% 
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String submit = request.getParameter("submit");

               if (submit != null) {
                	 if (name != null && !name.trim().isEmpty() && email != null && !email.trim().isEmpty()) {
                		 if (email.endsWith("@mail.ru")) {
                             response.sendRedirect("errorMailRu.jsp");
                             return;
                         }
                		 name_array.add(name);
                         email_array.add(email);
                         reg_date_array.add(new Date());
                     }
                     else {
                    	 if (name == "")
                     	 	out.println("<b>не указан логин!</b><br />");
                    	 if (email == "")
                      	 	out.println("<b>не указана почта!</b><br />");
                     }
                }
            %>

            <table class="user-table">
                <%-- названия колонок в таблице --%>
                <tr>
                    <th>Имя</th>
                    <th>Почта</th>
                    <th>Время регистрации</th>
                </tr>
                <%-- заполнение таблицы --%>
                <% 
                    // определение формата даты и времени для Украины
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", new Locale("uk", "UA"));
                %>
                <% for (int i = 0; i < name_array.size(); ++i) { %>
                    <tr>
                        <td><%= name_array.get(i) %></td>
                        <td><%= email_array.get(i) %></td>
                        <td><%= sdf.format(reg_date_array.get(i)) %></td>
                    </tr>
                <% } %>
            </table>
        </div>
    </div>
</body>
</html>