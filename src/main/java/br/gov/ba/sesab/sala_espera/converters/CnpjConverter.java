package br.gov.ba.sesab.sala_espera.converters;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "cnpjConverter")
public class CnpjConverter implements Converter<Object> {

    /**
     * Converte o valor do Back-end (String 'somente números') para o Front-end (String 'formatada').
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }

        String cnpj = value.toString();

        // Remove quaisquer caracteres que não sejam dígitos, caso já venha com algum lixo.
        cnpj = cnpj.replaceAll("[^0-9]", ""); 

        if (cnpj.length() != 14) {
             // Retorna o valor original se não tiver 14 dígitos
             return cnpj; 
        }

        // Aplica a máscara 99.999.999/9999-99
        return cnpj.substring(0, 2) + "."
                + cnpj.substring(2, 5) + "."
                + cnpj.substring(5, 8) + "/"
                + cnpj.substring(8, 12) + "-"
                + cnpj.substring(12, 14);
    }

    /**
     * Converte o valor do Front-end (String 'formatada') para o Back-end (String 'somente números').
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }        
        // Remove todos os caracteres não numéricos (pontos, traços, barras)
        String cnpjSomenteNumeros = value.replaceAll("[^0-9]", "");
        return cnpjSomenteNumeros;
    }
}
