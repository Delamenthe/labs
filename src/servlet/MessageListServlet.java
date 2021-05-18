package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.ChatMessage;


@WebServlet(name = "MessageListServlet")
public class MessageListServlet extends ChatServlet {
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Установить кодировку HTTP-ответа UTF-8
        response.setCharacterEncoding("utf8");
        PrintWriter pw = response.getWriter();

        pw.println("<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/><meta http-equiv='refresh' content='10'></head>");
        pw.println("<body>");
        for (int i = messages.size() - 1; i >= 0; i--) {
            ChatMessage aMessage = messages.get(i);
            if (aMessage.getStyle().equals("normal")) {
                pw.println("<div><strong>" + aMessage.getAuthor().getName() + "</strong> : " + aMessage.getMessage() + "</div>");
            }
            if (aMessage.getStyle().equals("whisper")) {
                pw.println("<div><strong>" + aMessage.getAuthor().getName() + "</strong> : <em><small>" + aMessage.getMessage() + "</small></em></div>");
            }
            if (aMessage.getStyle().equals("scream")) {
                pw.println("<div><strong>" + aMessage.getAuthor().getName() + " :<style> .colortext { color:red;} </style> <big><big><big><span class=colortext>" + aMessage.getMessage() + "</span></big></big></big></strong></div>");
            }
        }
    }
}
