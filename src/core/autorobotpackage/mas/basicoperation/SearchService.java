/******************************************************
author : yangsen
time   : 2016-04-14, Suesday

name  : SearchService
type  : class
package :autorobotpackage.mas.basicoperation
version: 0.1

description:搜索agent服务，获得AID列表
******************************************************/
package core.autorobotpackage.mas.basicoperation ;

import jade.core.* ;
import jade.domain.* ;
import jade.domain.FIPAAgentManagement.* ;
import jade.domain.JADEAgentManagement.* ;
import java.util.* ;

public class SearchService {
   DFAgentDescription template ;
   ServiceDescription sd ;
   Agent agent ;

   public SearchService( Agent _agent , String type ) {
      this.agent = _agent ;
      this.template = new DFAgentDescription() ;
      this.sd = new ServiceDescription() ;
      this.sd.setType(type) ;
      this.template.addServices(sd) ;
   }

   /**
    *  [查询提供type类型服务的Agent]
    *  @method getAgent
    *  @return [Agent的ID的列表]
    */
   public ArrayList<AID> getAgent() {
      try {
         ArrayList<AID> aidList = new ArrayList<AID>() ;
         DFAgentDescription[] result = DFService.search( this.agent , this.template ) ;
         for ( int i = 0 ; i < result.length ; i ++ )
            aidList.add( result[i].getName() ) ;
         return aidList;
      } catch( FIPAException fe ) {
         fe.printStackTrace() ;
      }
      return null ;
   }
}
