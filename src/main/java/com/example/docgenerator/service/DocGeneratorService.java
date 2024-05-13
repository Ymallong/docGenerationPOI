package com.example.docgenerator.service;

import com.example.docgenerator.model.TemplateData;
import lombok.Value;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class DocGeneratorService {

    public void generateAndSaveDocument(TemplateData data, String outputPath) throws Exception {
        XWPFDocument document = new XWPFDocument(OPCPackage.open("Template.docx"));

        replacePlaceholders(document, data);

        saveDocument(document, outputPath);
    }

    private void replacePlaceholders(XWPFDocument document, TemplateData data) {
        for (XWPFParagraph p : document.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null && text.contains("{{name}}")) {
                        text = text.replace("{{name}}", data.getName());
                        r.setText(text, 0);
                    }
                    if (text != null && text.contains("{{email}}")) {
                        text = text.replace("{{email}}", data.getEmail());
                        r.setText(text, 0);
                    }
                    if (text != null && text.contains("{{phone}}")) {
                        text = text.replace("{{phone}}", data.getPhone());
                        r.setText(text, 0);
                    }
                }
            }
        }
    }

    private void saveDocument(XWPFDocument document, String outputPath) throws IOException {
        FileOutputStream out = new FileOutputStream(outputPath);
        document.write(out);
        out.close();
        document.close();
    }
}
