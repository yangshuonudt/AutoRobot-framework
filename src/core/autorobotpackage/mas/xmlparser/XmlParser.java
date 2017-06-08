/******************************************************
author : yangsen
time   : 2016-04-07, Thursday

name  : XmlParser
type  : class
package :autorobotpackage.mas.xmlparser
version: 0.1

description: 解析xml文件,按功能查找节点的名字，按节点
名字查找话题与服务
******************************************************/
package core.autorobotpackage.mas.xmlparser ;

import org.w3c.dom.* ;
import javax.xml.parsers.* ;
import java.io.* ;
import java.util.* ;

public class XmlParser {

   static XmlParser parser = null;
   static Document doc ;

   private XmlParser() {
   }
   public static void init() {
      try{
         File  inputFile = new File("src/autorobotpackage/mas/nodes.xml") ;
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance() ;
         DocumentBuilder builder = factory.newDocumentBuilder() ;
         doc = builder.parse(inputFile) ;
         doc.getDocumentElement().normalize() ;
      }catch (Exception e) {
         e.printStackTrace() ;
      }
   }
   public static XmlParser getXmlParser() {
      if(parser == null) {
         parser = new XmlParser() ;
         init() ;
      }
      return parser ;
   }

   /**
    *  [给出节点的描述，获取ros节点信息]
    *  @method getRosNode
    *  @param  description [节点的描述信息，参考xml文件]
    *  @return XmlRosNode  [返回xmlrosnode]
    */
   public XmlRosNode getRosNode(String description){
      XmlRosNode xrn = new XmlRosNode() ;;
      NodeList nList = doc.getElementsByTagName("node") ;
      for(int temp = 0 ; temp < nList.getLength() ; temp++) {
         Node nNode = nList.item(temp) ;
         Element element = (Element) nNode ;

         if( description.equals(element.getAttribute("description")) ) {
            xrn.setName( element.getAttribute("name") ) ;
            xrn.setDescription( element.getAttribute("description") ) ;
            NodeList topicList = element.getElementsByTagName("topic") ;
            Element topic = (Element) topicList.item(0) ;
            xrn.setTopic( topic.getAttribute("name") , topic.getAttribute("type") ) ;
            NodeList serviceList = element.getElementsByTagName("service") ;
            for( int count = 0 ; count < serviceList.getLength() ; count++ ) {
               Element service = (Element) serviceList.item(count) ;
               xrn.addService( service.getAttribute("name") ,
                               service.getAttribute("type") ,
                               service.getAttribute("function") ) ;
            }
            break ;
         }
      }
      return xrn ;
   }

}
