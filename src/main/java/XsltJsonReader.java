import java.io.IOException;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;

public class XsltJsonReader implements XMLReader {
  
  private static final Map<String, Boolean> FEATURES = Map.of(
    "http://xml.org/sax/features/namespaces", true,
    "http://xml.org/sax/features/namespace-prefixes", false
  );

  private static final String NS = "http://www.w3.org/2005/xpath-functions";

  private EntityResolver entityResolver;
  private DTDHandler dtdHandler;
  private ContentHandler handler;
  private ErrorHandler errorHandler;

  @Override
  public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
    Boolean value = FEATURES.get(name);
    if (value == null) throw new SAXNotRecognizedException(name);
    return value.booleanValue();
  }

  @Override
  public void setFeature(String name, boolean v) throws SAXNotRecognizedException, SAXNotSupportedException {
    Boolean value = FEATURES.get(name);
    if (value == null) throw new SAXNotRecognizedException(name);
    if (v != value.booleanValue()) throw new SAXNotSupportedException(name + " = " + v);
  }

  @Override
  public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
    throw new SAXNotRecognizedException(name);
  }

  @Override
  public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
    throw new SAXNotRecognizedException(name);
  }

  @Override
  public void setEntityResolver(EntityResolver resolver) {
    this.entityResolver = resolver;
  }

  @Override
  public EntityResolver getEntityResolver() {
    return this.entityResolver;
  }

  @Override
  public void setDTDHandler(DTDHandler handler) {
    this.dtdHandler = handler;
  }

  @Override
  public DTDHandler getDTDHandler() {
    return this.dtdHandler;
  }

  @Override
  public void setContentHandler(ContentHandler handler) {
    this.handler = handler;
  }

  @Override
  public ContentHandler getContentHandler() {
    return this.handler;
  }

  @Override
  public void setErrorHandler(ErrorHandler handler) {
    this.errorHandler = handler;
  }

  @Override
  public ErrorHandler getErrorHandler() {
    return this.errorHandler;
  }

  @Override
  public void parse(InputSource input) throws IOException, SAXException {
    try {
      try (JsonParser parser = Json.createParser(input.getByteStream())) {
        handler.startDocument();
        handler.startPrefixMapping("", NS);
        AttributesImpl keyAttrs = new AttributesImpl();
        keyAttrs.addAttribute("", "key", "key", "CDATA", null);
        AttributesImpl noAttrs = new AttributesImpl();
        Attributes attrs = noAttrs;
        while (parser.hasNext()) {
          Event event = parser.next();
          switch (event) {
          case START_OBJECT:
            handler.startElement(NS, "map", "map", attrs);
            attrs = noAttrs;
            break;
          case END_OBJECT:
            handler.endElement(NS, "map", "map");
            break;
          case START_ARRAY:
            handler.startElement(NS, "array", "array", attrs);
            attrs = noAttrs;
            break;
          case END_ARRAY:
            handler.endElement(NS, "array", "array");
            break;
          case KEY_NAME:
            keyAttrs.setValue(0, parser.getString());
            attrs = keyAttrs;
            break;
          case VALUE_STRING:
            handler.startElement(NS, "string", "string", attrs);
            attrs = noAttrs;
            char[] str = parser.getString().toCharArray();
            handler.characters(str, 0, str.length);
            handler.endElement(NS, "string", "string");
            break;
          case VALUE_NUMBER:
            handler.startElement(NS, "number", "number", attrs);
            attrs = noAttrs;
            char[] num = parser.getString().toCharArray();
            handler.characters(num, 0, num.length);
            handler.endElement(NS, "number", "number");
            break;
          case VALUE_TRUE:
          case VALUE_FALSE:
            handler.startElement(NS, "boolean", "boolean", attrs);
            attrs = noAttrs;
            String bool = event == Event.VALUE_TRUE ? "true" : "false";
            handler.characters(bool.toCharArray(), 0, bool.length());
            handler.endElement(NS, "boolean", "boolean");
            break;
          case VALUE_NULL:
            handler.startElement(NS, "null", "null", attrs);
            attrs = noAttrs;
            handler.endElement(NS, "null", "null");
            break;
          }
        }
        handler.endPrefixMapping("");
        handler.endDocument();
      }
    }
    catch (JsonException e) {
      Throwable cause = e.getCause();
      if (cause instanceof IOException) throw (IOException)cause;
      else throw new SAXException(e);
    }
  }

  @Override
  public void parse(String systemId) throws IOException, SAXException {
    parse(new InputSource(systemId));
  }

}
