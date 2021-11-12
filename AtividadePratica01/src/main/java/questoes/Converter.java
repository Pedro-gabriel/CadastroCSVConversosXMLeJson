package questoes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;

public class Converter {

    private static Scanner entrada;
    private static List<Livro> livros;

    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException {
        entrada = new Scanner(System.in);
        System.out.print("Digite o nome do arquivo .csv: ");
        String fileName = entrada.nextLine();
        livros = convertToObjectList(fileName);
        livroXml(livros);
        livroJson(livros);
    }

    private static Livro readCsvLine(String line) {
        String[] splitedLivro = line.split(",");
        Livro livro = new Livro();
        livro.setIsbn(splitedLivro[0]);
        livro.setTitulo(splitedLivro[1]);
        livro.setEditora(splitedLivro[2]);
        livro.setAno_publicacao(splitedLivro[3]);
        return livro;
    }

    private static ArrayList<Livro> convertToObjectList(String fileName) {
        ArrayList<Livro> livros = new ArrayList<>();
        InputStream inputStream = Converter.class.getClassLoader().getResourceAsStream(fileName);
        Scanner entradaArquivo = new Scanner(inputStream);
        while (entradaArquivo.hasNextLine()) {
            Livro livro = readCsvLine(entradaArquivo.nextLine());
            livros.add(livro);
        }
        return livros;
    }

    private static void livroJson(List<Livro> livro) throws IOException {
        ObjectMapper om = new ObjectMapper();
        om.writeValue(new File("C:\\Users\\Pedro\\AtividadePratica01\\src\\main\\resources\\livro.json"), livro);
        System.out.println("Json ok!");
    }

    private static void livroXml(List<Livro> livros) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.newDocument();
        Element rootElement = doc.createElement("livros");
        doc.appendChild(rootElement);

        for (Livro livro: livros) {
            Element taskElement = convertElement(livro, doc);
            rootElement.appendChild(taskElement);
        }

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource domSource = new DOMSource(doc);
        StreamResult sr = new StreamResult(new File("C:\\Users\\Pedro\\AtividadePratica01\\src\\main\\resources\\livro.xml"));

        transformer.transform(domSource, sr);
        System.out.println("XMl ok!");
    }

    private static Element convertElement(Livro livro, Document doc) {
        Element e = doc.createElement("Livro");

        Attr isbnAttr = doc.createAttribute("Isbn");
        isbnAttr.setValue(livro.getIsnb().toString());
        e.setAttributeNode(isbnAttr);

        Element tituloElement = doc.createElement("titulo");
        tituloElement.appendChild(doc.createTextNode(livro.getTitulo()));
        e.appendChild(tituloElement);

        Element editoraElement = doc.createElement("editora");
        editoraElement.appendChild(doc.createTextNode(livro.getEditora()));
        e.appendChild(editoraElement);

        Element ano_publicacaoElement = doc.createElement("ano_publicacao");
        ano_publicacaoElement.appendChild(doc.createTextNode(livro.getAno_publicacao()));
        e.appendChild(ano_publicacaoElement);

        return e;
    }
}
