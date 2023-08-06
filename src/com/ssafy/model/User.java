package com.ssafy.model;

import java.io.Serializable;

//객체 입출력: 직렬화 객체
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8522453566584619547L;
	private String id;
	private String pw;
	private String name;
	private String mobile;
	private String entryDate;
	
	public User() {}
	public User(String id, String pw, String name, String mobile, String entryDate) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.mobile = mobile;
		this.entryDate = entryDate;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(id);
		buffer.append(", ");
		buffer.append(pw);
		buffer.append(", ");
		buffer.append(name);
		buffer.append(", ");
		buffer.append(mobile);
		buffer.append(", ");
		buffer.append(entryDate);
		return buffer.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public void showMyInfo() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("아이디: ");
		buffer.append(id);
		buffer.append(", 비밀번호: ");
		buffer.append(pw);
		buffer.append(", 이름: ");
		buffer.append(name);
		buffer.append(", 휴대폰: ");
		buffer.append(mobile);
		buffer.append(", 가입일: ");
		buffer.append(entryDate);
		
		System.out.println(buffer.toString());
	}
	
	
}
