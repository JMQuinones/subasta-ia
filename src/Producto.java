
public class Producto {
    private int precio;
    private String nombre;
    
    public Producto(int p, String n){
        this.precio=p;
        this.nombre=n;
    }

    public int getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }            
}
