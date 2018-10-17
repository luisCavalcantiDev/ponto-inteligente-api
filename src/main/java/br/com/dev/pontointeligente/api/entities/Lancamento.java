package br.com.dev.pontointeligente.api.entities;

import br.com.dev.pontointeligente.api.enums.TipoEnum;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author LuisGustavo
 */
@Entity
@Table(name = "lancamento")
@SuppressWarnings("PersistenceUnitPresent")
public class Lancamento implements Serializable {

    private Long _id;
    private Date _dataLcto;
    private String _descricao;
    private String _localizacao;
    private Date _dataCriacao;
    private Date _dataAtualizacao;
    private TipoEnum _tipo;
    private Funcionario _funcionario;

    private static final long serialVersionUID = 5750234323428493451L;

    public Lancamento() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
	return _id;
    }

    public void setId(Long _id) {
	this._id = _id;
    }

    @Column(name = "dataLcto", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getDataLcto() {
	return _dataLcto;
    }

    public void setDataLcto(Date _dataLcto) {
	this._dataLcto = _dataLcto;
    }

    @Column(name = "descricao", nullable = false)
    public String getDescricao() {
	return _descricao;
    }

    public void setDescricao(String _descricao) {
	this._descricao = _descricao;
    }

    @Column(name = "localizacao", nullable = false)
    public String getLocalizacao() {
	return _localizacao;
    }

    public void setLocalizacao(String _localizacao) {
	this._localizacao = _localizacao;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    public TipoEnum getTipo() {
	return _tipo;
    }

    public void setTipo(TipoEnum _tipo) {
	this._tipo = _tipo;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Funcionario getFuncionario() {
	return _funcionario;
    }

    public void setFuncionario(Funcionario _funcionario) {
	this._funcionario = _funcionario;
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
	return "Lancamento [id=" + this._id
		+ ", data=" + this._dataLcto
		+ ", descricao=" + this._descricao
		+ ", localizacao=" + this._localizacao
		+ ", dataCriacao=" + this._dataCriacao
		+ ", dataAtualizacao=" + this._dataAtualizacao
		+ ", tipo=" + this._tipo
		+ ", funcionario=" + this._funcionario + "]";
    }

}
