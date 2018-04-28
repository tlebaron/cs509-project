package seat;

public class Seats {
	double price;
	int numberOfSeats;
	SeatClass seatClass;
	
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
	
	public SeatClass getSeatClass() {
		return this.seatClass;
	}
	
	public void setSeatClass(SeatClass classtype) {
		seatClass = classtype;
	}
}
