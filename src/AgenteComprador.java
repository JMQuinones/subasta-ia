import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JOptionPane;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class AgenteComprador extends Agent{
	@Override
	protected void setup() {
		System.out.println("Hola soy el agente comprador: " + getAID().getName());
		//TickerBehaviour ocurre cada x milisegundos
		addBehaviour(new TickerBehaviour(this, 10000) {
			protected void onTick() {
				// Envia el mensaje al otro agente
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.setContent(" Hola quiero comprar el producto, ofrezco: " +ThreadLocalRandom.current().nextInt(1000, 3000 + 1));
				msg.addReceiver(new AID("vendedor",AID.ISLOCALNAME));
				send(msg);
				//recive la respuesta del otro agente
				ACLMessage reply = receive();
				if(reply!=null) {	
					System.out.println("Mensaje del vendedor:  " + reply.getContent());
					JOptionPane.showMessageDialog(null,"Mensaje recivido" + reply.getContent());							
				}else  block();
				
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
	
}
