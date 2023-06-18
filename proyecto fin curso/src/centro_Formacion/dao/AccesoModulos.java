package centro_Formacion.dao;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import centro_Formacion.configDB.ConfigDB;
import centro_Formacion.modelo.Ciclo;
import centro_Formacion.modelo.LineaMatricula;
import centro_Formacion.modelo.Modulos;
import entrada.Teclado;


public class AccesoModulos {
	
	private static final String NOMBRE_FICHERO_EMPLEADOS = "datos/modulos.txt";


	
	
	//---------------------------------------------------------------------
	
	//---------------------------------------------------------------------
	//-----------------------consultarModulos
	//---------------------------------------------------------------------
	
	//---------------------------------------------------------------------
	
	public static List<Modulos> consultarModulos() {

		List<Modulos> listaModulos = new ArrayList<Modulos>();
		PreparedStatement ps = null;

		Connection conexion = null;

		try {
			// Conexi�n a la bd
			conexion = ConfigDB.abrirConexion();

			String query = "SELECT * FROM modulo ORDER BY Codigo";

			ps = conexion.prepareStatement(query);
			// Al primer interrogante
			//ps.setString(1, ubicacion);

			ResultSet resultados = ps.executeQuery();
			while (resultados.next()) {
				int codigo = resultados.getInt("codigo");
				int codigo_ciclo = resultados.getInt("codigo_ciclo");
				String nombre = resultados.getString("nombre");
				String curso = resultados.getString("curso");
				int creditos_ects = resultados.getInt("creditos_ects");
				int horas = resultados.getInt("horas");
				
				Ciclo ciclo = new Ciclo(codigo_ciclo);
				
				Modulos mod1 = new Modulos(codigo,ciclo,nombre,curso,creditos_ects,horas);
				listaModulos.add(mod1);
			}
		} catch (SQLException e) {
			System.out.println("Error al ejecutar la consulta consultarModulos");
			e.printStackTrace();
		}

		finally {
			ConfigDB.cerrarConexion(conexion);
		}
		return listaModulos;
	}
	
	//---------------------------------------------------------------------
	
		//---------------------------------------------------------------------
		//-----------------------consultarModulosPorCodigo
		//---------------------------------------------------------------------
		
		//---------------------------------------------------------------------
	//Hecho por lucia
	public static List<Modulos> consultarModulosAlumno(List<LineaMatricula> lineas){
		List<Modulos> modAlumnos = new ArrayList<Modulos>();
		
		for(LineaMatricula lin : lineas) {
			Modulos codModulo = lin.getCodigoModulo();
			codModulo = AccesoModulos.consultarModulosPorCodigo(codModulo.getCodigo());
			modAlumnos.add(codModulo);
			
		}
		
		return modAlumnos;
	}
	public static Modulos consultarModulosPorCodigo(int codigo) {

			Modulos mod1 = null;
			PreparedStatement ps = null;

			Connection conexion = null;

			try {
				// Conexi�n a la bd
				conexion = ConfigDB.abrirConexion();

				String query = "SELECT * FROM Modulo WHERE Codigo = ? ORDER BY Codigo";

				ps = conexion.prepareStatement(query);
				ps.setInt(1, codigo);

				ResultSet resultados = ps.executeQuery();
				while (resultados.next()) {
					
					int codigo_ciclo = resultados.getInt("codigo_ciclo");
					String nombre = resultados.getString("nombre");
					String curso = resultados.getString("curso");
					int creditos_ects = resultados.getInt("creditos_ects");
					int horas = resultados.getInt("horas");
					
					Ciclo ciclo = new Ciclo (codigo_ciclo);
					
					mod1 = new Modulos(codigo,ciclo,nombre,curso,creditos_ects ,horas);
					
				}
			} catch (SQLException e) {
				System.out.println("Error al ejecutar la consulta consultarModulos");
				e.printStackTrace();
			}

			finally {
				ConfigDB.cerrarConexion(conexion);
			}
			return mod1;
		}
	
	//---------------------------------------------------------------------
	
	//---------------------------------------------------------------------
	//-----------------------insertarModulos
	//---------------------------------------------------------------------
	
	//---------------------------------------------------------------------
	
	public static int insertarModulos(int codigo_ciclo, String nombre, String curso, int creditos_ects, int horas) {
		PreparedStatement ps = null;

		Connection conexion = null;
		
		int numeroFilasActualizadas =0;

		try {
			// Conexion a la bd
			conexion = ConfigDB.abrirConexion();
			

				String query = "INSERT INTO Modulo (codigo_ciclo, nombre, curso, creditos_ects, horas) VALUES (?,?,?,?,?)";

				ps = conexion.prepareStatement(query);
				// Al primer interrogante
				ps.setInt(1, codigo_ciclo);
				ps.setString(2, nombre);
				ps.setString(3, curso);
				ps.setInt(4, creditos_ects);
				ps.setInt(5, horas);
				numeroFilasActualizadas = ps.executeUpdate();	
				
		
		} catch (SQLException e) {
			System.out.println("Error al ejecutar la consulta insertarModulos");
			e.printStackTrace();
		}

		finally {
			ConfigDB.cerrarConexion(conexion);
		}
		return numeroFilasActualizadas;
	}
	public static int insertarModulosImportado(int codigo, int codigo_ciclo, String nombre, String curso, int creditos_ects, int horas) {

		PreparedStatement ps = null;

		Connection conexion = null;
		
		int numeroFilasActualizadas =0;
		boolean insertado = false;
		try {
			// Conexion a la bd
			conexion = ConfigDB.abrirConexion();
			String query1 = "UPDATE modulo SET  nombre = ?, curso = ?, creditos_ects = ?, horas = ? WHERE codigo = ? AND codigo_ciclo = ?";
			

			ps = conexion.prepareStatement(query1);
			
			
			ps.setString(1, nombre);
			ps.setString(2, curso);
			ps.setInt(3, creditos_ects);
			ps.setInt(4, horas);
			ps.setInt(5, codigo);
			ps.setInt(6, codigo_ciclo);

			numeroFilasActualizadas = ps.executeUpdate();
			if (numeroFilasActualizadas > 0) {
				insertado = false;
			}
			else {
				String query = "INSERT INTO Modulo (codigo, codigo_ciclo, nombre, curso, creditos_ects, horas) VALUES (?,?,?,?,?,?)";

				ps = conexion.prepareStatement(query);
				// Al primer interrogante
				ps.setInt(1, codigo_ciclo);
				ps.setInt(2, codigo);
				ps.setString(3, nombre);
				ps.setString(4, curso);
				ps.setInt(5, creditos_ects);
				ps.setInt(6, horas);
				numeroFilasActualizadas = ps.executeUpdate();
				
				if (numeroFilasActualizadas > 0) {
					insertado = true;
				}
			}
				
			
			
		} catch (SQLException e) {
			System.out.println("Error al ejecutar la consulta insertarModulos");
			e.printStackTrace();
		}

		finally {
			ConfigDB.cerrarConexion(conexion);
		}
		return numeroFilasActualizadas;
		
	}
	
	//---------------------------------------------------------------------
	
		//---------------------------------------------------------------------
		//-----------------------actualizarModulosPorCodigo
		//---------------------------------------------------------------------
		
		//---------------------------------------------------------------------
		
		public static int actualizarModulosPorCodigo(int codigo, int codigo_ciclo, String nombre, String curso, int creditos_ects, int horas) {

			PreparedStatement ps = null;

			Connection conexion = null;
			
			int numeroFilasActualizadas =0;

			try {
				// Conexion a la bd
				conexion = ConfigDB.abrirConexion();

				String query = "UPDATE Modulo\n"
						+ "	SET (codigo_ciclo, nombre, curso, creditos_ects, horas)=(?,?,?,?,?)"
						+ "	WHERE codigo=?";

				ps = conexion.prepareStatement(query);
				ps.setInt(1, codigo_ciclo);
				ps.setString(2, nombre);
				ps.setString(3, curso);
				ps.setInt(4, creditos_ects);
				ps.setInt(5, horas);
				ps.setInt(6, codigo);
				numeroFilasActualizadas = ps.executeUpdate();		
				
				
			} catch (SQLException e) {
				System.out.println("Error al ejecutar la actualizacion actualizarModulosPorCodigo");
				e.printStackTrace();
			}

			finally {
				ConfigDB.cerrarConexion(conexion);
			}
			return numeroFilasActualizadas;
			
		}
	
		//---------------------------------------------------------------------
		
			//---------------------------------------------------------------------
			//-----------------------borrarModulos
			//---------------------------------------------------------------------
			
			//---------------------------------------------------------------------
			
			public static boolean borrarModulos(int codigo) {

				PreparedStatement ps = null;

				Connection conexion = null;
				
				boolean delete=false;

				try {
					// Conexion a la bd
					conexion = ConfigDB.abrirConexion();

					String query = "DELETE from Modulo WHERE codigo=?;";

					ps = conexion.prepareStatement(query);
					// Al primer interrogante
					ps.setInt(1, codigo);
					delete = ps.execute();
					
					
				} catch (SQLException e) {
					System.out.println("Error al ejecutar la actualizacion actualizarModulosPorCodigo");
					e.printStackTrace();
				}

				finally {
					ConfigDB.cerrarConexion(conexion);
				}
				return delete;
				
			}
		
		
				//---------------------------------------------------------------------
		
			//---------------------------------------------------------------------
			//-----------------------exportarDatos
			//---------------------------------------------------------------------
			
			//---------------------------------------------------------------------
  
    public static void exportarDatos (){
      
    BufferedWriter flujoSalida = null;
		
		try {
			
			File fichero = new File("datos/Modulos.txt");
			
			flujoSalida = new BufferedWriter(new FileWriter(fichero, false));
			
      ArrayList<Modulos> listaModulos =  (ArrayList<Modulos>) AccesoModulos.consultarModulos();
      
      for(int i =0; i<listaModulos.size();i++){
        Modulos mod1 = listaModulos.get(i);
        flujoSalida.write(mod1.toStringWithSeparators());
			   flujoSalida.newLine();
      }
			
			
		} 
		catch (IOException ioe) {
			System.out.println("Error al escribir en el fichero: " + ioe.getMessage());
			ioe.printStackTrace();
		}
		finally {
			try {
				if (flujoSalida != null) {
					flujoSalida.close();
				}
			}
			catch (IOException ioe) {
				System.out.println("Error al cerrar el fichero: " + ioe.getMessage());
				ioe.printStackTrace();
			}
		}


      
    }
    
  //---------------------------------------------------------------------
		
		//---------------------------------------------------------------------
		//-----------------------importarDatos
		//---------------------------------------------------------------------
		
		//---------------------------------------------------------------------

  public static ArrayList<Modulos> importarDatos (){
    
  	BufferedReader flujoEntrada = null;
		ArrayList<Modulos> listaModulos = new ArrayList<Modulos>();
		try {
			
			FileReader lectorFichero = new FileReader(NOMBRE_FICHERO_EMPLEADOS);
			flujoEntrada = new BufferedReader(lectorFichero);
			
			String linea = flujoEntrada.readLine();
			while (linea != null) {
				Modulos mod1 = new Modulos(linea);
				AccesoModulos.insertarModulosImportado(mod1.getCodigo(), mod1.getCiclo().getCodigo(),mod1.getNombre(),mod1.getCurso(),mod1.getCreditos_ects(),mod1.getHoras()); //AQUI SE INSERTA EN LA DB
				listaModulos.add(mod1);
				linea = flujoEntrada.readLine();
			}			
			
		} catch (FileNotFoundException fnfe) {
			System.out.println("importarDatos, Error de fichero no encontrado:");
			System.out.println(fnfe.getMessage());
		}  catch (IOException ioe) {
			System.out.println("Error de entrada/salida:");
			System.out.println(ioe.getMessage());
		} finally {
			if (flujoEntrada != null) {
				try {
					flujoEntrada.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return listaModulos;
	}

	
//---------------------------------------------------------------------
	
	//---------------------------------------------------------------------
	//-----------------------consultarB: modulossegúnniveldecicloycurso
	//---------------------------------------------------------------------
	
	//---------------------------------------------------------------------
	
	public static List<Modulos> consultarModulosporcicloycurso(String nivel, String curso) {

		List<Modulos> listaModulos = new ArrayList<Modulos>();
		PreparedStatement ps = null;

		Connection conexion = null;

		try {
			// Conexi�n a la bd
			conexion = ConfigDB.abrirConexion();

			String query1 = "SELECT mod.codigo, mod.nombre, mod.creditos_ects, mod.horas, cic.codigo as codigo_ciclo, cic.denominacion "
					+ "FROM modulo mod, ciclo cic ON mod.codigo_ciclo=cic.codigo "
					+ "WHERE cic.nivel = ? AND mod.curso= ? "
					+ "ORDER BY mod.nombre;";
			
			String query2 = "SELECT mod.codigo, mod.nombre,  cic.codigo as codigo_ciclo, cic.denominacion, count(alu.codigo) as numAlu"
					+ "					FROM modulo mod JOIN ciclo cic ON mod.codigo_ciclo=cic.codigo"
					+ "					JOIN linea_matricula lin ON mod.codigo=lin.codigo_modulo"
					+ "					JOIN matricula mat ON lin.codigo_matricula=mat.codigo"
					+ "					JOIN alumno alu ON mat.codigo_alumno=alu.codigo"
					+ "					WHERE cic.nivel = ? AND mod.curso= ? "
					+ "					GROUP BY mod.codigo, mod.nombre, cic.codigo, cic.denominacion"
					+ "					ORDER BY mod.nombre;";

			ps = conexion.prepareStatement(query2); 
			ps.setString(1, nivel);
			ps.setString(2, curso);

			ResultSet resultados = ps.executeQuery();
			while (resultados.next()) {				
							
				int codigo = resultados.getInt("codigo");				
				String nombre = resultados.getString("nombre");								
				//int creditos_ects = resultados.getInt("creditos_ects");
				//int horas = resultados.getInt("horas");
				
				int codigo_ciclo = resultados.getInt("codigo_ciclo");
				String denominacion = resultados.getString("denominacion");
				
				int numAlu = resultados.getInt("numAlu");
				
				Ciclo ciclo = new Ciclo(codigo_ciclo);
				ciclo.setDenominacion(denominacion);
				
				Modulos mod1 = new Modulos(codigo,ciclo,nombre,curso,numAlu);				
				listaModulos.add(mod1);
			}
		} catch (SQLException e) {
			System.out.println("Error al ejecutar la consulta consultarModulos");
			e.printStackTrace();
		}

		finally {
			ConfigDB.cerrarConexion(conexion);
		}
		return listaModulos;
	}




}	

