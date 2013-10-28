package f1app.gui;

import f1app.core.Circuits;
import f1app.core.LapTime;

public interface NewLapTimeListener {
	public void addLapTime(LapTime time, Circuits circuit);
}
