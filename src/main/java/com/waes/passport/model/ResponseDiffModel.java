package com.waes.passport.model;

import java.util.ArrayList;
import java.util.List;

/**
 * ResponseDiffModel to use as a response of the diff api method with the result 
 * and diffList of the comparison of the left and right values
 * @author tonsaito
 *
 */
public class ResponseDiffModel {

	private String result;
	private List<DiffHintModel> diffList = new ArrayList<DiffHintModel>();

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<DiffHintModel> getDiffList() {
		return diffList;
	}

	public void setDiffList(List<DiffHintModel> diffList) {
		this.diffList = diffList;
	}
	
	public void addDiffList(DiffHintModel diffHintModel) {
		this.diffList.add(diffHintModel);
	}

}
