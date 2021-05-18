package LabaTags;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import LabaPacks.User;
import LabaPacks.UserActiveList;
import LabaPacks.UserList;

public class Logout extends SimpleTagSupport {
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public void doTag() throws JspException, IOException {
        UserActiveList userActiveList = new UserActiveList();
        userActiveList.removeUserActive(name);
    }

}