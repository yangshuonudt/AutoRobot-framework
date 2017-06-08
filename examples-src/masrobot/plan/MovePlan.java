package masrobot.plan;

import java.util.Vector;

import bdi4jade.plan.PlanBody;
import bdi4jade.plan.PlanInstance;
import bdi4jade.plan.PlanInstance.EndState;
import core.autorobotpackage.mas.plan.PlanTemplate;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class MovePlan extends CyclicBehaviour implements PlanTemplate {

	private static boolean flag = false;
	private boolean sent = false;
	private MessageTemplate mt = null;
	private boolean planState = false;

	private Agent myAgent;
	
	private Vector walkerAgents;
	
	public MovePlan (Agent agent){
		this.myAgent = agent;
	}

	public void start(boolean flag){
		this.flag = flag;
	}
	
	public void stopCyclicBehaviour(){
		this.myAgent.removeBehaviour(this);
	}
	
	// receive reply whether the plan has been executed
	public boolean getPlanState() {
		return this.planState;
	}

	@Override
	public boolean getFlag() {
		// TODO Auto-generated method stub
		return this.flag;
	}

	@Override
	public Agent getMyAgent() {
		// TODO Auto-generated method stub
		return this.myAgent;
	}
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		if (!sent && this.flag) {
			//////////////////////////////////////////////////////
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("walker");
			template.addServices(sd);
			try{
				DFAgentDescription[] result = DFService.search(this.myAgent,template);
				walkerAgents.clear();
				for (int i=0; i<result.length; ++i){
					walkerAgents.addElement(result[i].getName());
				}
			} catch(FIPAException fe){
				fe.printStackTrace();
			}
			//////////////////////////////////////////////
			
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setContent("Walk");

			msg.addReceiver((AID) walkerAgents.firstElement());
			
			msg.setConversationId("cin" + System.currentTimeMillis());
			msg.setReplyWith("inform" + System.currentTimeMillis());
			this.myAgent.send(msg);
			this.mt = MessageTemplate.and(MessageTemplate.MatchConversationId(msg.getConversationId()),
					MessageTemplate.MatchInReplyTo(msg.getReplyWith()));
			this.sent = true;
			System.out.println("The Walk Plan has been sent to actuator by: " + msg.getSender().getName());
		} else {
			
			ACLMessage reply = this.myAgent.receive(mt);
			if (reply != null) {
				if(reply.getPerformative() == ACLMessage.AGREE){
					this.planState = true;
					System.out.println("The Walk Plan has finished by: " + reply.getSender().getName());
					System.out.println("The reply from walkerAgent is: " + reply.getContent());
				} else if(reply.getPerformative() == ACLMessage.REFUSE){
					this.planState = false;
					System.out.println("The Walk Plan has been interruptted by: " + reply.getSender().getName());
					System.out.println("The reply from walkerAgent is: " + reply.getContent());
				}
			} else {
				block();
			}
		}

	}
}
