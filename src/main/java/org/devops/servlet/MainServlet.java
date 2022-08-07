package org.devops.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.devops.util.NativeCache;
import org.nutz.http.Header;
import org.nutz.http.Http;
import org.nutz.http.Request;
import org.nutz.http.Response;
import org.nutz.http.Sender;
import org.nutz.lang.Streams;
import org.nutz.lang.Strings;

@WebServlet(urlPatterns = "/*")
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/plain; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String uri = req.getRequestURI();
        String urlParam = Strings.isBlank(req.getQueryString()) ? "" : "?" + req.getQueryString();

        String postBody = new String(Streams.readBytes(req.getInputStream()));
        Response Response = Http.post3("http://" + NativeCache.agentHost + uri + urlParam, postBody, Header.create().asJsonContentType(), 600000, 5000);
        resp.setContentType(Response.getHeader().get("Content-Type", "application/json; charset=UTF-8"));
        writer.write(Response.getContent());

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String uri = req.getRequestURI();
        String urlParam = Strings.isBlank(req.getQueryString()) ? "" : "?" + req.getQueryString();

        Response Response = Http.get("http://" + NativeCache.agentHost + uri + urlParam, 600000, 5000);
        resp.setContentType(Response.getHeader().get("Content-Type", "text/plain; charset=UTF-8"));
        writer.write(Response.getContent());

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String uri = req.getRequestURI();
        String urlParam = Strings.isBlank(req.getQueryString()) ? "" : "?" + req.getQueryString();

        Request request = Request.create("http://" + NativeCache.agentHost + uri + urlParam, Request.METHOD.DELETE);
        Sender sender = Sender.create(request, 60000);
        Response Response = sender.send();
        resp.setContentType(Response.getHeader().get("Content-Type", "text/plain; charset=UTF-8"));
        writer.write(Response.getContent());
    }
    
    
}
