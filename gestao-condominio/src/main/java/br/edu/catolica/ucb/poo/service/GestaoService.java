package br.edu.catolica.ucb.poo.service;
import br.edu.catolica.ucb.poo.infra.Banco;
import br.edu.catolica.ucb.poo.model.*;
import br.edu.catolica.ucb.poo.repository.CondominioRepository;
import java.util.List;
public class GestaoService {
 private final Banco banco;
 public GestaoService(Banco banco){this.banco=banco;}
 public Condominio consultarCondominio(){return banco.consultar(em->new CondominioRepository(em).buscar(Condominio.class,1));}
 public List<Apartamento> listarApartamentos(){return banco.consultar(em->new CondominioRepository(em).listar(Apartamento.class));}
 public List<Morador> listarMoradores(){return banco.consultar(em->new CondominioRepository(em).listar(Morador.class));}
 public List<Funcionario> listarFuncionarios(){return banco.consultar(em->new CondominioRepository(em).listar(Funcionario.class));}
 public Morador consultarMorador(long id){return banco.consultar(em->new CondominioRepository(em).buscar(Morador.class,id));}
 public Apartamento cadastrarApartamento(String bloco,String numero,String vaga){
  return banco.transacionar(em->{var r=new CondominioRepository(em);var a=new Apartamento(bloco,numero,vaga);
   if(r.existeApartamento(a.getBloco(),a.getNumero()))throw new IllegalArgumentException("Apartamento já cadastrado nesse bloco.");
   if(a.getVaga()!=null && r.existeVaga(a.getVaga()))throw new IllegalArgumentException("Vaga já vinculada a outro apartamento.");
   r.salvar(a);return a;});
 }
 public Morador cadastrarMorador(String nome,String cpf,String telefone,long apartamentoId){
  return banco.transacionar(em->{var r=new CondominioRepository(em);
   var m=new Morador(nome,cpf,telefone,r.buscar(Apartamento.class,apartamentoId));
   if(r.existeCpf(cpf))throw new IllegalArgumentException("CPF já cadastrado.");r.salvar(m);return m;});
 }
 public Funcionario cadastrarFuncionario(String nome,String cargo){return banco.transacionar(em->{var f=new Funcionario(nome,cargo);em.persist(f);return f;});}
 public Encomenda receberEncomenda(long moradorId,String descricao){return banco.transacionar(em->{var r=new CondominioRepository(em);var e=new Encomenda(r.buscar(Morador.class,moradorId),descricao);r.salvar(e);return e;});}
 public void retirarEncomenda(long id){banco.transacionar(em->{var e=em.find(Encomenda.class,id,jakarta.persistence.LockModeType.PESSIMISTIC_WRITE);if(e==null)throw new IllegalArgumentException("Encomenda não encontrada.");e.retirar();return null;});}
 public List<Encomenda> listarPendentes(){return banco.consultar(em->new CondominioRepository(em).encomendas(null,true));}
 public List<Encomenda> encomendasPendentes(long moradorId){return banco.consultar(em->{var r=new CondominioRepository(em);r.buscar(Morador.class,moradorId);return r.encomendas(moradorId,true);});}
 public List<Encomenda> historicoEncomendas(long moradorId){return banco.consultar(em->{var r=new CondominioRepository(em);r.buscar(Morador.class,moradorId);return r.encomendas(moradorId,false);});}
}
