package br.com.example.forum.Forum.model.Persistent;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.stereotype.Component;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author icaroafonso
 *
 */

@SuppressWarnings("serial")
@MappedSuperclass
@Component
public abstract class Persistent implements Serializable, Cloneable {
	
	protected Integer id;
	
	@Transient
	public abstract Integer getId();
	
	public void setId(Integer id) {
		this.id  = id;
	}
	
	public Persistent() {
		super();
	}

	public Persistent(Integer id) {
		super();
		this.id = id;
	}	
}