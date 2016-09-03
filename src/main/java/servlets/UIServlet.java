package servlets;

import java.io.IOException;
import java.io.Writer;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.event.implement.IncludeRelativePath;
import org.apache.velocity.runtime.RuntimeConstants;

/**
 * Servlet implementation class UIServlet
 */

public class UIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public volatile static VelocityEngine engine;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	private static VelocityEngine getEngine() {
		if (engine == null) {
			synchronized (UIServlet.class) {
				if (engine == null) {
					engine = new VelocityEngine();
					Properties p =new Properties();
					p.setProperty("resource.loader", "class");
					p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
					p.setProperty(RuntimeConstants.EVENTHANDLER_INCLUDE, IncludeRelativePath.class.getName());
					engine.init(p);
				}
			}
		}
		return engine;
	}
	public static void render(String template, VelocityContext context, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		Template t = getEngine().getTemplate(template, "utf-8");
		Writer writer = resp.getWriter();
		t.merge(context, writer);
		writer.flush();
		writer.close();
	}

}
