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
import centro_Formacion.modelo.Matricula;



public class AccesoMatricula {

	
	public static boolean insertarMatricula(int codigo, Alumno alumno, int ano_academico, String estado,double importe) {
		Connection conexion = null;
		boolean insertado = false;
		PreparedStatement ps = null;
		try {
			conexion = ConfigDB.abrirConexion();
			

				String sentenciaInsrtMatricula = "INSERT  INTO matricula(codigo, codigo_alumno, año_academico, estado, importe) VALUES (?,?,?,?,?);";
				PreparedStatement sentencia = conexion.prepareStatement(sentenciaInsrtMatricula);
				sentencia.setInt(1, codigo);
				sentencia.setInt(2, alumno.getCodigo());
				sentencia.setInt(3, ano_academico);
				sentencia.setString(4, estado);
				sentencia.setDouble(5, importe);
				if (sentencia.executeUpdate() > 0) {
					insertado = true;
				}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			ConfigDB.cerrarConexion(conexion);
		}

		return insertado;
	}
	//Hecho por lucia
	public static boolean insertarMatriculaImportado(int codigo, Alumno alumno, int ano_academico, String estado,double importe) {
		Connection conexion = null;
		boolean insertado = false;
		PreparedStatement ps = null;
		try {
			conexion = ConfigDB.abrirConexion();
			String query1 = "UPDATE matricula SET  codigo_alumno = ?, año_academico = ?, estado = ?, importe = ? WHERE codigo = ?";
			

			ps = conexion.prepareStatement(query1);
			
			
			ps.setInt(1, alumno.getCodigo());
			ps.setInt(2, ano_academico);
			ps.setString(3, estado);
			ps.setDouble(4, importe);
			ps.setInt(5, codigo);

			int numeroFilasActualizadas = ps.executeUpdate();
			if (numeroFilasActualizadas > 0) {
				insertado = false;
			}
			else {
				String sentenciaInsrtMatricula = "INSERT  INTO matricula(codigo, codigo_alumno, año_academico, estado, importe) VALUES (?,?,?,?,?);";
				PreparedStatement sentencia = conexion.prepareStatement(sentenciaInsrtMatricula);
				sentencia.setInt(1, codigo);
				sentencia.setInt(2, alumno.getCodigo());
				sentencia.setInt(3, ano_academico);
				sentencia.setString(4, estado);
				sentencia.setDouble(5, importe);
				if (sentencia.executeUpdate() > 0) {
					insertado = true;
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			ConfigDB.cerrarConexion(conexion);
		}

		return insertado;
	}
	//Modificado por Lucía
	public static boolean actualizarMatricula(Matricula matricula, Alumno alumno) {
		Connection conexion = null;
		boolean insertado = false;
		try {
			conexion = ConfigDB.abrirConexion();
			String sentenciaUpdtMatricula = "UPDATE  matricula SET  codigo_alumno = ?, año_academico = ?, estado = ?, importe = ? WHERE CODIGO = ?;";
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaUpdtMatricula);
			
			sentencia.setInt(1,  alumno.getCodigo());
			sentencia.setInt(2, matricula.getAno_academico());
			sentencia.setString(3, matricula.getEstado());
			sentencia.setDouble(4, matricula.getImporte());
			sentencia.setInt(5, matricula.getCodigo());
			if (sentencia.executeUpdate() > 0) {
				insertado = true;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			ConfigDB.cerrarConexion(conexion);
		}
		return insertado;
	}

	public static boolean eliminarMatricula(int codigo) {
		Connection conexion = null;
		boolean insertado = false;
		try {
			conexion = ConfigDB.abrirConexion();
			String sentenciaDeleteMatricula = "DELETE FROM matricula WHERE codigo = ?;";
			PreparedStatement sentencia = conexion.prepareStatement(sentenciaDeleteMatricula);
			sentencia.setInt(1, codigo);
			if (sentencia.executeUpdate() > 0) {
				insertado = true;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			ConfigDB.cerrarConexion(conexion);
		}
		return insertado;
	}

	//Modificado por Lucía
	public static Matricula consultarMatricula(int codigo) {
		PreparedStatement ps = null;
		Connection conexion = null;
		Matricula m = null;
		try {
			// Conexi�n a la bd
			conexion = ConfigDB.abrirConexion();

			String sentenciaconsultaMatricula = "SELECT * FROM Matricula WHERE codigo = ? ";

			ps = conexion.prepareStatement(sentenciaconsultaMatricula);
			// Al primer interrogante
			ps.setInt(1, codigo);

			ResultSet resultados = ps.executeQuery();
			while (resultados.next()) {
				Alumno alumno = new Alumno(resultados.getInt("codigo_alumno"));
				m = new Matricula(resultados.getInt("codigo"), alumno,
						resultados.getInt("año_academico"), resultados.getString("estado"),
						resultados.getDouble("importe"));
			}
		} catch (SQLException e) {
			System.out.println("Error al ejecutar la consulta consultarMatriculas");
			e.printStackTrace();
		}

		finally {
			ConfigDB.cerrarConexion(conexion);
		}
		return m;

	}
	
	//hecho por Lucía----------------------------------------------------------------------
	public static List<Matricula> consultarMatriculasAlumno(int codigo) {
		PreparedStatement ps = null;
		Connection conexion = null;
		List <Matricula>  matriculas = new ArrayList<Matricula>();
		try {
			// Conexi�n a la bd
			conexion = ConfigDB.abrirConexion();

			String sentenciaconsultaMatricula = "SELECT codigo, año_academico FROM Matricula WHERE codigo_alumno = ? ";

			ps = conexion.prepareStatement(sentenciaconsultaMatricula);
			// Al primer interrogante
			ps.setInt(1, codigo);

			ResultSet resultados = ps.executeQuery();
			while (resultados.next()) {
				Matricula m = new Matricula(resultados.getInt("codigo"), resultados.getInt("año_academico"));
				matriculas.add(m);
			}
		} catch (SQLException e) {
			System.out.println("Error al ejecutar la consulta consultarMatriculas");
			e.printStackTrace();
		}

		finally {
			ConfigDB.cerrarConexion(conexion);
		}
		return matriculas;

	}
	//---------------------------------------------------------------------------
	public static List<Matricula> consultarTodasMatriculas() {

		List<Matricula> listaMatriculas = new ArrayList<Matricula>();
		PreparedStatement ps = null;

		Connection conexion = null;

		try {
			// Conexi�n a la bd
			conexion = ConfigDB.abrirConexion();

			String sentenciaconsultaMatricula = "SELECT * FROM Matricula ORDER BY nombre";

			ps = conexion.prepareStatement(sentenciaconsultaMatricula);

			ResultSet resultados = ps.executeQuery();
			while (resultados.next()) {
				Alumno alumno = new Alumno(resultados.getInt("codigo"));
				Matricula matricula = new Matricula(resultados.getInt("codigo"), alumno,
						resultados.getInt("año_academico"), resultados.getString("estado"),
						resultados.getDouble("importe"));
				listaMatriculas.add(matricula);
			}
		} catch (SQLException e) {
			System.out.println("Error al ejecutar la consulta consultarTodasMatriculas");
			e.printStackTrace();
		}

		finally {
			ConfigDB.cerrarConexion(conexion);
		}
		return listaMatriculas;
	}
	//Modificado por Lucia
	public static boolean exportarMatriculas() {
		List<Matricula> listaMatriculas = new ArrayList<Matricula>();
		boolean exportado = false;
		BufferedWriter flujoSalida = null;
		PreparedStatement ps = null;

		Connection conexion = null;
		try {
			
			// Conexi�n a la bd
			conexion = ConfigDB.abrirConexion();
			File fichero = new File("datos/matriculas.txt");
			flujoSalida = new BufferedWriter(new FileWriter(fichero, false));
			String sentenciaconsultaMatricula = "SELECT * FROM Matricula ORDER BY codigo";

			ps = conexion.prepareStatement(sentenciaconsultaMatricula);

			ResultSet resultados = ps.executeQuery();
			while (resultados.next()) {
				Alumno alumno = new Alumno(resultados.getInt("codigo"));
				Matricula matricula = new Matricula(resultados.getInt("codigo"), alumno,
						resultados.getInt("año_academico"), resultados.getString("estado"),
						resultados.getDouble("importe"));
				listaMatriculas.add(matricula);
			}
			for (Matricula matricula : listaMatriculas) {
				String linea = matricula.toStringWithSeparators();
				flujoSalida.write(linea);
				flujoSalida.newLine();
			}
		} catch (SQLException | IOException e) {
			System.out.println("Error al ejecutar la consulta consultarTodasMatriculas");
			e.printStackTrace();
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
			ConfigDB.cerrarConexion(conexion);
		}
		
		return exportado;
	}

	
	//Modificado Lucía
	public static boolean importarMatriculas() {
		BufferedReader flujoEntrada = null;
		boolean importado = false;
		try {
			File archivo = new File("datos/matriculas.txt");
			FileReader lectorFichero = new FileReader(archivo);
			flujoEntrada = new BufferedReader(lectorFichero);

			String linea = flujoEntrada.readLine();
			while (linea != null) {
				String[] datos = linea.split(";");
				int codigo = Integer.parseInt(datos[0]);
				int cdgoAlumno = (Integer.parseInt(datos[1]));
				Alumno alumno = new Alumno(cdgoAlumno);
				int ano_academico = Integer.parseInt(datos[2]);
				String estado = datos[3];
				double importe = Double.parseDouble(datos[4]);		
				
				insertarMatriculaImportado(codigo, alumno, ano_academico, estado, importe);
				linea = flujoEntrada.readLine();
							}
		} catch (FileNotFoundException fnfe) {
			System.out.println("Error de fichero no encontrado:");
			System.out.println(fnfe.getMessage());
		} catch (IOException ioe) {
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
		return importado;
	}
	//Hecho por Lucía
	public static int nuevoCodigoMatricula() {
		Connection conexion  = null;
		int codigoNuevo = 0;
		
		try {
			conexion = ConfigDB.abrirConexion();
			String query = "SELECT max(codigo) as codigo FROM matricula";
			Statement sentencia = conexion.createStatement();
			ResultSet resultado = sentencia.executeQuery(query);
			
			while(resultado.next()) {
				codigoNuevo = resultado.getInt("codigo") ;	
				codigoNuevo += 1;
			}
			
			resultado.close();
			sentencia.close();
		}catch (SQLException e) {
			System.out.println("Error al ejecutar la insercion");
			e.printStackTrace();
		}
		finally {
			ConfigDB.cerrarConexion(conexion);
		}
		return codigoNuevo;
	}
	
	

	}

	

