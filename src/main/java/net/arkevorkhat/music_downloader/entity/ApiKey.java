package net.arkevorkhat.music_downloader.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ApiKey {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String keyText;
	public ApiKey() {}
	
	@Override
	public String toString() {
		return "ApiKey [keyText=" + keyText + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyText == null) ? 0 : keyText.hashCode());
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
		ApiKey other = (ApiKey) obj;
		if (keyText == null) {
			if (other.keyText != null)
				return false;
		} else if (!keyText.equals(other.keyText))
			return false;
		return true;
	}
}
