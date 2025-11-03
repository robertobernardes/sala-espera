package br.gov.ba.sesab.sala_espera.converters;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import org.springframework.stereotype.Component;

@Component("cpfConverter")
@FacesConverter(value = "cpfConverter")
public class CpfConverter implements Converter<String> {

    private static final String NON_DIGIT_REGEX = "[^0-9]";

    @Override
    public String getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        if (submittedValue == null || submittedValue.trim().isEmpty()) {
            return null;
        }
        String cpfLimpo = submittedValue.replaceAll(NON_DIGIT_REGEX, "");        
        return cpfLimpo;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, String modelValue) {
        if (modelValue == null || modelValue.trim().isEmpty()) {
            return "";
        }
        String cpfLimpo = modelValue.replaceAll(NON_DIGIT_REGEX, "");
        if (cpfLimpo.length() == 11) {
            return cpfLimpo.substring(0, 3) + "." + 
                   cpfLimpo.substring(3, 6) + "." + 
                   cpfLimpo.substring(6, 9) + "-" + 
                   cpfLimpo.substring(9, 11);
        }
        return modelValue;
    }
}
