package FollowMe.agent ;

import jade.core.* ;
import jade.wrapper.* ;
import jade.domain.* ;
import jade.domain.FIPAAgentManagement.* ;
import jade.domain.JADEAgentManagement.* ;

public class StartAgent {
   public static void main( String[] args ) {
      long startTime = System.currentTimeMillis() ;

      jade.core.Runtime rt = jade.core.Runtime.instance() ;
		rt.setCloseVM ( true ) ;
		Profile pMain = new ProfileImpl(null, 8888, null);
		System.out.println("Launching a whole in-process platform..."+pMain);
		jade.wrapper.AgentContainer mc = rt.createMainContainer(pMain);
		ProfileImpl pContainer = new ProfileImpl(null, 8888, null);
		System.out.println("Launching the agent container ..."+pContainer);
		try {
			AgentController actor = mc.createNewAgent("actor" , "agent.Actuator", null );
         AgentController sensor = mc.createNewAgent("sensor" ,"agent.Sensor" , null ) ;
			AgentController planner = mc.createNewAgent("plan" , "agent.Planner" , null ) ;
         sensor.start() ;
			actor.start() ;
			planner.start() ;
		} catch (StaleProxyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

      long endTime = System.currentTimeMillis() ;
      System.out.println( "JADE 启动时间： " + ( endTime - startTime ) + "ms" );
   }
}
