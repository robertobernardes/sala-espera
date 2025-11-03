package br.gov.ba.sesab.sala_espera.converters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

//Registra o conversor para todos os campos LocalDateTime
@FacesConverter(forClass = LocalDateTime.class)
public class LocalDateTimeConverter implements Converter<LocalDateTime> {
	
	private static final DateTimeFormatter FORMATTER = 
	        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	    @Override
	    public LocalDateTime getAsObject(FacesContext context, UIComponent component, String value) {
	        if (value == null || value.trim().isEmpty()) {
	            return null;
	        }
	        try {
	            // Converte a string do front-end para LocalDateTime
	            return LocalDateTime.parse(value, FORMATTER);
	        } catch (Exception e) {
	            // Se falhar na conversão, retorne null e o JSF tratará o erro de validação
	            return null;
	        }
	    }

	    @Override
	    public String getAsString(FacesContext context, UIComponent component, LocalDateTime value) {
	        if (value == null) {
	            return null;
	        }
	        // Converte o LocalDateTime do Java para a string exibida na tela
	        return value.format(FORMATTER);
	    }

}
