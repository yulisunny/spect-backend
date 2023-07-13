package site.spect.beta.file;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Component
public class PdfSinglePageExtractor {

    byte[] extract(File pdfFile, int pageNumber) throws IOException {
        try (PDDocument pdf = PDDocument.load(pdfFile)) {
            Splitter splitter = new Splitter();
            splitter.setStartPage(pageNumber);
            splitter.setEndPage(pageNumber);
            PDDocument singlePage = splitter.split(pdf).get(0);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            singlePage.save(baos);
            return baos.toByteArray();
        }
    }

}
