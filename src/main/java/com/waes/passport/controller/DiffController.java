package com.waes.passport.controller;

import java.io.UnsupportedEncodingException;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waes.passport.config.Constants;
import com.waes.passport.model.RequestDiffModel;
import com.waes.passport.model.ResponseDiffModel;
import com.waes.passport.model.ResponseModel;
import com.waes.passport.service.DiffService;
import com.waes.passport.util.EncodingUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * DiffControler class exposes all the Diff Rest APIs starting with /v1/diff
 * @author tonsaito
 *
 */
@Api(value = "Diff")
@RestController
@RequestMapping("/v1/diff")
@Produces(MediaType.APPLICATION_JSON)
public class DiffController {
	
	@Autowired
	private DiffService diffService;

	/**
	 * left method saves base64 values associated to a given id. this method will validate the given payload and check if
	 * the given id is numeric or not
	 * @param id         		the associated id
	 * @param requestDiffModel  payload with the base64 value
	 * @return response with the result of the save operation
	 * @throws NumberFormatException
	 * @throws UnsupportedEncodingException
	 */
	@ApiOperation(value="Saves and converts the LEFT base64 value of the given id")
	@PutMapping("/{id}/left")
	public ResponseEntity<?> left(@PathVariable("id") String id, @Valid @RequestBody RequestDiffModel requestDiffModel) throws NumberFormatException, UnsupportedEncodingException {
		if(!StringUtils.isNumeric(id)) {
	        return new ResponseEntity<ResponseModel>(new ResponseModel(Constants.INVALID_ID_PLEASE_TRY_AGAIN), HttpStatus.BAD_REQUEST); 
		}
		diffService.save(Long.parseLong(id), EncodingUtil.decodeBase64ToByteArray(requestDiffModel.getEncodedData()), null);
        return new ResponseEntity<ResponseModel>(new ResponseModel(Constants.REQUEST_RECEIVED_SUCCESSFULLY), HttpStatus.CREATED); 
	}
	
	/**
	 * right method saves base64 values associated to a given id. this method will validate the given payload and check if
	 * the given id is numeric or not
	 * @param id         		the associated id
	 * @param requestDiffModel  payload with the base64 value
	 * @return response with the result of the save operation
	 * @throws NumberFormatException
	 * @throws UnsupportedEncodingException
	 */
	@ApiOperation(value="Saves and converts the RIGHT base64 value of the given id")
	@PutMapping("/{id}/right")
	public ResponseEntity<?> right(@PathVariable("id") String id, @Valid @RequestBody RequestDiffModel requestDiffModel) throws NumberFormatException, UnsupportedEncodingException {
		if(!StringUtils.isNumeric(id)) {
	        return new ResponseEntity<ResponseModel>(new ResponseModel(Constants.INVALID_ID_PLEASE_TRY_AGAIN), HttpStatus.BAD_REQUEST); 
		}
		diffService.save(Long.parseLong(id), null, EncodingUtil.decodeBase64ToByteArray(requestDiffModel.getEncodedData()));
        return new ResponseEntity<ResponseModel>(new ResponseModel(Constants.REQUEST_RECEIVED_SUCCESSFULLY), HttpStatus.CREATED); 
	}
	
	/**
	 * executes the diff method following the given id and return the result
	 * @param id
	 * @return 
	 */
	@ApiOperation(value="Execute diff operation between left and right values")
	@GetMapping("/{id}")
	public ResponseEntity<?> diff(@PathVariable("id") String id) {
		if(!StringUtils.isNumeric(id)) {
	        return new ResponseEntity<ResponseModel>(new ResponseModel(Constants.INVALID_ID_PLEASE_TRY_AGAIN), HttpStatus.BAD_REQUEST); 
		}
        return new ResponseEntity<ResponseDiffModel>(diffService.diff(Long.parseLong(id)), HttpStatus.OK); 
	}
}
