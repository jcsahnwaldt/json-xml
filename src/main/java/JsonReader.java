import java.io.IOException;

import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

public class JsonReader implements XMLReader {

  private EntityResolver entityResolver;
  private DTDHandler dtdHandler;
  private ContentHandler handler;
  private ErrorHandler errorHandler;

  @Override
  public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
    throw new SAXNotRecognizedException("Feature: " + name);
  }

  @Override
  public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
    throw new SAXNotRecognizedException("Feature: " + name);
  }

  @Override
  public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
    throw new SAXNotRecognizedException("Property: " + name);
  }

  @Override
  public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
    throw new SAXNotRecognizedException("Property: " + name);
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
  }

  @Override
  public void parse(String systemId) throws IOException, SAXException {
    parse(new InputSource(systemId));
  }

}
