package data;

public class XMLReaderException extends RuntimeException {
    private final XMLReader xmlReader;

    public XMLReaderException(XMLReader xmlReader){
        this.xmlReader = xmlReader;
    }

    public XMLReaderException (String message ,XMLReader xmlReader){
        super(message);
        this.xmlReader=xmlReader;
    }

    public XMLReader getXmlReader() {
        return xmlReader;
    }
}