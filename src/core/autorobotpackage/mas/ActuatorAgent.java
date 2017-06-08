/******************************************************
author : yangsen
time   : 2016-04-19, Tuesday

name  : ASensor
type  : agent
package :autorobotpackage.mas
version: 0.1

description: actutor agent用来管理机器人的物理部件
 ******************************************************/
package core.autorobotpackage.mas ;

import core.autorobotpackage.mas.basicoperation.RegistService;
import core.autorobotpackage.mas.event.SensorEvent;
import core.autorobotpackage.mas.xmlparser.XmlParser;
import core.autorobotpackage.mas.xmlparser.XmlRosNode;
import java.util.* ;

import jade.core.Agent ;

public class ActuatorAgent extends Agent{
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
	 *  [向DF(解)注册一个服务，供其他agent查找]
	 *  @method regASensorSrv
	 *  @param  serviceName [服务的名称]
	 */
	protected  void regASensorSrv ( String serviceName ) {
		if( this.rs == null ) {
			this.rs = new RegistService( this , "actuator" , serviceName ) ;
		}
		this.rs.registToDF() ;
	}
	protected void deRegist() {
		if( this.rs != null ) {
			this.rs.deRegist() ;
		}
	}
}
