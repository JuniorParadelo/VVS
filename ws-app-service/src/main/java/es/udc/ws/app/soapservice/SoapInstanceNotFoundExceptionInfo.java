package es.udc.ws.app.soapservice;

public class SoapInstanceNotFoundExceptionInfo {

	private Object instanceId;
	private String instanceType;
	
	public SoapInstanceNotFoundExceptionInfo(){
		
	}
	
	public SoapInstanceNotFoundExceptionInfo(Object instanceId,String instanceType){
		this.instanceId=instanceId;
		this.instanceType=instanceType;
	}
	
	public Object getinstanceId(){
		
		return this.instanceId;
	}
	public void setinstanceId(Object instanceId){
		this.instanceId=instanceId;
		
	}
	
	public String getinstanceType(){
		
		return this.instanceType;
	}
	
	public void setinstanceType(String instanceType){
		this.instanceType=instanceType;
		
	}
}
