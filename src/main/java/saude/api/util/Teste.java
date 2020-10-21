package saude.api.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Teste {


	
	
	public static void main(String[] args) {
		List<String> diasDaSemana = new ArrayList<>();
		//diasDaSemana.add("WEDNESDAY");
		diasDaSemana.add("THURSDAY");
		diasDaSemana.add("MONDAY");
		LocalDate dataAtual = LocalDate.now();
		System.out.println("DIA DA SEMANA HOJE = "+dataAtual.getDayOfWeek());

		//String diase = ;
		
		if(diasDaSemana.contains(dataAtual.getDayOfWeek().toString())) {
			System.out.println("Contem o dia da semana");
		} else {
			System.out.println("Não contem o dia da semana");
		}
		
		

	}//fecha metodo iniciar

}//fecha classe
