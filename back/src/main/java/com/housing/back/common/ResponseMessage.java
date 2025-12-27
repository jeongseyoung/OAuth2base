package com.housing.back.common;

public interface ResponseMessage {
    String SUCCESS = "SUCCESS";

    String VALIDATION_FAIL = "VALIDATION_FAIL";

    String DUPLICATED_ID = "DUPLICATED_ID";
  
    String SIGN_IN_FAIL = "SIGN_IN_FAIL";

    String CERTIFICATION_FAIL = "CERTIFICATION_FAIL";

    String DATABASE_ERROR = "DATABASE_ERROR";

    String MAIL_FAIL = "mail send failed";   
}
