package com.example.docgenerator.controller;

import com.example.docgenerator.model.TemplateData;
import com.example.docgenerator.service.DocGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/documents")
public class DocGeneratorController {

    @Autowired
    private DocGeneratorService docGeneratorService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateDocument(@RequestBody TemplateData data,
                                                   @RequestParam(defaultValue = "Generated/generatedDocument.docx") String outputPath) {
        try {
            docGeneratorService.generateAndSaveDocument(data, outputPath);
            return ResponseEntity.ok("Document generated successfully at: " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();  // This will help you debug the issue by printing the stack trace to the console or log file.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating document: " + e.getMessage());
        }
    }
}

