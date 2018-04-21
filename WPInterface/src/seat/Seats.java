package seat;

public class Seats {
	public double price;
	int numberOfSeats;
	public SeatClass seatClass;
	
	public int getNumberOfSeats(){
		return this.numberOfSeats;
	}
	public void setNumberOfSeats(int number){
		this.numberOfSeats = number;
	}
	
	public double getPrice(){
		return price;
	}
	
	public void setPrice(double p){
		price = p;
	}
}
