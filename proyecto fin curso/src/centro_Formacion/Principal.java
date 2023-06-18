package centro_Formacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import centro_Formacion.dao.AccesoAlumno;
import centro_Formacion.dao.AccesoCiclo;
import centro_Formacion.dao.AccesoLineaMatricula;
import centro_Formacion.dao.AccesoMatricula;
import centro_Formacion.dao.AccesoModulos;
import centro_Formacion.modelo.Alumno;
import centro_Formacion.modelo.Ciclo;
import centro_Formacion.modelo.LineaMatricula;
import centro_Formacion.modelo.Matricula;
import centro_Formacion.modelo.Modulos;
import entrada.Teclado;

public class Principal {
	public static void main(String[] args) {
		int opcion = -1;

		do {
//--------->MENU PRINCIPAL
			opcion = menu();
			switch (opcion) {
			case 0:
				break;
			case 1:
				// ----------->VER CICLOS CON SUS MODULOS
				List<Ciclo> ciclosModulos = AccesoCiclo.consultarCicloModulo();
				for (Ciclo c : ciclosModulos) {
					System.out.println(c);
				}
				break;

			case 2:
				// ----------->VER ORLA
				int año = Teclado.leerEntero("¿Que año quieres consultar?");
				Set<Alumno> alumnos = AccesoAlumno.informeMultitabla(año);
				for (Alumno alum : alumnos) {
					System.out.println(alum.toStringMultiTabla());
				}

				break;
			case 3:
				// ------------>ZONA ALUMNO
				int codigoAlum = Teclado.leerEntero("¿Eres alumno del centro?¿Cual es tu codigo?");
				Alumno alumnoConsultado = AccesoAlumno.consultarAlumnoXCodigo(codigoAlum);
				int opcionMenuAlumno = 0;
				if (alumnoConsultado != null) {
					opcionMenuAlumno = menuAlumno();
					///////////////////////////////////////////////////////////////////////////////////////////////////
					////////////////////////// MENU ALUMNO ///////////////////////////////
					///////////////////////////////////////////////////////////////////////////////////////////////////
					switch (opcionMenuAlumno) {
					case 1:// ------------>GESTION MATRICULAS

						int opcionMenuMatriculas = menuMatriculas();// ------------->OPCIONES GESTION MATRICULA
						if (opcionMenuMatriculas == 1) { // ------------>Crear Matricula (se inicializa e inserta en la
															// funcion elegir Modulos)
							elegirModulos(alumnoConsultado);
						}
						if (opcionMenuMatriculas == 2) {// ------------>Consultar y editar
							consultarMatrciulasAlumno(codigoAlum);

							int numero = Teclado.leerEntero("¿Que matricula quieres consultar?");
							Matricula matriculaAlumno = AccesoMatricula.consultarMatricula(numero);
							System.out.println(matriculaAlumno);
							leerLineasMatrciula(numero);

							boolean editar = Teclado.leerBooleano("¿Quieres editar tu matrícula?true/false");
							if (editar) {
								List<LineaMatricula> lineas = AccesoLineaMatricula.consultarTodoLineaMatricula(numero);
								if (cambiarModulos(matriculaAlumno, alumnoConsultado, lineas)) {
									System.out.println("Tu matrícula ha sido actualizada");
								}
							}
							boolean eliminar = Teclado.leerBooleano("¿Quieres eliminar tu matrícula?true/false");
							if (eliminar) {
								Matricula matricula = new Matricula(matriculaAlumno.getCodigo(),
										matriculaAlumno.getCodigo_alumno(), matriculaAlumno.getAno_academico(),
										"anulada", matriculaAlumno.getImporte());
								if (AccesoMatricula.actualizarMatricula(matricula, alumnoConsultado)) {
									System.out.println("Tu matrícula ha sido actualizada");
								}
							}
						}

						break;

					case 2: // ----------->GESTIONAR PERFIL
						opcionMenuAlumno = menuPerfil();
						switch (opcionMenuAlumno) {// ----------->OPCIONES PERFIL
						case 1:// ----------->Modificar Datos
							String nombre = Teclado.leerCadena("Inserta tu nombre");
							String fecha_nacimiento = Teclado.leerCadena("Inserta tu fecha de cumpleaños");
							String domicilio = Teclado.leerCadena("Inserta tu domicilio");
							String telefono = Teclado.leerCadena("Inserta tu telefono");
							String correo = Teclado.leerCadena("Inserta tu correo electronico");
							Alumno alumno = new Alumno(codigoAlum, nombre, fecha_nacimiento, domicilio, telefono,
									correo);
							if (AccesoAlumno.actualizarAlumno(alumno)) {
								System.out.println("Has sido actualizado correctamente");
							}
							break;
						case 2:// ----------->Eliminar Alumno
							if (AccesoAlumno.eliminarAlumno(codigoAlum)) {
								System.out.println("Ya no eres nuestro alumno, esperamos que hayas aprendido mucho!!");
							} else {
								System.out.println(
										"Upps...algo ha ido mal, vuelve a intentarlo(no hemos podido eliminarte)");
							}
							break;
						}
						break;
					default:
						System.out.println("Esa opcion no es valida");
					}
					break;
				} else {
					boolean correcto = Teclado.leerBooleano("¿Quieres ser nuestro alumno?");
					if (correcto) {
						String nombre = Teclado.leerCadena("Inserta tu nombre");
						String fecha_nacimiento = Teclado.leerCadena("Inserta tu fecha de cumpleaños");
						String domicilio = Teclado.leerCadena("Inserta tu domicilio");
						String telefono = Teclado.leerCadena("Inserta tu telefono");
						String correo = Teclado.leerCadena("Inserta tu correo electronico");
						Alumno alumno = new Alumno(0, nombre, fecha_nacimiento, domicilio, telefono, correo);
						if (AccesoAlumno.insertarAlumno(alumno)) {
							System.out.println("Has sido registrado correctamente");
						}
					}
				}
				break;
			//////////////////////////////////////////////////////////////////////////
			////////////////////////// FIN MENU ALUMNO ///////////////////////////////
			//////////////////////////////////////////////////////////////////////////

			case 4:
				System.out.println("Bienvenido a la Zona de Profesores");
				opcion = menuProfes();
				////////////////////////////////////////////////////////////////////////
				////////////////////////// MENU PROFESOR ///////////////////////////////
				////////////////////////////////////////////////////////////////////////
				switch (opcion) {
				case 1:
					boolean correcto = Teclado.leerBooleano("¿Quieres cambiar las notas?true/false");
					if (correcto) {
						List<Alumno> alumnosTodos = AccesoAlumno.consultarAlumnosBasico();
						for (Alumno alum : alumnosTodos) {
							System.out.println(alum);
						}
						int codigoAlumno = Teclado.leerEntero("Codigo del alumno que quieres cambiar: ");

						consultarMatrciulasAlumno(codigoAlumno);

						int numero = Teclado.leerEntero("¿Que matricula quieres consultar?");

						Matricula matriculaAlumno = AccesoMatricula.consultarMatricula(numero);
						leerLineasMatrciula(numero);

						int codigoModulo = Teclado.leerEntero("Codigo de la asignatira que quieres cambiar: ");
						LineaMatricula lineaMatricula = AccesoLineaMatricula.estaLineaMatricula(numero, codigoModulo);
						double calificacion1 = Teclado.leerReal("Calificacion 1ª: ");
						double calificacion2 = Teclado.leerReal("Calificacoin 2ª: ");
						if (AccesoLineaMatricula.actualizarLineaMatricula(numero, codigoModulo,
								lineaMatricula.getRepeticion(), calificacion1, calificacion2)) {
							System.out.println("Se han actualizado las calificaciones");
						}
					}
					break;
				case 2:
					correcto = Teclado.leerBooleano("¿Quieres cambiar la repeticion de un alumno?true/false");
					if (correcto) {
						List<Alumno> alumnosTodos = AccesoAlumno.consultarAlumnosBasico();
						for (Alumno alum : alumnosTodos) {
							System.out.println(alum);
						}
						int codigoAlumno = Teclado.leerEntero("Codigo del alumno que quieres cambiar: ");
						consultarMatrciulasAlumno(codigoAlumno);

						int codigoMatricula = Teclado.leerEntero("Codigo de la matricula que quieres cambiar: ");
						leerLineasMatrciula(codigoMatricula);

						int codigoModulo = Teclado.leerEntero("Codigo de la asignatira que quieres cambiar: ");
						LineaMatricula lineaMatricula = AccesoLineaMatricula.estaLineaMatricula(codigoMatricula,
								codigoModulo);
						System.out.println(
								lineaMatricula.getCalificacionPrimera() + "" + lineaMatricula.getCalificacionSegunda());
						int repeticiones = Teclado.leerEntero("Numero de repeticiones: ");

						if (AccesoLineaMatricula.actualizarLineaMatricula(codigoMatricula, codigoModulo, repeticiones,
								lineaMatricula.getCalificacionPrimera(), lineaMatricula.getCalificacionSegunda())) {
						}
					}
					break;
				}
				//////////////////////////////////////////////////////////////////////////
				////////////////////////// FIN MENU PROFES ///////////////////////////////
				//////////////////////////////////////////////////////////////////////////

				break;
			case 5:
				//////////////////////////////////////////////////////////////////////////
				////////////////////////// MENU ADMINISTRADOR ///////////////////////////
				//////////////////////////////////////////////////////////////////////////

				int opcionAdmin = menuAdministrador();
				switch (opcionAdmin) {
				case 1:

					AccesoAlumno.exportarAlumnos();
					AccesoLineaMatricula.exportarLineaMatricula();
					AccesoMatricula.exportarMatriculas();
					AccesoModulos.exportarDatos();
					AccesoCiclo.exportarCiclos();
					System.out.println("Exportacion terminada");

					break;

				case 2:

					AccesoAlumno.importarFichero();
					AccesoCiclo.importarCiclos();
					AccesoModulos.importarDatos();
					AccesoMatricula.importarMatriculas();
					AccesoLineaMatricula.importarLineaMatricula();

					System.out.println("Importacion terminada");

					break;
				case 3:
					boolean correcto = Teclado.leerBooleano("¿Quieres añadir un ciclo ?true/false");
					if (correcto) {
						String denominacion = Teclado.leerCadena("Nombre del ciclo: ");
						String famProf = Teclado.leerCadena("Familia profesional del ciclo: ");
						String nivel = Teclado.leerCadena("basico/medio/superior: ");
						int horas = Teclado.leerEntero("horas del ciclo: ");
						int añoCurriculo = Teclado.leerEntero("Año del curriculo: ");
						if (AccesoCiclo.insertarCiclo(denominacion, famProf, nivel, horas, añoCurriculo)) {
							System.out.println("Ciclo insertado correctamente");
						} else {
							System.out.println("No se ha podido insertar el ciclo revisa los datos");
						}
					}
					break;
				case 4:
					correcto = Teclado.leerBooleano("¿Quieres añadir un modulo ?true/false");
					if (correcto) {
						List<Ciclo> ciclos = AccesoCiclo.consultarCiclos();
						for (Ciclo c : ciclos) {
							System.out.println(c);
						}
						int codigoCiclo = Teclado.leerEntero("Codigo del ciclo al que pertenece: ");
						String nombre = Teclado.leerCadena("Nombre del modulo: ");
						String curso = Teclado.leerCadena("primero/segundo: ");
						int creditos = Teclado.leerEntero("Creditos del modulo: ");
						int horas = Teclado.leerEntero("horas del ciclo: ");

						int i = AccesoModulos.insertarModulos(codigoCiclo, nombre, curso, creditos, horas);
						if (i == 1) {
							System.out.println("Ciclo insertado correctamente");
						} else {
							System.out.println("No se ha podido insertar el ciclo revisa los datos");
						}
					}
					break;
				//////////////////////////////////////////////////////////////////////////
				////////////////////////// FIN MENU ADMIN ////////////////////////////////
				//////////////////////////////////////////////////////////////////////////

				}

			default:
				System.out.println("Elige una opcion entre 1 y 5");

			}
		} while (opcion != 0);

	}

	private static void consultarMatrciulasAlumno(int codigoAlumno) {
		List<Matricula> matriculasAlumno = AccesoMatricula.consultarMatriculasAlumno(codigoAlumno);
		for (Matricula matricula : matriculasAlumno) {
			System.out.println(matricula.toStringEspecial());
		}

	}

	private static void leerLineasMatrciula(int numero) {
		System.out.println("Líneas de Matricula consultada");
		List<LineaMatricula> lineas = AccesoLineaMatricula.consultarTodoLineaMatricula(numero);
		for (LineaMatricula linea : lineas) {
			System.out.println(linea);
		}

	}

	private static int menuAdministrador() {
		System.out.println("1 Exportar todo");
		System.out.println("2 Importar todo");
		System.out.println("3 Insertar Ciclo");
		System.out.println("4 insertar Modulo");

		int opcion = Teclado.leerEntero("opcion entre 1-2");
		return opcion;

	}

	private static int menuProfes() {
		System.out.println("1 Cambiar nota");
		System.out.println("2 Cambiar repeticion");

		int opcion = Teclado.leerEntero("opcion entre 1-2");
		return opcion;

	}

	private static boolean cambiarModulos(Matricula matricula, Alumno alumno, List<LineaMatricula> lineasMat) {
		boolean actualizar = false;
		List<Modulos> modulosMatricula = new ArrayList<Modulos>();
		int numeroAsignaturas = 0;
		String estado = "completa";

		List<Modulos> modulos = AccesoModulos.consultarModulosAlumno(lineasMat);
		for (Modulos m : modulos) {

			Boolean eliminarAsignatura = Teclado
					.leerBooleano("¿Quieres eliminar la matricula en " + m + " ? True/False");
			if (eliminarAsignatura) {

				AccesoLineaMatricula.eliminarLineaMatricula(matricula, m);

			}
			numeroAsignaturas++;
		}

		if (modulosMatricula.size() < numeroAsignaturas) {
			estado = "parcial";
		}

		matricula = new Matricula(matricula.getCodigo(), alumno, matricula.getAno_academico(), estado,
				matricula.getImporte());
		if (AccesoMatricula.actualizarMatricula(matricula, alumno)) {
			actualizar = true;
		}

		return actualizar;

	}

	private static boolean elegirModulos(Alumno alumno) {
		boolean insertado = false;
		List<Modulos> modulosMatricula = new ArrayList<Modulos>();
		int numeroAsignaturas = 0;
		Matricula matriculaNueva = new Matricula(AccesoMatricula.nuevoCodigoMatricula());

		LocalDate fechaActual = LocalDate.now();
		int año_academico = fechaActual.getYear();
		int importe = 0;
		String estado = "completa";

		String nivel = Teclado.leerCadena("medio/superior");
		String curso = Teclado.leerCadena("¿primero/segundo?");

		List<Modulos> modulos = AccesoModulos.consultarModulosporcicloycurso(nivel, curso);
		for (Modulos m : modulos) {

			Boolean matricular = Teclado.leerBooleano("¿Quieres matricularte en " + m.getNombre() + " ? True/False");
			if (matricular) {
				Modulos moduloParaMatricular = new Modulos(m.getCodigo());
				LineaMatricula lineaMatriculaNueva = new LineaMatricula(matriculaNueva, moduloParaMatricular, 0, 0, 0);
				AccesoLineaMatricula.insertarLineaMatricula(lineaMatriculaNueva, matriculaNueva, moduloParaMatricular);
				importe += 80;
				modulosMatricula.add(moduloParaMatricular);
			}
			numeroAsignaturas++;
		}

		if (modulosMatricula.size() == 0) {
			insertado = false;
			return insertado;

		} else {
			AccesoMatricula.insertarMatricula(matriculaNueva.getCodigo(), alumno, año_academico, estado, importe);

			if (modulosMatricula.size() < numeroAsignaturas) {

				estado = "parcial";
			} else {
				matriculaNueva = new Matricula(matriculaNueva.getCodigo(), alumno, año_academico, estado, importe);
				if (AccesoMatricula.actualizarMatricula(matriculaNueva, alumno)) {
					insertado = true;
				}
			}
		}
		return insertado;
	}

	private static int menuPerfil() {
		System.out.println("1 Actualizar Alumno");
		System.out.println("2 Eliminar Alumno");
		int opcion = Teclado.leerEntero("¿Que quieres hacer?");
		return opcion;

	}

	private static int menuMatriculas() {
		System.out.println("1 Crear Matricula");
		System.out.println("2 Consultar Matricula");

		int opcion = Teclado.leerEntero("¿Que quieres hacer?");
		return opcion;

	}

	private static int menuAlumno() {

		System.out.println("1 Gestionar Matricula");
		System.out.println("2 Gestionar Perfil");
		int opcion = Teclado.leerEntero("¿Que quieres hacer?");
		return opcion;
	}

	private static int menu() {

		System.out.println("0 Salir del programa");
		System.out.println("1 Consulta nuestros ciclos");
		System.out.println("2 Ver orla por a�os");
		System.out.println("3 Zona Alumno");
		System.out.println("4 Zona Profesores");
		System.out.println("5 Zona Administrador");
		int opcion = Teclado.leerEntero("opcion entre 0-5");
		return opcion;

	}

}
