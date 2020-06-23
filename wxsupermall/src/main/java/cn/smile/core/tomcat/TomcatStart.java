package cn.smile.core.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/7
 */
public class TomcatStart {
	
	public static void run() throws LifecycleException, ServletException, IOException {
		InputStream resourceAsStream = TomcatStart.class.getClassLoader().getResourceAsStream("tomcat.properties");
		Properties properties = new Properties();
		properties.load(resourceAsStream);
		Tomcat tomcat = new Tomcat();
		Connector connector = tomcat.getConnector();
		connector.setURIEncoding("UTF-8");
		connector.setUseBodyEncodingForURI(true);
		tomcat.setConnector(connector);
		tomcat.setPort(8080);
		Context context = tomcat.addWebapp("/wx", (String) properties.get("docBse"));
		tomcat.start();
		tomcat.getServer().await();
	}
}
