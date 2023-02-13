package Projeto;


import static java.math.BigDecimal.ROUND_UP;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;

//CLASSE PESSOA
class Pessoa {
	String nome;
	LocalDate dataNascimento;

	public Pessoa(String nome, LocalDate dataNascimento) {
		this.nome = nome;
		this.dataNascimento = dataNascimento;
	}

	public int getIdade() {
		return LocalDate.now().getYear() - dataNascimento.getYear();
	}

}

// CLASSE FUNCIONARIO ESTENDENDO CLASSE PESSOA
class Funcionario extends Pessoa {
	BigDecimal salario;
	String funcao;

	public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
		super(nome, dataNascimento);
		this.salario = salario;
		this.funcao = funcao;
	}

	public Object getFuncao() {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getSalario() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setSalario(int i) {
		// TODO Auto-generated method stub
	}

}

// CLASSE PRINCIPAL DO PROJETO
public class Principal {
	public static void main(String[] args) {
		List<Funcionario> funcionarios = new ArrayList<>();
		// LISTA DE FUNCIONARIOS
		funcionarios.add(new Funcionario("Maria",  LocalDate.of(2000, 10, 18),new BigDecimal(2009.44), "Operador"));
		funcionarios.add(new Funcionario("João",   LocalDate.of(1990, 5, 12), new BigDecimal(2284.38), "Operador"));
		funcionarios.add(new Funcionario("Caio",   LocalDate.of(1961, 5, 2),  new BigDecimal(9836.14), "Coordenador"));
		funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14),new BigDecimal(19119.88), "Diretor"));
		funcionarios.add(new Funcionario("Alice",  LocalDate.of(1995, 1, 5),  new BigDecimal(2234.68), "Recepcionista"));
		funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19),new BigDecimal(1582.72), "Operador"));
		funcionarios.add(new Funcionario("Artur",  LocalDate.of(1993, 3, 31), new BigDecimal(4071.84), "Contador"));
		funcionarios.add(new Funcionario("Laura",  LocalDate.of(1994, 7, 8),  new BigDecimal(3017.45), "Gerente"));
		funcionarios.add(new Funcionario("Heloisa",LocalDate.of(2003, 5, 24), new BigDecimal(1606.85), "Eletricista"));
		funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2),  new BigDecimal(2799.93), "Gerente"));

		// REMOVER O FUNCIONARIO 'JOÃO' DA LISTA
		funcionarios.removeIf(funcionario -> funcionario.nome.equals("João"));

		// AUMENTO DE 10% NO SALÁRIO DOS FUNCIONARIOS
		for (Funcionario funcionario : funcionarios) {
			BigDecimal aumento = funcionario.salario.multiply(new BigDecimal("0.10"));
			funcionario.salario = funcionario.salario.add(aumento);
		}

		// fORMATO DO SALARIO EM DECIMAIS E FORMATAR A DATA NA EXIBIÇÃO
		NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		// IMPRIMIR TODOS OS FUNCIONARIOS CONTENDO TODAS A INFORMAÇÕES
		funcionarios.forEach(funcionario -> {
			System.out.println("Nome: " + funcionario.nome);
			System.out.println("Data de Nascimento: " + funcionario.dataNascimento.format(dateFormat));
			System.out.println("Salário: " + nf.format(funcionario.salario));
			System.out.println("Função: " + funcionario.funcao);
			System.out.println();
		});

		// AGRUPAR OS FUNCINARIOS POR FUNÇÕES.
		Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();
		for (Funcionario f : funcionarios) {
			if (!funcionariosPorFuncao.containsKey(f.getFuncao())) {
				funcionariosPorFuncao.put((String) f.getFuncao(), new ArrayList<>());
			}
			funcionariosPorFuncao.get(f.getFuncao()).add(f);
		}

		// IMPRIMIR FUNCIONARIOS AGRUPADO POR FUNÇÃO
		System.out.println("Funcionários agrupados por função:");
		Map<String, List<Funcionario>> funcionariosPorFuncao1 = funcionarios.stream()
				.collect(Collectors.groupingBy(f -> f.funcao));
		funcionariosPorFuncao1.forEach((funcao, funcionariosDaFuncao) -> {
			System.out.println("  Função: " + funcao);
			funcionariosDaFuncao.forEach(f -> System.out.println("    " + f.nome));
		});
		System.out.println();

		// IMPRIMIR FUNCIONARIOS QUE FAZEM ANIVERSÁRIO NO MÊS 10 E 12.
		System.out.println("Funcionários que fazem aniversário em outubro e dezembro:");
		funcionarios.stream()
				.filter(f -> f.dataNascimento.getMonthValue() == 10 || f.dataNascimento.getMonthValue() == 12)
				.forEach(f -> System.out.println("  " + f.nome));
		System.out.println();

		// IMPRIMIR FUNCIONARIO COM MAIOR IDADES EXIBINDO NOME E IDADE.
		Funcionario funcionarioComMaiorIdade = funcionarios.stream().max(Comparator.comparingInt(f -> f.getIdade()))
				.get();
		System.out.println("Funcionário com a maior idade: " + funcionarioComMaiorIdade.nome + ", idade: "
				+ funcionarioComMaiorIdade.getIdade());
		System.out.println();

		// IMPRIMIR LISTA D FUNCIONARIOS EM ORDEM ALFABÉTICA.
		System.out.println("Lista de funcionários por ordem alfabética:");
		funcionarios.stream().sorted(Comparator.comparing(f -> f.nome)).forEach(f -> System.out.println("  " + f.nome));
		System.out.println();

		// IMPRIMIR O VALOR TOTAL DA SOMA DE TODOS OS SALARIOS DOS FUNCIONARIOS.
		BigDecimal totalSalarios = funcionarios.stream().map(f -> f.salario).reduce(BigDecimal.ZERO, BigDecimal::add);
		System.out.println("Total dos salários: " + nf.format(totalSalarios));
		System.out.println();

		// IMPRIMIR QUANTOS SLÁRIOS MINIMO GANHA CADA FUNCIONARIO.
		BigDecimal salarioMinimo = new BigDecimal("1212.00");
		System.out.println("Quantidade de salários mínimos por funcionário:");
		funcionarios
				.forEach(f -> System.out.println("  " + f.nome + ": " + f.salario.divide(salarioMinimo, 2, ROUND_UP)));
	}

	// private static Object getIdade() {
	// TODO Auto-generated method stub
	// return null;
	// }
}
