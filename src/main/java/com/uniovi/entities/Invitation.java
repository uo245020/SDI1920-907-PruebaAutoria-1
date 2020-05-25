package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Invitation {
	
	@Id
	@GeneratedValue
	private long id;
	
	
	@ManyToOne
	@JoinColumn(name = "sender_id")
	private User usuarioEnvia;

	@ManyToOne
	@JoinColumn(name = "receiver_id")
	private User usuarioRecibe;
	
	private boolean aceptada;
	
	public Invitation() {}
	
	public Invitation(User usuarioEnvia, User usuarioRecibe) {
		this.usuarioEnvia=usuarioEnvia;
		this.usuarioRecibe=usuarioRecibe;
		this.aceptada=false;
	}

	public User getUsuarioEnvia() {
		return usuarioEnvia;
	}

	public void setUsuarioEnvia(User usuarioEnvia) {
		this.usuarioEnvia = usuarioEnvia;
	}

	public User getUsuarioRecibe() {
		return usuarioRecibe;
	}

	public void setUsuarioRecibe(User usuarioRecibe) {
		this.usuarioRecibe = usuarioRecibe;
	}

	public boolean isAceptada() {
		return aceptada;
	}

	public void setAceptada(boolean aceptada) {
		this.aceptada = aceptada;
	}

	public long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (aceptada ? 1231 : 1237);
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((usuarioEnvia == null) ? 0 : usuarioEnvia.hashCode());
		result = prime * result + ((usuarioRecibe == null) ? 0 : usuarioRecibe.hashCode());
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
		Invitation other = (Invitation) obj;
		if (aceptada != other.aceptada)
			return false;
		if (id != other.id)
			return false;
		if (usuarioEnvia == null) {
			if (other.usuarioEnvia != null)
				return false;
		} else if (!usuarioEnvia.equals(other.usuarioEnvia))
			return false;
		if (usuarioRecibe == null) {
			if (other.usuarioRecibe != null)
				return false;
		} else if (!usuarioRecibe.equals(other.usuarioRecibe))
			return false;
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	

	
	

}
