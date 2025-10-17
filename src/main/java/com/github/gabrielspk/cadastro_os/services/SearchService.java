package com.github.gabrielspk.cadastro_os.services;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.gabrielspk.cadastro_os.dto.v1.DocumentMatchDTO;
import com.github.gabrielspk.cadastro_os.dto.v1.SearchResultDTO;
import com.github.gabrielspk.cadastro_os.exceptions.SearchException;
import com.github.gabrielspk.cadastro_os.utils.EncodingDetector;

@Service
public class SearchService {

    private static final Logger log = LoggerFactory.getLogger(SearchService.class);

    private static final List<String> EXTENSOES_PADRAO_DESCARTADAS = List.of(
        ".fpl", ".zip", ".ini", ".pdf", ".xlsx"
    );

    public SearchResultDTO executeDocumentsSearch(String diretorioAtual,
                                                  boolean validarFpl,
                                                  List<String> documentos) {

        long inicio = System.currentTimeMillis();
        List<String> extensoesDescartadas = getExtensoesDescartadas(validarFpl);

        // Listas thread-safe
        List<DocumentMatchDTO> documentosEncontrados = Collections.synchronizedList(new ArrayList<>());
        Set<String> documentosNaoEncontrados = ConcurrentHashMap.newKeySet();
        documentosNaoEncontrados.addAll(documentos);

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            final int[] arquivosPesquisados = {0};

            List<Callable<Void>> tarefas = Files.walk(Path.of(diretorioAtual))
                .filter(Files::isRegularFile)
                .filter(path -> deveProcessarArquivo(path.getFileName().toString(), extensoesDescartadas))
                .map(path -> (Callable<Void>) () -> {
                    processarArquivo(path, documentos, documentosEncontrados, documentosNaoEncontrados, arquivosPesquisados);
                    return null;
                })
                .toList();

            executor.invokeAll(tarefas);
            
            long duracao = System.currentTimeMillis() - inicio;
            String tempoExecucao = formatarDuracao(duracao);

            log.info("✅ Pesquisa finalizada — diretório: '{}', arquivos: {}, tempo: {}",
                    diretorioAtual, arquivosPesquisados[0], tempoExecucao);

            return new SearchResultDTO(
                arquivosPesquisados[0],
                documentosEncontrados,
                new ArrayList<>(documentosNaoEncontrados),
                tempoExecucao
            );

        } catch (Exception e) {
            log.error("❌ Erro durante a execução da pesquisa: {}", e.getMessage(), e);
            throw new SearchException("Erro ao processar a pesquisa de documentos", e);
        }
    }

    private boolean deveProcessarArquivo(String nomeArquivo, List<String> extensoesDescartadas) {
        return extensoesDescartadas.stream().noneMatch(nomeArquivo::endsWith);
    }

    private void processarArquivo(Path path, List<String> documentos,
                                  List<DocumentMatchDTO> documentosEncontrados,
                                  Set<String> documentosNaoEncontrados,
                                  int[] arquivosPesquisados) {

        String nomeArquivo = path.getFileName().toString();

        try {
            String encoding = EncodingDetector.detectarEncoding(path);
            Set<String> encontrados = new HashSet<>();

            try (Stream<String> linhas = Files.lines(path, Charset.forName(encoding))) {
                linhas.forEach(line -> {
                    for (String doc : documentos) {
                        if (line.contains(doc)) {
                            encontrados.add(doc);
                        }
                    }
                });
            }

            for (String doc : encontrados) {
                documentosEncontrados.add(new DocumentMatchDTO(doc, nomeArquivo));
                documentosNaoEncontrados.remove(doc);
            }

            synchronized (arquivosPesquisados) {
                arquivosPesquisados[0]++;
            }

            if (!encontrados.isEmpty()) {
                log.debug("📄 Documento(s) encontrados em '{}': {}", nomeArquivo, encontrados);
            }

        } catch (IOException e) {
            log.warn("⚠️ Falha ao ler arquivo '{}': {}", nomeArquivo, e.getMessage());
        } catch (Exception e) {
            log.error("💥 Erro inesperado em '{}': {}", nomeArquivo, e.getMessage(), e);
        }
    }

    private List<String> getExtensoesDescartadas(boolean validarFpl) {
        if (validarFpl) {
            return EXTENSOES_PADRAO_DESCARTADAS.stream()
                    .filter(ext -> !ext.equals(".fpl"))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>(EXTENSOES_PADRAO_DESCARTADAS);
    }

    private String formatarDuracao(long duracaoMillis) {
        long minutos = duracaoMillis / 60000;
        long segundos = (duracaoMillis / 1000) % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }
}
