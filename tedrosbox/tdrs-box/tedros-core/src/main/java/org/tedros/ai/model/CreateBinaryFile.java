package org.tedros.ai.model;

import org.tedros.ai.function.TRequiredProperty;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Request to create any file type on server (PDF, DOCX, XLSX, PNG, ZIP, etc.)")
public class CreateBinaryFile {

    @TRequiredProperty
    @JsonPropertyDescription("File name without extension (e.g. 'relatorio_final', 'evidencias')")
    private String name;

    @TRequiredProperty
    @JsonPropertyDescription("File extension without dot (e.g. 'pdf', 'docx', 'xlsx', 'png', 'zip')")
    private String extension;

    @TRequiredProperty
    @JsonPropertyDescription("File content encoded in Base64 (full binary data)")
    private String base64Content;

    @JsonPropertyDescription("Optional subfolder inside export directory (e.g. '2025/04', 'evidencias/issue-12345')")
    private String subfolder;

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getExtension() { return extension; }
    public void setExtension(String extension) { this.extension = extension; }

    public String getBase64Content() { return base64Content; }
    public void setBase64Content(String base64Content) { this.base64Content = base64Content; }

    public String getSubfolder() { return subfolder; }
    public void setSubfolder(String subfolder) { this.subfolder = subfolder; }
}