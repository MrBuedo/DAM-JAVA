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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import centro_Formacion.configDB.ConfigDB;
import centro_Formacion.modelo.Alumno;
import centro_Formacion.modelo.Modulos;

public class AccesoAlumno {

	public static boolean insertarAlumno(Alumno alumno) {
		boolean insertado = false;
		PreparedStatement ps = null;
		Connection conexion = null;
		try {

			conexion = ConfigDB.abrirConexion();

			String query = "INSERT INTO alumno(nombre, fecha_nacimiento, domicilio, telefono, correo) VALUES(?,?,?,?,?)";

			ps = conexion.prepareStatement(query);
			ps.setString(1, alumno.getNombre());
			ps.setString(2, alumno.getFecha_nacimiento());
			ps.setString(3, alumno.getDomicilio());
			ps.setString(4, alumno.getTelefono());
			ps.setString(5, alumno.getCorreo());

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

	public static boolean insertarImportados(Alumno alumno) {
		boolean insertado = false;
		PreparedStatement ps = null;
		Connection conexion = null;
		try {

			conexion = ConfigDB.abrirConexion();
			String query1 = "UPDATE alumno SET  nombre = ?, fecha_nacimiento = ?, domicilio = ?, telefono = ?, correo = ? WHERE codigo = ?";
			

			ps = conexion.prepareStatement(query1);
			
			ps.setString(1, alumno.getNombre());
			ps.setString(2, alumno.getFecha_nacimiento());
			ps.setString(3, alumno.getDomicilio());
			ps.setString(4, alumno.getTelefono());
			ps.setString(5, alumno.getCorreo());
			ps.setInt(6, alumno.getCodigo());

			int filasAfectadas = ps.executeUpdate();
			if (filasAfectadas > 0) {
				insertado = false;
			}
			else {
				String query2 = "INSERT INTO alumno(codigo,nombre, fecha_nacimiento, domicilio, telefono, correo) VALUES(?,?,?,?,?,?)";
				ps = conexion.prepareStatement(query2);
				ps.setInt(1, alumno.getCodigo());
				ps.setString(2, alumno.getNombre());
				ps.setString(3, alumno.getFecha_nacimiento());
				ps.setString(4, alumno.getDomicilio());
				ps.setString(5, alumno.getTelefono());
				ps.setString(6, alumno.getCorreo());

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

	public static List<Alumno> consultarAlumnosBasico() {
		Connection conexion = null;
		Alumno Alumno = null;
		List<Alumno> listaAlm = new ArrayList<Alumno>();

		try {
			conexion = ConfigDB.abrirConexion();
			String query = "SELECT * FROM alumno";
			Statement sentencia = conexion.createStatement();
			ResultSet alumnos = sentencia.executeQuery(query);

			while (alumnos.next()) {
				int codigo = alumnos.getInt("codigo");
				String nombre = alumnos.getString("nombre");
				String fecha_nacimiento = alumnos.getString("fecha_nacimiento");
				String domicilio = alumnos.getString("domicilio");
				String telefono = alumnos.getString("telefono");
				String correo = alumnos.getString("correo");

				Alumno = new Alumno(codigo, nombre, fecha_nacimiento, domicilio, telefono, correo);
				listaAlm.add(Alumno);

			}

			alumnos.close();
			sentencia.close();
		} catch (SQLException e) {
			System.out.println("Error al ejecutar la insercion");
			e.printStackTrace();
		} finally {
			ConfigDB.cerrarConexion(conexion);
		}

		return listaAlm;
	}

	public static Alumno consultarAlumnoXCodigo(int codigo) {

		Connection conexion = null;
		Alumno alumno = null;
		try {
			conexion = ConfigDB.abrirConexion();
			String query = "SELECT * FROM alumno WHERE codigo   = ?";
			PreparedStatement ps = conexion.prepareStatement(query);
			ps.setInt(1, codigo);

			ResultSet resultado = ps.executeQuery();
			if (resultado.next()) {
				String nombre = resultado.getString("nombre");
				String fecha_nacimiento = resultado.getString("fecha_nacimiento");
				String domicilio = resultado.getString("domicilio");
				String telefono = resultado.getString("telefono");
				String correo = resultado.getString("correo");

				alumno = new Alumno(codigo, nombre, fecha_nacimiento, domicilio, telefono, correo);

			}
		} catch (SQLException e) {
			System.out.println("Error al ejecutar la insercion");
			e.printStackTrace();
		} finally {
			ConfigDB.cerrarConexion(conexion);
		}
		return alumno;
	}

	public static boolean actualizarAlumno(Alumno alumno) {
		Connection conexion = null;
		PreparedStatement ps = null;
		boolean actualizado = false;
		try {
			conexion = ConfigDB.abrirConexion();

			String sentenciaConsultar = "UPDATE alumno SET nombre = ?,fecha_nacimiento = ?, domicilio = ?, telefono = ?, correo = ? WHERE codigo = ?";

			ps = conexion.prepareStatement(sentenciaConsultar);
			ps.setString(1, alumno.getNombre());
			ps.setString(2, alumno.getFecha_nacimiento());
			ps.setString(3, alumno.getDomicilio());
			ps.setString(4, alumno.getTelefono());
			ps.setString(5, alumno.getCorreo());
			ps.setInt(6, alumno.getCodigo());

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

	public static boolean eliminarAlumno(int codigo) {
		Connection conexion = null;
		PreparedStatement ps = null;
		boolean eliminado = false;
		try {
			conexion = ConfigDB.abrirConexion();

			String query = "DELETE FROM alumno WHERE codigo = ?";

			ps = conexion.prepareStatement(query);
			ps.setInt(1, codigo);

			int filasAfectadas = ps.executeUpdate();
			if (filasAfectadas > 0) {
				eliminado = true;
			}
		} catch (SQLException sqle) {
			System.out.println("Error de SQL: " + sqle.getMessage());
			sqle.printStackTrace();
		} finally {
			ConfigDB.cerrarConexion(conexion);
		}
		return eliminado;
	}

	public static List<Alumno> importarFichero() {
		BufferedReader flujoEntrada = null;
		Connection conexion = null;
		PreparedStatement ps = null;
		List<Alumno> alumnos = new ArrayList<Alumno>();
		try {
			

			File archivo = new File("datos/alumnos.txt");
			FileReader fr = new FileReader(archivo);
			flujoEntrada = new BufferedReader(fr);

			String linea = flujoEntrada.readLine();
			while (linea != null) {

				Alumno alumno = new Alumno(linea);
				alumnos.add(alumno);
				linea = flujoEntrada.readLine();
			}
			for (Alumno alum : alumnos) {
				insertarImportados(alum);
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
		} 

		finally {
			
			try {
				if (flujoEntrada != null) {
					flujoEntrada.close();
				}
			} catch (IOException ioe) {
				System.out.println("Error al cerrar el fichero: " + ioe.getMessage());
				ioe.printStackTrace();
			}
		}
		return alumnos;
	}
	
	public static boolean exportarAlumnos() {
		boolean exportados = false;
		BufferedWriter flujoSalida = null;

		try {

			File fichero = new File("datos/alumnos.txt");
			flujoSalida = new BufferedWriter(new FileWriter(fichero, false));

			ArrayList<Alumno> alumnos = (ArrayList<Alumno>) AccesoAlumno.consultarAlumnosBasico();

			for (Alumno alm : alumnos) {
				flujoSalida.write(alm.toStringWithSeparators());
				flujoSalida.newLine();
			}
			exportados = true;

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
		return exportados;
	}

	public static Set<Alumno> informeMultitabla(int año) {
		Connection conexion = null;
		PreparedStatement ps = null;
		Set<Alumno> alumnosConsultados = new LinkedHashSet<Alumno>();
		;

		try {

			Modulos modulo = null;

			conexion = ConfigDB.abrirConexion();
			String query = "SELECT alumno.codigo,alumno.nombre, alumno.fecha_nacimiento, modulo.nombre AS modulo \r\n"
					+ "			FROM alumno \r\n"
					+ "			INNER JOIN matricula on alumno.codigo = matricula.codigo_alumno\r\n"
					+ "			INNER JOIN linea_matricula on matricula.codigo = linea_matricula.codigo_matricula\r\n"
					+ "			INNER JOIN modulo on modulo.codigo  = linea_matricula.codigo_modulo\r\n"
					+ "			where matricula.año_academico = ? " + "			order by matricula.codigo; ";

			ps = conexion.prepareStatement(query);
			ps.setInt(1, año);

			ResultSet resultados = ps.executeQuery();

			int codigoAnterior = 0;
			Alumno alumnoActual = null;
			while (resultados.next()) {

				int codigoAlumno = resultados.getInt("codigo");

				if (codigoAlumno != codigoAnterior) {
					if (alumnoActual != null) {
						alumnosConsultados.add(alumnoActual);
					}
					String nombre = resultados.getString("nombre");
					String fechaNacimiento = resultados.getString("fecha_nacimiento");
					Set<Modulos> modulosAlumno = new LinkedHashSet<Modulos>();

					alumnoActual = new Alumno(nombre, fechaNacimiento, modulosAlumno);
					codigoAnterior = codigoAlumno;

				}
				if (alumnoActual != null) {
					alumnosConsultados.add(alumnoActual);
				}

				modulo = new Modulos(codigoAnterior, resultados.getString("modulo"));
				alumnoActual.getModulos().add(modulo);

			}

			resultados.close();
		} catch (SQLException e) {
			System.out.println("Error al ejecutar la insercion");
			e.printStackTrace();
		}

		finally {
			ConfigDB.cerrarConexion(conexion);
		}
		return alumnosConsultados;
	}
}
