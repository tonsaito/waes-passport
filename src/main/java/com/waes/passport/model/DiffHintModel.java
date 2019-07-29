package com.waes.passport.model;

/**
 * DiffHintModel helps to provide insights in where the diffs are
 * @author tonsaito
 *
 */
public class DiffHintModel {

	private Integer offset;
	private Integer length;
	
	public DiffHintModel() {}
	
	public DiffHintModel(Integer offset, Integer length) {
		this.offset = offset;
		this.length = length;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

}
