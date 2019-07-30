package com.waes.passport.service;

import com.waes.passport.model.ResponseDiffModel;

/**
 * DiffService Interface with the main Diffs methods to be implemented
 * @author tonsaito
 */
public interface DiffService {
	public void save(Long id, byte[] left, byte[] right);
	public ResponseDiffModel diff(Long id);
}
