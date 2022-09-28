package com.capgemini.testutil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Object, used to test JSON serialization
 */
public class JSONTestObject {
    private String attrib1 = "test1";
    private String attrib2 = "test2";
    private Integer int1 = 1;
    private Boolean booleanAttrib = true;

    public String getAttrib1() {
        return attrib1;
    }

    public void setAttrib1(String attrib1) {
        this.attrib1 = attrib1;
    }

    public String getAttrib2() {
        return attrib2;
    }

    public void setAttrib2(String attrib2) {
        this.attrib2 = attrib2;
    }

    public Integer getInt1() {
        return int1;
    }

    public void setInt1(Integer int1) {
        this.int1 = int1;
    }

    public Boolean getBooleanAttrib() {
        return booleanAttrib;
    }

    public void setBooleanAttrib(Boolean booleanAttrib) {
        this.booleanAttrib = booleanAttrib;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        //        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return super.toString();
        }
    }

}
