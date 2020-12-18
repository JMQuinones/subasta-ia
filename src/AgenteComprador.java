import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AgenteComprador extends Agent{
    private int presupuesto;
    private boolean puedoOfertar;
    public int maxOferta=0;
    
    
    @Override
    protected void setup() {
        inicializarPresupuesto();                
        System.out.println("Hola soy el agente comprador: " + getAID().getName());
        //TickerBehaviour ocurre cada x milisegundos
        addBehaviour(new TickerBehaviour(this, 5000) {
            protected void onTick() {
                // Envia el mensaje al otro agente
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                if(maxOferta<presupuesto){                    
                    msg.setContent("Hola quiero comprar el producto, ofrezco: " + ThreadLocalRandom.current().nextInt(maxOferta, presupuesto));
                    msg.addReceiver(new AID("vendedor",AID.ISLOCALNAME));
                    send(msg); 
                }else{
                    System.out.println("Ya no puedo ofertar más, terminando");
                    doDelete();
                }
                //recive la respuesta del otro agente
                ACLMessage reply = receive();
                if(reply!=null) {	
                    System.out.println("Mensaje del vendedor:  " + reply.getContent());
                    //JOptionPane.showMessageDialog(null,"Mensaje recivido" + reply.getContent());	
                    setMaxOferta(Integer.parseInt(msg.getContent().replaceAll("\\D+",""))); 
                }else  block();
                
                MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.AGREE);
                ACLMessage comprado = receive(messageTemplate);
                if(comprado!=null) {	
                    System.out.println("Mensaje del vendedor:  " + reply.getContent());
                    //JOptionPane.showMessageDialog(null,"Mensaje recivido" + reply.getContent());
                    //doDelete();                    
                }else{                    
                    block();
                }                                                
            }
        } );
                /*addBehaviour(new CyclicBehaviour() {				
                        @Override
                        public void action() {
                                // Envia el mensaje al otro agente
                                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                                msg.setContent(" Hola quiero comprar el producto, ofrezco: " +ThreadLocalRandom.current().nextInt(1000, 3000 + 1));
                                msg.addReceiver(new AID("vendedor",AID.ISLOCALNAME));
                                send(msg);

                                ACLMessage reply = receive();
                                if(reply!=null) {	
                                        System.out.println("Mensaje del vendedor:  " + reply.getContent());
                                        JOptionPane.showMessageDialog(null,"Mensaje recivido" + reply.getContent());							
                                }else  block();
                        }
                });*/

    }
    
    public int generarRandom(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    public void inicializarPresupuesto(){
        //genera un presupuesto de entre 10.000 y 30.000
        presupuesto =ThreadLocalRandom.current().nextInt(10000, 30000 + 1);
    }
    
    public void retirarDinero(int cant){
        presupuesto-=cant;
    }
    
    public boolean tieneDinero(){
        return presupuesto>0;
    }

    public int getMaxOferta() {
        return maxOferta;
    }

    public void setMaxOferta(int maxOferta) {
        this.maxOferta = maxOferta;
    }
    
    
	
}
