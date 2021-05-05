package br.com.boavista.tubosp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EntityErrorsJson {
	public Integer code_error;
	
	public String code_message;
	
}
