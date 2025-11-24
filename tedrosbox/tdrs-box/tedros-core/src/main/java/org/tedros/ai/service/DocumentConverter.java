package org.tedros.ai.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.tedros.util.TLoggerUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

public class DocumentConverter {

    private static final Logger log = TLoggerUtil.getLogger(DocumentConverter.class);
    
    // Extensões conhecidas
    private static final Set<String> IMG_EXTS = Set.of("jpg", "jpeg", "png", "gif", "webp", "bmp");
    private static final Set<String> TEXT_EXTS = Set.of("txt", "csv", "json", "xml", "md", "html", "java", "py", "js", "css", "sql", "log");

    // Record para retorno unificado. 
    // 'textContent': Texto extraído para leitura.
    // 'base64Images': Lista de Data URLs (data:image/...) para visão.
    public record ProcessedDocument(String textContent, List<String> base64Images, boolean isSupported) {}

    public static ProcessedDocument processFile(byte[] bytes, String fileName) {
        String ext = getExtension(fileName);

        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
            
            // 1. Tratamento de Documentos Complexos (Office/PDF)
            if (ext.equals("pdf")) return processPdf(bytes);
            if (ext.equals("docx")) return processDocx(bais);
            if (ext.equals("xlsx")) return processXlsx(bais);

            // 2. Tratamento de Imagens Nativas
            if (IMG_EXTS.contains(ext)) {
                String mimeType = "image/" + (ext.equals("jpg") ? "jpeg" : ext);
                String base64 = Base64.getEncoder().encodeToString(bytes);
                String dataUrl = "data:" + mimeType + ";base64," + base64;
                
                return new ProcessedDocument(
                    "Este arquivo é uma imagem (" + fileName + "). Analise-a visualmente.", 
                    List.of(dataUrl), 
                    true
                );
            }

            // 3. Tratamento de Texto Puro
            if (TEXT_EXTS.contains(ext)) {
                String text = new String(bytes, StandardCharsets.UTF_8);
                // Limite de segurança (ex: 40k chars)
                if (text.length() > 40000) text = text.substring(0, 40000) + "\n... [Texto truncado por tamanho]";
                return new ProcessedDocument(text, List.of(), true);
            }

            // 4. Fallback (Binário ou Desconhecido)
            return new ProcessedDocument(
                "Formato de arquivo (" + ext + ") não suporta extração de conteúdo textual direto pelo sistema. O arquivo foi anexado via upload.", 
                List.of(), 
                false
            );

        } catch (Exception e) {
            log.error("Erro processando arquivo " + fileName, e);
            return new ProcessedDocument("Erro ao ler arquivo: " + e.getMessage(), List.of(), false);
        }
    }

    private static String getExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) return "";
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    // --- Processadores Específicos ---

    private static ProcessedDocument processPdf(byte[] bytes) throws IOException {
        try (PDDocument document = PDDocument.load(bytes)) {
            // Texto
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            String text = stripper.getText(document);
            if (text.length() > 30000) text = text.substring(0, 30000) + "... [texto truncado]";

            // Imagens (Visão)
            List<String> images = new ArrayList<>();
            PDFRenderer renderer = new PDFRenderer(document);
            int pagesToRender = Math.min(document.getNumberOfPages(), 3); 

            for (int i = 0; i < pagesToRender; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 150, ImageType.RGB);
                images.add(imageToDataUrl(image, "jpg"));
            }

            return new ProcessedDocument(text, images, true);
        }
    }

    private static ProcessedDocument processDocx(ByteArrayInputStream stream) throws IOException {
        try (XWPFDocument doc = new XWPFDocument(stream);
             XWPFWordExtractor extractor = new XWPFWordExtractor(doc)) {
            String text = extractor.getText();
            if (text.length() > 30000) text = text.substring(0, 30000) + "... [texto truncado]";
            return new ProcessedDocument(text, List.of(), true);
        }
    }

    private static ProcessedDocument processXlsx(ByteArrayInputStream stream) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(stream)) {
            StringBuilder sb = new StringBuilder();
            // Pega a primeira aba apenas para contexto
            var sheet = workbook.getSheetAt(0);
            sb.append("Planilha (Aba 1): ").append(sheet.getSheetName()).append("\n");
            
            // Limita a 100 linhas para não estourar
            int rowCount = 0;
            for (var row : sheet) {
                if (rowCount++ > 100) { sb.append("... [mais linhas omitidas]"); break; }
                row.forEach(cell -> sb.append(cell.toString()).append(" | "));
                sb.append("\n");
            }
            return new ProcessedDocument(sb.toString(), List.of(), true);
        }
    }

    private static String imageToDataUrl(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}