package masrobot.plannerAgent;

import jade.core.Agent;
import masrobot.plan.TurnHeadPlan;
import masrobot.plan.DetectPlan;
import masrobot.plan.MovePlan;

public class PlannerAgent extends Agent{
	
	private MovePlan movePlan = new MovePlan(this);
	private DetectPlan detectPlan = new DetectPlan(this);
	private TurnHeadPlan turnHeadPlan = new TurnHeadPlan(this);
	
	
	protected void setup(){
		System.out.println("plannerAgent");
		while(true){
			detectPlan.start(true);
			detectPlan.action();
			
			if(detectPlan.getPlanState() == true && detectPlan.getDetectionResult())
			{
				detectPlan.stopCyclicBehaviour();
				movePlan.start(true);
				
				movePlan.action();
				if (movePlan.getPlanState()){
					movePlan.stopCyclicBehaviour();
					break;
				}
			} else if(detectPlan.getPlanState() == true && detectPlan.getFlag() == false){
				turnHeadPlan.action();
			}
		}
		System.out.println("The whole process completed!");
	}
}
