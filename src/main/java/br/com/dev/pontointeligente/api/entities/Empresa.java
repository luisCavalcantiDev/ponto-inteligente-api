package br.com.dev.pontointeligente.api.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author LuisGustavo
 */
@Entity
@Table(name = "empresa")
public class Empresa implements Serializable {

    private static final long serialVersionUID = -3666539947301579619L;

    private Long _id;
    private String _razaoSocial;
    private String _cnpj;
    private Date _dataCriacao;
    private Date _dataAtualizacao;
    private List<Funcionario> _funcionarios;

    public Empresa() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
	return _id;
    }

    public void setId(Long id) {
	this._id = id;
    }

    @Column(name = "razao_social", nullable = false)
    public String getRazaoSocial() {
	return _razaoSocial;
    }

    public void setRazaoSocial(String _razaoSocial) {
	this._razaoSocial = _razaoSocial;
    }

    @Column(name = "cnpj", nullable = true)
    public String getCnpj() {
	return _cnpj;
    }

    public void setCnpj(String _cnpj) {
	this._cnpj = _cnpj;
    }

    @Column(name = "data_criacao", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getDataCriacao() {
	return _dataCriacao;
    }

    public void setDataCriacao(Date _dataCriacao) {
	this._dataCriacao = _dataCriacao;
    }

    @Column(name = "data_atualizacao", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getDataAtualizacao() {
	return _dataAtualizacao;
    }

    public void setDataAtualizacao(Date _dataAtualizacao) {
	this._dataAtualizacao = _dataAtualizacao;
    }

    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Funcionario> getFuncionarios() {
	return _funcionarios;
    }

    public void setFuncionarios(List<Funcionario> _funcionarios) {
	this._funcionarios = _funcionarios;
    }

    @PreUpdate
    public void preUpdate() {
	this._dataAtualizacao = new Date();
    }

    @PrePersist
    public void prePersist() {
	final Date _atual = new Date();
	this._dataCriacao = _atual;
	this._dataAtualizacao = _atual;
    }

    @Override
    public String toString() {
	return "Empresa [id=" + this._id
		+ ", razaoSocial=" + this._razaoSocial
		+ ", cnpj=" + this._cnpj
		+ ", dataCriacao=" + this._dataCriacao
		+ ", dataAtualizacao=" + this._dataAtualizacao + "]";
    }

}
