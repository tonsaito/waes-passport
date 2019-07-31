package com.waes.passport.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waes.passport.config.Application;
import com.waes.passport.config.Constants;
import com.waes.passport.entity.DiffEntity;
import com.waes.passport.model.RequestDiffModel;
import com.waes.passport.repository.DiffRepository;
import com.waes.passport.util.EncodingUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class DiffControllerTest {
	private static final ObjectMapper om = new ObjectMapper();

	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
    private DiffRepository diffRepository;
	
	@Before
	public void setup() {
		when(diffRepository.findById(any(Long.class))).thenReturn(getEntity(1L, "YWJjZGVmZ2hpag==", "YTEyMzRmZzExag=="));
	}
		
	@Test
    public void shouldReturnBadRequestForNonNumericIdOnLeftMethod() throws Exception {
		mockMvc.perform(put("/v1/diff/abc/left")
                .content(om.writeValueAsString(new RequestDiffModel("YWJjZGU=")))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
	
	@Test(expected=NestedServletException.class)
    public void shouldThrowNestedServletExceptionForInvalidBase64EntryOnLeftMethod() throws Exception {
		mockMvc.perform(put("/v1/diff/1/left")
                .content(om.writeValueAsString(new RequestDiffModel("abcd%")))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is(Constants.INVALID_BASE64_VALUE)));
	}
	
	@Test
    public void shouldSaveLeftContentForTheGivenId() throws Exception {
		mockMvc.perform(put("/v1/diff/1/left")
                .content(om.writeValueAsString(new RequestDiffModel("YWJjZGU=")))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(Constants.REQUEST_RECEIVED_SUCCESSFULLY)));
		verify(diffRepository, times(1)).save(any(DiffEntity.class));
    }
	
	@Test
    public void shouldReturnBadRequestForNonNumericIdOnRightMethod() throws Exception {
		mockMvc.perform(put("/v1/diff/abc/right")
                .content(om.writeValueAsString(new RequestDiffModel("YWJjZGU=")))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
	
	@Test(expected=NestedServletException.class)
    public void shouldThrowNestedServletExceptionForInvalidBase64EntryOnRightMethod() throws Exception {
		mockMvc.perform(put("/v1/diff/1/right")
                .content(om.writeValueAsString(new RequestDiffModel("abcd%")))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.result", is(Constants.INVALID_BASE64_VALUE)));
	}
	
	@Test
    public void shouldSaveRightContentForTheGivenId() throws Exception {
		mockMvc.perform(put("/v1/diff/1/right")
                .content(om.writeValueAsString(new RequestDiffModel("YWJjZGU=")))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(Constants.REQUEST_RECEIVED_SUCCESSFULLY)));
		verify(diffRepository, times(1)).save(any(DiffEntity.class));
    }
	
	@Test
    public void shouldReturnBadRequestForNonNumericIdOnDiffMethod() throws Exception {
		mockMvc.perform(get("/v1/diff/abc")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
	
	@Test
    public void shouldReturnOkAndTheDiffResultForTheGivenId() throws Exception {
		mockMvc.perform(get("/v1/diff/1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(Constants.NOT_EQUAL_CONTENT)))
                .andExpect(jsonPath("$.diffList[0].offset", is(1)))
		        .andExpect(jsonPath("$.diffList[0].length", is(4)))
		        .andExpect(jsonPath("$.diffList[1].offset", is(7)))
		        .andExpect(jsonPath("$.diffList[1].length", is(2)));
		verify(diffRepository, times(1)).findById(any(Long.class));
    }
	
	private Optional<DiffEntity> getEntity(Long id, String left, String right) {
		DiffEntity diffEntity = new DiffEntity();
		diffEntity.setId(id);
		diffEntity.setLeft(EncodingUtil.decodeBase64ToByteArray(left));
		diffEntity.setRight(EncodingUtil.decodeBase64ToByteArray(right));
		return Optional.of(diffEntity);
	}

}