package br.com.dev.pontointeligente.api.dtos;

import com.google.gson.annotations.SerializedName;
import java.util.Optional;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

/**
 *
 * @author LuisGustavo
 */
public class PessoaFisicaDto {

    @SerializedName("id")
    private Long _id;

    @SerializedName("nome")
    private String _nome;

    @SerializedName("email")
    private String _email;

    @SerializedName("senha")
    private String _senha;

    @SerializedName("cpf")
    private String _cpf;

    @SerializedName("cnpj")
    private String _cnpj;

    @SerializedName("valorHora")
    @SuppressWarnings("FieldMayBeFinal")
    private Optional<String> _valorHora = Optional.empty();

    @SerializedName("qtdHorasTrabalhoDia")
    @SuppressWarnings("FieldMayBeFinal")
    private Optional<String> _qtdHorasTrabalhoDia = Optional.empty();

    @SerializedName("qtdHorasAlmoco")
    @SuppressWarnings("FieldMayBeFinal")
    private Optional<String> _qtdHorasAlmoco = Optional.empty();

    public PessoaFisicaDto() {
    }

    public Long getId() {
	return _id;
    }

    public void setId(Long _id) {
	this._id = _id;
    }

    @NotEmpty(message = "Nome não pode ser vazio")
    @Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres")
    public String getNome() {
	return _nome;
    }

    public void setNome(String _nome) {
	this._nome = _nome;
    }

    @NotEmpty(message = "Email não pode ser vazio")
    @Length(min = 5, max = 200, message = "Email deve conter entre 3 e 200 caracteres")
    public String getEmail() {
	return _email;
    }

    public void setEmail(String _email) {
	this._email = _email;
    }

    public String getSenha() {
	return _senha;
    }

    public void setSenha(String _senha) {
	this._senha = _senha;
    }

    @NotEmpty(message = "CPF não pode ser vazio")
    @CPF(message = "CPF inválido")
    public String getCpf() {
	return _cpf;
    }

    public void setCpf(String _cpf) {
	this._cpf = _cpf;
    }

    @NotEmpty(message = "CNPJ não pode ser vazio")
    @CNPJ(message = "CNPJ inválido")
    public String getCnpj() {
	return _cnpj;
    }

    public void setCnpj(String _cnpj) {
	this._cnpj = _cnpj;
    }

    public Optional<String> getValorHora() {
	return _valorHora;
    }

    public void setValorHora(Optional<String> _valorHora) {
	this._valorHora = _valorHora;
    }

    public Optional<String> getQtdHorasTrabalhoDia() {
	return _qtdHorasTrabalhoDia;
    }

    public void setQtdHorasTrabalhoDia(Optional<String> _qtdHorasTrabalhoDia) {
	this._qtdHorasTrabalhoDia = _qtdHorasTrabalhoDia;
    }

    public Optional<String> getQtdHorasAlmoco() {
	return _qtdHorasAlmoco;
    }

    public void setQtdHorasAlmoco(Optional<String> _qtdHorasAlmoco) {
	this._qtdHorasAlmoco = _qtdHorasAlmoco;
    }

    @Override
    public String toString() {
	return "FuncionarioDto [id=" + this._id + ", nome=" + this._nome
		+ ", email=" + this._email + ", senha=" + this._senha
		+ ", cpf=" + this._cpf + ", valorHora=" + this._valorHora
		+ ", qtdHorasTrabalhoDia=" + this._qtdHorasTrabalhoDia
		+ ", qtdHorasAlmoco=" + this._qtdHorasAlmoco + ", cnpj=" + this._cnpj + "]";
    }

}
