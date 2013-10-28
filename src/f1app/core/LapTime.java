package f1app.core;

/**
 * Holds the minutes, seconds and milliseconds as well as the tyre used for a lap of a circuit.
 * @author Charlie
 *
 */
public class LapTime implements Comparable<LapTime> {
	private Tyres tyreChoice;
	private String minutes;
	private String seconds;
	private String milliseseconds;

	public LapTime(Tyres tyreChoice, String minutes, String seconds, String milliseconds) {
		this.tyreChoice = tyreChoice;
		this.minutes = minutes;
		this.seconds = seconds;
		this.milliseseconds = milliseconds;
	}

	/**
	 * @return tyreChoice
	 */
	public Tyres getTyreChoice() {
		return tyreChoice;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return minutes + ":" + seconds + "." + milliseseconds;
	}

	@Override
	public int compareTo(LapTime o) {
		if(!minutes.equals(o.minutes)) { 
			return minutes.compareTo(o.minutes);
		} else if (!seconds.equals(o.seconds)) {
			return seconds.compareTo(o.seconds);
		} else {
			return milliseseconds.compareTo(o.milliseseconds);
		}
	}
}
