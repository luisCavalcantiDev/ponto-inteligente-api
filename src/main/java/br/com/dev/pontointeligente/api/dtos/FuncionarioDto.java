package br.com.dev.pontointeligente.api.dtos;

import java.util.Optional;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author LuisGustavo
 */
public class FuncionarioDto {

    private Long _id;
    private String _nome;
    private String _email;
    private Optional<String> _senha = Optional.empty();
    private Optional<String> _valorHora = Optional.empty();
    private Optional<String> _qtdHorasTrabalhoDia = Optional.empty();
    private Optional<String> _qtdHorasAlmoco = Optional.empty();

    public FuncionarioDto() {
    }

    public Long getId() {
	return _id;
    }

    public void setId(Long id) {
	this._id = id;
    }

    @NotEmpty(message = "Nome não pode ser vazio.")
    @Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
    public String getNome() {
	return _nome;
    }

    public void setNome(String nome) {
	this._nome = nome;
    }

    @NotEmpty(message = "Email não pode ser vazio.")
    @Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres.")
    @Email(message = "Email inválido.")
    public String getEmail() {
	return _email;
    }

    public void setEmail(String email) {
	this._email = email;
    }

    public Optional<String> getSenha() {
	return _senha;
    }

    public void setSenha(Optional<String> senha) {
	this._senha = senha;
    }

    public Optional<String> getValorHora() {
	return _valorHora;
    }

    public void setValorHora(Optional<String> valorHora) {
	this._valorHora = valorHora;
    }

    public Optional<String> getQtdHorasTrabalhoDia() {
	return _qtdHorasTrabalhoDia;
    }

    public void setQtdHorasTrabalhoDia(Optional<String> qtdHorasTrabalhoDia) {
	this._qtdHorasTrabalhoDia = qtdHorasTrabalhoDia;
    }

    public Optional<String> getQtdHorasAlmoco() {
	return _qtdHorasAlmoco;
    }

    public void setQtdHorasAlmoco(Optional<String> qtdHorasAlmoco) {
	this._qtdHorasAlmoco = qtdHorasAlmoco;
    }

    @Override
    public String toString() {
	return "FuncionarioDto [id=" + this._id + ", nome=" + this._nome + ", email=" + this._email
		+ ", senha=" + this._senha + ", valorHora="
		+ this._valorHora + ", qtdHorasTrabalhoDia=" + this._qtdHorasTrabalhoDia
		+ ", qtdHorasAlmoco=" + this._qtdHorasAlmoco
		+ "]";
    }

}
