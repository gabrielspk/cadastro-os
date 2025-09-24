package com.github.gabrielspk.cadastro_os.exceptions;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) {
	
}
