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
import jade.lang.acl.UnreadableException;
import masrobot.domain.CameraInfo;

public class DetectPlan extends CyclicBehaviour implements PlanTemplate{
	
	private static boolean flag;
	private boolean sent;
	private MessageTemplate mt;
	private boolean planState;
	private Agent myAgent;
	
	private Vector cameraAgents;
	
	public DetectPlan (Agent myAgent){
		this.myAgent = myAgent;
		this.flag = false;
		this.sent = false;
		this.mt = new MessageTemplate(null);
		this.planState = false;	
	}
	
	public void start(boolean flag){
		this.flag = flag;
	}
	
	public boolean getFlag(){
		return this.flag;
	}

	public Agent getMyAgent(){
		return this.myAgent;
	}
	
	public void stopCyclicBehaviour(){
		this.myAgent.removeBehaviour(this);
	}
	
	@Override
	public boolean getPlanState() {
		// TODO Auto-generated method stub
		return this.planState;
	}
	
	public boolean getDetectionResult(){
		return true;
	}


	@Override
	public void action() {
		// TODO Auto-generated method stub
		if (!sent && this.flag) {
			///////////////////dynamic search/////////////////
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("camera");
			template.addServices(sd);
			try{
				DFAgentDescription[] result = DFService.search(this.myAgent, template);
				cameraAgents.clear();
				for (int i=0; i< result.length; ++i){
					cameraAgents.addElement(result[i].getName());
				}
			} catch(FIPAException fe){
				fe.printStackTrace();
			}
			/////////////////////////////////////////////////		
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setContent("Face");
			
			msg.addReceiver((AID) cameraAgents.firstElement());
			
			msg.setConversationId("cin" + System.currentTimeMillis());
			msg.setReplyWith("inform" + System.currentTimeMillis());
			
			this.myAgent.send(msg);
			
			this.mt = MessageTemplate.and(MessageTemplate.MatchConversationId(msg.getConversationId()),
					MessageTemplate.MatchInReplyTo(msg.getReplyWith()));
			this.sent = true;
			System.out.println("The Search Plan has been sent to camera by: " + msg.getSender().getName());	
		} else {
			ACLMessage reply = this.myAgent.receive(mt);
			if (reply != null) {
				this.planState = true;
				System.out.println("The Search Plan has been completed by: " + reply.getSender().getName());
				
				if("JavaSerialization".equals(reply.getLanguage())){
					try {
						CameraInfo cameraInfo = (CameraInfo)reply.getContentObject();
						System.out.println("Retrieve result of camera detection: " + 
						cameraInfo.getFlag() + ", x= " + cameraInfo.getX() + ", y= " + cameraInfo.getY()
						+ ", z= " + cameraInfo.getZ());
					} catch (UnreadableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}	
			} else {
				block();
			}
		}
	}
}
