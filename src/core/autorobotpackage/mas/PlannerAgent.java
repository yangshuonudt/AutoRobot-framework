/******************************************************
author : yangsen
time   : 2016-04-19, Tuesday

name  : APlanner
type  : agent
package :autorobotpackage.mas
version: 0.1

description: planner agent用来做规划，
******************************************************/
package core.autorobotpackage.mas ;

import jade.core.* ;
import jade.core.behaviours.* ;
import core.autorobotpackage.dataprocess.ProcessingSense;

import java.util.* ;

public class PlannerAgent extends Agent {

   ProcessingSense ps ;
   ArrayList<AID> asList , aaList ;


   /**
    *  [agent的启动方法，包含初始化操作，ASensor的子类需要在自身的setup方法中
    *  调用父类的setup方法，即super.setup()]
    *  @method setup
    */
   public void setup() {

   }

   public void getASList() {

   }

   public void getAAList() {

   }



}
