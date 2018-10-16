package br.com.dev.pontointeligente.api.dtos;

import com.google.gson.annotations.SerializedName;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

/**
 *
 * @author LuisGustavo
 */
public class PessoaJuridicaDto {

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

    @SerializedName("razaoSocial")
    private String _razaoSocial;

    @SerializedName("cnpj")
    private String _cnpj;

    public PessoaJuridicaDto() {
    }

    public Long getId() {
	return _id;
    }

    public void setId(Long _id) {
	this._id = _id;
    }

    @NotEmpty(message = "Nome não pode ser vazio")
    @Length(min = 3, max = 200, message = "Nome deve conter entre 5 e 200 caracteres")
    public String getNome() {
	return _nome;
    }

    public void setNome(String _nome) {
	this._nome = _nome;
    }

    @NotEmpty(message = "Email não pode ser vazio")
    @Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres")
    @Email(message = "Email inválido")
    public String getEmail() {
	return _email;
    }

    public void setEmail(String _email) {
	this._email = _email;
    }

    @NotEmpty(message = "Senha não pode ser vazia")
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

    @NotEmpty(message = "Razão Social não pode ser vazio")
    @Length(min = 5, max = 200, message = "Razão Social deve conter entre 5 e 200 caracteres")
    public String getRazaoSocial() {
	return _razaoSocial;
    }

    public void setRazaoSocial(String _razaoSocial) {
	this._razaoSocial = _razaoSocial;
    }

    @NotEmpty(message = "CNPJ não pode ser vazio")
    @CNPJ(message = "CNPJ inválido")
    public String getCnpj() {
	return _cnpj;
    }

    public void setCnpj(String _cnpj) {
	this._cnpj = _cnpj;
    }

    @Override
    public String toString() {
	return "PessoaJuridicaDto [id=" + this._id + ", nome=" + this._nome
		+ ", email=" + this._email + ", senha=" + this._senha + ", cpf=" + this._cpf
		+ ", razaoSocial=" + this._razaoSocial + ", cnpj=" + this._cnpj + "]";
    }

}
