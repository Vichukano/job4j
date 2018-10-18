package ru.job4j.sql.magnit;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

/**
 * Класс описывает конвертацию xml на основе шаблона XSLT.
 */
public class ConvertXSQL {

    /**
     * Метод производит конвертацию xml.
     * @param src файл xml, который нужно конвертировать.
     * @param dst новый файл xml - результат конвертации.
     * @param scheme шаблон, по которому будет производиться конвертация.
     * @throws FileNotFoundException в случае не нахождения файла в указанной директории.
     */
   public void convert(File src, File dst, File scheme) throws FileNotFoundException {
       Source xsl = new StreamSource(scheme);
       Source xml = new StreamSource(src);
       TransformerFactory factory = TransformerFactory.newInstance();
       Transformer transformer = null;
       try {
           transformer = factory.newTransformer(xsl);
           transformer.transform(xml, new StreamResult(new FileOutputStream(dst)));
       }  catch (TransformerException e) {
           e.printStackTrace();
       }
   }
}
