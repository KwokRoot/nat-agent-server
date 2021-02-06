package org.devops.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.devops.util.NativeCache;
import org.nutz.json.Json;
import org.nutz.lang.Streams;
import org.nutz.lang.util.PType;

@WebServlet(urlPatterns = "/settings")
public class SetServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();

        String postBody = new String(Streams.readBytes(request.getInputStream()));
        Map<String, String> map = Json.fromJson(new PType<Map<String, String>>() {
        }, postBody);

        //System.out.println(map.get("host"));
        NativeCache.agentHost = map.get("host");
        writer.write("ok," + NativeCache.agentHost);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.append("代理地址：" + "{\"host\": \"" + NativeCache.agentHost + "\"}");

    }
}
