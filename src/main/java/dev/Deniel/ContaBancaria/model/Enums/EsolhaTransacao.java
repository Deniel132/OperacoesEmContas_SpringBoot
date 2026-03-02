package dev.Deniel.ContaBancaria.model.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EsolhaTransacao {
		SAQUE(1),
		DEPOSITO(2),
		TRANSFERENCIA(3),
		INVALIDA(4);

		private final int valor;

		EsolhaTransacao(int escolha){this.valor = escolha;}

		@JsonCreator
		public static EsolhaTransacao deint(int opcoes){

			for (EsolhaTransacao escolhas : values()){
				if (escolhas.valor == opcoes){
					return escolhas;
				}
			}
			return INVALIDA;
		}






}
