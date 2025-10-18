package com.github.gabrielspk.cadastro_os.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import com.github.gabrielspk.cadastro_os.dto.v1.DocumentMatchDTO;

public class ExcelReportGenerator {

    public static Path criarRelatorio(List<DocumentMatchDTO> documentosEncontrados,
                                      List<String> documentosNaoEncontrados,
                                      String nomeArquivo,
                                      String diretorioAtual) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Relatório");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Documentos em arquivos");
        header.createCell(1).setCellValue("Nomenclatura arquivo");
        header.createCell(2).setCellValue("Não encontrado");

        int linhaDocsEncontrados = 1;
        int linhaDocsNaoEncontrados = 1;

        for (DocumentMatchDTO doc : documentosEncontrados) {
            Row row = sheet.createRow(linhaDocsEncontrados++);
            row.createCell(0).setCellValue(doc.getDocumento());
            row.createCell(1).setCellValue(doc.getArquivo());
        }

        // Preenche não encontrados
        for (String doc : documentosNaoEncontrados) {
            Row row = sheet.getRow(linhaDocsNaoEncontrados);
            if (row == null) row = sheet.createRow(linhaDocsNaoEncontrados);
            row.createCell(2).setCellValue(doc);
            linhaDocsNaoEncontrados++;
        }

        // Ajusta tamanho das colunas
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

        // Gera caminho e nome final do arquivo
        Path dir = Paths.get(diretorioAtual);
        if (!Files.exists(dir)) Files.createDirectories(dir);

        Path caminhoRelatorio = dir.resolve(incrementarNomeArquivo(nomeArquivo));

        try (FileOutputStream out = new FileOutputStream(caminhoRelatorio.toFile())) {
            workbook.write(out);
        } catch (IOException e) {
            throw new IOException("Não foi possível gerar o relatório — o arquivo pode estar aberto.", e);
        } finally {
            workbook.close();
        }

        return caminhoRelatorio;
    }

    private static String incrementarNomeArquivo(String nomeArquivo) {
        Path path = Paths.get(nomeArquivo);
        String nomeBase = path.getFileName().toString();
        String nomeSemExtensao = nomeBase.replaceFirst("\\.xlsx$", "");
        Path dir = path.getParent();

        int contador = 1;
        Path novoCaminho = dir != null ? dir.resolve(nomeBase) : Path.of(nomeBase);

        while (Files.exists(novoCaminho)) {
            String novoNome = nomeSemExtensao + "_" + contador + ".xlsx";
            novoCaminho = dir != null ? dir.resolve(novoNome) : Path.of(novoNome);
            contador++;
        }

        return novoCaminho.getFileName().toString();
    }
}
