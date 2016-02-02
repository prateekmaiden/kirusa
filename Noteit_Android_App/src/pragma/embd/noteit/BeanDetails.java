package pragma.embd.noteit;

public class BeanDetails{
	
	public String Id, Subject, Details, Image, Dates, Times;
	
	public BeanDetails(){
		
	}
	
	public BeanDetails(String id, String subject){
		
		Id = id;
		Subject = subject;
	}
	
	public BeanDetails(String id, String subject, String dates, String times){
		
		Id = id;
		Subject = subject;
		Dates = dates;
		Times = times;
	}
	
	

}
