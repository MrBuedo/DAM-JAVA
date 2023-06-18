package centro_Formacion.modelo;

import java.util.Set;

public class Alumno {
	private static final String SEPARADOR = ";";
	private int codigo;
	private String nombre;
	private String fecha_nacimiento;
	private String domicilio;
	private String telefono;
	private String correo;
	private int calificacion1;
	private int calificacion2;
	private Modulos modulo;
	private Set<Modulos> modulos;
	
	public Alumno(int codigo, String nombre, String fecha_nacimiento, String domicilio, String telefono,
			String correo) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.fecha_nacimiento = fecha_nacimiento;
		this.domicilio = domicilio;
		this.telefono = telefono;
		this.correo = correo;
	}
	
	//Se utiliza para crear alumnos unicamente con codigo, para utilizarlos en otros objetos  
		public Alumno(int codigo) {
			this.codigo = codigo;
			
		}
		public Alumno(String nombre, String fecha_nacimiento, int calificacion1, int calificacion2,Modulos modulo) {
			this.nombre = nombre;
			this.fecha_nacimiento = fecha_nacimiento;
			this.setCalificacion1(calificacion1);
			this.setCalificacion2(calificacion2);
			this.setModulo(modulo);
			
		}
	public Alumno(String linea) {
		String[] datos = linea.split(SEPARADOR);
		this.codigo = Integer.parseInt(datos[0]);
		this.nombre = datos[1];
		this.fecha_nacimiento = datos[2];
		this.domicilio = datos[3];
		this.telefono = datos[4];
		this.correo = datos[5];
	}
	public Alumno(String nombre, String fecha_nacimiento, Set<Modulos> modulosAlumno) {
		this.nombre = nombre;
		this.fecha_nacimiento = fecha_nacimiento;
		this.modulos = modulosAlumno;
		
	}
	public String toStringMultiTabla() {
		String cadena = "";
		for(Modulos m : modulos) {
			cadena = cadena + m.getNombre();
		}
		return "Alumno nombre=" + nombre + ", fecha nacimiento= " + fecha_nacimiento + "modulos= " + cadena ;
	}

	

	public String toString() {
		return "Alumno [codigo=" + codigo + ", nombre=" + nombre + ", fecha_nacimiento=" + fecha_nacimiento
				+ ", domicilio=" + domicilio + ", telefono=" + telefono + ", correo=" + correo + "]";
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimeinto(String fecha_nacimeinto) {
		this.fecha_nacimiento = fecha_nacimeinto;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String toStringWithSeparators() {
		return 
				this.codigo + SEPARADOR + 
				this.nombre + SEPARADOR + 
				this.fecha_nacimiento +SEPARADOR+
				this.domicilio + SEPARADOR +
				this.telefono + SEPARADOR +
				this.correo + SEPARADOR ;
	}

	

	public Set<Modulos> getModulos() {

		return modulos;
	}

	public int getCalificacion1() {
		return calificacion1;
	}

	public void setCalificacion1(int calificacion1) {
		this.calificacion1 = calificacion1;
	}

	public int getCalificacion2() {
		return calificacion2;
	}

	public void setCalificacion2(int calificacion2) {
		this.calificacion2 = calificacion2;
	}

	public Modulos getModulo() {
		return modulo;
	}

	public void setModulo(Modulos modulo) {
		this.modulo = modulo;
	}

	
	
	

}

