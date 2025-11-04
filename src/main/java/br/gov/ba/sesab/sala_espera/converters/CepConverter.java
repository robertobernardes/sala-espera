package br.gov.ba.sesab.sala_espera.converters;

import org.springframework.stereotype.Component;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;

@Component("cepConverter")
public class CepConverter implements Converter<String> {

    @Override
    public String getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }        
        return value.replaceAll("[^0-9]", ""); 
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }        
        String cep = value.replaceAll("[^0-9]", "");
        // Aplica a formatação de CEP (XX.XXX-XXX)
        if (cep.length() == 8) {
            StringBuilder sb = new StringBuilder(cep);
            sb.insert(2, '.'); // Insere o ponto após o segundo dígito (XX.)
            sb.insert(6, '-'); // Insere o hífen após o sexto dígito (XX.XXX-)
            return sb.toString();
        }        
        return cep;
    }
}