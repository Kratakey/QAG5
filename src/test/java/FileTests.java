import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import net.lingala.zip4j.core.ZipFile;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FileTests {

    @Test
    void txtContentCheck() throws Exception {
        String text;
        try (InputStream stream = new FileInputStream("src/test/resources/1.First.txt")) {
            text = new String(stream.readAllBytes(), "UTF-8");
            assertThat(text).contains("Faker");
        }
    }

    @Test
    void pdfContentCheck() throws Exception {
        File file = new File("src/test/resources/2.Second.pdf");
        PDF text = new PDF(file);
        assertThat(text.text).contains("xPeke");
    }

    @Test
    void xlsxContentCheck() throws Exception {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("3.Third.xlsx")) {
            XLS text = new XLS(stream);
            assertThat(text.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue()).isEqualTo("Rekkles");
        }
    }

    @Test
    void zipContentCheck() throws Exception {
        ZipFile zipFile = new ZipFile("src/test/resources/4.Forth.zip");
        if (zipFile.isEncrypted()) {
            zipFile.setPassword("Apdo");
        }
        zipFile.extractAll("src/test/resources");
        String text;
        try (InputStream stream = new FileInputStream("src/test/resources/Forth.txt")) {
            text = new String(stream.readAllBytes(), "UTF-8");
            assertThat(text).contains("Dopa");
        }
    }

    @Test
    void docContentCheck() throws Exception {
        File file = new File("src/test/resources/5.Fifth.docx");
        String text;
        try (FileInputStream fis = new FileInputStream(file.getAbsolutePath())) {
            XWPFDocument document = new XWPFDocument(fis);
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph para : paragraphs) {
                text = para.getText();
                assertThat(text).contains("Uzi");
            }
        }
    }
}
