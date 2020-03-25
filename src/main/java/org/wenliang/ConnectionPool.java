package org.wenliang;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * 数据库连接池 1.初始化连接池 根据初始化连接数，创建连接放到空闲池中 2.创建获取连接getConnection方法 判断是否小于最大连接数 小于==》判断空连接池是否有连接
 *
 * @author wenliang
 */
public class ConnectionPool {

    /**
     * 空闲连接池
     */
    private List<Connection> freeConnection;

    /**
     * 活动线程
     */
    private List<Connection> activeConnection;


    private DbBean bean;

    private volatile int connNum;

    public ConnectionPool(DbBean bean) {
        this.bean = bean;
        this.freeConnection = new CopyOnWriteArrayList<Connection>();
        this.activeConnection = new CopyOnWriteArrayList<Connection>();
        this.connNum = 0;
        for (int i = 0; i < bean.getInitConnections(); i++) {
            try {
                Connection connection = createConnect();
                if (connection != null) {
                    //放入空闲队列当中去
                    freeConnection.add(connection);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * 创建连接
     *
     * @return
     */
    private Connection createConnect() throws SQLException, ClassNotFoundException {
        synchronized (this) {
            Connection connection = null;
            Class.forName(bean.getDriverName());
            connection = DriverManager.getConnection(bean.getUrl(), bean.getUserName(), bean.getPassword());
            //记录连接数
            connNum++;
            return connection;
        }
    }


    /**
     * 2.创建获取连接getConnection方法 判断是否小于最大活动连接数 小于==》判断空闲连接池是否存有连接 有==》直接取出放到活动连接池中，然后空闲连接池删除 无==》创建新的连接，放到活动连接池中
     * 大于==》等待，重试 3.释放连接 回收 判断连接是否可用 ==>判断空闲线程是否已满 没满==》回收连接，放到空闲线程池中 已满==》关闭连接 活动连接池移除该连接
     */
    public Connection getConnection() throws SQLException, ClassNotFoundException, InterruptedException {
        synchronized (this) {
            Connection connection = null;
            //判读是否小于最大连接数
            if (connNum <= bean.getMaxActiveConnections()) {
                //判断空闲连接池是够有连接
                if (freeConnection.size() > 0) {
                    connection = freeConnection.remove(0);
                }
//                connection = createConnect();
                //判断连接是否可用
                if (isAvailable(connection)) {
                    activeConnection.add(connection);
                }
                connNum--;
//                connection = getConnection();
            }
            wait(bean.getConnTimeOut());
            //重新获取连接
            //connection = getConnection();
            return connection;
        }

    }


    /**
     * 判断连接是否可用
     *
     * @param connection
     * @return
     */
    private boolean isAvailable(Connection connection) throws SQLException {
        if (connection == null || connection.isClosed()) {
            return false;
        }
        return true;
    }

    /**
     * 释放连接
     *
     * @param connection
     */
    public void releaseConnection(Connection connection) throws SQLException {
        synchronized (this) {
            if (isAvailable(connection)) {
                //判断空闲连接池是够已满
                if (freeConnection.size() < bean.getMaxConnections()) {
                    //空闲连接池没有满
                    freeConnection.add(connection);
                }
                //已满，关闭连接
                connection.close();
                activeConnection.remove(connection);
                connNum--;
                notifyAll();
            }
        }
    }

}
