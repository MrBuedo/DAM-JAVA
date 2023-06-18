package centro_Formacion.modelo;

public class Modulos {
	//ATRIBUTOS
	private int codigo; //NN PK
	private int codigo_ciclo; //NN FK	
	private Ciclo ciclo; //NN FK
	private String nombre; // NN 
	private String curso; //2 opciones: primero o segundo, NN
	private int creditos_ects; //NN
	private int horas; //NN
	private int numAlumnosMatriculados;
  private static String SEPARADOR = ";";
	
	//CONSTRUCTOR
	public Modulos(int codigo, int codigo_ciclo, String nombre, String curso, int creditos_ects, int horas) {
		if(curso.equalsIgnoreCase("Primero")||curso.equalsIgnoreCase("Segundo")) {
			this.codigo = codigo;
			this.codigo_ciclo = codigo_ciclo;
			this.nombre = nombre;
			this.curso = curso;
			this.creditos_ects = creditos_ects;
			this.horas = horas;
		} else {
			System.out.println("El curso debe ser primero o segundo");
		}
		
	}
	
	//CONSTRUCTOR Multitabla
		public Modulos(int codigo, Ciclo ciclo, String nombre, String curso,  int numAlumnosMatriculados ) {
			if(curso.equalsIgnoreCase("Primero")||curso.equalsIgnoreCase("Segundo")) {
				this.codigo = codigo;
				this.ciclo = ciclo;
				this.nombre = nombre;
				this.curso = curso;
//				this.creditos_ects = creditos_ects;
//				this.horas = horas;
				this.numAlumnosMatriculados=numAlumnosMatriculados;
			} else {
				System.out.println("El curso debe ser primero o segundo");
			}
			
		}
	
	//Constructor con solo un codigo as input
	public Modulos (int codigo) {
		this.codigo=codigo;
	}
	public Modulos (int codigo, String modulo) {
		this.codigo=codigo;
		this.nombre =modulo;
	}
	
	public Modulos(int codigo_ciclo, String nombre, String curso, int creditos_ects, int horas) {
		if(curso.equalsIgnoreCase("Primero")||curso.equalsIgnoreCase("Segundo")) {		
			this.codigo_ciclo = codigo_ciclo;
			this.nombre = nombre;
			this.curso = curso;
			this.creditos_ects = creditos_ects;
			this.horas = horas;
		} else {
			System.out.println("El curso debe ser primero o segundo");
		}
	}
	
//Crea un empleado a partir de una l�nea de texto.
	public Modulos(String linea) {
		String[] datos = linea.split(SEPARADOR);
		this.codigo = Integer.parseInt(datos[0]);
		this.ciclo = new Ciclo(Integer.parseInt(datos[1]));		
		this.nombre = datos[2];
		this.curso = datos[3];
		this.creditos_ects = Integer.parseInt(datos[4]);
		this.horas = Integer.parseInt(datos[5]);
	}
	
	

	public Modulos(int codigo2, Ciclo ciclo2, String nombre2, String curso2, int creditos_ects2, int horas2) {
		// TODO Auto-generated constructor stub
		this.codigo = codigo2;
		this.ciclo = ciclo2;
		this.nombre = nombre2;
		this.curso = curso2;
		this.creditos_ects = creditos_ects2;
		this.horas = horas2;
	}

	public Modulos(int codigo2, Ciclo ciclo2, String nombre2, String curso2) {
		this.codigo = codigo2;
		this.ciclo = ciclo2;
		this.nombre = nombre2;
		this.curso = curso2;
		
	}

	//METODOS
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCodigo_ciclo() {
		return codigo_ciclo;
	}

	public void setCodigo_ciclo(int codigo_ciclo) {
		this.codigo_ciclo = codigo_ciclo;
	}
	
	

	public Ciclo getCiclo() {
		return ciclo;
	}

	public void setCiclo(Ciclo ciclo) {
		this.ciclo = ciclo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public int getCreditos_ects() {
		return creditos_ects;
	}

	public void setCreditos_ects(int creditos_ects) {
		this.creditos_ects = creditos_ects;
	}

	public int getHoras() {
		return horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}

	@Override
	public String toString() {
		return "Modulos \n [codigo=" + codigo + ", [ciclo=" + ciclo.getCodigo() +", denominacion: " + ciclo.getDenominacion() + "], nombre=" + nombre + ", curso="
				+ curso + ", creditos_ects=" + creditos_ects + ", horas=" + horas + "] con= "+numAlumnosMatriculados+" alumnos matriculados\n";
		
	}

  public String toStringWithSeparators(){
    return  codigo + SEPARADOR
      + ciclo.getCodigo() + SEPARADOR
      + nombre + SEPARADOR
			+ curso + SEPARADOR
      + creditos_ects + SEPARADOR
      + horas + SEPARADOR;
  }
  
  public String toStringAlumnos() {
		return "[Modulo=" + codigo + ", [ciclo=" + ciclo.getCodigo() +", denominacion: " + ciclo.getDenominacion() + "], nombre=" + nombre + ", curso="
				+ curso + ", creditos_ects=" + creditos_ects + ", horas=" + horas + "] con= "+numAlumnosMatriculados+" alumnos matriculados\n";
	}
  
  
	
	
	
	
	
	

}
