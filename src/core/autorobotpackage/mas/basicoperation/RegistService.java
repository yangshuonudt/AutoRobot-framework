/******************************************************
author : yangsen
time   : 2016-04-14, Suesday

name  : RegistService
type  : class
package :autorobotpackage.mas.basicoperation
version: 0.1

description: 向df注册服务或注销服务
******************************************************/
package core.autorobotpackage.mas.basicoperation ;

import jade.core.* ;
import jade.domain.* ;
import jade.domain.FIPAAgentManagement.* ;
import jade.domain.JADEAgentManagement.* ;

public class RegistService {
   private DFAgentDescription dfd ;
   private ServiceDescription sd ;
   private Agent agent ;
   private boolean isRegisted ;

   /**
    *  [构造一个用于向DF注册服务的类]
    *  @method RegistService
    *  @param  agent       [注册服务的agent]
    *  @param  type        [服务的类型]
    *  @param  serviceName [服务名称]
    */
   public RegistService( Agent _agent , String type , String serviceName ) {
      this.dfd = new DFAgentDescription() ;
      this.sd = new ServiceDescription() ;

      this.dfd.setName( this.agent.getAID() ) ;
      this.sd.setType(type) ;
      this.sd.setName(serviceName) ;
      this.dfd.addServices(sd) ;
      this.isRegisted = false ;
   }

   /**
    *  [向df进行注册]
    *  @method registToDF
    */
   public void registToDF() {
      try {
         DFService.register( this.agent , dfd ) ;
         this.isRegisted = true ;
      }catch ( FIPAException fe ) {
         fe.printStackTrace() ;
      }
   }

   /**
    *  [注销服务]
    *  @method deRegist
    */
   public void deRegist() {
      if( this.isRegisted == true ) {
         try {
            DFService.deregister( this.agent ) ;
         }
         catch ( FIPAException fe ) {
            fe.printStackTrace() ;
         }
      }
   }



}
