package com.utils.mylibs;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * <p> Classe que serve para cronometrar o tempo entre dois intervalos. 
 * Ele cronometra o tempo em horas, minutos e segundos.
 *    
 * <p> Cronometra tempo entre dois eventos.
 * <pre>
 * Cronometro cron = new Cronometro();  
 * cron.start("primeira contagem de tempo");    
 * metodoDemorado(); 
 * cron.stop();
 * cron.prettyPrint(); // Imprimi tabela com todos os intervalos
 * System.out.println(cron.getLastTimeInterval().getDurationFormatted()); => 00:00:10
 * </pre>
 * 
 * @author Rodrigo Eggea
 *
 */
public class Cronometro {
	
	/**
	 * <p> Classe que representa um Intervalo de Tempo.
	 * <P> Contém identificador (incrementado automático) a cada marcação, uma descrição, 
	 * data e hora de início, data e hora do fim da marcação, e duração. 
	 * 
	 * @author Rodrigo Eggea
	 */
	static class TimeInterval {
		private static int instanceCounter=0;
		private int idInterval;
		private String description = "";
		private LocalDateTime startTime;
		private LocalDateTime stopTime;
		private Duration duration;
		private long durationSeconds;
		private long durationMinutes;
		private long durationHours;
		private String durationFormatted;

		private TimeInterval() {
			this("");
		}

		private TimeInterval(String description) {
			this.description = description;
			instanceCounter++;
			idInterval = instanceCounter;
		}
		
		public int getIdInterval() {
			return idInterval;
		}

		public String getDescription() {
			return description;
		}

		public LocalDateTime getStartTime() {
			return startTime;
		}

		public LocalDateTime getStopTime() {
			return stopTime;
		}

		public Duration getDuration() {
			return duration;
		}

		public String getDurationFormatted() {
			return durationFormatted;
		}
		
		public long getDurationSeconds() {
			return durationSeconds;
		}

		public long getDurationMinutes() {
			return durationMinutes;
		}

		public long getDurationHours() {
			return durationHours;
		}
	}

	// Atributos da classe Cronometro
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	private TimeInterval timeInterval;
	private List<TimeInterval> timeIntervals = new ArrayList<>();
	private boolean isRunning=false;

	/**
	 * <p> Inicializa cronômetro sem uma descrição. 
	 * @throws Exception exceção se chamar start() com o cronômetro rodando.
	 */
	public void start() {
		start("");
	}
	
	/**
	 * <p> Inicializa cronômetro com uma descrição.
	 * @param description
	 * @throws Exception exceção se chamar start() com o cronômetro rodando.
	 */
	public void start(String description) {
		if (!isRunning) {
			isRunning=true;
			timeInterval = new TimeInterval(description);
			timeInterval.startTime = LocalDateTime.now();
		} else {
			throw new IllegalStateException("ERRO: Cronômetro não pode ser iniciado duas vezes.");
		}
	}

	/**
	 * Para a contagem de tempo.
	 */
	public void stop() {
		if(isRunning) {
			isRunning = false;
			timeInterval.stopTime = LocalDateTime.now();
			calculateInterval();
		}
	}
	
	private void calculateInterval() {
		// Armazena a duração
		Duration duration = Duration.between(timeInterval.startTime, timeInterval.stopTime);
		timeInterval.duration = duration;
		
		// Converte a duração em segundo, minutos e horas e armazena.
		long secondsDuration = duration.getSeconds();
		timeInterval.durationSeconds = duration.getSeconds();
		timeInterval.durationMinutes = (secondsDuration % 3600) / 60;
		timeInterval.durationHours =   (secondsDuration / 3600);
		
		// Armazena a duração no formato "00:00:00"
		timeInterval.durationFormatted = durationFormatted(duration);
		timeIntervals.add(timeInterval);
	}

	/**
	 * <p> Converte {@link Duration} em uma saída {@link String} no seguinte formato:
	 * <p> <code> horas:minutos:segundos </code></b>
	 * @param duration - duração do intervalo
	 * @return
	 */
	private static String durationFormatted(Duration duration) {
		long secondsDuration = duration.getSeconds();
		long seconds = (secondsDuration % 60);
		long minutes = (secondsDuration % 3600) / 60;
		long hours = (secondsDuration / 3600);
		String result = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		return result;
	}

	/**
	 * Retorna o intervalo do último start e stop
	 * @return
	 */
	public TimeInterval getLastTimeInterval() {
		return timeInterval;
	}

	/**
	 * Retorna uma lista com todos os intervalos salvos no cronômetro.
	 * @return
	 */
	public List<TimeInterval> getTimeIntervals() {
		return timeIntervals;
	}

	/**
	 * <p> Retorna um intervalo pelo Id.
	 * <p> Se não encontrar nenhum registro retorna <i> null </i>. 
	 * @param id - Identificação do Intervalo de Tempo
	 * @return intervalo de tempo com o identificador.
	 */
	public TimeInterval getTimeIntervalbyId(int id) {
		for(TimeInterval interval: timeIntervals) {
			if(interval.idInterval == id) {
				return interval;
			}
		}
		return null;
	}
	
	/**
	 * <p> Retorna um intervalo por parte do texto da descrição.
	 * <p> Ele dá match se parte do texto for igual.
	 * 
	 * @param description - parte do texto da descrição
	 * @return intervalo de tempo que contém parte da descrição
	 */
	public TimeInterval getTimeIntervalByDescription(String description) {
		for(TimeInterval interval: timeIntervals) {
			if(interval.getDescription().contains(description)) {
				return interval;
			}
		}
		return null;
	}
	
	/**
	 * Retorna o tempo total de todos os intervalos.
	 * @return tempo total dos intervalos.
	 */
	public Duration getTotalDuration() {
		Duration totalDuration = Duration.ZERO;
		for(TimeInterval interval: timeIntervals) {
			totalDuration = totalDuration.plus(interval.duration); 
		}
		return totalDuration;
	}
	
	/**
	 * Retorna o tempo total de todos os intervalos formatado em 00:00:00.
	 * @return retorna String formatada 00:00:00
	 */
	public String getTotalDurationFormatted() {
		 return durationFormatted(getTotalDuration());
	}
	
	/**
	 * <p> Imprimi uma tabela com os valores do cronômetro </p>
	 */
	public void prettyPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append(" ----------------------------------------------------------------------------------------------- \n");
		sb.append("|   Id  |   Description                |        Start        |        End          |  Duration  |\n");
		sb.append(" ----------------------------------------------------------------------------------------------- \n");
		String template = ("|%s|%-30s|%s|%s|%s|\n");
		
		for(TimeInterval interval: timeIntervals) {
			String idInterval     = StringUtils.center(""+interval.idInterval, 7);
			String description    = StringUtils.substring(interval.description,0, 30);
			String start          = StringUtils.center(interval.getStartTime().format(formatter), 21); 
			String end            = StringUtils.center(interval.getStartTime().format(formatter), 21);
			String duration       = StringUtils.center(interval.durationFormatted,12);
			sb.append(String.format(template, idInterval, description, start, end, duration));
		}
		sb.append(" -----------------------------------------------------------------------------------------------\n");
		sb.append("Total duration=" + getTotalDurationFormatted());
		System.out.println(sb.toString());
	}
}
