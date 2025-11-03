package br.gov.ba.sesab.sala_espera.domains.Enums;


public enum StatusEnum {
	
	CADASTRADA(1, "Cadastrada"),
    CANCELADA(2, "Cancelada"),
    ARQUIVADA(3, "Arquivada");

    private final Integer codigo;
    private final String descricao;

    private StatusEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public static StatusEnum valueOf(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        for (StatusEnum status : StatusEnum.values()) {
            if (codigo.equals(status.getCodigo())) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código de Status inválido: " + codigo);
    }
}
