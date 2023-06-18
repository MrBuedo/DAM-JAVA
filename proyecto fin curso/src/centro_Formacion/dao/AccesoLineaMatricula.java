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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import centro_Formacion.configDB.ConfigDB;
import centro_Formacion.modelo.Alumno;
import centro_Formacion.modelo.LineaMatricula;
import centro_Formacion.modelo.Matricula;
import centro_Formacion.modelo.Modulos;

public class AccesoLineaMatricula {
	//modificado
	public static boolean insertarLineaMatricula(LineaMatricula lineaMatricula, Matricula matricula, Modulos modulo) {
		boolean insertado = false;
		PreparedStatement ps = null;
		Connection conexion = null;
		try {

			conexion = ConfigDB.abrirConexion();

			String query = "INSERT INTO linea_matricula(codigo_matricula,codigo_modulo,repeticion, calificacion_primera, calificacion_segunda) VALUES(?,?,?,?,?)";

			ps = conexion.prepareStatement(query);
			ps.setInt(1, matricula.getCodigo());
			ps.setInt(2, modulo.getCodigo());
			ps.setInt(3, lineaMatricula.getRepeticion());
			ps.setDouble(4, lineaMatricula.getCalificacionPrimera());
			ps.setDouble(5, lineaMatricula.getCalificacionSegunda());

			int filasAfectadas = ps.executeUpdate();
			if (filasAfectadas > 0) {
				insertado = true;
			}
		} catch (SQLException e) {
			System.out.println("Error al ejecutar la insercion");
			e.printStackTrace();
		}

		finally {
			ConfigDB.cerrarConexion(conexion);
		}
		return insertado;

	}
	//modificado
	public static boolean insertarLineaMatriculaImportado(LineaMatricula lineaMatricula, Matricula matricula, Modulos modulo) {
	    boolean insertado = false;
	    PreparedStatement ps = null;
	    Connection conexion = null;
	    int codigo_matricula = matricula.getCodigo();
	    int codigo_modulo = modulo.getCodigo();
	    try {

	      conexion = ConfigDB.abrirConexion();
	      String query1 = "UPDATE linea_matricula SET  repeticion = ?, calificacion_primera = ?, calificacion_segunda = ? WHERE codigo_matricula = ? AND codigo_modulo = ?";
			

			ps = conexion.prepareStatement(query1);
			
			 
		      ps.setInt(1, lineaMatricula.getRepeticion());
		      ps.setDouble(2, lineaMatricula.getCalificacionPrimera());
		      ps.setDouble(3, lineaMatricula.getCalificacionSegunda());
		      ps.setInt(4, codigo_matricula);
		      ps.setInt(5, codigo_modulo);

			int filasAfectadas = ps.executeUpdate();
			if (filasAfectadas == 0) {
			     String query2 = "INSERT INTO linea_matricula(codigo_matricula, codigo_modulo,repeticion, calificacion_primera, calificacion_segunda) VALUES(?,?,?,?,?)";
		
			      ps = conexion.prepareStatement(query2);
			      ps.setInt(1, codigo_matricula);
			      ps.setInt(2, codigo_modulo);
			      ps.setInt(3, lineaMatricula.getRepeticion());
			      ps.setDouble(4, lineaMatricula.getCalificacionPrimera());
			      ps.setDouble(5, lineaMatricula.getCalificacionSegunda());
		
			     filasAfectadas = ps.executeUpdate();
			      if (filasAfectadas > 0) {
			        insertado = true;
			      }
			}
	    } catch (SQLException e) {
	      System.out.println("Error al ejecutar la insercion");
	      e.printStackTrace();
	    }

	    finally {
	      ConfigDB.cerrarConexion(conexion);
	    }
	    return insertado;

	  }

	// Modificado por Lucía
	public static boolean eliminarLineaMatricula(Matricula matricula, Modulos m) {
		boolean eliminado = false;
		PreparedStatement ps = null;
		Connection conexion = null;
		try {

			conexion = ConfigDB.abrirConexion();

			String query = "DELETE FROM linea_matricula WHERE codigo_matricula = ? AND codigo_modulo = ?";

			ps = conexion.prepareStatement(query);
			ps.setInt(1, matricula.getCodigo());
			ps.setInt(2, m.getCodigo());

			int filasAfectadas = ps.executeUpdate();
			if (filasAfectadas > 0) {
				eliminado = true;
			}
		} catch (SQLException e) {
			System.out.println("Error al ejecutar la insercion");
			e.printStackTrace();
		}

		finally {
			ConfigDB.cerrarConexion(conexion);
		}
		return eliminado;

	}

	public static boolean actualizarLineaMatricula(int codigoMatricula, int codigoModulo, int repeticion,
			double calificacion_primera, double calificacion_segunda) {
		Connection conexion = null;
		PreparedStatement ps = null;
		boolean actualizado = false;
		try {
			conexion = ConfigDB.abrirConexion();

			String sentenciaConsultar = "UPDATE linea_matricula SET repeticion = ?, calificacion_primera = ?, calificacion_segunda = ? WHERE codigo_matricula = ? AND codigo_modulo = ?";

			ps = conexion.prepareStatement(sentenciaConsultar);
			ps.setInt(1, repeticion);
			ps.setDouble(2, calificacion_primera);
			ps.setDouble(3, calificacion_segunda);
			ps.setInt(4, codigoMatricula);
			ps.setInt(5, codigoModulo);

			int resultados = ps.executeUpdate();
			if (resultados != 0) {
				actualizado = true;
			}
		} catch (SQLException sqle) {
			System.out.println("Error de SQL: " + sqle.getMessage());
			sqle.printStackTrace();
		} finally {
			ConfigDB.cerrarConexion(conexion);
		}

		return actualizado;
	}

	public static LineaMatricula estaLineaMatricula(int codigoMatricula, int codigoModulo) {
		Connection conexion = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conexion = ConfigDB.abrirConexion();

			String sentenciaConsultar = "SELECT codigo_matricula, codigo_modulo,repeticion, calificacion_primera, calificacion_segunda FROM linea_matricula WHERE codigo_matricula = ? AND codigo_modulo = ?";

			ps = conexion.prepareStatement(sentenciaConsultar);
			ps.setInt(1, codigoMatricula);
			ps.setInt(2, codigoModulo);

			rs = ps.executeQuery();
			Matricula ma = null;
			Modulos mo = null;
			LineaMatricula lineaMatricula = null;
			if (rs.next()) {
				lineaMatricula = new LineaMatricula(ma = new Matricula(rs.getInt("codigo_matricula")),
						mo = new Modulos(rs.getInt("codigo_modulo")), rs.getInt("repeticion"),
						rs.getDouble("calificacion_primera"), rs.getDouble("calificacion_segunda"));

			}

			return lineaMatricula;

		} catch (SQLException sqle) {
			System.out.println("Error de SQL: " + sqle.getMessage());
			sqle.printStackTrace();
		} finally {
			ConfigDB.cerrarConexion(conexion);
		}

		return null;

	}

	public static List<LineaMatricula> consultarTodoLineaMatricula(int codigoMatricula) {
		List<LineaMatricula> todoLineaMatricula = new ArrayList<LineaMatricula>();
		Connection conexion = null;
		PreparedStatement ps = null;

		try {
			conexion = ConfigDB.abrirConexion();

			String sentenciaConsultar = "SELECT * FROM linea_matricula WHERE codigo_matricula = ?";
			ps = conexion.prepareStatement(sentenciaConsultar);
			ps.setInt(1, codigoMatricula);

			ResultSet resultados = ps.executeQuery();
			Matricula m = null;
			Modulos mo = null;
			while (resultados.next()) {
				LineaMatricula lineaMatricula = new LineaMatricula(
						m = new Matricula(resultados.getInt("codigo_matricula")),
						mo = new Modulos(resultados.getInt("codigo_modulo")), resultados.getInt("repeticion"),
						resultados.getDouble("calificacion_primera"), resultados.getDouble("calificacion_segunda"));
				todoLineaMatricula.add(lineaMatricula);
			}
			resultados.close();
			return todoLineaMatricula;

		} catch (SQLException sqle) {
			System.out.println("Error de SQL: " + sqle.getMessage());
			sqle.printStackTrace();
		} finally {
			ConfigDB.cerrarConexion(conexion);
		}

		return null;
	}

	public static void importarLineaMatricula() {
		BufferedReader br = null;
		Connection conexion = null;
		PreparedStatement ps = null;
		try {

			File fichero = new File("datos/lineasMatricula.txt");
			br = new BufferedReader(new FileReader(fichero));

			String linea = br.readLine();
			Matricula m = null;
			Modulos mo = null;
			

			while (linea != null) {
				String[] datos = linea.split(";");
				m = new Matricula(Integer.parseInt(datos[0]));
				mo = new Modulos(Integer.parseInt(datos[1]));
				int repeticion = Integer.parseInt(datos[2]);
				double calificacionPrimera = Double.parseDouble(datos[3]);
				double calificacionSegunda = Double.parseDouble(datos[4]);

				LineaMatricula lineaMatricula = new LineaMatricula(m, mo, repeticion, calificacionPrimera,
						calificacionSegunda);
				insertarLineaMatriculaImportado(lineaMatricula, m, mo);
				linea = br.readLine();

			}

		} catch (FileNotFoundException fnfe) {
			System.out.println("Error al abrir el fichero: " + fnfe.getMessage());
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			System.out.println("Error al leer del fichero: " + ioe.getMessage());
			ioe.printStackTrace();
		} catch (NumberFormatException nfe) {
			System.out.println("Error al convertir de cadena a numero: " + nfe.getMessage());
			nfe.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException ioe) {
				System.out.println("Error al cerrar el fichero: " + ioe.getMessage());
				ioe.printStackTrace();
			}
		}
	}

	
	// Exporta la tabla equipo a un fichero

	public static void exportarLineaMatricula() {
		BufferedWriter bw = null;
		List<LineaMatricula> lineasMatricula = consultarTodas();
		try {
			bw = new BufferedWriter(new FileWriter("datos/lineasMatricula.txt", false));
			for (int i = 0; i < lineasMatricula.size(); i++) {
				bw.write(lineasMatricula.get(i).toStringWithSeparators());
				bw.newLine();
			}

		} catch (IOException ioe) {
			System.out.println("Error al escribir en el fichero: " + ioe.getMessage());
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (IOException ioe) {
				System.out.println("Error al cerrar el fichero: " + ioe.getMessage());
				ioe.printStackTrace();
			}
		}
	}
//Hecho por lucia
	private static List<LineaMatricula> consultarTodas() {
		Connection conexion = null;
		LineaMatricula lineaMatricula = null;
		List<LineaMatricula> listaLineas = new ArrayList<LineaMatricula>();

		try {
			conexion = ConfigDB.abrirConexion();
			String query = "SELECT * FROM linea_matricula";
			Statement sentencia = conexion.createStatement();
			ResultSet lineasMatriculas = sentencia.executeQuery(query);

			while (lineasMatriculas.next()) {
				int codigoMat = lineasMatriculas.getInt("codigo_matricula");
				Matricula matricula = new Matricula(codigoMat);
				int codigoMod = lineasMatriculas.getInt("codigo_modulo");
				Modulos modulo = new Modulos(codigoMod);
				int repeticion = lineasMatriculas.getInt("repeticion");
				Double califiPrimera = lineasMatriculas.getDouble("calificacion_primera");
				Double califiSec = lineasMatriculas.getDouble("calificacion_segunda");

				lineaMatricula = new LineaMatricula(matricula, modulo, repeticion, califiPrimera, califiSec);
				listaLineas.add(lineaMatricula);

			}

			lineasMatriculas.close();
			sentencia.close();
		} catch (SQLException e) {
			System.out.println("Error al ejecutar la insercion");
			e.printStackTrace();
		} finally {
			ConfigDB.cerrarConexion(conexion);
		}

		return listaLineas;
	}

	public static List<Alumno> alumnosMatriculados(String nombreCiclo, String nombreCurso, int anoAcademico) {
		List<Alumno> todoAlumnosMatriculados = new ArrayList<Alumno>();
		Connection conexion = null;
		PreparedStatement ps = null;
		// lucia hace lista de lineas matricula y yo meto dentro del while el add para
		// añadir las lineas matricula
		try {
			conexion = ConfigDB.abrirConexion();

			String sentenciaConsultar = "SELECT alu.codigo , alu.nombre AS nombreAlumno, alu.fecha_nacimiento AS fechaNacimiento, mod.codigo AS codigoModulo, mod.nombre AS nombreModulo ,lin.calificacion_primera AS calificacionPrimera, lin.calificacion_segunda AS calificacionSegunda \r\n"
					+ "	FROM modulo mod " + "	JOIN ciclo cic ON mod.codigo_ciclo=cic.codigo"
					+ "	JOIN linea_matricula lin ON mod.codigo=lin.codigo_modulo"
					+ "	JOIN matricula mat ON lin.codigo_matricula=mat.codigo"
					+ "	JOIN alumno alu ON mat.codigo_alumno=alu.codigo" + "	WHERE cic.denominacion = ?"
					+ "	AND mod.curso = ?" + "	AND mat.año_academico = ?;";
			ps = conexion.prepareStatement(sentenciaConsultar);
			ps.setString(1, nombreCiclo);
			ps.setString(2, nombreCurso);
			ps.setInt(3, anoAcademico);

			Statement sentencia = conexion.createStatement();
			ResultSet resultados = sentencia.executeQuery(sentenciaConsultar);
			Alumno alumno = null;
			Modulos modulo = null;
			while (resultados.next()) {
				modulo = new Modulos(resultados.getInt("codigoModulo"));
				modulo.setNombre(resultados.getString("nombreModulo"));
				// generar constructor con AS y las cosas de la multitabla
				alumno = new Alumno(resultados.getString("nombreAlumno"), resultados.getString("fechaNacimiento"),
						resultados.getInt("calificacionPrimera"), resultados.getInt("calificacionSegunda"), modulo);
				todoAlumnosMatriculados.add(alumno);
			}
			resultados.close();
			sentencia.close();

		} catch (SQLException sqle) {
			System.out.println("Error de SQL: " + sqle.getMessage());
			sqle.printStackTrace();
		} finally {
			ConfigDB.cerrarConexion(conexion);
		}

		return todoAlumnosMatriculados;
	}

}
