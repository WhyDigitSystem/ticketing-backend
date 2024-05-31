package com.base.basesetup.dto;

public class UserCountDTO {
	 private Long totalCustomer;
	    private Long totalEmployee;

	    public UserCountDTO(Long totalCustomer, Long totalEmployee) {
	        this.totalCustomer = totalCustomer;
	        this.totalEmployee = totalEmployee;
	    }

	    // Getters and setters
	    public Long getTotalCustomer() {
	        return totalCustomer;
	    }

	    public void setTotalCustomer(Long totalCustomer) {
	        this.totalCustomer = totalCustomer;
	    }

	    public Long getTotalEmployee() {
	        return totalEmployee;
	    }

	    public void setTotalEmployee(Long totalEmployee) {
	        this.totalEmployee = totalEmployee;
	    }
}
