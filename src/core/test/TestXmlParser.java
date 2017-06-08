package core.test ;

import java.util.* ;

import core.autorobotpackage.mas.xmlparser.XmlParser;
import core.autorobotpackage.mas.xmlparser.XmlRosNode;
import core.autorobotpackage.mas.xmlparser.XmlRosService;
public class TestXmlParser {

   XmlParser parser = XmlParser.getXmlParser() ;
   public XmlParser getParser() {
      return this.parser ;
   }
   public static void main(String[] args) {
      TestXmlParser test = new TestXmlParser() ;
      XmlRosNode xrn = test.getParser().getRosNode("takepicture") ;
      System.out.println("name: " + xrn.getName()) ;
      System.out.println("description: " + xrn.getDescription()) ;

      System.out.println("topic: \n\tname: " + xrn.getTopic().getName()) ;
      System.out.println("\ttype: " + xrn.getTopic().getType()) ;
      Iterator<XmlRosService> itor = xrn.getServiceList().iterator() ;
      while(itor.hasNext()) {
         XmlRosService xrs = itor.next() ;
         System.out.println("service:\n\tname: " + xrs.getName()) ;
         System.out.println("\ttype: " + xrs.getType()) ;
         System.out.println("\tfunction: " + xrs.getFunction()) ;
      }

   }
}
