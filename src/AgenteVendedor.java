import javax.swing.JOptionPane;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class AgenteVendedor extends Agent {
    private Producto producto;
    private int maxOferta;
    private int ofertaPrevia;
    public long tiempoModificado = getHoraActual()+1000000000;
    
    @Override
    protected void setup() {
        System.out.println("Hola soy el agente vendedor");
        //CyclicBehaviour ocurre en un ciclo infinito
        addBehaviour(new CyclicBehaviour() {			
            @Override
            public void action() {
                //Recibe un mensaje
                ACLMessage msg = receive();
                ACLMessage ultimoOfertante = new ACLMessage();
                if(msg!=null) {
                    ultimoOfertante = msg;
                    System.out.println("Mensaje del comprador:  " + msg.getContent());
                    int precio = 0;
                    try {
                        precio = Integer.parseInt(msg.getContent().replaceAll("\\D+",""));
                    } catch (NumberFormatException ex) {
                        System.err.println("Invalid string in argumment");  
                    }
                    
                    //System.out.println("PRECIO OFERTADO:   " +precio);
                    if(precio>maxOferta){
                        setOfertaPrevia(getMaxOferta());
                        setMaxOferta(precio);
                        System.out.println("OFERTA MAXIMA: "+maxOferta);
                        setTiempoModificado(getHoraActual());
                    }                    
                    //JOptionPane.showMessageDialog(null,"Mensaje del comprador recibido:  " + msg.getContent());	
                    //Envia la respuesta
                    ACLMessage reply = new ACLMessage( ACLMessage.INFORM);
                    reply.setContent( "Hola recibi tu oferta, la oferta maxima actual es: "+maxOferta);
                    reply.addReceiver(msg.getSender());
                    send(reply);
                }else  block();
                
                if(verificarModif()){
                    if(ultimoOfertante.getSender()!=null){
                      System.out.println("Ya no hay ofertas terminando");
                        ACLMessage aceptar = new ACLMessage( ACLMessage.AGREE);
                        aceptar.setContent( "Hola  tu oferta fue la ganadora: "+maxOferta);
                        aceptar.addReceiver(ultimoOfertante.getSender());
                        System.out.println("El agente "+ultimoOfertante.getSender().getLocalName()+" fue el comprador" );
                        send(aceptar);
                        //doDelete();  
                    }else{
                        System.exit(0);
                    }
                    
                }
            }
        });
    }
    
    public boolean verificarModif(){        
        if(getHoraActual() > tiempoModificado + 15000) {
            if(getMaxOferta() == getOfertaPrevia()) {
              return false;
            } else {
              return true;
            }
            
        }
        return false;
    }
    
    public long getHoraActual(){
        Date date = new Date();
        long hora = date.getTime();
        //System.out.println(hora);
        return hora;
    }

    public void setTiempoModificado(long tiempoModificado) {
        this.tiempoModificado = tiempoModificado;
    }
    
    
    public void inicializarProducto(){
        this.producto=new Producto(ThreadLocalRandom.current().nextInt(20000, 30000 + 1), "Producto 1");
    }
        

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getMaxOferta() {
        return maxOferta;
    }

    public void setMaxOferta(int maxOferta) {
        this.maxOferta = maxOferta;
    }

    public int getOfertaPrevia() {
        return ofertaPrevia;
    }

    public void setOfertaPrevia(int ofertaPrevia) {
        this.ofertaPrevia = ofertaPrevia;
    }
    
    
    
}
