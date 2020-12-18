package org.devops.servlet;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import org.devops.util.NativeCache;
import org.nutz.http.Header;
import org.nutz.http.Http;
import org.nutz.http.Request;
import org.nutz.http.Response;
import org.nutz.http.Sender;
import org.nutz.lang.Streams;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.handler.MessageContext;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/*")
public class MainServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/plain; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String uri = request.getRequestURI();

        String postBody = new String(Streams.readBytes(request.getInputStream()));
        Response Response = Http.post3("http://" + NativeCache.agentHost + uri, postBody, Header.create().asJsonContentType(), 600000, 5000);
        resp.setContentType(Response.getHeader().get("Content-Type", "application/json; charset=UTF-8"));
        writer.write(Response.getContent());

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String uri = req.getRequestURI();

        Response Response = Http.get("http://" + NativeCache.agentHost + uri, 600000, 5000);
        resp.setContentType(Response.getHeader().get("Content-Type", "text/plain; charset=UTF-8"));
        writer.write(Response.getContent());

    }

}
