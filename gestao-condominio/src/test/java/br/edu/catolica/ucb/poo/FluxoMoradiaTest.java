package br.edu.catolica.ucb.poo;
import br.edu.catolica.ucb.poo.infra.*;
import br.edu.catolica.ucb.poo.service.GestaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.util.Scanner;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;
class FluxoMoradiaTest {
 @TempDir Path pasta;
 @Test void cadastroEncomendaERetiradaPersistem() {
  String url="jdbc:h2:"+pasta.resolve("moradia"); long id;
  try(Banco b=Banco.abrirH2(url)) {
   DadosIniciais.inicializar(b); var s=new GestaoService(b);
   var a=s.cadastrarApartamento("B","102","G02");
   var m=s.cadastrarMorador("Ana","22222222222","61999998888",a.getId()); id=m.getId();
   var e=s.receberEncomenda(id,"Caixa de livros");
   assertEquals(1,s.encomendasPendentes(id).size());
   assertEquals("G02",s.consultarMorador(id).getApartamento().getVaga());
   s.retirarEncomenda(e.getId());
   assertTrue(s.encomendasPendentes(id).isEmpty());
   assertThrows(IllegalArgumentException.class,()->s.retirarEncomenda(e.getId()));
  }
  try(Banco b=Banco.abrirH2(url)) {
   DadosIniciais.inicializar(b); var s=new GestaoService(b);
   assertEquals("Ana",s.consultarMorador(id).getNome());
   assertEquals(2,s.listarMoradores().size());
   assertEquals(1,s.historicoEncomendas(id).size());
   assertNotNull(s.historicoEncomendas(id).get(0).getRetiradaEm());
  }
 }
 @Test void validaDuplicidadesEVinculosSemGravarParcialmente() {
  try(Banco b=Banco.abrirH2("jdbc:h2:mem:validacao")) {
   DadosIniciais.inicializar(b);var s=new GestaoService(b);
   assertThrows(IllegalArgumentException.class,()->s.cadastrarApartamento("A","101",""));
   assertThrows(IllegalArgumentException.class,()->s.cadastrarApartamento("B","101","G01"));
   assertThrows(IllegalArgumentException.class,()->s.cadastrarMorador("Outro","11111111111","61999999999",1));
   assertThrows(IllegalArgumentException.class,()->s.cadastrarMorador("Ana","22222222222","61999999999",999));
   assertThrows(IllegalArgumentException.class,()->s.receberEncomenda(999,"Caixa"));
   assertThrows(IllegalArgumentException.class,()->s.receberEncomenda(1," "));
   assertEquals(1,s.listarMoradores().size());assertTrue(s.listarPendentes().isEmpty());
  }
 }
 @Test void apartamentosPermitemMesmoNumeroEmBlocosDiferentesESemVaga() {
  try(Banco b=Banco.abrirH2("jdbc:h2:mem:blocos")) {
   DadosIniciais.inicializar(b);var s=new GestaoService(b);
   assertNull(s.cadastrarApartamento("B","101","").getVaga());
   assertNull(s.cadastrarApartamento("C","101","").getVaga());
   assertEquals("Zelador",s.cadastrarFuncionario("Marcos","Zelador").getCargo());
  }
 }
 @Test void menuCadastraMoradorEMostraFicha() {
  try(Banco b=Banco.abrirH2("jdbc:h2:mem:menuNovo")) {
   DadosIniciais.inicializar(b);var s=new GestaoService(b);var out=new ByteArrayOutputStream();
   var c=new br.edu.catolica.ucb.poo.view.Console(new Scanner("2\n1\nAna\n22222222222\n61999998888\n1\n3\n2\n0\n0\n"),new PrintStream(out));
   new br.edu.catolica.ucb.poo.view.MenuPrincipal(s,c).exibir();
   assertEquals(2,s.listarMoradores().size());
   assertTrue(out.toString().contains("Encomendas pendentes: 0"));
   assertTrue(out.toString().contains("G01"));
  }
 }
 @Test void fimDaEntradaCancelaCadastro() {
  try(Banco b=Banco.abrirH2("jdbc:h2:mem:eofNovo")) {
   DadosIniciais.inicializar(b);var s=new GestaoService(b);
   var c=new br.edu.catolica.ucb.poo.view.Console(new Scanner("2\n1\nAna\n"),System.out);
   new br.edu.catolica.ucb.poo.view.MenuPrincipal(s,c).exibir();
   assertEquals(1,s.listarMoradores().size());
  }
 }
}
