package masrobot.plan;

import bdi4jade.plan.PlanBody;
import bdi4jade.plan.PlanInstance;
import bdi4jade.plan.PlanInstance.EndState;
import core.autorobotpackage.mas.plan.PlanTemplate;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class TurnHeadPlan extends CyclicBehaviour implements PlanTemplate{
	
	private static boolean flag = false;
	private boolean sent = false;
	private MessageTemplate mt = null;
	private boolean planState = false;
	
	private Agent myAgent;
	
	public TurnHeadPlan(Agent myAgent){
		this.myAgent = myAgent;
	}
	
	@Override
	public boolean getPlanState() {
		// TODO Auto-generated method stub
		return this.planState;
	}

	@Override
	public void start(boolean flag) {
		// TODO Auto-generated method stub
		this.flag = flag;
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
	public void stopCyclicBehaviour() {
		// TODO Auto-generated method stub
		this.myAgent.removeBehaviour(this);
		
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		if (!sent && this.flag) {
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setContent("Follow");
			msg.addReceiver(new AID("camera", false));
			msg.setConversationId("cin" + System.currentTimeMillis());
			msg.setReplyWith("inform" + System.currentTimeMillis());
			this.myAgent.send(msg);
			this.mt = MessageTemplate.and(MessageTemplate.MatchConversationId(msg.getConversationId()),
					MessageTemplate.MatchInReplyTo(msg.getReplyWith()));
			this.sent = true;
			System.out.println("The Search Plan has been sent to actuator by: " + msg.getSender().getName());
		} else {
			ACLMessage reply = this.myAgent.receive(mt);
			if (reply != null) {
				this.planState = true;
				System.out.println("The Search Plan has been completed by: " + reply.getSender().getName());
				System.out.println("The reply from camera agent is: " + reply.getContent());
			} else {
				block();
			}
		}

	}

}
