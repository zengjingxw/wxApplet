package cn.smile.core.tomcat;

import org.apache.catalina.LifecycleException;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author smiletofotget
 * @creationTime 2020-06-2020/6/7
 */
public class Start {
	public static void main(String[] args) throws ServletException, LifecycleException, IOException {
		TomcatStart.run();
	}
}
