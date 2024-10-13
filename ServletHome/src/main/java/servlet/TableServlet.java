package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/generateTable")

public class TableServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        int rows = Integer.parseInt(request.getParameter("rows"));
        int columns = Integer.parseInt(request.getParameter("columns"));
        String bgColor = request.getParameter("bgColor");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Table Generator</title></head><body>");
        out.println("<h1>" + title + "</h1>");
        out.println("<table border='1' style='background-color:" + bgColor + ";'>");

        for (int i = 0; i < rows; i++) {
            out.println("<tr>");
            for (int j = 0; j < columns; j++) {
                out.println("<td>Cell " + (i + 1) + "," + (j + 1) + "</td>");
            }
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }

}