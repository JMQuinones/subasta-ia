

import javax.swing.JOptionPane;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class AgenteVendedor extends Agent {
	@Override
	protected void setup() {
		System.out.println("Hola soy el agente vendedor");
		//CyclicBehaviour ocurre en un ciclo infinito
		addBehaviour(new CyclicBehaviour() {			
			@Override
			public void action() {
				//Recibe un mensaje
				ACLMessage msg = receive();
				if(msg!=null) {					
					System.out.println("Mensaje del comprador:  " + msg.getContent());
					JOptionPane.showMessageDialog(null,"Mensaje del comprador recibido:  " + msg.getContent());	
					//Envia la respuesta
					ACLMessage reply = new ACLMessage( ACLMessage.INFORM );
				    reply.setContent( "Hola recibi tu oferta" );
				    reply.addReceiver( msg.getSender() );
				    send(reply);
				}else  block();
			}
		});
	}

}
