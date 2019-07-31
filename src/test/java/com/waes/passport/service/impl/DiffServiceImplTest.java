package com.waes.passport.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.waes.passport.config.Constants;
import com.waes.passport.entity.DiffEntity;
import com.waes.passport.model.ResponseDiffModel;
import com.waes.passport.repository.DiffRepository;
import com.waes.passport.service.DiffService;
import com.waes.passport.util.EncodingUtil;

public class DiffServiceImplTest {
	private DiffRepository diffRepository;
	private DiffService diffService;

	@Before
	public void setup() {
		diffRepository = mock(DiffRepository.class);
		diffService = new DiffServiceImpl();
		ReflectionTestUtils.setField(diffService, "diffRepository", diffRepository, DiffRepository.class);
		
	}
	
	@Test
	public void shoudlSaveDiffEntityWithoutErrors() {
		diffService.save(1L, "string".getBytes(), "string".getBytes());
		verify(diffRepository, times(1)).save(any(DiffEntity.class));
	}
	
	@Test
	public void shouldReturnInvalidIdOnDiff() {
		when(diffRepository.findById(any(Long.class))).thenReturn(Optional.empty());
		ResponseDiffModel responseDiffModel = diffService.diff(1L);
		assertEquals(Constants.ID_NOT_FOUND_PLEASE_TRY_AGAIN, responseDiffModel.getResult());
	}
	
	@Test
	public void shouldReturnEqualContentOnDiff() {
		when(diffRepository.findById(any(Long.class))).thenReturn(getEntity(1L, "d2UgYXJlIHdhZXM=", "d2UgYXJlIHdhZXM="));
		ResponseDiffModel responseDiffModel = diffService.diff(1L);
		assertEquals(Constants.EQUAL_CONTENT, responseDiffModel.getResult());
	}
	
	@Test
	public void shouldReturnNotEqualSizeOnDiff() {
		when(diffRepository.findById(any(Long.class))).thenReturn(getEntity(1L, "d2UgYXJlIHdhZXM=", "WWVzIQ=="));
		ResponseDiffModel responseDiffModel = diffService.diff(1L);
		assertEquals(Constants.NOT_EQUAL_SIZE, responseDiffModel.getResult());
	}
	
	@Test
	public void shouldReturnNotEqualContentAndDiffListOnDiff() {
		when(diffRepository.findById(any(Long.class))).thenReturn(getEntity(1L, "YWJjZGVmZ2hpag==", "YTEyMzRmZzExag=="));
		ResponseDiffModel responseDiffModel = diffService.diff(1L);
		assertEquals(Constants.NOT_EQUAL_CONTENT, responseDiffModel.getResult());
		assertTrue(responseDiffModel.getDiffList().get(0).getOffset().equals(1));
		assertTrue(responseDiffModel.getDiffList().get(0).getLength().equals(4));
		assertTrue( responseDiffModel.getDiffList().get(1).getOffset().equals(7));
		assertTrue(responseDiffModel.getDiffList().get(1).getLength().equals(2));
	}
	
	private Optional<DiffEntity> getEntity(Long id, String left, String right) {
		DiffEntity diffEntity = new DiffEntity();
		diffEntity.setId(id);
		diffEntity.setLeft(EncodingUtil.decodeBase64ToByteArray(left));
		diffEntity.setRight(EncodingUtil.decodeBase64ToByteArray(right));
		return Optional.of(diffEntity);
	}
}
