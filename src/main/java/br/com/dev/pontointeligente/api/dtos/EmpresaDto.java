package br.com.dev.pontointeligente.api.dtos;

/**
 *
 * @author LuisGustavo
 */
public class EmpresaDto {

    private Long _id;
    private String _razaoSocial;
    private String _cnpj;

    public EmpresaDto() {
    }

    public Long getId() {
	return _id;
    }

    public void setId(Long _id) {
	this._id = _id;
    }

    public String getRazaoSocial() {
	return _razaoSocial;
    }

    public void setRazaoSocial(String _razaoSocial) {
	this._razaoSocial = _razaoSocial;
    }

    public String getCnpj() {
	return _cnpj;
    }

    public void setCnpj(String _cnpj) {
	this._cnpj = _cnpj;
    }

    @Override
    public String toString() {
	return "EmpresaDto [id=" + this._id + ", razaSocial=" + this._razaoSocial + ", cnpj=" + this._cnpj + "]";
    }

}
