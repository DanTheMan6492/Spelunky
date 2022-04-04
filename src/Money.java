public class Money 
{
	public int money;
	
	public Money(int x)
	{
		this.money = 0;
	}
	
	public void getMoney(int x)
	{
		money += x;
	}
	
	public void spendMoney(int x)
	{
		if (x>money){}
		else
		{
			money -= x;
		}
	}
}