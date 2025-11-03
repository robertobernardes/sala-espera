package br.gov.ba.sesab.sala_espera.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.ba.sesab.sala_espera.domains.Sala;
import br.gov.ba.sesab.sala_espera.services.SalaService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@Component("salaConverter")
@FacesConverter(value = "salaConverter")
public class SalaConverter implements Converter<Sala> {
	
    @Autowired
    private SalaService salaService;

    @Override
    public Sala getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty() || value.equals("Selecione")) {
            return null;
        }

        try {
            Integer id = Integer.parseInt(value);
            return salaService.buscarPorId(id); 
            
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Sala value) {
        if (value == null) {
            return "";
        }
        return value.getId() != null ? value.getId().toString() : "";
    }
}
