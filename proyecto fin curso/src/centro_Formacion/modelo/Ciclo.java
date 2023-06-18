package centro_Formacion.modelo;

import java.util.ArrayList;
import java.util.List;

public class Ciclo {

  protected final String SEPARADOR = ";";
  protected int codigo;
  protected String denominacion;
  protected String familia_profesional;
  protected String nivel;
  protected int horas;
  protected int año_curriculo;
  List<Modulos> modulos = new ArrayList<Modulos>();

  public Ciclo(int codigo) {
    this.codigo = codigo;
  }

  public Ciclo(int codigo, String denominacion, String familia_profesional, String nivel, int horas,
      int año_curriculo) {
    super();
    this.codigo = codigo;
    this.denominacion = denominacion;
    this.familia_profesional = familia_profesional;
    this.nivel = nivel;
    this.horas = horas;
    this.año_curriculo = año_curriculo;
  }

  public Ciclo(String denominacion, String familia_profesional, String nivel, int horas,
      int año_curriculo) {
    this.denominacion = denominacion;
    this.familia_profesional = familia_profesional;
    this.nivel = nivel;
    this.horas = horas;
    this.año_curriculo = año_curriculo;
  }

  public Ciclo(int codigo, String denominacion, String familia_profesional, String nivel, int horas,
      int año_curriculo, List<Modulos> modulos) {
    super();
    this.codigo = codigo;
    this.denominacion = denominacion;
    this.familia_profesional = familia_profesional;
    this.nivel = nivel;
    this.horas = horas;
    this.año_curriculo = año_curriculo;
    this.modulos = modulos;
  }

  public Ciclo(int codigo,String denominacion, String familia_profesional, String nivel, List<Modulos> modulos) {
    super();
    this.codigo = codigo;
    this.denominacion = denominacion;
    this.familia_profesional = familia_profesional;
    this.nivel = nivel;
    this.modulos = modulos;
  }

  public Ciclo(String linea) {
    String[] datos = linea.split(SEPARADOR);
    this.codigo = Integer.parseInt(datos[0]);
    this.denominacion = datos[1];
    this.familia_profesional = datos[2];
    this.nivel = datos[3];
    this.horas = Integer.parseInt(datos[4]);
    this.año_curriculo = Integer.parseInt(datos[5]);
  }

  @Override
  public String toString() {
    return "Ciclo [codigo=" + codigo + ", denominacion=" + denominacion + ", familia_profesional="
        + familia_profesional + ", nivel=" + nivel + ", horas=" + horas + ", año_curriculo=" + año_curriculo
        + "Modulos: " + modulos + "]";
  }

  public int getCodigo() {
    return codigo;
  }

  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  public String getDenominacion() {
    return denominacion;
  }

  public void setDenominacion(String denominacion) {
    this.denominacion = denominacion;
  }

  public String getFamilia_profesional() {
    return familia_profesional;
  }

  public void setFamilia_profesional(String familia_profesional) {
    this.familia_profesional = familia_profesional;
  }

  public String getNivel() {
    return nivel;
  }

  public void setNivel(String nivel) {
    this.nivel = nivel;
  }

  public int getHoras() {
    return horas;
  }

  public void setHoras(int horas) {
    this.horas = horas;
  }

  public int getAño_curriculo() {
    return año_curriculo;
  }

  public void setAño_curriculo(int año_curriculo) {
    this.año_curriculo = año_curriculo;
  }

  public List<Modulos> getModulos() {
    return modulos;
  }

  public void setModulos(List<Modulos> modulos) {
    this.modulos = modulos;
  }

  public String toStringWithSeparators() {
    return codigo + SEPARADOR
        + denominacion + SEPARADOR
        + familia_profesional + SEPARADOR
        + nivel + SEPARADOR
        + horas + SEPARADOR
        + año_curriculo + SEPARADOR;
  }
}