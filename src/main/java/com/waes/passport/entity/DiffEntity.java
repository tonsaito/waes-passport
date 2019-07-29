package com.waes.passport.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Diff Entity that contains the left and right fields to generate the final
 * comparison
 * 
 * @author tonsaito
 *
 */
@Entity
@Table(name = "diff", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class DiffEntity {

	@Id
	@Column(name = "id", nullable = false, unique = true)
	private Long id;

	@Column(name = "left", nullable = true)
	private byte[] left;

	@Column(name = "right", nullable = true)
	private byte[] right;

	public DiffEntity() {
	}
	
	public DiffEntity(Long id) {
		this.id = id;
	}

	public DiffEntity(Long id, byte[] left, byte[] right) {
		this.id = id;
		this.left = left;
		this.right = right;
	}

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getLeft() {
		return left;
	}

	public void setLeft(byte[] left) {
		this.left = left;
	}

	public byte[] getRight() {
		return right;
	}

	public void setRight(byte[] right) {
		this.right = right;
	}

}
