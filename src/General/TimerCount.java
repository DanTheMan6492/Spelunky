package General;
public class TimerCount 
{
	public int totalSeconds;
	public int totalMinutes;
	public int seconds;
	public int minutes;
	public int increment;
	
	public TimerCount()
	{
		totalSeconds = 0;
		totalMinutes = 0;
		seconds = 0;
		minutes = 0;
		increment = 30;
	}

	public void stageEnd()
	{
		totalSeconds += seconds;
		totalMinutes += (minutes + totalSeconds/60);
		totalSeconds = totalSeconds%60;
		seconds = 0;
		minutes = 0;
	}

	public void update()
	{
		increment--;
		if (increment == 0)
		{
			increment = 60;
			seconds++;
			minutes = seconds/60;
			seconds = seconds%60;
		}
	}
}