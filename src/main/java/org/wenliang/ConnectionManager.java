package org.wenliang;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author wenliang
 */
public class ConnectionManager {


    private  static  volatile ConnectionManager instance = null;

    public static  ConnectionManager getInstance() {

        if (instance == null) {
            synchronized (ConnectionManager.class) {
                if (instance == null) {
                    instance = new ConnectionManager();

                }

            }
        }
        return instance;

    }


    private DbBean dbBean;

    private  ConnectionPool connectionPool;


    public ConnectionManager() {
        this.dbBean = new DbBean();
        this.connectionPool = new ConnectionPool(dbBean);
    }


    /**
     * 获取连接
     * @return
     * @throws InterruptedException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Connection getConnection() throws InterruptedException, SQLException, ClassNotFoundException {
        return  connectionPool.getConnection();

    }


    public void releaseConnection(Connection connection) throws SQLException {
        connectionPool.releaseConnection(connection);
    }

}
