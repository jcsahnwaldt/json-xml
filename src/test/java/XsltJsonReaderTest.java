import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

class XsltJsonReaderTest {

  @Test
  void testParse() throws IOException, SAXException, TransformerException {
    try (InputStream in = getClass().getResourceAsStream("test.json")) {
      InputSource input = new InputSource(in);
      XMLReader reader = new XsltJsonReader();
      SAXSource source = new SAXSource(reader, input);
      StreamResult result = new StreamResult(System.out);
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      transformer.transform(source, result);
    }
  }
  
}
