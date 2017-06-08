/******************************************************
author : yangsen
time   : 2016-04-20, Wensday

name  : ARobot
type  : agent
package :autorobotpackage.mas
version: 0.1

******************************************************/
package core.autorobotpackage.mas ;

import java.util.* ;

import core.autorobotpackage.mas.basicoperation.SearchService;
import core.autorobotpackage.stateinfo.State;
import jade.core.* ;

public class RobotAgent extends Agent{

   /**
    *  [agent的启动方法，包含初始化操作，ASensor的子类需要在自身的setup方法中
    *  调用父类的setup方法，即super.setup()]
    *  @method setup
    */
   public void setup() {
      this.getASensorList() ;
      this.getAActuatorList() ;
   }

   /**
    *  [查找所有的sensor Agent，并将他们的AID的列表存储到State中]
    *  @method getASensorList
    */
   public void getASensorList(){
      SearchService ss = new SearchService( this , "sensor" ) ;
      State.setData( "ASensorList" , ss.getAgent() ) ;
   }

   /**
    *  [查找所有的actutor agent，并将他们的AID的列表存储到State中]
    *  @method getAActuatorList
    */
   public void getAActuatorList() {
      SearchService ss = new SearchService( this , "actutor" ) ;
      State.setData( "AActuatorList" , ss.getAgent() ) ;
   }
}
