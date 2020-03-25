package org.wenliang;

/**
 * @author wenliang
 */
public class DbBean {

    private String driverName = "com.mysql.jdbc.Driver";

    private String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";

    private String userName = "root";

    private String password = "xy123123";

    /**
     * 连接池名字
     */
    private String poolName = "DbBeanPool";

    /**
     * 空闲池，最小连接数
     */
    private int minConnections = 1;

    /**
     * 空闲池，最大连接数
     */
    private int maxConnections = 10;

    /**
     * 初始化连接数
     */
    private int initConnections = 50;

    /**
     * 重复获得连接的频率
     */
    private long waitTimeOut = 1000;

    /**
     * 最大允许的连接数，和数据库对应
     */
    private int maxActiveConnections = 100;

    /**
     * 连接超时时间，默认20分钟
     */
    private long connectionTimeOut = 1000 * 60 * 20;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public int getMinConnections() {
        return minConnections;
    }

    public void setMinConnections(int minConnections) {
        this.minConnections = minConnections;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public int getInitConnections() {
        return initConnections;
    }

    public void setInitConnections(int initConnections) {
        this.initConnections = initConnections;
    }

    public long getConnTimeOut() {
        return waitTimeOut;
    }

    public void setConnTimeOut(long waitTimeOut) {
        this.waitTimeOut = waitTimeOut;
    }

    public int getMaxActiveConnections() {
        return maxActiveConnections;
    }

    public void setMaxActiveConnections(int maxActiveConnections) {
        this.maxActiveConnections = maxActiveConnections;
    }

    public long getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(long connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

}
