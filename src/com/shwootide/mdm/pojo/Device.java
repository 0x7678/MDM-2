package com.shwootide.mdm.pojo;

//������ʱ�Ȳ���
public class Device {
	private String deviceName;//���ֿ�������
	private String deviceType;//�豸���ͣ�android��ios��winphone�ȵȣ�����������ϸ��һЩ������ϵͳ�ͺŵȶ������оٽ���
	private String cid;//��������ϢID�����IDҲ��Ψһ��û���û���¼���˵���ˣ�����Ψһ��
	private String IMEI;//�豸Ψһʶ���ʾ�ţ������Ǹ���������������Ϣ�����õģ�
	private String phoneNumber;//����SIM����ʱ���ȡ�ֻ��Ż���SIM������
	//����IMEI�����Ի�ȡ���ܶ��ֻ������̣����ֻ���ص�һЩ������Ϣ
	//http://wenku.baidu.com/link?url=3ap3tRGKqxyLKfaztnEf8IpxT8XXapexoczwZSa3psc-5PVmq8TfnUHC6azBhHok01IgmDbD3IeRXgPPyl9JIgZfX75EW3YoSfEre2jDT5q
	
	
	public void Device()
	{
		//�����ӣ�������д��ϵͳ�����ӣ�ʲôҲ����
	}
	
	
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;//�����豸����
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getIMEI() {
		return IMEI;
	}
	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	
	
	

}
