package f1app.core;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

//TODO NEXT: Add functionality to save all the Circuits (probably in F1 Coord class)
public class Circuit {
	private Circuits circuitType;
	private String length;
	private String lapRecord;
	private String time2013;
	private EnumMap<Tyres, LapTime> circuitTimes;
	
	public Circuit(Circuits circuitType, String length, String lapRecord, String time2013) {
		this.circuitType = circuitType;
		this.length = length;
		this.lapRecord = lapRecord;
		this.time2013 = time2013;
		this.circuitTimes = new EnumMap<>(Tyres.class);
	}

	/**
	 * @return the circuitType
	 */
	public Circuits getCircuitType() {
		return circuitType;
	}

	/**
	 * @return the length
	 */
	public String getLength() {
		return length;
	}

	/**
	 * @return the lapRecord
	 */
	public String getLapRecord() {
		return lapRecord;
	}

	/**
	 * @return the time2013
	 */
	public String getTime2013() {
		return time2013;
	}
	
	/**
	 * @return the circuitTimes
	 */
	public Map<Tyres, LapTime> getCircuitTimes() {
		return Collections.unmodifiableMap(circuitTimes);
	}

	public boolean addLapTime(LapTime lapTime) {
		Tyres tyreChoice = lapTime.getTyreChoice();
		
		if(circuitTimes.containsKey(tyreChoice)) {
			LapTime previousTime = circuitTimes.get(tyreChoice);
			// Compare the two times and check the new one is faster
			if(lapTime.compareTo(previousTime) >= 0) {
				System.out.println("Old lap time is faster");
				return false;
			}
		}
		circuitTimes.put(tyreChoice, lapTime);
		System.out.println(circuitTimes);
		return true;
	}
}
