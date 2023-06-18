package centro_Formacion.modelo;

public class LineaMatricula {
	
	private static final String SEPARADOR = ";";
	private  Matricula codigoMatricula;
	private  Modulos codigoModulo;
	private  int repeticion;
	private  double calificacionPrimera;
	private  double calificacionSegunda;
	
	public LineaMatricula(Matricula codigoMatricula, Modulos codigoModulo, int repeticion, double calificacionPrimera, double calificacionSegunda) {
		this.codigoMatricula = codigoMatricula;
		this.codigoModulo = codigoModulo;
		this.repeticion = repeticion;
		this.calificacionPrimera = calificacionPrimera;
		this.calificacionSegunda = calificacionSegunda;
	}
	 

	public Matricula getCodigoMatricula() {
		return codigoMatricula;
	}
	public void setCodigoMatricula(Matricula codigoMatricula) {
		this.codigoMatricula = codigoMatricula;
	}
	public  Modulos getCodigoModulo() {
		return codigoModulo;
	}
	public void setCodigoModulo(Modulos codigoModulo) {
		this.codigoModulo = codigoModulo;
	}
	public   int getRepeticion() {
		return repeticion;
	}
	public void setRepeticion(int repeticion) {
		this.repeticion = repeticion;
	}
	public  double getCalificacionPrimera() {
		return calificacionPrimera;
	}
	public void setCalificacionPrimera(int calificacionPrimera) {
		this.calificacionPrimera = calificacionPrimera;
	}
	public  double getCalificacionSegunda() {
		return calificacionSegunda;
	}
	public void setCalificacionSegunda(int calificacionSegunda) {
		this.calificacionSegunda = calificacionSegunda;
	}
	@Override
	public String toString() {
		return "Linea_matricula [Codigo matricula = " + codigoMatricula.getCodigo() + ", Codigo modulo = " + codigoModulo.getCodigo()
				+ ", Repetición = " + repeticion + ", Calificacion primera = " + calificacionPrimera
				+ ", Calificacion segunda = " + calificacionSegunda + "]";
	}
	
	
	public String toStringWithSeparators() {
		return 
				this.codigoMatricula.getCodigo() + SEPARADOR + 
				this.codigoModulo.getCodigo() + SEPARADOR + 
				this.repeticion +SEPARADOR+
				this.calificacionPrimera + SEPARADOR +
				this.calificacionSegunda ;
		}
	
}
