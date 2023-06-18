package centro_Formacion.modelo;

public class Matricula {
	// atributo de clase para separar datos de matricula
	private static final String SEPARADOR = ";";

	private int codigo;
	private Alumno codigo_alumno;
	private int ano_academico;
	private String estado;
	private double importe;
	

	public Matricula(int codigo) {
		this.codigo = codigo;
	}

	public Matricula(int codigo, Alumno codigo_alumno, int ano_academico, String estado, double importe) {
		this.codigo = codigo;
		this.codigo_alumno = codigo_alumno;
		this.ano_academico = ano_academico;
		this.estado = estado;
		this.importe = importe;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Alumno getCodigo_alumno() {
		return codigo_alumno;
	}

	public void setCodigo_alumno(Alumno codigo_alumno) {
		this.codigo_alumno = codigo_alumno;
	}

	public int getAno_academico() {
		return ano_academico;
	}

	public void setAno_academico(int ano_academico) {
		this.ano_academico = ano_academico;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public Matricula(String linea) {
		String[] datos = linea.split(SEPARADOR);
		this.codigo = Integer.parseInt(datos[0]);
		// this.codigo_alumno = Integer.parseInt(datos[1]);
		this.ano_academico = Integer.parseInt(datos[2]);
		this.estado = datos[3];
		this.importe = Double.parseDouble(datos[4]);
	}

	public Matricula(int codigo, int año) {
		this.codigo = codigo;
		this.ano_academico = año;
	}

	// Devuelve una cadena de caracteres con el estado de la matriucla.
	// Se utiliza para escribir la matricula en un fichero de texto.
	public String toStringWithSeparators() {
		return this.codigo + SEPARADOR + this.codigo_alumno.getCodigo() + SEPARADOR + this.ano_academico + SEPARADOR + this.estado
				+ SEPARADOR + this.importe + SEPARADOR + String.format("%.2f", this.importe).replace(',', '.');
	}

	@Override
	//Modificado por Lucía
	public String toString() {
		return "Matricula [codigo =" + codigo + ", codigo_alumno =" + codigo_alumno.getCodigo() + ", ano_academico ="
				+ ano_academico + ", estado =" + estado + ", importe =" + String.format("%.2f", this.importe) + "]";
	}
	public String toStringEspecial() {
		return "Matricula [codigo =" + codigo + ", ano_academico ="
				+ ano_academico + "]";
	}

}
