import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

class XsltJsonReaderTest {

  @Test
  void testParse() throws IOException, SAXException {
    InputSource source = new InputSource(new StringReader("{}"));
    new XsltJsonReader().parse(source);
  }

}
