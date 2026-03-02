package dev.Deniel.ContaBancaria.model.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TiposDeConta {
	CORRENTE(1),
	POUPANCA(2),
	INVALIDA(3);

	private final int valor;

	TiposDeConta(int escolha){this.valor = escolha;}

	@JsonCreator
	public static TiposDeConta deint(int opcoes){

		for (TiposDeConta escolhas : values()){
			if (escolhas.valor == opcoes){
				return escolhas;
			}
		}
		return INVALIDA;
	}




}
