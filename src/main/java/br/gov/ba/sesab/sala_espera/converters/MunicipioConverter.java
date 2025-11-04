package br.gov.ba.sesab.sala_espera.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.ba.sesab.sala_espera.domains.Municipio;
import br.gov.ba.sesab.sala_espera.services.MunicipioService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@Component("municipioConverter")
@FacesConverter(value = "municipioConverter")
public class MunicipioConverter implements Converter<Municipio> {

    @Autowired
    private MunicipioService municipioService;

    @Override
    public Municipio getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            Integer id = Integer.parseInt(value);
            return municipioService.buscarPorId(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Municipio municipio) {
        if (municipio == null || municipio.getId() == null) {
            return null;
        }
        return municipio.getId().toString();
    }
}