package br.gov.ba.sesab.sala_espera.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.ba.sesab.sala_espera.domains.Estado;
import br.gov.ba.sesab.sala_espera.services.EstadoService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@Component("estadoConverter")
@FacesConverter(value = "estadoConverter")
public class EstadoConverter implements Converter<Estado> {

    @Autowired
    private EstadoService estadoService;

    @Override
    public Estado getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            Integer id = Integer.parseInt(value);
            return estadoService.buscarPorId(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Estado estado) {
        if (estado == null || estado.getId() == null) {
            return null;
        }
        return estado.getId().toString();
    }
}