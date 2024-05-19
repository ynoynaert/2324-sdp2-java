package domain;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Getter;


public abstract class OrderState{
	
	protected final SubOrder subOrder;

	public OrderState(SubOrder subOrder) {
		this.subOrder=subOrder;
	}
	
	public abstract int getId();
	
	public boolean setOpen() {
		throw new UnsupportedOperationException();
	}
	
	public boolean setInProgress() {
		throw new UnsupportedOperationException();
	}
	
	public boolean setFinished() {
		throw new UnsupportedOperationException();
	}
	
	public abstract String getName();
	

}
