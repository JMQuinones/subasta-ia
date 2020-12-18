
public class Producto {
    private boolean vendido;
    private String nombre;
    
    public Producto(boolean v, String n){
        this.vendido=v;
        this.nombre=n;
    }

    public boolean getVendido() {
        return vendido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setVendido(boolean vendido) {
        this.vendido = vendido;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }            
}
