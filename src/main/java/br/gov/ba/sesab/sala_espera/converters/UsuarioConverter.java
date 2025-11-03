package br.gov.ba.sesab.sala_espera.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.ba.sesab.sala_espera.domains.Usuario;
import br.gov.ba.sesab.sala_espera.services.UsuarioService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@Component("usuarioConverter")
@FacesConverter(value = "usuarioConverter")
public class UsuarioConverter implements Converter<Usuario> {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public Usuario getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty() || value.equals("Selecione")) {
            return null;
        }

        try {
            Integer id = Integer.parseInt(value);
            return usuarioService.buscarPorId(id); 
            
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Usuario value) {
        if (value == null) {
            return "";
        }
        return value.getId() != null ? value.getId().toString() : "";
    }
}
