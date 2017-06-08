package core.autorobotpackage.mas.plan;

import jade.core.Agent;

public interface PlanTemplate {
	
	public boolean getPlanState();
	
	public void start(boolean flag);
	
	public boolean getFlag();
	
	public Agent getMyAgent();
	
	public void stopCyclicBehaviour();

}
