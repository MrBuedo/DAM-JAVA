package centro_Formacion.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import centro_Formacion.configDB.ConfigDB;
import centro_Formacion.modelo.Ciclo;
import centro_Formacion.modelo.Modulos;


public class AccesoCiclo {

	  private static final String NOMBRE_FICHERO_EMPLEADOS = "datos/ciclos.txt";

	  public static boolean insertarCiclo(String denominacion, String familia_profesional, String nivel, int horas,
	      int año_curriculo) {
	    Connection conexion = null;
	    PreparedStatement ps = null;
	    Ciclo ciclo = null;
	    boolean insertado  = false;
	    try {
	      conexion = ConfigDB.abrirConexion();

	      String query = "INSERT INTO ciclo ( denominacion, familia_profesional, nivel, horas, año_curriculo ) "
	          + "VALUES (?,?,?,?,?)";

	      ps = conexion.prepareStatement(query);
	      ps.setString(1, denominacion);
	      ps.setString(2, familia_profesional);
	      ps.setString(3, nivel);
	      ps.setInt(4, horas);
	      ps.setInt(5, año_curriculo);

	      ciclo = new Ciclo(denominacion, familia_profesional, nivel, horas, año_curriculo);
	      int filasAfectadas = ps.executeUpdate();
			if (filasAfectadas > 0) {
				insertado = true;
			}
	    } catch (SQLException sqle) {
	      System.out.println("Error de SQL: " + sqle.getMessage());
	      sqle.printStackTrace();
	    } finally {
	    	ConfigDB.cerrarConexion(conexion);
	    }
	    return insertado;
	  }

	  public static List<Ciclo> consultarCiclos() {
	    List<Ciclo> listaCiclos = new ArrayList<Ciclo>();
	    Connection conexion = null;
	    Ciclo ciclo = null;
	    try {
	      conexion = ConfigDB.abrirConexion();
	      String sentenciaConsultar = "SELECT * FROM ciclo";
	      Statement sentencia = conexion.createStatement();
	      ResultSet resultados = sentencia.executeQuery(sentenciaConsultar);
	      while (resultados.next()) {
	        int codigo = resultados.getInt("codigo");
	        String denominacion = resultados.getString("denominacion");
	        String familia_profesional = resultados.getString("familia_profesional");
	        String nivel = resultados.getString("nivel");
	        int horas = resultados.getInt("horas");
	        int año_curriculo = resultados.getInt("año_curriculo");

	        ciclo = new Ciclo(codigo, denominacion, familia_profesional, nivel, horas, año_curriculo);
	        listaCiclos.add(ciclo);
	      }

	      resultados.close();
	      sentencia.close();
	    } catch (SQLException sqle) {
	      System.out.println("Error de SQL: " + sqle.getMessage());
	      sqle.printStackTrace();
	    } finally {
	      try {
	        if (conexion != null) {
	          conexion.close();
	        }
	      } catch (SQLException sqle) {
	        System.out.println("Error al cerrar la base de datos: " + sqle.getMessage());
	        sqle.printStackTrace();
	      }
	    }

	    return listaCiclos;

	  }


	  public static List<Ciclo> consultarCicloModulo() {
	    List<Ciclo> listaCiclos = new ArrayList<Ciclo>();
	    Connection conexion = null;
	    Ciclo ciclo = null;
	    Modulos modulo = null;
	    List<Modulos> modulos = new ArrayList<Modulos>();
	    List<Modulos> modulos2 = new ArrayList<Modulos>();
	    List<Modulos> modulos3 = new ArrayList<Modulos>();
	    List<Modulos> modulos4 = new ArrayList<Modulos>();
	    int contador = 1;
	    try {
	      conexion = ConfigDB.abrirConexion();
	      for (int i = 1; i < 5; i++) {
	        String sentenciaConsultar = "SELECT modulo.codigo AS codigoModulo, nombre, modulo.horas AS horasModulo FROM ciclo"
	            + " JOIN modulo ON modulo.codigo_ciclo = ciclo.codigo WHERE ciclo.codigo = " + i;
	        Statement sentencia = conexion.createStatement();
	        ResultSet resultados = sentencia.executeQuery(sentenciaConsultar);
	        while (resultados.next()) {
	          int codigo = resultados.getInt("codigoModulo");
	          String nombre = resultados.getString("nombre");
	          int horasModulo = resultados.getInt("horasModulo");

	          String curso = "";
	          ciclo = new Ciclo(i);
	          modulo = new Modulos(codigo, ciclo, nombre, curso);
	          modulo.setHoras(horasModulo);
	          if (i == 1) {
	            modulos.add(modulo);
	          } else if (i == 2) {
	            modulos2.add(modulo);
	          } else if (i == 3) {
	            modulos3.add(modulo);
	          } else {
	            modulos4.add(modulo);
	          }

	        }
	        resultados.close();
	        sentencia.close();
	      }
	      String sentenciaConsultar = "SELECT codigo, denominacion, familia_profesional, nivel from ciclo ORDER BY denominacion";
	      Statement sentencia = conexion.createStatement();
	      ResultSet resultados = sentencia.executeQuery(sentenciaConsultar);
	      while (resultados.next()) {
	    	int codigo = resultados.getInt("codigo");
	        String denominacion = resultados.getString("denominacion");
	        String familia_profesional = resultados.getString("familia_profesional");
	        String nivel = resultados.getString("nivel");

	        if (contador == 1) {
	          ciclo = new Ciclo(codigo, denominacion, familia_profesional, nivel, modulos);
	          contador++;
	        } else if (contador == 2) {
	          ciclo = new Ciclo(codigo, denominacion, familia_profesional, nivel, modulos2);
	          contador++;
	        } else if (contador == 3) {
	          ciclo = new Ciclo(codigo, denominacion, familia_profesional, nivel, modulos3);
	          contador++;
	        } else {
	          ciclo = new Ciclo(codigo, denominacion, familia_profesional, nivel, modulos4);
	        }
	        listaCiclos.add(ciclo);

	      }

	      resultados.close();
	      sentencia.close();
	    } catch (SQLException sqle) {
	      System.out.println("Error de SQL: " + sqle.getMessage());
	      sqle.printStackTrace();
	    } finally {
	      try {
	        if (conexion != null) {
	          conexion.close();
	        }
	      } catch (SQLException sqle) {
	        System.out.println("Error al cerrar la base de datos: " + sqle.getMessage());
	        sqle.printStackTrace();
	      }
	    }

	    return listaCiclos;
	  }

	  public static List<Modulos> modulosCiclo(String nombreCiclo) {
	    Connection conexion = null;
	    Modulos modulo = null;
	    List<Modulos> modulos = new ArrayList<Modulos>();
	    try {
	      conexion = ConfigDB.abrirConexion();
	      String sentenciaConsultar = "SELECT modulo.codigo AS codigoModulo, codigo_cilo AS cicloPert, nombre, curso, creditos_ects, modulo.horas AS horasModulo"
	          + "FROM ciclo JOIN modulo ON codigoPert = ciclo.codigo WHERE denominacion = " + nombreCiclo;
	      Statement sentencia = conexion.createStatement();
	      ResultSet resultados = sentencia.executeQuery(sentenciaConsultar);
	      while (resultados.next()) {
	        int codigoModulo = resultados.getInt("codigoModulo");
	        int cicloPert = resultados.getInt("cicloPert");
	        String nombre = resultados.getString("nombre");
	        String curso = resultados.getString("curso");
	        int creditos_ects = resultados.getInt("creditos_ects");
	        int horasModulo = resultados.getInt("horasModulo");

	        modulo = new Modulos(codigoModulo, cicloPert, nombre, curso, creditos_ects, horasModulo);
	        modulos.add(modulo);
	      }
	      resultados.close();
	      sentencia.close();
	    } catch (SQLException sqle) {
	      System.out.println("Error de SQL: " + sqle.getMessage());
	      sqle.printStackTrace();
	    } finally {
	      try {
	        if (conexion != null) {
	          conexion.close();
	        }
	      } catch (SQLException sqle) {
	        System.out.println("Error al cerrar la base de datos: " + sqle.getMessage());
	        sqle.printStackTrace();
	      }
	    }

	    return modulos;
	  }

	  public static Ciclo consultarCicloXCodigo( int codigo) {
	    Connection conexion = null;
	    Ciclo ciclo = null;
	    try {
	      conexion = ConfigDB.abrirConexion();
	      String sentenciaConsultar = "SELECT * FROM ciclo WHERE codigo = " + codigo;
	      Statement sentencia = conexion.createStatement();
	      ResultSet resultados = sentencia.executeQuery(sentenciaConsultar);
	      while (resultados.next()) {
	        String denominacion = resultados.getString("denominacion");
	        String familia_profesional = resultados.getString("familia_profesional");
	        String nivel = resultados.getString("nivel");
	        int horas = resultados.getInt("horas");
	        int año_curriculo = resultados.getInt("año_curriculo");

	        ciclo = new Ciclo(codigo, denominacion, familia_profesional, nivel, horas, año_curriculo);
	      }

	      resultados.close();
	      sentencia.close();
	    } catch (SQLException sqle) {
	      System.out.println("Error de SQL: " + sqle.getMessage());
	      sqle.printStackTrace();
	    } finally {
	      try {
	        if (conexion != null) {
	          conexion.close();
	        }
	      } catch (SQLException sqle) {
	        System.out.println("Error al cerrar la base de datos: " + sqle.getMessage());
	        sqle.printStackTrace();
	      }
	    }

	    return ciclo;

	  }

	  public static boolean actualizarCiclo(int codigo, String denominacion, String familia_profesional, String nivel,
	      int horas, int año_curriculo) {
	    Connection conexion = null;
	    try {
	      conexion = ConfigDB.abrirConexion();
	      String sentenciaActualizar = "UPDATE ciclo " + "SET denominacion = '" + denominacion
	          + "', familia_profesional = '" + familia_profesional + "', nivel = '" + nivel + "', horas = "
	          + horas + ", año_curiculo = " + año_curriculo + "WHERE codigo = " + codigo;
	      Statement sentencia = conexion.createStatement();
	      int filasActualizadas = sentencia.executeUpdate(sentenciaActualizar);
	      if (filasActualizadas == 0) {
	        return false;
	      }
	    } catch (SQLException sqle) {
	      System.out.println("Error de SQL: " + sqle.getMessage());
	      sqle.printStackTrace();
	    } finally {
	      try {
	        if (conexion != null) {
	          conexion.close();
	        }
	      } catch (SQLException sqle) {
	        System.out.println("Error al cerrar la base de datos: " + sqle.getMessage());
	        sqle.printStackTrace();
	      }
	    }
	    return true;
	  }

	  public static boolean borrarCiclo(int codigo) {
	    Connection conexion = null;
	    try {
	      conexion = ConfigDB.abrirConexion();
	      String sentenciaActualizar = "DELETE from ciclo WHERE codigo = " + codigo;
	      Statement sentencia = conexion.createStatement();
	      int filasActualizadas = sentencia.executeUpdate(sentenciaActualizar);
	      if (filasActualizadas == 0) {
	        return false;
	      }
	    } catch (SQLException sqle) {
	      System.out.println("Error de SQL: " + sqle.getMessage());
	      sqle.printStackTrace();
	    } finally {
	      try {
	        if (conexion != null) {
	          conexion.close();
	        }
	      } catch (SQLException sqle) {
	        System.out.println("Error al cerrar la base de datos: " + sqle.getMessage());
	        sqle.printStackTrace();
	      }
	    }
	    return true;
	  }

	  public static void exportarCiclos() {
	    BufferedWriter flujoSalida = null;

	    try {

	      File fichero = new File("datos/Ciclos.txt");

	      flujoSalida = new BufferedWriter(new FileWriter(fichero, false));

	      ArrayList<Ciclo> listaCiclos = (ArrayList<Ciclo>) AccesoCiclo.consultarCiclos();

	      for (int i = 0; i < listaCiclos.size(); i++) {
	        Ciclo ciclo = listaCiclos.get(i);
	        flujoSalida.write(ciclo.toStringWithSeparators());
	        flujoSalida.newLine();
	      }

	    } catch (IOException ioe) {
	      System.out.println("Error al escribir en el fichero: " + ioe.getMessage());
	      ioe.printStackTrace();
	    } finally {
	      try {
	        if (flujoSalida != null) {
	          flujoSalida.close();
	        }
	      } catch (IOException ioe) {
	        System.out.println("Error al cerrar el fichero: " + ioe.getMessage());
	        ioe.printStackTrace();
	      }
	      
	    }
	  }
//Modificado por lucia
	  public static ArrayList<Ciclo> importarCiclos() {
		Connection conexion = null;
		PreparedStatement ps = null;
	    BufferedReader flujoEntrada = null;
	    ArrayList<Ciclo> listaCiclos = new ArrayList<Ciclo>();
	    try {
	    	
	      FileReader lectorFichero = new FileReader(NOMBRE_FICHERO_EMPLEADOS);
	      flujoEntrada = new BufferedReader(lectorFichero);

	      String linea = flujoEntrada.readLine();
	      while (linea != null) {
	        Ciclo ciclo = new Ciclo(linea);
	        insertarCiclo(ciclo);
	        listaCiclos.add(ciclo);
	        linea = flujoEntrada.readLine();
	      }
	    } catch (FileNotFoundException fnfe) {
	      System.out.println("importarDatos, Error de fichero no encontrado:");
	      System.out.println(fnfe.getMessage());
	    }catch (IOException ioe) {
	      System.out.println("Error de entrada/salida:");
	      System.out.println(ioe.getMessage());
	    } finally {
	      if (flujoEntrada != null) {
	        try {
	          flujoEntrada.close();
	        } catch (IOException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	        }
	      }
	    }
	    return listaCiclos;
	  }
		private static boolean  insertarCiclo(Ciclo ciclo) {
			Connection conexion = null;
		    PreparedStatement ps = null;
		    
		    boolean insertado  = false;
		    try {
		      conexion = ConfigDB.abrirConexion();

		      String query1 = "UPDATE  ciclo SET  denominacion = ?, familia_profesional = ?, nivel = ?, horas = ?, año_curriculo = ? WHERE codigo = ? ";

		      ps = conexion.prepareStatement(query1);
		     
		      ps.setString(1, ciclo.getDenominacion());
		      ps.setString(2, ciclo.getFamilia_profesional());
		      ps.setString(3, ciclo.getNivel());
		      ps.setInt(4, ciclo.getHoras());
		      ps.setInt(5, ciclo.getAño_curriculo());
		      ps.setInt(6, ciclo.getCodigo());

		      int filasAfectadas = ps.executeUpdate();
				if (filasAfectadas > 0) {
					insertado = false;
				}else {
					String query2 = "INSERT INTO ciclo ( codigo, denominacion, familia_profesional, nivel, horas, año_curriculo ) "
					          + "VALUES (?,?,?,?,?,?)";
					ps = conexion.prepareStatement(query2);
				      ps.setInt(1, ciclo.getCodigo());
				      ps.setString(2, ciclo.getDenominacion());
				      ps.setString(3, ciclo.getFamilia_profesional());
				      ps.setString(4, ciclo.getNivel());
				      ps.setInt(5, ciclo.getHoras());
				      ps.setInt(6, ciclo.getAño_curriculo());
				      filasAfectadas = ps.executeUpdate();
						if (filasAfectadas > 0) {
							insertado = true;
						}
				}
		    } catch (SQLException sqle) {
		      System.out.println("Error de SQL: " + sqle.getMessage());
		      sqle.printStackTrace();
		    } finally {
		    	ConfigDB.cerrarConexion(conexion);
		    }
		    return insertado;
	
}

		
}