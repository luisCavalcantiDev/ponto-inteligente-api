package br.com.dev.pontointeligente.api.entities;

import br.com.dev.pontointeligente.api.enums.PerfilEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author LuisGustavo
 */
@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable {

    private static final long serialVersionUID = -2632296716387350406L;

    private Long _id;
    private String _nome;
    private String _email;
    private String _senha;
    private String _cpf;
    private BigDecimal _valorHora;
    private Float _qtdHorasTrabalhoDia;
    private Float _qtdHorasAlmoco;
    private PerfilEnum _perfil;
    private Date _dataCriacao;
    private Date _dataAtualizacao;
    private Empresa _empresa;
    private List<Lancamento> _lancamentos;

    public Funcionario() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
	return _id;
    }

    public void setId(Long id) {
	this._id = id;
    }

    @Column(name = "nome", nullable = false)
    public String getNome() {
	return _nome;
    }

    public void setNome(String _nome) {
	this._nome = _nome;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
	return _email;
    }

    public void setEmail(String _email) {
	this._email = _email;
    }

    @Column(name = "senha", nullable = false)
    public String getSenha() {
	return _senha;
    }

    public void setSenha(String _senha) {
	this._senha = _senha;
    }

    @Column(name = "cpf", nullable = false)
    public String getCpf() {
	return _cpf;
    }

    public void setCpf(String _cpf) {
	this._cpf = _cpf;
    }

    @Column(name = "valor_hora", nullable = false)
    public BigDecimal getValorHora() {
	return _valorHora;
    }

    public void setValorHora(BigDecimal _valorHora) {
	this._valorHora = _valorHora;
    }

    @Column(name = "qtd_horas_trabalho_dia", nullable = false)
    public Float getQtdHorasTrabalhoDia() {
	return _qtdHorasTrabalhoDia;
    }

    public void setQtdHorasTrabalhoDia(Float _qtdHorasTrabalhoDia) {
	this._qtdHorasTrabalhoDia = _qtdHorasTrabalhoDia;
    }

    @Column(name = "qtd_horas_almoco", nullable = false)
    public Float getQtdHorasAlmoco() {
	return _qtdHorasAlmoco;
    }

    public void setQtdHorasAlmoco(Float _qtdHorasAlmoco) {
	this._qtdHorasAlmoco = _qtdHorasAlmoco;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false)
    public PerfilEnum getPerfil() {
	return _perfil;
    }

    public void setPerfil(PerfilEnum _perfil) {
	this._perfil = _perfil;
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

    @ManyToOne(fetch = FetchType.EAGER)
    public Empresa getEmpresa() {
	return _empresa;
    }

    public void setEmpresa(Empresa _empresa) {
	this._empresa = _empresa;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Lancamento> getLancamentos() {
	return _lancamentos;
    }

    public void setLancamentos(List<Lancamento> _lancamentos) {
	this._lancamentos = _lancamentos;
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

    @Transient
    public Optional<BigDecimal> getValorHoraOpt() {
	return Optional.ofNullable(this._valorHora);
    }

    @Transient
    public Optional<Float> getQtdHorasTrabalhoDiaOpt() {
	return Optional.ofNullable(this._qtdHorasTrabalhoDia);
    }

    @Transient
    public Optional<Float> getQtdHorasAlmocoOpt() {
	return Optional.ofNullable(this._qtdHorasAlmoco);
    }

    @Override
    public String toString() {
	return "Funcionario [id=" + this._id
		+ ", nome=" + this._nome
		+ ", email=" + this._email
		+ ", senha=" + this._senha
		+ ", cpf=" + this._cpf
		+ ", valorHora=" + this._valorHora
		+ ", qtdHorasTrabalhoDia=" + this._qtdHorasTrabalhoDia
		+ ", qtdHorasAlmoco=" + this._qtdHorasAlmoco
		+ ", perfil=" + this._perfil
		+ ", dataCriacao=" + this._dataCriacao
		+ ", dataAtualizacao=" + this._dataAtualizacao
		+ ", empresa=" + this._empresa + "]";
    }
}
