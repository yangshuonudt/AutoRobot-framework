/******************************************************
author : yangsen
time   : 2016-04-19, Tuesday

name  : ASensor
type  : agent
package :autorobotpackage.mas
version: 0.1

description: sensor agent用来管理机器人的传感设备，并
获取传感信息，
******************************************************/
package core.autorobotpackage.mas ;

import jade.domain.* ;
import jade.domain.FIPAAgentManagement.* ;
import jade.domain.JADEAgentManagement.* ;
import java.util.* ;

import core.autorobotpackage.mas.basicoperation.RegistService;
import core.autorobotpackage.mas.xmlparser.XmlParser;
import core.autorobotpackage.mas.xmlparser.XmlRosNode;
import jade.core.Agent ;

public class SensorAgent extends Agent {
   ArrayList<XmlRosNode> nodeList ;

   RegistService rs ;

   /**
    *  [agent的启动方法，包含初始化操作，ASensor的子类需要在自身的setup方法中
    *  调用父类的setup方法，即super.setup()]
    *  @method setup
    */
   public void setup() {
   }

   /**
    *  [agent注销后需要将注册的服务注销掉]
    *  @method takeDown
    */
   public void takeDown() {
      this.deRegist() ;
   }

   /**
    *  [封装了xmlparser的方法，返回xmlrosnode]
    *  @method getXmlRosNode
    *  @param  description [节点的描述信息，参考xml文件]
    */
   protected void getXmlRosNode( String description ) {
      if( nodeList == null ) {
         nodeList = new ArrayList<XmlRosNode>() ;
      }
      XmlParser xp = XmlParser.getXmlParser() ;
      nodeList.add( xp.getRosNode(description) ) ;
   }

   /**
    *  [根据名字删除一个节点]
    *  @method getXmlRosNode
    *  @param  name [节点的名字]
    */
   protected void removeXmlRosNode( String name ) {
      Iterator<XmlRosNode> it = nodeList.iterator() ;
      while( it.hasNext() ) {
         if( it.next().getName().equals(name) ){
            it.remove() ;
            break ;
         }
      }
   }

   /**
    *  [向DF(解)注册一个服务，供其他agent查找]
    *  @method regASensorSrv
    *  @param  serviceName [服务的名称]
    */
   protected  void regASensorSrv ( String serviceName ) {
      DFAgentDescription dfd = new DFAgentDescription() ;
      ServiceDescription sd = new ServiceDescription();
      Agent agent ;
      boolean isRegisted ;
      dfd.setName( getAID() ) ;
      sd.setType("sensor") ;
      sd.setName("sensor") ;
      dfd.addServices(sd) ;
      try {
         DFService.register( this , dfd ) ;
      }catch ( FIPAException fe ) {
         fe.printStackTrace() ;
      }
   }
   protected void deRegist() {
      try {
         DFService.deregister( this ) ;
      }
      catch ( FIPAException fe ) {
         fe.printStackTrace() ;
      }
   }


}
