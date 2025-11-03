package br.gov.ba.sesab.sala_espera.converters;

import org.springframework.stereotype.Component;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;

@Component("cepConverter")
public class CepConverter implements Converter<String> {

    /**
     * getAsObject: Pega o valor formatado (ex: 80.010-000) e retorna o valor limpo (ex: 80010000).
     * Não precisa de alteração, pois o replaceAll("[^0-9]", "") já remove pontos e hífens.
     */
    @Override
    public String getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        
        // Remove todos os caracteres que não são dígitos (0-9)
        return value.replaceAll("[^0-9]", ""); 
    }

    /**
     * getAsString: Pega o valor limpo (ex: 80010000) e retorna o valor formatado (ex: 80.010-000).
     */
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

        // Se o CEP tiver outro tamanho, retorna sem formatação
        return cep;
    }
}