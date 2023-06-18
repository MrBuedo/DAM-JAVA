package centro_Formacion.configDB;

import java.sql.Connection;

import java.sql.SQLException;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;


public class ConfigDB {
	
	private static final String DRIVER = "org.sqlite.JDBC";
	private static final String URLBD = "jdbc:sqlite:db\\centro_formativo.db";

	public static Connection abrirConexion() {
		Connection conexion = null;

		try {
			
			
			Class.forName(DRIVER);
			SQLiteConfig config = new SQLiteConfig();
			config.enforceForeignKeys(true);
			SQLiteDataSource ds = new SQLiteDataSource(config);
		    ds.setUrl(URLBD);
			conexion = ds.getConnection();
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al cargar driver" + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al obtener la conexi�n a la bd" + e.getMessage());
			e.printStackTrace();
		}

		return conexion;

	}

	public static void cerrarConexion(Connection conexion) {
	
		try {
			conexion.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
