package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.webcohesion.ofx4j.domain.data.ResponseEnvelope;
import com.webcohesion.ofx4j.domain.data.common.TransactionList;
import com.webcohesion.ofx4j.io.AggregateUnmarshaller;
import com.webcohesion.ofx4j.io.BaseOFXReader;
import com.webcohesion.ofx4j.io.DefaultHandler;

import com.webcohesion.ofx4j.io.OFXParseException;

/**
 * import com.webcohesion.ofx4j.model.OFXBankAccount; import
 * com.webcohesion.ofx4j.model.OFXDocument; import
 * com.webcohesion.ofx4j.model.OFXTransaction; import
 * com.webcohesion.ofx4j.model.OFXV1Envelope; import
 * com.webcohesion.ofx4j.model.common.AccountInfo; import
 * com.webcohesion.ofx4j.model.common.BankAccountDetails; import
 * com.webcohesion.ofx4j.model.common.Transaction; import
 * com.webcohesion.ofx4j.model.common.TransactionList;
 */

public class OFXFileViewer {
  private static final Log LOG = LogFactory.getLog(OFXFileViewer.class);

  public static void main(String[] argv) {

//    String FILE_TO_READ = "F:\\dev\\Tools\\OFXViewer\\resources\\example-response.ofx";
    // String FILE_TO_READ =
    // "F:\\dev\\Tools\\OFXViewer\\resources\\NL34INGB0000187782_NL34INGB0000187782_20231001_20231009.ofx";
    // String FILE_TO_READ = "F:\\dev\\Tools\\OFXViewer\\resources\\response.ofx";
    // String FILE_TO_READ =
    // "F:\\dev\\Tools\\OFXViewer\\resources\\NL90KNAB0445266309_Alle_rekeningen.ofx";
    String FILE_TO_READ = "F:\\dev\\Tools\\OFXViewer\\resources\\Prive_RK__20231106134904.ofx";
    final Map<String, String> headers = new HashMap<String, String>();
    final Stack<Map<String, Object>> aggregateStack = new Stack<Map<String, Object>>();
    TreeMap<String, Object> root = new TreeMap<String, Object>();
    aggregateStack.push(root);

    AggregateUnmarshaller<ResponseEnvelope> unmarshaller = new AggregateUnmarshaller<ResponseEnvelope>(
        ResponseEnvelope.class);

    try {
      FileInputStream file = new FileInputStream(FILE_TO_READ);
      BaseOFXReader reader = new BaseOFXReader() {
        @Override
        protected void parseV1FromFirstElement(Reader reader) throws IOException, OFXParseException {
          // fail();
        }
      };
//      NanoXMLOFXReader reader = new NanoXMLOFXReader();
      reader.setContentHandler(new DefaultHandler() {

        @Override
        public void onHeader(String name, String value) {
          LOG.debug("onHeader: " + name + ":" + value);
          headers.put(name, value);
        }

        @Override
        public void onElement(String name, String value) {
          LOG.debug("onElement: " + name + "=" + value);

          char[] tabs = new char[aggregateStack.size() * 2];
          Arrays.fill(tabs, ' ');
          LOG.debug(new String(tabs) + name + "=" + value);

          aggregateStack.peek().put(name, value);
        }

        @Override
        public void startAggregate(String aggregateName) {
          LOG.debug("Start aggregateName: " + aggregateName);
          char[] tabs = new char[aggregateStack.size() * 2];
          Arrays.fill(tabs, ' ');
          LOG.debug(new String(tabs) + aggregateName + " {");

          TreeMap<String, Object> aggregate = new TreeMap<String, Object>();
          aggregateStack.peek().put(aggregateName, aggregate);
          aggregateStack.push(aggregate);
        }

        @Override
        public void endAggregate(String aggregateName) {
          LOG.debug("End aggregateName: " + aggregateName);
          aggregateStack.pop();

          char[] tabs = new char[aggregateStack.size() * 2];
          Arrays.fill(tabs, ' ');
          LOG.debug(new String(tabs) + "}");
        }
      });
      ResponseEnvelope a = unmarshaller.unmarshal(file);

      file = new FileInputStream(FILE_TO_READ);
      reader.parse(file);
      // OFXHandler ofxhand = nano.getContentHandler();
      // ofxhand.
      System.out.println("woooo It workssss!!!!");
      TransactionList translist = new TransactionList();
      translist.getEnd();

    } catch (OFXParseException e) {
      System.out.println("Message : " + e.getMessage());
    } catch (Exception e1) {
      System.out.println("Other Message : " + e1.getMessage());
    }
  }
}
