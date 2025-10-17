package com.github.gabrielspk.cadastro_os.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.tika.parser.txt.CharsetDetector;
import org.apache.tika.parser.txt.CharsetMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncodingDetector {
    private static final Logger log = LoggerFactory.getLogger(EncodingDetector.class);

    public static String detectarEncoding(Path path) {
        try (InputStream is = new BufferedInputStream(Files.newInputStream(path))) {
            CharsetDetector detector = new CharsetDetector();
            detector.setText(is);
            CharsetMatch match = detector.detect();

            String encoding = (match != null) ? match.getName() : "UTF-8";
            log.debug("📄 Encoding detectado para '{}': {}", path.getFileName(), encoding);
            return encoding;
        } catch (IOException e) {
            log.warn("⚠️ Falha ao detectar encoding para '{}': {}. Usando UTF-8 por padrão.",
                    path.getFileName(), e.getMessage());
            return "UTF-8";
        }
    }
}
