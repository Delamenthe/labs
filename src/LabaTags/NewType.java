package LabaTags;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import LabaPacks.User;
import LabaPacks.UserList;
import LabaPacks.UserListHelper;
import LabaPacks.UserList.UserExistsException;

public class NewType extends SimpleTagSupport {

    // Поле данных для атрибута user
    private User type;

    // Метод-сеттер для установки атрибута (вызывается контейнером)
    public void setUser(User type) {
        this.type = type;
    }

    public void doTag() throws JspException, IOException {

        // Изначально описание ошибки = null (т.е. ошибки нет)
        String errorMessage = null;

        // Извлечь из контекста приложения общий список пользователей
        UserList userList = (UserList) getJspContext().getAttribute("users", PageContext.APPLICATION_SCOPE);

        // Проверить, что логин не пустой
        if (type.getType()==null || type.getType().equals("")) {
            errorMessage = "Select ad type";
        } else {

            // Проверить, что имя не пустое
            if (type.getName()==null || type.getName().equals("")) {
                errorMessage = "Username cannot be empty!";
            }
        }
        // Если ошибки не было - добавить пользователя
        if (errorMessage==null) {
            try {

                // Непосредственное добавление пользователя делает
                userList.addUser(type);

                // Записать обновлѐнный список пользователей в файл
                UserListHelper.saveUserList(userList);

            } catch (UserExistsException e) {}
        }
        // Сохранить описание ошибки (текст или null) в сессии
        getJspContext().setAttribute("errorMessage", errorMessage,PageContext.SESSION_SCOPE);
    }
}
