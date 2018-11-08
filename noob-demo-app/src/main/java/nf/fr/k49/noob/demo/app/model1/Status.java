package nf.fr.k49.noob.demo.app.model1;

import java.time.LocalDate;

public class Status {
	private String version = "0.1.0";
	private LocalDate buildTime = LocalDate.now();

	public String getVersion() {
		return version;
	}

	public LocalDate getBuildTime() {
		return buildTime;
	}

}
