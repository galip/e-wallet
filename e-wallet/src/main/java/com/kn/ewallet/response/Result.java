package com.kn.ewallet.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
/**
 * @author galip
 * 
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Result implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	private String message;
	
}