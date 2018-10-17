package br.com.dev.pontointeligente.api.dtos;

import java.util.Optional;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author LuisGustavo
 */
public class LancamentoDto {

    private Optional<Long> _id = Optional.empty();
    private String _data;
    private String _tipo;
    private String _descricao;
    private String _localizacao;
    private Long _funcionarioId;

    public LancamentoDto() {
    }

    public Optional<Long> getId() {
	return _id;
    }

    public void setId(Optional<Long> id) {
	this._id = id;
    }

    @NotEmpty(message = "Data não pode ser vazia.")
    public String getData() {
	return _data;
    }

    public void setData(String data) {
	this._data = data;
    }

    public String getTipo() {
	return _tipo;
    }

    public void setTipo(String tipo) {
	this._tipo = tipo;
    }

    public String getDescricao() {
	return _descricao;
    }

    public void setDescricao(String descricao) {
	this._descricao = descricao;
    }

    public String getLocalizacao() {
	return _localizacao;
    }

    public void setLocalizacao(String localizacao) {
	this._localizacao = localizacao;
    }

    public Long getFuncionarioId() {
	return _funcionarioId;
    }

    public void setFuncionarioId(Long funcionarioId) {
	this._funcionarioId = funcionarioId;
    }

    @Override
    public String toString() {
	return "LancamentoDto [id=" + this._id + ", data=" + this._data + ", tipo=" + this._tipo + ", descricao=" + this._descricao
		+ ", localizacao=" + this._localizacao + ", funcionarioId=" + this._funcionarioId + "]";
    }

}
