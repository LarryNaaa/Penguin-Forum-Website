package web.listener;

import web.dal.ConnectionManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebListener
public class OneListener implements ServletContextListener {

    protected ConnectionManager connectionManager;
    public OneListener() {
        connectionManager = new ConnectionManager();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Map<Connection, Boolean> map = new HashMap<>();
        int n = 20;
        for(int i = 0; i <= n; i++){
            try {
                Connection connection = connectionManager.getConnection();
                //System.out.println("服务器启动创建connection "+connection);
                map.put(connection, true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("connectionsMap", map);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        Map map = (Map) servletContext.getAttribute("connectionsMap");

    }
}
