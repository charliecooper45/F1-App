package f1app.gui;

import f1app.core.Circuits;
import f1app.core.LapTime;
import f1app.core.Tyres;

public interface LapTimeChangeListener {
	public boolean addLapTime(LapTime time, Circuits circuit);
	public boolean deleteLapTime(Circuits circuit, Tyres tyre);
}
