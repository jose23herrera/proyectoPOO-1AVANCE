package Conexion;

import java.sql.*;

public class Conexion {
    public static String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
    public static String JDBC_URL = "jdbc:mysql://localhost:3306/primecimena";
    public static String JDBC_USER = "root";
    public static String JDBC_PASS = "";
    public static Driver driver = null;
    public static synchronized Connection getConnection() throws SQLException{
        Connection conn = null;
        if(driver == null){
            try{
                Class.forName(JDBC_Driver);
            }catch (Exception e){
                System.out.println("Error al cargar el driver");
                e.printStackTrace();
            }
        }
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }//Fin de getConnection
    public static void close(ResultSet rs){
        try{
            if(rs != null){
                rs.close();
            }
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }//Fin metodo close
    public static void close(PreparedStatement stmt){
        try{
            if(stmt != null){
                stmt.close();
            }
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }//Fin metodo close
    public static void close(Connection conn){
        try{
            if(conn != null){
                conn.close();
            }
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }//Fin metodo close()
}//