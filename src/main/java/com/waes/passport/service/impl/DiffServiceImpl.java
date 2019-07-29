package com.waes.passport.service.impl;

import java.util.Arrays;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.waes.passport.config.Constants;
import com.waes.passport.entity.DiffEntity;
import com.waes.passport.model.DiffHintModel;
import com.waes.passport.model.ResponseDiffModel;
import com.waes.passport.repository.DiffRepository;
import com.waes.passport.service.DiffService;

/**
 * DiffServiceImpl with the implementation of the DiffService Interface
 * @author tonsaito
 *
 */
@Component
public class DiffServiceImpl implements DiffService{
	private static final Logger LOGGER = Logger.getLogger(DiffServiceImpl.class);
	
	@Autowired
	private DiffRepository diffRepository;

	/**
	 * Saves the DiffEntity getting the id, left and right params to create a new DiffEntity object or an Existing one if the id
	 * already exists
	 * Transaction is used here to guarantee the atomicity of the request
	 * @param id     the id of DiffEntity
	 * @param left   the left data of DiffEntity
	 * @param right  the right data of DiffEntity
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Long id, byte[] left, byte[] right) {
		Optional<DiffEntity> optionalDiffEntity = diffRepository.findById(id);
		DiffEntity diffEntity = optionalDiffEntity.orElse(new DiffEntity(id));
		
		if(left != null) {
			diffEntity.setLeft(left);
		}
		if(right != null) {
			diffEntity.setRight(right);
		}
		diffRepository.save(diffEntity);
		LOGGER.info("Saved DiffEntity with the follow id: "+id);
	}
	
	/**
	 * Returns the DiffEntity associated with the given id
	 * Transaction readOnly | Propagation.SUPPORTS
	 * @param id the requested id associated of a DiffEntity entry
	 * @return DiffEntity
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DiffEntity findById(Long id) {
		Optional<DiffEntity> optionalDiffEntity = diffRepository.findById(id);
		return optionalDiffEntity.orElse(new DiffEntity());
	}

	@Override
	public ResponseDiffModel diff(Long id) {
		ResponseDiffModel responseDiffModel = new ResponseDiffModel();
		Optional<DiffEntity> optionalDiffEntity = diffRepository.findById(id);
		DiffEntity diffEntity = optionalDiffEntity.orElse(null);
		
		if(diffEntity == null) {
			responseDiffModel.setResult(Constants.INVALID_ID_PLEASE_TRY_AGAIN);
		} else if ((diffEntity.getLeft() == null) && (diffEntity.getRight() == null) || Arrays.equals(diffEntity.getLeft(), diffEntity.getRight())) {
			responseDiffModel.setResult(Constants.EQUAL_CONTENT);
			return responseDiffModel;
		} else if (diffEntity.getLeft().length != diffEntity.getRight().length) {
			responseDiffModel.setResult(Constants.NOT_EQUAL_SIZE);
			return responseDiffModel;
		} else {
			for(int i=0; i< diffEntity.getLeft().length; i++) {
				if(diffEntity.getLeft()[i] != diffEntity.getRight()[i]) {
					responseDiffModel.addDiffList(new DiffHintModel(i, Math.abs(diffEntity.getRight()[i]-diffEntity.getLeft()[i])));
				}
				responseDiffModel.setResult(Constants.NOT_EQUAL_CONTENT);
			}
		}
		return responseDiffModel;
	}
}
