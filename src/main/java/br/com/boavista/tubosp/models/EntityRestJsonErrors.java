package br.com.boavista.tubosp.models;

public class EntityRestJsonErrors {
	private Integer code;
	private String code_message;
	EntityRestHeaderErrors HeaderObject;
	private Integer data_count;
	EntityRestDataErrors DataObject;
	private String errors;
	EntityRestReceiptErrors ReceiptObject;

	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

	// Getter Methods

	public Integer getCode() {
		return code;
	}

	public String getCode_message() {
		return code_message;
	}

	public EntityRestHeaderErrors getHeader() {
		return HeaderObject;
	}

	public Integer getData_count() {
		return data_count;
	}

	public EntityRestDataErrors getData() {
		return DataObject;
	}

	public EntityRestReceiptErrors getReceipt() {
		return ReceiptObject;
	}

	// Setter Methods

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setCode_message(String code_message) {
		this.code_message = code_message;
	}

	public void setHeader(EntityRestHeaderErrors headerObject) {
		this.HeaderObject = headerObject;
	}

	public void setData_count(Integer data_count) {
		this.data_count = data_count;
	}

	public void setData(EntityRestDataErrors dataObject) {
		this.DataObject = dataObject;
	}

	public void setReceipt(EntityRestReceiptErrors receiptObject) {
		this.ReceiptObject = receiptObject;
	}
}
